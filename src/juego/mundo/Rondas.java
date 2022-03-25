package juego.mundo;

import java.util.ArrayList;

public class Rondas {
	//ATRIBUTOS
	private ArrayList<Categoria> categorias = new ArrayList<Categoria>();
	
	//CONSTRUCTOR
	public Rondas() {
	}
	//METODOS
	public void agregarCategoria(Categoria categoria) {
		categorias.add(categoria);
	}
	
	public Pregunta getPreguntaRonda(int nivel){
		Pregunta preguntaElegida = new Pregunta("Error");
		for(int i=0; i<categorias.size();i++){
			Categoria nCategoria = categorias.get(i);
			
			if(nivel == nCategoria.getNivel()){
				preguntaElegida = nCategoria.preguntaAleatoria();
				break;
			}
		}
		return preguntaElegida;
	}
	public ArrayList<Categoria> getCategorias() {
		return categorias;
	}
	
	
}
