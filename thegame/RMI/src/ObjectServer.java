import java.rmi.*;
import java.rmi.server.*;



public class ObjectServer extends UnicastRemoteObject implements ServerInterface {
	public ObjectServer () throws RemoteException {}
	
	public int add(int a, int b) throws RemoteException{
		int result=a+b;
		return result;
	}
}
