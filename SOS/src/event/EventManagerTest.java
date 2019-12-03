package event;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import user.UserManager;

// TODO: Auto-generated Javadoc
/**
 * The Class EventManagerTest.
 */
class EventManagerTest {

	/** The em. */
	EventManager em;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		em = EventManager.instance();
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		em = null;
	}

	
	/**
	 * Test instance.
	 */
	@Test
	void testInstance() {
		// Must return the same object if called twice.
		EventManager um1 = EventManager.instance();
		EventManager um2 = EventManager.instance();
		assertEquals("same instance", um1, um2);
	}

	/**
	 * Test load user.
	 */
	@Test
	void testLoadUser() {
		try {
			
			JSONObject ret = null;
			// Invalid JSONObject, missing parameter
			ret = em.loadEventDetails(new JSONObject("{something:else}"));
			assertEquals("error message", true, ret.has("error"));
			assertEquals("error message", "true", ret.get("error"));
			assertEquals("error message", true, ret.has("type"));
			assertEquals("error message", "payloadError", ret.get("type"));
			
			// Valid username, found.
			ret = em.loadEventDetails(new JSONObject("{event_id:6}"));
			assertEquals("error message", true, ret.has("data"));
			
			// event_id, description, date, is_cancelled, visibility, time, name, event_type, hosted_by, lat_coordinate, long_coordinate
			// 6	Come to fish and relax	2019-11-30	1	1	12:00:01	Fishing	1	1	10.34	21.31
			JSONObject loadedUser = ret.getJSONObject("data");
			assertEquals("error message", true, loadedUser.has("event_id"));
			assertEquals("error message", 6, loadedUser.getInt("event_id"));			
			assertEquals("error message", true, loadedUser.has("description"));
			assertEquals("error message", "Come to fish and relax", loadedUser.get("description"));
			assertEquals("error message", true, loadedUser.has("date"));
			assertEquals("error message", "2019-11-30", loadedUser.getString("date"));
			assertEquals("error message", true, loadedUser.has("is_cancelled"));
			assertEquals("error message", true, loadedUser.get("is_cancelled"));
			assertEquals("error message", true, loadedUser.has("visibility"));
			assertEquals("error message", 1, loadedUser.getInt("visibility"));
			assertEquals("error message", true, loadedUser.has("time"));
			assertEquals("error message", "12:00:01", loadedUser.get("time"));
			assertEquals("error message", true, loadedUser.has("name"));
			assertEquals("error message", "Fishing", loadedUser.get("name"));
			assertEquals("error message", true, loadedUser.has("event_type"));
			assertEquals("error message", 1, loadedUser.getInt("event_type"));
			assertEquals("error message", true, loadedUser.has("hosted_by"));
			assertEquals("error message", 1, loadedUser.getInt("hosted_by"));
			assertEquals("error message", true, loadedUser.has("lat_coordinate"));
			assertEquals("error message", 10.34, loadedUser.getDouble("lat_coordinate"));
			assertEquals("error message", true, loadedUser.has("long_coordinate"));
			assertEquals("error message", 21.31, loadedUser.getDouble("long_coordinate"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
