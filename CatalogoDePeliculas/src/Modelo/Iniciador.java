package Modelo;

import Vista.InicioDeSesion;
import db.SQLite;

public class Iniciador {

	public static void main(String[] args) {
		SQLite.getBaseDeDatos();
		
		GestorGeneral.getGestorGeneral().cargarBD();
		
		new InicioDeSesion();
	}

}
