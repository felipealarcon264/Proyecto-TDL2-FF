//Verificacion JavaDoc -> Realizada.
package comparadores;

import java.util.Comparator;
import modelo.ente.Usuario;

/**
 * Implementación de Comparator para ordenar objetos Usuario
 * por su nombre de usuario (ascendente, A-Z).
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */
public class ComparadorUsuarioPorNombreUsuario implements Comparator<Usuario> {
    /**
     * Constructor por defecto.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public ComparadorUsuarioPorNombreUsuario() {
    }

    /**
     * Compara dos objetos Usuario basándose en su nombre de usuario.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param u1 El primer objeto Usuario a comparar.
     * @param u2 El segundo objeto Usuario a comparar.
     * @return Un entero negativo, cero, o un entero positivo si el nombre de
     *         usuario del primer objeto es lexicográficamente menor, igual o
     *         mayor que el del segundo.
     */
    @Override
    public int compare(Usuario u1, Usuario u2) {
        return u1.getNombreUsuario().compareTo(u2.getNombreUsuario());
    }
}