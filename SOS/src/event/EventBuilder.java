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
	public boolean attemptCreatingEvent(String name, String description, String date, boolean visibility, String time, int eventType, 
						int hostedBy, double latCoordinate, double longCoordinate )
	{
		try
		{
			
			if ( date.split("/").length != 3)
			{
				return false;
			}
			
			if (time.split(":").length != 3)
			{
				return false;
			}
			
			
			
			event = new Event(name, description, date, visibility, time, eventType, hostedBy, latCoordinate, longCoordinate);
			
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
		
	}
	
	

}
