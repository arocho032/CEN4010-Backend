package user;

import java.util.Map;

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
	 * @param user
	 * 		the User that will be updated.
	 * @param change
	 * 		a Map where the key is the variable
	 * 		name and the value is the update.
	 */
	public void ChangeUser(User user, Map<String, String> update) {}

}
