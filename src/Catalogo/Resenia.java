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

    private Usuario usuario;
    private Contenido contenido;
    private int calificacion;
    private String comentario;


    /**
     * Constructor vacío.
     */
    public Resenia() {
    }

    /**
     * Constructor para crear una instancia de Reseña con datos iniciales.
     * @param usuario El usuario que realiza la reseña.
     * @param contenido El contenido que está siendo reseñado.
     * @param calificacion La puntuación dada (ej: 1 a 5).
     * @param comentario El texto de la reseña.
     */
    public Resenia(Usuario usuario, Contenido contenido, int calificacion, String comentario) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }


    // --- Getters y Setters ---

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
}
