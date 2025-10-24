package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Catalogo.Pelicula;
import DataBase.ConexionDB;
import Enums.Genero;

public class PeliculaDAOImpl implements PeliculaDAO {
    @Override
    public boolean guardar(Pelicula pelicula) {
        if(pelicula == null) {
            System.out.println("La película es nula. No se puede guardar.");
            return false;
        }
        String sql = "INSERT INTO PELICULA (titulo, director, duracion, resumen, genero) VALUES (?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = ConexionDB.conectar();
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pelicula.getTitulo());
            pstmt.setString(2, pelicula.getDirector());
            pstmt.setInt(3, pelicula.getDuracion());
            pstmt.setString(4, pelicula.getResumen());
            pstmt.setString(5, pelicula.getGenero().toString());
            if(pstmt.executeUpdate() > 0) {
                System.out.println("Película guardada exitosamente.");
                return true;
            } else {
                System.out.println("No se pudo guardar la película.");
                return false;
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error al guardar la película: " + e.getMessage());
            return false;
        }
        
    }

    @Override
    public boolean borrar(Catalogo.Pelicula pelicula) {
        // Implementación para borrar la película de la base de datos
        return false; // Placeholder
    }

    /**
     * SE SUPONE QUE TODA PELICULA TIENE UN GENERO VALIDO
     */
    @Override
    public Pelicula buscarPorTitulo(String titulo) {
        String sql = "SELECT TITULO, DIRECTOR, DURACION, RESUMEN, GENERO FROM PELICULA WHERE TITULO = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titulo);
            var rs = pstmt.executeQuery();
            if (rs.next()){
                String generoSTR = rs.getString("GENERO");
                Genero generoEnum = Genero.valueOf(generoSTR.toUpperCase());
                System.out.println("Pelicula ["+titulo+"] Encontrada correcta.");
                return new Pelicula(rs.getString("TITULO"),rs.getString("DIRECTOR"),rs.getInt("DURACION"),rs.getString("RESUMEN"),generoEnum);
            }
                else
                System.out.println("Pelicula ["+titulo+"] No pertenece a la base de datos.");
                return null;
        } catch (SQLException e) {
            System.out.println("Error al buscar los datos personales: " + e.getMessage());
            return null;
        }
    }
}
