package comparadores;

import java.util.Comparator;

import ente.Usuario;

/**
 * Implementación de Comparator para ordenar objetos Usuario
 * por su nombre de usuario (ascendente, A-Z).
 * 
 * @author Gemini.
 * @version 1.0
 * 
 */
public class ComparadorUsuarioPorNombreUsuario implements Comparator<Usuario> {
    /**
     * Constructor por defecto.
     */
    public ComparadorUsuarioPorNombreUsuario() {
    }

    /**
     * Compara dos objetos Usuario basándose en su nombre de usuario.
     *
     * @param u1 El primer objeto Usuario a comparar.
     * @param u2 El segundo objeto Usuario a comparar.
     * @return Un entero negativo, cero, o un entero positivo si el nombre de
     *         usuario del primer objeto es lexicográficamente menor, igual o
     *         mayor que el del segundo.
     */

    @Override
    public int compare(Usuario u1, Usuario u2) {
        // Compara el nombre de usuario del primer objeto con el segundo.
        // String.compareTo() devuelve:
        // - un valor negativo si u1 es menor que u2 (debe ir antes)
        // - 0 si son iguales
        // - un valor positivo si u1 es mayor que u2 (debe ir después)
        return u1.getNombreUsuario().compareTo(u2.getNombreUsuario());
    }
}