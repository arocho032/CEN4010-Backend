package user;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * A class which deals with User modifications. User modifications 
 * are done on the system logic-level User object first and are only 
 * finalized once they are stored to the database. The UserUpdater 
 * decouples these modifications from the UserManager class and from 
 * the User class itself and implements checks and validations in 
 * the same way that NewUserBuilder does. It also ensures that 
 * every modification to the User class is saved to the SOS Data Store.
 */
public class UserUpdater {
	
	/**
	 * Updates a User object with the given changes.
	 *
	 * @param user 		the User that will be updated.
	 * @param update the update
	 */
	public void ChangeUser(User user, Map<String, String> update) {
		if(update.containsKey("email"))
			user.email = update.get("email");
		if(update.containsKey("privacy"))
			user.privacy = update.get("privacy");		
	}

	/**
	 * Creates an update-map from a json payload.
	 *
	 * @param json the json
	 * @return the map
	 */
	public Map<String, String> makeUpdatesMap(JSONObject json) {
	
		Map<String, String> update = new HashMap<>();
	    try {
			Iterator<String> keys = json.keys();
			while(keys.hasNext()) {
			    String key = keys.next();
				if (json.get(key) instanceof String) {
					update.put(key, (String) json.get(key));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return update;
	}

}
