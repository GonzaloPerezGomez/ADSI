package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Pelicula {
	
	private String titulo;
	private String director;
	private LocalDate fecha;
	private Usuario aceptadoPor;
	
	public Pelicula(String pTitulo, String pDirector, String pFecha) {
		titulo = pTitulo;
		director = pDirector;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
		fecha = LocalDate.parse(pFecha, formatter);
	}
	
	public Pelicula(String pTitulo, String pDirector, String pFecha, String pNombreUsuario ) {
		titulo = pTitulo;
		director = pDirector;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
		fecha = LocalDate.parse(pFecha, formatter);
		
		aceptadoPor = GestorUsuarios.getGestorUsuarios().buscarUsuario(pNombreUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pelicula other = (Pelicula) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(titulo, other.titulo);
	}
	
	public boolean equals(String pTitulo, String pFecha) {
		return (titulo.equals(pTitulo) && fecha.equals(pFecha));
	}

	@Override
	public String toString() {
		return "<html>Titulo: " + titulo + "<br>Director: " + director + "<br>Fecha: " + fecha + "<html>";
	}
	
	
	
	

}
