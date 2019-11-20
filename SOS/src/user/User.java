package user;

/**
 * A run-time representation of a User persistent object. This class 
 * is used as an intermediary for creation, retrieval, and modification 
 * of User data within the Java code (and the JVM). It is encodable 
 * (or serializable) to a database format (e.g., SQL Entry). 
 */
public class User {

	/**
	 *  Constructs a new User class. Called through the UserBuilder class. 
	 *  Attribute assignations are done through protected scope. 
	 */
	protected User() {};
	
}
