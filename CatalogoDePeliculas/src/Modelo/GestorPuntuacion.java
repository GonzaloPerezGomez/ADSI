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
		
	
	
	
	
	public void  ValorarPelicula(String titulo, String  fecha, String comentario, float puntuación) {
		Pelicula pelicula= gestorPeliculas.buscarPelicula(titulo,fecha);
		Usuario usuario=gestorUsuario.getUsuarioSesion();
		//cojer de la base de datps las puntuaciones de esa pelicula y añadirlas a la lista puntuaciones
		if ("lo de la base de datos"!=null) {
			//recogerlo de la base de datos
			
		}
		else {
			new Puntua(comentario, puntuación, usuario, pelicula);
			////añadirlo a la base de datos 
		}
		
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
