
package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import javax.swing.JOptionPane;



public class GestorAlquiler {

    private static GestorAlquiler gestorAlquiler;
    private List<Alquila> alquiladas;
    
	private GestorAlquiler() throws SQLException {
		alquiladas = new ArrayList<Alquila>();
			
	/////pruebas para punuaciones y demas
		Usuario p1 = new Usuario("b","b","Hola1234.",false);
		Usuario p2 = new Usuario("bb","bb","Hola1234.",false);
		Usuario p3 = new Usuario("bbb","bbb","Hola1234.",false);
		
		Pelicula p11 = new Pelicula("ff","ff","2000-01-02");
		Pelicula p22 = new Pelicula("rr","ff","2000-01-02");
		Pelicula p33 = new Pelicula("r3","ff","2000-01-02");
		
		Alquila p111 = new Alquila(p1,p22); Alquila p333 = new Alquila(p2,p22);
		Alquila p222 = new Alquila(p1,p33);Alquila p444 = new Alquila(p2,p33);
		alquiladas.add(p444);alquiladas.add(p111);alquiladas.add(p222);alquiladas.add(p333);
	}
    
	public static GestorAlquiler getGestorAlquiler(){
		if(gestorAlquiler == null) {
			try {
			gestorAlquiler = new GestorAlquiler();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return gestorAlquiler;
	}

	public List<Pelicula> getPeliculasAlquiladasPorUsuario(Usuario usuario) {
	    // Lista donde se almacenarán las películas filtradas
	    List<Pelicula> peliculasAlquiladas = new ArrayList<>();
	    
	    // Recorrer la lista de todos los alquileres
	    for (Alquila alquiler : GestorAlquiler.getGestorAlquiler().alquiladas) {
	        // Si el usuario del alquiler coincide con el usuario proporcionado
	        if (alquiler.getusuario().equals(usuario.getNombreUsuario())) {
	            peliculasAlquiladas.add(alquiler.getPelicula());
	        }
	    }
	    
	    // Devolver la lista de películas alquiladas por el usuario
	    return peliculasAlquiladas;
	}
	public List<Alquila> getAlquiladasPorUsuario(Usuario usuario) {
	    // Lista donde se almacenarán las películas filtradas
	    List<Alquila> peliculasAlquiladas = new ArrayList<>();
	    
	    // Recorrer la lista de todos los alquileres
	    for (Alquila alquiler : GestorAlquiler.getGestorAlquiler().alquiladas) {
	        // Si el usuario del alquiler coincide con el usuario proporcionado
	        if (alquiler.getusuario().equals(usuario.getNombreUsuario())) {
	            peliculasAlquiladas.add(alquiler);
	        }
	    }
	    
	    // Devolver la lista de películas alquiladas por el usuario
	    return peliculasAlquiladas;
	}

    
    public void alquilarPelicula(Usuario usuario, Pelicula pelicula) {
    	if (GestorPeliculas.getGestorPeliculas().estaAlquilada(usuario, pelicula) == false) {
    		Alquila nuevo = new Alquila(usuario, pelicula);
    		alquiladas.add(nuevo);
    		JOptionPane.showMessageDialog(null,"Película alquilada correctamente");
    	}
    	else {JOptionPane.showMessageDialog(null,"Película ya alquilada");}
    	
    }
}
