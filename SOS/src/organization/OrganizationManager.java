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

	/**
 	* A protected or private constructor ensures
 	* that no other class has access to the Singleton.
	*/
	protected OrganizationManager() {}

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
	  * @param privIds
	  * 	the unique ids of the Privileges given to the User.
	  */
	 public void grantRole(int userId, int orgId, String roleName, boolean[] privIds) throws Exception
	 {
		 try
		 {
			 DataStoreFacade ds = new DataStoreFacade();
			 
			 ds.addNewRoleToOrganization(roleName, orgId, userId, privIds);
			 
			 ds.terminateConnection();
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("There was an error while attempting to grant the new role to the user.\nMore detials: " +ex.getMessage());
		 }
		 
	 }
	 
	 /**
	  * Creates an organization in the SOS System.
	  * @param jsonString A JSON string that has information about the organization we want to create.
	  * @throws Exception Throws an exception if their is a failure to create an organization.
	  */
	 public void createOrganization(String jsonString) throws Exception
	 {
		 try
		 {
			OrganizationBuilder builder = new OrganizationBuilder();
			
			JSONObject json = new JSONObject(jsonString);
			
			String name = json.getJSONObject("organization").getString("name");
			String description = json.getJSONObject("organization").getString("description");
			String requirements = json.getJSONObject("organization").getString("requirements");
			String privacy = json.getJSONObject("organization").getString("privacy");
			
			if(! builder.attemptToCreateAnOrganization(name, privacy, description, requirements) )
			{
				throw new Exception("Invalid format for one of the parameters.");
			}
			
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("An error occurred while attempting to create a new organization.\nMore details: "  + ex.getMessage() );
		 }
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
	 public JSONArray getAllOrganizations() throws Exception
	 { 
		 try
		 {
			 DataStoreFacade ds = new DataStoreFacade();
			 
			 return JSONTranslator.resultSetToJSONArray(ds.retrievePublicOrganizations());
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("An error occurred while attempting to retrieve all of the organizations.\nMore Details: " + ex.getMessage());
		 }
	 }
	 
	 /**
	  * Gets all the organizations which a user currently belongs to.
	  * @param userID The user that we want to retrieve the organizations for.
	  * @return A JSONArray with all the organizations the user is a part of.
	  * @throws Exception Throws an exception if there was an error retrieving the organizations for the user.
	  */
	 public JSONArray getAllOrganizations(int userID) throws Exception
	 {
		 try
		 {
			 DataStoreFacade ds = new DataStoreFacade();
			 
			 return JSONTranslator.resultSetToJSONArray(ds.retrieveOrganizationsForUser(userID));
		 }
		 catch(Exception ex)
		 {
			 throw new Exception("An error occurred while attempting to find all events for the user.\nMore Details: " + ex.getMessage());
		 }
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
