//package sosInterface;
//
//import java.util.ArrayList;
//
//import org.json.*;
//
//import com.corundumstudio.socketio.*;
//import com.corundumstudio.socketio.protocol.Packet;
//
//import user.UserManager;
//import utils.EnumerationsAndConstant;
//import organization.OrganizationManager;
//import event.EventManager;
//
//import utils.EnumerationsAndConstant;
//
///**
// * A Command class which propagates the front-end requests
// * to their specific target controllers. The requests messages 
// * which are parsed and pre-processed by the SOS Server then 
// * are used to call an appropriate dispatch from the SOS Dispatcher, 
// * which is in charge of directly calling all other controllers. 
// * Instead of being their own classes, each subcommand is defined 
// * in terms of parametrizations of the dispatch function within the 
// * SOS Dispatcher. Internally, SOS Dispatcher also keeps track of 
// * these requests and stores them in the Database
// */
//public class SOSDispatcher_Old {
//	
//	private boolean status;
//	private String message;
//	private String command;
//	private JSONObject jsonMsg;
//	private SocketIOClient client;
//	
//	
//	public SOSDispatcher_Old(SocketIOClient client, JSONObject json, String command)
//	{
//		try
//		{
//			status = true;
//			message = "";
//			jsonMsg = json;
//			this.command = command;
//			this.client = client;
//		}
//		catch(Exception ex)
//		{
//			status = false;
//			message = "Incorrect format for output URI.";
//		}
//		
//	}
//	
//	public boolean getStatus()
//	{
//		return this.status;
//	}
//	
//	public String getMessage()
//	{
//		return this.message;
//	}
//	
//	/**
//	* The method for dispatching events.
//	*/
//	public void Dispatch(EnumerationsAndConstant.REQUEST_TYPE request) 
//	{
//		status = true;
//		switch(request)
//		{
//			case USER:
//				dispatchUserEvents(this.command);
//				break;
//				
//			case EVENT:
//				dispatchEventEvents(this.command);
//				break;
//				
//			default:
//				dispatchOrganizationEvents(this.command);
//				break;
//		}
//	}
//	
//	private void dispatchUserEvents(String userCommand)
//	{
//		try
//		{
//			switch(userCommand)
//			{
//				
//				case "create":
//					try
//					{
//						UserManager manager = UserManager.instance();
//				
//						manager.CreateNewProfile(jsonMsg);		
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching user creation.\nMore Details: " + ex.getMessage());
//					}
//					break;
//					
//				case "load":
//					try
//					{
//						UserManager manager = UserManager.instance();
//				
//						
//						
//						int userID = jsonMsg.getInt("userID");
//						
//						client.sendEvent("userLoadDetails", manager.LoadUser(userID));
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching user load.\nMore Details: " + ex.getMessage());
//					}
//					break;
//					
//				case "update":
//					try
//					{
//						UserManager manager = UserManager.instance();
//						
//						int userID = jsonMsg.getInt("userID");
//						
//						manager.ChangeUserDetails(userID, jsonMsg);
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching user creation.\nMore Details: " + ex.getMessage());
//					}
//					break;
//					
//			}
//		}
//		catch(Exception ex)
//		{
//			status = false;
//			message = ex.getMessage();
//			client.sendEvent("failure", ex.getMessage());
//			System.out.println(ex.getMessage());
//		}
//	}
//	
//	private void dispatchEventEvents(String eventCommand)
//	{
//		try
//		{
//			
//			EventManager manager = EventManager.instance();
//			
//			switch(eventCommand)
//			{
//				
//				case "create":
//					try
//					{
//						manager.createEvent(jsonMsg);
//						
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching event creation.\nMore Details: " + ex.getMessage());
//					}
//					break;
//					
//				case "loadOne":
//					try
//					{	
//						int eventID = jsonMsg.getInt("eventID");
//						
//						client.sendEvent("eventLoadDetails", manager.loadEventDetails(eventID));
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching event single load.\nMore Details: " + ex.getMessage());
//					}
//					break;
//					
//				case "loadAll":
//					try
//					{
//						client.sendEvent("eventLoadAllEvents", manager.retrieveListOfEvents());
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching loading of all events.\nMore Details: " + ex.getMessage());
//					}
//					break;
//					
//				case "loadByLocation":
//					try
//					{
//						double latitude = jsonMsg.getDouble("latitude");
//						
//						double longitude = jsonMsg.getDouble("longitude");
//						
//						client.sendEvent("eventLoadByLocation", manager.retrieveListOfEventsByLocation(latitude, longitude));
//						
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching loading all events by location.\nMore Details: " + ex.getMessage());
//					}
//				case "cancel":
//					try
//					{	
//						int eventID = jsonMsg.getInt("eventID");
//						
//						manager.cancelEvent(eventID);
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching event cancellation.\nMore Details: " + ex.getMessage());
//					}
//					break;
//				case "attend":
//					try
//					{	
//						int eventID = jsonMsg.getJSONObject("event").getInt("eventID");
//						
//						int userID = jsonMsg.getJSONObject("user").getInt("userID");
//						
//						manager.markAttendance(userID, eventID);
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching event attendance.\nMore Details: " + ex.getMessage());
//					}
//					break;
//			}
//		}
//		catch(Exception ex)
//		{
//			status = false;
//			message = ex.getMessage();
//			
//			client.sendEvent("failure", ex.getMessage());
//			
//		}
//	}
//	
//	private void dispatchOrganizationEvents(String organizationCommand)
//	{
//		try
//		{
//			
//			OrganizationManager manager = OrganizationManager.instance();
//			
//			switch(organizationCommand)
//			{
//				
//				case "create":
//					try
//					{
//						System.out.println(jsonMsg);
//						manager.createOrganization(jsonMsg);
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching organization creation.\nMore Details: " + ex.getMessage());
//					}
//					break;
//					
//				case "loadOne":
//					try
//					{	
//						int organizationID = jsonMsg.getInt("organizationID");
//						
//						client.sendEvent("organizationLoadOne", manager.loadOrganizationDetails(organizationID));
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching organization single load.\nMore Details: " + ex.getMessage());
//					}
//					break;
//					
//				case "loadAll":
//					try
//					{
//						client.sendEvent("organizationLoadAll", manager.getAllOrganizations());
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching loading of all organizations.\nMore Details: " + ex.getMessage());
//					}
//					break;
//					
//				case "loadByUser":
//					try
//					{
//						client.sendEvent("organizationLoadByUser", manager.getAllOrganizations(jsonMsg.getJSONObject("user").getInt("userID")));
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching loading all organizations by user.\nMore Details: " + ex.getMessage());
//					}
//				case "join":
//					try
//					{	
//						int organizationID = jsonMsg.getJSONObject("organization").getInt("organizationID");
//						
//						manager.joinOrganization(jsonMsg.getJSONObject("user").getInt("userID"), organizationID);
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching the request to join an organization.\nMore Details: " + ex.getMessage());
//					}
//					break;
//				case "grantRole":
//					try
//					{	
//						int organizationID = jsonMsg.getJSONObject("organization").getInt("organizationID");
//						
//						String roleName = jsonMsg.getJSONObject("role").getString("roleName");
//						
//						int userID = jsonMsg.getJSONObject("user").getInt("userID");
//						
//						JSONArray privs = jsonMsg.getJSONArray("privIDs");
//						
//						manager.grantRole(userID, organizationID, roleName, privs);
//					}
//					catch(Exception ex)
//					{
//							throw new Exception("An error occurred while dispatching event single load.\nMore Details: " + ex.getMessage());
//					}
//					break;
//			}
//		}
//		catch(Exception ex)
//		{
//			status = false;
//			message = ex.getMessage();
//			
//			client.sendEvent("Failure", ex.getMessage());
//		}
//	}
//	
//	
//	
//}