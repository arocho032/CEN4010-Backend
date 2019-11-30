package sosInterface;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import com.corundumstudio.socketio.SocketIOClient;

import organization.OrganizationManager;
import sosInterface.SOSDispatcher.REQUEST_TYPES;
import sosInterface.socket.SOSEventListener;

public abstract class SOSCommand {

	private static REQUEST_TYPES[] implemented = { REQUEST_TYPES.CREATE_ORG };
	
	protected SocketIOClient client;
	protected String errorStatus = null;
	protected JSONObject errorPayload = null;
	protected SOSCommand(SocketIOClient client) {
		this.client = client;
	};
	
	abstract public boolean exectute() throws RuntimeException;
	
	public String errorStatus() {
		return this.errorStatus;
	}
	
	protected void failWith(JSONObject errorPayload) {
		SOSEventListener.doSendEvent(client, errorPayload);
	}
	
	protected void succeedWith(JSONObject successPauload) {
		SOSEventListener.doSendEvent(client, successPauload);
	}
	
	public static SOSCommand createCommand(REQUEST_TYPES request, SocketIOClient client, JSONObject payload) {
		if(!Arrays.asList(implemented).contains(request))
			throw new IllegalArgumentException("Request not implemented.");
		SOSCommand ret = null;
		switch(request) {
			case CREATE_ORG: {
				ret = new SOSCommand(client) {
						public boolean exectute() {
							JSONObject retPayload = OrganizationManager.instance().createOrganization(payload);
							if(!(retPayload == null)) {
								this.errorStatus = "argumentError";
								this.failWith(retPayload);
								return false;
							}
							retPayload = new JSONObject();
							try {
								retPayload.put("type", "successCreatingOrganization");
								this.succeedWith(retPayload);							
							} catch (JSONException e) {
								e.printStackTrace();
							}
							return true;
						}
					};
				break;
			}
			default:
				break;
		}
	return ret;
	}
	
}
