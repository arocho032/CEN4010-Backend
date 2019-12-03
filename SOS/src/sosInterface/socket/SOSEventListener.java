package sosInterface.socket;

import org.json.JSONException;
import org.json.JSONObject;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;

import security.TransferManager;

public abstract class SOSEventListener implements DataListener<String> {
	
	public void onData(final SocketIOClient client, String cipher, final AckRequest ackRequest) {
		
		try {
			String plain = TransferManager
								.instance()
								.decryptMessage(new JSONObject(cipher));
			this.doOnData(client, new JSONObject(plain), ackRequest);
			
			System.out.println(cipher);
			System.out.println(plain);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}

	public static void doSendEvent(SocketIOClient client, JSONObject data) {
		JSONObject payload = TransferManager.instance().encryptMessage(data.toString(), client.getSessionId().toString());
		client.sendEvent("action", payload.toString());	
		
//		client.sendEvent("action", data.toString());
	
	}
	
	public abstract void doOnData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest);
	
}
