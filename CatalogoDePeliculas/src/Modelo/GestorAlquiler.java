package Modelo;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;



public class GestorAlquiler {

    private static GestorAlquiler gestorAlquiler = new GestorAlquiler();


	public static GestorAlquiler getGestorAlquiler(){
		if(gestorAlquiler == null) {
			gestorAlquiler = new GestorAlquiler();
		}
		return gestorAlquiler;
	}

    public List<Pelicula> getPeliculasAlquiladasPorUsuario(Usuario usuario) {
        // Implementar la consulta a la base de datos
        // Ejemplo: SELECT * FROM Alquileres WHERE usuario = ?
        return /* Lista de películas alquiladas */;
    }
}
