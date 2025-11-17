package dao.implementaciones;

import basededatos.ConexionDB;
import modelo.catalogo.Pelicula;
import dao.interfaces.PeliculaDAO;
import modelo.enums.Genero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion de la interfaz PeliculaDAO
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.1
 * 
 */

public class PeliculaDAOImpl implements PeliculaDAO {
    /**
     * Constructor por defecto.
     */
    public PeliculaDAOImpl() {
    }

    /**
     * Guarda una película en la base de datos.
     * Tomas las precauciones necesarias para no cometer errores.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @param pelicula La película a guardar.
     * @return true si se logró guardar la película, false en caso contrario.
     */
    @Override
    public boolean guardar(Pelicula pelicula) {
        if (pelicula == null) {
            System.out.println("❌ La película es nula. No se puede guardar.");
            return false;
        }
        String sql = "INSERT INTO PELICULA (titulo, director, duracion, resumen, genero, rating_promedio, anio, poster) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = ConexionDB.conectar();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pelicula.getTitulo());
            pstmt.setString(2, pelicula.getDirector());
            pstmt.setInt(3, pelicula.getDuracion());
            pstmt.setString(4, pelicula.getResumen());
            pstmt.setString(5, pelicula.getGenero().name());
            pstmt.setDouble(6, pelicula.getRatingPromedio());
            pstmt.setInt(7, pelicula.getAnio());
            pstmt.setString(8, pelicula.getPoster());
            if (pstmt.executeUpdate() > 0) {
                System.out.println("✅ Película guardada exitosamente.");
                return true;
            } else {
                System.out.println("❌ No se pudo guardar la película.");
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
     * @version 1.1
     * 
     * @param pelicula La película que se quiere borrar.
     * @return true si la pelicula se borro correctamente, false en caso contrario.
     */
    @Override
    public boolean borrar(Pelicula pelicula) {
        if (buscarPorTitulo(pelicula.getTitulo()) == null) {
            System.out.println("❌ La película no existe en la base de datos. No se puede borrar.");
            return false;
        }
        if (buscarPorTitulo(pelicula.getTitulo()).getDuracion() != pelicula.getDuracion()) {
            System.out.println("❌ La película no existe en la base de datos. No se puede borrar.");
            return false;
        } // Nos aseguramos que no sea nuestra pelicula.
        String sql = "DELETE FROM PELICULA WHERE TITULO = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pelicula.getTitulo());
            // Encuentra la pelicula si o si pues se verifico antes que exista.
            pstmt.executeUpdate();
            System.out.println("✅ Película borrada correctamente.");
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al borrar la película: " + e.getMessage());
            return false; // Placeholder
        }
    }

    /**
     * Busca una pelicula por su titulo.
     * Supone que el Genero es valido como enum pues se tomo la precuacion al
     * cargarlo y guardarlo.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     *
     * @param titulo El título de la película a buscar.
     * @return La pelicula en caso de encontrarla o null en caso contrario.
     */
    @Override
    public Pelicula buscarPorTitulo(String titulo) {
        String sql = "SELECT ID, TITULO, DIRECTOR, DURACION, RESUMEN, GENERO, RATING_PROMEDIO, ANIO, POSTER FROM PELICULA WHERE TITULO = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titulo);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                String generoSTR = rs.getString("GENERO");
                Genero generoEnum = Genero.valueOf(generoSTR.toUpperCase());
                System.out.println("ℹ️ Película [" + titulo + "] encontrada.");
                return new Pelicula(rs.getInt("ID"), rs.getString("TITULO"), rs.getString("DIRECTOR"),
                        rs.getInt("DURACION"),
                        rs.getString("RESUMEN"), generoEnum,
                        rs.getDouble("RATING_PROMEDIO"), rs.getInt("ANIO"),
                        rs.getString("POSTER"));
            } else
                System.out.println("❌ Película [" + titulo + "] no encontrada en la base de datos.");
            return null;
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar la película: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca una pelicula por su ID.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     *
     * @param id El ID de la película a buscar.
     * @return La pelicula en caso de encontrarla o null en caso contrario.
     */
    @Override
    public Pelicula buscarPorId(int id) {
        String sql = "SELECT ID, TITULO, DIRECTOR, DURACION, RESUMEN, GENERO, RATING_PROMEDIO, ANIO, POSTER FROM PELICULA WHERE ID = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Genero generoEnum = Genero.valueOf(rs.getString("GENERO").toUpperCase());
                    return new Pelicula(rs.getInt("ID"), rs.getString("TITULO"), rs.getString("DIRECTOR"), rs.getInt("DURACION"),
                            rs.getString("RESUMEN"), generoEnum,
                            rs.getDouble("RATING_PROMEDIO"), rs.getInt("ANIO"),
                            rs.getString("POSTER"));
                }
            }
            return null; // No se encontró película con ese ID
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar la película por ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Devuelve una lista con todos las peliculas de la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @return Una lista con todas las películas de la base de datos, o null si
     *         ocurre un error.
     */
    @Override
    public List<Pelicula> devolverListaPelicula() {
        List<Pelicula> lista = new ArrayList<>();
        String sql = """ 
                    SELECT ID, GENERO, TITULO, RESUMEN, DIRECTOR, DURACION, RATING_PROMEDIO, ANIO, POSTER
                     FROM PELICULA
                """;
        try (java.sql.Connection conn = ConexionDB.conectar();
                java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Datos de la pelicula.
                int id = rs.getInt("ID");
                Genero generoEnum = Genero.valueOf(rs.getString("GENERO").toUpperCase());
                String titulo = rs.getString("TITULO");
                String resumen = rs.getString("RESUMEN");
                String director = rs.getString("DIRECTOR");
                int duracion = rs.getInt("DURACION");
                double ratingPromedio = rs.getDouble("RATING_PROMEDIO");
                int anio = rs.getInt("ANIO");
                String poster = rs.getString("POSTER");
                // Se crea el objeto pelicula usando su constructor cargandolo en la lista.
                lista.add(new Pelicula(id, titulo, director, duracion, resumen, generoEnum, ratingPromedio, anio,
                        poster));
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar las películas: " + e.getMessage());
            return null;
        }
        return lista;

    }

    /**
     * Actualiza una peliucla de la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param pelicula El objeto con la pelicula a actualizar.
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    @Override
    public boolean actualizar(Pelicula pelicula) {
        // Implementacion no necesaria para el entregable Nro 2.
        System.out.println("FUNCIONALIDAD NO IMPLEMENTADA");
        return false;
    }
}
