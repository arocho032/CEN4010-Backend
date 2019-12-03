package sosInterface;

import java.util.Stack;

import org.json.JSONObject;

import com.corundumstudio.socketio.SocketIOClient;

// TODO: Auto-generated Javadoc
/**
 * The Class SOSDispatcher.
 */
public class SOSDispatcher {

	/**
	 * The Enum REQUEST_TYPES.
	 */
	public enum REQUEST_TYPES {
		
		/** The create user. */
		CREATE_USER,
		
		/** The create org. */
		CREATE_ORG,
		
		/** The retr orgs. */
		RETR_ORGS,
		
		/** The retr org. */
		RETR_ORG,
		
		/** The login. */
		LOGIN,
		
		/** The load user. */
		LOAD_USER, 
		
		/** The retr member for org. */
		RETR_MEMBER_FOR_ORG, 
		
		/** The retr event for org. */
		RETR_EVENT_FOR_ORG, 
		
		/** The create event. */
		CREATE_EVENT, 
		
		/** The retr events for org. */
		RETR_EVENTS_FOR_ORG, 
		
		/** The retr all events. */
		RETR_ALL_EVENTS, 
		
		/** The join org. */
		JOIN_ORG, 
		
		/** The set role. */
		SET_ROLE, 
		
		/** The event cancel. */
		EVENT_CANCEL, 
		
		/** The retr orgs for user. */
		RETR_ORGS_FOR_USER, 
		
		/** The retr events by location. */
		RETR_EVENTS_BY_LOCATION, 
		
		/** The retr event. */
		RETR_EVENT, 
		
		/** The attend event. */
		ATTEND_EVENT,
		
		/** The update user. */
		UPDATE_USER
		;		
	}
	
	/** The instance. */
	static private SOSDispatcher _instance = null;
	
	/** The execution history. */
	private Stack<SOSCommand> executionHistory;
	
	/**
	 * Creates new dispatcher.
	 */
	protected SOSDispatcher() {
		this.executionHistory = new Stack<>();
	}
	
	/**
	 * Returns the Singleton dispatcher instance.
	 * @return
	 * 		the unique SOSDispatcher. 
	 */
	static public SOSDispatcher getInstance() {
		if(_instance == null)
			_instance = new SOSDispatcher();
		return _instance;
	}
	
	/**
	 * Dispatches an action by creating and executing an SOSCommand.
	 * @param request
	 * 		the request.
	 * @param client
	 * 		the client to return the request action.
	 * @param payload
	 * 		the payload of the request.
	 */
	public void dispatch(REQUEST_TYPES request, SocketIOClient client, JSONObject payload) {
		SOSCommand command = SOSCommand.createCommand(request, client, payload);
		if(command.execute())
			this.executionHistory.push(command);
	}

}
