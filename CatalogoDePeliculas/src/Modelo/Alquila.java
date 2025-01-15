package Modelo;

import java.util.Date;

public class Alquila {

	private Usuario usuario ;
	private Pelicula pelicula;
	private Date fecha;

	
	
	 public Alquila( Usuario usuario, Pelicula pelicula) {
	        this.usuario = usuario;
	        this.pelicula = pelicula;
            this.fecha = new Date();}
	 
	public Date getFecha() {
		return fecha;
	}
	public Pelicula getPelicula() {
		return pelicula;
	}
	public Usuario getusuario() {
		return usuario;
	}
}