package Streaming;

import Catalogo.Contenido;
import Entes.Perfil;

/**
 * Representa una sesion de visionado de un contenido por un perfil.
 * Almacena el contenido que se esta viendo, el perfil que lo ve y el
 * progreso (tiempo visto).
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class SesionDeStreaming {

    private Perfil perfilActivo;
    private Contenido contenidoEnVista;
    private int tiempoVisto; // en segundos

    /**
     * Constructor vacio.
     */
    public SesionDeStreaming() {
    }

    /**
     * Constructor para iniciar una nueva sesion de streaming.
     * @param perfilActivo El perfil que esta viendo el contenido.
     * @param contenidoEnVista El contenido que se esta reproduciendo.
     */
    public SesionDeStreaming(Perfil perfilActivo, Contenido contenidoEnVista) {
        this.perfilActivo = perfilActivo;
        this.contenidoEnVista = contenidoEnVista;
        this.tiempoVisto = 0; // Se inicializa el tiempo en 0
    }

    // --- Getters y Setters ---

    public Perfil getPerfilActivo() {
        return perfilActivo;
    }

    public void setPerfilActivo(Perfil perfilActivo) {
        this.perfilActivo = perfilActivo;
    }

    public Contenido getContenidoEnVista() {
        return contenidoEnVista;
    }

    public void setContenidoEnVista(Contenido contenidoEnVista) {
        this.contenidoEnVista = contenidoEnVista;
    }

    public int getTiempoVisto() {
        return tiempoVisto;
    }

    public void setTiempoVisto(int tiempoVisto) {
        this.tiempoVisto = tiempoVisto;
    }
}
