package juego.mundo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JugadoresDB {
	
	
	public final static String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public final static String JDBC_URL = "jdbc:derby:jugadoresDB;create=true";
	public final static String SHUT_DOWN = "admin.db.shutdown";
    
    private Connection conexion;
    
    //ATRIBUTOS
    private ArrayList<Jugador> jugadores;

    //CONSTRUCTOR
	public JugadoresDB() {
		jugadores = new ArrayList<Jugador>();
	}
	
    public void conectarDB( ) throws SQLException, Exception
    {
    	// Load driver
    	Class.forName(DRIVER);
    	
    	// Connect
    	conexion = DriverManager.getConnection( JDBC_URL );
        if (conexion != null)
        {
        	System.out.println("Conexion exitosa");
        }
    }
    
    public void desconectarDB( ) throws SQLException
    { 
        conexion.close( );
  
        try
        {
            DriverManager.getConnection( SHUT_DOWN );
        }
        catch( SQLException e )
        {
            // Al bajar la base de datos se produce siempre una excepcion
        }
    }
    
    public void inicializarTabla( ) throws SQLException
    {
        Statement s = conexion.createStatement( );

        boolean crearTabla = false;
        try
        {
            // Verificar si ya existe la tabla 
            s.executeQuery( "SELECT * FROM Jugadores WHERE 1=2" );
        }
        catch( SQLException se )
        {
            // La excepcion se lanza si la tabla no existe
            crearTabla = true;
        }

        // Se crea una nueva tabla vacia
        if( crearTabla )
        {
            s.execute( "CREATE TABLE Jugadores(\r\n"
            		+ "cedula int,\r\n"
            		+ "nombre varchar(32), \r\n"
            		+ "saldo int, \r\n"
            		+ "PRIMARY KEY (cedula)\r\n"
            		+ ")" );
        }

        s.close( );
    }
    
    
    public void inicializarJugadoresBD() 
    {
    	try {
			Statement st = conexion.createStatement();
			String sql = "SELECT * FROM Jugadores";
			ResultSet resultado = st.executeQuery(sql);
			
			while( resultado.next())
			{
				int cedula = resultado.getInt(1);
				String nombre = resultado.getString(2);
				int saldo = resultado.getInt(3);
								
	            Jugador nuevo = new Jugador(cedula, nombre, saldo );
	            jugadores.add(nuevo);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
    
    public boolean estaRegistradoJugador(int cedula){
    	for(int i=0; i<jugadores.size();i++){
    		if(jugadores.get(i).getCedula() == cedula){
    			return true;
    		}
    	}
    	return false;
    }
    
    public int encontrarJugador(int cedula){
    	for(int i=0; i<jugadores.size();i++){
    		if(jugadores.get(i).getCedula() == cedula){
    			return i;
    		}
    	}return -1;
    }
    
    public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	public void agregarOActualizarJugador(Jugador jugador){
    	if(!estaRegistradoJugador(jugador.getCedula())){
    		jugadores.add(jugador);
    		
            //Agregar a la base de datos
            try {
				Statement st = conexion.createStatement();
				String sql = "INSERT INTO Jugadores VALUES (" + jugador.getCedula() + ", '" + jugador.getNombre()  + "', " +  jugador.getSaldo() + ")";
				st.execute(sql);
            } catch (SQLException e) {
				e.printStackTrace();
			}
            
    	}else{
    		int index = encontrarJugador(jugador.getCedula());
    		jugadores.get(index).setNombre(jugador.getNombre());
    		jugadores.get(index).aumentarSaldo(jugador.getSaldo());
    		
            try {
				Statement st = conexion.createStatement();
				String sql = "UPDATE Jugadores SET nombre='"+jugadores.get(index).getNombre()+"', saldo="+jugadores.get(index).getSaldo()+" WHERE cedula = "+jugador.getCedula()+" "; 
				st.execute(sql);
				
            } catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    public void cerrar( )
    {
    	try {
			desconectarDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
