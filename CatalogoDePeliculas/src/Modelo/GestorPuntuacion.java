package Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GestorPuntuacion extends Observable{

	private static GestorPuntuacion GestorPuntuacion;
	private GestorPeliculas gestorPeliculas;
	private GestorUsuarios gestorUsuario;
	private List<Puntua> Puntuaciones;
	
	private GestorPuntuacion() throws SQLException {
		Puntuaciones = new ArrayList<Puntua>();
	}
	
	
	
	public static GestorPuntuacion getGestorPuntuacion()  {
		if(GestorPuntuacion == null) {
			try {
				GestorPuntuacion = new GestorPuntuacion();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return GestorPuntuacion;
	}
	
	
	public void addObserverJuego(Observer observer) {
        this.addObserver(observer);}
		
	
	
	
	
	public void  ValorarPelicula(String titulo, String  fecha, String comentario, Integer puntuacion) {
		Pelicula pelicula= gestorPeliculas.getGestorPeliculas().buscarPelicula(titulo,fecha);
		Usuario usuario=gestorUsuario.getGestorUsuarios().getUsuarioSesion();
		
		Puntua puntuacionExistente = getPuntuacionPorUsuarioYPelicula(usuario, pelicula);
		
		System.out.println(puntuacionExistente);//////para ver si guarda puntuaciones
		
		if (puntuacionExistente != null) {
			// Si ya existe, actualizar los valores
			puntuacionExistente.setComentario(comentario);
			puntuacionExistente.setPuntuacion(puntuacion);
		
			System.out.println("Puntuación actualizada correctamente.");

		}
		else {
			// Si no existe, crear una nueva puntuación
			Puntua nuevaPuntuacion = new Puntua(usuario, pelicula,comentario,puntuacion);
			Puntuaciones.add(nuevaPuntuacion);
			System.out.println("Nueva puntuación creada correctamente.");
		}
		
		
	}
			
	public Puntua getPuntuacionPorUsuarioYPelicula(Usuario usuario, Pelicula pelicula) {
	   System.out.println(Puntuaciones);
	    // Crear un iterador para recorrer la lista
	    Iterator<Puntua> iterator = Puntuaciones.iterator();

	    // Recorrer la lista buscando la coincidencia
	    while (iterator.hasNext()) {
	        Puntua puntua = iterator.next();
	        
	        // Comparar el usuario y la película
	        if (puntua.getUsuario().equals(usuario.getNombreUsuario()) && puntua.getPelicula().equals(pelicula.getTitulo(),pelicula.getFecha())) {
	            return puntua; // Retornar la puntuación si se encuentra
	        }
	    }

	    // Si no se encuentra, devolver null
	    return null;
	}
	
	public Integer getPuntuacion(Puntua puntu) {
		return puntu.getPuntuacion();
	}

}
