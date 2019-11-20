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

	/**
	 * Creates a new NewUserBuilder to instantiate the new User.  
	 */
	public NewUserBuilder() {};	
	
	/**
	 * Checks the current User, returning True if it is valid so far
	 * and False otherwise.
	 * @return
	 * 		True, if the Event is valid so far.
	 * 		False if otherwise. 
	 */
	protected boolean ValidateCredentials() {return false;}	
	
    /**
     * Adds a name value to the current User.
     * @param name
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public NewUserBuilder setName(String name) {return this;}
    
    /**
     * Adds a username value to the current User.
     * @param username
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public NewUserBuilder setUsername(String username) {return this;}

    /**
     * Adds a password value to the current User.
     * @param password
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public NewUserBuilder setPassword(String password) {return this;}
 
    /**
     * Adds a privacy value to the current User.
     * @param privacy
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public NewUserBuilder setPrivacy(String privacy) {return this;}    
    
    /**
     * Adds a email value to the current User.
     * @param email
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public NewUserBuilder setEmail(String email) {return this;}
    
	/**
	 * Finalizes the User creation and returns it.
	 * @return
	 * 		returns the final User.
	 */
	public User BuildUser() {return null;}
}
