package Streaming;

import Catalogo.Audio;
import Catalogo.Calidad;
import Catalogo.Contenido;
import Catalogo.Subtitulo;
import Enums.Estado;
import Entes.Perfil;

/**
 * Gestiona la reproduccion de un contenido audiovisual.
 * Controla el estado (play/pause), la seleccion de audio, subtitulos,
 * calidad y el tiempo de reproduccion.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.2
 */
public class Reproductor {

    private Estado estado;
    private Contenido contenidoActual;
    private Subtitulo subtituloActivo;
    private Audio pistaAudioActiva;
    private Calidad calidadActual;
    private SesionDeStreaming sesion;

    /**
     * Constructor para crear un Reproductor para un contenido especifico.
     * El reproductor se inicializa en estado PAUSA.
     * @param contenido El contenido que se va a reproducir.
     * @param perfil El perfil que esta viendo el contenido.
     */
    public Reproductor(Contenido contenido, Perfil perfil) {
        this.contenidoActual = contenido;
        this.estado = Estado.PAUSE;
        this.sesion = new SesionDeStreaming(perfil, contenido); // Inicia una nueva sesion
    }

    // --- Controles de Reproduccion ---

    /**
     * Inicia o reanuda la reproduccion del contenido.
     * Cambia el estado a PLAY.
     */
    public void play() {
        this.estado = Estado.PLAY;
        System.out.println("▶️ Reproduciendo: " + contenidoActual.getTitulo());
    }

    /**
     * Pausa la reproduccion del contenido.
     * Cambia el estado a PAUSE.
     */
    public void pause() {
        this.estado = Estado.PAUSE;
        System.out.println("⏸️ Pausado: " + contenidoActual.getTitulo());
    }

    /**
     * Adelanta la reproduccion un numero determinado de segundos.
     * @param segundos El numero de segundos a adelantar.
     */
    public void adelantar(int segundos) {
        this.sesion.setTiempoVisto(this.sesion.getTiempoVisto() + segundos);
        System.out.println("⏩ Adelantando " + segundos + " segundos. Tiempo actual: " + this.sesion.getTiempoVisto() + "s");
    }

    /**
     * Retrocede la reproduccion un numero determinado de segundos.
     * @param segundos El numero de segundos a retroceder.
     */
    public void retroceder(int segundos) {
        int nuevoTiempo = Math.max(0, this.sesion.getTiempoVisto() - segundos);
        this.sesion.setTiempoVisto(nuevoTiempo);
        System.out.println("⏪ Retrocediendo " + segundos + " segundos. Tiempo actual: " + this.sesion.getTiempoVisto() + "s");
    }

    /**
     * Cambia el subtitulo activo.
     * @param subtitulo El nuevo subtitulo a mostrar.
     */
    public void seleccionarSubtitulo(Subtitulo subtitulo) {
        this.subtituloActivo = subtitulo;
        System.out.println("Subtitulo cambiado a: " + subtitulo.getIdioma());
    }

    /**
     * Cambia la pista de audio activa.
     * @param audio La nueva pista de audio a reproducir.
     */
    public void seleccionarPistaAudio(Audio audio) {
        this.pistaAudioActiva = audio;
        System.out.println("Audio cambiado a: " + audio.getIdioma());
    }

    /**
     * Cambia la calidad del video.
     * @param calidad La nueva calidad de video a mostrar.
     */
    public void cambiarCalidad(Calidad calidad) {
        this.calidadActual = calidad;
        System.out.println("Calidad cambiada a: " + calidad.getResolucion());
    }

    // public Historial detenerYActualizarHistorial() {
    //     System.out.println("Deteniendo reproduccion y guardando en historial.");
    //     // Logica para finalizar la sesion y devolver el historial actualizado
    //     return null;
    // }


    // --- Getters y Setters ---

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Contenido getContenidoActual() {
        return contenidoActual;
    }

    public void setContenidoActual(Contenido contenidoActual) {
        this.contenidoActual = contenidoActual;
    }

    public Subtitulo getSubtituloActivo() {
        return subtituloActivo;
    }

    public void setSubtituloActivo(Subtitulo subtituloActivo) {
        this.subtituloActivo = subtituloActivo;
    }

    public Audio getPistaAudioActiva() {
        return pistaAudioActiva;
    }

    public void setPistaAudioActiva(Audio pistaAudioActiva) {
        this.pistaAudioActiva = pistaAudioActiva;
    }

    public Calidad getCalidadActual() {
        return calidadActual;
    }

    public void setCalidadActual(Calidad calidadActual) {
        this.calidadActual = calidadActual;
    }

    public SesionDeStreaming getSesion() {
        return sesion;
    }

    public void setSesion(SesionDeStreaming sesion) {
        this.sesion = sesion;
    }
}

