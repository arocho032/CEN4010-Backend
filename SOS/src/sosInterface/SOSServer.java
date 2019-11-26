package sosInterface;

import io.socket.client.IO;
import io.socket.client.*;
import io.socket.emitter.Emitter;
import okhttp3.Dispatcher;

import org.json.JSONObject;
import org.json.JSONArray;

/** SOSServer communicates with the front-end for creation of events.
* Also it is held responsible for managing user sessions and keeping
* track of them, as well as dispatching messages through the system.
*/
public class SOSServer {
	
	Socket socket;
	
	/**
 	* The constructor could be made private
 	* to prevent others from instantiating this
 	* class. But this would also make it
 	* impossible to create instances of
 	* SOSServer subclasses.
	*/
	public SOSServer(String uri) throws Exception
	{ 
		try
		{
			socket = IO.socket(uri);
		}
		catch(Exception ex)
		{
			throw new Exception("There was an issue connecting to the given URI.\nMore details: " + ex.getMessage());
		}
	};

 	/**
  	 * A handle to the unique SOSServer
  	* instance.
 	*/
 	static private SOSServer _instance = null;
 	
	/**
	 * @return The unique instance of this
	 * class.
	 */
	 static public SOSServer instance(String uri) throws Exception
	 {
		 try
		 {
		 if (null == _instance) {
			 _instance = new SOSServer(uri);
		 }
		 	return _instance;
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("Failed to retrieve the instance of the SOS server.\nMore details: " + ex.getMessage());
		 }
	 }
	 
    
	public void ListenForEvents() 
	{
		this.socket.on("register", new Emitter.Listener() {
			
			public void call(Object... args) {
				
				JSONObject register = (JSONObject)args[0];
				
				SOSDispatcher dispatch = new SOSDispatcher((String)args[1]);
				
				dispatch.Dispatch("create", 1, register);
				
			}
			
		});
		
	}
	
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
