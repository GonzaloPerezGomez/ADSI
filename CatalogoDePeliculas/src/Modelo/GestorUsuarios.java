package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class GestorUsuarios extends Observable{
	
	private static GestorUsuarios gestorUsuarios;
	private List<Usuario> usuarios;
	
	private GestorUsuarios() {
		usuarios = new ArrayList<Usuario>();
		cargarDatos();
	}
	
	public static GestorUsuarios getGestorUsuarios() {
		if(gestorUsuarios == null) {
			gestorUsuarios = new GestorUsuarios();
		}
		return gestorUsuarios;
	}
	
	private void cargarDatos() {
		//TODO: Cargar los datos de la base de datos
	}
	
	public void addUsuario(Usuario pUsuario) {
		usuarios.add(pUsuario);
	}
	
	public void deleteUsuario(Usuario pUsuario) {
		usuarios.remove(pUsuario);
	}
	
}
