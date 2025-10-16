package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:sqlite:plataforma.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}