package user;

import java.util.Map;
import storage.DataStoreFacade;
import utils.JSONTranslator;

import org.json.*;

import event.EventBuilder;

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
	 * @param userID The ID of the user that we want 
	 * @return	a User object with the given attributes.
	 */
	public JSONObject LoadUser(int userID) throws Exception
	{
		try
		{
			DataStoreFacade ds = new DataStoreFacade();
			
			UserLoader loader = new UserLoader();
			
			return loader.LoadUser(ds.retrieveUserDetails(userID)).getJSON();
			
		}
		catch(Exception ex)
		{
			throw new Exception("An error occurred while attempting to retrieve user details.\nMore details: " + ex.getMessage());
		}
	}
	
	/**
	 * Changes the details of the user in the SOS system.
	 * @param userID The ID of the user that wants to change their information.
	 * @param json2 The JSON string with the user information and their changes.
	 * @throws Exception Throws an exception if an error occurs while attempting to change user information.
	 */
	public void ChangeUserDetails(int userID, JSONObject json2) throws Exception
	{
		try
		{
			JSONObject json = json2;
			 
			 NewUserBuilder builder = new NewUserBuilder();
			 
			 
			 String name = json.getJSONObject("user").getString("userName");
			 String userName = json.getJSONObject("user").getString("userUserName");
			 String password = json.getJSONObject("user").getString("userPassword");
			 String privacy = json.getJSONObject("user").getString("userPrivacy");
			 String email = json.getJSONObject("user").getString("userEmail");
			 
			 //TO DO: PASSWORD ENCRYPTION
			 
			 if ( !builder.attemptToCreateNewUser(name, userName, password, privacy, email) )
			 {
				 throw new Exception("There was an error assigning user fields.");
			 }
			 
			 DataStoreFacade ds = new DataStoreFacade();
			 
			 ds.updateUserInformation(userID, email, privacy);
		}
		catch(Exception ex)
		{
			throw new Exception("There was an issue in updating the user's information.\nMore details: " + ex.getMessage());
		}
	};
	
	/**
	 * Creates a new profile when the user registers to the SOS site.
	 * @param input A JSON string representing the user's information.
	 * @throws Exception Throws an exception if there was an issue creating the user's profile.
	 */
	public void CreateNewProfile(JSONObject input) throws Exception
	{
		try
		{
			JSONObject json = input;
			 
			 NewUserBuilder builder = new NewUserBuilder();
			 
			 String name = json.getJSONObject("user").getString("userName");
			 String userName = json.getJSONObject("user").getString("userUserName");
			 String password = json.getJSONObject("user").getString("userPassword");
			 String privacy = json.getJSONObject("user").getString("userPrivacy");
			 String email = json.getJSONObject("user").getString("userEmail");

			 //TO DO: PASSWORD ENCRYPTION
			 
			 if ( !builder.attemptToCreateNewUser(name, userName, password, privacy, email) )
			 {
				 throw new Exception("There was an error assigning user fields.");
			 }
			 
			 DataStoreFacade ds = new DataStoreFacade();
			 
			 ds.registerNewUser(password, email, name, userName);
			 
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error creating a new User profile.\nMore details: " + ex.getMessage());
		}
		
		
	}
	
}
