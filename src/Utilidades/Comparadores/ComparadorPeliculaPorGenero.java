package Utilidades.Comparadores;

import Catalogo.Pelicula;
import java.util.Comparator;

/**
 * Implementación de Comparator para ordenar objetos Pelicula
 * por su género (en orden alfabético).
 *
 * @author Gemini
 * @version 1.0
 */
public class ComparadorPeliculaPorGenero implements Comparator<Pelicula> {

    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        // Compara la representación de texto de los enums de género.
        // .name() devuelve el nombre del enum como un String ("ACCION", "ANIME", etc.).
        return p1.getGenero().name().compareTo(p2.getGenero().name());
    }
}
