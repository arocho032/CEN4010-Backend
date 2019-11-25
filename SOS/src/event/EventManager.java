package event;

import storage.DataStoreFacade;
import org.json.*;

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
	  * Retrieves a list of events that are stored in the database.
	  * @return A JSON array of events.
	  * @throws Exception Throws an exception if anything fails while attempting to retrieve the events.
	  */
	 public JSONArray retrieveListOfEvents() throws Exception 
	 {
		 
		 DataStoreFacade ds = new DataStoreFacade();
		 
		 try 
		 {
			 EventListBuilder builder = new EventListBuilder();
			 
			 return builder.getAllAvailableEvents().returnJSONList();
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("Failed to retrieve list from the database.\nMore details: " + ex.getMessage());
		 }
		 
	 }

	/**
	 * Creates a new Event from a json Event description. Done by
	 * calling the EventBuilder class.
	 * @param jsonString
	 * 		the JSON object describing the new Event. 
	 * throws @Exception Throws exception when the fields given to create the event are invalid.
	 */ 
	 public void createEvent(String jsonString) throws Exception {
		 
		 DataStoreFacade ds = new DataStoreFacade();
		 
		 try
		 {
			 JSONObject json = new JSONObject(jsonString);
		 
			 EventBuilder builder = new EventBuilder();
			 
			 String name = json.getJSONObject("event").getString("eventName");
			 String description = json.getJSONObject("event").getString("eventDescription");
			 String date = json.getJSONObject("event").getString("eventDate");
			 String visibility = json.getJSONObject("event").getString("eventVisibility");
			 String time = json.getJSONObject("event").getString("eventTime");
			 String eventType = json.getJSONObject("event").getString("eventType");
			 String hostedBy = json.getJSONObject("event").getString("hostedBy");
			 String latCoord = json.getJSONObject("event").getString("latitude");
			 String longCoord = json.getJSONObject("event").getString("longitude");
			 
			 if ( !builder.attemptCreatingEvent(name, 
					 description, 
					 date, 
					 Boolean.getBoolean(visibility), 
					 time, 
					 Integer.getInteger(eventType), 
					 Integer.getInteger(hostedBy),
					 Double.parseDouble(latCoord), 
					 Double.parseDouble(longCoord)) )
			 {
				 throw new Exception("Invalid event fields.");
			 }
			 
			 ds.createNewEvent(name, Double.parseDouble(longCoord), Double.parseDouble(latCoord), 
					 			description, Boolean.getBoolean(visibility), time, date, 
					 			Integer.getInteger(eventType), 
					 			Integer.getInteger(hostedBy));
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("Failed to create the event.\n More details: " + ex.getMessage());
		 }
		 
		 ds.terminateConnection();
		 
	 }
	 
	 /**
	  * Gets event information based on the ID that is provided.
	  * @param eventID The ID of the event that details are being requested.
	  * @return A JSON object with the event details.
	  * @throws Exception An exception is thrown if the event is not found in the database.
	  */
	 public JSONObject loadEventDetails(int eventID) throws Exception 
	 {
		 DataStoreFacade ds = new DataStoreFacade();
		 
		 try
		 {
			 EventLoader loader = new EventLoader();
			 
			 return (loader.loadEventDetails(ds.retrieveEventDetails(eventID))).getJSONObject();
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("There was an issue in retrieving the event details.\nMore details: " + ex.getMessage());
		 }
		 
	 }
	 
	 /**
	  * Cancels the given event.
	  * @param event_id The event that is going to be cancelled.
	  * @throws Exception Throws an exception if the event is not found in the database.
	  */
	 public void cancelEvent(int event_id) throws Exception
	 {
		 DataStoreFacade ds = new DataStoreFacade();
		 
		 try 
		 {
			 ds.cancelEvent(event_id);
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("There was an issue in cancelling the event.\nMore details: " + ex.getMessage());
		 }
		 
		 ds.terminateConnection();
		 
	 }
	 
	 /**
	  * Marks a User as attending an Event by creating an entry on the 
	  * Attendance table.
	  * @param user_id
	  * 	the id of the User
	  * @param event_id
	  * 	the id of the Event
	  */
	 public void markAttendance(int userID, int eventID) throws Exception
	 {
		 DataStoreFacade ds = new DataStoreFacade();
		 try
		 {
			 ds.saveUserAttendance(userID, eventID);
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("Marking the attendance failed.\nMore details: " + ex.getMessage());
		 }
		 
		 ds.terminateConnection();
	 }
	 
}
