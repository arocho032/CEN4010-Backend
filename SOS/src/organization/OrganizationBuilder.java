package organization;

/**
 * A Builder which creates new Organization objects.  It is used to
 * decouple the process, including validations and checks, of creating 
 * an Organization from the actual Organization class itself. 
 */
public class OrganizationBuilder {

	/**
	 * Creates a new OrganizationBuilder to instantiate the new Event.  
	 */
	public OrganizationBuilder() {};

	/**
	 * Checks the current Organization, returning True if it is valid so far
	 * and False otherwise.
	 * @return
	 * 		True, if the Organization is valid so far.
	 * 		False if otherwise. 
	 */
	protected boolean ValidateOrganizationDetails() {return false;}

    /**
     * Adds a name value to the current Organization.
     * @param name
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public OrganizationBuilder setName(String name) {return this;}
    
    /**
     * Adds a description value to the current Organization.
     * @param description
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public OrganizationBuilder setDescription(String description) {return this;}
    
    /**
     * Adds a privacy value to the current Organization.
     * @param privacy
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public OrganizationBuilder setPrivacy(String privacy) {return this;}  

    /**
     * Adds a requirements value to the current Organization.
     * @param requirements
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public OrganizationBuilder setRequirements(String requirements) {return this;}
    
	/**
	 * Finalizes the Organization creation and returns it.
	 * @return
	 * 		returns the final Organization.
	 */
	public Organization CreateNewOrganization() {return null;}

}
