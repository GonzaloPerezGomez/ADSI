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

    public List<Alquila> getPeliculasAlquiladasPorUsuario(Usuario usuario) {
        // Implementar la consulta a la base de datos
        // Ejemplo: SELECT * FROM Alquileres WHERE usuario = ?
        return /* Lista de películas alquiladas */;
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
