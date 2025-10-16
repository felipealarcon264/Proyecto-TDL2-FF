package Catalogo;

import Enums.Resolucion;

/**
 * Representa una calidad de video disponible para un contenido.
 * Cada calidad tiene asociada una resolución y una URL al archivo de video
 * correspondiente a esa resolución.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.1
 */
public class Calidad {

    private String url;
    private Resolucion resolucion;


    /**
     * Constructor vacío.
     */
    public Calidad() {
    }

    /**
     * Constructor para crear una instancia de Calidad con datos iniciales.
     * @param url La ruta o URL del archivo de video para esta calidad.
     * @param resolucion La resolución del video (ej: FULL_HD_1080P).
     */
    public Calidad(String url, Resolucion resolucion) {
        this.url = url;
        this.resolucion = resolucion;
    }


    // --- Getters y Setters ---

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Resolucion getResolucion() {
        return resolucion;
    }

    public void setResolucion(Resolucion resolucion) {
        this.resolucion = resolucion;
    }
}

