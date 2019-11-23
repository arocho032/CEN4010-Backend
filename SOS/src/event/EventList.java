package event;

import java.sql.ResultSet;
import utils.ListHelper;
import org.json.JSONArray;


/**
 * A class that aggregates Events. 
 */
public class EventList extends ListHelper {
	
	private JSONArray jsonList;
	
	/**
	 *  Constructs a new EventList class. Called through the EventListBuilder
	 *  class. Attribute assignations are done through protected scope. 
	 */
	protected EventList() {
		jsonList = null;
	};
	
	protected EventList(ResultSet results) throws Exception
	{
		try
		{
			jsonList = resultSetToJSON(results);
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error in generating the list of events.\nMore Details: " + ex.getMessage());
		}
	}
	
	
	
	
	
}
