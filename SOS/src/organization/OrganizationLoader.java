package organization;

import java.sql.*;

// TODO: Auto-generated Javadoc
/**
 * A class which creates an Organization object from an Organization 
 * database object. This class decouples the parsing from the database 
 * to the system logic from the OrganizationManager class and can be 
 * extended to include internal checks for data integrity purposes.
 */
public class OrganizationLoader {
	
	/** The organization. */
	private Organization organization;

	/**
	 * Creates a Organization from a database-format entry.
	 *
	 * @param set the set
	 * @return a Organization object with the given attributes.
	 * @throws Exception the exception
	 */
	public Organization LoadOrganization(ResultSet set) throws Exception
	{
		try
		{
			if(set.next())
				organization = new Organization(set);
			return organization;
		}
		catch(Exception ex)
		{
			throw new Exception("An error occurred while attempting to load the organization.\nMore Details: " + ex.getMessage());
		}
		
	}

}
