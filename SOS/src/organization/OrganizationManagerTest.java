package organization;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import security.TransferManager;
import user.UserManager;

// TODO: Auto-generated Javadoc
/**
 * The Class OrganizationManagerTest.
 */
class OrganizationManagerTest {

	/** The om. */
	OrganizationManager om;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		om = OrganizationManager.instance();
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		om = null;
	}
	
	/**
	 * Test instance.
	 */
	@Test
	void testInstance() {
		// Must return the same object if called twice.
		OrganizationManager s1 = OrganizationManager.instance();
		OrganizationManager s2 = OrganizationManager.instance();
		assertEquals("same instance", s1, s2);	}

	/**
	 * Test load organization details.
	 */
	@Test
	void testLoadOrganizationDetails() {

		try {
			JSONObject ret = null;
			// Invalid JSONObject, missing parameter
			ret = om.loadOrganizationDetails(new JSONObject("{something:else}"));
			assertEquals("error message", true, ret.has("error"));
			assertEquals("error message", "true", ret.get("error"));
			assertEquals("error message", true, ret.has("type"));
			assertEquals("error message", "orgidValueError", ret.get("type"));
			
			// No orgid, not found
			ret = om.loadOrganizationDetails(new JSONObject("{organization:{}}"));
			assertEquals("error message", true, ret.has("error"));
			assertEquals("error message", "true", ret.get("error"));
			assertEquals("error message", true, ret.has("type"));
			assertEquals("error message", "noSearchParameter", ret.get("type"));
			
			// Valid org id, found.
			ret = om.loadOrganizationDetails(new JSONObject("{organization:{organization_id:1}}"));
			assertEquals("error message", true, ret.has("user"));
			
			
			// 1	Fishing John's	A fishing club.	PUBLIC	Must own fishing rod
			JSONObject org = ret.getJSONObject("data");
			System.out.println(org.toString());
			assertEquals("error message", true, org.has("description"));
			assertEquals("error message", "A fishing club.", org.get("description"));
			assertEquals("error message", true, org.has("organization_id"));
			assertEquals("error message", 1, org.getInt("organization_id"));
			assertEquals("error message", true, org.has("name"));
			assertEquals("error message", "Fishing John's", org.get("name"));
			assertEquals("error message", true, org.has("privacy"));
			assertEquals("error message", "PUBLIC", org.get("privacy"));
			assertEquals("error message", true, org.has("requirement"));
			assertEquals("error message", "Must own fishing rod", org.get("requirement"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
