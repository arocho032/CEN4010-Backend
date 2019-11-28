package security;

import user.User;
import java.security.*;

/**
 * A Singleton which deals with password control actions. It implements most of the back-end
 * side of the password policy for SOS, including resolving passwords and checking the input
 * password against the database.
 */
public class PasswordManager {

	private KeyPairGenerator kgen;
	private PrivateKey serverPiK;
	private PublicKey serverPuK;
	
	private static int keyLenght = 32;
	
	/**
 	* The constructor could be made private
 	* to prevent others from instantiating this
 	* class. But this would also make it
 	* impossible to create instances of
 	* PasswordManager subclasses.
	*/
	protected PasswordManager() {
		try {
			this.kgen = KeyPairGenerator.getInstance("RSA");
			this.kgen.initialize(keyLenght);
			KeyPair p = this.kgen.generateKeyPair();
			this.serverPiK = p.getPrivate();
			this.serverPuK = p.getPublic();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

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
    static public boolean ValidatePassword(User user, String password) {
    	return user.equals(password);
    }
	
    /**
	* @param password is a String to be validated
	* @return will return an encrypted version of the password as a String
	*/
  	static public String EncryptPassword(String password) {return null;}
	
  	/**
	* @param username is the user name for log in
	* @param pwd is the user's password for log in
	* @return is the validation of the login credentials
	*/
  	static public boolean ValidateLogInCredentials(String username, String pwd) {return false;}

}