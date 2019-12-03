package event;

import java.sql.*;
import org.json.*;

import utils.JSONTranslator;

// TODO: Auto-generated Javadoc
/**
 *	A run-time representation of an Event persistent Object. This class is used as
 *  an intermediary for creation, retrieval, and modification of Event data within
 *  the Java code (and the JVM). It is encodable (or serializable) to a database 
 *  format (e.g., SQL Entry).
 */
public class Event {
	
	/** The event id. */
	protected int eventId;
	
	/** The name. */
	protected String name;
	
	/** The description. */
	protected String description;
	
	/** The date. */
	protected String date;
	
	/** The visibility. */
	protected boolean visibility;
	
	/** The time. */
	protected String time;
	
	/** The event type. */
	protected int eventType;
	
	/** The hosted by. */
	protected int hostedBy;
	
	/** The lat coordinate. */
	protected double latCoordinate;
	
	/** The long coordinate. */
	protected double longCoordinate;
	
	/** The is cancelled. */
	protected boolean isCancelled = false;
	
	/** The json translation. */
	private JSONObject jsonTranslation;

	/**
	 *  Constructs a new Event class. Called through the EventBuilder class. 
	 *  Attribute assignations are done through protected scope. 
	 */
	protected Event() {}
	
	/**
	 * Creates an Event from a given ResultSet.
	 *
	 * @param results 		the input ResultSet
	 * @throws Exception 		Thrown if the ResultSet is missing any event-defining variable.
	 */
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
	
	/**
	 * Gets the event ID.
	 *
	 * @return the event id
	 */
	protected int getEventID() {
		return eventId;
	}
	
	/**
	 * Gets the json.
	 *
	 * @return the json
	 */
	protected JSONObject getJSON()
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
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Checks if is visibility.
	 *
	 * @return the visibility
	 */
	public boolean isVisibility() {
		return visibility;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Gets the event type.
	 *
	 * @return the eventType
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * Gets the hosted by.
	 *
	 * @return the hostedBy
	 */
	public int getHostedBy() {
		return hostedBy;
	}

	/**
	 * Gets the lat coordinate.
	 *
	 * @return the latCoordinate
	 */
	public double getLatCoordinate() {
		return latCoordinate;
	}

	/**
	 * Gets the long coordinate.
	 *
	 * @return the longCoordinate
	 */
	public double getLongCoordinate() {
		return longCoordinate;
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
