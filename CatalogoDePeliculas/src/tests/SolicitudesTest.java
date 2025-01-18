package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Modelo.GestorGeneral;
import Modelo.GestorPeliculas;
import Modelo.GestorUsuarios;
import Modelo.Pelicula;
import Modelo.Usuario;
import db.SQLite;

class SolicitudesTest {
	
	Pelicula peliculaASolicitar;
	
	@BeforeEach
	void before() {
		GestorUsuarios.getGestorUsuarios().reset();
		GestorPeliculas.getGestorPeliculas().reset();
		SQLite.getBaseDeDatos().deleteAll();
		
		char[] con = {'H','o','l','a','1','2','3','.'};
		GestorUsuarios.getGestorUsuarios().addUsuario("Pepe", "pepe", con);
		GestorUsuarios.getGestorUsuarios().iniciarSesion("pepe", con);
		peliculaASolicitar = new Pelicula("The Batman", "Matt Reeves", "2022-03-04");
		
		GestorGeneral.getGestorGeneral().gestionarSolicitud(peliculaASolicitar.getTitulo(), peliculaASolicitar.getDirector(), peliculaASolicitar.getFecha());
	}
		
	@Test
	void testAñadirSolicitud() {
		
		//Comprobar si la pelicula está en la base de datos
		try {
			Collection<Pelicula> pelis = SQLite.getBaseDeDatos().getAllPeliculas();
				
			if(pelis.size() != 1 || !pelis.contains(peliculaASolicitar)) {
				fail("No se ha añadido correctamente a la base de datos");
			}
		} catch (SQLException e) {
			fail("La conexión con la base de datos falló");
		}
		
		//Comprobar si la solicitud está en la base de datos
		
		JSONObject solicitudes = SQLite.getBaseDeDatos().getAllSolicitudes().getJSONObject(0);
		
		if(solicitudes == null || !solicitudes.getString("titulo").equals(peliculaASolicitar.getTitulo())  || 
			!solicitudes.getString("director").equals(peliculaASolicitar.getDirector())  || !solicitudes.getString("fecha").equals(peliculaASolicitar.getFecha())
			|| !solicitudes.getString("nombreUsuario").equals("pepe")) {
			fail("No se ha añadido correctamente la solicitud a la base de datos");
		}
		
		//Comprobar si la solicitud está en el usuario
		
		if(!peliculaASolicitar.equals(GestorUsuarios.getGestorUsuarios().getSolicitudes()[0])) {
			fail("No se ha añadido correctamente la solicitud al usuario");
		}
				
	}
	
	@Test
	void aceptarSolicitudTest() {
		GestorGeneral.getGestorGeneral().aceptarSolicitud(peliculaASolicitar.getTitulo(), peliculaASolicitar.getDirector(), peliculaASolicitar.getFecha());
		
		//Comprobar que la pelicula se ha añadido al gestor
		
		if(GestorPeliculas.getGestorPeliculas().buscarPelicula(peliculaASolicitar.getTitulo()) == null) {
			fail("La pelicula no se ha añadido correctamente al gestor");
		}
		
		//Comprobar que se ha actualizado el atributo aceptadoPor de la pelicula en la base de datos
		
		try {
			Collection<Pelicula> pelis = SQLite.getBaseDeDatos().getAllPeliculas();
				
			if(pelis.size() != 1 || !pelis.contains(peliculaASolicitar) || !pelis.iterator().next().getAceptadoPor().equals("pepe")) {
				fail("No se ha actualizado correctamente el atributo aceptadoPor de la pelicula en la base de datos");
			}
		} catch (SQLException e) {
			fail("La conexión con la base de datos falló");
		}
		
		//Comprobar que la solicitud se ha borrado del gestor
		
		if(GestorUsuarios.getGestorUsuarios().getSolicitudes().length != 0) {
			fail("No se ha borrado correctamente la solicitud del gestor");
		}
		
		//Comprobar que la solicitud se ha borrado de la base de datos
		
		JSONArray solicitudes = SQLite.getBaseDeDatos().getAllSolicitudes();
		
		if(!solicitudes.isEmpty()) {
			fail("No se ha borrado la solicitud de la base de datos");
		}
	}
	
	@Test
	void rechazarSolicitudTest() {
		
		GestorGeneral.getGestorGeneral().rechazarSolicitud(peliculaASolicitar.getTitulo(), peliculaASolicitar.getFecha());
		
		//Comprobar que se ha borrado la pelicula de la base de datos
		
		try {
			Collection<Pelicula> pelis = SQLite.getBaseDeDatos().getAllPeliculas();
				
			if(!pelis.isEmpty()) {
				fail("No se ha borrado correctamente la pelicula de la base de datos");
			}
		} catch (SQLException e) {
			fail("La conexión con la base de datos falló");
		}

		//Comprobar que la solicitud se ha borrado del gestor
		
		if(GestorUsuarios.getGestorUsuarios().getSolicitudes().length != 0) {
			fail("No se ha borrado correctamente la solicitud del gestor");
		}
				
		//Comprobar que la solicitud se ha borrado de la base de datos
				
		JSONArray solicitudes = SQLite.getBaseDeDatos().getAllSolicitudes();
				
		if(!solicitudes.isEmpty()) {
			fail("No se ha borrado la solicitud de la base de datos");
		}
		
	}
}

