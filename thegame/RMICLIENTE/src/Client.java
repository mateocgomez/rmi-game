import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;



import java.io.*;
import java.net.MalformedURLException;



public class Client {
	private static ServerInterface look_up;
	public Servers machine1;
	public Servers machine2;
	public Servers machine3;
	
	public static void conexionServer(Servers maquina) {
		long startTime = System.nanoTime();
		try {
			look_up = (ServerInterface) Naming.lookup("//" + maquina.NombreServidor+"/ABC");
			maquina.Primos = look_up.Primos(maquina.Numero, maquina.NombreServidor);
			System.out.println("Servidor " + maquina.NombreServidor + "             " + "Tiempo  " + maquina.Tiempo+ "milisegundos" + "->" + maquina.Primos);
		} catch (Exception e)
		{
			System.out.println("No se puede conectar");
			e.printStackTrace();
		}
		
	}
	
	public static String ObtenerPerdedor(Servers machine1, Servers machine2, Servers machine3) {
		String Perdedor;
		
		Perdedor = "";
		//HILOS IMPLEMENTACION DE ESTADOS
		if(machine1.Estado) {
			if(machine2.Estado) {
				if(machine3.Estado) {
					if(machine1.Tiempo < machine2.Tiempo) {
						if(machine2.Tiempo < machine3.Tiempo) {
							Perdedor = "machine3";
						} else {
							Perdedor = "machine2";
						}
					} else {
						if(machine1.Tiempo < machine3.Tiempo) {
							Perdedor = "machine3";
						} else {
							Perdedor = "machine1";
						}
					}
					
				}else {
					if (machine1.Tiempo < machine2.Tiempo) {
						Perdedor = "machine2";
					} else {
						Perdedor = "machine1";
					}
				}
			} else {
				if (machine1.Tiempo < machine3.Tiempo) {
					Perdedor = "machine3";
				} else {
					Perdedor = "machine1";
				}
			}
		} else {
			if(machine2.Tiempo < machine3.Tiempo) {
				Perdedor = "machine3";
			} else {
				Perdedor = "machine2";
			}
		}
		
		return Perdedor;
	}
	
	public static void desconectar(Servers maquina) {
		try {
			look_up = (ServerInterface) Naming.lookup("//" + maquina.NombreServidor + "/ABC");
			look_up.ApagarMaquina();
		} catch (Exception e) {
			System.out.println("Pilas, error");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{
		boolean Seguir;
		boolean PrimeraVez;
		String MaquinaPerdedora;
		long num = Long.parseLong(JOptionPane.showInputDialog("Digite un numero: (entre 10 a 15 digitos)")); 
		Servers machine1 = new Servers("192.168.0.26",num, true);
		Servers machine2 = new Servers("192.168.43.185",num, true);
        Servers machine3 = new Servers("192.168.43.48",num, true);
        
	 Seguir = true;
     PrimeraVez = true;
     MaquinaPerdedora = "";
     while(Seguir) {
             if(PrimeraVez){
                     machine1.start();
             }else{
                 if(machine1.Estado){
                	 machine1 = new Servers("192.168.0.1",num, true);
                	 machine1.start();
                 }
             }
             if(PrimeraVez){
            	 machine2.start();
             }else{
                 if(machine2.Estado){
                	 machine2 = new Servers("192.168.43.185",num, true);
                	 machine2.start();
                 }
             }
             if(PrimeraVez){
            	 machine3.start();
             }else{
                 if(machine3.Estado){
                	 machine3 = new Servers("192.168.43.48",num, true);
                	 machine3.start();
                 }
             }
             PrimeraVez = false;
             //REVISO HILOS
             while(machine1.isAlive() || machine2.isAlive() || machine3.isAlive()) {

             }

             MaquinaPerdedora = ObtenerPerdedor(machine1, machine2, machine3); 
             System.out.println("La peor maquina fue: " + MaquinaPerdedora);
             
             if("machine1".equals(MaquinaPerdedora)) {
                 machine1.Estado = false;
                 desconectar(machine1);
                 System.out.println ("Machine 1 apagada");
             }else{
                 if("maquina2".equals(MaquinaPerdedora)) {
                 machine2.Estado = false;
                 desconectar(machine2);
                 System.out.println ("Machine2 apagada");
                 }else{
                 machine3.Estado = false;
                 desconectar(machine3);
                 System.out.println ("Machine3 apagada");
                 }
             }
             Seguir = (machine1.Estado && machine3.Estado) || (machine1.Estado && machine2.Estado) || (machine2.Estado && machine3.Estado);
        }
     System.out.println(" FIN DE LA COMPETENCIA ");
 }
}      
