package sosInterface;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.protocol.Packet;

import org.json.JSONObject;
import org.json.JSONArray;

import utils.EnumerationsAndConstant;

/** SOSServer communicates with the front-end for creation of events.
* Also it is held responsible for managing user sessions and keeping
* track of them, as well as dispatching messages through the system.
*/


public class SOSServer {
	
	private Configuration config;
	
	private SocketIOServer server;
	
	/**
 	* The constructor could be made private
 	* to prevent others from instantiating this
 	* class. But this would also make it
 	* impossible to create instances of
 	* SOSServer subclasses.
	*/
	public SOSServer(String hostName, int portNumber) throws Exception
	{ 
		try
		{
			config = new Configuration();
			
			config.setHostname(hostName);
			config.setPort(portNumber);
			
			server =  new SocketIOServer(config);
		}
		catch(Exception ex)
		{
			throw new Exception("There was an issue establishing the server.\nMore details: " + ex.getMessage());
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
	 static public SOSServer instance(String hostName, int portNumber) throws Exception
	 {
		 try
		 {
		 if (null == _instance) {
			 _instance = new SOSServer(hostName, portNumber);
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
		
		EnumerationsAndConstant enumeratoins = new EnumerationsAndConstant();
		
		server.addEventListener("userRegister", JSONObject.class, new DataListener<JSONObject>()
				{
					public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest)
					{
						System.out.println("User registration was requested");
						
						System.out.println("JSON Obj: " + json.toString());
						
						SOSDispatcher dispatcher = new SOSDispatcher(client, json, , "create");
						
						dispatcher.Dispatch();
					}
				});
		
		server.addEventListener("", JSONObject.class, new DataListener<JSONObject>()
				{
					public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest)
					{
						System.out.println("User load requested.");
						
						System.out.println("JSON Obj: " + json.toString());
						
						SOSDipsatcher dispatcher = new SOSDispatcher(client, json, 1, );
					}
				});
		server.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(SocketIOClient client) {
				// TODO Auto-generated method stub
				System.out.println("Someone is connected");
			}
			
		});
		
		server.start();
		
		try {
			server.wait(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		server.stop();
		
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
