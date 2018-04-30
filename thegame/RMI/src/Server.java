import java.rmi.*;
import java.rmi.server.*;


public class Server {
	
	public static void main (String[] argv) {
		try {
			System.setProperty(
					"java.rmi.server.codebase",
					"file:/C:/Users/gomez/Desktop/RMI/");
			ServerInterface Hello = new ObjectServer();
			Naming.rebind("rmi://192.168.43.169/MyServer", Hello);
			System.out.println("El servidor esta preparado para ser usado");
		   }catch (Exception e) {
			   System.out.println("El servidor a fallado por:  " + e);
			}
		}
	}
