package Utilidades.Comparadores;

import Entes.Usuario;
import java.util.Comparator;

/**
 * Implementación de Comparator para ordenar objetos Usuario
 * por su dirección de Email (ascendente, a-z).
 * Recordar que todos los email se guardan en minusculas.
 * 
 * @author Gemini.
 * @version 1.0
 * 
 */
public class ComparadorUsuarioPorEmail implements Comparator<Usuario> {

    @Override
    public int compare(Usuario u1, Usuario u2) {
        // Compara la dirección de email del primer objeto con el segundo.
        return u1.getEmail().compareTo(u2.getEmail());
    }
}