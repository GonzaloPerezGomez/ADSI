
package Modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import db.SQLite;

@SuppressWarnings("deprecation")
public class GestorPeliculas extends Observable{
	
	private static GestorPeliculas gestorPeliculas;
	private List<Pelicula> peliculas;
	
	private GestorPeliculas() throws SQLException {
		peliculas = new ArrayList<Pelicula>();

	}
	
	public static GestorPeliculas getGestorPeliculas(){
		if(gestorPeliculas == null) {
			try {
				gestorPeliculas = new GestorPeliculas();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return gestorPeliculas;
	}
	
	public void cargarPeliculas() throws SQLException {
		System.out.println(peliculas);
		peliculas.addAll(SQLite.getBaseDeDatos().getAllPeliculas());
		System.out.println(peliculas);
	}
	
	public void addPelicula(Pelicula pPelicula) {
		peliculas.add(pPelicula);
		
		String sql = "UPDATE Pelicula SET aceptadoPor= '" + pPelicula.getAceptadoPor() + "' WHERE titulo = '" + pPelicula.getTitulo() + "' AND fecha = '" + pPelicula.getFecha() + "' ";
		SQLite.getBaseDeDatos().execSQL(sql);
	}
	
	public void deletePelicula(Pelicula pPelicula) {
		peliculas.remove(pPelicula);
	}
	
	public void gestionarSolicitud(String titulo, String director, String fecha) {
		if(!existe(titulo, fecha)) {
			Pelicula p = new Pelicula(titulo, director, fecha);
			GestorUsuarios.getGestorUsuarios().addSolicitud(p);
		}
		else {
			JOptionPane.showMessageDialog(null, "La pelicula solicitada ya está en el catalogo");
		}
	}
	
	private boolean existe(String titulo, String fecha) {
		return peliculas.stream().anyMatch(p -> p.equals(titulo));
	}

	public JSONObject solicitarAPI(String pTitulo) {
		JSONObject info;
		try {
			String apiKey = "58e6802f";
			String urlString = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + pTitulo.replace(" ", "%20");
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer respuesta = new StringBuffer();
			
			while((inputLine = in.readLine()) != null) {
				respuesta.append(inputLine);
			}
			in.close();
			
			info = new JSONObject(respuesta.toString());
		
			if(info.has("Response") && info.getString("Response").equals("True")) {
		        return info;
			}
			else {
				JOptionPane.showMessageDialog(null, "Película no encontrada");
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public Iterator<Pelicula> getIteratorPelicula() {
		return peliculas.iterator();
	}
	
	public Pelicula buscarPelicula(String titulo) {
		
		Iterator<Pelicula> iterador= getIteratorPelicula();
		while (iterador.hasNext()) {
            Pelicula pelicula = iterador.next();            
            if (pelicula.equals(titulo)) {
                return pelicula; // Retorna la película si coincide
            }
		}
		return null;
	}
		public JSONArray buscarPeliculas(String titulo) {
			//devuelve las peliculas que coincidan con el titulo introucido
			JSONArray peliculas = new JSONArray();
			Iterator<Pelicula> iterador= getIteratorPelicula();
			while (iterador.hasNext()) {
	            Pelicula pelicula = iterador.next();            
	            if (pelicula.getTitulo().toLowerCase().contains(titulo.toLowerCase())) { //comprueba si coincide el nombre con el titulo de la pelicula, si coincide lo añade
	            	JSONObject jsonPelicula = new JSONObject();
			        jsonPelicula.put("titulo", pelicula.getTitulo());
			        jsonPelicula.put("fecha", pelicula.getFecha());
			        jsonPelicula.put("director", pelicula.getDirector());
			        peliculas.put(jsonPelicula);
	            }
		}
	return peliculas; //devuelve las peliculas coincidentes
}

		public JSONObject recogerInfo(String titulo) {
			
			
			Pelicula peli= buscarPelicula(titulo);
			JSONObject json = new JSONObject();
		    json.put("titulo", peli.getTitulo());
	        json.put("fecha", peli.getFecha());
	        json.put("director", peli.getDirector());
	       
			return json;
		}

		public JSONArray sacarInfo(List<Pelicula> alquiladasPorEl) {
			JSONArray jsonPeliculas = new JSONArray();

		    // Llena el arreglo JSON con los títulos y fechas de las películas
		    for (Pelicula pelicula : alquiladasPorEl) {
		        JSONObject jsonPelicula = new JSONObject();
		        jsonPelicula.put("titulo", pelicula.getTitulo());
		        jsonPelicula.put("fecha", pelicula.getFecha());
		        jsonPeliculas.put(jsonPelicula);
		    }
			return jsonPeliculas;
		}
		
		public void reset() {
			gestorPeliculas = null;
		}

}	
	
