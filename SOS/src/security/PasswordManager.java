package security;

import user.User;

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
	* @param password as a String to be validated
	* @return is true if password successfully validates
	*/
    static public boolean ValidatePassword(String password) {return true;}
	
    /**
	* @param password is a String to be validated
	* @return will return an encrypted version of the password as a String
	*/
  	static public String HashPassword(String username, String password) {return null;}
	
  	/**
	* @param username is the user name for log in
	* @param pwd is the user's password for log in
	* @return is the validation of the login credentials
	*/
  	static public boolean ValidateLogInCredentials(User user, String pwd) {
  		return false;
  		
  	}

}