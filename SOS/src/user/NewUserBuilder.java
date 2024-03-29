package user;

import java.sql.ResultSet;

import security.PasswordManager;
import storage.DataStoreFacade;

// TODO: Auto-generated Javadoc
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

	/** The user. */
	User user;
	
	/**
	 * Creates a new NewUserBuilder to instantiate the new User.  
	 */
	public NewUserBuilder() {
		user = new User();
	}

	/**
	 * Sets the name.
	 *
	 * @param name 		the name
	 * @return 		the NewUserBuilder
	 */
	public NewUserBuilder setName(String name) {
		this.user.name = name;
		return this;
	}

	/**
	 * Sets the name.
	 *
	 * @param username the username
	 * @return 		the NewUserBuilder
	 */
	public NewUserBuilder setUsername(String username) {
		if(this.isUniqueUsername(username)) {
			this.user.userName = username;
		} else
			throw new IllegalArgumentException("Invalid build. Username not unique.");
		return this;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param password 		the password
	 * @return 		the NewUserBuilder
	 */
	public NewUserBuilder setPassword(String password) {
		if(this.user.userName != null) {
			if(PasswordManager.ValidatePassword(password)) {
				String passwordHash = PasswordManager.HashPassword(this.user.userName, password);
				this.user.password = passwordHash;
			} else 
				throw new IllegalArgumentException("Invalid build. Password not valid.");			
		} else
			throw new IllegalArgumentException("Invalid build. Username must be set before password.");
		return this;
	}
	
	/**
	 * Sets the privacy.
	 *
	 * @param privacy 		the privacy
	 * @return 		the NewUserBuilder
	 */
	public NewUserBuilder setPrivacy(String privacy) {
		if(privacy == "PUBLIC" || privacy == "PRIVATE")
			this.user.privacy = privacy;
		else
			this.user.privacy = "PUBLIC";
		return this;
	}	
	
	/**
	 * Sets the email.
	 *
	 * @param setEmail 		the email
	 * @return 		the NewUserBuilder
	 */
	public NewUserBuilder setEmail(String setEmail) {
		String[] email = setEmail.split("@");
		if(email.length != 2)	throw new IllegalArgumentException("Incorrect format for User Email");
		if(!email[1].contains(".")) throw new IllegalArgumentException("Incorrect format for User Email");
		this.user.email = setEmail;	
		return this;
	}	
	
	/**
	 * Builds the User.
	 * @return
	 * 		an User object, or throws an error.
	 * @throws IllegalArgumentException
	 * 		thrown if the organization being built is not complete. 
	 */
	public User build() {
		if(this.isNotComplete())
			throw new IllegalArgumentException("Invalid build. Missing some components");
		return this.user;
	}
	
	/**
	 * Checks if is not complete.
	 *
	 * @return 		true if the user is not complete
	 * 		false otherwise.
	 */
	public boolean isNotComplete() {	
		return this.user.name == null
				|| this.user.userName == null
				|| this.user.email == null
				|| this.user.password == null
				|| this.user.privacy == null;
	}
	
	/**
	 * Checks if is unique username.
	 *
	 * @param username the username
	 * @return true, if is unique username
	 */
	private boolean isUniqueUsername(String username) {
		try {
			DataStoreFacade ds = new DataStoreFacade();
			ResultSet user = ds.retrieveUserByUsername(username);
			return user.next() == false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;	
	}
	
}
