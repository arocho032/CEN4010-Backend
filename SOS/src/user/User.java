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
	
	private String name;
	private String userName;
	private String password;
	private String privacy;
	private String email;
	
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
		}
		catch(Exception ex)
		{
			throw new Exception("Failed to transform data contents to JSON.\nMore detials: " + ex.getMessage());
		}
		
	}
	
	public User(String setName, String setUserName, String setPassword, String setPrivacy, String setEmail) throws Exception
	{
		this.name = setName;
		this.userName = setUserName;
		this.password = setPassword;
		
		if(setPrivacy != "PUBLIC" || setPrivacy != "PRIVATE")
		{
			throw new Exception("Incorrect format for privacy value.");
		}
		
		this.privacy = setPrivacy;
		
		String[] email = setEmail.split("@");
		
		if(email.length != 2)
		{
			throw new Exception("Incorrect format for User Email");
		}
		
		if(!email[1].contains("."))
		{
			throw new Exception("Incorrect format for User Email");
		}
		
		this.email = setEmail;
		
	}
	
	public JSONObject getJSON()
	{
		return this.jsonTranslation;
	}
	
	
	
	
}
