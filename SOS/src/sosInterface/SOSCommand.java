package sosInterface;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.corundumstudio.socketio.SocketIOClient;

import event.EventManager;
import organization.OrganizationManager;
import sosInterface.SOSDispatcher.REQUEST_TYPES;
import sosInterface.socket.SOSEventListener;
import user.UserManager;

public abstract class SOSCommand {

	private static REQUEST_TYPES[] implemented = { 
			REQUEST_TYPES.CREATE_USER,
			REQUEST_TYPES.CREATE_ORG, 
			REQUEST_TYPES.RETR_ORGS,
			REQUEST_TYPES.RETR_ORG,
			REQUEST_TYPES.LOGIN,
			REQUEST_TYPES.LOAD_USER,
			REQUEST_TYPES.RETR_MEMBER_FOR_ORG,
			REQUEST_TYPES.RETR_EVENT_FOR_ORG,
			REQUEST_TYPES.CREATE_EVENT,
			REQUEST_TYPES.RETR_EVENTS_FOR_ORG,
			REQUEST_TYPES.RETR_ALL_EVENTS,
			REQUEST_TYPES.JOIN_ORG, 
			REQUEST_TYPES.SET_ROLE, 
			REQUEST_TYPES.EVENT_CANCEL,
			REQUEST_TYPES.RETR_ORGS_FOR_USER,
			REQUEST_TYPES.RETR_EVENTS_BY_LOCATION,			
	};
	
	protected SocketIOClient client;
	protected String errorStatus = null;
	protected JSONObject errorPayload = null;
	protected SOSCommand(SocketIOClient client) {
		this.client = client;
	};
	
	abstract public boolean execute() throws RuntimeException;
	
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
						public boolean execute() {
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
					public boolean execute() throws RuntimeException {
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
			case RETR_ORGS: {
				ret = new SOSCommand(client) {
					@Override
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = OrganizationManager.instance().getAllOrganizations();
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						
						try {

							JSONArray values = retPayload.getJSONArray("values");
							JSONArray split = new JSONArray();
							retPayload = new JSONObject();
							
							int startIndex = payload.getJSONObject("organization").getInt("startIndex");
							int count = payload.getJSONObject("organization").getInt("count");
							
							for(int i = startIndex; i < startIndex + count && i < values.length(); i++)
								split.put(values.get(i));	

							retPayload = new JSONObject();
							retPayload.put("org", split);
							retPayload.put("type", "updateOrgs");

						} catch (JSONException e) {
							e.printStackTrace();
						}

						this.succeedWith(retPayload);						
						return true;
					}
				};
				break;
			}
			case LOAD_USER: {
				ret = new SOSCommand(client) {
					@Override
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = UserManager.instance().LoadUser(payload);
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						
						try {
							retPayload.put("type", "successfulUserloaded");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						this.succeedWith(retPayload);						
						return true;
					}
				};
				break;
			}
			case CREATE_USER: {
				ret = new SOSCommand(client) {

					@Override
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = UserManager.instance().CreateNewProfile(payload);
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
			case RETR_ORG: {
				ret = new SOSCommand(client) {
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = OrganizationManager.instance().loadOrganizationDetails(payload);
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						
						try {
							retPayload.put("type", "succesfulOrgloaded");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						this.succeedWith(retPayload);						
						return true;
					}
				};
				break;
			}
			case RETR_MEMBER_FOR_ORG: {
				ret = new SOSCommand(client) {
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = UserManager.instance().getMembersOfOrganization(payload);
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						try {
							retPayload.put("type", "successMembersLoaded");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						this.succeedWith(retPayload);						
						return true;
					}
				};
				break;
			}
			case RETR_EVENTS_FOR_ORG: {
				ret = new SOSCommand(client) {
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = EventManager.instance().getEventOfOrganization(payload);
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						
						try {
							retPayload.put("type", "successOrgEventsLoaded");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						this.succeedWith(retPayload);						
						return true;
					}
				};
				break;
			} 
			case RETR_ALL_EVENTS: {
				ret = new SOSCommand(client) {
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = EventManager.instance().retrieveListOfEvents(payload);
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						
						try {
							retPayload.put("type", "successAllEventsLoaded");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						this.succeedWith(retPayload);						
						return true;
					}
				};
				break;
			} 
			case CREATE_EVENT:
				ret = new SOSCommand(client) {
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = EventManager.instance().createEvent(payload);
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						try {
							retPayload.put("type", "successEventCreation");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						this.succeedWith(retPayload);						
						return true;

					}
				};
				break;
			case JOIN_ORG: {
				ret = new SOSCommand(client) {
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = OrganizationManager.instance().joinOrganization(payload);
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						try {
							retPayload.put("type", "successJoinOrganization");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						this.succeedWith(retPayload);						
						return true;
					}
				};
				break;
			}
			case SET_ROLE: {
				ret = new SOSCommand(client) {
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = OrganizationManager.instance().grantRole(payload);
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						try {
							retPayload.put("type", "successGrantingRole");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						this.succeedWith(retPayload);						
						return true;
					}
				};
				break;		
			}
			case EVENT_CANCEL: {
				ret = new SOSCommand(client) {
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = EventManager.instance().cancelEvent(payload);
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						try {
							retPayload.put("type", "successEventCancel");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						this.succeedWith(retPayload);						
						return true;
					}
				};
				break;		
			}
			case RETR_ORGS_FOR_USER: {
				ret = new SOSCommand(client) {
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = OrganizationManager.instance().getAllOrganizations(payload);
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						
						try {
							retPayload.put("type", "successOrgForUserLoaded");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						this.succeedWith(retPayload);						
						return true;
					}
				};
				break;
			} 
			case RETR_EVENTS_BY_LOCATION: {
				ret = new SOSCommand(client) {
					public boolean execute() throws RuntimeException {
						JSONObject retPayload = EventManager.instance().retrieveListOfEventsByLocation(payload);
						if(retPayload.has("error")) {
							this.errorStatus = "argumentError";
							this.failWith(retPayload);
							return false;
						}
						
						try {
							retPayload.put("type", "successEventsByLocation");
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
