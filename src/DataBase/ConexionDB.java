package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Clase para gestionar la conexión a la base de datos SQLite.
 * @author Grupo 4 - Taller de lenguajes II
 * @version 1.0
 */

public class ConexionDB {
    private static final String URL = "jdbc:sqlite:plataforma.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}