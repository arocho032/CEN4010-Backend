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
	
	protected int eventId;
	protected String name;
	protected String description;
	protected String date;
	protected boolean visibility;
	protected String time;
	protected int eventType;
	protected int hostedBy;
	protected double latCoordinate;
	protected double longCoordinate;
	protected boolean isCancelled = false;
	
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
			 eventId = jsonTranslation.getInt("event_id");
			 name = jsonTranslation.getString("name");
			 description = jsonTranslation.getString("description");
			 date = jsonTranslation.getString("date");
			 visibility = jsonTranslation.getBoolean("visibility");
			 time = jsonTranslation.getString("time");
			 eventType = 1;
			 hostedBy = jsonTranslation.getInt("hosted_by");
			 latCoordinate = jsonTranslation.getDouble("lat_coordinate");
			 longCoordinate = jsonTranslation.getDouble("long_coordinate");		 
			 isCancelled = jsonTranslation.getBoolean("is_cancelled");		 
		
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Failed to parse event to JSON.\nMore Details: " + ex.getMessage());
		}
	}
	
	protected int getEventID() {
		return eventId;
	}
	
	protected JSONObject getJSON()
	{
		return this.jsonTranslation;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return the visibility
	 */
	public boolean isVisibility() {
		return visibility;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @return the eventType
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * @return the hostedBy
	 */
	public int getHostedBy() {
		return hostedBy;
	}

	/**
	 * @return the latCoordinate
	 */
	public double getLatCoordinate() {
		return latCoordinate;
	}

	/**
	 * @return the longCoordinate
	 */
	public double getLongCoordinate() {
		return longCoordinate;
	}

	/**
	 * @return the jsonTranslation
	 */
	public JSONObject getJsonTranslation() {
		return jsonTranslation;
	}

}
