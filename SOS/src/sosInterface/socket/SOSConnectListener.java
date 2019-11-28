package sosInterface.socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;

public class SOSConnectListener implements ConnectListener {
	
	Logger lg = LoggerFactory.getLogger(SOSConnectListener.class);
	
	@Override
	public void onConnect(SocketIOClient client) {
		
		try {
			JSONObject data = new JSONObject();
			data.put("type", "sos/app/ON_HANDSHAKE_REQUEST");	
			client.sendEvent("action", data.toString());
			lg.info("New client conencted to the server.");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
