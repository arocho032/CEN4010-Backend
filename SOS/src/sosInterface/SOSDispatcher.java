package sosInterface;

import java.util.Stack;

import org.json.JSONObject;

import com.corundumstudio.socketio.SocketIOClient;

public class SOSDispatcher {

	public enum REQUEST_TYPES {
		CREATE_USER ("createUser"),
		CREATE_ORG ("createOrganization"),
		RETR_ORGS ("retrieveOrganizations"),
		RETR_ORG ("retrieveOrganizations"),
		LOGIN ("login"),
		LOAD_USER ("loadUser"), 
		RETR_MEMBER_FOR_ORG ("retrMemberForOrg"), 
		RETR_EVENT_FOR_ORG ("retrEventForOrg"), 
		CREATE_EVENT ("createEvent"),
		;
		
		public final String code;
		REQUEST_TYPES(String code) {
			this.code = code;
		}
		
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
