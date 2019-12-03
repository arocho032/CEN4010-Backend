package event;

import java.sql.*;

// TODO: Auto-generated Javadoc
/**
 * The Class EventLoader.
 */
public class EventLoader {

	/** The loaded event. */
	private Event loadedEvent;

	/**
	 * Loads an Event from the given ResultSet.
	 * @param results
	 * 		the ResultSet
	 * @return
	 * 		the Event
	 * @throws Exception
	 * 		Happens when the ResultSet 
	 */
	public Event loadEventDetails(ResultSet results) throws Exception
	{
		try
		{
			if(results.next())
				loadedEvent = new Event(results);
			return loadedEvent;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("An error occurred while trying to load event details.\nMore details: " + ex.getMessage());
		}
	}
	
	
	
}
