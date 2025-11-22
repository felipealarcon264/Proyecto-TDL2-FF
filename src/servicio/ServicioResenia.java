package servicio;

import dao.interfaces.PeliculaDAO;
import modelo.catalogo.Contenido;
import modelo.catalogo.Resenia;
import modelo.ente.Usuario;
import dao.FactoryDAO;
import dao.interfaces.ReseniaDAO;
import excepciones.CampoVacio;
import excepciones.DatosInvalidosException;

import java.util.List;

public class ServicioResenia {
    ReseniaDAO reseniaDAO;
    PeliculaDAO peliculaDAO;

    public ServicioResenia() {
        this.reseniaDAO = FactoryDAO.getReseniaDAO();
        this.peliculaDAO = FactoryDAO.getPeliculaDAO();
    }

    /**
     * Suponemos que el contenido y usuario siempre se pasan <No hay manera que se
     * envien nulos>
     * 
     * @param usuario
     * @param contenido
     * @param calificacion
     * @param comentario
     */
    public void crearNuevaResenia(Usuario usuario, Contenido contenido, int calificacion, String comentario)
            throws CampoVacio, DatosInvalidosException {
        if (calificacion == 0 || comentario.trim().isEmpty())
            throw new CampoVacio("Todos los campos son obligatorios.");

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
     * @return Una lista de objetos {@link Resenia}.
     */
    public List<Resenia> obtenerReseniasDeUsuario(int idUsuario) {
        List<Resenia> reseniasDelUsuario = new java.util.ArrayList<>();
        List<Resenia> todasLasResenias = reseniaDAO.devolverListaResenia(); // Se busca en la DB
        if (todasLasResenias == null)
            return reseniasDelUsuario; // En caso de error en DAO
        for (Resenia resenia : todasLasResenias) {
            if (resenia.getUsuario() != null && resenia.getUsuario().getIdDB() == idUsuario) {
                reseniasDelUsuario.add(resenia);
            }
        }
        return reseniasDelUsuario;
    }

    /**
     * Verifica si ya existe una reseña de un usuario para una película específica.
     * 
     * @return true si ya existe, false si no.
     */
    public boolean existeResenia(int idUsuario, int idPelicula) {
        List<Resenia> todas = reseniaDAO.devolverListaResenia();
        // Si la lista es nula (error en DB), asumimos false para no bloquear, o true
        // por seguridad.
        if (todas == null)
            return false;

        for (Resenia r : todas) {
            // Comparamos IDs
            if (r.getUsuario().getIdDB() == idUsuario && r.getContenido().getIdDB() == idPelicula) {
                return true; // ¡Encontrada!
            }
        }
        return false;
    }

    public ReseniaDAO getReseniaDAOImpl() {
        return reseniaDAO;
    }

    public void setReseniaDAOImpl(ReseniaDAO reseniaDAOImpl) {
        this.reseniaDAO = reseniaDAOImpl;
    }

}
