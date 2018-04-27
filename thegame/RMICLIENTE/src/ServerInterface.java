import java.rmi.*;





public interface ServerInterface extends Remote{
		
		public String Primos(Long numero, String maquina) throws RemoteException;         
	    public Long Tiempo(Long time) throws RemoteException;
	    public void ApagarMaquina() throws RemoteException;
	    
	}
