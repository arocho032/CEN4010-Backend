package user;

import org.json.*;

import utils.JSONTranslator;

import java.sql.*;

// TODO: Auto-generated Javadoc
/**
 * A run-time representation of a User persistent object. This class 
 * is used as an intermediary for creation, retrieval, and modification 
 * of User data within the Java code (and the JVM). It is encodable 
 * (or serializable) to a database format (e.g., SQL Entry). 
 */
public class User {
	
	/** The user id. */
	protected int user_id = -1;
	
	/** The name. */
	protected String name = null;
	
	/** The user name. */
	protected String userName = null;
	
	/** The password. */
	protected String password = null;
	
	/** The privacy. */
	protected String privacy = null;
	
	/** The email. */
	protected String email = null;
	
	/** The json translation. */
	private JSONObject jsonTranslation = null;

	/**
	 *  Constructs a new User class. Called through the UserBuilder class. 
	 *  Attribute assignations are done through protected scope.
	 *
	 * @param set the set
	 * @throws Exception the exception
	 */
	protected User(ResultSet set) throws Exception
	{
		try
		{
			jsonTranslation = JSONTranslator.resultSetToJSONObject(set);
			this.name = jsonTranslation.getString("name");
			this.user_id = jsonTranslation.getInt("user_id");
			this.userName = jsonTranslation.getString("user_name");
			this.password = jsonTranslation.getString("password");
			this.privacy = jsonTranslation.getString("privacy");
			this.email = jsonTranslation.getString("email");
			
		}
		catch(Exception ex)
		{
			throw new Exception("Failed to transform data contents to JSON.\nMore detials: " + ex.getMessage());
		}
		
	}
		
	/**
	 * Creates an empty user object, for the Builder.
	 */
	protected User() {}

	/**
	 * Returns the JSON form of this User.
	 *
	 * @return the json
	 */
	public JSONObject getJSON() {
		return this.jsonTranslation;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the privacy.
	 *
	 * @return the privacy
	 */
	public String getPrivacy() {
		return privacy;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user_id
	 */
	public int getUser_id() {
		return user_id;
	}
	
}
