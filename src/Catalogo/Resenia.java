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
    private int calificacion;
    private String comentario;
    private int aprovado; //1 aprobado, 0 pendiente.
    private Usuario usuario;
    private Contenido contenido;


    /**
     * Constructor vacío.
     */
    public Resenia() {
    }

    /**
     * Constructor para crear una instancia de Reseña con datos iniciales.
     * @param idDB El ID de la reseña en la base de datos.
     * @param calificacion La puntuación dada (ej: 1 a 5).
     * @param comentario El texto de la reseña.
     * @param aprovado Aprovacion por parte de un adm.
     * @param usuario El usuario que realiza la reseña.
     * @param contenido El contenido que está siendo reseñado.
     */
    public Resenia(int idDB, int calificacion, String comentario, int aprovado, Usuario usuario, Contenido contenido) {
        this.idDB = idDB;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.aprovado = aprovado;
        this.usuario = usuario;
        this.contenido = contenido;
    }

    // --- Getters y Setters ---

    public int getIdDB() {
        return idDB;
    }

    public void setIdDB(int idDB) {
        this.idDB = idDB;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public void setContenido(Contenido contenido) {
        this.contenido = contenido;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public int getAprobado() {
        return aprovado;
    }
    public void setAprobado(int aprovado) {
        this.aprovado = aprovado;
    }

    /**
     * Devuelve una representación en formato String del objeto Resenia.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
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
        sb.append("  estado=").append(aprovado == 1 ? "Aprobado" : "Pendiente").append("\n");
        sb.append("}");
        return sb.toString();
    }


}
