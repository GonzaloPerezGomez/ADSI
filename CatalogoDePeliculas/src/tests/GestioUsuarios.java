package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Collection;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Modelo.GestorGeneral;
import Modelo.GestorUsuarios;
import Modelo.Usuario;
import Modelo.Pelicula;
import db.SQLite;

class GestioUsuarios {
	@BeforeEach
	void before() {
		GestorUsuarios.getGestorUsuarios().reset();
		SQLite.getBaseDeDatos().deleteAll();
	}
	
	@Test
	void testRegistrarse() {
		
		//Comprobamos en caso de que todo este bien 
		char[] con = {'H','o','l','a','1','2','3','.'};
		char[] conSinLarguraSuf = {'H','o','l','a','1','.'};
		char[] conSinMayus = {'h','o','l','a','1','2','3','.'};
		char[] conSinSigno = {'H','o','l','a','1','2','3'};
		char[] conSinNumeros = {'H','o','l','a','.'};
		GestorGeneral.getGestorGeneral().addUsuario("Pepe", "pepe", con );
		try {
			Collection<Usuario> usuarios = SQLite.getBaseDeDatos().getAllUsuarios();	
			if(usuarios.size() == 0 || GestorUsuarios.getGestorUsuarios().buscarUsuario("pepe") == null) {
				fail("No se ha añadido correctamente a la base de datos");}
		} catch (SQLException e) {
			fail("La conexión con la base de datos falló");}	
		
		//Comprobamos en caso de meter contraseñas no permitidas
		
		GestorGeneral.getGestorGeneral().addUsuario("A", "A", conSinLarguraSuf );
		GestorGeneral.getGestorGeneral().addUsuario("A", "A", conSinMayus );
		GestorGeneral.getGestorGeneral().addUsuario("A", "A", conSinSigno );
		GestorGeneral.getGestorGeneral().addUsuario("A", "A", conSinNumeros );
		
		try {
			Collection<Usuario> usuarios = SQLite.getBaseDeDatos().getAllUsuarios();	
			if(usuarios.size() == 0 || GestorUsuarios.getGestorUsuarios().buscarUsuario("A") != null) {
				fail("Se ha añadido el usuario");
			}
		} catch (SQLException e) {
			fail("La conexión con la base de datos falló");
		}	
		
		
		
		
	}
	
	@Test
	void testIniciarSesion() {
		char[] con = {'H','o','l','a','1','2','3','.'};
		GestorGeneral.getGestorGeneral().addUsuario("Pepe", "pepe", con );	
	//Comprobacion en caso de que todo este bien	
		if (!GestorGeneral.getGestorGeneral().seInicia("pepe", con)){
			fail("No se ha iniciado sesion");}
	//Comprobacion en caso de haber campos vacios
		char[] conVacia = {' '};
		if (GestorGeneral.getGestorGeneral().seInicia("pepe", conVacia) || GestorGeneral.getGestorGeneral().seInicia("", con)){
			fail("Se ha iniciado sesion con campo vacio");}
	//Comprobacion en caso de usuario incorrecto
		if (GestorGeneral.getGestorGeneral().seInicia("pepa", con)){
			fail("Se ha iniciado sesion con usuario incorecto");}
	//Comprobacion en caso de contraseña incorrecta
		char[] conIncorrecta = {'H', 'o', 'l', 'a'};
		if (GestorGeneral.getGestorGeneral().seInicia("pepe", conIncorrecta)){
			fail("Se ha iniciado sesion con usuario incorecto");}
	}

