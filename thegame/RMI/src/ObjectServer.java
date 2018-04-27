import java.rmi.*;
import java.rmi.server.*;





public class ObjectServer extends UnicastRemoteObject implements ServerInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectServer () throws RemoteException {
		super();
	}
	
	//Turnoff machine
	
	public void ApagarMaquina() throws RemoteException{
		try {
			Runtime.getRuntime().exec("shutdown.exe -s -t 0");
        }catch (Exception e){
            System.out.println("*ERROR* no se puede apagar la maquina");
        }
		}
	
	//THE GAME
	
	public String Primos(Long numero, String maquina) throws RemoteException {
		long nPrimos = 0;
		boolean Primo;
		long x = 0;
		
		for (int i=3; i < numero; i = i+2) {
			Primo = true;
			for (int j=2; Primo && j <= Math.sqrt((double) i); j++) {
				if (i % j ==0) {
					Primo = false;
				}
		}
		if (Primo) {
			nPrimos++;
		}
	}
		
		x= nPrimos + 1;
		System.out.println("El servidor:  " +maquina + "Tiene un total de " + numero + "numero de primos" + x);
		return (x + "Numero total de primos");
	}
	
	//this function calculate the time that process the server to complete the sintaxys
	
	public Long Tiempo(Long time) throws RemoteException{
		long tfinal;
		long endTime = System.nanoTime();
		 tfinal = (endTime - time) / 1000000;
	     System.out.println("Tiempo " + tfinal+" milisegundos");
	     return ((endTime - time) / 1000000);
	}
	



	
}



