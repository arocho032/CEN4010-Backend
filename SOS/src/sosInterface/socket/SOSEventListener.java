package sosInterface.socket;

import org.json.JSONException;
import org.json.JSONObject;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;

import security.TransferManager;

// TODO: Auto-generated Javadoc
/**
 * Internal class extending a Socket.IO class to wrap the whole connection within
 * an encryption mechanism.
 *
 * @see SOSEventEvent
 */
public abstract class SOSEventListener implements DataListener<String> {
	
	/**
	 * Decrypts on data.
	 *
	 * @param client the client
	 * @param cipher the cipher
	 * @param ackRequest the ack request
	 */
	public void onData(final SocketIOClient client, String cipher, final AckRequest ackRequest) {
		
		try {
			String plain = TransferManager
								.instance()
								.decryptMessage(new JSONObject(cipher));
			this.doOnData(client, new JSONObject(plain), ackRequest);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Encrypts the given data and sends it to the client.
	 * @param client
	 * 		the client target.
	 * @param data
	 * 		the plaintext JSON data.
	 */
	public static void doSendEvent(SocketIOClient client, JSONObject data) {
		JSONObject payload = TransferManager.instance().encryptMessage(data.toString(), client.getSessionId().toString());
		client.sendEvent("action", payload.toString());	
			
	}
	
	/**
	 * Implemented by the event-specific data listener so it can work afte decrypting. For more information, 
	 * read the javadoc for the netty-socket.io DataListener<>() class.
	 *
	 * @param client the client
	 * @param json the json
	 * @param ackRequest the ack request
	 */
	public abstract void doOnData(final SocketIOClient client, JSONObject json, final AckRequest ackRequest);
	
}
