//Verificacion JavaDoc -> Realizada.
package excepciones;

/**
 * Excepción personalizada para campos vacíos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class CampoVacio extends Exception {
    /**
     * Constructor de la excepción CampoVacio.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param mensaje El mensaje de error personalizado.
     */
    public CampoVacio(String mensaje) {
        super(mensaje); // msj de la clase padre
    }
}