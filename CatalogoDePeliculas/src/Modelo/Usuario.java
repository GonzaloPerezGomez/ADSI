package Modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.json.JSONObject;

public class Usuario {
	
	private String nombre;
	private String nombreUsuario;
	private String contraseña;
	private boolean esAdmin;
	private Usuario aceptadoPor;
	private List<Pelicula> solicitudes;
	
	public Usuario(String nombre, String nombreUsuario, String contraseña, boolean esAdmin) {
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.contraseña = new String(contraseña);
		this.esAdmin = esAdmin;
		this.aceptadoPor = null;
		solicitudes = new ArrayList<Pelicula>();
	}
	
	public void addSolicitud(Pelicula pPelicula) {
		solicitudes.add(pPelicula);
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

	public void aceptar(Usuario pUsuario) {
		this.aceptadoPor=pUsuario;
	}
	
	public void setNombreContraseña(String pNombreUsuario, String pNombre, String pAdministrador) {
		this.nombreUsuario = pNombreUsuario;
		this.nombre = pNombre;
		this.esAdmin = Boolean.valueOf(pAdministrador);
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
	
}
