package Modelo;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GestorPuntuacion extends Observable{

	private static GestorPuntuacion GestorPuntuacion;
	private GestorPeliculas gestorPeliculas;
	private GestorUsuarios gestorUsuario;
	
	
	
	
	
	public static GestorPuntuacion getGestorPuntuacion() {
		if(GestorPuntuacion == null) {
			GestorPuntuacion = new GestorPuntuacion();
		}
		return GestorPuntuacion;
	}
	
	
	public void addObserverJuego(Observer observer) {
        this.addObserver(observer);}
		
	
	
	
	
	public void  ValorarPelicula(String titulo, String  fecha, String comentario, Integer puntuacion) {
		Pelicula pelicula= gestorPeliculas.buscarPelicula(titulo,fecha);
		Usuario usuario=gestorUsuario.getUsuarioSesion();
		//comprobar si existia o no la puntuacion en la base de datos
		// Buscar si ya existe una puntuación para este usuario y película
		Puntua puntuacionExistente = getPuntuacionPorUsuarioYPelicula(usuario, pelicula);

		if (puntuacionExistente != null) {
			// Si ya existe, actualizar los valores
			puntuacionExistente.setComentario(comentario);
			puntuacionExistente.setPuntuacion(puntuacion);
	
			// Guardar los cambios en la base de datos
			actualizarPuntuacion(puntuacionExistente);
			System.out.println("Puntuación actualizada correctamente.");

		}
		else {
			// Si no existe, crear una nueva puntuación
			Puntua nuevaPuntuacion = new Puntua(usuario, pelicula,comentario,puntuacion);

			// Guardar la nueva puntuación en la base de datos
			guardarNuevaPuntuacion(nuevaPuntuacion);
			System.out.println("Nueva puntuación creada correctamente.");
		}
		
		
	}
			
	public Puntua getPuntuacionPorUsuarioYPelicula(Usuario usuario, Pelicula pelicula) {
		// Lógica para buscar en la base de datos
		String titulo= pelicula.getTitulo();
		Puntua puntuacion=new Puntua(null,null,null,null);
		String query = "SELECT puntuacion FROM Puntua WHERE nombreUsuario = ? AND titulo= ?";
		puntuacion.setPuntuacion(Integer.parseInt(query));
		String query2 = "SELECT comentario FROM Puntua WHERE nombreUsuario = ? AND titulo= ?";
		puntuacion.setComentario(query2);
		 
		// Retorna un objeto Puntuacion o null si no se encuentra
		return puntuacion;
	}
	
	public void actualizarPuntuacion(Puntua puntuacion) {
		// Lógica para actualizar en la base de datos
		// Ejemplo: UPDATE Puntua SET puntuacion = ?, comentario = ? WHERE pelicula = ? AND usuario = ?
	}
	
	
	public void guardarNuevaPuntuacion(Puntua puntuacion) {
		// Lógica para insertar en la base de datos
		// Ejemplo: INSERT INTO Puntuaciones (usuario_id, pelicula_id, puntuacion, comentario) VALUES (?, ?, ?, ?)
	}
				
//	public Iterator<Puntua> getIteratorPuntuaciones() {
//		return puntuaciones.iterator();
//	}
//	
//	public Puntua buscarPuntuacion(Pelicula peli, Usuario usu ) {
//		
//		Iterator<Puntua> iterador= getIteratorPuntuaciones();
//		while (iterador.hasNext()) {
//            Puntua puntu = iterador.next();            
//            if (puntu.equals(peli, usu)) {
//                return puntu; 
//            }
//		
//	}
//	return null;
//}
}
