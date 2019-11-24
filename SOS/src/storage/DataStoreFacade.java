package storage;

import java.sql.*;
import java.text.*;
import org.json.*;

///**
// * A Façade object that acts as the interface for the SOS 
// * Storage subsystem. The other subsystems interact with 
// * the database through a preset set of actions defined in 
// * the SOS Data Store Façade. A façade is a structural design 
// * pattern which is used to provide a unified interface to a 
// * set of objects within a subsystem. Even though our data store 
// * is a single object, a façade is still warranted because the 
// * SOS Data Store is implemented using a database component (SQL) 
// * and through the SOS Data Store Façade we can decouple the 
// * details of the database component (such as the SQL language) 
// * from the rest of the system.
// */
public class DataStoreFacade {
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	final private String host = "localhost:3306";
	final private String user = "root";
	final private String password = "SOSDBCEN4010!";
	
	
	/**
	 * Attempts to connect to the Database.
	 * @throws Exception Throws an exception if the database connection fails.
	 */
	public DataStoreFacade() throws Exception
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/?user=root&password=" + password);
		}
		catch(Exception ex)
		{
			throw new Exception("Failed to connect to database.\nMore details: " + ex.getMessage());
		}
	}
	
	/**
	 * Terminates connection to the database.
	 * @throws Exception Throws an exception if database connection cannot be closed.
	 */
	public void terminateConnection() throws Exception
	{
		try
		{
			connect.close();
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error disconnecting from the database.\nMore details:" + ex.getMessage());
		}
	}
	
	/**
	 * Adds a new role to the organization provided and assigns the role to the user provided.
	 * @param roleName The name that is given to the role.
	 * @param organizationID The organization ID of the organization that the role belongs to.
	 * @param userID The user ID of the user that will own the role.
	 * @throws Exception An exception is thrown when the new role fails to be stored in the SOS database.
	 */
	public void addNewRoleToOrganization(String roleName, int organizationID, int userID) throws Exception
	{
		try {
			
			final String query = "CALL sos_storage.add_new_role(?,?,?)";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setString(1, roleName);
			procedure.setInt(2, organizationID);
			procedure.setInt(3, userID);
			
			procedure.execute();

		} catch (Exception ex) {
			
			throw new Exception("An error occured while trying to add a new role to the organization.\n More Details:" + ex.getMessage());
			
		}
		
	}
	
	/**
	 * Registers a new user for the SOS system.
	 * @param encryptedPassword An encrypted string that represents the user's password.
	 * @param email The email the user is registering under.
	 * @param name The user's name.
	 * @param userName The user's name.
	 * @throws Exception An exception is thrown when the new user information fails to be stored in the SOS database.
	 */
	public void registerNewUser(String encryptedPassword, String email, String name, String userName ) throws Exception
	{
		
		try
		{
			final String query = "CALL sos_storage.user_register(?,?,?,?)";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setString(1, encryptedPassword);
			procedure.setString(2, email);
			procedure.setString(3, name);
			procedure.setString(4, userName);
			
			procedure.execute();
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error registering the new user.\nMore Details: " + ex.getMessage());
		}
		
	}
	
	
	/**
	 * Creates a new event in the SOS system.
	 * @param name The name of the event.
	 * @param longCoordinate The longitude of the location where the event will take place.
	 * @param latCoordinate The latitude of the location where the event will take place.
	 * @param description The description of the event.
	 * @param visibility The visibility of the event: True is visible, False is not visible
	 * @param time The time when the event will take place.
	 * @param date The date when the event will take place.
	 * @param eventType The type of event being hosted.
	 * @param hostedBy The organization that is hosting the event.
	 * @throws Exception Throws an exception if the parameters are not in the expected format and if the organization hosting the event no longer exists.
	 */
	public void createNewEvent(String name, double longCoordinate, double latCoordinate, String description, boolean visibility, 
							   String time, String date, int eventType, int hostedBy ) throws Exception
	{
		try
		{
			final String query = "CALL sos_storage.event_create(?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setString(1, name);
			procedure.setDouble(2, longCoordinate);
			procedure.setDouble(3, latCoordinate);
			procedure.setString(4, description);
			procedure.setBoolean(5, visibility);
			
			DateFormat transform = new SimpleDateFormat("hh:mm:ss a");
			
			Time sqlTime = new Time ((transform.parse(time)).getTime());
			
			transform = new SimpleDateFormat("MM/dd/yyyy");
			
			Date sqlDate = new Date (transform.parse(date).getTime());
	
			procedure.setTime(6,sqlTime );
			procedure.setDate(7, sqlDate);
			procedure.setInt(8, eventType);
			procedure.setInt(9, hostedBy);
			
			
			procedure.execute();
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error creating the new event.\nMore Details: " + ex.getMessage());
		}
		
	}
	
	
	
	/**
	 * Returns a list of JSON objects 
	 * @param lat_coordinate The latitude of the location of interest.
	 * @param long_coordinate The longitude of the location of interest.
	 * @return The results from the database of the closest events.
	 * @throws Exception An exception is thrown if their is a problem retrieving nearby events.
	 */
	public ResultSet filterEventsByLocation(double lat_coordinate, double long_coordinate) throws Exception
	{
		try
		{
			final String query = "CALL `sos_storage`.`filter_events_by_location`(?, ?)";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setDouble(1, lat_coordinate);
			procedure.setDouble(2, long_coordinate);
			
			procedure.execute();
			
			ResultSet results = procedure.getResultSet();
			
			return results;

		}
		catch(Exception ex)
		{
			throw new Exception("There was an error while attempting to filter events by location.\nMore details: " + ex.getMessage());
		}
		
	}
	
	/**
	 * Gets all the events in the database that are not cancelled.
	 * @return A result set with the events found in the database.
	 * @throws Exception An exception is thrown when the procedure fails to retrieve the results from the database.
	 */
	public ResultSet getEvents() throws Exception
	{
		try
		{
			final String query = "CALL `sos_storage`.`get_events`();";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.execute();
			
			return procedure.getResultSet();
			
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error while retrieving the events.\nMore details:" + ex.getMessage());
		}
	}
	
	/**
	 * Requests to cancel the event in the database.
	 * @param eventID The ID of the event that needs to be cancelled.
	 * @throws Exception Throws an exception if the event could not be cancelled.
	 */
	public void cancelEvent(int eventID) throws Exception
	{
		try
		{
			String query = "CALL `sos_storage`.`cancel_event`(?);";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setInt(1, eventID);
			
			procedure.execute();
			
			
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error while trying to cancel the event.\nMore details: " + ex.getMessage());
		}
	}
	
	/**
	 * Retrieves all the details for a certain event.
	 * @param eventID The ID of the event that we want the details for.
	 * @return The details of the events in the form of a result set.
	 * @throws Exception Throws an exception if the event details were not found.
	 */
	public ResultSet retrieveEventDetails(int eventID) throws Exception
	{
		try
		{
			String query = "CALL `sos_storage`.`get_event_details`(?);";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setInt(1, eventID);
			
			procedure.execute();
			
			return procedure.getResultSet();
			
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error while trying to retrieve the details for the event.\nMore details: " + ex.getMessage());
		}
		
	}
	
	/**
	 * Creates a new organization on the SOS system.
	 * @param name The name of the organization.
	 * @param description The description of the organization.
	 * @param privacy The privacy of the organization (PUBLIC or PRIVATE).
	 * @param requirements The requirements for joining the organization.
	 * @param userID The user ID of the user creating the organization.
	 * @throws Exception Throws an exception if there is an issue storing the organization into the database.
	 */
	public void createNewOrganization(String name, String description, String privacy, String requirements, int userID ) throws Exception
	{
		try
		{
			final String query = "CALL sos_storage.organization_create(?,?,?,?,?)";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setString(1, name);
			procedure.setString(2, description);
			procedure.setString(3, privacy);
			procedure.setString(4, requirements);
			procedure.setInt(5, userID);
			
			procedure.execute();
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error creating the new organization\nMore Details: " + ex.getMessage());
		}
		
	}
	
	/**
	 * Saves the attendance of the user to a particular event in the SOS storage.
	 * @param userID The ID of the user that is attending the event.
	 * @param eventID The ID of the event that the user is attending.
	 * @throws Exception Throws an exception if there is an issue in storing the attendance of the user in the database.
	 */
	public void saveUserAttendance(long userID,long eventID) throws Exception
	{
		try
		{
			final String query = "CALL sos_storage.store_attendance(?,?)";
			
			CallableStatement procedure = connect.prepareCall(query);
	
			procedure.setLong(1, userID);
			procedure.setLong(2, eventID);
			
			procedure.execute();
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error saving the attendance.\nMore Details: " + ex.getMessage());
		}
		
	}
	
	/**
	 * Updates the information of the user to the given information
	 * @param userID The ID of the user that will be updated in the database.
	 * @param email The modified email assigned to the user in the database.
	 * @param privacy The modified privacy assigned to the user in the database.
	 * @throws Exception If the email is already present in the database under a different user then an exception is thrown.
	 */
	public void updateUserInformation(int userID, String email, String privacy) throws Exception
	{
		try
		{
			final String query = "Call sos_storage.update_user_information(?,?,?)";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setInt(1, userID);
			procedure.setString(2, email);
			procedure.setString(3, privacy);
			
			procedure.execute();
			
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error updating the user's information in the database.\nMore Details: " + ex.getMessage());
		}
		
	}
	
	/**
	 * Verifies the login of the user.
	 * @param email The email that the user provides when logging in.
	 * @param password The encrypted password the user provides when logging in.
	 * @return A boolean verifying if the user has correct credentials to log into SOS.
	 * @throws Exception Throws an exception if the credentials are in an invalid format.
	 */
	public boolean verifyUserLogin(String email, String password ) throws Exception
	{
		boolean result = false;
		try
		{
			final String query = "CALL sos_storage.user_verify_login(?,?,?)";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setString(1, email);
			procedure.setString(2, password);
			
			Boolean bool = new Boolean(result);
			
			procedure.setBoolean(3, bool);
			
			procedure.execute();
			
			result = procedure.getBoolean(3);
			
		}
		catch(Exception ex)
		{
			throw new Exception("Invalid credentials.");
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
}
