

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import java.rmi.server.*;
import java.lang.Object;
import javax.swing.JOptionPane;



public class Client {
	private static ServerInterface look_up;
	public Servers maquina1;
	public Servers maquina2;
	public Servers maquina3;

	public static void conexionServer(Servers maquina){
		long startTime = System.nanoTime();
		try {
                    look_up = (ServerInterface) Naming.lookup("//"+maquina.NombreServidor+"/MyServer");
                    maquina.Primos = look_up.Primos(maquina.Numero, maquina.NombreServidor);
                    maquina.Tiempo = look_up.Tiempo(startTime);
                    System.out.println("Los resultados son:   "+maquina.NombreServidor+"        "+" Tiempo  "+maquina.Tiempo+" segundos" + " :"+maquina.Primos);
                   }
		catch (Exception e)
		{
			System.out.println(" ERROR EN CONEXION ");
		    e.printStackTrace();
		}
	}
        
	
	public static String ObtenerPerdedor(Servers maquina1, Servers maquina2, Servers maquina3){
		//Llegando a esta funciï¿½n, asumo que existen al menos dos maquinas activas
		String Perdedor;
		
		Perdedor = "";
		if(maquina1.Estado) {
			if(maquina2.Estado) {
				if(maquina3.Estado) {
					if(maquina1.Tiempo < maquina2.Tiempo) {
						if(maquina2.Tiempo < maquina3.Tiempo) {
							Perdedor = "maquina3";
						}
						else {
							Perdedor = "maquina2";
						}
					}
					else {
						if(maquina1.Tiempo < maquina3.Tiempo) {
							Perdedor = "maquina3";
						}
						else {
							Perdedor = "maquina1";
						}
					}
				}else
				{
					if(maquina1.Tiempo < maquina2.Tiempo) {
						Perdedor = "maquina2";
					}
					else {
						Perdedor = "maquina1";
					}
				}
			}
			else {
				if(maquina1.Tiempo < maquina3.Tiempo) {
					Perdedor = "maquina3";
				}
				else {
					Perdedor = "maquina1";
				}
			}
		}
		else {
			if(maquina2.Tiempo < maquina3.Tiempo) {
				Perdedor = "maquina3";
			}
			else {
				Perdedor = "maquina2";
			}
		}
		
		return Perdedor;
	}
        public static void desconectar(Servers maquina){
            try {
                look_up = (ServerInterface) Naming.lookup("//"+maquina.NombreServidor+"/MyServer");
                look_up.ApagarMaquina();
               }
               catch (Exception e)
               {
                System.out.println("Ha ocurrido una excepcion no esperada...");
                   e.printStackTrace();
                }
            //System.exit(0);
        } 
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        boolean Seguir;
        boolean PrimeraVez;
        String MaquinaPerdedora;
        long num = Long.parseLong(JOptionPane.showInputDialog("Ingrese un numero entre 10 y 15 digitos")); 
        Servers maquina1 = new Servers("192.168.0.14",num, true);
        Servers maquina2 = new Servers("192.168.0.5",num, true);
        Servers maquina3 = new Servers("192.168.0.10",num, true);

        Seguir = true;
        PrimeraVez = true;
        MaquinaPerdedora = "";
        while(Seguir) {
                if(PrimeraVez){
                        maquina1.start();
                }else{
                    if(maquina1.Estado){
                        maquina1 = new Servers("192.168.0.14",num, true);
                        maquina1.start();
                    }
                }
                if(PrimeraVez){
                    maquina2.start();
                }else{
                    if(maquina2.Estado){
                        maquina2 = new Servers("192.168.0.5",num, true);
                        maquina2.start();
                    }
                }
                if(PrimeraVez){
                        maquina3.start();
                }else{
                    if(maquina3.Estado){
                    maquina3 = new Servers("192.168.0.10",num, true);
                    maquina3.start();
                    }
                }
                PrimeraVez = false;
                //Chequeo hasta que todos los hilos hayan terminado ejecuciï¿½n...
                while(maquina1.isAlive() || maquina2.isAlive() || maquina3.isAlive()) {

                }

                MaquinaPerdedora = ObtenerPerdedor(maquina1, maquina2, maquina3); 
                System.out.println("La maquina perdedora fue " + MaquinaPerdedora);
                //Inhabilito la maquina perdedora...
                if("maquina1".equals(MaquinaPerdedora)) {
                    maquina1.Estado = false;
                    desconectar(maquina1);
                    System.out.println ("Maquina 1 apagada");
                }else{
                    if("maquina2".equals(MaquinaPerdedora)) {
                    maquina2.Estado = false;
                    desconectar(maquina2);
                    System.out.println ("Maquina 2 apagada");
                    }else{
                    maquina3.Estado = false;
                    desconectar(maquina3);
                    System.out.println ("Maquina 3 apagada");
                    }
                }
                Seguir = (maquina1.Estado && maquina3.Estado) || (maquina1.Estado && maquina2.Estado) || (maquina2.Estado && maquina3.Estado);
           }
        System.out.println(" FIN DE LA COMPETENCIA ");
    }
}   