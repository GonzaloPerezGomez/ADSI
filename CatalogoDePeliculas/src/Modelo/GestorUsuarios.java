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
		usuarios = new ArrayList<Usuario>();// crea el array de usuarios
	}
	
	public static GestorUsuarios getGestorUsuarios() {
		if(gestorUsuarios == null) {//si el atributo getorUsuario es null
			try {//intenta
				gestorUsuarios = new GestorUsuarios();//crea un nuevo gestorUsuario
			} catch (SQLException e) {//atrapa al excepcion
				e.printStackTrace(); // imprime el error
			}
		}
		return gestorUsuarios;// devuelve el gestorUsuario
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
		if (nombre.matches("^[a-zA-Z]+$")) {// si nombre es son todo letras
			if (buscarUsuario(nombreUsuario) == null) {// si no existe un usuario con el nombre de Usuario que se ha introducido
				if(nombre == " " || nombreUsuario == " " || contraseña.length==0 ) { // si alguno de los campos no tiene valores
					System.out.println("uno de los campos no esta completado"); ////imprimir el fallo por consalo
					return false;// devuelve el valor false 
				}
				
				else {//si no
					String contraseñaString =  new String(contraseña); // se crea una nuev ainstancia de String
					if (esContraseñaValida(contraseñaString)) { // se mira si la contraseña es valida
						Usuario pUsuario = new Usuario(nombre, nombreUsuario, contraseñaString); //creamos un usuario con los valores introducidos
						usuarios.add(pUsuario); // se añada el nuevo usuario a la lista de usuarios del gestor
						usuarioSesion = nombreUsuario; // se inicia la sesion 
						System.out.println("iniciado correctamente");//imprimir que se ha iniciado correctamente
						return true;}// devuelve el valor true
					
					else {
						System.out.println("La contraseña no es valida");//imprimir el fallo por consalo
						return false;}// devuelve el valor false
				}	
			}
			else {
				System.out.println("Ya existe un usuario con ese nombre de usuario");//imprimir el fallo por consalo
				return false;}// devuelve el valor false
		}
		else {
			System.out.println("el campo nombre solo pueden ser letras");//imprimir el fallo por consalo
			return false;// devuelve el valor false
		}
	}
	
	//Se verifica que la contraseña es valida
	private boolean esContraseñaValida(String contraseña) {
		if (contraseña.length() < 8) {//si l alongitud es inferior a 8 
			System.out.println("Su tamaño tiene que ser minimo de ocho caracteres");//imprimir el fallo por consola
			return false;}// devuelve el valor false
        if (!contraseña.matches(".*[A-Z].*")) {// si al menos tiene 1 mayuscula 
	        System.out.println("Tiene que tener al menos una letras en mayuscula");//imprimir el fallo por consola
	        return false;}// devuelve el valor false
        if (!contraseña.matches(".*[a-z].*")) {// si al menos tiene 1 minuscula 
	        System.out.println("Tiene que tener al menos una letras en minusculas");//imprimir el fallo por consola
	        return false;}// devuelve el valor false
        if (!contraseña.matches(".*\\d.*")) {// si al menos tiene 1 numero
        	System.out.println("Tiene que tener al menos un número");//imprimir el fallo por consola
        	return false;}// devuelve el valor false
        if (!contraseña.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {//si al menos tiene un caracter especial
	        System.out.println("Tiene que contener caracteres especiales");//imprimir el fallo por consola
	        return false;}// devuelve el valor false
        if (contraseña.contains(" ")) {//si no es contraseña vacia
        	System.out.println("No puede contener espacios");//imprimir el fallo por consola
        	return false;}// devuelve el valor false
        return true;// devuelve el valor true
    }
	
	//Se comprueban las credenciales de un usuario, si son correctas de inicia la sesión
	public boolean iniciarSesion(String nombreUsuario, char[] contraseña) {
		Usuario pUsuario = buscarUsuario(nombreUsuario);// se busca un usuario que tenga el nombre de usuario qu ese ha introducido
		if (pUsuario != null) { // si hay usuario con ese nombre de usuario
			if (!pUsuario.estaEliminado()) {// si no esta eliminado
				if (pUsuario.esCorrectaLaContraseña(new String(contraseña))) {//si es correcta la contraseña introducida
					usuarioSesion = nombreUsuario;// inisia sesion
					System.out.println("Sesión iniciada correctamente");// indica el correcto inicio de sesion
					return true;}//devuelve true
				else {
					System.out.println("La contraseña no es correcta");//imprimir el fallo porconsola
					return false;}//devuelve false
			}
			else {
				System.out.println("El usuario se ha eliminado");//imprimir el fallo porconsola
				return false;}//devuelve false
			}
		else {
			System.out.println("No existe un usuario con ese nombre de usuario");//imprimir el fallo porconsola
			return false;}//devuelve false
	}
	
	//Elimina un usuario del gestor
	public void deleteUsuario(String pUsuario) {
		if (pUsuario != usuarioSesion){//si no es la sesion que esta iniciada
			Usuario usu = buscarUsuario(pUsuario); // busca el usuario con el nombre de usuario introducido en la lista de usuarios
			usu.eliminar(); // se elimina el usuario
			this.usuarios.remove(usu); // se elimina de la lista de usuarios
			System.out.println("usuario eliminado"); // indica la correcta eliminacion
		} else {
			System.out.println("no te puedes eliminar a ti mismo"); // indica que no se puede borrar a uno mismo
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

	//devuelve una lista con los nombres de los usuarios que no estan aceptados
	public List<String> mostrarUsuariosNoAceptados(){
		List<String> listaUsuario = new ArrayList<String>(); // se crea la lista de Strings
		Iterator<Usuario> iterador= getIteratorUsuario(); //se obtiene el iterador de lalista de los usuarios de gestorUsuarios
		while (iterador.hasNext()) {// mientras no haya terminado
            Usuario usuario = iterador.next();// pasa al siguiente dandome el usuario que hemso pasado            
            if (!usuario.estaAceptada() && !usuario.estaEliminado()) {// si el usuario no esta eliminado ni aceptado
            	listaUsuario.add(usuario.getNombreUsuario());}}// se añade el nombre a la lista de Strings
		return listaUsuario;// se devuelve la lista de Strings
	}

	//obtiene el iterador de la lista de usuarios
	private Iterator<Usuario> getIteratorUsuario() {
		return usuarios.iterator();
	}

	//se acepta el registro
	public void aceptarUsuario(String pUsuario) {
		Usuario aceptado = buscarUsuario(pUsuario);// se busca a la persona mediante el nombre de usuario
		aceptado.aceptar(usuarioSesion); //se acepta el usuario, dandole mi nombre
		
	}

	//devuelve una lista con los nombres de los usuarios que no estan eliminados
	public List<String> mostrarUsuarios(){
		List<String> lista = new ArrayList<String>();// se crea la lista de Strings
		for(Usuario pUs: usuarios) {//para toda la lista de usuarios
			if (!pUs.estaEliminado()) {// si usuario no esta eliminado
				lista.add(pUs.getNombreUsuario());// se añade el nombre a la lista de Strings	
			}
		}	
		return lista;// se devuelve la lista de Strings
	}
	
	public boolean modificarUsuariosAdmin(String pNombre, String pNombreUsuario, String pNombreUsuarioAnt, String pAdmin) {
		if (pNombre.matches("^[a-zA-Z]+$")) {//si el nombre es todo letras
			if (!pNombreUsuario.equals(pNombreUsuarioAnt)) {// si el nombreUsuarioAnt no coincide con el nombreUsuarionuevo
				Usuario usu = buscarUsuario(pNombreUsuario);//busca el usuario con el nuevo nombre en la lista de usuarios
				if (usu==null) {// si no existe ese usuario
					if(!pNombreUsuario.trim().isEmpty() && !pNombre.trim().isEmpty() && !pAdmin.trim().isEmpty()) {
						usu = buscarUsuario(pNombreUsuarioAnt);//busca el usuario con el anterior nombre en la lista de usuarios
						usu.setNombreContraseñaAdmin(pNombre, pNombreUsuario, pAdmin);//modifica el valor de los parametros en el usuario
						if (usuarioSesion.equals(pNombreUsuarioAnt)){//si la sesion y la persona a modificar es misma persona
							usuarioSesion = pNombreUsuario;// se cambia el nombre usuarioSesion por el nuevo valor
						}
						return true;//devuelve el valore true al metodo que le ha llamado
					}
					else {
						System.out.println("uno de los campos no esta completado");//imprimir el fallo por consola
						return false;//devuelve el valore false al metodo que le ha llamado
					}
				}
				else {
					System.out.println("Ya existe un usuario con ese nombre de usuario");//imprimir el fallo por consola
					return false;//devuelve el valore false al metodo que le ha llamado
				}
		
			}
			else {
				Usuario usu=buscarUsuario(pNombreUsuarioAnt);//busca el usuario en la lista de usuarios
				usu.setNombreContraseñaAdmin(pNombre, pNombreUsuario, pAdmin);//modifica el valor de los parametros en el usuario
				return true;//devuelve el valore true al metodo que le ha llamado
			}
		}
		else {
			System.out.println("el campo nombre solo pueden ser letras");//imprimir el fallo por consola
			return false;//devuelve el valore false al metodo que le ha llamado
		}
	}
	
	public boolean modificarUsuariosUsuario(String pNombre, String pNombreUsuario,  String pContraseña) {
		if (pNombre.matches("^[a-zA-Z]+$")) {//si el nombre esta compuesto por letras unicamente
			if (!pNombreUsuario.equals(usuarioSesion)) {//si usuarioSesion es igual al nuevo nombre
				Usuario usu = buscarUsuario(pNombreUsuario);//busca el usuario con el nuevo nombre en la lista de usuarios
				if (usu == null) {//si no existe ningun usuario con es nombre
					if(!pNombreUsuario.trim().isEmpty() && !pNombre.trim().isEmpty() && !pContraseña.trim().isEmpty() ) {//si no se ha escrito algo como valores de alguno de los parametros 
						if(this.esContraseñaValida(pContraseña)){// si la contraseña es valida
							usu = buscarUsuario(usuarioSesion);//busca el usuario con el anterior nombre en la lista de usuarios
							usuarioSesion=pNombreUsuario;//cambiamos el usuarioSesion por el nuevo nombreUsuario qu ese haya introducido
							usu.setNombreContraseñaUsuario(pNombre, pNombreUsuario, pContraseña);;//modifica el valor de los parametros en el usuario 
							return true;//devuelve el valore true al metodo que le ha llamado
						}
						else {
							System.out.println("contraseña no valida");//imprimir el fallo por consola
							return false;//devuelve el valore false al metodo que le ha llamado
						}
					}
					else {
						System.out.println("uno de los campos no esta completado");//imprimir el fallo por consola
						return false;//devuelve el valore false al metodo que le ha llamado
					}
				}
				else{
					System.out.println("Ya existe un usuario con ese nombre de usuario");//imprimir el fallo por consola
					return false;//devuelve el valore false al metodo que le ha llamado
				}
			}
			else {
				if(this.esContraseñaValida(pContraseña)){
					Usuario usu=buscarUsuario(usuarioSesion);//busca el usuario en la lista de usuarios
					usu.setNombreContraseñaUsuario(pNombre, usuarioSesion, pContraseña);//modifica el valor de los parametros en el usuario 
					return true;//devuelve el valore true al metodo que le ha llamado
				}
				else {
					System.out.println("contraseña no valida");//imprimir el fallo por consalo
					return false;//devuelve el valore false al metodo que le ha llamado
				}
			}
		}
		else {
			System.out.println("el campo nombre solo pueden ser letras");//imprimir el fallo por consalo
			return false;
		}
	}	
	
	//obtiene la informacion del usuario necesaria para rellenar la vista de modificacion de usuario siendo el usuario admin
	public JSONObject obtenerInfoAdministrador(String pUsuarioInfor) {
		System.out.println(pUsuarioInfor);
		Usuario usuario = buscarUsuario(pUsuarioInfor);//buscar el usuario el la lista de usuarios que tiene(siempre va a existir el usuario)
		return  usuario.getInfoAdministrador();//obtiene la informacion del usuario necesaria
	}

	//obtiene la informacion del usuario necesaria para rellenar la vista de modificacion de usuario siendo el usuario no admin
	public JSONObject obtenerInfoUsuario() {
		Usuario usuario = buscarUsuario(usuarioSesion);//buscar el usuario el la lista de usuarios que tiene(siempre va a existir el usuario)
		return  usuario.getInfoUsuario();//obtiene la informacion del usuario necesaria
	}
	
	//Para tests
	public void reset() {
		gestorUsuarios = null;//restaura el valor de sesion
	}
}
