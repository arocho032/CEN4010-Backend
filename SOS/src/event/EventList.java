package event;

import java.sql.ResultSet;
import utils.JSONTranslator;
import org.json.JSONArray;


// TODO: Auto-generated Javadoc
/**
 * A class that aggregates Events. 
 */
public class EventList {
	
	/** The json list. */
	private JSONArray jsonList;
	
	/**
	 *  Constructs a new EventList class. Called through the EventListBuilder
	 *  class. Attribute assignations are done through protected scope. 
	 */
	protected EventList() {
		jsonList = null;
	};
	
	/**
	 * Contrusts a new EventList from the contents of the ResultSet.
	 * @param results
	 * 		the ResultSet containing a collection of Event SQL entries.
	 * @throws Exception
	 * 		thrown if errors occur while parsing the ResultSet
	 */
	protected EventList(ResultSet results) throws Exception
	{
		try
		{
			jsonList = JSONTranslator.resultSetToJSONArray(results);
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error in generating the list of events.\nMore Details: " + ex.getMessage());
		}
	}
	
	/**
	 * Return JSON list.
	 *
	 * @return the JSONArray containing the list of Events
	 */
	public JSONArray returnJSONList()
	{
		return this.jsonList;
	}
		
}
