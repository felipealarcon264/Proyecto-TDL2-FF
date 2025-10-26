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

    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        // Integer.compare es la forma estándar y segura de comparar dos enteros.
        return Integer.compare(p1.getDuracion(), p2.getDuracion());
    }
}
