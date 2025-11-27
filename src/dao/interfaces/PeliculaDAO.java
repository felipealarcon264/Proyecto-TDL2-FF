package dao.interfaces;

import modelo.catalogo.Pelicula;

import java.util.List;

/**
 * Interfaz DAO para la gestión de películas.
 * Define los métodos para guardar y borrar películas en la base de datos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public interface PeliculaDAO {

    /**
     * Guarda una película en la base de datos.
     * 
     * @param pelicula Dato a guardar.
     * @return Exito.
     */
    public boolean guardar(Pelicula pelicula);

    /**
     * Borra una pelicula.
     * @param pelicula Dato a borrar.
     * @return Exito.
     */
    public boolean borrar(Pelicula pelicula);
/**
 * Busca pelicula por titulo.
 * @param titulo Titulo de pelicula a buscar.
 * @return El dato encontrado.
 */
    public Pelicula buscarPorTitulo(String titulo);

    /**Busca por titulo y duracion.
     * 
     * @param titulo Titulo de la pelicula.
     * @param resumen Resumen de la pelicula.
     * @return El dato encontrado o no.
     */
    public Pelicula buscarPorTituloyResumen(String titulo,String resumen);

    /**
     * Busca pelicula por ID.
     * @param id Id del dato a buscar.
     * @return El dato encontrado.
     */
    public Pelicula buscarPorId(int id);

    /**
     * Actualiza una pelicula.
     * @param pelicula Dato a actualizar.
     * @return Exito.
     */
    public boolean actualizar(Pelicula pelicula);

    /**
     * Devuelve la lista con todos los datos de la base de datos.
     * @return Lista con los datos.
     */
    public List<Pelicula> devolverListaPelicula();

}
