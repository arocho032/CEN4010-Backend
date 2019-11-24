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
	
    /**
     * Requests to get all events in the database that are close to the latitude and longitude given.
     * @param lat The latitude coordinate of the user requesting the events.
     * @param lon The longitude coordinate of the user requesting the events.
     * @return A list of events in JSON.
     * @throws Exception Throws an exception if the events are not successfully extracted from the database.
     */
    public EventList filterEventsByLocation(double lat, double lon) throws Exception
    {
    	try
    	{
    		DataStoreFacade ds = new DataStoreFacade();
    	
    		list = new EventList(ds.filterEventsByLocation(lat, lon));
    		
    		ds.terminateConnection();
    		
    		return list;
    	}
    	catch(Exception ex)
    	{
    		throw new Exception("An error occurred while attempting to filter events by location.\nMore Details: " + ex.getMessage());
    	}
    }
    
    public EventList getAllAvailableEvents() throws Exception
    {
    	try
    	{
    		DataStoreFacade ds = new DataStoreFacade();
    		
    		list = new EventList(ds.getEvents());
    		
    		ds.terminateConnection();
    		
    		return list;
    	}
    	catch(Exception ex)
    	{
    		throw new Exception("An error occurred while attempting to grab all available events.\nMore detials: " + ex.getMessage());
    	}
    }
    
    
	
}
