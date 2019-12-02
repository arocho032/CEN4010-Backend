package event;

/**
 * A Builder class which creates new Events. It is used to decouple the 
 * parts of the process of creating a new Event from the actual Event class,
 * which is intended to only be a data wrapper class. Namely, this class 
 * implements the checks and validations necessary to create a valid Event 
 * and will reject invalid ones.
 */
public class EventBuilder {

	private Event event;
	
	/**
	 * Creates a new EventBuilder to instantiate the new Event.  
	 */
	public EventBuilder() 
	{
		event = new Event();
	};

	public EventBuilder setName(String name) {
		this.event.name = name;
		return this;
	}
	
	public EventBuilder setDescription(String description) {
		this.event.description = description;
		return this;
	}
	
	public EventBuilder setDate(String date) {
		// TODO: Add test for date.
		if(date.split("/").length != 3)
			throw new IllegalArgumentException("Date must be of format: xx/xx/xxx");
		this.event.date = date;
		return this;
	}
	
	public EventBuilder setVisibility(boolean visibility) {
		this.event.visibility = visibility;
		return this;
	}
	
	public EventBuilder setTime(String time) {
		// TODO: Add time check.
		if (time.split(":").length != 3)
			throw new IllegalArgumentException("Time must be of format: 00:00:00");
		this.event.time = time;
		return this;
	}
	
	public EventBuilder setEventType(int eventType) {
		if(eventType != 1)
			eventType = 1;
		this.event.eventType = eventType;
		return this;
	}
	
	public EventBuilder setHostedBy(int organization_id) {
		// TODO: Add org_id check (if exist)
		this.event.hostedBy = organization_id;
		return this;
	}
	
	public EventBuilder setCoordinates(double lat, double logn) {
		this.event.latCoordinate = lat;
		this.event.longCoordinate = logn;
		return this;
	}
	
	public Event build() { 
		if(isNotComplete())
			throw new IllegalArgumentException("Build not complete, missing something.");
		return this.event;
	}
	
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
	
	/**
	 * Attempts to create an event from the given parameters
	 * @param name The name of the event.
	 * @param description The description of the event.
	 * @param date The date of the event.
	 * @param visibility The visibility of the event.
	 * @param time The time of the event.
	 * @param eventType the type of event.
	 * @param hostedBy The organization who is hosting the event.
	 * @param latCoordinate The latitude where the event will take place.
	 * @param longCoordinate The longitude where the event will take place.
	 * @return True or false depending on the status of the event creation.
	 */

}
