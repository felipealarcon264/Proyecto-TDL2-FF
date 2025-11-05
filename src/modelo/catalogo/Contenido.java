package modelo.catalogo;

import modelo.enums.Genero;

/**
 * Clase base abstracta para todo el contenido audiovisual de la plataforma.
 * Define los atributos y comportamientos comunes como título, resumen,
 * duración, géneros, etc.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 2.0
 */
public abstract class Contenido {

    private int idDB;
    private String titulo;
    private String resumen;
    private String director;
    private int duracion;
    private Genero genero;

    /**
     * Constructor vacío.
     * Inicializa todas las listas para evitar NullPointerException.
     */
    public Contenido() {
    }

    /**
     * Constructor para crear una instancia de Contenido con datos esenciales.
     * 
     * @param idDB     Identificador único del contenido.
     * @param titulo   Título del contenido.
     * @param director Director del contenido.
     * @param duracion Duración en minutos.
     * @param resumen  Resumen del contenido.
     * @param genero   Genero del contenido.
     */
    public Contenido(int idDB, String titulo, String director, int duracion, String resumen, Genero genero) {
        this.idDB = idDB;
        this.titulo = titulo;
        this.director = director;
        this.duracion = duracion;
        this.resumen = resumen;
        this.genero = genero;
    }

    /**
     * Obtiene el ID del contenido en la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El ID del contenido.
     */
    public int getIdDB() {
        return idDB;
    }

    /**
     * Establece el ID del contenido en la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param idDB El nuevo ID del contenido.
     */
    public void setIdDB(int idDB) {
        this.idDB = idDB;
    }

    /**
     * Obtiene el título del contenido.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El título del contenido.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del contenido.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param titulo El nuevo título del contenido.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el resumen del contenido.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El resumen del contenido.
     */
    public String getResumen() {
        return resumen;
    }

    /**
     * Establece el resumen del contenido.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param resumen El nuevo resumen del contenido.
     */
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    /**
     * Obtiene el director del contenido.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El director del contenido.
     */
    public String getDirector() {
        return director;
    }

    /**
     * Establece el director del contenido.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param director El nuevo director del contenido.
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Obtiene la duración del contenido en segundos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return La duración en segundos.
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Establece la duración del contenido en segundos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param duracion La nueva duración en segundos.
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * Obtiene el género del contenido.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El género del contenido.
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * Establece el género del contenido.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param genero El nuevo género del contenido.
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * Compara este contenido con otro objeto para ver si son iguales.
     * Dos contenidos se consideran iguales si tienen el mismo título.
     * Se asume que el título es un identificador único.
     *
     * @author Gemini.
     * @version 1.0
     * 
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
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
     * 
     * @author Gemini.
     * @version 1.0
     * 
     * @return entero.
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(titulo, resumen, director, duracion, genero);
    }

    /**
     * Retorna los valores de un contenido en una cadena.
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
                ", duracion=" + (duracion / 60) + " min" +
                ", resumen='" + resumen + '\'' +
                ", genero=" + genero +
                '}';
    }
}
