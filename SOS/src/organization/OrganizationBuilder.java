package organization;

/**
 * A Builder which creates new Organization objects.  It is used to
 * decouple the process, including validations and checks, of creating 
 * an Organization from the actual Organization class itself. 
 */
public class OrganizationBuilder {

	Organization organization;
	
	/**
	 * Creates a new OrganizationBuilder to instantiate the new Event.  
	 */
	public OrganizationBuilder() 
	{
		
		organization = null;
		
	};

	public boolean attemptToCreateAnOrganization(String name, String privacy, String description, String requirements)
	{
		boolean status = true;
		
		if (!privacy.equals("PUBLIC") && !privacy.equals("PRIVATE"))
		{
			return false;
		}
		
		organization = new Organization(name, privacy, description, requirements);
		
		return status;
	}
	
	

}
