import java.rmi.*;
public class Client {
	public static void main (String[] args) {
		ClientInterface hello;
		try {
			hello = (ClientInterface)Naming.lookup("rmi://localhost/ABC");
			int result=hello.add(9,10);
			System.out.println("El resultado es:" +result);
		}
		
		catch (Exception e) {
			System.out.println("Bienvenido " + e);
		}
	}
}
