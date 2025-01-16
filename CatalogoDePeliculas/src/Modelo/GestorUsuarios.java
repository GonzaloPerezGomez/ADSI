package Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import org.json.JSONArray;
import org.json.JSONObject;

import db.SQLite;

import java.util.Iterator;

@SuppressWarnings("deprecation")
public class GestorUsuarios{
	
	private static GestorUsuarios gestorUsuarios;
	private List<Usuario> usuarios;
	private String usuarioSesion;
	
	private GestorUsuarios() throws SQLException {
		usuarios = new ArrayList<Usuario>();
		
	/////pruebas para punuaciones y demas
		Usuario p1 = new Usuario("b","b","Hola1234.",false);
		Usuario p2 = new Usuario("bb","bb","Hola1234.",false);
		Usuario p3 = new Usuario("bbb","bbb","Hola1234.",false);
		usuarios.add(p3);usuarios.add(p2);usuarios.add(p1);
	}
	
	public static GestorUsuarios getGestorUsuarios() {
		if(gestorUsuarios == null) {
			try {
				gestorUsuarios = new GestorUsuarios();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return gestorUsuarios;
	}
	
	public void cargarUsuarios() throws SQLException {
		usuarios.add(new Usuario("a","a","a",true));
		usuarioSesion = "a";
		
		usuarios.addAll(SQLite.getBaseDeDatos().getAllUsuarios());
	}
	
	public void cargarSolicitudes() throws SQLException {
		
		JSONArray result;
		if((result = SQLite.getBaseDeDatos().getAllSolicitudes()) != null) {
			for (int i = 0; i < result.length(); i++) {
				JSONObject solicitud = result.getJSONObject(i);
				
				Usuario u = this.buscarUsuario(solicitud.getString("nombreUsuario"));
				
				u.cargarSolicitud(new Pelicula(solicitud.getString("titulo"), solicitud.getString("director"), solicitud.getString("fecha")));
			}
		}
		
	}
	
	public String getNombreUsuario(Usuario usu) {
		return usu.getNombreUsuario();
	}
	public boolean addUsuario(String nombre, String nombreUsuario, char[] contraseña) {
		if (buscarUsuario(nombreUsuario) == null) {
			if(nombre == " " || nombreUsuario == " " || contraseña.length==0 ) {
				System.out.println("uno de los campos no esta completado");
				return false;
			}
			else {
				String contraseñaString =  new String(contraseña);
				if (esContraseñaValida(contraseñaString)) {
					Usuario pUsuario = new Usuario(nombre, nombreUsuario, contraseñaString, false);
					usuarios.add(pUsuario);
					usuarioSesion = nombreUsuario;
					System.out.println("iniciado correctamente");
					return true;}
				
				else {
					System.out.println("La contraseña no es valida");
					return false;}
			}	
		}
		else {
			System.out.println("Ya existe un usuario con ese nombre de usuario");
			return false;}
	}
	
	private boolean esContraseñaValida(String contraseña) {
		if (contraseña.length() < 8) {
			System.out.println("Su tamaño tiene que ser minimo de ocho caracteres");
			return false;}
        if (!contraseña.matches(".*[A-Z].*")) {
	        System.out.println("Tiene que tener al menos una letras en mayuscula");
	        return false;}
        if (!contraseña.matches(".*[a-z].*")) {
	        System.out.println("Tiene que tener al menos una letras en minusculas");
	        return false;}
        if (!contraseña.matches(".*\\d.*")) {
        	System.out.println("Tiene que tener al menos un número");
        	return false;}
        if (!contraseña.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
	        System.out.println("Tiene que contener caracteres especiales");
	        return false;}
        if (contraseña.contains(" ")) {
        	System.out.println("No puede contener espacios");
        	return false;}
        return true;
    }
	
	public boolean iniciarSesion(String nombreUsuario, char[] contraseña) {
		Usuario pUsuario = buscarUsuario(nombreUsuario);
		if (pUsuario != null) {
			if (pUsuario.esCorrectaLaContraseña(new String(contraseña))) {
				usuarioSesion = nombreUsuario;
				System.out.println("Sesión iniciada correctamente");
				return true;}
			else {
				System.out.println("La contraseña no es correcta");
				return false;}}
		else {
			System.out.println("No existe un usuario con ese nombre de usuario");
			return false;}
	}
	
	public void deleteUsuario(String pUsuario) {
		if (pUsuario != usuarioSesion){
			Usuario pUsu = buscarUsuario(pUsuario);
			usuarios.remove(pUsu);
		}
		else {
			System.out.println("no te puedes eliminar a ti mismo");
		}
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
		
		String sql = "DELETE FROM Solicitud WHERE titulo = '" + titulo + "' AND fecha = '" + fecha + "' ";
		SQLite.getBaseDeDatos().execSQL(sql);
	}

	public void rechazarSolicitud(String titulo, String fecha) {
		deleteSolicitudes(titulo, fecha);
		
		String sql = "DELETE FROM Solicitud WHERE titulo = '" + titulo + "' AND fecha = '" + fecha + "' ";
		SQLite.getBaseDeDatos().execSQL(sql);
		
		sql = "DELETE FROM Pelicula WHERE titulo = '" + titulo + "' AND fecha = '" + fecha + "' ";
		SQLite.getBaseDeDatos().execSQL(sql);
	}
	
	public List<String> mostrarUsuariosNoAceptados(){
		List<String> listaUsuario = new ArrayList<String>();
		Iterator<Usuario> iterador= getIteratorUsuario();
		while (iterador.hasNext()) {
            Usuario usuario = iterador.next();            
            if (!usuario.estaAceptada()) {
            	listaUsuario.add(usuario.getNombreUsuario());}}
		return listaUsuario;	
	}
	
	private Iterator<Usuario> getIteratorUsuario() {
		return usuarios.iterator();
	}
	
	public void aceptarUsuario(String pUsuario) {
		Usuario aceptado = buscarUsuario(pUsuario);
		String administrador = usuarioSesion;
		aceptado.aceptar(administrador);
		
	}
	
	public List<String> mostrarUsuarios(){
		List<String> lista = new ArrayList<String>();
		for(Usuario pUs: usuarios) {
			lista.add(pUs.getNombreUsuario());}
		return lista;
	}
	
	public boolean modificarUsuariosAdmin(String pNombreUsuario, String pNombre, String pAdmin) {
		if(!pNombreUsuario.trim().isEmpty() && !pNombre.trim().isEmpty() && !pAdmin.trim().isEmpty()) {
			Usuario usu = buscarUsuario(pNombreUsuario);
			if (!usuarioSesion.equals(pNombreUsuario)) {
				usu=buscarUsuario(usuarioSesion);
				usuarioSesion=pNombreUsuario;
				usu.setNombreContraseña(usuarioSesion, pNombre, pAdmin);}
			else {
				usu.setNombreContraseña(usuarioSesion, pNombre, pAdmin);}
			return true;}
		else{
			System.out.println("uno de los campos no esta completado");
			return false;}
	}
	
	public boolean modificarUsuariosUsuario(String pNombreUsuario, String pNombre, String pContraseña) {
		if(!pNombreUsuario.trim().isEmpty() && !pNombre.trim().isEmpty() && !pContraseña.trim().isEmpty() ) {
			Usuario usu = buscarUsuario(pNombreUsuario);
			if (!usuarioSesion.equals(pNombreUsuario)) {
					if(esContraseñaValida(pContraseña)) {
						usu=buscarUsuario(usuarioSesion);
						usuarioSesion=pNombreUsuario;
						usu.setNombreContraseña(usuarioSesion, pNombre, pContraseña);
						return true;}
					else {System.out.println("contraseña no valida");}}
			else {
				if(esContraseñaValida(pContraseña)) {
					usu.setNombreContraseña(usuarioSesion, pNombre, pContraseña);
					return true;}
				else {System.out.println("contraseña no valida");}}}
		else{System.out.println("uno de los campos no esta completado");}
		return false;
	}
	
	public JSONObject obtenerInfoAdministrador(String pUsuarioInfor) {
		System.out.println(pUsuarioInfor);
		Usuario usuario = buscarUsuario(pUsuarioInfor);
		return  usuario.getInfoAdministrador();
	}
	
	public JSONObject obtenerInfoUsuario() {
		Usuario usuario = buscarUsuario(usuarioSesion);
		return  usuario.getInfoUsuario();
	}
}