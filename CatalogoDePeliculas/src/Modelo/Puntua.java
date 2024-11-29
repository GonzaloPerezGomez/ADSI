package Modelo;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Puntua {

	private Usuario usuario ;
	private Pelicula pelicula;
	private Float puntuacion;
	private String comentario;
	
	
	 public Puntua(String comentario, float puntuacion, Usuario usuario, Pelicula pelicula) {
	        this.comentario = comentario;
	        this.puntuacion = puntuacion;
	        this.usuario = usuario;
	        this.pelicula = pelicula;
	    }
	
	 public String getComentario() {
	        return comentario;
	    }

	    public float getPuntuacion() {
	        return puntuacion;
	    }

	    public Usuario getUsuario() {
	        return usuario;
	    }

	    public Pelicula getPelicula() {
	        return pelicula;
	    }
	    
	    public boolean equals(Pelicula peli, Usuario usu) {
			return (pelicula.equals(peli) && usuario.equals(usu));
		}
	    
	    
}
