package sosInterface;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

import security.TransferManager;
import sosInterface.socket.SOSConnectListener;
import sosInterface.socket.SOSEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.EnumerationsAndConstant;

/** SOSServer communicates with the front-end for creation of events.
* Also it is held responsible for managing user sessions and keeping
* track of them, as well as dispatching messages through the system.
*/


public class SOSServer {
	
	private Configuration config;
	private SocketIOServer server;
	
	private Logger lg;
	
	/**
 	* The constructor could be made private
 	* to prevent others from instantiating this
 	* class. But this would also make it
 	* impossible to create instances of
 	* SOSServer subclasses.
	*/
	private SOSServer(String hostName, int portNumber) throws Exception
	{ 
		try
		{
			config = new Configuration();
			config.setHostname(hostName);
			config.setPort(portNumber);
			config.setOrigin("http://localhost:3000");
			server =  new SocketIOServer(config);
			lg = LoggerFactory.getLogger(SOSServer.class);
		}
		catch(Exception ex)
		{
			lg.error("There was an issue establishing the server.\nMore details: ", ex);
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

		server.addEventListener("organizationCreate", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Organization create requested.");
//				lg.info("Got message JSON: " + json.toString());	
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.CREATE_ORG, client, json);				
			}
		});		
		
		server.addEventListener("userLogin", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Login Attempt.");
				lg.info("Got message JSON: " + json.toString());
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.LOGIN, client, json);
			}
		});
		
		server.addEventListener("organizationLoadAll", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Load all organizations requested.");
				lg.info("Got message JSON: " + json.toString());
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.RETR_ORG, client, json);
			}
		});
		
//		server.addEventListener("userRegister", String.class, new SOSEventListener() {
//			@Override
//			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
//				lg.info("User registration requested.");
//				lg.info("Got message JSON: " + json.toString());
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "create");
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.USER);
//				
//				try {
//					JSONObject data = new JSONObject();
//					data.put("result", "Added Correctly");
//					SOSEventListener.doSendEvent(client, data);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//					
//			}
//		});
//	
//		server.addEventListener("userLoadUser", JSONObject.class, new DataListener<JSONObject>() {
//			public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest) {
//				lg.info("User load requested.");
//				lg.info("Got message JSON: " + json.toString());
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "load" );
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.USER);
//			}
//		});
//		
//		server.addEventListener("userUpdateProfile", JSONObject.class, new DataListener<JSONObject>() {
//			public void  onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest) {
//				lg.info("User update requested.");
//				lg.info("Got message JSON: " + json.toString());
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "update");				
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.USER);
//			}
//		});
//		
//		server.addEventListener("eventCreate", JSONObject.class, new DataListener<JSONObject>() {
//			public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest) {				
//				lg.info("Event create requested.");
//				lg.info("Got message JSON: " + json.toString());
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "create");
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.EVENT);
//			}
//		});
//		
//		server.addEventListener("eventLoadEvent", JSONObject.class, new DataListener<JSONObject>() {
//			public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest) {
//				lg.info("Load chosen event requested.");
//				lg.info("Got message JSON: " + json.toString());
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "loadOne");
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.EVENT);
//			}
//		});
//		
//		server.addEventListener("eventLoadAllEvent", JSONObject.class, new DataListener<JSONObject>() {
//			public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest) {
//				lg.info("Load all events requested.");
//				lg.info("Got message JSON: " + json.toString());				
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "loadAll");
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.EVENT);
//			}
//		});
//		
//		server.addEventListener("eventLoadEventLocation", JSONObject.class, new DataListener<JSONObject>() {
//			public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest) {
//				lg.info("Load events by location requested.");
//				lg.info("Got message JSON: " + json.toString());	
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "loadByLocation");
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.EVENT);
//			}
//		});
//		
//		server.addEventListener("eventCancellation", JSONObject.class, new DataListener<JSONObject>() {
//			public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest) {
//				lg.info("Event cancellation requested.");
//				lg.info("Got message JSON: " + json.toString());
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "cancel");
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.EVENT);
//			}
//		});
//		
//		server.addEventListener("eventAttend", JSONObject.class, new DataListener<JSONObject>() {
//			public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest) {
//				lg.info("Event attendance requested.");
//				lg.info("Got message JSON: " + json.toString());	
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "attend");
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.EVENT);
//			}
//		});
//		
//		
//		server.addEventListener("organizationLoadOrganization", JSONObject.class, new DataListener<JSONObject>() { 
//			public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest) {
//				lg.info("Load selected organization requested.");
//				lg.info("Got message JSON: " + json.toString());	
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "loadOne");
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.ORGANIZATION);
//			}
//		});
//		
//		
//		server.addEventListener("organizationLoadByUser", JSONObject.class, new DataListener<JSONObject>() {
//			public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest) {
//				lg.info("Load all organizations by user requested.");
//				lg.info("Got message JSON: " + json.toString());	
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "loadByUser");
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.ORGANIZATION);
//			}
//		});
//		
//		server.addEventListener("organizationJoin", JSONObject.class, new DataListener<JSONObject>() {
//			public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest) {
//				lg.info("Join organization requested.");
//				lg.info("Got message JSON: " + json.toString());	
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "join");
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.ORGANIZATION);
//			}
//		});
//		
//		server.addEventListener("organizationGrantRole", JSONObject.class, new DataListener<JSONObject>() {
//			public void onData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest) {
//				lg.info("granting new role requested.");
//				lg.info("Got message JSON: " + json.toString());	
//				SOSDispatcher_Old dispatcher = new SOSDispatcher_Old(client, json, "grantRole");
//				dispatcher.Dispatch(EnumerationsAndConstant.REQUEST_TYPE.ORGANIZATION);
//			}
//		});
//		
		server.addConnectListener(new SOSConnectListener());

		
		server.start();
		
		try {
			synchronized(server) {
				server.wait(Integer.MAX_VALUE);
			}
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
