package security;

/**
 * A Singleton dealing with access control actions. It implements 
 * most of the back-end side of the access policy for SOS and host
 * the relevant Enumerations for access permissions and other privileges.
 * It also must be called to do checks on the relevant actions, such as 
 * creating events, deleting profiles, etc.  
 */
public class AccessManager {
	
	/**
 	* A protected or private constructor ensures
 	* that no other class has access to the Singleton.
	*/
	protected AccessManager() {}

 	/**
  	 * A handle to the unique AccessManager
  	* instance.
 	*/
 	static private AccessManager _instance = null;
	
	/**
	 * @return The unique instance of this
	 * class.
	 */
	 static public AccessManager instance() {
		 if ( null == _instance) {
			 _instance = new AccessManager();
		 }
		 return _instance;
	 }

	/**
	* @return The result of privilege check for the current user
	* class.
	*/
    public boolean CheckPrivileges() {return false;}

}