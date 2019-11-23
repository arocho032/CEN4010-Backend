package storage;

import java.sql.*;
import utils.ListHelper;
import org.json.*;

public class DataStoreFacade_Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		try
		{
			DataStoreFacade ds = new DataStoreFacade();
			
			ResultSet resultSet = ds.filterEventsByLocation(10, 21);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (resultSet.next()) {
			    for (int i = 1; i <= columnsNumber; i++) {
			        if (i > 1) System.out.print(",  ");
			        String columnValue = resultSet.getString(i);
			        System.out.print(columnValue + " " + rsmd.getColumnName(i));
			    }
			    System.out.println("");
			}
			
			ListHelper testing = new ListHelper();
			
			JSONArray jarray = testing.resultSetToJSON(ds.filterEventsByLocation(10, 21));
			
			for(int i = 0; i < jarray.length(); i++)
			{
				System.out.println(jarray.get(i));
			}
			
			
			
			ds.terminateConnection();
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		
	}

}
