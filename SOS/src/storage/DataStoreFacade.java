package storage;

import java.sql.*;
import java.text.*;
import java.util.Arrays;

import event.Event;
import organization.Organization;
import user.User;

// TODO: Auto-generated Javadoc
///**
// * A Fa�ade object that acts as the interface for the SOS 
// * Storage subsystem. The other subsystems interact with 
// * the database through a preset set of actions defined in 
// * the SOS Data Store Fa�ade. A fa�ade is a structural design 
// * pattern which is used to provide a unified interface to a 
// * set of objects within a subsystem. Even though our data store 
// * is a single object, a fa�ade is still warranted because the 
// * SOS Data Store is implemented using a database component (SQL) 
// * and through the SOS Data Store Fa�ade we can decouple the 
// * details of the database component (such as the SQL language) 
// * from the rest of the system.
/**
 * The Class DataStoreFacade.
 */
// */
public class DataStoreFacade {
	
	/** The connect. */
	private Connection connect = null;
	
	/** The password. */
	//final private String password = "SOSDBCEN4010";
	final private String password = "root";
	
	/**
	 * Attempts to connect to the Database.
	 * @throws Exception Throws an exception if the database connection fails.
	 */
	public DataStoreFacade() throws Exception
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Change this to a const.
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
	 * @param privileges The list of privileges granted to the user for their particular role.
	 * @throws Exception An exception is thrown when the new role fails to be stored in the SOS database.
	 */
	public void addNewRoleToOrganization(String roleName, int organizationID, int userID, boolean[] privileges ) throws Exception
	{
		try {
			
			final String query = "CALL sos_storage.add_new_role(?,?,?,?,?,?,?,?)";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setString(1, roleName);
			procedure.setInt(2, organizationID);
			procedure.setInt(3, userID);

			for( int index = 0; index < privileges.length; index++ )
			{
				procedure.setBoolean(index + 4, privileges[index]);
			}
			
			procedure.execute();

		} catch (Exception ex) {
			
			throw new Exception("An error occured while trying to add a new role to the organization.\n More Details:" + ex.getMessage());
			
		}
		
	}
	
	/**
	 * Retrieves the details from a given user stored in the DB.
	 * @param userID The ID for the user that the details are requested for.
	 * @return A set of details found in the storage.
	 * @throws Exception Throws an exception if their is an issue connecting to the database.
	 */
	public ResultSet retrieveUserDetails(int userID) throws Exception
	{
		try
		{
			final String query = "CALL `sos_storage`.`retrieve_user_details`(?);";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setInt(1, userID);
			
			procedure.execute();
			
			return procedure.getResultSet();

		}
		catch(Exception ex)
		{
			throw new Exception("Failed to retrieve user details.\nMore details: " + ex.getMessage());
		}
	}
	
	/**
	 * Allows the user to join an organization.
	 * @param userID The ID of the user that wants to join an organization.
	 * @param organizationID The ID of the organization that the user wants to join.
	 * @throws Exception Throws an exception if the user tries to join an organization they already belong to or does not exist.
	 */
	public void joinOrganization(int userID, int organizationID) throws Exception
	{
		try
		{
			final String query = "CALL `sos_storage`.`join_organization`(?,?);";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setInt(1, userID);
			procedure.setInt(2, organizationID);
			
			procedure.execute();
			
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error while trying to store the users joining of the organization.\nMore details: " + ex.getMessage());
		}
		
		
	}
	
	/**
	 * Retrieves the information of the organization that is stored in the database.
	 * @param organizationID The ID of the organization that the details were requested for.
	 * @return The set of details found in the database.
	 * @throws Exception Throws an exception if there is a problem retrieving the details for the specified information.
	 */
	public ResultSet retrieveOrganizationDetails(int organizationID) throws Exception
	{
		try
		{
			final String query ="CALL `sos_storage`.`get_organization_details`(?);";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setInt(1, organizationID);
			
			procedure.execute();
			
			return procedure.getResultSet();
			
		}
		catch(Exception ex)
		{
			throw new Exception("An error occurred while executing the stored procedure for retrieving event details.\nMore details: " + ex.getMessage());
		}
	}
	
	/**
	 * Retrieves all of the public organizations stored in the storage.
	 * @return The set of all the public organizations located in the storage.
	 * @throws Exception Throws an exception if their was an issue retrieving all of the organizations from the storage.
	 */
	public ResultSet retrievePublicOrganizations() throws Exception
	{
		try
		{
			final String query = "CALL `sos_storage`.`get_all_organizations`();";
			CallableStatement procedure = connect.prepareCall(query);		
			procedure.execute();
			return procedure.getResultSet();
					
		}
		catch(Exception ex)
		{
			throw new Exception("An error occurred while attempting to retrieve the public organizations from the storage.\nMore details: " + ex.getMessage());
		}
	}
	
	
	/**
	 * Retrieves all the organizations which the user belongs to,.
	 *
	 * @param userID The ID of the user that we want all the organizations for.
	 * @return A set of organizations which the user belongs to within the SOS.
	 * @throws Exception Throws an exception if their is an error with the connectivity to the storage of the system.
	 */
	public ResultSet retrieveOrganizationsForUser(int userID) throws Exception
	{
		try
		{
			final String query = "CALL `sos_storage`.`get_organizations_for_user`(?);";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setInt(1, userID);
			
			procedure.execute();
			
			return procedure.getResultSet();
		}
		catch(Exception ex)
		{
			throw new Exception("An error occurred while attempting to retrieve organizations for the user.\nMore details: " + ex.getMessage());
		}
		
	}
	
