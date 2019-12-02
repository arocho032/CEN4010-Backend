package event;

import storage.DataStoreFacade;
import java.sql.*;


/**
 * A Builder which creates new EventList objects. As other builders, it is 
 * used to decouple the process of creating a new EventList from the actual 
 * EventList class, and also provides functions implementing attribute-base 
 * filtering (e.g., filter by location, or by hosting organization, etc.)
 */
public class EventListBuilder {
	
	private EventList list;
	
	/**
	 * Creates a new EventListBuilder to instantiate the new EventList.  
	 */
	public EventListBuilder() 
	{
		list = null;
	}
	
    
    public EventList getAllAvailableEvents(ResultSet set) throws Exception
    {
    	try
    	{
    		list = new EventList(set);
    		return list;
    	}
    	catch(Exception ex)
    	{
    		throw new Exception("An error occurred while attempting to retrieve all available events.\nMore detials: " + ex.getMessage());
    	}
    }
    
    
	
}
