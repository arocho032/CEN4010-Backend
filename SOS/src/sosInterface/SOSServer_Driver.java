package sosInterface;

public class SOSServer_Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Starting the SOS server");
		try
		{
			SOSServer server = new SOSServer("10.108.141.242",4567);	
			
			server.ListenForEvents();
			
		
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}

	}

}
