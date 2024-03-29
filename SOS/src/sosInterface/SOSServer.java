package sosInterface;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

import security.TransferManager;
import sosInterface.SOSDispatcher.REQUEST_TYPES;
import sosInterface.socket.SOSConnectListener;
import sosInterface.socket.SOSEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Constants;

// TODO: Auto-generated Javadoc
/** SOSServer communicates with the front-end for creation of events.
* Also it is held responsible for managing user sessions and keeping
* track of them, as well as dispatching messages through the system.
*/


public class SOSServer {
	
	/** The config. */
	private Configuration config;
	
	/** The server. */
	private SocketIOServer server;
	
	/** The lg. */
	private Logger lg;
	
	/**
	 * The constructor could be made private
	 * to prevent others from instantiating this
	 * class. But this would also make it
	 * impossible to create instances of
	 * SOSServer subclasses.
	 *
	 * @param hostName the host name
	 * @param portNumber the port number
	 * @throws Exception the exception
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
	 * Instance.
	 *
	 * @return The unique instance of this
	 * class.
	 * @throws Exception the exception
	 */
	 static public SOSServer instance() throws Exception
	 {
		 try
		 {
			 if (null == _instance) {
				 _instance = new SOSServer("0.0.0.0", Constants.SERVER_PORT);
			 }
			 return _instance;
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("Failed to retrieve the instance of the SOS server.\nMore details: " + ex.getMessage());
		 }
	 }
	 
    
	/**
	 * Starts the server and sets it to listen for events from a client
	 * socket.io front-end. 
	 */
	public void ListenForEvents() 
	{

		server.addEventListener("organizationLoadAll", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Load all organizations requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.RETR_ORGS, client, json);
			}
		});		
	
		server.addEventListener("organizationCreate", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Organization create requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.CREATE_ORG, client, json);				
			}
		});		
		
		server.addEventListener("userLogin", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Login Attempt.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.LOGIN, client, json);
			}
		});
		
		server.addEventListener("userRegister", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("User registration requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.CREATE_USER, client, json);
			}
		});

		server.addEventListener("userLoadUser", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("User registration requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.LOAD_USER, client, json);
			}
		});
		
		server.addEventListener("organizationLoadOrganization", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("User registration requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.RETR_ORG, client, json);
			}
		});		

		server.addEventListener("organizationLoadEventsFromOrganization", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Events from Organization Requested.");
				SOSDispatcher.getInstance().dispatch(REQUEST_TYPES.RETR_MEMBER_FOR_ORG, client, json);
			}
		});				
		
		server.addEventListener("organizationLoadUsersFromOrganization", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Members from Organization Requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.RETR_MEMBER_FOR_ORG, client, json);
			}
		});		
		
		server.addEventListener("eventCreate", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Event Creation Requested.");
				lg.info(json.toString());
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.CREATE_EVENT, client, json);
			}
		});	
				
		server.addEventListener("organizationLoadEventsFromOrganization", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Event Creation Requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.RETR_EVENTS_FOR_ORG, client, json);
			}
		});			
		
		server.addEventListener("eventLoadAllEvent", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Load all events requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.RETR_ALL_EVENTS, client, json);
			}
		});

		server.addEventListener("organizationJoin", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Join an Organization requested..");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.JOIN_ORG, client, json);
			}
		});
		
		server.addEventListener("organizationGrantRole", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Grant a Role requested..");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.SET_ROLE, client, json);
			}
		});
		
		server.addEventListener("eventCancellation", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Cancel Event Requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.EVENT_CANCEL, client, json);
			}
		});
		
		server.addEventListener("organizationLoadByUser", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Retrieve Orgs for User Requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.RETR_ORGS_FOR_USER, client, json);
			}
		});
		
		server.addEventListener("eventLoadEventLocation", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("eventLoadEventLocation Requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.RETR_EVENTS_BY_LOCATION, client, json);
			}
		});		

		server.addEventListener("eventLoadEvent", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("eventLoadEventLocation Requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.RETR_EVENT, client, json);
			}
		});		

		server.addEventListener("eventAttend", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Attending Event Requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.ATTEND_EVENT, client, json);
			}
		});		
		
		server.addEventListener("userUpdateProfile", String.class, new SOSEventListener() {
			@Override
			public void doOnData(SocketIOClient client, JSONObject json, AckRequest ackRequest) {
				lg.info("Attending Event Requested.");
				SOSDispatcher.getInstance().dispatch(SOSDispatcher.REQUEST_TYPES.UPDATE_USER, client, json);
			}
		});		
				
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
	
}
