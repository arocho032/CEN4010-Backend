package organization;

import java.sql.*;
import org.json.*;

import utils.JSONTranslator;

/**
 * A run-time representation of an Organization persistent object. 
 * This class is used as an intermediary for creation, retrieval, and 
 * modification of Organization data within the Java code (and the JVM). 
 * It is encodable (or serializable) to a database format (e.g., SQL Entry)
 */
public class Organization {
	
	protected String name;
	protected String description;
	protected String privacy;
	protected String requirements;
	
	private JSONObject jsonTranslation;
	
	
	/**
	 * Constructs a new Organization class. Called through the OrganizationBuilder
	 * class. Attribute assignations are done through protected scope. 
	 */
	protected Organization() {}
	
	protected Organization(ResultSet results) throws Exception
	{
		try
		{
			jsonTranslation = JSONTranslator.resultSetToJSONObject(results);
		}
		catch(Exception ex)
		{
			throw new Exception("Failed to parse event to JSON.\nMore Details: " + ex.getMessage());
		}
		
	}
	
	protected JSONObject getJSONObject()
	{
		return this.jsonTranslation;
	}

}
