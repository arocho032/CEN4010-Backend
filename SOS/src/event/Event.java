package event;


/**
 *	A run-time representation of an Event persistent Object. This class is used as
 *  an intermediary for creation, retrieval, and modification of Event data within
 *  the Java code (and the JVM). It is encodable (or serializable) to a database 
 *  format (e.g., SQL Entry).
 */
public class Event {

	/**
	 *  Constructs a new Event class. Called through the EventBuilder class. 
	 *  Attribute assignations are done through protected scope. 
	 */
	protected Event() {}

}
