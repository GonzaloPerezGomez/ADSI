package Modelo;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Alquila {

	private Usuario usuario ;
	private Pelicula pelicula;
	private Date fecha;

	
	
	 public Alquila( Usuario usuario, Pelicula pelicula) {
	        this.usuario = usuario;
	        this.pelicula = pelicula;
            this.fecha = new Date();}