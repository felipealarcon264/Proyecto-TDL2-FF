package Utilidades.Comparadores;

import Catalogo.Pelicula;
import java.util.Comparator;

/**
 * Implementación de Comparator para ordenar objetos Pelicula
 * por su título (ascendente, A-Z).
 *
 * @author Gemini
 * @version 1.0
 */
public class ComparadorPeliculaPorTitulo implements Comparator<Pelicula> {

    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        // Compara el título de la primera película con el de la segunda.
        // String.compareTo() realiza una comparación alfabética.
        return p1.getTitulo().compareTo(p2.getTitulo());
    }
}
