package storage;

import java.sql.*;

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
	 * @throws Exception
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
	 * NOT FINISHED 
	 * @param name
	 * @param location
	 * @param description
	 * @param visibility
	 * @param time
	 * @param date
	 * @param eventType
	 * @param hostedBy
	 * @throws Exception
	 */
	public void createNewEvent(String name, String location, String description, boolean visibility, Time time, Date date, int eventType, int hostedBy ) throws Exception
	{
		try
		{
			final String query = "CALL sos_storage.event_create(?,?,?,?,?,?,?,?)";
			
			CallableStatement procedure = connect.prepareCall(query);
			
			procedure.setString(1, name);
			
			
			procedure.execute();
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error registering the new user.\nMore Details: " + ex.getMessage());
		}
	}
	
	/**
	 * NOT FINISHED
	 * @param location
	 */
	public void filterEventsByLocation(String location)
	{
		
	}
	
	/**
	 * NOT FINISHED
	 */
	public void getEvents()
	{
		
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
		finally
		{
			connect.close();
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
		finally
		{
			connect.close();
		}
	}
	
	
	
	
	
	
	
}
