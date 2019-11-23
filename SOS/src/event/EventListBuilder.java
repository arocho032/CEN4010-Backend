package event;

import storage.DataStoreFacade;


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

    public EventList filterEventsByLocation(double lat, double lon) throws Exception
    {
    	try
    	{
    		DataStoreFacade ds = new DataStoreFacade();
    	
    		list = new EventList(ds.filterEventsByLocation(lat, lon));
    		
    		return list;
    	}
    	catch(Exception ex)
    	{
    		throw new Exception("An error occurred while attempting to filter events by location.\nMore Details: " + ex.getMessage());
    	}
    }
    
    
	
}
