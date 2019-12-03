package sosInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import user.UserManager;

// TODO: Auto-generated Javadoc
/**
 * The Class SOSServerTest.
 */
class SOSServerTest {

	/**
	 * Test instance.
	 */
	@Test
	void testInstance() {
		// Must return the same object if called twice.
		try {
			SOSServer s1 = SOSServer.instance();
			SOSServer s2 = SOSServer.instance();
			assertEquals("same instance", s1, s2);;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
