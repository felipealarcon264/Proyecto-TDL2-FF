package Catalogo;

import java.util.ArrayList;
import java.util.List;
import Enums.Genero;
import Enums.Idioma;
import Enums.Pais;

/**
 * Clase base abstracta para todo el contenido audiovisual de la plataforma.
 * Define los atributos y comportamientos comunes como título, sinopsis,
 * duración, géneros, etc.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 2.0
 */
public abstract class Contenido {

    private int idContenido;
    private String titulo;
    private String sinopsis;
    private String director;
    private int duracion; // en minutos
    private Idioma idiomaOriginal;
    private List<String> elenco;
    private List<Subtitulo> subtitulos;
    private List<Audio> pistasDeAudio;
    private List<Calidad> calidades;
    private List<Pais> restriccionesGeograficas;
    private List<Genero> generos;
    private List<Resenia> listReseña;
    private String trailer; // URL o ruta al archivo del trailer
    private String video;   // URL o ruta al archivo principal

    /**
     * Constructor vacío.
     * Inicializa todas las listas para evitar NullPointerException.
     */
    public Contenido() {
        this.elenco = new ArrayList<>();
        this.subtitulos = new ArrayList<>();
        this.pistasDeAudio = new ArrayList<>();
        this.calidades = new ArrayList<>();
        this.restriccionesGeograficas = new ArrayList<>();
        this.generos = new ArrayList<>();
        this.listReseña = new ArrayList<>();
    }

    /**
     * Constructor para crear una instancia de Contenido con datos esenciales.
     * @param idContenido Identificador único del contenido.
     * @param titulo Título del contenido.
     * @param director Director del contenido.
     * @param duracion Duración en minutos.
     */
    public Contenido(int idContenido, String titulo, String director, int duracion) {
        this.idContenido = idContenido;
        this.titulo = titulo;
        this.director = director;
        this.duracion = duracion;
        this.elenco = new ArrayList<>();
        this.subtitulos = new ArrayList<>();
        this.pistasDeAudio = new ArrayList<>();
        this.calidades = new ArrayList<>();
        this.restriccionesGeograficas = new ArrayList<>();
        this.generos = new ArrayList<>();
        this.listReseña = new ArrayList<>();
    }

    // --- Getters y Setters ---

    public int getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Idioma getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(Idioma idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public List<String> getElenco() {
        return elenco;
    }

    public void setElenco(List<String> elenco) {
        this.elenco = elenco;
    }

    public List<Subtitulo> getSubtitulos() {
        return subtitulos;
    }

    public void setSubtitulos(List<Subtitulo> subtitulos) {
        this.subtitulos = subtitulos;
    }

    public List<Audio> getPistasDeAudio() {
        return pistasDeAudio;
    }

    public void setPistasDeAudio(List<Audio> pistasDeAudio) {
        this.pistasDeAudio = pistasDeAudio;
    }

    public List<Calidad> getCalidades() {
        return calidades;
    }

    public void setCalidades(List<Calidad> calidades) {
        this.calidades = calidades;
    }

    public List<Pais> getRestriccionesGeograficas() {
        return restriccionesGeograficas;
    }

    public void setRestriccionesGeograficas(List<Pais> restriccionesGeograficas) {
        this.restriccionesGeograficas = restriccionesGeograficas;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public List<Resenia> getListReseña() {
        return listReseña;
    }

    public void setListReseña(List<Resenia> listReseña) {
        this.listReseña = listReseña;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
