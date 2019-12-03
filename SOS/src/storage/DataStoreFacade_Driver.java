package storage;

import java.sql.*;
import org.json.*;

import utils.JSONTranslator;

// TODO: Auto-generated Javadoc
/**
 * The Class DataStoreFacade_Driver.
 */
public class DataStoreFacade_Driver {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
			DataStoreFacade ds = new DataStoreFacade();
			JSONObject json = JSONTranslator.resultSetToJSONObject(ds.retrieveEventDetails(1));
			
			ds.terminateConnection();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		
	}

}
