package comparadores;

import java.util.Comparator;

import modelo.catalogo.Pelicula;

/**
 * Implementación de Comparator para ordenar objetos Pelicula
 * por su título (ascendente, A-Z).
 *
 * @author Gemini
 * @version 1.0
 */
public class ComparadorPeliculaPorTitulo implements Comparator<Pelicula> {
    /**
     * Constructor por defecto.
     */
    public ComparadorPeliculaPorTitulo() {
    }

    /**
     * Compara dos objetos Pelicula basándose en su título.
     *
     * @param p1 La primera película a comparar.
     * @param p2 La segunda película a comparar.
     * @return Un entero negativo, cero, o un entero positivo si el título
     *         de la primera película es lexicográficamente menor, igual o
     *         mayor que el de la segunda.
     */

    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        return p1.getTitulo().compareTo(p2.getTitulo());
    }
}
