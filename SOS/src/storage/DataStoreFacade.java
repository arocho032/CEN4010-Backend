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
			throw new Exception("Failed to connect to database: " + ex.getMessage());
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
			
			throw new Exception("An error occured while trying to add a new role to the organization. More Details:" + ex.getMessage());
			
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
			throw new Exception("There was an error registering the new user. More Details: " + ex.getMessage());
		}
		
	}
	
	public void createNewEvent()
	{
		
	}
	
	
	
	
	
	
}
