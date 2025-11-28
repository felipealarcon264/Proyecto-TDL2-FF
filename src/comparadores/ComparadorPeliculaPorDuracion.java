//Verificacion JavaDoc -> Realizada.
package comparadores;

import java.util.Comparator;
import modelo.catalogo.Pelicula;

/**
 * Implementación de Comparator para ordenar objetos Pelicula
 * por su duración (ascendente, de menor a mayor).
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ComparadorPeliculaPorDuracion implements Comparator<Pelicula> {

    /**
     * Constructor por defecto.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public ComparadorPeliculaPorDuracion() {
    }

    /**
     * Compara dos objetos Pelicula basándose en su duración.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     *
     * @param p1 La primera película a comparar.
     * @param p2 La segunda película a comparar.
     * @return Un entero negativo, cero, o un entero positivo si la duración
     *         de la primera película es menor, igual o mayor que la de la segunda.
     */
    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        // "Integer. compare" es la forma estándar y segura de comparar dos enteros.
        return Integer.compare(p1.getDuracion(), p2.getDuracion());
    }
}
