package comparadores;

import java.util.Comparator;
import modelo.catalogo.Pelicula;

/**
 * Implementación de Comparator para ordenar objetos Pelicula
 * por su rating promedio (descendente, de mayor a menor).
 *
 * @author Gemini
 * @version 1.0
 */
public class ComparadorPeliculaPorRating implements Comparator<Pelicula> {

    /**
     * Constructor por defecto.
     */
    public ComparadorPeliculaPorRating() {
    }

    /**
     * Compara dos objetos Pelicula basándose en su rating promedio.
     *
     * @param p1 La primera película a comparar.
     * @param p2 La segunda película a comparar.
     * @return Un entero positivo, cero, o un entero negativo si el rating de la primera película es menor, igual o mayor que la de la segunda. Se invierten los parámetros para lograr un orden descendente.
     */
    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        // Comparamos p2 con p1 para obtener un orden descendente (de mayor a menor rating).
        return Double.compare(p2.getRatingPromedio(), p1.getRatingPromedio());
    }
}