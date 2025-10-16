package Catalogo;

import Enums.Idioma;

/**
 * Representa un archivo de subtítulos para un contenido.
 * Cada subtítulo está asociado a un idioma específico y tiene una URL
 * desde donde se puede cargar.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Subtitulo {

    private Idioma idioma;
    private String url;

    /**
     * Constructor vacío.
     */
    public Subtitulo() {
    }

    /**
     * Constructor para crear una instancia de Subtitulo con datos iniciales.
     * @param idioma El idioma del subtítulo.
     * @param url La ruta o URL del archivo de subtítulos.
     */
    public Subtitulo(Idioma idioma, String url) {
        this.idioma = idioma;
        this.url = url;
    }

    // --- Getters y Setters ---

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
