package user;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class UserManagerTest.
 */
class UserManagerTest {

	/** The um. */
	UserManager um;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		um = UserManager.instance();
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		um = null;
	}

	/**
	 * Test user manager.
	 */
	@Test
	void testUserManager() {
		// Must have an non-null DataStoreFacade, UserLoader, and UserUpdater.
		UserManager um1 = new UserManager();
		assertNotNull(um1.ds);
		assertNotNull(um1.ul);
		assertNotNull(um1.up);
	}

	/**
	 * Test instance.
	 */
	@Test
	void testInstance() {
		// Must return the same object if called twice.
		UserManager um1 = UserManager.instance();
		UserManager um2 = UserManager.instance();
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
			ret = um.LoadUser(new JSONObject("{something:else}"));
			assertEquals("error message", true, ret.has("error"));
			assertEquals("error message", "true", ret.get("error"));
			assertEquals("error message", true, ret.has("type"));
			assertEquals("error message", "usernameValueError", ret.get("type"));
			
			// Invalid Username, not found
			ret = um.LoadUser(new JSONObject("{user:{username:nothere}}"));
			assertEquals("error message", true, ret.has("error"));
			assertEquals("error message", "true", ret.get("error"));
			assertEquals("error message", true, ret.has("type"));
			assertEquals("error message", "usernotfoundError", ret.get("type"));
			
			// Valid username, found.
			ret = um.LoadUser(new JSONObject("{user:{username:jdoe001}}"));
			assertEquals("error message", true, ret.has("user"));
			
			JSONObject loadedUser = ret.getJSONObject("user");
			System.out.println(loadedUser.toString());
			assertEquals("error message", true, loadedUser.has("password"));
			assertEquals("error message", "password123", loadedUser.get("password"));
			assertEquals("error message", true, loadedUser.has("user_id"));
			assertEquals("error message", 2, loadedUser.getInt("user_id"));
			assertEquals("error message", true, loadedUser.has("user_name"));
			assertEquals("error message", "jdoe001", loadedUser.get("user_name"));
			assertEquals("error message", true, loadedUser.has("name"));
			assertEquals("error message", "John", loadedUser.get("name"));
			assertEquals("error message", true, loadedUser.has("privacy"));
			assertEquals("error message", "PUBLIC", loadedUser.get("privacy"));
			assertEquals("error message", true, loadedUser.has("email"));
			assertEquals("error message", "test@gmail.com", loadedUser.get("email"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test login.
	 */
	@Test
	void testLogin() {
		try {
			
			JSONObject ret = null;
			// Invalid JSONObject, missing parameter
			ret = um.login(new JSONObject("{something:else}"));
			assertEquals("error message", true, ret.has("error"));
			assertEquals("error message", "true", ret.get("error"));
			assertEquals("error message", true, ret.has("type"));
			assertEquals("error message", "loginValueError", ret.get("type"));
			
			// Invalid Username, not found
			ret = um.login(new JSONObject("{user:{username:nothere,password:notthere}}"));
			System.out.println(ret.toString());
			assertEquals("error message", true, ret.has("error"));
			assertEquals("error message", "true", ret.get("error"));
			assertEquals("error message", true, ret.has("type"));
			assertEquals("error message", "usernotfoundError", ret.get("type"));
			
			// Valid username, wrong password.
			ret = um.login(new JSONObject("{user:{username:jdoe001,password:notthere}}"));
			assertEquals("error message", true, ret.has("error"));
			assertEquals("error message", "true", ret.get("error"));
			assertEquals("error message", true, ret.has("type"));
			assertEquals("error message", "invalidcredentials", ret.get("type"));

			// Valid username, Valid password.
			ret = um.login(new JSONObject("{user:{username:jdoe001,password:password123}}"));
			assertEquals("correct message", true, ret.has("type"));
			assertEquals("correct message", "doLogin", ret.get("type"));
			assertEquals("correct message", true, ret.has("user"));
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test change user details.
	 */
	@Test
	void testChangeUserDetails() {
		try {
			JSONObject ret = null;
			// Invalid JSONObject, missing parameter
			ret = um.ChangeUserDetails(new JSONObject("{something:else}"));
			assertEquals("error message", true, ret.has("error"));
			assertEquals("error message", "true", ret.get("error"));
			assertEquals("error message", true, ret.has("type"));
			assertEquals("error message", "userupdateValueError", ret.get("type"));
			
			// Invalid user id, not found
			ret = um.ChangeUserDetails(new JSONObject("{user_id:1,password:notthere}"));
			assertEquals("error message", true, ret.has("error"));
			assertEquals("error message", "true", ret.get("error"));
			assertEquals("error message", true, ret.has("type"));
			assertEquals("error message", "usernotfoundError", ret.get("type"));
			
			// Valid user id, wrong password.
			ret = um.ChangeUserDetails(new JSONObject("{user_id:2,password:notthere}"));
			assertEquals("error message", null, ret);

			// Valid username, Valid password, Update
			ret = um.ChangeUserDetails(new JSONObject("{user_id:2,password:notthere,update:{}}"));
			assertEquals("correct message", "{}", ret.toString());
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test get members of organization.
	 */
	@Test
	void testGetMembersOfOrganization() {
		try {
			JSONObject ret = null;
			// Invalid JSONObject, missing parameter
			ret = um.getMembersOfOrganization(new JSONObject("{something:else}"));
			assertEquals("error message", true, ret.has("error"));
			assertEquals("error message", "true", ret.get("error"));
			assertEquals("error message", true, ret.has("type"));
			assertEquals("error message", "orgidValueError", ret.get("type"));
			
			// Invalid org id, not found
			ret = um.getMembersOfOrganization(new JSONObject("{organization:{}}"));
			assertEquals("error message", true, ret.has("error"));
			assertEquals("error message", "true", ret.get("error"));
			assertEquals("error message", true, ret.has("type"));
			assertEquals("error message", "noSearchParameter", ret.get("type"));

			// Invalid org id, returns empty.
			ret = um.getMembersOfOrganization(new JSONObject("{organization:{organization_id:20, startIndex:0}}"));
			System.out.print(ret);
			assertEquals("error message", true, ret.has("data"));
			assertEquals("error message", 0, ret.getJSONArray("data").length());
			
			// Valid org id, contains:
			// 2	password123	test@gmail.com	John	jdoe001	PUBLIC
			ret = um.getMembersOfOrganization(new JSONObject("{organization:{organization_id:21, startIndex:0}}"));
			assertEquals("correct message", true, ret.has("data"));
			assertEquals("correct message", 1, ret.getJSONArray("data").length());
			assertEquals("correct message", 2, ret.getJSONArray("data").getJSONObject(0).get("user_id"));
						
		} catch (Exception e) {
			e.printStackTrace();
		}	}

}
