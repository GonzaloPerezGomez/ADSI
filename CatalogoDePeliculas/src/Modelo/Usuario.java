package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import db.SQLite;

import java.sql.SQLException;

public class Usuario {
	
	private String nombre;
	private String nombreUsuario;
	private String contraseña;
	private boolean esAdmin;
	private String aceptadoPor;
	private boolean eliminado;
	private List<Pelicula> solicitudes;

	// genera un nuevo usuario que se guardara en la base de datos
	public Usuario(String nombre, String nombreUsuario, String contraseña) {
		//se introducen los parametros del usuario
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.contraseña = new String(contraseña);
		this.esAdmin = false;
		this.eliminado = false;
		this.aceptadoPor = null;
		//se crea un ArrayLista para las solicitudes
		solicitudes = new ArrayList<Pelicula>();
		// se carga el comando sql a ejecutar
		String sql = "INSERT INTO Usuario (nombreUsuario, nombre, contraseña, administrador,eliminado, aceptadoPor) VALUES ('" + nombreUsuario + "', '" + nombre + "', '" + contraseña + "', '" + (esAdmin ? 1 : 0) + "', 0 , NULL) ";
		//se ejecuta el comando
		SQLite.getBaseDeDatos().execSQL(sql);
		//indica el correcto registro
		System.out.println("Registro confirmado");
	}

	//genera un usuario pero se utiliza a la hora de cargar el usuario proveniente de la base de datod
	public Usuario(String nombre, String nombreUsuario, String contraseña, boolean esAdmin, boolean eliminado, String aceptadoPor) {
		//se introducen los parametros del usuario
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.contraseña = new String(contraseña);
		this.esAdmin = esAdmin;
		this.eliminado = eliminado;
		this.aceptadoPor = aceptadoPor;
		//se crea un ArrayLista para las solicitudes
		solicitudes = new ArrayList<Pelicula>();
	}
	
	//Dada una pelicula la añade a su lista de solicitudes
	public void cargarSolicitud(Pelicula pPelicula) {
		solicitudes.add(pPelicula);
	}
	
	//Dada una pelicula comprueba si ya está en el catalogo, si no lo esta la añade a su lista de solicitudes, la mete en la base de datos y crea
	//una solicitud en la base de datos para esa pelicula
	public void addSolicitud(Pelicula pPelicula) {
		boolean esta = false;
		
		Pelicula[] s = GestorUsuarios.getGestorUsuarios().getSolicitudes();
		
		for(Pelicula p : s) {
			if (p.equals(pPelicula)) {
				esta = true;
			}
		}
		
		if(!esta) {
			solicitudes.add(pPelicula);
		
			String sql = "INSERT INTO Pelicula(titulo, director, fecha) VALUES('" + pPelicula.getTitulo() + "', '" + pPelicula.getDirector() + "', '" + pPelicula.getFecha() + "')";
			SQLite.getBaseDeDatos().execSQL(sql);
			
			sql = "INSERT INTO Solicitud(nombreUsuario, titulo, fecha) VALUES('" + this.getNombreUsuario() + "', '" + pPelicula.getTitulo() + "', '" + pPelicula.getFecha() + "')";
			SQLite.getBaseDeDatos().execSQL(sql);
			
			JOptionPane.showMessageDialog(null, "Película solicitada correctamente");
		}
		else {
			JOptionPane.showMessageDialog(null, "Película ya solicitada por otro usuario");
		}
	}
	
	//Elimina todas la solicitudes de una pelicula
	public void deleteSolicitud(Pelicula pPelicula) {
		boolean quedan = true;
		while(quedan) {
			quedan = solicitudes.remove(pPelicula);
		}
	}
	
