package DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializadorDB {

    public static void crearTablas() {
        try (Connection connection = ConexionDB.conectar();
             Statement stmt = connection.createStatement()) {

            // Tabla DATOS_PERSONALES
            String sql = """
                CREATE TABLE IF NOT EXISTS DATOS_PERSONALES (
                    ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                    NOMBRE TEXT(100) NOT NULL,
                    APELLIDO TEXT(100) NOT NULL,
                    DNI INTEGER NOT NULL UNIQUE
                );
            """;
            stmt.executeUpdate(sql);

            // Tabla PELICULA
            sql = """
                CREATE TABLE IF NOT EXISTS PELICULA (
                    ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                    GENERO TEXT(1) NOT NULL,
                    TITULO TEXT(100) NOT NULL,
                    RESUMEN TEXT(500),
                    DIRECTOR TEXT(100) NOT NULL,
                    DURACION REAL NOT NULL
                );
            """;
            stmt.executeUpdate(sql);

            // Tabla USUARIO
            sql = """
                CREATE TABLE IF NOT EXISTS USUARIO (
                    ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                    NOMBRE_USUARIO TEXT NOT NULL,
                    EMAIL TEXT NOT NULL,
                    CONTRASENIA TEXT NOT NULL,
                    ID_DATOS_PERSONALES INTEGER NOT NULL,
                    CONSTRAINT USUARIO_DATOS_PERSONALES_FK FOREIGN KEY (ID_DATOS_PERSONALES)
                        REFERENCES DATOS_PERSONALES(ID)
                );
            """;
            stmt.executeUpdate(sql);

            // Tabla RESENIA
            sql = """
                CREATE TABLE IF NOT EXISTS RESENIA (
                    ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                    CALIFICACION INTEGER NOT NULL,
                    COMENTARIO TEXT(500),
                    APROBADO INTEGER DEFAULT (1) NOT NULL,
                    FECHA_HORA DATETIME NOT NULL,
                    ID_USUARIO INTEGER NOT NULL,
                    ID_PELICULA INTEGER NOT NULL,
                    CONSTRAINT RESENIA_USUARIO_FK FOREIGN KEY (ID_USUARIO)
                        REFERENCES USUARIO(ID),
                    CONSTRAINT RESENIA_PELICULA_FK FOREIGN KEY (ID_PELICULA)
                        REFERENCES PELICULA(ID)
                );
            """;
            stmt.executeUpdate(sql);

            System.out.println("✅ Tablas creadas correctamente.");

        } catch (SQLException e) {
            System.out.println("❌ Error creando tablas: " + e.getMessage());
        }
    }
}