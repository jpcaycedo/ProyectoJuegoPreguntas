package juego.mundo;

public class Jugador {
	
	//ATRIBUTOS
	private String nombre;
	private int saldo;
	private int cedula;
	
	//CONSTRUCTOR
	public Jugador(int cedula, String nombre,int saldo) {
		this.nombre = nombre;
		this.saldo = saldo;
		this.cedula = cedula;
	}
	
	//METODOS
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public int getCedula(){
		return cedula;
	}
	
	public int getSaldo() {
		return saldo;
	}

	public void aumentarSaldo(int saldo) {
		this.saldo += saldo;
	}
	
	

}
