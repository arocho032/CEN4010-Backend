package sosInterface;

import java.util.Stack;

import org.json.JSONObject;

import com.corundumstudio.socketio.SocketIOClient;

public class SOSDispatcher {

	public enum REQUEST_TYPES {
		CREATE_USER ("createUser"),
		CREATE_ORG ("createOrganization")
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
		if(command.exectute())
			this.executionHistory.push(command);
	}

}
