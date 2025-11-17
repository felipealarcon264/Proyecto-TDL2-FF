package modelo.catalogo;


/**
 * Representa una Película dentro de la plataforma.
 * Es una clase concreta que hereda todos los atributos y métodos
 * de la clase abstracta Contenido. Su propósito es permitir la instanciación
 * de contenidos que son específicamente películas.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */
public class Pelicula extends Contenido {

    // --- Atributos nuevos para el Entregable 3 ---
    private double ratingPromedio;
    private int anio;
    private String poster;

    /**
     * Constructor vacío.
     * Llama al constructor vacío de la clase padre (Contenido).
     */
    public Pelicula() {
        super();
    }

    /**
     * Constructor para crear una Película con datos esenciales.
     * Llama al constructor correspondiente de la clase padre para inicializar los
     * datos.
     *
     * @param idDB     Identificador único de la película.
     * @param titulo   Título de la película.
     * @param director Director de la película.
     * @param duracion Duración en segundos.
     * @param resumen  Resumen de la pelicula.
     * @param genero   Genero de la pelicula.
     * @param ratingPromedio Calificación promedio de la película.
     * @param anio     Año de lanzamiento.
     * @param poster   URL de la imagen del póster.
     */
    public Pelicula(int idDB, String titulo, String director, int duracion, String resumen, String genero,
            double ratingPromedio, int anio, String poster) {
        super(idDB, titulo, director, duracion, resumen, genero);
        this.ratingPromedio = ratingPromedio;
        this.anio = anio;
        this.poster = poster;
    }

    /**
     * Muestra los valores de una pelicula.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return String con todos los valores.
     */
    @Override
    public String toString() {
        return "Pelicula: " + super.toString() +
                ", Rating Promedio=" + ratingPromedio +
                ", Año=" + anio +
                ", Poster='" + poster + '\'';
    }

    // --- Getters y Setters para los nuevos atributos ---

    public double getRatingPromedio() {
        return ratingPromedio;
    }

    public void setRatingPromedio(double ratingPromedio) {
        this.ratingPromedio = ratingPromedio;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
