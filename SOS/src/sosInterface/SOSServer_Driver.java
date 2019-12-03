package sosInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Constants;

public class SOSServer_Driver {

	public static void main(String[] args) {
		
		Logger lg = LoggerFactory.getLogger(SOSServer_Driver.class);
		lg.info("Starting the SOS Server.");
	
		try {
			SOSServer server = SOSServer.instance("0.0.0.0", Constants.SERVER_PORT);	
			server.ListenForEvents();
		} catch(Exception ex) {
			lg.error("Error:", ex);
			return;
		}

	}

}
