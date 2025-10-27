package Catalogo;

import Enums.Genero;

/**
 * Representa una Película dentro de la plataforma.
 * Es una clase concreta que hereda todos los atributos y métodos
 * de la clase abstracta Contenido. Su propósito es permitir la instanciación
 * de contenidos que son específicamente películas.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Pelicula extends Contenido {

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
     * @param idDB Identificador único de la película.
     * @param titulo      Título de la película.
     * @param director    Director de la película.
     * @param duracion    Duración en minutos.
     */
    public Pelicula(int idDB, String titulo, String director, int duracion, String resumen, Genero genero) {
        super(idDB, titulo, director, duracion, resumen, genero);
    }

    /**
     * Muestra los valores de una pelicula.
     * 
     * 
     * @return String con todos los valores.
     */
    @Override
    public String toString() {
        return "Pelicula: " + super.toString();
    }
}
