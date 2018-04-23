import java.rmi.*;
public interface ServerInterface extends Remote{
	public int add(int a,int b) throws RemoteException;
	

}
