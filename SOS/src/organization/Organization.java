package organization;

import java.sql.*;
import org.json.*;

import utils.JSONTranslator;

// TODO: Auto-generated Javadoc
/**
 * A run-time representation of an Organization persistent object. 
 * This class is used as an intermediary for creation, retrieval, and 
 * modification of Organization data within the Java code (and the JVM). 
 * It is encodable (or serializable) to a database format (e.g., SQL Entry)
 */
public class Organization {
	
	/** The name. */
	protected String name;
	
	/** The description. */
	protected String description;
	
	/** The privacy. */
	protected String privacy;
	
	/** The requirements. */
	protected String requirements;
		
	/** The json translation. */
	private JSONObject jsonTranslation;
	
	
	/**
	 * Constructs a new Organization class. Called through the OrganizationBuilder
	 * class. Attribute assignations are done through protected scope. 
	 */
	protected Organization() {}
	
	/**
	 * Creates an Organization from the target ResultSet. 
	 * @param results
	 * 		the result set.
	 * @throws Exception
	 * 		thrown if there's an error, like using the incorrect entry.
	 */
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
	
	/**
	 * Gets the JSON object.
	 *
	 * @return the json translation of this Organization.
	 */
	protected JSONObject getJSONObject()
	{
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * Gets the requirements.
	 *
	 * @return the requirements
	 */
	public String getRequirements() {
		return requirements;
	}

	/**
	 * Gets the json translation.
	 *
	 * @return the jsonTranslation
	 */
	public JSONObject getJsonTranslation() {
		return jsonTranslation;
	}

}
