package Modelo;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class GestorGeneral {
	private static GestorGeneral gestorGeneral;
	
	private GestorGeneral() {
	}
	
	public static GestorGeneral getGestorGeneral() {
		if(gestorGeneral == null) {
			gestorGeneral = new GestorGeneral();
		}
		return gestorGeneral;
	}
	
	public void cargarBD() {
		try {
			GestorUsuarios.getGestorUsuarios().cargarUsuarios();
			GestorPeliculas.getGestorPeliculas().cargarPeliculas();
			GestorUsuarios.getGestorUsuarios().cargarSolicitudes();
			GestorPuntuacion.getGestorPuntuacion().cargarPuntuaciones();
			GestorAlquiler.getGestorAlquiler().cargarAlquila();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getRolSesion() {
		return GestorUsuarios.getGestorUsuarios().getRolSesion();
	}
	
	public void cerrarSesion() {
		GestorUsuarios.getGestorUsuarios().cerrarSesion();
	}
	
	public String obtenerUsuario() {
		return GestorUsuarios.getGestorUsuarios().getUsuarioSesion().getNombreUsuario();
	}
	
	public Usuario obtenerUsuarioActual() {
		
		 return GestorUsuarios.getGestorUsuarios().getUsuarioSesion();
	}
	
	public List<String> mostrarUsuarios(){
		return GestorUsuarios.getGestorUsuarios().mostrarUsuarios();
	}
	
	public void deleteUsuario(String usuario) {
		GestorUsuarios.getGestorUsuarios().deleteUsuario(usuario);
	}
	
	public boolean seInicia(String nombreUsuario, char[] contraseña ) {
		return GestorUsuarios.getGestorUsuarios().iniciarSesion( nombreUsuario, contraseña);
	}
	
	public JSONObject obtenerInfoUsuario() {
		return GestorUsuarios.getGestorUsuarios().obtenerInfoUsuario();
	}
	
	public boolean modificarUsuariosUsuario(String nombre, String nombreUsuario, String contraseña) { 
		 return GestorUsuarios.getGestorUsuarios().modificarUsuariosUsuario( nombre, nombreUsuario, contraseña);
	}
	
	public boolean modificarUsuariosAdmin(String nombre, String nombreUsuario, String nombreUsuarioAnt, String esAdmin) { 
		 return GestorUsuarios.getGestorUsuarios().modificarUsuariosAdmin( nombre, nombreUsuario, nombreUsuarioAnt, esAdmin);
	}
	
	public JSONObject obtenerInfoAdministrador(String pUsuario) {
		return GestorUsuarios.getGestorUsuarios().obtenerInfoAdministrador(pUsuario);
		
	}
	
	public boolean addUsuario(String nombre, String nombreUsuario, char[] contraseña) {
		return GestorUsuarios.getGestorUsuarios().addUsuario(nombre, nombreUsuario, contraseña);
	}
	
	public JSONObject solicitarAPI(String buscador) {
		return GestorPeliculas.getGestorPeliculas().solicitarAPI(buscador); 
	}
	
	public void gestionarSolicitud(String titulo, String director, String fecha) {
		GestorPeliculas.getGestorPeliculas().gestionarSolicitud(titulo, director, fecha);
	}
	
	public void aceptarSolicitud(String titulo, String director, String fecha) {
		GestorUsuarios.getGestorUsuarios().aceptarSolicitud(titulo, director, fecha);
	}
	
	public void rechazarSolicitud(String titulo, String fecha) {
		GestorUsuarios.getGestorUsuarios().rechazarSolicitud(titulo, fecha);
	}
	
	public Pelicula[] getSolicitudes() {
		return GestorUsuarios.getGestorUsuarios().getSolicitudes();
	}
	
	public List<String> mostrarUsuariosNoAceptados(){
		return GestorUsuarios.getGestorUsuarios().mostrarUsuariosNoAceptados();
	}
	
	public void aceptarUsuario(String usuario){
		GestorUsuarios.getGestorUsuarios().aceptarUsuario(usuario);
	}
	public JSONArray buscarPeliculas(String titulo){
		return GestorPeliculas.getGestorPeliculas().buscarPeliculas(titulo);
	}

	public void ValorarPelicula(JSONObject json ){
		 GestorPuntuacion.getGestorPuntuacion().ValorarPelicula(json);
	}
	public void alquilarPelicula(Usuario usuario, Pelicula pelicula) {
		GestorAlquiler.getGestorAlquiler().alquilarPelicula(usuario, pelicula);
	}

	public JSONObject getPeliculasAlquiladasPorUsuario() {
		Usuario usuarioActual = GestorUsuarios.getGestorUsuarios().getUsuarioSesion();
		List<Pelicula> alquiladasPorEl = GestorAlquiler.getGestorAlquiler().getPeliculasAlquiladasPorUsuario(usuarioActual);
		 System.out.println("gestorgeneral");
		System.out.println(alquiladasPorEl.isEmpty());
		JSONArray jsonPeliculas = GestorPeliculas.getGestorPeliculas().sacarInfo(alquiladasPorEl);
		JSONObject jsonResultado = new JSONObject();
		 // Añade el arreglo de películas al objeto JSON
		  jsonResultado.put("peliculas", jsonPeliculas);
		  return jsonResultado;
	}
	
	public List<Alquila> getAlquiladasPorUsuario(Usuario usuarioActual) {
		return GestorAlquiler.getGestorAlquiler().getAlquiladasPorUsuario(usuarioActual);
	}

	public JSONObject revisarPuntuacionexistente(JSONObject json) {
		Usuario usu = GestorUsuarios.getGestorUsuarios().getUsuarioSesion(); 
		Pelicula peli = GestorPeliculas.getGestorPeliculas().buscarPelicula(json.getString("titulo"));
		JSONObject resultado = new JSONObject();
	    try {
		
		Puntua puntu = GestorPuntuacion.getGestorPuntuacion().getPuntuacionPorUsuarioYPelicula(usu, peli);
		
		 if (puntu != null) {
	            resultado.put("comentario", puntu.getComentario());
	            resultado.put("puntuacion", puntu.getPuntuacion());
	        
		 }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return resultado;
	}

	public JSONObject obtenerPeliculasOrdenadasPorPuntuacionMedia() {
		JSONObject json = GestorPuntuacion.getGestorPuntuacion().CalcularMedia();
		return json;
	}

	public JSONObject obtenerComentariosYPuntuaciones(JSONObject json) {
		JSONObject json2 = GestorPuntuacion.getGestorPuntuacion().obtenerComentariosYPuntuaciones(json.getString("titulo"));
		return json2;
	}

	public JSONObject infoPelicula(JSONObject json) {
		JSONObject json2=GestorPeliculas.getGestorPeliculas().recogerInfo(json.getString("titulo"));
		return json2;
	}
	public Pelicula buscarPelicula(String titulo) {
		return GestorPeliculas.getGestorPeliculas().buscarPelicula(titulo);
	}
}