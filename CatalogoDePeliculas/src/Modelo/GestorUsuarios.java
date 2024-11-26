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
		usuarios.add(new Usuario("a","a","a",true));
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
	
	public Usuario getUsuarioSesion() {
		return usuarios.stream().filter(p -> p.equals(usuarioSesion)).findFirst().orElse(null);
	}
	
	public void addSolicitud(Pelicula p) {
		Usuario u;
		if((u = buscarUsuario(usuarioSesion)) != null)
			u.addSolicitud(p);
	}
	
	public boolean getRolSesion() {
		return buscarUsuario(usuarioSesion).isAdmin();
	}
	
	public void cerrarSesion() {
		usuarioSesion = null;
	}
	
	public Pelicula[] getSolicitudes() {
		List<Pelicula> s = new ArrayList<Pelicula>();
		
		usuarios.forEach(x-> s.addAll(x.getPeliculasSolicitadas()));
		
		Pelicula[] l = new Pelicula[s.size()];
		for(int i=0; i<s.size(); i++) {
			l[i] = s.get(i);
		}
		
		return l;
	}

	public void aceptarSolicitud(String titulo, String director, String fecha) {
		GestorPeliculas.getGestorPeliculas().addPelicula(new Pelicula(titulo, director, fecha, usuarioSesion));
		deleteSolicitudes(titulo, fecha);
		
	}
	
	private void deleteSolicitudes(String titulo, String fecha) {
		for (Usuario u : usuarios) {
			u.deleteSolicitud(new Pelicula(titulo, null, fecha));
		}
	}

	public void rechazarSolicitud(String titulo, String fecha) {
		deleteSolicitudes(titulo, fecha);
	}
	
}
