package user;

/**
 * A class which creates a User object from a database-format User 
 * object (e.g., a SQL Table entry for User). This class decouples 
 * the parsing from the database to the system logic from the 
 * UserManager class and can be extended to include internal 
 * checks for data integrity purposes.
 */
public class UserLoader {
	
	/**
	 * Creates a User from a database-format entry.
	 * @param sqlEntry
	 * 		a sql entry for the given organization.
	 * @return	a User object with the given attributes.
	 */
	public static User LoadOrganization(String sqlEntry) {return null;}
	
}
