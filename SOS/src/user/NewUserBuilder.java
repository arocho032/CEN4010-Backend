package user;

/**
 * A Builder which creates new User objects. It is used to 
 * decouple the parts of the process of creating a new User 
 * from the actual User class, which is intended to only be a 
 * data wrapper class which can be easily parsed into the 
 * database format. Namely, this class implements the checks 
 * and validations necessary to create a valid User and will 
 * reject invalid ones. As part of this validation, it must 
 * interact with the SOS Security System classes that 
 * implement the password and access policies.
 */
public class NewUserBuilder {

	User user;
	
	/**
	 * Creates a new NewUserBuilder to instantiate the new User.  
	 */
	public NewUserBuilder() 
	{
		user = null;
	}
	
	public boolean attemptToCreateNewUser(String setName, String setUserName, String setPassword, String setPrivacy, String setEmail) 
	{
		try
		{
			user = new User(setName, setUserName, setPassword, setPrivacy, setEmail);
		}
		catch(Exception ex)
		{
			return false;
		}
		
		return true;
	}
}
