package event;

import storage.DataStoreFacade;
import user.User;

import java.sql.ResultSet;

import org.json.*;

// TODO: Auto-generated Javadoc
/**
 * EventManager, which is a Singleton which manages all the Event functions. 
 * This class receives dispatched actions from the SOS Dispatcher and completes 
 * that action using objects internal to its subsystem. It also is in charge of 
 * interacting with the SOS Data Store Fa�ade directly. Part of the role of this 
 * class is to parse front-end format data (e.g., JSON-String description of new 
 * Events) and calling the appropriate functions on other classes according to that 
 * data. It is also in charge of encoding Event objects into database-format (e.g., 
 * SQL Table entries). Another role is to create EventLists based on filter requests 
 * through the EventListBuilder.
 */
public class EventManager {

	/** The ds. */
	DataStoreFacade ds;
	
	/** The el. */
	EventLoader el;
	
	/** The elb. */
	EventListBuilder elb;
	
	/**
 	* A protected or private constructor ensures
 	* that no other class has access to the Singleton.
	*/
	protected EventManager() {
		try {
			this.ds = new DataStoreFacade();
			this.el = new EventLoader();
			this.elb = new EventListBuilder();			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

 	/**
  	 * A handle to the unique EventManager
  	* instance.
 	*/
 	static private EventManager _instance = null;
	
	/**
	 * Instance.
	 *
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
 	 *
 	 * @param payload the payload
 	 * @return A JSON array of events.
 	 */
	 public JSONObject retrieveListOfEvents(JSONObject payload)
	 {
			JSONObject ret = new JSONObject();
			try {
								
				JSONArray members = elb.getAllAvailableEvents(ds.getEvents()).returnJSONList();					
				ret.put("data", members.toString());
				
			} catch (JSONException e) {
				try {
					ret.put("error", "true");
					ret.put("type", "orgidValueError");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (Exception e) {
				try {
					ret.put("error", "true");
					ret.put("type", "generalException");
					ret.put("payload", "An error occurred while attempting to load the details for the Events.\nMore information: " + e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			return ret;		
		 		 
	 }
	 
	 /**
	  * Retrieves a list of Event given a location.
	  * @param payload
	  * 	A JSONObject with the following keys:
	  * 		lantitude	the latitute of the search center
	  * 		longitude	the longitude of the search center
	  * @return
	  * 	All events within 0.5 latitude/longitude range from the given center.
	  */
	 public JSONObject retrieveListOfEventsByLocation(JSONObject payload)
	 {
		 
			JSONObject ret = new JSONObject();
			try {
								
				ResultSet set = ds.filterEventsByLocation(payload.getDouble("lantitude"), payload.getDouble("longitude"));
				JSONArray events = this.elb.getAllAvailableEvents(set).returnJSONList();
				ret.put("data", events.toString());
				
			} catch (JSONException e) {
				try {
					ret.put("error", "true");
					ret.put("type", "valueError");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (Exception e) {
				try {
					ret.put("error", "true");
					ret.put("type", "generalException");
					ret.put("payload", "An error occurred while attempting to retrieve all events by location.\nMore details: " + e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			return ret;		
	 }

	/**
	 * Creates a new Event from a json Event description. Done by
	 * calling the EventBuilder class.
	 *
	 * @param json the json
	 * @return the JSON object
	 */ 
	 public JSONObject createEvent(JSONObject json) {
		 
			JSONObject ret = new JSONObject();
			try {
				
				 EventBuilder builder = new EventBuilder();
				 
				 try {
					 builder.setDate(json.getJSONObject("event").getString("eventDate"));
				 } catch (IllegalArgumentException ex) {
					 ex.printStackTrace();
					 ret.put("error", "true");
					 ret.put("type", "invalidDateError");
				 }
				 
				 try {
					 builder.setTime(json.getJSONObject("event").getString("eventTime") + ":00");
				 } catch (IllegalArgumentException ex) {
					 ex.printStackTrace();
					 ret.put("error", "true");
					 ret.put("type", "invalidTimeError");
				 }
				 
				 builder
				 .setName(json.getJSONObject("event").getString("eventName"))
				 .setDescription(json.getJSONObject("event").getString("eventDescription"))
				 .setVisibility(json.getJSONObject("event").getBoolean("eventVisibility"))
				 .setEventType(json.getJSONObject("event").getInt("eventType"))
				 .setHostedBy(json.getJSONObject("event").getInt("hostedBy"))
				 .setCoordinates(json.getJSONObject("event").getDouble("latitude"), json.getJSONObject("event").getDouble("longitude"));
				 
				 Event event = null;
				 if(!builder.isNotComplete())
					 event = builder.build();
				 
				 this.ds.createNewEvent(event);
				 return ret;
				 
			} catch (JSONException e) {
				try {
					ret.put("error", "true");
					ret.put("type", "usernameValueError");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (Exception e) {
				try {
					ret.put("error", "true");
					ret.put("type", "generalException");
					ret.put("payload", "Failed to create the event.\n More details: " + e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			return ret;		 		 
	 }
	 
	 /**
 	 * Gets event information based on the ID that is provided.
 	 *
 	 * @param payload The ID of the event that details are being requested.
 	 * @return A JSON object with the event details.
 	 */
	 public JSONObject loadEventDetails(JSONObject payload) 
	 {
		 
		JSONObject ret = new JSONObject();
		try {
	
			ResultSet set = ds.retrieveEventDetails(payload.getInt("event_id"));
			Event event = this.el.loadEventDetails(set);
			ret.put("data", event.getJSON());
			return ret;
	
		} catch (JSONException e) {
			try {
				ret.put("error", "true");
				ret.put("type", "payloadError");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				ret.put("error", "true");
				ret.put("type", "generalException");
				ret.put("payload", "There was an issue in retrieving the event details.\nMore details: " + e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return ret;	
		 		 
	 }
	 
	 /**
 	 * Cancels the given event.
 	 *
 	 * @param payload The event that is going to be cancelled.
 	 * @return the JSON object
 	 */
	 public JSONObject cancelEvent(JSONObject payload)
	 {
		 
			JSONObject ret = new JSONObject();
			try {
		
				 this.ds.cancelEvent(payload.getJSONObject("event").getInt("event_id"));
				
			} catch (JSONException e) {
				try {
					ret.put("error", "true");
					ret.put("type", "payloadError");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (Exception e) {
				try {
					ret.put("error", "true");
					ret.put("type", "generalException");
					ret.put("payload", "An error occurred while attempting to cancel the Event.\nMore information: " + e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			return ret;	
		 
		 
	 }
	 
	 /**
 	 * Marks a User as attending an Event by creating an entry on the 
 	 * Attendance table.
 	 *
 	 * @param payload the payload
 	 * @return the JSON object
 	 */
	 public JSONObject markAttendance(JSONObject payload)
	 {
			JSONObject ret = new JSONObject();
			try {
		
				ds.saveUserAttendance(payload.getInt("user_id"), payload.getInt("event_id"));
		
			} catch (JSONException e) {
				try {
					ret.put("error", "true");
					ret.put("type", "payloadError");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (Exception e) {
				try {
					ret.put("error", "true");
					ret.put("type", "generalException");
					ret.put("payload", "There was an issue in retrieving the event details.\nMore details: " + e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			return ret;

	 }
	 
	 public static void main(String[] args) throws JSONException {
		 System.out.println("Test");
		 System.out.println(
				 EventManager.instance().getEventOfOrganization(new JSONObject("{\"organization\":{\"startIndex\":0,\"organization_id\":\"3\"}}\r\n"))
		 );
	 }

	 /**
	  * Returns all the Events hosted by a given Organization.
	  * @param payload
	  * 	A JSONObject which containts the following keys:
	  * 		organization	a JSONObject with the following keys:
	  * 			organization_id		which is the id of the target organization.
	  * @return
	  * 	All events hosted by the organization.
	  */
	public JSONObject getEventOfOrganization(JSONObject payload) {
		
		JSONObject ret = new JSONObject();
		try {
			
			ResultSet set = null;
			if(payload.getJSONObject("organization").has("organization_id")) {
			
				set = this.ds.getEventsByOrganization(payload.getJSONObject("organization").getInt("organization_id"));
				int skip = payload.getJSONObject("organization").getInt("startIndex");
				int count = 20;
				
				JSONArray members = this.elb.getAllAvailableEvents(set).returnJSONList();

				set.close();
				ret.put("data", members.toString());
				
			} else {
				ret.put("error", "true");
				ret.put("type", "noSearchParameter");
				return ret;
			}
			
		} catch (JSONException e) {
			try {
				ret.put("error", "true");
				ret.put("type", "orgidValueError");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				ret.put("error", "true");
				ret.put("type", "generalException");
				ret.put("payload", "An error occurred while attempting to load the details for the organization.\nMore information: " + e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return ret;

	}
	 
}
