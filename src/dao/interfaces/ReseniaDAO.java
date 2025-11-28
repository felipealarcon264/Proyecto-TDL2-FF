package dao.interfaces;

import modelo.catalogo.Resenia;

import java.util.List;

/**
 * Interfaz DAO para la gestión de reseñas.
 * Define los métodos para guardar, borrar y buscar reseñas en la base de
 * datos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */
public interface ReseniaDAO {
    /**
     * Guarda una reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param resenia Dato a guardar.
     * @return Exito.
     */
    public boolean guardar(Resenia resenia);

    /**
     * Borra una reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param resenia Dato a borrar.
     * @return Exito.
     */
    public boolean borrar(Resenia resenia);

    /**
     * Busca una reseña por su id.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param id Id del dato a buscar.
     * @return Dato encontrado.
     */
    public Resenia buscarPorId(int id);

    /**
     * Actualiza una reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param resenia Dato a actualizar.
     * @return Exito.
     */
    public boolean actualizar(Resenia resenia);

    /**
     * Devuelve la lista con todos los datos de la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return Lista con los datos.
     */
    public List<Resenia> devolverListaResenia();

}
