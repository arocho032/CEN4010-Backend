package utils;

import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;


// TODO: Auto-generated Javadoc
/**
 * The Class JSONTranslator.
 */
public class JSONTranslator {

	
	/**
	 * Result set to JSON array.
	 *
	 * @param set the set
	 * @return the JSON array
	 * @throws Exception the exception
	 */
	public static JSONArray resultSetToJSONArray(ResultSet set) throws Exception
	{
		
		JSONArray jsonArray = new JSONArray();
		
		try
		{
	
			while(set.next())
			{
				int num_rows = set.getMetaData().getColumnCount();
			
				JSONObject json = new JSONObject();
			
				for(int setIndex = 1; setIndex < num_rows + 1; setIndex++)
				{
				
					json.put(set.getMetaData().getColumnLabel(setIndex).toLowerCase(),
						set.getObject(setIndex));
				
				}
				
				jsonArray.put(json);
			
			}
		}
		catch(Exception ex)
		{
			throw new Exception("There was an error converting results to JSON.");
		}
		
		return jsonArray;
		
	}
	
	/**
	 * Result set to JSON object.
	 *
	 * @param set the set
	 * @return the JSON object
	 * @throws Exception the exception
	 */
	public static JSONObject resultSetToJSONObject(ResultSet set) throws Exception
	{
		
		JSONObject json = new JSONObject();
		
		try
		{
//			while(set.next())
//			{
				int num_rows = set.getMetaData().getColumnCount();	
			
				for(int setIndex = 1; setIndex < num_rows + 1; setIndex++)
				{
				
					json.put(set.getMetaData().getColumnLabel(setIndex).toLowerCase(),
						set.getObject(setIndex));
				
				}
//				
//			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("There was an error converting results to JSON.");
		}
		
		return json;
		
		
	}
	
}
