package DAO;

import DataBase.ConexionDB;
import Entes.Datos_Personales;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Datos_PersonalesDAOImpl implements Datos_PersonalesDAO {
    /*
     * Guardar datos personales
     * @param dp Datos_Personales a guardar
     * @autor Grupo 4 - Taller de lenguajes II
     */
    @Override
    public void guardar(Datos_Personales dp) {
        String sql = "INSERT INTO datos_personales (NOMBRE, APELLIDO, DNI) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dp.getNombre());
            pstmt.setString(2, dp.getApellido());
            pstmt.setInt(3, dp.getDni());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar los datos personales: " + e.getMessage());
        }
    }
    /*
     * Borrar datos personales
     * Verifica si el objeto Datos_Personales es nulo antes de intentar borrarlo.
     * @param dp Datos_Personales a borrar
     * @autor Grupo 4 - Taller de lenguajes II
     */
    @Override
    public void borrar(Datos_Personales dp) {
        if (dp == null) {
            System.out.println("No se puede borrar datos personales nulos.");
            return;
        }
        String sql = "DELETE FROM datos_personales WHERE dni = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dp.getDni());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al borrar los datos personales: " + e.getMessage());
        }
    }

    /*
     * Buscar datos personales por DNI
     * @param dni DNI a buscar
     * @return Datos_Personales o null si no se encuentra
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
