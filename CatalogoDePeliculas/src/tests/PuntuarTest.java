package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Modelo.GestorAlquiler;
import Modelo.GestorGeneral;
import Modelo.GestorPeliculas;
import Modelo.GestorPuntuacion;
import Modelo.GestorUsuarios;
import Modelo.Pelicula;
import Modelo.Puntua;
import Modelo.Usuario;
import db.SQLite;

class PuntuarTest {
	
	Pelicula p1;
	Pelicula p2;
	Pelicula p3;

	@BeforeEach
	void before() {
		GestorUsuarios.getGestorUsuarios().reset();
		GestorPeliculas.getGestorPeliculas().reset();
		GestorAlquiler.getGestorAlquiler().reset();
		GestorPuntuacion.getGestorPuntuacion().reset();
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
	void test() {///Usuario crea una puntuacion y despues la modifica
		System.out.println("Prueba 1-------------------");
		char[] con = {'H','o','l','a','1','2','3','.'};
		GestorUsuarios.getGestorUsuarios().iniciarSesion("pepe", con);
		
		GestorGeneral.getGestorGeneral().alquilarPelicula(GestorGeneral.getGestorGeneral().obtenerUsuarioActual(), GestorGeneral.getGestorGeneral().buscarPelicula("Barbie"));
		Usuario usu= GestorUsuarios.getGestorUsuarios().buscarUsuario("pepe");
		JSONObject json = new JSONObject();
		json.put("titulo", "Barbie");
		json.put("puntuacion", 3);
		json.put("comentario", "fjfhjfjf");
		
		///Añadimos una valoracion nueva al usuario pepe
		GestorGeneral.getGestorGeneral().ValorarPelicula(json);
		Puntua puntu=GestorPuntuacion.getGestorPuntuacion().getPuntuacionPorUsuarioYPelicula(usu, p1);
		if (puntu.getPuntuacion()==json.get("puntuacion") &&puntu.getComentario()==json.get("comentario") ) {
			System.out.println("Prueba nueva puntuacion correcta");
		}
		else {
			System.out.println("Prueba nueva puntuacion fallida");
		}
		
		//modificamos la puntuacion ya existente
		usu= GestorUsuarios.getGestorUsuarios().buscarUsuario("pepe");
		json = new JSONObject();
		json.put("titulo", "Barbie");
		json.put("puntuacion", 5);
		json.put("comentario", "hola buenos dias");
		
		GestorGeneral.getGestorGeneral().ValorarPelicula(json);
		puntu=GestorPuntuacion.getGestorPuntuacion().getPuntuacionPorUsuarioYPelicula(usu, p1);
		if (puntu.getPuntuacion()==json.get("puntuacion") &&puntu.getComentario()==json.get("comentario") ) {
			System.out.println("Prueba modificar puntuacion correcta");
		}
		else {
			System.out.println("Prueba modificar puntuacion fallida");
		}
		
		
	}
	
	@Test
	void test2() { ///Usuario no tiene peliculas alquiladas
		System.out.println("Prueba 2-------------------");
		char[] con = {'H','o','l','a','1','2','3','.'};
		GestorUsuarios.getGestorUsuarios().iniciarSesion("pepe", con);
		
		
		///ahora pepe no tiene alquilada esa pelicula por ende no va a poder alquilar
		Usuario usu= GestorUsuarios.getGestorUsuarios().buscarUsuario("pepe");
		JSONObject json = new JSONObject();
		json.put("titulo", "Barbie");
		json.put("puntuacion", 3);
		json.put("comentario", "fjfhjfjf");
		
		if (GestorAlquiler.getGestorAlquiler().getPeliculasAlquiladasPorUsuario(usu).isEmpty()) {
			System.out.println("No tiene alquileres no puede puntuar");
		}else  {
			GestorGeneral.getGestorGeneral().ValorarPelicula(json);
			
		}
		
		Puntua puntu=GestorPuntuacion.getGestorPuntuacion().getPuntuacionPorUsuarioYPelicula(usu, p1);
		if (puntu==null ) {
			System.out.println("Prueba no puntuar si no hay alquiladas");
		}
		else {
			System.out.println("Prueba fallida");}
		}
		
	@Test
	void test3() { 
		System.out.println("Prueba 3-------------------");
		char[] con = {'H','o','l','a','1','2','3','.'};
		GestorUsuarios.getGestorUsuarios().iniciarSesion("pepe", con);
		Usuario usu= GestorUsuarios.getGestorUsuarios().buscarUsuario("pepe");
		JSONObject json = new JSONObject();
		json.put("titulo", "Barbie");
		json.put("puntuacion", 3);
		json.put("comentario", "fjfhjfjf");
		GestorGeneral.getGestorGeneral().ValorarPelicula(json);
		json = new JSONObject();
		json.put("titulo", "Wicked");
		json.put("puntuacion", 5);
		json.put("comentario", "dfgfg");
		GestorGeneral.getGestorGeneral().ValorarPelicula(json);
		
		GestorUsuarios.getGestorUsuarios().iniciarSesion("enrique", con);
		usu= GestorUsuarios.getGestorUsuarios().buscarUsuario("enrique");
		json = new JSONObject();
		json.put("titulo", "Barbie");
		json.put("puntuacion", 1);
		json.put("comentario", "fjfhjfjf");
		GestorGeneral.getGestorGeneral().ValorarPelicula(json);
		json = new JSONObject();
		json.put("titulo", "Wicked");
		json.put("puntuacion", 5);
		json.put("comentario", "dfgfg");
		GestorGeneral.getGestorGeneral().ValorarPelicula(json);
		
		JSONObject json2 =GestorPuntuacion.getGestorPuntuacion().CalcularMedia();
		System.out.println(json2.get("peliculas")); ///debe salir una puntuacion media de 2 para barbie y 5 para wicked siendo esta la primera que debe salir
	
	
		
	}
}
