package Modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class GestorPeliculas extends Observable{
	
	private static GestorPeliculas gestorPeliculas = new GestorPeliculas();
	private List<Pelicula> peliculas;
	
	private GestorPeliculas() {
		peliculas = new ArrayList<Pelicula>();
		cargarDatos();
	}
	
	public static GestorPeliculas getGestorPeliculas(){
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
	
	public void gestionarSolicitud(String titulo, String director, String fecha) {
		if(!existe(titulo, fecha)) {
			Pelicula p = new Pelicula(titulo, director, fecha);
			GestorUsuarios.getGestorUsuarios().addSolicitud(p);
		}
	}
	
	private boolean existe(String titulo, String fecha) {
		return peliculas.stream().anyMatch(p -> p.equals(titulo, fecha));
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
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return null;
	}

	
}