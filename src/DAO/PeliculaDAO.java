package DAO;

import Catalogo.Pelicula;
/**
 * Interfaz DAO para la gestión de películas.
 * Define los métodos para guardar y borrar películas en la base de datos.
 * 
 * @author Grupo 4 - Taller de lenguajes II
 * @version 1.0
 */
public interface PeliculaDAO {
    public boolean guardar(Pelicula pelicula);
    public boolean borrar(Pelicula pelicula);
    public Pelicula buscarPorTitulo(String titulo);

}
