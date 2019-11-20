package sosInterface;

import java.util.ArrayList;

/**
 * A Command class which propagates the front-end requests
 * to their specific target controllers. The requests messages 
 * which are parsed and pre-processed by the SOS Server then 
 * are used to call an appropriate dispatch from the SOS Dispatcher, 
 * which is in charge of directly calling all other controllers. 
 * Instead of being their own classes, each subcommand is defined 
 * in terms of parametrizations of the dispatch function within the 
 * SOS Dispatcher. Internally, SOS Dispatcher also keeps track of 
 * these requests and stores them in the Database
 */
public class SOSDispatcher {
	
	/**
	* This method returns all of the events.
	* @return is an ArrayList contain of Strings of events.
	*/
	public ArrayList<String> GetAllEvents() {return null;}
	
	/**
	* The method for dispatching events.
	*/
	public void Dispatch() {}
	
}