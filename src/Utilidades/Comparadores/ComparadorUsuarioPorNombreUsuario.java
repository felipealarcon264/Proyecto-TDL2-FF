package Utilidades.Comparadores;

import Entes.Usuario;
import java.util.Comparator;

/**
 * Implementación de Comparator para ordenar objetos Usuario
 * por su nombre de usuario (ascendente, A-Z).
 * 
 * @author Gemini.
 * @version 1.0
 * 
 */
public class ComparadorUsuarioPorNombreUsuario implements Comparator<Usuario> {

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