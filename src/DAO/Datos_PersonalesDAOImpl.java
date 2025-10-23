package DAO;

import DataBase.ConexionDB;
import Entes.Datos_Personales;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Datos_PersonalesDAOImpl implements Datos_PersonalesDAO {
    /*
     * Guarda datos personales a la base de datos
     * 
     * @param dp Datos_Personales a guardar
     * @return true si se guardaron los datos personales correctamente, false en caso contrario
     * 
     * @autor Grupo 4 - Taller de lenguajes II
     * @version 1.1
     */
    @Override
    public boolean guardar(Datos_Personales dp) {
        String sql = "INSERT INTO datos_personales (NOMBRE, APELLIDO, DNI) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dp.getNombre());
            pstmt.setString(2, dp.getApellido());
            pstmt.setInt(3, dp.getDni());
            pstmt.executeUpdate();
            System.out.println("Datos personales guardados correctamente.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar los datos personales: " + e.getMessage());
            return false;
        }
    }

    /*
     * Borrar datos personales
     * Verifica si el objeto Datos_Personales es nulo antes de intentar borrarlo.\
     * Recupera el valor del dni del objeto Datos_Personales para usarlo en la
     * consulta SQL.
     * 
     * @param dp Datos_Personales a borrar
     * @return true si se borraron los datos personales correctamente, false en caso contrario
     * 
     * @autor Grupo 4 - Taller de lenguajes II
     * @version 1.1
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

    /*
     * Buscar datos personales por DNI
     * 
     * @param dni DNI a buscar
     * @return Datos_Personales o null si no se encuentra
     * 
     * @autor Grupo 4 - Taller de lenguajes II
     * @version 1.0
     */
    @Override
    public Datos_Personales buscarPorDNI(int dni) {
        String sql = "SELECT NOMBRE, APELLIDO, DNI FROM datos_personales WHERE DNI = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dni);
            var rs = pstmt.executeQuery();
            if (rs.next())
                return new Datos_Personales(rs.getString("NOMBRE"), rs.getString("APELLIDO"), rs.getInt("DNI"));
            else
                return null;
        } catch (SQLException e) {
            System.out.println("Error al buscar los datos personales: " + e.getMessage());
            return null;
        }
    }
}
