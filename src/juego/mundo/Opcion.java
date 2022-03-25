package juego.mundo;

public class Opcion {
	
	//ATRIBUTOS
	private String enunciado;
	private boolean correcta;
	
	//CONSTRUCTOR
	public Opcion(String enunciado, boolean correcta) {
		this.enunciado = enunciado;
		this.correcta = correcta;
	}
	//METODOS

	public String getEnunciado() {
		return enunciado;
	}

	public boolean isCorrecta() {
		return correcta;
	}
	

}
