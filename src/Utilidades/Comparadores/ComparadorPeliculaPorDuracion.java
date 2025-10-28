package Utilidades.Comparadores;

import Catalogo.Pelicula;
import java.util.Comparator;

/**
 * Implementación de Comparator para ordenar objetos Pelicula
 * por su duración (ascendente, de menor a mayor).
 *
 * @author Gemini
 * @version 1.0
 */
public class ComparadorPeliculaPorDuracion implements Comparator<Pelicula> {

    /**
     * Constructor por defecto.
     */
    public ComparadorPeliculaPorDuracion() {
    }

    /**
     * Compara dos objetos Pelicula basándose en su duración.
     *
     * @param p1 La primera película a comparar.
     * @param p2 La segunda película a comparar.
     * @return Un entero negativo, cero, o un entero positivo si la duración
     *         de la primera película es menor, igual o mayor que la de la segunda.
     */

    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        // Integer.compare es la forma estándar y segura de comparar dos enteros.
        return Integer.compare(p1.getDuracion(), p2.getDuracion());
    }
}
