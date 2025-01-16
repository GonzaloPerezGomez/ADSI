package Modelo;


import java.time.LocalDateTime;
import java.util.Date;

public class Alquila {

	private Usuario usuario;
	private Pelicula pelicula;
	private LocalDateTime fecha;

	
	
	 public Alquila( Usuario usuario, Pelicula pelicula, LocalDateTime fecha) {
	        this.usuario = usuario;
	        this.pelicula = pelicula;
            this.fecha = fecha;}
	 
	public LocalDateTime getFecha() {
		return fecha;
	}
	public Pelicula getPelicula() {
		return pelicula;
	}
	public Usuario getusuario() {
		return usuario;
	}
}