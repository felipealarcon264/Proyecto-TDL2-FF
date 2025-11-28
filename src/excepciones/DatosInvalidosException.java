//Verificacion JavaDoc -> Realizada.
package excepciones;

/**
 * Excepción personalizada para datos inválidos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class DatosInvalidosException extends Exception {
    /**
     * Constructor de la excepción DatosInvalidosException.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param mensaje El mensaje de error personalizado.
     */
    public DatosInvalidosException(String mensaje) {
        super(mensaje); //msj de la clase padre
    }
}
