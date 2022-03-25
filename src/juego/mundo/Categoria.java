package juego.mundo;

import java.util.ArrayList;
import java.util.Random;

public class Categoria {
	
	//ATRIBUTOS
	private int nivel;
	private int premio;
	private ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
	
	//CONSTRUCTOR
	public Categoria(int nivel, int premio) {
		this.nivel = nivel;
		this.premio = premio;
	}
	
	//METODOS
	public int getNivel(){
		return nivel;
	}
	
	public int getPremio(){
		return premio;
	}

	public ArrayList<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void agregarPregunta(Pregunta pregunta) {
		preguntas.add(pregunta);
	}
	
	public Pregunta preguntaAleatoria(){
		int index = new Random().nextInt(preguntas.size());
		return preguntas.get(index);
	}

	
}
