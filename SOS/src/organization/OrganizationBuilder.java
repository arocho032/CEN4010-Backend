package organization;

/**
 * A Builder which creates new Organization objects.  It is used to
 * decouple the process, including validations and checks, of creating 
 * an Organization from the actual Organization class itself. 
 */
public class OrganizationBuilder {

	public static final String PRIVACY_PUBLIC = "PUBLIC";
	public static final String PRIVACY_PRIVATE = "PRIVATE";
	private Organization organization;
	
	/**
	 * Creates a new OrganizationBuilder to instantiate the new Event.  
	 */
	public OrganizationBuilder() 
	{
		organization = new Organization();	
	};

	public OrganizationBuilder setName(String name) {
		this.organization.name = name;
		return this;
	}
	
	public OrganizationBuilder setPrivacy(String privacy) throws IllegalArgumentException {
		if (!privacy.equals(PRIVACY_PUBLIC) && !privacy.equals(PRIVACY_PRIVATE))
			throw new IllegalArgumentException("Invalid Privacy Value: " + privacy);
		this.organization.privacy = privacy;
		return this;
	}
	
	public OrganizationBuilder setDescription(String description) {
		this.organization.description = description;
		return this;
	}
	
	public OrganizationBuilder setRequirements(String requirements) {
		this.organization.requirements = requirements;
		return this;
	}
	
	public Organization build() throws IllegalArgumentException {
		if(this.isNotComplete())
			throw new IllegalArgumentException("Invalid build. Missing some components.");
		return this.organization;
	}
	
	
	public boolean isNotComplete() {
		return this.organization.name == null
				|| this.organization.description == null
				|| this.organization.privacy == null
				|| this.organization.requirements == null;
	}
	
}
