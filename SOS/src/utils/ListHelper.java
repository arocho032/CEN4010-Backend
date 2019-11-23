package utils;

import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;


public class ListHelper {

	
	public JSONArray resultSetToJSON(ResultSet set) throws Exception
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
	
}
