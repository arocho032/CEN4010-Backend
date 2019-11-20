package user;

import java.util.Map;

/**
 * A Singleton class which managers all the User functions. This class
 * receives dispatched actions from the SOS Dispatcher and completes 
 * that action using objects internal to its subsystem. It also is in 
 * charge of interacting with the SOS Data Store Façade directly. Part 
 * of the role of this class is to parse front-end format user data 
 * (e.g., JSON-String defining a new User) and calling the appropriate 
 * functions on the other classes according to that data. It also is in 
 * charge of encoding a User object into database format objects (e.g., 
 * SQL Table entry for User).
 */
public class UserManager {

	/**
 	* A protected or private constructor ensures
 	* that no other class has access to the Singleton.
	*/
	protected UserManager() {}
	
	/**
	 * A handle to the unique UserMangaer instance
	 */
	static private UserManager _instance = null;
	
	/**
	 * Gives the instance of the UserManager, or creates one
	 * if none exists.
	 * @return	the unique instance of this class.
	 */
	static public UserManager instance() {
		if (null == _instance) {
			_instance = new UserManager();
		}
		return _instance;
	}

	/**
	 * Creates a User from a database-format entry. Done by
	 * calling the UserLoader class.
	 * @param sqlEntry
	 * 		a sql entry for the given user.
	 * @return	a User object with the given attributes.
	 */
	public User LoadUser(String sqlEntry) {return null;}
	
	/**
	 * Updates a User object with the given changes. Done through
	 * the UserUpdater class.
	 * @param user
	 * 		the User that will be updated.
	 * @param change
	 * 		a Map where the key is the variable
	 * 		name and the value is the update.
	 */
	public void ChangeUserDetails(User user, Map<String, String> change) {};
	
	/**
	 * Creates a new User from a json User description. Done by
	 * calling the NewUserBuilder class.
	 * @param jsonString
	 * 		the JSON object describing the new User.
	 * @return
	 * 		a User object with the given attributes. 
	 */
	public User CreateNewProfile(String jsonString) {return null;};
	
}
