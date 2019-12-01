package sosInterface;

import java.util.Stack;

import org.json.JSONObject;

import com.corundumstudio.socketio.SocketIOClient;

public class SOSDispatcher {

	public enum REQUEST_TYPES {
		CREATE_USER,
		CREATE_ORG,
		RETR_ORGS,
		RETR_ORG,
		LOGIN,
		LOAD_USER, 
		RETR_MEMBER_FOR_ORG, 
		RETR_EVENT_FOR_ORG, 
		CREATE_EVENT, 
		RETR_EVENTS_FOR_ORG, 
		RETR_ALL_EVENTS, 
		JOIN_ORG, 
		SET_ROLE, 
		EVENT_CANCEL, RETR_ORGS_FOR_USER, RETR_EVENTS_BY_LOCATION,
		;		
	}
	
	static private SOSDispatcher _instance = null;
	private Stack<SOSCommand> executionHistory;
	
	protected SOSDispatcher() {
		this.executionHistory = new Stack<>();
	}
	
	static public SOSDispatcher getInstance() {
		if(_instance == null)
			_instance = new SOSDispatcher();
		return _instance;
	}
	
	public void dispatch(REQUEST_TYPES request, SocketIOClient client, JSONObject payload) {
		SOSCommand command = SOSCommand.createCommand(request, client, payload);
		if(command.execute())
			this.executionHistory.push(command);
	}

}
