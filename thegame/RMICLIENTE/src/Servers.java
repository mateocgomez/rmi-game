
public class Servers extends Thread {
	public String NombreServidor;
	public long Numero;
	public boolean Estado;
	public long Tiempo;
	public String Primos;
	
	
	public Servers(String NombreServidor, long Numero, boolean Estado) {
		this.NombreServidor = NombreServidor;
		this.Numero = Numero;
		this.Estado = Estado;
		this.Tiempo = 0;
		this.Primos = "";
	}
	
	public void run() {
		System.out.println("Servidor: " + this.NombreServidor + "ACTIVADO");
		try {
			Client.conexionServer(this);
		}catch (Exception e) {
			System.out.println("Servidor "+ this.NombreServidor + "FALLO");
		}
		System.out.println("Servidor " + this.NombreServidor + "buen viaje");
	}
}
