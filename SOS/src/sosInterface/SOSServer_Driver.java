package sosInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class SOSServer_Driver.
 */
public class SOSServer_Driver {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		Logger lg = LoggerFactory.getLogger(SOSServer_Driver.class);
		lg.info("Starting the SOS Server.");
	
		try {
			SOSServer server = SOSServer.instance();	
			server.ListenForEvents();
		} catch(Exception ex) {
			lg.error("Error:", ex);
			return;
		}

	}

}
