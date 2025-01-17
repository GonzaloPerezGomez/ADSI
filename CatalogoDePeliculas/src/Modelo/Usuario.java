package Modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.json.JSONObject;

import db.SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Usuario {
	
	private String nombre;
	private String nombreUsuario;
	private String contraseña;
	private boolean esAdmin;
	private String aceptadoPor;
	private boolean eliminado;
	private List<Pelicula> solicitudes;
	private String url = "jdbc:sqlite:src/db/database.db";
	
	public Usuario(String nombre, String nombreUsuario, String contraseña) {
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.contraseña = new String(contraseña);
		this.esAdmin = true;
		this.eliminado = false;
		this.aceptadoPor = null;
		solicitudes = new ArrayList<Pelicula>();
		
		try (Connection conn = DriverManager.getConnection(url)) {
			String insertData = "INSERT INTO Usuario (nombreUsuario, nombre, contraseña, administrador,eliminado, aceptadoPor) VALUES (?, ?, ?, ?, 0, ?)" ;
			try (PreparedStatement pstmt = conn.prepareStatement(insertData)) {
				pstmt.setString(1, nombreUsuario);
				pstmt.setString(2, nombre);
				pstmt.setString(3, contraseña);
				pstmt.setInt(4, esAdmin? 1:0);
				pstmt.setString(5, aceptadoPor);
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("Error en la conexión con SQLite.");
	        e.printStackTrace();
	    }
	}
	
	public Usuario(String nombre, String nombreUsuario, String contraseña, boolean esAdmin, boolean eliminado, String aceptadoPor) {
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.contraseña = new String(contraseña);
		this.esAdmin = esAdmin;
		this.eliminado = eliminado;
		this.aceptadoPor = aceptadoPor;
		solicitudes = new ArrayList<Pelicula>();
	}
	
	public void cargarSolicitud(Pelicula pPelicula) {
		solicitudes.add(pPelicula);
		System.out.println(solicitudes);
	}
	
	public void addSolicitud(Pelicula pPelicula) {
		if(!solicitudes.contains(pPelicula))
			solicitudes.add(pPelicula);
		
			String sql = "INSERT INTO Pelicula(titulo, director, fecha) VALUES('" + pPelicula.getTitulo() + "', '" + pPelicula.getDirector() + "', '" + pPelicula.getFecha() + "')";
			SQLite.getBaseDeDatos().execSQL(sql);
			
			sql = "INSERT INTO Solicitud(nombreUsuario, titulo, fecha) VALUES('" + this.getNombreUsuario() + "', '" + pPelicula.getTitulo() + "', '" + pPelicula.getFecha() + "')";
			SQLite.getBaseDeDatos().execSQL(sql);
	}
	
	public void deleteSolicitud(Pelicula pPelicula) {
		boolean quedan = true;
		while(quedan) {
			quedan = solicitudes.remove(pPelicula);
		}
	}

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
	
	public boolean equals(String pNombre) {
		return (nombreUsuario.equals(pNombre));
	}
	
	public boolean isAdmin() {
		return esAdmin;
	}
	
	public List<Pelicula> getPeliculasSolicitadas(){
		return solicitudes;
	}

	public boolean esCorrectaLaContraseña(String pContraseña) {
		return contraseña.equals(pContraseña);
	}
	
	public boolean estaAceptada() {
		if (this.aceptadoPor == null){
			return false;
		}
		return true;
	}
	
	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void aceptar(String pUsuario) {
		this.aceptadoPor=pUsuario;
		try (Connection conn = DriverManager.getConnection(url)) {
			String insertData = "UPDATE Usuario SET aceptadoPor = ? where nombreUsuario = ?" ;
			try (PreparedStatement pstmt = conn.prepareStatement(insertData)) {
				pstmt.setString(1, pUsuario);
				pstmt.setString(2, nombreUsuario);
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
            System.out.println("Error en la conexión con SQLite.");
            e.printStackTrace();
        }
	}
	
	public void setNombreContraseñaAdmin(String pNombre, String pNombreUsuario, String pAdministrador) {
		try (Connection conn = DriverManager.getConnection(url)) {
			String insertData = "UPDATE Usuario SET nombreUsuario = ?, nombre = ?, administrador = ? where nombreUsuario = ?" ;
			try (PreparedStatement pstmt = conn.prepareStatement(insertData)) {
				pstmt.setString(1, pNombreUsuario);
				pstmt.setString(2, pNombre);
				pstmt.setInt(3, Boolean.parseBoolean(pAdministrador)?1:0);
				pstmt.setString(4, nombreUsuario);
				pstmt.executeUpdate();
				
				this.nombreUsuario = pNombreUsuario;
				this.nombre = pNombre;
				this.esAdmin = Boolean.valueOf(pAdministrador);
			}
		} catch (Exception e) {
			System.out.println("Error en la conexión con SQLite.");
		    e.printStackTrace();
		}	
	}
	
	public void setNombreContraseñaUsuario(String pNombre, String pNombreUsuario, String contraseña) {
		try (Connection conn = DriverManager.getConnection(url)) {
			String insertData = "UPDATE Usuario SET nombreUsuario = ?, nombre = ?, contraseña = ? where nombreUsuario = ?" ;
			try (PreparedStatement pstmt = conn.prepareStatement(insertData)) {
				pstmt.setString(1, pNombreUsuario);
				pstmt.setString(2, pNombre);
				pstmt.setString(3, contraseña);
				pstmt.setString(4, nombreUsuario);
				pstmt.executeUpdate();
				this.nombreUsuario = pNombreUsuario;
				this.nombre = pNombre;
				this.contraseña = contraseña;
			}
		} catch (Exception e) {
			System.out.println("Error en la conexión con SQLite.");
		    e.printStackTrace();
		}
	}
	
	public JSONObject getInfoAdministrador() {
		JSONObject datosUsuario = new JSONObject();
		datosUsuario.put("Nombre", nombre);
		datosUsuario.put("NombreUsuario", nombreUsuario);
		datosUsuario.put("Administrador", String.valueOf(esAdmin));
		return datosUsuario;
	}
	
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
	
	public boolean estaEliminado() {
		return eliminado;
	}
}