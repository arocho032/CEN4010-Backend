package sosInterface;

import java.util.ArrayList;

import org.json.*;

import io.socket.client.IO;
import io.socket.client.Socket;
import user.UserManager;
import organization.OrganizationManager;
import event.EventManager;

/**
 * A Command class which propagates the front-end requests
 * to their specific target controllers. The requests messages 
 * which are parsed and pre-processed by the SOS Server then 
 * are used to call an appropriate dispatch from the SOS Dispatcher, 
 * which is in charge of directly calling all other controllers. 
 * Instead of being their own classes, each subcommand is defined 
 * in terms of parametrizations of the dispatch function within the 
 * SOS Dispatcher. Internally, SOS Dispatcher also keeps track of 
 * these requests and stores them in the Database
 */
public class SOSDispatcher {
	
	private boolean status;
	private String message;
	private Socket socket;
	
	public SOSDispatcher(String uri)
	{
		try
		{
			socket = IO.socket(uri);
		}
		catch(Exception ex)
		{
			status = false;
			message = "Incorrect format for output URI.";
		}
		
	}
	
	public boolean getStatus()
	{
		return this.status;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	/**
	* The method for dispatching events.
	*/
	public void Dispatch(String eventName, int typeOfRequest, Object... input ) 
	{
		status = true;
		switch(typeOfRequest)
		{
			case 1:
				dispatchUserEvents(eventName);
				break;
				
			case 2:
				dispatchEventEvents(eventName);
				break;
				
			default:
				dispatchOrganizationEvents(eventName);
				break;
		}
	}
	
	private void dispatchUserEvents(String eventName, Object... input)
	{
		try
		{
			switch(eventName)
			{
				
				case "create":
					try
					{
						UserManager manager = UserManager.instance();
				
						manager.CreateNewProfile((JSONObject)input[0]);		
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching user creation.\nMore Details: " + ex.getMessage());
					}
					break;
					
				case "load":
					try
					{
						UserManager manager = UserManager.instance();
				
						JSONObject json = (JSONObject)input[0];
						
						int userID = json.getInt("userID");
						
						socket.emit("loadedUser", manager.LoadUser(userID) );
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching user load.\nMore Details: " + ex.getMessage());
					}
					break;
					
				case "update":
					try
					{
						UserManager manager = UserManager.instance();
				
						JSONObject json = (JSONObject)input[0];
						
						int userID = json.getInt("userID");
						
						manager.ChangeUserDetails(userID, json);
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching user creation.\nMore Details: " + ex.getMessage());
					}
					break;
					
			}
		}
		catch(Exception ex)
		{
			status = false;
			message = ex.getMessage();
			
			socket.emit("failure", message);
			
		}
	}
	
	private void dispatchEventEvents(String eventName, Object... input)
	{
		try
		{
			
			EventManager manager = EventManager.instance();
			
			JSONObject json = (JSONObject)input[0];
			
			switch(eventName)
			{
				
				case "create":
					try
					{
						manager.createEvent(json);
						
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching event creation.\nMore Details: " + ex.getMessage());
					}
					break;
					
				case "loadOne":
					try
					{	
						int eventID = json.getInt("eventID");
						
						socket.emit("eventDetailsResponse", manager.loadEventDetails(eventID));
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching event single load.\nMore Details: " + ex.getMessage());
					}
					break;
					
				case "loadAll":
					try
					{
						socket.emit("allEventsResponse", manager.retrieveListOfEvents());
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching loading of all events.\nMore Details: " + ex.getMessage());
					}
					break;
					
				case "loadByLocation":
					try
					{
						double latitude = json.getDouble("latitude");
						
						double longitude = json.getDouble("longitude");
						
						socket.emit("allEventsResponse", manager.retrieveListOfEventsByLocation(latitude, longitude));
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching loading all events by location.\nMore Details: " + ex.getMessage());
					}
				case "cancel":
					try
					{	
						int eventID = json.getInt("eventID");
						
						manager.cancelEvent(eventID);
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching event cancellation.\nMore Details: " + ex.getMessage());
					}
					break;
				case "attend":
					try
					{	
						int eventID = json.getInt("eventID");
						
						JSONObject user = (JSONObject)input[2];
						
						manager.markAttendance(user.getInt("userID"), eventID);
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching event attendance.\nMore Details: " + ex.getMessage());
					}
					break;
			}
		}
		catch(Exception ex)
		{
			status = false;
			message = ex.getMessage();
			
			socket.emit("failure", message);
			
		}
	}
	
	private void dispatchOrganizationEvents(String eventName, Object... input)
	{
		try
		{
			
			OrganizationManager manager = OrganizationManager.instance();
			
			JSONObject json = (JSONObject)input[0];
			
			switch(eventName)
			{
				
				case "create":
					try
					{
						manager.createOrganization(json);
						
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching organization creation.\nMore Details: " + ex.getMessage());
					}
					break;
					
				case "loadOne":
					try
					{	
						int organizationID = json.getInt("organizationID");
						
						socket.emit("loadOrganizationResponse", manager.loadOrganizationDetails(organizationID));
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching organization single load.\nMore Details: " + ex.getMessage());
					}
					break;
					
				case "loadAll":
					try
					{
						socket.emit("allOrganizationResponse", manager.getAllOrganizations());
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching loading of all organizations.\nMore Details: " + ex.getMessage());
					}
					break;
					
				case "loadByUser":
					try
					{
						JSONObject user = (JSONObject)input[2];
						
						socket.emit("organizationByUserResponse", manager.getAllOrganizations(user.getInt("userID")));
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching loading all organizations by user.\nMore Details: " + ex.getMessage());
					}
				case "join":
					try
					{	
						int organizationID = json.getInt("organizationID");
						
						JSONObject user = (JSONObject)input[2];
						
						manager.joinOrganization(user.getInt("userID"), organizationID);
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching the request to join an organization.\nMore Details: " + ex.getMessage());
					}
					break;
				case "grantRole":
					try
					{	
						int organizationID = json.getInt("organizationID");
						
						JSONObject user = (JSONObject)input[2];
						
						JSONObject role = (JSONObject)input[3];
						
						manager.grantRole(user.getInt("userID"), organizationID, role.getString("roleName"), );
					}
					catch(Exception ex)
					{
							throw new Exception("An error occurred while dispatching event single load.\nMore Details: " + ex.getMessage());
					}
					break;
			}
		}
		catch(Exception ex)
		{
			status = false;
			message = ex.getMessage();
			
			socket.emit("failure", message);
			
		}
	}
	
	
	
}