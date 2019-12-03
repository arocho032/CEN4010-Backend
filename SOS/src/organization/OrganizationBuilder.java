package organization;

// TODO: Auto-generated Javadoc
/**
 * A Builder which creates new Organization objects.  It is used to
 * decouple the process, including validations and checks, of creating 
 * an Organization from the actual Organization class itself. 
 */
public class OrganizationBuilder {

	/** The Constant PRIVACY_PUBLIC. */
	public static final String PRIVACY_PUBLIC = "PUBLIC";
	
	/** The Constant PRIVACY_PRIVATE. */
	public static final String PRIVACY_PRIVATE = "PRIVATE";
	
	/** The organization. */
	private Organization organization;
	
	/**
	 * Creates a new OrganizationBuilder to instantiate the new Event.  
	 */
	public OrganizationBuilder() 
	{
		organization = new Organization();	
	};

	/**
	 * Sets the name.
	 * @param name
	 * 		the name.
	 * @return
	 * 		the OrganizationBuilder
	 */
	public OrganizationBuilder setName(String name) {
		this.organization.name = name;
		return this;
	}
	
	/**
	 * Sets the privacy.
	 *
	 * @param privacy 		the privacy.
	 * @return 		the OrganizationBuilder
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public OrganizationBuilder setPrivacy(String privacy) throws IllegalArgumentException {
		if (!privacy.equals(PRIVACY_PUBLIC) && !privacy.equals(PRIVACY_PRIVATE))
			throw new IllegalArgumentException("Invalid Privacy Value: " + privacy);
		this.organization.privacy = privacy;
		return this;
	}
	
	/**
	 * Sets the description.
	 * @param description
	 * 		the description.
	 * @return
	 * 		the OrganizationBuilder
	 */
	public OrganizationBuilder setDescription(String description) {
		this.organization.description = description;
		return this;
	}
	
	/**
	 * Sets the requirements.
	 * @param requirements
	 * 		the requirements.
	 * @return
	 * 		the OrganizationBuilder
	 */
	public OrganizationBuilder setRequirements(String requirements) {
		this.organization.requirements = requirements;
		return this;
	}
	

	/**
	 * Builds the Organization.
	 * @return
	 * 		an Organization object, or throws an error.
	 * @throws IllegalArgumentException
	 * 		thrown if the organization being built is not complete. 
	 */
	public Organization build() throws IllegalArgumentException {
		if(this.isNotComplete())
			throw new IllegalArgumentException("Invalid build. Missing some components.");
		return this.organization;
	}
	
	/**
	 * Checks if is not complete.
	 *
	 * @return 		true if the organization is not complete
	 * 		false otherwise.
	 */
	public boolean isNotComplete() {
		return this.organization.name == null
				|| this.organization.description == null
				|| this.organization.privacy == null
				|| this.organization.requirements == null;
	}
	
}