	/**
	 * Registers a new user for the SOS system.
	 *
	 * @param user the user
	 */
	public void registerNewUser(User user)
	{
		
		final String query = "CALL sos_storage.user_register(?,?,?,?,?)";
		try {
			CallableStatement procedure = connect.prepareCall(query);
			procedure.setString(1, user.getPassword());
			procedure.setString(2, user.getEmail());
			procedure.setString(3, user.getName());
			procedure.setString(4, user.getUserName());
			procedure.setString(5, user.getPrivacy());
			procedure.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	
	/**
	 * Creates a new event in the SOS system.
	 *
	 * @param event the event
	 * @throws Exception Throws an exception if the parameters are not in the expected format and if the organization hosting the event no longer exists.
	 */
	public void createNewEvent(Event event) throws Exception
	{
		try
		{
			final String query = "CALL sos_storage.event_create(?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setString(1, event.getName());
			procedure.setDouble(2, event.getLatCoordinate());
			procedure.setDouble(3, event.getLongCoordinate());
			procedure.setString(4, event.getDescription());
			procedure.setBoolean(5, event.isVisibility());
			
			DateFormat transform = new SimpleDateFormat("hh:mm:ss");
			Time sqlTime = new Time ((transform.parse(event.getTime())).getTime());
			
			transform = new SimpleDateFormat("yy/MM/dd");
			Date sqlDate = new Date (transform.parse(event.getDate()).getTime());
	
			procedure.setTime(6, sqlTime );
			procedure.setDate(7, sqlDate);
			
			procedure.setInt(8, event.getEventType());
			procedure.setInt(9, event.getHostedBy());
			
			procedure.execute();
					
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error creating the new event.\nMore Details: " + ex.getMessage());
		}
		
	}
	
	
	
	/**
	 * Returns a list of JSON objects .
	 *
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
	 * Returns the Events of the given Organization.
	 *
	 * @param orgID 		the id of the organization.
	 * @return 		the ResultSet containing the event entries. 
	 * @throws Exception the exception
	 */
	public ResultSet getEventsByOrganization(int orgID) throws Exception
	{
		try
		{
			final String query = "CALL `sos_storage`.`get_events_from_organization`(?);";
			
			CallableStatement procedure = connect.prepareCall(query);
			procedure.setInt(1, orgID);			
			procedure.execute();
			return procedure.getResultSet();
			
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error while retrieving the events.\nMore details:" + ex.getMessage());
		}
	}
	
	/**
	 * Returns the Events attended by the given user.
	 *
	 * @param userID 		the user id
	 * @return 		a ResultSet containing the target events
	 * @throws Exception the exception
	 */
	public ResultSet getEventsByUser(int userID) throws Exception
	{
		try
		{
			final String query = "CALL `sos_storage`.`get_events_from_user`(?);";
			
			CallableStatement procedure = connect.prepareCall(query);
			procedure.setInt(1, userID);			
			procedure.execute();
			return procedure.getResultSet();
			
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error while retrieving the events.\nMore details:" + ex.getMessage());
		}
	}
	
	
	/**
	 * Retrieves an user entry from its unique username. User for login in moslty.
	 *
	 * @param username 		the username of the user
	 * @return 		the result set containing the user entry.
	 * @throws Exception the exception
	 */
	public ResultSet retrieveUserByUsername(String username) throws Exception {
		
		try
		{
			final String query = "CALL `sos_storage`.`get_user_by_username`(?);";
			CallableStatement procedure = connect.prepareCall(query);
			procedure.setString(1,  username);
			procedure.execute();
			ResultSet set = procedure.getResultSet();
			return set;
			
		} catch (Exception ex) {
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
	 *
	 * @param org the org
	 * @param userID The user ID of the user creating the organization.
	 * @throws Exception Throws an exception if there is an issue storing the organization into the database.
	 */
	public void createNewOrganization(Organization org, int userID) throws Exception
	{
		try
		{
			final String query = "CALL sos_storage.organization_create(?,?,?,?,?)";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setString(1, org.getName());
			procedure.setString(2, org.getDescription());
			procedure.setString(3, org.getPrivacy());
			procedure.setString(4, org.getRequirements());
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
	public void saveUserAttendance(long userID, long eventID) throws Exception
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
	 * Updates the information of the user to the given information.
	 *
	 * @param user the user
	 * @throws Exception If the email is already present in the database under a different user then an exception is thrown.
	 */
	public void updateUserInformation(User user) throws Exception
	{
		try
		{
			int userID = user.getUser_id();
			if(userID == -1) {
				ResultSet backenduser = this.retrieveUserByUsername(user.getUserName());
				userID = (int) backenduser.getObject("user_id");
			}
			
			final String query = "Call `sos_storage`.`update_user_information`(?,?,?)";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setInt(1, userID);
			procedure.setString(2, user.getEmail());
			procedure.setString(3, user.getPrivacy());
			
			procedure.execute();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
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

	/**
	 * Retrives all the users which are members of a given organization.
	 *
	 * @param organization_id 			the id of the organization.
	 * @return 			the ResultSet containing the user entries.
	 * @throws Exception the exception
	 */
	public ResultSet retrieveMembersOfOrganization(int organization_id) throws Exception {
		try
		{
			String query = "CALL `sos_storage`.`get_members_of_organization`(?);";
			CallableStatement procedure = connect.prepareCall(query);
			procedure.setInt(1, organization_id);
			procedure.execute();
			return procedure.getResultSet();
			
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error while trying to retrieve the details for the event.\nMore details: " + ex.getMessage());
		}
	}
	
	
	

	
}
