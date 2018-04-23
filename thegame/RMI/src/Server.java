import java.rmi.*;
import java.rmi.server.*;
public class Server {
	public static void main (String[] argv) {
		try {
			ObjectServer Hello = new ObjectServer();
			Naming.rebind("rmi://localhost/ABC", Hello);
			System.out.println("EL PUTO SERVIDOR YA ESTA CORRIENDO.");
		   }catch (Exception e) {
			   System.out.println("Addition Server failed: " + e);
			}
		}
	}

