package Catalogo;

import Entes.Usuario;

/**
 * Representa una reseña hecha por un usuario sobre un contenido específico.
 * Incluye una calificación numérica y un comentario de texto.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Resenia {

    private int idDB;// el valor real se lo dara la base de datos cuando se cargue.
    private int calificacion; // Del 0-5
    private String comentario;
    private int aprobado; // 1 aprobado, 0 pendiente.
    private Usuario usuario;
    private Contenido contenido;

    /**
     * Constructor vacío.
     */
    public Resenia() {
    }

    /**
     * Constructor para crear una instancia de Reseña con datos iniciales.
     * 
     * @param idDB         El ID de la reseña en la base de datos.
     * @param calificacion La puntuación dada (ej: 1 a 5).
     * @param comentario   El texto de la reseña.
     * @param aprobado     Aprobacion por parte de un adm.
     * @param usuario      El usuario que realiza la reseña.
     * @param contenido    El contenido que está siendo reseñado.
     */
    public Resenia(int idDB, int calificacion, String comentario, int aprobado, Usuario usuario, Contenido contenido) {
        this.idDB = idDB;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.aprobado = aprobado;
        this.usuario = usuario;
        this.contenido = contenido;
    }

    // --- Getters y Setters ---

    /**
     * Obtiene el ID de la reseña en la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return El ID de la reseña.
     */
    public int getIdDB() {
        return idDB;
    }

    /**
     * Establece el ID de la reseña en la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param idDB El nuevo ID de la reseña.
     */
    public void setIdDB(int idDB) {
        this.idDB = idDB;
    }

    /**
     * Obtiene el usuario que creó la reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return El objeto Usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario que creó la reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param usuario El nuevo objeto Usuario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el contenido (película) asociado a la reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return El objeto Contenido.
     */
    public Contenido getContenido() {
        return contenido;
    }

    /**
     * Establece el contenido (película) asociado a la reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param contenido El nuevo objeto Contenido.
     */
    public void setContenido(Contenido contenido) {
        this.contenido = contenido;
    }

    /**
     * Obtiene la calificación de la reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return La calificación (0-5).
     */
    public int getCalificacion() {
        return calificacion;
    }

    /**
     * Establece la calificación de la reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param calificacion La nueva calificación.
     */
    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * Obtiene el comentario de texto de la reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return El comentario.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Establece el comentario de texto de la reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param comentario El nuevo comentario.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Obtiene el estado de aprobación de la reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return 1 si está aprobada, 0 si está pendiente.
     */
    public int getAprobado() {
        return aprobado;
    }

    /**
     * Establece el estado de aprobación de la reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param aprobado El nuevo estado (1 para aprobado, 0 para pendiente).
     */
    public void setAprobado(int aprobado) {
        this.aprobado = aprobado;
    }

    /**
     * Devuelve una representación en formato String del objeto Resenia.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return Un String con los detalles de la reseña.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resenia{\n");
        sb.append("  idDB=").append(idDB).append(",\n");
        sb.append("  pelicula='").append(contenido != null ? contenido.getTitulo() : "N/A").append("',\n");
        sb.append("  usuario='").append(usuario != null ? usuario.getNombreUsuario() : "N/A").append("',\n");
        sb.append("  calificacion=").append(calificacion).append("/5,\n");
        sb.append("  comentario='").append(comentario).append("',\n");
        sb.append("  estado=").append(aprobado == 1 ? "Aprobado" : "Pendiente").append("\n");
        sb.append("}");
        return sb.toString();
    }

}
