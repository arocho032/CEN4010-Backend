package event;

/**
 * A Builder which creates new EventList objects. As other builders, it is 
 * used to decouple the process of creating a new EventList from the actual 
 * EventList class, and also provides functions implementing attribute-base 
 * filtering (e.g., filter by location, or by hosting organization, etc.)
 */
public class EventListBuilder {
	
	/**
	 * Creates a new EventListBuilder to instantiate the new EventList.  
	 */
	public EventListBuilder() {};

    /**
     * Adds an Event value to the current EventList.
     * @param addEventToList
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public EventListBuilder setAddeventtolist(String addEventToList) {return this;}
	
}
