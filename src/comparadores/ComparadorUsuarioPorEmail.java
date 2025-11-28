//Verificacion JavaDoc -> Realizada.
package comparadores;

import java.util.Comparator;
import modelo.ente.Usuario;

/**
 * Implementación de Comparator para ordenar objetos Usuario
 * por su dirección de Email (ascendente, a-z).
 * Recordar que todos los email se guardan en minusculas.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */
public class ComparadorUsuarioPorEmail implements Comparator<Usuario> {
    /**
     * Constructor por defecto.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public ComparadorUsuarioPorEmail() {
    }

    /**
     * Compara dos objetos Usuario basándose en su dirección de Email.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param u1 El primer objeto Usuario a comparar.
     * @param u2 El segundo objeto Usuario a comparar.
     * @return Un entero negativo, cero, o un entero positivo si el email
     *         del primer objeto es lexicográficamente menor, igual o
     *         mayor que el del segundo.
     */

    @Override
    public int compare(Usuario u1, Usuario u2) {
        // Compara la dirección de email del primer objeto con el segundo.
        return u1.getEmail().compareTo(u2.getEmail());
    }
}