package sosInterface;

/** SOSSessionManager keeps track of each session for every user.
* It can gather information about the current session, update the session,
* or even destroy it.
*/
public class SOSSessionManager {
	/**
 	* The constructor could be made private
 	* to prevent others from instantiating this
 	* class. But this would also make it
 	* impossible to create instances of
 	* SOSSessionManager subclasses.
	*/
	protected SOSSessionManager() {}
	
 	/**
  	 * A handle to the unique SOSSessionManager
  	* instance.
 	*/
 	static private SOSSessionManager _instance = null;
	/**
	 * @return The unique instance of this
	 * class.
	 */
	 static public SOSSessionManager instance( ) {
		 if ( null == _instance) {
			 _instance = new SOSSessionManager( );
		 }
		 return _instance;
	 }
	 
    /**
	* This method returns the information regarding a given session.
	* @param sessionID is the ID for the desired session
	* @return is a String containing current session's information.
	*/
	 static public String GetSessionInformation(String sessionID) {return null;}
	 
	/**
	* This method returns the information regarding a current live session
	* @return is a String containing current session's ID.
	*/
	 static public String GetCurrentSession() {return null;}
	 
	/**
	* This method updates the given session with the modified data
	* @param sessionID is the ID of the session to be updated
	* @param data is the modified data to be updated on the given session
	* @return is true if update information is successfull and false otherwise.
	*/
	static public boolean UpdateSessionInformation(String data, String sessionID) {return false;}
	
	/**
	* This method destroys a given user session
	* @param sessionID is the ID for the session to be destroyed
	* @return is true if DestroySession is successful and false otherwise.
	*/
	static public boolean DestroySession(String sessionID) {return false;}
	/**
	* This method logs out the user from their current session.
	* @return is true if Logout is successful and false otherwise.
	*/
	static public boolean LogOutUser() {return false;}

}
