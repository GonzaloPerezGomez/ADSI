package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteExample {
    public static void main(String[] args) {
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
                        "fechaAlquila INT NOT NULL," +
                        "PRIMARY KEY (nombreUsuario, titulo, fechaPelicula, fechaAlquila), " +
                        "FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombreUsuario)" + 
                        "FOREIGN KEY (titulo) REFERENCES Pelicula(titulo)" +
                        "FOREIGN KEY (fechaPelicula) REFERENCES Pelicula(fecha))";
                stmt.execute(createTable);
                
                // Crear la tabla Alquila si no existe
                createTable = "CREATE TABLE IF NOT EXISTS Solicitud (" +
                        "nombreUsuario TEXT NOT NULL, " +
                        "titulo TEXT NOT NULL, " +
                        "fecha DATE NOT NULL," +
                        "PRIMARY KEY (nombreUsuario, titulo, fecha), " +
                        "FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombreUsuario)" + 
                        "FOREIGN KEY (titulo) REFERENCES Pelicula(titulo)" +
                        "FOREIGN KEY (fecha) REFERENCES Pelicula(fecha))";
                stmt.execute(createTable);

                String sql2 = "DELETE FROM Pelicula";
                stmt.execute(sql2);
                
                String sql = "INSERT INTO Pelicula(titulo, director, fecha) VALUES ('e','James','1999-01-01')";
                stmt.execute(sql);
                
                String sql1 = "SELECT * FROM Pelicula";
                ResultSet rs = stmt.executeQuery(sql1);
                while(rs.next())
                {
                  // read the result set
                  System.out.println("titulo = " + rs.getString("titulo"));
                  System.out.println("fecha = " + rs.getInt("fecha"));
                  System.out.println("director = " + rs.getInt("director"));
                }
                
                rs.close();
                stmt.close();
            }
        } catch (Exception e) {
            System.out.println("Error en la conexión con SQLite.");
            e.printStackTrace();
        }
    }
}