	//Devuelve true si los nombres de usuario coinciden
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(nombreUsuario, other.nombreUsuario);
	}
	//mira si el string introducido es el mismo que el nombreUsuario
	public boolean equals(String pNombre) {
		return (nombreUsuario.equals(pNombre));
	}
	//indica si el usuario es admin
	public boolean isAdmin() {
		return esAdmin;
	}

	public List<Pelicula> getPeliculasSolicitadas(){
		return solicitudes;
	}

	//verifica si la contraseña introducida es correcta
	public boolean esCorrectaLaContraseña(String pContraseña) {
		return contraseña.equals(pContraseña);
	}

	//indica si el usuario esta aceptado
	public boolean estaAceptada() {
		if (this.aceptadoPor == null){
			return false;
		}
		return true;
	}

	// devuelve el nombre de usuario 
	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	//acepta la solicitud de registro
	public void aceptar(String pUsuario) {
		this.aceptadoPor=pUsuario;//se le asigna al atributo aceptadoPor el valor introducido
		String sql = "UPDATE Usuario SET aceptadoPor = '" + pUsuario + "' where nombreUsuario = '" + nombreUsuario + "'";//se prepara el comando de SQL
		SQLite.getBaseDeDatos().execSQL(sql);// se ejecuta el comando
		System.out.println("Solicitud aceptada");// se indica su corrceta aceptacion
	}
	
	public void setNombreContraseñaAdmin(String pNombre, String pNombreUsuario, String pAdministrador) {
		String sql = "UPDATE Usuario SET nombreUsuario = '" + pNombreUsuario + "', nombre = '" + pNombre + "', administrador = '" + (Boolean.parseBoolean(pAdministrador) ? 1 : 0) + "' where nombreUsuario = '" + nombreUsuario + "'";//se prepara el comando de SQL
		try {
			SQLite.getBaseDeDatos().execSQLModificar(sql);// se ejecuta el comando
			// se asinan los nuevos valores, si hay fallo en la ejecucion de alguna linea vuleven a su estado original
			this.nombreUsuario = pNombreUsuario;
			this.nombre = pNombre;
			this.esAdmin = Boolean.valueOf(pAdministrador);
			System.out.print("Se ha modificado correctamente los datos");// indica la correcta modificacion
		}catch (SQLException e) {
	        // Manejo del error
	        System.out.println("No se pudo actualizar el usuario: " + e.getMessage());
	    }
	}
	
	public void setNombreContraseñaUsuario(String pNombre, String pNombreUsuario, String contraseña) {
		String sql = "UPDATE Usuario SET nombreUsuario = '" + pNombreUsuario + "', nombre = '" + pNombre + "', contraseña = '" + contraseña + "' where nombreUsuario = '" + nombreUsuario + "'";//se prepara el comando de SQL
		try {
			SQLite.getBaseDeDatos().execSQLModificar(sql);// se ejecuta el comando
			// se asinan los nuevos valores, si hay fallo en la ejecucion de alguna linea vuleven a su estado original
			this.nombreUsuario = pNombreUsuario;
			this.nombre = pNombre;
			this.contraseña = contraseña;
			System.out.print("Se ha modificado correctamente los datos");// indica la correcta modificacion
		} catch (Exception e) {
			System.out.println("No se pudo actualizar el usuario: " + e.getMessage());
		}
	}
	
	//da unicamente la informacion imprescindible par acambiar al usuario
	public JSONObject getInfoAdministrador() {
		JSONObject datosUsuario = new JSONObject();
		datosUsuario.put("Nombre", nombre);
		datosUsuario.put("NombreUsuario", nombreUsuario);
		datosUsuario.put("Administrador", String.valueOf(esAdmin));
		return datosUsuario;
	}

	//da unicamente la informacion imprescindible par acambiar al usuario
	public JSONObject getInfoUsuario() {
		JSONObject datosUsuario = new JSONObject();
		datosUsuario.put("NombreUsuario", nombreUsuario);
		datosUsuario.put("Nombre", nombre);
		datosUsuario.put("Contraseña", contraseña);
		return datosUsuario;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", nombreUsuario=" + nombreUsuario + ", contraseña=" + contraseña
				+ ", esAdmin=" + esAdmin + ", aceptadoPor=" + aceptadoPor + "]";
	}

	//indica si el usuario esta eliminado
	public boolean estaEliminado() {
		return eliminado;
	}

	//elimina el usuario
	public void eliminar() {
		eliminado = true;// da el valor true al atributo eliminado
		String sql = "UPDATE Usuario SET eliminado = 1 WHERE nombreUsuario = '" + nombreUsuario + "'";// se prepara el comando de SQL
		SQLite.getBaseDeDatos().execSQL(sql);//se ejecuta el comando
		solicitudes=null;// se borran todas las solicitud de pelicula 
		sql = "DELETE FROM Solicitud WHERE nombreUsuario = '" + nombreUsuario + "'";// se prepara el comando de SQL
		SQLite.getBaseDeDatos().execSQL(sql);//se ejecuta el comando
	}
}
