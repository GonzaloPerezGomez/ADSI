package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class GestorPeliculas extends Observable{
	
	private static GestorPeliculas gestorPeliculas = new GestorPeliculas();
	private List<Pelicula> peliculas;
	
	private GestorPeliculas() {
		peliculas = new ArrayList<Pelicula>();
		cargarDatos();
	}
	
	public GestorPeliculas getGestorPeliculas(){
		if(gestorPeliculas == null) {
			gestorPeliculas = new GestorPeliculas();
		}
		return gestorPeliculas;
	}
	
	private void cargarDatos() {
		//TODO: Cargar los datos de la base de datos
	}
	
	public void addPelicula(Pelicula pPelicula) {
		peliculas.add(pPelicula);
	}
	
	public void deletePelicula(Pelicula pPelicula) {
		peliculas.remove(pPelicula);
	}
	
	public List<Pelicula> solicitarAPI(String titulo) {
		return null;
	}

}
