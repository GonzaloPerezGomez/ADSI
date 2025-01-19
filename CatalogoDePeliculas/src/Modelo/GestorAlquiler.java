
package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Locale;
import java.util.Objects;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.swing.JOptionPane;

import db.SQLite;



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
	public void cargarAlquila() throws SQLException {
		alquiladas.addAll(SQLite.getBaseDeDatos().getAllAlquila());
	}

	public List<Pelicula> getPeliculasAlquiladasPorUsuario(Usuario usuario) {
	    // Lista donde se almacenarán las películas filtradas
	    List<Pelicula> peliculasAlquiladas = new ArrayList<>();
	    
	    // Recorrer la lista de todos los alquileres
	    for (Alquila alquiler : alquiladas) {
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
	    for (Alquila alquiler : alquiladas) {
	        // Si el usuario del alquiler coincide con el usuario proporcionado
	        if (alquiler.getusuario().equals(usuario.getNombreUsuario())) {
	            peliculasAlquiladas.add(alquiler);
	        }
	    }
	    
	    // Devolver la lista de alquilar por el usuario
	    return peliculasAlquiladas;
	}

	public boolean estaAlquilada(Usuario usuario, Pelicula pelicula) {
		List<Alquila> peliculasAlquiladas = GestorAlquiler.getGestorAlquiler().getAlquiladasPorUsuario(usuario);
		for(int i = 0; i < peliculasAlquiladas.size(); i++) {
			if (pelicula.equals(peliculasAlquiladas.get(i).getPelicula())) {
				LocalDateTime ahora = LocalDateTime.now();
				ZoneId zona = ZoneId.of("Europe/Madrid");
				Duration diff = Duration.between(peliculasAlquiladas.get(i).getFecha().atZone(zona).toInstant(), ahora.atZone(zona).toInstant());
				if (diff.toHours() < 48) {
					return true;
				}
			}
		}
		return false;
	}
	 public void alquilarPelicula(Usuario usuario, Pelicula pelicula) {
	    	if (estaAlquilada(usuario, pelicula) == false) {
	    		Alquila nuevo = new Alquila(usuario, pelicula, LocalDateTime.now());
	    		alquiladas.add(nuevo);
	    		String sql = "INSERT INTO Alquila (nombreUsuario, titulo, fechaPelicula, fechaAlquila) VALUES ('" + usuario.getNombreUsuario() + "', '" + pelicula.getTitulo() + "', '" + pelicula.getFecha() + "','" + nuevo.getFecha() + "' )";
	    		SQLite.getBaseDeDatos().execSQL(sql);
	    		JOptionPane.showMessageDialog(null,"Película alquilada correctamente");
	    		System.out.print("Película alquilada correctamente");
	    	}
	    	else {JOptionPane.showMessageDialog(null,"Película ya alquilada"); System.out.print("Película ya alquilada");}
	    	
	    }
	 
	 	//Para los test
	 
		public void reset() {
			gestorAlquiler = null;
		}
		
		public void addAlquila(Alquila pAlquila) {
			alquiladas.add(pAlquila);
		}
		
}