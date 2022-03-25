package juego.mundo;

import java.sql.SQLException;
import java.util.Scanner;

public class Juego {
	
	private static Scanner lectura = new Scanner(System.in);
	private static Rondas rondas = cargarPreguntas();
	private static Premio premioAcumulado = new Premio(0);
	private static Jugador jugador;
	
	private static JugadoresDB lista;
	
	public static void main(String[] args) {

        lista = new JugadoresDB( );
        try {
        	// Conectar a BD
			lista.conectarDB();
			
			// Iniciar la tabla
			lista.inicializarTabla();
			
			lista.inicializarJugadoresBD();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//EJECUCION DEL PROGRAMA
		System.out.println("Para empezar le pediremos que responda las preguntas solamente con el numero que la representa, y evite poner cualquier caracter adicional.");
		
		Pregunta pregunta = rondas.getPreguntaRonda(1);
		System.out.println("");
		System.out.println("PRIMERA PREGUNTA POR 100000 PESOS" );
		imprimirPregunta(pregunta);
		
		pregunta = rondas.getPreguntaRonda(2);
		System.out.println("");
		System.out.println("SEGUNDA PREGUNTA POR 200000 PESOS" );
		imprimirPregunta(pregunta);
		
		pregunta = rondas.getPreguntaRonda(3);
		System.out.println("");
		System.out.println("TERCERA PREGUNTA POR 500000 PESOS" );
		imprimirPregunta(pregunta);
		
		pregunta = rondas.getPreguntaRonda(4);
		System.out.println("");
		System.out.println("CUARTA PREGUNTA POR 1000000 PESOS" );
		imprimirPregunta(pregunta);
		
		pregunta = rondas.getPreguntaRonda(5);
		System.out.println("");
		System.out.println("QUINTA PREGUNTA POR 2000000 PESOS" );
		imprimirPregunta(pregunta);
		
		actualizarDatosJugador();
		
	}
	public static void imprimirPregunta(Pregunta pregunta){
		System.out.println(pregunta.getEnunciado());
		for(int i=0;i<4;i++){
			System.out.println(String.valueOf(i+1) + ") " + pregunta.getOpciones().get(i).getEnunciado());
		}
		
		int respuesta = 100;
		
		boolean valid = false;
	    do{
	        System.out.print("Por favor ingresa la respuesta: ");
	        if(lectura.hasNextInt()){ // This checks to see if the next input is a valid **int**
	            respuesta = lectura.nextInt();
	            if(respuesta>0 && respuesta <5){
	            	valid = true;
	            }else{
	            	System.out.println("La respuesta debe ser un numero entero entre 1 y 4. Vuelve a intentarlo:");
	            }
	        }
	        else{
	            System.out.print("Esa respuesta no es valida. Ingresa un numero entero entre 1 y 4.\n");
	            lectura.next();
	        }
	    }while(valid == false);
		
		//si contesta correctamente actualizar el valor del premio acumulado y preguntar si quiere rendirse
		if(pregunta.getOpciones().get(respuesta-1).isCorrecta()){
			System.out.println("su respuesta es correcta");
			for(int i=0; i<rondas.getCategorias().size(); i++){
				if(rondas.getCategorias().get(i).getPreguntas().contains(pregunta)){
					premioAcumulado.aumentarPremio(rondas.getCategorias().get(i).getPremio());
				}
			}
			retirarse();
		}else{ //si el jugador se equivoca al contestar se termina el programa
			System.out.println("Has perdido. Vuelve a iniciar el programa.");
			lista.cerrar();
			System.exit(1);
		}
	}
	
	public static void retirarse(){
		System.out.println("Le gustaria retirarse? responda 1 para si, o 2 para no.");
		
		int respuesta = 100;
		
		boolean valid = false;
	    do{
	        if(lectura.hasNextInt()){ // This checks to see if the next input is a valid **int**
	            respuesta = lectura.nextInt();
	            if(respuesta>0 && respuesta <3){
	            	valid = true;
	            }else{
	            	System.out.println("La respuesta debe ser 1 o 2. Vuelva a intentarlo: ");
	            }
	        }
	        else{
	            System.out.print("Esa respuesta no es valida. Ingresa 1 o 2.\n");
	            lectura.next();
	        }
	    }while(valid == false);
		
		
		
		if(respuesta == 1){
			actualizarDatosJugador();
		}
	}
	
	public static void actualizarDatosJugador(){
		System.out.println("Digite su nombre de jugador para actualizar su saldo historico");
		String nombre = lectura.next();
		System.out.println("Digite su cedula para autenticar su cuenta");
		int cedula = 0;
		
		
		boolean valid = false;
	    do{
	        if(lectura.hasNextInt()){ // This checks to see if the next input is a valid **int**
	            cedula = lectura.nextInt();
	            valid = true;
	        }
	        else{
	            System.out.print("Esa respuesta no es valida. Ingresa un numero entero. Vuelve a intentar: \n");
	            lectura.next();
	        }
	    }while(valid == false);
		
		
		
		jugador = new Jugador(cedula, nombre ,premioAcumulado.getPremio());
		lista.agregarOActualizarJugador(jugador);
		Jugador jugadorActualizado = lista.getJugadores().get(lista.encontrarJugador(cedula));
		System.out.println(jugadorActualizado.getNombre()+", su saldo ganado en esta partida es: " + jugador.getSaldo() + ", y su saldo total historico es de: " +jugadorActualizado.getSaldo()+ " .Reinicie el programa para volver a jugar.");
		lista.cerrar();
		System.exit(1);
	}
	
	
	public static Rondas cargarPreguntas(){
		Rondas rondas = new Rondas();
		
		//PARA LA CATEGORIA 1
		Categoria categoria = new Categoria(1, 100000);
		
		//PREGUNTA 1
		Pregunta pregunta = new Pregunta("¿Qué nombre tiene tradicionalmente la fiesta que se hace a una mujer que espera un bebé? ");
		
		Opcion opcion = new Opcion("Baby drizzle", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Baby downpour", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Baby shower", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Baby deluge", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 2
		pregunta = new Pregunta("pregunta 2¿Cómo se llama la parte que queda en el suelo después de talar un árbol?");
		
		opcion = new Opcion("Tocón", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Muñon", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Joroba", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Restos", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 3
		pregunta = new Pregunta("¿Qué planta es el símbolo nacional de Irlanda?");
		
		opcion = new Opcion("Rosa", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Cardo", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Puerro", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Trébol", true);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 4
		pregunta = new Pregunta("¿De qué país son nativos los canguros?");
		
		opcion = new Opcion("Kenia", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Australia", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Perú", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Irlanda", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 5
		pregunta = new Pregunta("Una canción de cuna se canta para ayudar a los bebés a...");
		
		opcion = new Opcion("Despertar", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Comer", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Llorar", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Dormir", true);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		rondas.agregarCategoria(categoria);
		
		//--------------------------------------------------------------------------------------
		//PARA LA CATEGORIA 2
		categoria = new Categoria(2, 200000);
		
		//PREGUNTA 1
		pregunta = new Pregunta("¿Cómo se llaman los saltamontes comestibles de México?");
		
		opcion = new Opcion("Cuchamá", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Escamoles", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Chapulines", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Flips", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 2
		pregunta = new Pregunta("¿Cuántos segundos cuenta un árbitro de boxeo cuando un púgil cae con su espalda sobre el ring?");
		
		opcion = new Opcion("Diez", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Tres", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Cinco", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Siete", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 3
		pregunta = new Pregunta("¿Qué batalla supuso la derrota definitiva de Napoleón?");
		
		opcion = new Opcion("Watergate", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Lepanto", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Stalingrado", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Waterloo", true);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 4
		pregunta = new Pregunta("¿Qué ocurre siempre en un estuario?");
		
		opcion = new Opcion("LLueve mucho", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Solo hay agua salada", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Confluye agua salada y dulce", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Solo hay agua dulce", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 5
		pregunta = new Pregunta("¿Cuál es el país más grande de África?");
		
		opcion = new Opcion("Ghana", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Burundi", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Islas Seychelles", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Argelia.", true);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		rondas.agregarCategoria(categoria);
		
		//-------------------------------------------------------------------------
		//PARA LA CATEGORIA 3
		categoria = new Categoria(3, 500000);
		
		//PREGUNTA 1
		pregunta = new Pregunta("Cortar el pelo a una oveja se llama");
		
		opcion = new Opcion("Deslanar", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Desmelenar", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Esquilar", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Descargar", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 2
		pregunta = new Pregunta("¿Cuál de todas ellas es la mejor jugada de póquer?");
		
		opcion = new Opcion("Escalera de color", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Póker", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Full house", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Trio", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 3
		pregunta = new Pregunta("¿Quién fue el último gobernante del Imperio inca?");
		
		opcion = new Opcion("Pakal", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Moctezuma", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Cuauhtémoc", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Atahualpa", true);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 4
		pregunta = new Pregunta("El “hueso palomo” es…");
		
		opcion = new Opcion("La cintura", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("El coxis", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("La muñeca", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("El talón", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 5
		pregunta = new Pregunta("En la película de Disney; Tambor, el amigo de Bambi, es un:");
		
		opcion = new Opcion("Gato", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Perro", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Elefante", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Conejo.", true);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		rondas.agregarCategoria(categoria);
		
		//-------------------------------------------------------------------------
		//PARA LA CATEGORIA 4
		categoria = new Categoria(4, 1000000);
		
		//PREGUNTA 1
		pregunta = new Pregunta("El amaretto es un licor a base de...");
		
		opcion = new Opcion("Anís", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Café", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Almendra", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Manzana", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 2
		pregunta = new Pregunta("La nectarina es: ");
		
		opcion = new Opcion("Una fruta", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Una enzima", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Una hormona", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Un insecto", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 3
		pregunta = new Pregunta("Una tarjeta SD es un dispositivo de...");
		
		opcion = new Opcion("Comprension de video", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Procesamiento de audio", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Comunicacion remota", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Memoria", true);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 4
		pregunta = new Pregunta("De que fruta se obtiene la copra");
		
		opcion = new Opcion("Cereza", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Piña", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Albaricoque", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Coco", true);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 5
		pregunta = new Pregunta("¿En qué parte del mundo se dio la batalla de Ticonderoga?");
		
		opcion = new Opcion("Europa", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("sur de la India", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Filipinas", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Norteamerica", true);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		rondas.agregarCategoria(categoria);
		
		//-----------------------------------------------------------------------
		//PARA LA CATEGORIA 5
		categoria = new Categoria(5, 2000000);
		
		//PREGUNTA 1
		pregunta = new Pregunta("Si un día decidieras sembrar semillas de Quercus robur. ¿Qué crecería?");
		
		opcion = new Opcion("Arbol", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Flores", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Verduras", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Grano", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 2
		pregunta = new Pregunta("¿De qué murió el compositor Chopin?");
		
		opcion = new Opcion("Tuberculosis", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Ataque al corazon", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Insuficiencia renal aguda", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Cancer estomacal", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 3
		pregunta = new Pregunta("¿Quién fue el primer hombre en viajar dos veces al espacio?");
		
		opcion = new Opcion("Vladimir Titov", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Michael Collins", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Gus Grissom", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Yuri Gagarin", true);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 4
		pregunta = new Pregunta("¿Cuál es el río más largo del mundo?");
		
		opcion = new Opcion("Nilo", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Amazonas", true);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Orinoco", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Misisipi", false);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		
		//PREGUNTA 5
		pregunta = new Pregunta("¿Cuál es el país con más habitantes del mundo?");
		
		opcion = new Opcion("Russia", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Canadá", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("Antártida", false);
		pregunta.agregarOpcion(opcion);
		opcion = new Opcion("China", true);
		pregunta.agregarOpcion(opcion);
		
		categoria.agregarPregunta(pregunta);
		rondas.agregarCategoria(categoria);
		return rondas;
	}

}
