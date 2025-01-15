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
		peliculas.addAll(SQLite.getBaseDeDatos().getAllPeliculas());
		System.out.print(peliculas);
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
	
	public Iterator<Pelicula> getIteratorPelicula() {
		return peliculas.iterator();
	}
	
	public Pelicula buscarPelicula(String titulo, String fecha ) {
		
		Iterator<Pelicula> iterador= getIteratorPelicula();
		while (iterador.hasNext()) {
            Pelicula pelicula = iterador.next();            
            if (pelicula.equals(titulo, fecha)) {
                return pelicula; // Retorna la película si coincide
            }
		}
		return null;
	}
		public ArrayList<Pelicula> buscarPeliculas(String titulo) {
			ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();
			Iterator<Pelicula> iterador= getIteratorPelicula();
			while (iterador.hasNext()) {
	            Pelicula pelicula = iterador.next();            
	            if (pelicula.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
	               peliculas.add(pelicula);
	            }
		}
	return peliculas;
}
		public boolean estaAlquilada(Usuario usuario, Pelicula pelicula) {
			List<Alquila> alquiladas = GestorAlquiler.getGestorAlquiler().getAlquiladasPorUsuario(usuario);
			for(int i = 0; i < alquiladas.size(); i++) {
				if (pelicula.equals(alquiladas.get(i).getPelicula())) {
					Instant ahora = Instant.now();
					Duration diff = Duration.between(ahora, (Temporal) alquiladas.get(i).getFecha());
					if (diff.toHours() < 48) {
						return true;
					}
				}
			}
			return false;
		}

}	
	