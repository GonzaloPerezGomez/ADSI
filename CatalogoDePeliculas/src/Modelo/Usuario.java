package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
		this.contraseña = contraseña;
		this.esAdmin = esAdmin;
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

	
	

}
