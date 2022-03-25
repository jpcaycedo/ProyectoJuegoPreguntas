package juego.mundo;

import java.util.ArrayList;

public class Pregunta {
	
	//ATRIBUTOS
	private String enunciado;
	private ArrayList<Opcion> opciones = new ArrayList<Opcion>();
	
	//CONSTRUCTOR
	public Pregunta(String enunciado) {
		this.enunciado = enunciado;
	}
	
	//METODOS
	public String getEnunciado(){
		return enunciado;
	}

	public ArrayList<Opcion> getOpciones() {
		return opciones;
	}

	public void agregarOpcion(Opcion opcion) {
		opciones.add(opcion);
	}

}
