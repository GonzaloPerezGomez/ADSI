package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Modelo.Alquila;
import Modelo.GestorAlquiler;
import Modelo.GestorGeneral;
import Modelo.GestorPeliculas;
import Modelo.GestorUsuarios;
import Modelo.Pelicula;
import Modelo.Usuario;
import db.SQLite;

public class AlquilarTest {

	Pelicula p1;
	Pelicula p2;
	Pelicula p3;
	
	@BeforeEach
	void before() {
		GestorUsuarios.getGestorUsuarios().reset();
		GestorPeliculas.getGestorPeliculas().reset();
		GestorAlquiler.getGestorAlquiler().reset();
		SQLite.getBaseDeDatos().deleteAll();
		char[] con = {'H','o','l','a','1','2','3','.'};
		GestorUsuarios.getGestorUsuarios().addUsuario("Pepe", "pepe", con);
		GestorUsuarios.getGestorUsuarios().addUsuario("Enrique", "enrique", con);
		p1 = new Pelicula("Barbie", "Greta Gerwig", "2023-07-20");
		p2 = new Pelicula("Wicked", "Jon M. Chu", "2024-11-22");
		GestorPeliculas.getGestorPeliculas().addPelicula(p1);
		GestorPeliculas.getGestorPeliculas().addPelicula(p2);
	}
	
	@Test
	public void testAlquilarPelicula() {
		char[] con = {'H','o','l','a','1','2','3','.'};
		GestorUsuarios.getGestorUsuarios().iniciarSesion("pepe", con);
		System.out.print(GestorGeneral.getGestorGeneral().buscarPelicula("Barbie"));
		GestorGeneral.getGestorGeneral().alquilarPelicula(GestorGeneral.getGestorGeneral().obtenerUsuarioActual(), GestorGeneral.getGestorGeneral().buscarPelicula("Barbie")); //No esta alquilada
		GestorGeneral.getGestorGeneral().alquilarPelicula(GestorGeneral.getGestorGeneral().obtenerUsuarioActual(), GestorGeneral.getGestorGeneral().buscarPelicula("Barbie")); //Ya esta alquilada
		GestorGeneral.getGestorGeneral().alquilarPelicula(GestorGeneral.getGestorGeneral().obtenerUsuarioActual(), GestorGeneral.getGestorGeneral().buscarPelicula("Wicked")); //Pelicula que no tiene alquilada
		GestorUsuarios.getGestorUsuarios().cerrarSesion();
		GestorUsuarios.getGestorUsuarios().iniciarSesion("enrique", con);
		GestorGeneral.getGestorGeneral().alquilarPelicula(GestorGeneral.getGestorGeneral().obtenerUsuarioActual(), GestorGeneral.getGestorGeneral().buscarPelicula("Barbie")); //Misma pelicula, otro usuario
		LocalDateTime fecha = LocalDateTime.of(2025, 01, 16, 22, 51, 00);
		Alquila a = new Alquila(GestorGeneral.getGestorGeneral().obtenerUsuarioActual(), p2, fecha);
		GestorAlquiler.getGestorAlquiler().addAlquila(a);
		GestorGeneral.getGestorGeneral().alquilarPelicula(GestorGeneral.getGestorGeneral().obtenerUsuarioActual(), GestorGeneral.getGestorGeneral().buscarPelicula("Wicked")); //Pelicula alquilada previamente, que se vuelve a alquilar (han pasado 48 horas o más)
		
	}


}
