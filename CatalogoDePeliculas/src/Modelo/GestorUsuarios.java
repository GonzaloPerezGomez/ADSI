package Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import db.SQLite;

public class GestorUsuarios{
	
	private static GestorUsuarios gestorUsuarios;
	private List<Usuario> usuarios;
	private String usuarioSesion;
	
	private GestorUsuarios() throws SQLException {
		usuarios = new ArrayList<Usuario>();
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

	//Carga todos los usuarios de la base de datos
	public void cargarUsuarios() throws SQLException {
		usuarios.addAll(SQLite.getBaseDeDatos().getAllUsuarios());
	}
	
	//Carga todas las solicitudes de la base de datos
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
	
	//Devulve el nombre de usuario de un usuario
	public String getNombreUsuario(Usuario usu) {
		return usu.getNombreUsuario();
	}
	
	//Dado un nombre, nombre de usuario y contraseña se añade un usuario al gestor
	public boolean addUsuario(String nombre, String nombreUsuario, char[] contraseña) {
		if (nombre.matches("^[a-zA-Z]+$")) {
			if (buscarUsuario(nombreUsuario) == null) {
				if(nombre == " " || nombreUsuario == " " || contraseña.length==0 ) {
					System.out.println("uno de los campos no esta completado");
					return false;
				}
				
				else {
					String contraseñaString =  new String(contraseña);
					if (esContraseñaValida(contraseñaString)) {
						Usuario pUsuario = new Usuario(nombre, nombreUsuario, contraseñaString);
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
		else {
			System.out.println("el campo nombre solo pueden ser letras");
			return false;
		}
	}
	
	//Se verifica que la contraseña es valida
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
	
	//Se comprueban las credenciales de un usuario, si son correctas de inicia la sesión
	public boolean iniciarSesion(String nombreUsuario, char[] contraseña) {
		Usuario pUsuario = buscarUsuario(nombreUsuario);
		if (pUsuario != null) {
			if (!pUsuario.estaEliminado()) {
				if (pUsuario.esCorrectaLaContraseña(new String(contraseña))) {
					usuarioSesion = nombreUsuario;
					System.out.println("Sesión iniciada correctamente");
					return true;}
				else {
					System.out.println("La contraseña no es correcta");
					return false;}
			}
			else {
				System.out.println("El usuario se ha eliminado");
				return false;}
			}
		else {
			System.out.println("No existe un usuario con ese nombre de usuario");
			return false;}
	}
	
	//Elimina un usuario del gestor
	public void deleteUsuario(String pUsuario) {
		if (pUsuario != usuarioSesion){
			Usuario usu = buscarUsuario(pUsuario);
			usu.eliminar();
			this.usuarios.remove(usu);
			System.out.println("usuario eliminado");
		} else {
			System.out.println("no te puedes eliminar a ti mismo");
		}
	}
	
	//Dado un nombre de usuario se devuelve el objeto del usuario
	public Usuario buscarUsuario(String pNombre) {
		return usuarios.stream().filter(p -> p.equals(pNombre)).findFirst().orElse(null);
	}
	
	//Se devuelve el objeto del usuario que tiene la sesión iniciada
	public Usuario getUsuarioSesion() {
		return usuarios.stream().filter(p -> p.equals(usuarioSesion)).findFirst().orElse(null);
	}
	
	//Dada una peli, se añade al usuario con la sesion iniciada dicha peli a la lista de solicitudes
	public void addSolicitud(Pelicula p) {
		Usuario u;
		if((u = buscarUsuario(usuarioSesion)) != null)
			u.addSolicitud(p);
	}
	
	//Se devuelve el rol del usuario con la sesion iniciada
	public boolean getRolSesion() {
		return buscarUsuario(usuarioSesion).isAdmin();
	}
	
	//Se cierra la sesion
	public void cerrarSesion() {
		usuarioSesion = null;
	}
	
	//Se recoge entre todos los usuarios todas las peliculas solicitadas
	public Pelicula[] getSolicitudes() {
		List<Pelicula> s = new ArrayList<Pelicula>();
		
		usuarios.forEach(x-> s.addAll(x.getPeliculasSolicitadas()));
		
		Pelicula[] l = new Pelicula[s.size()];
		for(int i=0; i<s.size(); i++) {
			l[i] = s.get(i);
		}
		
		return l;
	}

	//Se añade la pelicula al catalogo y se elimina la solicitud del usuario
	public void aceptarSolicitud(String titulo, String director, String fecha) {
		GestorPeliculas.getGestorPeliculas().addPelicula(new Pelicula(titulo, director, fecha, usuarioSesion));
		deleteSolicitudes(titulo, fecha);
		
		JOptionPane.showMessageDialog(null, "Solicitud aceptada correctamente");
	}
	
	//Se elimina la solitud tanto del usuario como de la base de datos
	private void deleteSolicitudes(String titulo, String fecha) {
		for (Usuario u : usuarios) {
			u.deleteSolicitud(new Pelicula(titulo, null, fecha));
		}
		
		String sql = "DELETE FROM Solicitud WHERE titulo = '" + titulo + "' AND fecha = '" + fecha + "' ";
		SQLite.getBaseDeDatos().execSQL(sql);
	}

	//Se elimina la solicitud del usuario
	public void rechazarSolicitud(String titulo, String fecha) {
		deleteSolicitudes(titulo, fecha);
		
		String sql = "DELETE FROM Solicitud WHERE titulo = '" + titulo + "' AND fecha = '" + fecha + "' ";
		SQLite.getBaseDeDatos().execSQL(sql);
		
		sql = "DELETE FROM Pelicula WHERE titulo = '" + titulo + "' AND fecha = '" + fecha + "' ";
		SQLite.getBaseDeDatos().execSQL(sql);
		
		JOptionPane.showMessageDialog(null, "Solicitud rechazada correctamente");
	}
	
	public List<String> mostrarUsuariosNoAceptados(){
		List<String> listaUsuario = new ArrayList<String>();
		Iterator<Usuario> iterador= getIteratorUsuario();
		while (iterador.hasNext()) {
            Usuario usuario = iterador.next();            
            if (!usuario.estaAceptada() && !usuario.estaEliminado()) {
            	listaUsuario.add(usuario.getNombreUsuario());}}
		return listaUsuario;	
	}
	
	private Iterator<Usuario> getIteratorUsuario() {
		return usuarios.iterator();
	}
	
	public void aceptarUsuario(String pUsuario) {
		Usuario aceptado = buscarUsuario(pUsuario);
		aceptado.aceptar(usuarioSesion);
		
	}
	
	public List<String> mostrarUsuarios(){
		List<String> lista = new ArrayList<String>();
		for(Usuario pUs: usuarios) {
			if (!pUs.estaEliminado()) {
				lista.add(pUs.getNombreUsuario());	
			}
		}	
		return lista;
	}
	
	public boolean modificarUsuariosAdmin(String pNombre, String pNombreUsuario, String pNombreUsuarioAnt, String pAdmin) {
		if (pNombre.matches("^[a-zA-Z]+$")) {
			if (!pNombreUsuario.equals(pNombreUsuarioAnt)) {
				Usuario usu = buscarUsuario(pNombreUsuario);
				if (usu==null) {
					if(!pNombreUsuario.trim().isEmpty() && !pNombre.trim().isEmpty() && !pAdmin.trim().isEmpty()) {
						usu = buscarUsuario(pNombreUsuarioAnt);
						usu.setNombreContraseñaAdmin(pNombre, pNombreUsuario, pAdmin);
						if (usuarioSesion.equals(pNombreUsuarioAnt)){
							usuarioSesion = pNombreUsuario;
						}
						return true;
					}
					else {
						System.out.println("uno de los campos no esta completado");
						return false;
					}
				}
				else {
					System.out.println("Ya existe un usuario con ese nombre de usuario");
					return false;
				}
		
			}
			else {
				Usuario usu=buscarUsuario(pNombreUsuarioAnt);
				usu.setNombreContraseñaAdmin(pNombre, pNombreUsuario, pAdmin);
				return true;
			}
		}
		else {
			System.out.println("el campo nombre solo pueden ser letras");
			return false;
		}
	}
	
	public boolean modificarUsuariosUsuario(String pNombre, String pNombreUsuario,  String pContraseña) {
		if (pNombre.matches("^[a-zA-Z]+$")) {
			if (!pNombreUsuario.equals(usuarioSesion)) {
				Usuario usu = buscarUsuario(pNombreUsuario);
				if (usu == null) {
					if(!pNombreUsuario.trim().isEmpty() && !pNombre.trim().isEmpty() && !pContraseña.trim().isEmpty() ) {
						if(this.esContraseñaValida(pContraseña)){
							usu = buscarUsuario(usuarioSesion);
							usuarioSesion=pNombreUsuario;
							usu.setNombreContraseñaUsuario(pNombre, pNombreUsuario, pContraseña);
							return true;
						}
						else {
							System.out.println("contraseña no valida");
							return false;
						}
					}
					else {
						System.out.println("uno de los campos no esta completado");
						return false;
					}
				}
				else{
					System.out.println("Ya existe un usuario con ese nombre de usuario");
					return false;
				}
			}
			else {
				if(this.esContraseñaValida(pContraseña)){
					Usuario usu=buscarUsuario(usuarioSesion);
					usu.setNombreContraseñaUsuario(pNombre, usuarioSesion, pContraseña);
					return true;
				}
				else {
					System.out.println("contraseña no valida");
					return false;
				}
			}
		}
		else {
			System.out.println("el campo nombre solo pueden ser letras");
			return false;
		}
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
	
	//Para tests
	public void reset() {
		gestorUsuarios = null;
	}
}