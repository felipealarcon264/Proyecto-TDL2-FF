package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Catalogo.Contenido;
import Catalogo.Resenia;
import DataBase.ConexionDB;
import Entes.Usuario;
import Entes.Datos_Personales;

/**
 * Implementacion de la interfaz ReseniaDAO para la gestión de reseñas en la
 * base de datos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */
public class ReseniaDAOImpl implements ReseniaDAO {

    /**
     * Guarda una reseña en la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param resenia La reseña a guardar.
     * @return true si se guardó correctamente, false en caso contrario.
     */
    @Override
    public boolean guardar(Resenia resenia) {
        if (resenia == null || resenia.getUsuario() == null || resenia.getContenido() == null) {
            System.out.println("❌ La reseña, el usuario o el contenido son nulos. No se puede guardar.");
            return false;
        }

        String sql = "INSERT INTO RESENIA (CALIFICACION, COMENTARIO, APROBADO, FECHA_HORA, ID_USUARIO, ID_PELICULA) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, resenia.getCalificacion());
            pstmt.setString(2, resenia.getComentario());
            pstmt.setInt(3, resenia.getAprobado());
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now())); // Guarda la fecha y hora actual
            pstmt.setInt(5, resenia.getUsuario().getIdDB());
            // Ahora que Contenido tiene idDB, podemos usarlo.
            pstmt.setInt(6, resenia.getContenido().getIdDB());

            if (pstmt.executeUpdate() > 0) {
                System.out.println("✅ Reseña guardada exitosamente.");
                return true;
            } else {
                System.out.println("❌ No se pudo guardar la reseña.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al guardar la reseña: " + e.getMessage());
            return false;
        }
    }

    /**
     * Borra una reseña de la base de datos usando su ID.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param resenia La reseña a borrar.
     * @return true si se borró correctamente, false en caso contrario.
     */
    @Override
    public boolean borrar(Resenia resenia) {
        if (resenia == null || resenia.getIdDB() <= 0) {
            System.out.println("❌ La reseña es nula o inválida. No se puede borrar.");
            return false;
        }
        String sql = "DELETE FROM RESENIA WHERE ID = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, resenia.getIdDB());
            if (pstmt.executeUpdate() > 0) {
                System.out.println("✅ Reseña borrada correctamente.");
                return true;
            } else {
                System.out.println("⚠️ No se encontró una reseña con el ID proporcionado.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al borrar la reseña: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca una reseña por su ID.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param id El ID de la reseña a buscar.
     * @return Un objeto Resenia si se encuentra, o null en caso contrario.
     */
    @Override
    public Resenia buscarPorId(int id) {
        String sql = "SELECT * FROM RESENIA WHERE ID = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
                PeliculaDAO peliculaDAO = new PeliculaDAOImpl();

                Usuario usuario = usuarioDAO.buscarPorId(rs.getInt("ID_USUARIO"));
                Contenido contenido = peliculaDAO.buscarPorId(rs.getInt("ID_PELICULA"));

                return new Resenia(
                        rs.getInt("ID"),
                        rs.getInt("CALIFICACION"),
                        rs.getString("COMENTARIO"),
                        rs.getInt("APROBADO"),
                        usuario,
                        contenido);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar la reseña por ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Devuelve una lista con todas las reseñas de la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return Una lista de objetos Resenia.
     */
    @Override
    public List<Resenia> devolverListaResenia() {
        List<Resenia> lista = new ArrayList<>();
        // Consulta optimizada con JOIN para evitar el problema N+1
        String sql = """
            SELECT
                r.ID AS resenia_id, r.CALIFICACION, r.COMENTARIO, r.APROBADO,
                u.ID AS usuario_id, u.NOMBRE_USUARIO, u.EMAIL, u.CONTRASENA, u.ROL,
                dp.ID AS dp_id, dp.NOMBRE, dp.APELLIDO, dp.DNI,
                p.ID AS pelicula_id, p.TITULO, p.DIRECTOR, p.DURACION, p.RESUMEN, p.GENERO
            FROM RESENIA r
            JOIN USUARIO u ON r.ID_USUARIO = u.ID
            JOIN DATOS_PERSONALES dp ON u.ID_DATOS_PERSONALES = dp.ID
            JOIN PELICULA p ON r.ID_PELICULA = p.ID
        """;

        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Reconstruir Datos_Personales
                Datos_Personales dp = new Datos_Personales(rs.getInt("dp_id"), rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getInt("DNI"));

                // Reconstruir Usuario (Cuenta o Administrador)
                Usuario usuario = null;
                if ("CUENTA".equals(rs.getString("ROL"))) {
                    usuario = new Entes.Cuenta(rs.getInt("usuario_id"), rs.getString("NOMBRE_USUARIO"), rs.getString("EMAIL"), rs.getString("CONTRASENA"), dp, rs.getString("ROL"));
                } else { // Asumimos que si no es CUENTA, es ADMINISTRADOR
                    usuario = new Entes.Administrador(rs.getInt("usuario_id"), rs.getString("NOMBRE_USUARIO"), rs.getString("EMAIL"), rs.getString("CONTRASENA"), dp, rs.getString("ROL"));
                }

                // Reconstruir Pelicula
                Catalogo.Pelicula pelicula = new Catalogo.Pelicula(rs.getInt("pelicula_id"), rs.getString("TITULO"), rs.getString("DIRECTOR"), rs.getInt("DURACION"), rs.getString("RESUMEN"), Enums.Genero.valueOf(rs.getString("GENERO")));

                // Reconstruir Resenia y añadirla a la lista
                lista.add(new Resenia(rs.getInt("resenia_id"), rs.getInt("CALIFICACION"), rs.getString("COMENTARIO"), rs.getInt("APROBADO"), usuario, pelicula));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar las reseñas: " + e.getMessage());
            return null;
        }
        return lista;
    }

    /**
     * Actualiza una reseña existente en la base de datos.
     * Solo cambia la condicion de aprobado o desaprovado, tarea que realiza un
     * administrador.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param resenia La reseña con los datos a actualizar.
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    @Override
    public boolean actualizar(Resenia resenia) {
        if (resenia == null || resenia.getIdDB() <= 0) {
            System.out.println("❌ La reseña es nula o inválida. No se puede actualizar.");
            return false;
        }
        String sql = "UPDATE RESENIA SET APROBADO = ? WHERE ID = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, resenia.getAprobado());
            pstmt.setInt(2, resenia.getIdDB());
            if (pstmt.executeUpdate() > 0) {
                System.out.println("✅ Estado de la reseña actualizado correctamente.");
                return true;
            } else {
                System.out.println("⚠️ No se encontró una reseña con el ID proporcionado para actualizar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar la reseña: " + e.getMessage());
            return false;
        }
    }
}
