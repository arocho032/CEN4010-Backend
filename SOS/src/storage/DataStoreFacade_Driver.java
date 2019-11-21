package storage;

public class DataStoreFacade_Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		try
		{
			DataStoreFacade ds = new DataStoreFacade();
		
			//ds.registerNewUser("testing", "jdoe002@fiu.edu", "John", "jdoe002");
			
			ds.saveUserAttendance(5, 1);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		
	}

}
