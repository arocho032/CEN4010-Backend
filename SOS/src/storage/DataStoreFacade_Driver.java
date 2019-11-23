package storage;

public class DataStoreFacade_Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		try
		{
			DataStoreFacade ds = new DataStoreFacade();
		
			//ds.registerNewUser("testing", "jdoe002@fiu.edu", "John", "jdoe002");
			
			System.out.println(ds.verifyUserLogin("jdoe002@fiu.edu", "testing"));
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		
	}

}
