
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
