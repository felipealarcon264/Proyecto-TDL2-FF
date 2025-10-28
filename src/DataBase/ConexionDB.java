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
    /**
     * Constructor por defecto.
     */
    public ConexionDB() {
    }

    private static final String URL = "jdbc:sqlite:plataforma.db";

    /**
     * Metodo para conectar a la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @throws SQLException Si ocurre un error al conectar a la base de datos.
     * @return Objeto Connection a la base de datos.
     */
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}