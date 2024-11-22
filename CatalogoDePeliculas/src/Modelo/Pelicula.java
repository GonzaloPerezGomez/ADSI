package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Pelicula {
	
	private String titulo;
	private String director;
	private LocalDate fecha;
	
	public Pelicula(String pTitulo, String pDirector, String pFecha) {
		titulo = pTitulo;
		director = pDirector;
		fecha = LocalDate.parse(pFecha, DateTimeFormatter.ISO_DATE);
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
	
	

}
