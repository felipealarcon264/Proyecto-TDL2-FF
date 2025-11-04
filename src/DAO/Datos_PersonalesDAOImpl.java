package DAO;

import DataBase.ConexionDB;
import Entes.Datos_Personales;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz Datos_PersonalesDAO para la gestión de datos
 * personales en la base de datos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */

public class Datos_PersonalesDAOImpl implements Datos_PersonalesDAO {
    /**
     * Constructor por defecto.
     */
    public Datos_PersonalesDAOImpl() {
    }

    /**
     * Guarda datos personales en la base de datos y devuelve el ID generado.
     * Asume que los datos ya están validados.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     *
     * @param dp Datos_Personales a guardar.
     * @return El ID autogenerado por la base de datos si la inserción fue exitosa
     *         y se pudo recuperar el ID, o -1 si ocurrió un error.
     */
    @Override
    public int guardar(Datos_Personales dp) { // Devuelve int
        String sql = "INSERT INTO datos_personales (NOMBRE, APELLIDO, DNI) VALUES (?, ?, ?)";
        int idGenerado = -1; // Valor por defecto en caso de error

        // Añade Statement.RETURN_GENERATED_KEYS aquí
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, dp.getNombre());
            pstmt.setString(2, dp.getApellido());
            pstmt.setInt(3, dp.getDni());

            int filasAfectadas = pstmt.executeUpdate(); // Ejecuta la inserción

            if (filasAfectadas > 0) { // Solo si la inserción tuvo éxito
                // Intenta recuperar el ID generado directamente
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idGenerado = generatedKeys.getInt(1); // Obtiene el ID
                        System.out.println("Datos personales guardados con ID: " + idGenerado);
                    } else {
                        // Si de todos modos no se pudo obtener el ID
                        System.err.println("⚠️ No se pudo obtener el ID generado tras insertar.");
                    }
                }
                // El SQLException del generatedKeys será capturado por el catch externo
            } else {
                System.err.println("⚠️ No se insertó ninguna fila para Datos Personales.");
            }
            // try-with-resources cierra conn y pstmt automáticamente

        } catch (SQLException e) {
            System.err.println("❌ ERROR al guardar los datos personales: " + e.getMessage());
        }
        return idGenerado;
    }

    /**
     * Borrar datos personales
     * Verifica si el objeto Datos_Personales es nulo antes de intentar borrarlo.\
     * Recupera el valor del dni del objeto Datos_Personales para usarlo en la
     * consulta SQL.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param dp Datos_Personales a borrar
     * @return true si se borraron los datos personales correctamente, false en caso
     *         contrario
     * 
     */
    @Override
    public boolean borrar(Datos_Personales dp) {
        if (dp == null) {
            System.out.println("No se puede borrar datos personales nulos.");
            return false;
        }
        String sql = "DELETE FROM datos_personales WHERE dni = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dp.getDni());
            if (pstmt.executeUpdate() == 0) {
                System.out.println("No se encontraron datos personales con el DNI proporcionado.");
                return false;
            } else {
                System.out.println("Datos personales borrados correctamente.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al borrar los datos personales: " + e.getMessage());
            return false;
        }
    }

    /**
     * Buscar datos personales por DNI
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param dni DNI a buscar
     * @return Datos_Personales o null si no se encuentra
     * 
     */
    @Override
    public Datos_Personales buscarPorDNI(int dni) {
        String sql = "SELECT ID, NOMBRE, APELLIDO, DNI FROM datos_personales WHERE DNI = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    return new Datos_Personales(rs.getInt("ID"), rs.getString("NOMBRE"), rs.getString("APELLIDO"),
                            rs.getInt("DNI"));
                else
                    return null;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar los datos personales: " + e.getMessage());
            return null;
        }
    }

    /**
     * Buscar datos personales por ID
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param id ID a buscar
     * @return Datos_Personales o null si no se encuentra
     * 
     */
    @Override
    public Datos_Personales buscarPorID(int id) {
        String sql = "SELECT ID, NOMBRE, APELLIDO, DNI FROM datos_personales WHERE ID = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    return new Datos_Personales(rs.getInt("ID"), rs.getString("NOMBRE"), rs.getString("APELLIDO"),
                            rs.getInt("DNI"));
                else
                    return null;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar los datos personales: " + e.getMessage());
            return null;
        }
    }

    /**
     * Actualiza los datos personales en la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param datosPersonales El objeto con los datos a actualizar.
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    @Override
    public boolean actualizar(Datos_Personales datosPersonales) {
        // Implementacion no necesaria para el entregable Nro 2.
        System.out.println("FUNCIONALIDAD NO IMPLEMENTADA");
        return false;
    }

    /**
     * Devuelve una lista con todos los datos personales de la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return Lista con todos los datos personales de la DB.
     */
    @Override
    public List<Datos_Personales> devolverListaDatosPersonales() {
        List<Datos_Personales> lista = new ArrayList<>();
        String sql = """
                    SELECT ID, NOMBRE, APELLIDO, DNI
                     FROM DATOS_PERSONALES
                """;
        try (java.sql.Connection conn = ConexionDB.conectar();
                java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Datos.
                int id = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                String apellido = rs.getString("APELLIDO");
                int dni = rs.getInt("DNI");
                // Se crea el objeto.
                lista.add(new Datos_Personales(id, nombre, apellido, dni));
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar los Datos Personales: " + e.getMessage());
            return null;
        }
        return lista;

    }
}
