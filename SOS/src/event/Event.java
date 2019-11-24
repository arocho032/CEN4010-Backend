package event;

import java.sql.*;
import org.json.*;

import utils.JSONTranslator;

/**
 *	A run-time representation of an Event persistent Object. This class is used as
 *  an intermediary for creation, retrieval, and modification of Event data within
 *  the Java code (and the JVM). It is encodable (or serializable) to a database 
 *  format (e.g., SQL Entry).
 */
public class Event {
	
	protected String name;
	protected String description;
	protected String date;
	protected boolean visibility;
	protected String time;
	protected int eventType;
	protected int hostedBy;
	protected double latCoordinate;
	protected double longCoordinate;
	
	private JSONObject jsonTranslation;

	/**
	 *  Constructs a new Event class. Called through the EventBuilder class. 
	 *  Attribute assignations are done through protected scope. 
	 */
	protected Event() {}
	
	protected Event(ResultSet results) throws Exception
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
	
	
	protected Event(String name, String description, String date, boolean visibility, String time, int eventType,
					int hostedBy, double lat, double lon)
	{
		this.name = name;
		this.description = description;
		this.date = date;
		this.visibility = visibility;
		this.time = time;
		this.eventType = eventType;
		this.hostedBy = hostedBy;
		this.latCoordinate = lat;
		this.longCoordinate = lon;
	}
	
	protected JSONObject getJSONObject()
	{
		return this.jsonTranslation;
	}
	
	
	
	

}
