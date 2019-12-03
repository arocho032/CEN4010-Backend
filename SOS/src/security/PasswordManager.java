package security;

import user.User;

import java.security.*;

// TODO: Auto-generated Javadoc
/**
 * A Singleton which deals with password control actions. It implements most of the back-end
 * side of the password policy for SOS, including resolving passwords and checking the input
 * password against the database.
 */
public class PasswordManager {
	
	/**
 	* The constructor could be made private
 	* to prevent others from instantiating this
 	* class. But this would also make it
 	* impossible to create instances of
 	* PasswordManager subclasses.
	*/
	protected PasswordManager() {}

 	/**
  	 * A handle to the unique PasswordManager
  	* instance.
 	*/
 	static private PasswordManager _instance = null;
 	
	/**
	 * Instance.
	 *
	 * @return The unique instance of this
	 * class.
	 */
		 static public PasswordManager instance( ) {
		 if ( null == _instance) {
			 _instance = new PasswordManager( );
		 }
		 return _instance;
	 }
		
    /**
     * Hash password.
     *
     * @param username the username
     * @param password is a String to be validated
     * @return will return an encrypted version of the password as a String
     */
  	static public String HashPassword(String username, String password) {
  		return password;
  	}
 
	/**
	 * Validate password.
	 *
	 * @param password as a String to be validated
	 * @return is true if password successfully validates
	 */
	static public boolean ValidatePassword(String password) {return true;}
  	
  	/**
	   * Validate log in credentials.
	   *
	   * @param user the user
	   * @param pwd is the user's password for log in
	   * @return is the validation of the login credentials
	   */
  	static public boolean ValidateLogInCredentials(User user, String pwd) {
  		return user.getPassword().equals(HashPassword(user.getUserName(), pwd));
  	}

}