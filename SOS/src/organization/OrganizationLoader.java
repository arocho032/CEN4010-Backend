package organization;

/**
 * A class which creates an Organization object from an Organization 
 * database object. This class decouples the parsing from the database 
 * to the system logic from the OrganizationManager class and can be 
 * extended to include internal checks for data integrity purposes.
 */
public class OrganizationLoader {

	/**
	 * Creates a Organization from a database-format entry.
	 * @param sqlEntry
	 * 		a sql entry for the given organization.
	 * @return	a Organization object with the given attributes.
	 */
	public static Organization LoadOrganization(String sqlEntry) {return null;}

}
