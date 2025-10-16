package Catalogo;

import Enums.Idioma;

/**
 * Representa una pista de audio disponible para un contenido.
 * Cada pista está asociada a un idioma específico y tiene una URL
 * que apunta al archivo de audio.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Audio {

    private Idioma idioma;
    private String url;

    /**
     * Constructor vacío.
     */
    public Audio() {
    }

    /**
     * Constructor para crear una instancia de Audio con datos iniciales.
     * @param idioma El idioma de la pista de audio.
     * @param url La ruta o URL del archivo de audio.
     */
    public Audio(Idioma idioma, String url) {
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
