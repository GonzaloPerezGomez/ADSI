package Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
	
	public boolean modificarUsuariosAdmin(String nombre, String nombreUsuario, String esAdmin) { 
		 return GestorUsuarios.getGestorUsuarios().modificarUsuariosAdmin( nombre, nombreUsuario,(String) esAdmin);
	}
	
	public JSONObject obtenerInfoAdministrador(String pUsuario) {
		return GestorUsuarios.getGestorUsuarios().obtenerInfoAdministrador(pUsuario);
		
	}
	
	public boolean addUsuario(String nombre, String nombreUsuario,char[] contraseña) {
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
	public ArrayList<Pelicula> buscarPeliculas(String titulo){
		return GestorPeliculas.getGestorPeliculas().buscarPeliculas(titulo);
	}
}
