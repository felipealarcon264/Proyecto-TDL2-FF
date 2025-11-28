//Verificacion JavaDoc -> Realizada.
package excepciones;

/**
 * Excepción que se lanza al intentar registrar un Email
 * que ya existe en la base de datos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class EmailYaRegistradoException extends Exception {

    /**
     * Constructor de la excepción EmailYaRegistradoException.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param mensaje Mensaje de la excepción.
     */
    public EmailYaRegistradoException(String mensaje) {
        super(mensaje);
    }
}