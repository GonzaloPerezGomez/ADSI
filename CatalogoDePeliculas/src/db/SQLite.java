
package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import Modelo.Alquila;
import Modelo.GestorPeliculas;
import Modelo.GestorUsuarios;
import Modelo.Pelicula;
import Modelo.Puntua;
import Modelo.Usuario;

public class SQLite {
	
	private static SQLite baseDeDatos;
	
	private SQLite() {
		build();
	}
	
	public static SQLite getBaseDeDatos() {
		if(baseDeDatos == null) {
			baseDeDatos = new SQLite();
		}
		return baseDeDatos;
	}
    public void build() {
        // Ruta del archivo de base de datos SQLite
        String url = "jdbc:sqlite:src/db/database.db";

        // Conexión y operaciones
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Conexión establecida con SQLite.");

                // Crear un Statement para ejecutar SQL
                Statement stmt = conn.createStatement();
                
                // Crear la tabla Usuario si no existe
                String createTable = "CREATE TABLE IF NOT EXISTS Usuario (" +
	                        "nombreUsuario TEXT NOT NULL, " +
	                        "nombre TEXT NOT NULL, " +
	                        "contraseña TEXT NOT NULL," +
	                        "administrador BIT NOT NULL," + // 0 == FALSE y 1 == TRUE*/
	                        "aceptadoPor TEXT," +
	                        "eliminado BIT NOT NULL," +
	                        "PRIMARY KEY (nombreUsuario), " +
	                        "FOREIGN KEY (aceptadoPor) REFERENCES Usuario(nombreUsuario))";
                stmt.execute(createTable);

                // Crear la tabla Pelicula si no existe
                createTable = "CREATE TABLE IF NOT EXISTS Pelicula (" +
                            "titulo TEXT NOT NULL, " +
                            "director TEXT NOT NULL, " +
                            "fecha DATE NOT NULL," +
                            "aceptadoPor TEXT, " +
                            "PRIMARY KEY (titulo, fecha), " + 
                            "FOREIGN KEY (aceptadoPor) REFERENCES Usuario(nombreUsuario))";
                stmt.execute(createTable);
                
                // Crear la tabla Puntua si no existe
                createTable = "CREATE TABLE IF NOT EXISTS Puntua (" +
                        "nombreUsuario TEXT NOT NULL, " +
                        "titulo TEXT NOT NULL, " +
                        "fecha  DATE NOT NULL," +
                        "puntuacion INTEGER NOT NULL," +
                        "comentario TEXT," +
                        "PRIMARY KEY (nombreUsuario, titulo, fecha), " +
                        "FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombreUsuario)" + 
                        "FOREIGN KEY (titulo) REFERENCES Pelicula(titulo)" +
                        "FOREIGN KEY (fecha) REFERENCES Pelicula(fecha))";
                stmt.execute(createTable);
                
                // Crear la tabla Alquila si no existe
                createTable = "CREATE TABLE IF NOT EXISTS Alquila (" +
                        "nombreUsuario TEXT NOT NULL, " +
                        "titulo TEXT NOT NULL, " +
                        "fechaPelicula DATE NOT NULL," +
                        "fechaAlquila TIMESTAMP WITH TIME ZONE NOT NULL," +
                        "PRIMARY KEY (nombreUsuario, titulo, fechaPelicula, fechaAlquila), " +
                        "FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombreUsuario)" + 
                        "FOREIGN KEY (titulo) REFERENCES Pelicula(titulo)" +
                        "FOREIGN KEY (fechaPelicula) REFERENCES Pelicula(fecha))";
                stmt.execute(createTable);
                
                // Crear la tabla Solicitud si no existe
                createTable = "CREATE TABLE IF NOT EXISTS Solicitud (" +
                        "nombreUsuario TEXT NOT NULL, " +
                        "titulo TEXT NOT NULL, " +
                        "fecha DATE NOT NULL," +
                        "PRIMARY KEY (nombreUsuario, titulo, fecha), " +
                        "FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombreUsuario)" + 
                        "FOREIGN KEY (titulo) REFERENCES Pelicula(titulo)" +
                        "FOREIGN KEY (fecha) REFERENCES Pelicula(fecha))";
                stmt.execute(createTable);
                
                conn.close();
                stmt.close();
            }
        } catch (Exception e) {
            System.out.println("Error en la conexión con SQLite.");
            e.printStackTrace();
        }
    }
    
    
    public Collection<Usuario> getAllUsuarios() throws SQLException {
    	List<Usuario> listaUsuarios = new ArrayList<Usuario>();
    	// Ruta del archivo de base de datos SQLite
        String url = "jdbc:sqlite:src/db/database.db";

        // Conexión y operaciones
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
            	// Crear un Statement para ejecutar SQL
                Statement stmt = conn.createStatement();
                
            	 String sql1 = "SELECT * FROM Usuario";
                 ResultSet rs = stmt.executeQuery(sql1);
                 while(rs.next())
                 {
                	 listaUsuarios.add(new Usuario(rs.getString("nombre"), rs.getString("nombreUsuario"), rs.getString("contraseña"),  
                			 						rs.getBoolean("administrador"), rs.getBoolean("eliminado"), rs.getString("aceptadoPor")));
                 }
            }
        }
        
        return listaUsuarios;
    }
    
    public Collection<Pelicula> getAllPeliculas() throws SQLException {
    	List<Pelicula> listaPeliculas = new ArrayList<Pelicula>();
    	// Ruta del archivo de base de datos SQLite
        String url = "jdbc:sqlite:src/db/database.db";
        // Conexión y operaciones
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
            	// Crear un Statement para ejecutar SQL
                Statement stmt = conn.createStatement();
                
            	 String sql1 = "SELECT * FROM Pelicula";
                 ResultSet rs = stmt.executeQuery(sql1);
                 while(rs.next())
                 {
                	 listaPeliculas.add(new Pelicula(rs.getString("titulo"), rs.getString("director"), rs.getString("fecha"), 
                			 						rs.getString("aceptadoPor")));
                 }
            }
        }
        
        return listaPeliculas;
    }
    
    public Collection<Alquila> getAllAlquila() throws SQLException {
        List<Alquila> listaAlquiladas = new ArrayList<>();
        // Ruta del archivo de base de datos SQLite
        String url = "jdbc:sqlite:src/db/database.db";

        // Conexión y operaciones
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                // Crear un Statement para ejecutar SQL
                Statement stmt = conn.createStatement();
                String sql1 = "SELECT * FROM Alquila";
                ResultSet rs = stmt.executeQuery(sql1);

                while (rs.next()) {
                    Usuario u = GestorUsuarios.getGestorUsuarios().buscarUsuario(rs.getString("nombreUsuario"));
                    Pelicula p = GestorPeliculas.getGestorPeliculas().buscarPelicula(rs.getString("titulo"));

                    // Obtener el campo fechaAlquila como String
                    String fechaAlquilaStr = rs.getString("fechaAlquila");
                    // Parsear la fecha y truncar nanosegundos
                    LocalDateTime fechaAlquila = LocalDateTime.parse(fechaAlquilaStr).truncatedTo(ChronoUnit.SECONDS);
                    listaAlquiladas.add(new Alquila(u, p, fechaAlquila));
                }
            }

            if (listaAlquiladas.isEmpty()) {
                System.out.println("Vacio");
            } else {
                listaAlquiladas.forEach(System.out::println);
            }
        }

        return listaAlquiladas;
    }
    
    public void execSQL(String sql) {
    	 String url = "jdbc:sqlite:src/db/database.db";

         // Conexión y operaciones
         try (Connection conn = DriverManager.getConnection(url)) {
             if (conn != null) {
                 System.out.println("Conexión establecida con SQLite.");

                 // Crear un Statement para ejecutar SQL
                 Statement stmt = conn.createStatement();
                 
                 stmt.execute(sql);
                 
                 conn.close();
                 stmt.close();
             }
         } catch (SQLException e) {
        	 System.out.println("Error en la conexión con SQLite.");
             e.printStackTrace();
		}
    }
    
    public void execSQLModificar(String sql) throws SQLException {
   	 String url = "jdbc:sqlite:src/db/database.db";

        // Conexión y operaciones
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {

                // Crear un Statement para ejecutar SQL
                Statement stmt = conn.createStatement();
                
                stmt.execute(sql);
                
                conn.close();
                stmt.close();
            }
        } catch (SQLException e) {
       	 System.out.println("Error en la conexión con SQLite.");
            e.printStackTrace();
            throw e;
		}
   }
    
    
    public JSONArray getAllSolicitudes() {
    	String url = "jdbc:sqlite:src/db/database.db";
    	JSONArray solicitudes = new JSONArray();

        // Conexión y operaciones
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Conexión establecida con SQLite.");

                Statement stmt = conn.createStatement();
                
           	 	String sql1 = "SELECT * FROM Solicitud";
                ResultSet result = stmt.executeQuery(sql1);
                
                while(result.next())
                {
                	Statement stmt2 = conn.createStatement();
	               	sql1 = "Select director FROM Pelicula WHERE titulo = '" + result.getString("titulo") + "' AND fecha = '" + result.getString("fecha") + "' ";
	               	ResultSet rs = stmt2.executeQuery(sql1);
	               	
	               	JSONObject solicitud = new JSONObject();
	               	solicitud.put("nombreUsuario", result.getString("nombreUsuario"));
	               	solicitud.put("titulo", result.getString("titulo"));
	               	solicitud.put("director", rs.getString("director"));
	               	solicitud.put("fecha", result.getString("fecha"));
	               	solicitudes.put(solicitud);
	               	
	               	stmt2.close();
                }
                
                stmt.close();
                conn.close();
                
                return solicitudes;
           }
        } catch (SQLException e) {
        	System.out.println("Error en la conexión con SQLite.");
            e.printStackTrace();
		}
		return null;
    }
    
    public Collection<Puntua> getAllPuntua() throws SQLException {
    	List<Puntua> listaPuntuaciones = new ArrayList<Puntua>();
    	// Ruta del archivo de base de datos SQLite
        String url = "jdbc:sqlite:src/db/database.db";

        // Conexión y operaciones
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
            	// Crear un Statement para ejecutar SQL
                Statement stmt = conn.createStatement();
                
            	 String sql1 = "SELECT * FROM Puntua";
                 ResultSet rs = stmt.executeQuery(sql1);
                 while(rs.next())
                 {
                	 Usuario u = GestorUsuarios.getGestorUsuarios().buscarUsuario(rs.getString("nombreUsuario"));
                	 Pelicula p = GestorPeliculas.getGestorPeliculas().buscarPelicula(rs.getString("titulo"));
                	 listaPuntuaciones.add(new Puntua(u, p, rs.getString("comentario"), rs.getInt("puntuacion")));
                 }
            }
        }
        
        return listaPuntuaciones;
    }
    
    public void deleteAll() {
    	
		String url = "jdbc:sqlite:src/db/database.db";
    	
    	try (Connection conn = DriverManager.getConnection(url)) {
    		if (conn != null) {

    			Statement stmt = conn.createStatement();
                  
    			String sql = "DELETE FROM Solicitud";
    			stmt.execute(sql);
    			
    			sql = "DELETE FROM Alquila";
    			stmt.execute(sql);
    			
    			sql = "DELETE FROM Puntua";
    			stmt.execute(sql);
    			
    			sql = "DELETE FROM Pelicula";
    			stmt.execute(sql);
    			
    			sql = "DELETE FROM Usuario";
    			stmt.execute(sql);
                  
              }
    	  } catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
