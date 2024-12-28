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
	private Integer puntuacion;
	private String comentario;
	
	

	 public Puntua(Usuario usuario, Pelicula pelicula,String comentario, Integer puntuacion) {
	        this.comentario = comentario;
	        this.puntuacion = puntuacion;
	        this.usuario = usuario;
	        this.pelicula = pelicula;
	    }
	
	 public String getComentario() {
	        return comentario;
	    }

	    public Integer getPuntuacion() {
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

		public void setComentario(String comentario) {
			 this.comentario = comentario;
			
		}
	    
		public void setPuntuacion(Integer puntuacion) {
			 this.puntuacion = puntuacion;
			
		}
	    
}
