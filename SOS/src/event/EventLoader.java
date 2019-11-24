package event;

import java.sql.*;

public class EventLoader {

	private Event loadedEvent;
	
	public EventLoader()
	{
		loadedEvent = null;
	}
	
	public Event loadEventDetails(ResultSet results) throws Exception
	{
		try
		{
			loadedEvent = new Event(results);
			return loadedEvent;
		}
		catch(Exception ex)
		{
			throw new Exception("An error occurred while trying to load event details.\nMore details: " + ex.getMessage());
		}
	}
	
	
	
}
