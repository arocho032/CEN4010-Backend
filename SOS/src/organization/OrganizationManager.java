package organization;

import storage.DataStoreFacade;
import utils.JSONTranslator;

import org.json.*;

/**
 * A Singleton which manages all the Organization functions. This 
 * class receives dispatched actions from the SOS Dispatcher and 
 * completes that action using objects internal to its subsystem. 
 * It also is in charge of interacting with the SOS Data Store 
 * Façade directly. Part of the role of this class is to parse 
 * front-end format data (e.g., JSON-String description of new 
 * Organization) and calling the appropriate functions on other 
 * classes according to that data. Another job of this class is 
 * to manage Role creation and assignment, as well as mediate the 
 * modification of data in an Organization, and of Event hosting.
 */
public class OrganizationManager {

	private DataStoreFacade ds;
	
	/**
 	* A protected or private constructor ensures
 	* that no other class has access to the Singleton.
	*/
	protected OrganizationManager() {
		try {
			this.ds = new DataStoreFacade();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

 	/**
  	 * A handle to the unique OrganizationManager
  	* instance.
 	*/
 	static private OrganizationManager _instance = null;
	
	/**
	 * @return The unique instance of this
	 * class.
	 */
	 static public OrganizationManager instance() {
		 if ( null == _instance) {
			 _instance = new OrganizationManager();
		 }
		 return _instance;
	 }
	 
	 /**
	  * Grants a number of privileges to a User for a given Organization.
	  * @param userId
	  * 	the unique id of the User 
	  * @param orgId
	  * 	the unique id of the Organization
	  * @param roleName
	  * 	the name of the assigned Role.
	  * @param privs
	  * 	the unique ids of the Privileges given to the User.
	  */
	 public void grantRole(int userId, int orgId, String roleName, JSONArray privs) throws Exception
	 {
		 try
		 {
			 DataStoreFacade ds = new DataStoreFacade();
			 
			 boolean[] privMatrix = new boolean[5];
			 
			 for(int index = 0; index < privs.length(); index++)
			 {
				 privMatrix[index] = privs.getBoolean(index);
			 }
			 
			 ds.addNewRoleToOrganization(roleName, orgId, userId, privMatrix);
			 
			 ds.terminateConnection();
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("There was an error while attempting to grant the new role to the user.\nMore detials: " +ex.getMessage());
		 }
		 
	 }
	 	 
	 /**
	  * Creates an organization in the SOS System.
	  * @param json2 A JSON string that has information about the organization we want to create.
	  * @throws Exception Throws an exception if their is a failure to create an organization.
	  */
	 public JSONObject createOrganization(JSONObject json)
	 {
			JSONObject errorPayload = null;
		 	OrganizationBuilder builder = new OrganizationBuilder();
			try {
				builder.setName(json.getJSONObject("organization").getString("name"))
				.setDescription(json.getJSONObject("organization").getString("description"));
				
				try {
					builder.setPrivacy(json.getJSONObject("organization").getString("privacy"));			
				} catch (IllegalArgumentException e) {
					errorPayload = new JSONObject();					
					errorPayload.put("type", "organizationCreationValueError");
					errorPayload.put("variable", "privacy");
				} finally {
					builder.setPrivacy("PUBLIC");
				}
				
				try {
					builder.setRequirements(json.getJSONObject("organization").getString("requirements"));
				} catch (IllegalArgumentException e) {
					errorPayload = new JSONObject();					
					errorPayload.put("type", "organizationCreationValueError");
					errorPayload.put("variable", "privacy");
				}
				
//				try {
//					String username = json.getJSONObject("organization").getString("user");
//					
//					builder.setOwner();	
//				} catch (IllegalArgumentException e) {
//					
//				}
				
				
				Organization org = null;
				if(!builder.isNotComplete())
					org = builder.build();
				
//				ds.createNewOrganization(name, description, privacy, requirements, userID);
				
			} catch(Exception ex) {
				errorPayload = new JSONObject();
				try {
					errorPayload.put("type", "generalException");
					errorPayload.put("payload", "An error occurred while attempting to create a new organization.\nMore details: "  + ex.getMessage());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				ex.printStackTrace();
			} 
			return errorPayload;
	 }
	 
	 /**
	  * Loads details for the requested organization.
	  * @param organizationID The ID of the organization which the SOS system is asking for.
	  * @throws Exception Throws an exception if the organization is not found within the database.
	  */
	 public JSONObject loadOrganizationDetails(int organizationID) throws Exception
	 {
		 try
		 {
			 DataStoreFacade ds = new DataStoreFacade();
			 
			 OrganizationLoader loader = new OrganizationLoader();
			 
			 return loader.LoadOrganization(ds.retrieveOrganizationDetails(organizationID)).getJSONObject();
			 
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("An error occurred while attempting to load the details for the organization.\nMore information: " + ex.getMessage());
		 }
	 }
	 
	 /**
	  * Gets all the public organizations in the SOS.
	  * @return A JSONArray with all the public organizations stored in the SOS.
	  * @throws Exception Throws an exception if their was an error retrieving the SOS public organizations.
	  */
	 public JSONObject getAllOrganizations() 
	 { 
		JSONObject retPayload = new JSONObject();
		try {
			JSONArray array = JSONTranslator.resultSetToJSONArray(this.ds.retrievePublicOrganizations());
			 retPayload.put("values", array);
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				retPayload.put("error", "true");
				retPayload.put("type", "generalException");
				retPayload.put("payload", "An error occurred while attempting to find all events for the user.\nMore Details: " + ex.getMessage());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return retPayload;
	 }
	 
	 /**
	  * Gets all the organizations which a user currently belongs to.
	  * @param userID The user that we want to retrieve the organizations for.
	  * @return A JSONObject with all the organizations the user is a part of.
	  * @throws Exception Throws an exception if there was an error retrieving the organizations for the user.
	  */
	 public JSONObject getAllOrganizations(int userID)
	 {
		JSONObject retPayload = new JSONObject();
		try {
			JSONArray array = JSONTranslator.resultSetToJSONArray(this.ds.retrieveOrganizationsForUser(userID));
			 retPayload.put("values", array);
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				retPayload.put("error", "true");
				retPayload.put("type", "generalException");
				retPayload.put("payload", "An error occurred while attempting to find all events for the user.\nMore Details: " + ex.getMessage());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return retPayload;
	 }
	 
	 /**
	  * Allows the user to join an organization that is part of SOS.
	  * @param userID The ID of the user that wants to join the organization.
	  * @param organizationID The ID of the organization that the user wants to join.
	  * @throws Exception Throws an exception if there was an error preventing the user from joining said organization.
	  */
	 public void joinOrganization(int userID, int organizationID) throws Exception
	 {
		 try
		 {
			 DataStoreFacade ds = new DataStoreFacade();
			 
			 ds.joinOrganization(userID, organizationID);
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("The user could not join the organization.\nMore details: " + ex.getMessage());
		 }
	 }
	
}
