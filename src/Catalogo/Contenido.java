package Catalogo;

//import java.util.ArrayList;
//import java.util.List;
import Enums.Genero;
//import Enums.Idioma;
//No se necesita en la segunda entrega.import Enums.Pais;

/**
 * Clase base abstracta para todo el contenido audiovisual de la plataforma.
 * Define los atributos y comportamientos comunes como título, resumen,
 * duración, géneros, etc.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 2.0
 */
public abstract class Contenido {

    // private int idContenido; //No se necesita en la segunda entrega.
    private String titulo;
    private String resumen;
    private String director;
    private int duracion; // En segundos
    // private Idioma idiomaOriginal; //No se necesita en la segunda entrega.
    // private List<String> elenco; //No se necesita en la segunda entrega.
    // private List<Subtitulo> subtitulos; //No se necesita en la segunda entrega.
    // private List<Audio> pistasDeAudio; //No se necesita en la segunda entrega.
    // private List<Calidad> calidades; //No se necesita en la segunda entrega.
    // private List<Pais> restriccionesGeograficas; //No se necesita en la segunda
    // entrega.
    private Genero genero; // En realidad es una lista de géneros.
    // private List<Resenia> listReseña; //No se necesita en la segunda entrega.
    // private String trailer; // URL o ruta al archivo del trailer //No se necesita
    // en la segunda entrega.
    // private String video; // URL o ruta al archivo principal //No se necesita en
    // la segunda entrega.

    /**
     * Constructor vacío.
     * Inicializa todas las listas para evitar NullPointerException.
     */
    public Contenido() {
        // No se necesita en la segunda entrega.this.elenco = new ArrayList<>();
        // No se necesita en la segunda entrega.this.subtitulos = new ArrayList<>();
        // No se necesita en la segunda entrega.this.pistasDeAudio = new ArrayList<>();
        // No se necesita en la segunda entrega.this.calidades = new ArrayList<>();
        // No se necesita en la segunda entrega.this.restriccionesGeograficas = new
        // ArrayList<>();
        // No se necesita en la segunda entrega.this.generos = new ArrayList<>();
        // No se necesita en la segunda entrega.this.listReseña = new ArrayList<>();
    }

    /**
     * Constructor para crear una instancia de Contenido con datos esenciales.
     * 
     * @param idContenido Identificador único del contenido.
     * @param titulo      Título del contenido.
     * @param director    Director del contenido.
     * @param duracion    Duración en minutos.
     */
    public Contenido(String titulo, String director, int duracion, String resumen, Genero genero) {
        // No se necesita en la segunda entrega.this.idContenido = idContenido;
        this.titulo = titulo;
        this.director = director;
        this.duracion = duracion;
        this.resumen = resumen;
        this.genero = genero;
        // No se necesita en la segunda entrega.this.elenco = new ArrayList<>();
        // No se necesita en la segunda entrega.this.subtitulos = new ArrayList<>();
        // No se necesita en la segunda entrega.this.pistasDeAudio = new ArrayList<>();
        // No se necesita en la segunda entrega.this.calidades = new ArrayList<>();
        // No se necesita en la segunda entrega.this.restriccionesGeograficas = new
        // ArrayList<>();
        // No se necesita en la segunda entrega.this.generos = new ArrayList<>();
        // No se necesita en la segunda entrega.this.listReseña = new ArrayList<>();
    }

    // --- Getters y Setters ---

    // No se necesita en la segunda entrega.public int getIdContenido() {
    // No se necesita en la segunda entrega. return idContenido;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public void setIdContenido(int
    // idContenido) {
    // No se necesita en la segunda entrega. this.idContenido = idContenido;
    // No se necesita en la segunda entrega.}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
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

    // No se necesita en la segunda entrega.public Idioma getIdiomaOriginal() {
    // No se necesita en la segunda entrega. return idiomaOriginal;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public void setIdiomaOriginal(Idioma
    // idiomaOriginal) {
    // No se necesita en la segunda entrega. this.idiomaOriginal = idiomaOriginal;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public List<String> getElenco() {
    // No se necesita en la segunda entrega. return elenco;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public void setElenco(List<String>
    // elenco) {
    // No se necesita en la segunda entrega. this.elenco = elenco;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public List<Subtitulo> getSubtitulos() {
    // No se necesita en la segunda entrega. return subtitulos;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public void
    // setSubtitulos(List<Subtitulo> subtitulos) {
    // No se necesita en la segunda entrega. this.subtitulos = subtitulos;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public List<Audio> getPistasDeAudio() {
    // No se necesita en la segunda entrega. return pistasDeAudio;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public void setPistasDeAudio(List<Audio>
    // pistasDeAudio) {
    // No se necesita en la segunda entrega. this.pistasDeAudio = pistasDeAudio;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public List<Calidad> getCalidades() {
    // No se necesita en la segunda entrega. return calidades;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public void setCalidades(List<Calidad>
    // calidades) {
    // No se necesita en la segunda entrega. this.calidades = calidades;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public List<Pais>
    // getRestriccionesGeograficas() {
    // No se necesita en la segunda entrega. return restriccionesGeograficas;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public void
    // setRestriccionesGeograficas(List<Pais> restriccionesGeograficas) {
    // No se necesita en la segunda entrega. this.restriccionesGeograficas =
    // restriccionesGeograficas;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public List<Genero> getGeneros() {
    // No se necesita en la segunda entrega. return generos;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public void setGeneros(List<Genero>
    // generos) {
    // No se necesita en la segunda entrega. this.generos = generos;
    // No se necesita en la segunda entrega.}
    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    // No se necesita en la segunda entrega.public List<Resenia> getListReseña() {
    // No se necesita en la segunda entrega. return listReseña;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public void setListReseña(List<Resenia>
    // listReseña) {
    // No se necesita en la segunda entrega. this.listReseña = listReseña;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public String getTrailer() {
    // No se necesita en la segunda entrega. return trailer;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public void setTrailer(String trailer) {
    // No se necesita en la segunda entrega. this.trailer = trailer;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public String getVideo() {
    // No se necesita en la segunda entrega. return video;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public void setVideo(String video) {
    // No se necesita en la segunda entrega. this.video = video;
    // No se necesita en la segunda entrega.}

    /**
     * Muestra los valores de un contenido
     * 
     * 
     * @return String con todos los valores.
     */
    @Override
    public String toString() {
        // El formato "Clase{variable=valor, ...}" es un estándar muy común
        // y es el que suelen generar los IDEs.
        return "{" +
                "titulo='" + titulo + '\'' +
                ", director='" + director + '\'' +
                ", duracion=" + duracion + " seg" +
                ", resumen='" + resumen + '\'' +
                ", genero=" + genero +
                '}';
    }

    // Se utiliza la clase Objects para simplificar y asegurar el manejo de nulos.

    /**
     * Compara este contenido con otro objeto para ver si son iguales.
     * Dos contenidos se consideran iguales si tienen el mismo título.
     * Se asume que el título es un identificador único.
     *
     *@author Gemini.
     *@version 1.0
     * 
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contenido contenido = (Contenido) o;
        return duracion == contenido.duracion &&
                java.util.Objects.equals(titulo, contenido.titulo) &&
                java.util.Objects.equals(resumen, contenido.resumen) &&
                java.util.Objects.equals(director, contenido.director) &&
                genero == contenido.genero;
    }

    /**
     * Devuelve un código hash para este contenido.
     * Es importante sobreescribir hashCode si se sobreescribe equals.
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(titulo, resumen, director, duracion, genero);
    }
}
