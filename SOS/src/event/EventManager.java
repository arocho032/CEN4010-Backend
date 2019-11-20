package event;


/**
 * EventManager, which is a Singleton which manages all the Event functions. 
 * This class receives dispatched actions from the SOS Dispatcher and completes 
 * that action using objects internal to its subsystem. It also is in charge of 
 * interacting with the SOS Data Store Façade directly. Part of the role of this 
 * class is to parse front-end format data (e.g., JSON-String description of new 
 * Events) and calling the appropriate functions on other classes according to that 
 * data. It is also in charge of encoding Event objects into database-format (e.g., 
 * SQL Table entries). Another role is to create EventLists based on filter requests 
 * through the EventListBuilder.
 */
public class EventManager {

	/**
 	* A protected or private constructor ensures
 	* that no other class has access to the Singleton.
	*/
	protected EventManager() {}

 	/**
  	 * A handle to the unique EventManager
  	* instance.
 	*/
 	static private EventManager _instance = null;
	
	/**
	 * @return The unique instance of this
	 * class.
	 */
	 static public EventManager instance() {
		 if ( null == _instance) {
			 _instance = new EventManager();
		 }
		 return _instance;
	 }

	 /**
	  * Retrieves a list of Events in the for of an EventList. This is
	  * done through an EventListBuilder.
	  * @param event_ids
	  * 	the ids of the Events to be added.
	  * @return
	  * 	the EventList containing the given Events.
	  */
	 public EventList retrieveListOfEvents(int[] event_ids) {return null;}

	/**
	 * Creates a new Event from a json Event description. Done by
	 * calling the EventBuilder class.
	 * @param jsonString
	 * 		the JSON object describing the new Event.
	 * @return
	 * 		a Event object with the given attributes. 
	 */
	 public Event createEvent(String jsonString) {return null;}
	 
	 /**
	  * Loads an Event with the given Event id. 
	  * @param event_id
	  * 	the id of the wanted Event.
	  * @return
	  * 	the Event with the corresponding id.
	  */
	 public Event getEventDetails(int event_id) {return null;}
	 
	 /**
	  * Sets the is_cancelled property of the given Event to
	  * True. 
	  * @param event_id
	  * 	the wanted Event.
	  */
	 public void cancelEvent(int event_id) {}
	 
	 /**
	  * Marks a User as attending an Event by creating an entry on the 
	  * Attendance table.
	  * @param user_id
	  * 	the id of the User
	  * @param event_id
	  * 	the id of teh Event
	  */
	 public void markAttendance(int user_id, int event_id) {}
	 
}
