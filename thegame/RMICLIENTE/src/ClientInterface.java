
 import java.rmi.*;

public interface ClientInterface extends Remote {
	public int add(int a,int b) throws RemoteException;

}
