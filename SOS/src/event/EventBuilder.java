package event;

// TODO: Auto-generated Javadoc
/**
 * A Builder class which creates new Events. It is used to decouple the 
 * parts of the process of creating a new Event from the actual Event class,
 * which is intended to only be a data wrapper class. Namely, this class 
 * implements the checks and validations necessary to create a valid Event 
 * and will reject invalid ones.
 */
public class EventBuilder {

	/** The event. */
	private Event event;
	
	/**
	 * Creates a new EventBuilder to instantiate the new Event.  
	 */
	public EventBuilder() 
	{
		event = new Event();
	};

	/**
	 * Attempts to create an event from the given parameters.
	 *
	 * @param name The name of the event.
	 * @return True or false depending on the status of the event creation.
	 */
	
	/**
	 * Sets the name of the event.
	 * @param name
	 * 		the name of the event.
	 * @return
	 * 		the EventBuilder.
	 */
	public EventBuilder setName(String name) {
		this.event.name = name;
		return this;
	}
	
	/**
	 * Sets the description of the event.
	 * @param description
	 * 		the description of the event.
	 * @return
	 * 		the EventBuilder.
	 */
	public EventBuilder setDescription(String description) {
		this.event.description = description;
		return this;
	}
	
	/**
	 * Sets the date of the event.
	 * @param date
	 * 		the date of the event.
	 * @return
	 * 		the EventBuilder.
	 */
	public EventBuilder setDate(String date) {
		// TODO: Add test for date.
		if(date.split("/").length != 3)
			throw new IllegalArgumentException("Date must be of format: xx/xx/xxx");
		this.event.date = date;
		return this;
	}
	
	/**
	 * Sets the visibility of the event.
	 * @param visibility
	 * 		the visibility of the event.
	 * @return
	 * 		the EventBuilder.
	 */
	public EventBuilder setVisibility(boolean visibility) {
		this.event.visibility = visibility;
		return this;
	}
	
	/**
	 * Sets the time of the event.
	 * @param time
	 * 		the time of the event.
	 * @return
	 * 		the EventBuilder.
	 */
	public EventBuilder setTime(String time) {
		// TODO: Add time check.
		if (time.split(":").length != 3)
			throw new IllegalArgumentException("Time must be of format: 00:00:00");
		this.event.time = time;
		return this;
	}
	
	/**
	 * Sets the type of the event.
	 * @param eventType
	 * 		the type of the event.
	 * @return
	 * 		the EventBuilder.
	 */
	public EventBuilder setEventType(int eventType) {
		if(eventType != 1)
			eventType = 1;
		this.event.eventType = eventType;
		return this;
	}
	
	/**
	 * Sets the id of the host.
	 * @param organization_id
	 * 		the id of the host.
	 * @return
	 * 		the EventBuilder.
	 */
	public EventBuilder setHostedBy(int organization_id) {
		// TODO: Add org_id check (if exist)
		this.event.hostedBy = organization_id;
		return this;
	}

	/**
	 * Sets the coordinates of the event location.
	 * @param lat
	 * 			the latitute of the location
	 * @param logn
	 * 			the longitude of the location
	 * @return
	 * 		the event builder.
	 */
	public EventBuilder setCoordinates(double lat, double logn) {
		this.event.latCoordinate = lat;
		this.event.longCoordinate = logn;
		return this;
	}
	
	/**
	 * Builds the event.
	 * @return
	 * 		the event if the build is complete, throws an error otherwise. 
	 */
	public Event build() { 
		if(isNotComplete())
			throw new IllegalArgumentException("Build not complete, missing something.");
		return this.event;
	}
	
	/**
	 * Checks if is not complete.
	 *
	 * @return 		true if the event is not complete,
	 * 		false otherwise.
	 */
	public boolean isNotComplete() {
		return event.name == null
				|| event.description == null
				|| event.date == null
				|| event.eventType == -1
				|| event.hostedBy == -1
				|| event.time == null
				|| event.latCoordinate == -1
				|| event.longCoordinate == -1;
	}
	


}
