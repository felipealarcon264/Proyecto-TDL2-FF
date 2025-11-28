//Verificacion JavaDoc -> Realizada.
package comparadores;

import java.util.Comparator;
import modelo.catalogo.Pelicula;

/**
 * Implementación de Comparator para ordenar objetos Pelicula
 * por su género (en orden alfabético).
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ComparadorPeliculaPorGenero implements Comparator<Pelicula> {
    /**
     * Constructor por defecto.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public ComparadorPeliculaPorGenero() {
    }

    /**
     * Compara dos objetos Pelicula basándose en su género.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     *
     * @param p1 La primera película a comparar.
     * @param p2 La segunda película a comparar.
     * @return Un entero negativo, cero, o un entero positivo si el género
     *         de la primera película es lexicográficamente menor, igual o
     *         mayor que el de la segunda.
     */

    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        return p1.getGenero().compareTo(p2.getGenero());
    }
}
