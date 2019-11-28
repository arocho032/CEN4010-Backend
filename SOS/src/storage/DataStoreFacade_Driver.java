package storage;

import java.sql.*;
import org.json.*;

import utils.JSONTranslator;

public class DataStoreFacade_Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
			DataStoreFacade ds = new DataStoreFacade();
//			ds.registerNewUser("Test", "t@g.com", "Name", "userName");
			JSONObject json = JSONTranslator.resultSetToJSONObject(ds.retrieveEventDetails(1));
			
			System.out.println(json);	
			ds.terminateConnection();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		
	}

}