	@Test
	void testActualizarDatosP() {
		char[] con = {'H','o','l','a','1','2','3','.'};
		GestorGeneral.getGestorGeneral().addUsuario("Pepe", "pepe", con );
		GestorGeneral.getGestorGeneral().addUsuario("Emma", "emma", con );
	//Comprobacion en caso de que todo este bien	
		if (GestorGeneral.getGestorGeneral().modificarUsuariosUsuario("Elena", "elena", "Hola4321.")) {
			JSONObject info =GestorUsuarios.getGestorUsuarios().obtenerInfoUsuario();
			if (!info.getString("Nombre").equals("Elena") || !info.getString("NombreUsuario").equals("elena") || !info.getString("Contraseña").equals("Hola4321.")) {
				fail("no se ha modificado bien ");}}
		else {
			fail("no se ha modificado");}
	//Comprobacion en caso de haber campos vacios
		if (GestorGeneral.getGestorGeneral().modificarUsuariosUsuario("", "a", "Hola1234.")) {
			fail("se ha modificado");}
		if (GestorGeneral.getGestorGeneral().modificarUsuariosUsuario("a", "", "Hola1234.")) {
			fail("se ha modificado");}
		if (GestorGeneral.getGestorGeneral().modificarUsuariosUsuario("a", "a", "")) {
			fail("se ha modificado");}
	//Comprobacion en caso de meter el nombre de unusuario que ya este
		if (GestorGeneral.getGestorGeneral().modificarUsuariosUsuario("Pepe", "pepe", "Hola1234.")) {
			fail("se ha modificado");}
	//Comprobamos en caso de meter contraseñas no permitidas
		if (GestorGeneral.getGestorGeneral().modificarUsuariosUsuario("a", "a", "Hola1.")) {
			fail("se ha modificado");}
		if (GestorGeneral.getGestorGeneral().modificarUsuariosUsuario("a", "a", "hola1234.")) {
			fail("se ha modificado");}
		if (GestorGeneral.getGestorGeneral().modificarUsuariosUsuario("a", "a", "Hola1234")) {
			fail("se ha modificado");}
		if (GestorGeneral.getGestorGeneral().modificarUsuariosUsuario("a", "a", "Hola.")) {
			fail("se ha modificado");}
		try {
			Collection<Usuario> usuarios = SQLite.getBaseDeDatos().getAllUsuarios();	
			if (GestorUsuarios.getGestorUsuarios().buscarUsuario("a")!=null) {
				fail("se ha modificado");}
		} catch (SQLException e) {
			fail("La conexión con la base de datos falló");}
	}
		

	
	@Test
	void testAceptaRegistro() {
	//Comprobacion no haya solicitudes	
		if (GestorUsuarios.getGestorUsuarios().mostrarUsuariosNoAceptados().size()!=0){
			fail("no deberia haber solicitudes");}
	//Comprobacion en caso de que todo este bien
		char[] con = {'H','o','l','a','1','2','3','.'};
		GestorGeneral.getGestorGeneral().addUsuario("Pepe", "pepe", con );
		if (GestorUsuarios.getGestorUsuarios().mostrarUsuariosNoAceptados().size()==1){
			GestorGeneral.getGestorGeneral().aceptarUsuario("pepe");
			if (GestorUsuarios.getGestorUsuarios().mostrarUsuariosNoAceptados().size()!=0){
				fail("no se ha aceptado");}
		}
		else {
			fail("no hay usuarios en la lista");}
	//Comprobacion en caso de rechazar la solicitud, se elimina el usuario que se habia creado, mejor probado en el siguiente apartado
		GestorGeneral.getGestorGeneral().addUsuario("Sonia", "Sonia", con );
		GestorGeneral.getGestorGeneral().addUsuario("Elena", "Elena", con );
		if (GestorUsuarios.getGestorUsuarios().mostrarUsuariosNoAceptados().size()==2){
			GestorGeneral.getGestorGeneral().deleteUsuario("Sonia");
			if (GestorUsuarios.getGestorUsuarios().mostrarUsuariosNoAceptados().size()!=1){
				fail("no se ha aceptado");}
		}			
	}
	
	
	@Test
	void testEliminarCuenta() {
		//Comprobacion de eliminar a alguien que no eres tu
		char[] con = {'H','o','l','a','1','2','3','.'};
		GestorGeneral.getGestorGeneral().addUsuario("Pepe", "pepe", con );
		GestorGeneral.getGestorGeneral().addUsuario("Emma", "Emma", con );
			if (GestorUsuarios.getGestorUsuarios().buscarUsuario("pepe")!=null){
				GestorGeneral.getGestorGeneral().deleteUsuario("pepe");
				if (GestorUsuarios.getGestorUsuarios().buscarUsuario("pepe")!=null){
					fail("no se ha borrado el usuario");}
			}else {
				fail("no se ha añadiso el usuario");}
	//Comprobacion de eliminarte a ti mismo
		try {
			Collection<Usuario> usuarios = SQLite.getBaseDeDatos().getAllUsuarios();
			if (GestorUsuarios.getGestorUsuarios().buscarUsuario("Emma")!=null){
				GestorGeneral.getGestorGeneral().deleteUsuario("Emma");
				if (GestorUsuarios.getGestorUsuarios().buscarUsuario("Emma")==null){
					fail("no se ha borrado el usuario");}
			}else {
				fail("no se ha añadiso el usuario");}
		} catch (SQLException e) {
			fail("La conexión con la base de datos falló");}
	}
	
	@Test
	void testActualizarDatosA() {
		char[] con = {'H','o','l','a','1','2','3','.'};
		GestorGeneral.getGestorGeneral().addUsuario("Pepe", "pepe", con );
		GestorGeneral.getGestorGeneral().addUsuario("Emma", "emma", con );
	//Comprobacion en caso de que todo este bien	
		if (GestorGeneral.getGestorGeneral().modificarUsuariosAdmin("Elena", "Pedro", "emma", "true")) {
			JSONObject info =GestorUsuarios.getGestorUsuarios().obtenerInfoAdministrador("Pedro");
			if (!info.getString("Nombre").equals("Elena") || !info.getString("NombreUsuario").equals("Pedro") || !info.getBoolean("Administrador")) {
				fail("no se ha modificado bien ");}}
		else {
			fail("no se ha modificado");}
		
	//Comprobacion en caso de haber campos vacios
		if (GestorGeneral.getGestorGeneral().modificarUsuariosAdmin("", "a", "Pedro", "Hola1234.")) {
			fail("se ha modificado");}
		if (GestorGeneral.getGestorGeneral().modificarUsuariosAdmin("a", "", "Pedro", "Hola1234.")) {
			fail("se ha modificado");}
		if (GestorGeneral.getGestorGeneral().modificarUsuariosAdmin("a", "a", "Pedro", "")) {
			fail("se ha modificado");}
	//Comprobacion en caso de meter el nombre de unusuario que ya este
		if (GestorGeneral.getGestorGeneral().modificarUsuariosAdmin("Pepe", "pepe", "Pedro", "Hola1234.")) {
			fail("se ha modificado");}
		try {
			Collection<Usuario> usuarios = SQLite.getBaseDeDatos().getAllUsuarios();	
			if (GestorUsuarios.getGestorUsuarios().buscarUsuario("a")!=null) {
				fail("se ha modificado");}
		} catch (SQLException e) {
			fail("La conexión con la base de datos falló");}
	}
}
