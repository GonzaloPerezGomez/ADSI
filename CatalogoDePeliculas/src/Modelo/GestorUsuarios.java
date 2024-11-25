package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class GestorUsuarios extends Observable{
	
	private static GestorUsuarios gestorUsuarios;
	private List<Usuario> usuarios;
	private String usuarioSesion;
	
	private GestorUsuarios() {
		usuarios = new ArrayList<Usuario>();
		cargarDatos();
	}
	
	public static GestorUsuarios getGestorUsuarios() {
		if(gestorUsuarios == null) {
			gestorUsuarios = new GestorUsuarios();
		}
		return gestorUsuarios;
	}
	
	private void cargarDatos() {
		usuarios.add(new Usuario("a","a","a",false));
		usuarioSesion = "a";
	}
	
	public void addUsuario(Usuario pUsuario) {
		usuarios.add(pUsuario);
	}
	
	public void deleteUsuario(Usuario pUsuario) {
		usuarios.remove(pUsuario);
	}
	
	public Usuario buscarUsuario(String pNombre) {
		return usuarios.stream().filter(p -> p.equals(pNombre)).findFirst().orElse(null);
	}
	
	public void addSolicitud(Pelicula p) {
		Usuario u;
		if((u = buscarUsuario(usuarioSesion)) != null)
			u.addSolicitud(p);
	}
	
	public boolean getRolSesion() {
		return buscarUsuario(usuarioSesion).isAdmin();
	}
	
}
