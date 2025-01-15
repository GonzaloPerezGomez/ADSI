package Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class GestorPuntuacion extends Observable{

	private static GestorPuntuacion GestorPuntuacion;
	private GestorPeliculas gestorPeliculas;
	private GestorUsuarios gestorUsuario;
	private List<Puntua> Puntuaciones;
	
	private GestorPuntuacion() throws SQLException {
		Puntuaciones = new ArrayList<Puntua>();
		
		Usuario p1 = new Usuario("b","b","Hola1234.",false);
		Pelicula p22 = new Pelicula("rr","ff","2000-01-02");
		Puntua puntu = new Puntua(p1,p22,"jhfdkdgs",4);
		Puntuaciones.add(puntu);
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
		Pelicula pelicula= gestorPeliculas.getGestorPeliculas().buscarPelicula(titulo);
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
	        
	        System.out.println( puntua.getPelicula());
	        
	        // Comparar el usuario y la película
	        if (puntua.getUsuario().equals(usuario.getNombreUsuario()) && puntua.getPelicula().equals(pelicula.getTitulo())) {
	            return puntua; // Retornar la puntuación si se encuentra
	        }
	    }

	    // Si no se encuentra, devolver null
	    return null;
	}
	
	public Integer getPuntuacion(Puntua puntu) {
		return puntu.getPuntuacion();
	}



	public JSONObject CalcularMedia() {
		
	    Map<Pelicula, List<Integer>> peliculaPuntuaciones = new HashMap<>();

	    // Agrupar puntuaciones por película
	    for (Puntua puntuacion : Puntuaciones) {
	        Pelicula pelicula = puntuacion.getPelicula();
	        peliculaPuntuaciones.putIfAbsent(pelicula, new ArrayList<>());
	        peliculaPuntuaciones.get(pelicula).add(puntuacion.getPuntuacion());
	    }

	    // Calcular la media de puntuaciones para cada película
	    Map<Pelicula, Double> peliculaPuntuacionMedia = new HashMap<>();
	    for (Map.Entry<Pelicula, List<Integer>> entry : peliculaPuntuaciones.entrySet()) {
	        List<Integer> puntuaciones = entry.getValue();
	        double media = puntuaciones.stream().mapToInt(Integer::intValue).average().orElse(0);
	        peliculaPuntuacionMedia.put(entry.getKey(), media);
	    }

	    // Ordenar las películas por puntuación media en orden descendente
	    List<Map.Entry<Pelicula, Double>> listaOrdenada = new ArrayList<>(peliculaPuntuacionMedia.entrySet());
	    listaOrdenada.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));

	    // Crear el JSON con la lista ordenada
	    JSONObject resultado = new JSONObject();
	    for (Map.Entry<Pelicula, Double> entry : listaOrdenada) {
	        JSONObject peliculaJson = new JSONObject();
	        peliculaJson.put("titulo", entry.getKey().getTitulo());
	        peliculaJson.put("fecha", entry.getKey().getFecha());
	        peliculaJson.put("puntuacionMedia", entry.getValue());
	        resultado.append("peliculas", peliculaJson);
	    }

	    return resultado;
	}



	public JSONObject obtenerComentariosYPuntuaciones(String titulo) {
		List<Puntua> puntuaciones = obtenerPuntuacionesPorPelicula(titulo);
	    puntuaciones.sort((p1, p2) -> Integer.compare(p2.getPuntuacion(), p1.getPuntuacion()));
	    
	    System.out.println(puntuaciones);
	    
	    JSONObject resultado = new JSONObject();
	    JSONArray comentariosArray = new JSONArray();

	    for (Puntua puntuacion : puntuaciones) {
	        JSONObject comentarioJson = new JSONObject();
	        String usu = GestorUsuarios.getGestorUsuarios().getNombreUsuario(puntuacion.getUsuario());
	        comentarioJson.put("usuario", usu);
	        comentarioJson.put("puntuacion", puntuacion.getPuntuacion());
	        comentarioJson.put("comentario", puntuacion.getComentario());
	        comentariosArray.put(comentarioJson);
	    }

	    resultado.put("comentarios", comentariosArray);
	    System.out.println(resultado);
	    return resultado;
	}

	private List<Puntua> obtenerPuntuacionesPorPelicula(String titulo) {
	    // Implementar la lógica para obtener todas las puntuaciones de una película de la base de datos
	    return Puntuaciones.stream().filter(p -> p.getPelicula().getTitulo().equals(titulo)).collect(Collectors.toList());
	}


}
