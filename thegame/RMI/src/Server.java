import java.rmi.*;

public class Server {
	public static void main (String[] argv) {
		try {
			ObjectServer Hello = new ObjectServer();
			Naming.rebind("rmi://192.168.0.26/ABC", Hello);
			System.out.println("El servidor esta preparado para ser usado");
		   }catch (Exception e) {
			   System.out.println("El servidor a fallado por:  " + e);
			}
		}
	}

