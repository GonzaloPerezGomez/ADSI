package tests;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JButton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Modelo.GestorAlquiler;
import Modelo.GestorGeneral;
import Modelo.GestorPeliculas;
import Modelo.GestorUsuarios;
import Modelo.Pelicula;
import Vista.Catalogo;
import db.SQLite;

class BuscarTest {

	Pelicula p1;
	Pelicula p2;
	Pelicula p3;
	Pelicula p4;
	Pelicula p5;
	
	@BeforeEach
	void before() {
		GestorUsuarios.getGestorUsuarios().reset();
		GestorPeliculas.getGestorPeliculas().reset();
		GestorAlquiler.getGestorAlquiler().reset();
		SQLite.getBaseDeDatos().deleteAll();
		char[] con = {'H','o','l','a','1','2','3','.'};
		GestorUsuarios.getGestorUsuarios().addUsuario("Pepe", "pepe", con);
		p1 = new Pelicula("Barbie", "Greta Gerwig", "2023-07-20");
		p2 = new Pelicula("Wicked", "Jon M. Chu", "2024-11-22");
		p3 = new Pelicula("Star Wars: A New Hope", "Gonzalo Perez", "2008-11-22");
		p4 = new Pelicula("Star", "Santiago Peces", "2013-07-15");
		p5 = new Pelicula("Sabadi", "Enrique DeLos", "2021-06-18");
		GestorPeliculas.getGestorPeliculas().addPelicula(p1);
		GestorPeliculas.getGestorPeliculas().addPelicula(p2);
		GestorPeliculas.getGestorPeliculas().addPelicula(p3);
		GestorPeliculas.getGestorPeliculas().addPelicula(p4);
		GestorPeliculas.getGestorPeliculas().addPelicula(p5);
		
	}
	
	@Test
	void testBuscar() {
		System.out.println("Prueba 1: Debe dar 2 resultados");
		System.out.print(GestorGeneral.getGestorGeneral().buscarPeliculas("star").length());
		System.out.println("\nPrueba 2: Debe dar 4 resultados");
		System.out.print(GestorGeneral.getGestorGeneral().buscarPeliculas("a").length());
		System.out.println("\nPrueba 3: Debe dar 1 resultado");
		System.out.print(GestorGeneral.getGestorGeneral().buscarPeliculas("Sa").length());
		System.out.println("\nPrueba 4: Debe dar 0 resultados");
		System.out.print(GestorGeneral.getGestorGeneral().buscarPeliculas("z").length());
	}

}
