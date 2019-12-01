package user;

import java.sql.ResultSet;
import java.util.Map;
import storage.DataStoreFacade;
import utils.JSONTranslator;

import org.json.*;

import event.EventBuilder;
import organization.Organization;
import security.PasswordManager;

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

	
	DataStoreFacade ds;
	UserLoader ul;
	UserUpdater up;
	
	/**
 	* A protected or private constructor ensures
 	* that no other class has access to the Singleton.
	*/
	protected UserManager() {
		try {
			this.ds = new DataStoreFacade();
			this.ul = new UserLoader();
			this.up = new UserUpdater();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	 * @param payload The ID of the user that we want 
	 * @return	a User object with the given attributes.
	 */
	public JSONObject LoadUser(JSONObject payload)
	{
		
		JSONObject ret = new JSONObject();
		try {
			String username = payload.getJSONObject("user").getString("username");
			ResultSet user = ds.retrieveUserByUsername(username);
			if(user == null) {
				ret.put("error", "true");
				ret.put("type", "usernotfoundError");
			} else {
				if(user.next()) {
					User loadedUser = ul.LoadUser(user);
					ret.put("user", loadedUser.getJSON());					
				}
			}
		} catch (JSONException e) {
			try {
				ret.put("error", "true");
				ret.put("type", "usernameValueError");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				ret.put("error", "true");
				ret.put("type", "generalException");
				ret.put("payload", "An error occurred while attempting to find all events for the user.\nMore Details: " + e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return ret;
	
	}
	
	public JSONObject login(JSONObject payload) {
		JSONObject ret = new JSONObject();
		try {
			String username = payload.getJSONObject("user").getString("username");
			String password = payload.getJSONObject("user").getString("password");
			ResultSet user = this.ds.retrieveUserByUsername(username);
			if(user == null) {
				ret.put("error", "true");
				ret.put("type", "usernotfoundError");
			} else {
				if(user.next()) {					
					User loadedUser = ul.LoadUser(user);
					if (!PasswordManager.ValidateLogInCredentials(loadedUser, password)) {
						ret.put("error", "true");
						ret.put("type", "invalidcredentials");
					} else {
						ret.put("type", "doLogin");
						ret.put("user", loadedUser.getJSON());
					}
				}
			}
		} catch (JSONException e) {
			try {
				ret.put("error", "true");
				ret.put("type", "loginValueError");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				ret.put("error", "true");
				ret.put("type", "generalException");
				ret.put("payload", "An error occurred while attempting to find all events for the user.\nMore Details: " + e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return ret;
	}
	
	
	/**
	 * Changes the details of the user in the SOS system.
	 * @param userID The ID of the user that wants to change their information.
	 * @param json2 The JSON string with the user information and their changes.
	 * @throws Exception Throws an exception if an error occurs while attempting to change user information.
	 */
	public JSONObject ChangeUserDetails(JSONObject json)
	{
		
		JSONObject ret = new JSONObject();
		try {						
			ResultSet user = this.ds.retrieveUserDetails(json.getInt("user_id"));
			if(user == null) {
				ret.put("error", "true");
				ret.put("type", "usernotfoundError");
			} else {
				if(user.next()) {
					User loadedUser = ul.LoadUser(user);
					if(!PasswordManager.instance().ValidateLogInCredentials(loadedUser, json.getString("password")))
						return null;
					
					up.ChangeUser(loadedUser, up.makeUpdatesMap(json.getJSONObject("update"))); 
					ds.updateUserInformation(loadedUser);
					return ret;
				}
			}
		} catch (JSONException e) {
			try {
				ret.put("error", "true");
				ret.put("type", "usernameValueError");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				ret.put("error", "true");
				ret.put("type", "generalException");
				ret.put("payload", "There was an issue in updating the user's information.\nMore details: " + e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return ret;		
		
	};
	
	/**
	 * Creates a new profile when the user registers to the SOS site.
	 * @param input A JSON string representing the user's information.
	 * @throws Exception Throws an exception if there was an issue creating the user's profile.
	 */
	public JSONObject CreateNewProfile(JSONObject json)
	{
		JSONObject ret = new JSONObject();
		NewUserBuilder builder = new NewUserBuilder();
		
		try {
			try {
				builder.setUsername(json.getJSONObject("user").getString("userUserName"));
			} catch (IllegalArgumentException e) {
				ret.put("error", "true");
				ret.put("type", "nonuniqueUsername");
				return ret;
			}
			
			try {
				builder.setPassword(json.getJSONObject("user").getString("userPassword"));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				ret.put("error", "true");
				ret.put("type", "invalidPassword");
				return ret;
			}

			try {
				builder.setEmail(json.getJSONObject("user").getString("userEmail"));
			} catch (IllegalArgumentException e) {
				ret.put("error", "true");
				ret.put("type", "invalidEmail");
				return ret;
			}
			
			User user = builder
			.setName(json.getJSONObject("user").getString("userName"))
			.setPrivacy("PUBLIC")
			.build();
			
			this.ds.registerNewUser(user);
			ret.put("type", "validRegistration");
			ret.put("user", user.toString());
		
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
			 
		return ret;
	}
	
	
	public JSONObject getMembersOfOrganization(JSONObject payload) {
		JSONObject ret = new JSONObject();
		try {
			
			ResultSet set = null;
			if(payload.getJSONObject("organization").has("organization_id")) {
			
				set = this.ds.retrieveMembersOfOrganization(payload.getJSONObject("organization").getInt("organization_id"));
				int skip = payload.getJSONObject("organization").getInt("startIndex");
				int count = 20;
				
				JSONArray members = new JSONArray();
				while(set.next()) {
					User loadedUser = ul.LoadUser(set);
					if(loadedUser != null) {
						members.put(loadedUser.getJSON());
					} else break;
					
				}
				
				set.close();
				ret.put("data", members.toString());
				
			} else {
				ret.put("error", "true");
				ret.put("type", "noSearchParameter");
				return ret;
			}
			
		} catch (JSONException e) {
			try {
				ret.put("error", "true");
				ret.put("type", "orgidValueError");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				ret.put("error", "true");
				ret.put("type", "generalException");
				ret.put("payload", "An error occurred while attempting to load the details for the organization.\nMore information: " + e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return ret;
	}
	
	
}
