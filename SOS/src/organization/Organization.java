package organization;

/**
 * A run-time representation of an Organization persistent object. 
 * This class is used as an intermediary for creation, retrieval, and 
 * modification of Organization data within the Java code (and the JVM). 
 * It is encodable (or serializable) to a database format (e.g., SQL Entry)
 */
public class Organization {
	
	/**
	 * Constructs a new Organization class. Called through the OrganizationBuilder
	 * class. Attribute assignations are done through protected scope. 
	 */
	protected Organization() {}

}
