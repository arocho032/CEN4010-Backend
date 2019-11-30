package user;

import java.sql.*;

/**
 * A class which creates a User object from a database-format User 
 * object (e.g., a SQL Table entry for User). This class decouples 
 * the parsing from the database to the system logic from the 
 * UserManager class and can be extended to include internal 
 * checks for data integrity purposes.
 */
public class UserLoader {
	
	private User user;
	
	public UserLoader()
	{
		user = null;
	}
	
	/**
	 * Creates a User from a database-format entry.
	 * @param results The set of details found in the storage of the SOS.
	 * @return	a User object with the given attributes.
	 */
	public User LoadUser(ResultSet results) throws Exception
	{
		try
		{
			user = new User(results);
			return this.user;
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error loading the user.\nMore details: " + ex.getMessage());
		}
		
	}
	
}
