package sosInterface.socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;

import security.TransferManager;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving SOSConnect events.
 * The class that is interested in processing a SOSConnect
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSOSConnectListener<code> method. When
 * the SOSConnect event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SOSConnectEvent
 */
public class SOSConnectListener implements ConnectListener {
	
	/** The lg. */
	Logger lg = LoggerFactory.getLogger(SOSConnectListener.class);
	
	/**
	 * On connect.
	 *
	 * @param client the client
	 */
	@Override
	public void onConnect(SocketIOClient client) {
		
		try {
			JSONObject trans = new JSONObject();
			String cert = TransferManager.instance().getSharableCertificate();
			JSONObject data = new JSONObject();
			data.put("certificate", cert);
			
			trans.put("type", "sos/app/ON_HANDSHAKE_REQUEST");
			trans.put("data", data.toString());
			
			AckCallback<String> cback = new AckCallback<String>(String.class) {
				@Override
				public void onSuccess(String result) {
					lg.info("Got AckCallback");					
					TransferManager.instance().setCertificateEntry(result, client.getSessionId().toString());
				}
			};			

			client.sendEvent("action", cback, trans.toString());
			lg.info("New client conencted to the server.");
									
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
