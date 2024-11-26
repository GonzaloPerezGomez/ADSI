package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteExample {
    public static void main(String[] args) {
        // Ruta del archivo de base de datos SQLite
        String url = "jdbc:sqlite:database.db";

        // Conexión y operaciones
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Conexión establecida con SQLite.");

                // Crear un Statement para ejecutar SQL
                Statement stmt = conn.createStatement();

                // Crear tabla si no existe
                String createTable = "CREATE TABLE IF NOT EXISTS pelicula (" +
                                     "titulo TEXT NOT NULL, " +
                                     "director TEXT NOT NULL, " +
                                     "fecha TEXT," +
                                     "PRIMARY KEY (titulo, fecha))";
                stmt.execute(createTable);
                
                createTable = "CREATE TABLE IF NOT EXISTS usuario (" +
	                        "titulo TEXT NOT NULL, " +
	                        "director TEXT NOT NULL, " +
	                        "fecha TEXT," +
	                        "PRIMARY KEY (titulo, fecha))";
                stmt.execute(createTable);

                // Insertar datos
                String insertData = "INSERT INTO movies (title, director, release_date) " +
                                    "VALUES ('Harry Potter', 'David Yates', '2011-07-15')";
                stmt.execute(insertData);

                // Consultar datos
                String selectData = "SELECT * FROM movies";
                ResultSet rs = stmt.executeQuery(selectData);

                // Imprimir resultados
                while (rs.next()) {
                    System.out.println("Título: " + rs.getString("title"));
                    System.out.println("Director: " + rs.getString("director"));
                    System.out.println("Fecha de estreno: " + rs.getString("release_date"));
                    System.out.println("-------------");
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

