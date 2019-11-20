package event;

/**
 * A Builder class which creates new Events. It is used to decouple the 
 * parts of the process of creating a new Event from the actual Event class,
 * which is intended to only be a data wrapper class. Namely, this class 
 * implements the checks and validations necessary to create a valid Event 
 * and will reject invalid ones.
 */
public class EventBuilder {

	/**
	 * Creates a new EventBuilder to instantiate the new Event.  
	 */
	public EventBuilder() {};
	
	/**
	 * Checks the current Event, returning True if it is valid so far
	 * and False otherwise.
	 * @return
	 * 		True, if the Event is valid so far.
	 * 		False if otherwise. 
	 */
	protected boolean Validate() {return false;}

	/**
	 * Adds a location value to the current Event.
	 * @param location
	 * 		the value to be added.
	 * @return
	 * 		the current builder.
	 */
	public EventBuilder setLocation(String location) {return this;}
	
	/**
	 * Adds a description value to the current Event.
	 * @param description
	 * 		the value to be added.
	 * @return
	 * 		the current builder.
	 */
	public EventBuilder setDescription(String description) {return this;}
	
    /**
     * Adds a date value to the current Event.
     * @param date
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public EventBuilder setDate(String date) {return this;}
	
    /**
     * Adds a visibility value to the current Event.
     * @param visibility
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public EventBuilder setVisibility(String visibility) {return this;}
    
    /**
     * Adds a name value to the current Event.
     * @param name
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public EventBuilder setName(String name) {return this;}
    
    /**
     * Adds a time value to the current Event.
     * @param time
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public EventBuilder setTime(String time) {return this;} 

    /**
     * Adds a Event Type value to the current Event.
     * @param eventType
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public EventBuilder setEventtype(String eventType) {return this;}
    
    /**
     * Adds a host value to the current Event.
     * @param host
     *              the value to be added.
     * @return
     *              the current builder.
     */
    public EventBuilder setHost(String host) {return this;}
    
	/**
	 * Finalizes the Event creation and returns it.
	 * @return
	 * 		returns the final Event.
	 */
	public Event BuildEvent() {return null;}

}
