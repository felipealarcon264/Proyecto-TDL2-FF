package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos SQLite.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */

public class ConexionDB {
    private static final String URL = "jdbc:sqlite:plataforma.db";

    /**
     * Metodo para conectar a la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     */
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}