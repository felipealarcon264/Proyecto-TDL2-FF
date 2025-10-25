package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Catalogo.Pelicula;
import DataBase.ConexionDB;
import Enums.Genero;

/**
 * Implementacion de la interfaz PeliculaDAO
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */

public class PeliculaDAOImpl implements PeliculaDAO {

    /**
     * Guarda una película en la base de datos.
     * Tomas las precauciones necesarias para no cometer errores.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return true si se logro guardar la pelicula correctamente, false en caso
     *         contrario.
     */
    @Override
    public boolean guardar(Pelicula pelicula) {
        if (pelicula == null) {
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
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Película guardada exitosamente.");
                return true;
            } else {
                System.out.println("❌No se pudo guardar la película.");
                return false;
            }
        } catch (java.sql.SQLException e) {
            System.out.println("❌Error al guardar la película: " + e.getMessage());
            return false;
        }

    }

    /**
     * Borra una película de la base de datos.
     * Utiliza el metodo buscar por titulo para asegurarse que la pelicula existe en
     * la base de datos.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param pelicula pelicula que se quiere borrar.
     * @return true si la pelicula se borro correctamente, false en caso contrario.
     */
    @Override
    public boolean borrar(Catalogo.Pelicula pelicula) {
        if (buscarPorTitulo(pelicula.getTitulo()) == null) {
            System.out.println("La pelicula no existe en la base de datos. ❌NO SE PUEDE BORRAR.");
            return false;
        }
        String sql = "DELETE FROM PELICULA WHERE TITULO = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pelicula.getTitulo());
            // Encuentra la pelicula si o si pues se verifico antes que exista.
            pstmt.executeUpdate();
            System.out.println("Pelicula borrada correctamente.");
            return true;
        } catch (SQLException e) {
            System.out.println("❌Error al borrar la pelicula: " + e.getMessage());
            return false; // Placeholder
        }
    }

    /**
     * Busca una pelicula por su titulo.
     * Supone que el Genero es valido como enum pues se tomo la precuacion al
     * cargarlo y guardarlo.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     *
     * @param titulo
     * @return La pelicula en caso de encontrarla o null en caso contrario.
     */
    @Override
    public Pelicula buscarPorTitulo(String titulo) {
        String sql = "SELECT TITULO, DIRECTOR, DURACION, RESUMEN, GENERO FROM PELICULA WHERE TITULO = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titulo);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                String generoSTR = rs.getString("GENERO");
                Genero generoEnum = Genero.valueOf(generoSTR.toUpperCase());
                System.out.println("Pelicula [" + titulo + "] Encontrada correcta.");
                return new Pelicula(rs.getString("TITULO"), rs.getString("DIRECTOR"), rs.getInt("DURACION"),
                        rs.getString("RESUMEN"), generoEnum);
            } else
                System.out.println("Pelicula [" + titulo + "] No pertenece a la base de datos.");
            return null;
        } catch (SQLException e) {
            System.out.println("❌Error al buscar la pelicula: " + e.getMessage());
            return null;
        }
    }
}
