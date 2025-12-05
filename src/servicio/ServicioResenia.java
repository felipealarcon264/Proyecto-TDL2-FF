//Verificacion JavaDoc -> Realizada.
package servicio;

import dao.interfaces.PeliculaDAO;
import modelo.catalogo.Contenido;
import modelo.catalogo.Resenia;
import modelo.ente.Usuario;
import dao.FactoryDAO;
import dao.interfaces.ReseniaDAO;
import excepciones.CampoVacioException;
import excepciones.DatosInvalidosException;
import java.util.List;

/**
 * Servicio para operaciones relacionadas con las reseñas.
 * Utiliza ReseniaDAO y PeliculaDAO para interactuar con la base de datos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ServicioResenia {
    private ReseniaDAO reseniaDAO;//Se usa externamente.
    private PeliculaDAO peliculaDAO;//Se usa externamente.

    /**
     * Constructor de ServicioResenia.
     * Crea instancias de ReseniaDAO y PeliculaDAO con el FactoryDAO.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public ServicioResenia() {
        this.reseniaDAO = FactoryDAO.getReseniaDAO();
        this.peliculaDAO = FactoryDAO.getPeliculaDAO();
    }

    /**
     * Suponemos que el contenido y usuario siempre se pasan. En cada boton de
     * creacion de una reseñia se pasan.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param usuario      Usuario que hace la reseña
     * @param contenido    Contenido al que se hace la reseña
     * @param calificacion Calificacion dada por el usuario
     * @param comentario   Comentario escrito por el usuario
     * @throws CampoVacioException              Si algun campo esta vacio
     * @throws DatosInvalidosException Si la calificacion esta fuera de rango o el
     *                                 comentario tiene mas de 200 caracteres
     */
    public void crearNuevaResenia(Usuario usuario, Contenido contenido, int calificacion, String comentario)
            throws CampoVacioException, DatosInvalidosException {
        if (calificacion == 0 || comentario.trim().isEmpty())
            throw new CampoVacioException("Todos los campos son obligatorios.");

        if (calificacion < 1 || calificacion > 10)
            throw new DatosInvalidosException("Error al ingresar la calificacion.");

        if (comentario.length() > 200)
            throw new DatosInvalidosException("El comentario no puede tener más de 200 caracteres.");

        Resenia resenia = new Resenia(-1, calificacion, comentario, 0, usuario, contenido);
        boolean exito = reseniaDAO.guardar(resenia);
        if (!exito) {
            // Un error genérico si la BD falla por otra razón
            throw new RuntimeException("Error desconocido al guardar en la base de datos.");
        }

    }

    /**
     * Filtra y devuelve una lista de reseñas que pertenecen a un usuario
     * específico.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param idUsuario El ID del usuario cuyas reseñas se quieren obtener.
     * @return Una lista de reseñias del usuario especificado.
     */
    public List<Resenia> obtenerReseniasDeUsuario(int idUsuario) {
        List<Resenia> reseniasDelUsuario = new java.util.ArrayList<>();
        List<Resenia> todasLasResenias = reseniaDAO.devolverListaResenia(); // Se busca en la DB
        if (todasLasResenias == null)
            return reseniasDelUsuario; // No hay reseñias en la DB.
        for (Resenia resenia : todasLasResenias) {// Filtramos las reseñias del usuario.
            if (resenia.getUsuario() != null && resenia.getUsuario().getIdDB() == idUsuario) {
                reseniasDelUsuario.add(resenia);
            }
        }
        return reseniasDelUsuario;
    }

    /**
     * Verifica si ya existe una reseña de un usuario para una película específica.
     * No se permiten mas de una reseñia por usuario y pelicula.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param idUsuario  El ID del usuario que hace la reseña.
     * @param idPelicula El ID de la película a la que se hace la reseña.
     * 
     * @return true si ya existe, false si no.
     */
    public boolean existeResenia(int idUsuario, int idPelicula) {
        List<Resenia> todas = reseniaDAO.devolverListaResenia();
        if (todas == null)
            return false;
        for (Resenia r : todas) {
            if (r.getUsuario().getIdDB() == idUsuario && r.getContenido().getIdDB() == idPelicula) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene la reseña con el ID especificado.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     */
    public PeliculaDAO getPeliculaDAOImpl() {
        return peliculaDAO;
    }

    /**
     * Obtiene el DAO de Resenia.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return El DAO de Resenia.
     */
    public ReseniaDAO getReseniaDAOImpl() {
        return reseniaDAO;
    }
}
