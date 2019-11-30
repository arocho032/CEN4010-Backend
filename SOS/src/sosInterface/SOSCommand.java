package sosInterface;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.corundumstudio.socketio.SocketIOClient;

import organization.OrganizationManager;
import sosInterface.SOSDispatcher.REQUEST_TYPES;
import sosInterface.socket.SOSEventListener;
import user.UserManager;

public abstract class SOSCommand {

	private static REQUEST_TYPES[] implemented = { 
			REQUEST_TYPES.CREATE_ORG, 
			REQUEST_TYPES.RETR_ORG,
			REQUEST_TYPES.LOGIN
	};
	
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
	
	protected void succeedWith(JSONObject successPayload) {
		SOSEventListener.doSendEvent(client, successPayload);
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
			case LOGIN: {
				ret = new SOSCommand(client) {
					@Override
					public boolean exectute() throws RuntimeException {
						JSONObject retPayload = UserManager.instance().login(payload);
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						this.succeedWith(retPayload);
						return true;
					}
				};
				break;
			}
			case RETR_ORG: {
				ret = new SOSCommand(client) {
					@Override
					public boolean exectute() throws RuntimeException {
						JSONObject retPayload = OrganizationManager.instance().getAllOrganizations();
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						
						try {

							System.out.println("Trace here.");
							JSONArray values = retPayload.getJSONArray("values");
							JSONArray split = new JSONArray();
							retPayload = new JSONObject();
							System.out.println("Trace here.");
							
							int startIndex = payload.getJSONObject("organization").getInt("startIndex");
							int count = payload.getJSONObject("organization").getInt("count");
							
							for(int i = startIndex; i < startIndex + count && i < values.length(); i++)
								split.put(values.get(i));	

							System.out.println("Trace here.");
							retPayload = new JSONObject();
							retPayload.put("org", split);
							retPayload.put("type", "updateOrgs");
							System.out.println(retPayload);


						} catch (JSONException e) {
							e.printStackTrace();
						}

						this.succeedWith(retPayload);						
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
