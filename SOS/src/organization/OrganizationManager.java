package organization;

/**
 * A Singleton which manages all the Organization functions. This 
 * class receives dispatched actions from the SOS Dispatcher and 
 * completes that action using objects internal to its subsystem. 
 * It also is in charge of interacting with the SOS Data Store 
 * Façade directly. Part of the role of this class is to parse 
 * front-end format data (e.g., JSON-String description of new 
 * Organization) and calling the appropriate functions on other 
 * classes according to that data. Another job of this class is 
 * to manage Role creation and assignment, as well as mediate the 
 * modification of data in an Organization, and of Event hosting.
 */
public class OrganizationManager {

	/**
 	* A protected or private constructor ensures
 	* that no other class has access to the Singleton.
	*/
	protected OrganizationManager() {}

 	/**
  	 * A handle to the unique OrganizationManager
  	* instance.
 	*/
 	static private OrganizationManager _instance = null;
	
	/**
	 * @return The unique instance of this
	 * class.
	 */
	 static public OrganizationManager instance() {
		 if ( null == _instance) {
			 _instance = new OrganizationManager();
		 }
		 return _instance;
	 }
	 
	 /**
	  * Grants a number of privileges to a User for a given Organization.
	  * @param userId
	  * 	the unique id of the User 
	  * @param orgId
	  * 	the unique id of the Organization
	  * @param privIds
	  * 	the unique ids of the Privileges given to the User.
	  */
	 public void grantRole(int userId, int orgId, int[] privIds) {};
	
}
