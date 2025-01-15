package Modelo;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Pelicula {
	
	private String titulo;
	private String director;
	private LocalDate fecha;
	private String aceptadoPor;
	private static final String[] FORMATS = {
	        "dd MMM yyyy",
	        "yyyy-MM-dd"
	        };
	
	
	public Pelicula(String pTitulo, String pDirector, String pFecha) {
		titulo = pTitulo;
		director = pDirector;
		
		if ("N/A".equalsIgnoreCase(pFecha)) 
			pFecha = "1000 01 01";
		
		fecha = formatFecha(pFecha);
	}
	
	public Pelicula(String pTitulo, String pDirector, String pFecha, String pAceptadoPor) {
		titulo = pTitulo;
		director = pDirector;
		
		if ("N/A".equalsIgnoreCase(pFecha)) 
			pFecha = "1000 01 01";
		
		fecha = formatFecha(pFecha);
		
		aceptadoPor = pAceptadoPor;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getDirector() {
		return director;
	}
	public String getFecha() {
		return fecha.toString();
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
	
	public boolean equals(String pTitulo) {
		return (titulo.equals(pTitulo));
	}
	
	@Override
	public String toString() {
		return "<html>Titulo: " + titulo + "<br>Director: " + director + "<br>Fecha: " + fecha + "<html>";
	}
	
	public String toJSON() {
		return "{\"Titulo\":\"" + titulo + "\",\"Director\":\"" + director + "\",\"Fecha\":\"" + fecha + "\"}";
	}
	
	public void setAceptadoPor(String pNombreAdmin) {
		aceptadoPor = pNombreAdmin;
	}
	
	public static LocalDate formatFecha(String fecha) {
        for (String formato : FORMATS) {
            try {
            	DateTimeFormatter df = DateTimeFormatter.ofPattern(formato, Locale.ENGLISH);
                return LocalDate.parse(fecha, df);
            } catch (Exception e) {
                // Ignora el error y prueba el siguiente formato
            }
        }
        return null;
    }
	
	
	

}
