package user;

import org.json.*;

import utils.JSONTranslator;

import java.sql.*;

/**
 * A run-time representation of a User persistent object. This class 
 * is used as an intermediary for creation, retrieval, and modification 
 * of User data within the Java code (and the JVM). It is encodable 
 * (or serializable) to a database format (e.g., SQL Entry). 
 */
public class User {
	
	protected int user_id = -1;
	protected String name;
	protected String userName;
	protected String password;
	protected String privacy = "PUBLIC";
	protected String email = "test@gmail.com";
	
	private JSONObject jsonTranslation;

	/**
	 *  Constructs a new User class. Called through the UserBuilder class. 
	 *  Attribute assignations are done through protected scope. 
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
//			this.email = jsonTranslation.getString("email");
			
		}
		catch(Exception ex)
		{
			throw new Exception("Failed to transform data contents to JSON.\nMore detials: " + ex.getMessage());
		}
		
	}
		
	public User() {}

	public JSONObject getJSON() {
		return this.jsonTranslation;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the privacy
	 */
	public String getPrivacy() {
		return privacy;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return user_id;
	}
	
}
