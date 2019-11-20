package sosInterface;

/** SOSServer communicates with the front-end for creation of events.
* Also it is held responsible for managing user sessions and keeping
* track of them, as well as dispatching messages through the system.
*/
public class SOSServer {
	/**
 	* The constructor could be made private
 	* to prevent others from instantiating this
 	* class. But this would also make it
 	* impossible to create instances of
 	* SOSServer subclasses.
	*/
	protected SOSServer() {  };

 	/**
  	 * A handle to the unique SOSServer
  	* instance.
 	*/
 	static private SOSServer _instance = null;
 	
	/**
	 * @return The unique instance of this
	 * class.
	 */
	 static public SOSServer instance() {
		 if (null == _instance) {
			 _instance = new SOSServer( );
		 }
		 	return _instance;
	 }
	 
    /**
	* This method creates an event and stores its data.
	* @param event is a String coming from the front-end including a JSON
	*							 which stores all of the event details, including name,
	*							 type, location, etc.
	* @return is true if event is successfully created and false otherwise.
	*/
	static public boolean CreateEvent(String event) {return true;}
	
	/**
	 * Dispatch an action with the parameters defined in the payload.
	 * @param action
	 * 		the action to be dispatched.
	 * @param payload
	 * 		the payload of the action.
	 */
	static public void send(String action, Object payload) {}
	
	/**
	* This method parses a message coming from the front-end which supposed
	* to be in JSON format.
	* @param jsonString is a String coming from the front-end including the JSON
	* @return is the parsed message
	*/
	static public String ParseMessage(String jsonString) {return null;}
}
