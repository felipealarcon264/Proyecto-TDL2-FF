//Verificacion JavaDoc -> Realizada.
package excepciones;

/**
 * Excepción personalizada para manejar errores específicos de la comunicación
 * con la API de OMDb.
 * Su propósito es encapsular errores técnicos de bajo nivel (como IOException,
 * InterruptedException o errores de parseo JSON) en una única excepción de
 * negocio
 * que los controladores puedan atrapar y gestionar fácilmente para mostrar
 * mensajes
 * claros al usuario.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ErrorApiOMDbException extends Exception {
    /**
     * Constructor de la excepción ErrorApiOMDbException.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     *
     * @param mensaje El mensaje de error personalizado.
     * @param causa   La causa del error, si es necesario.
     */
    public ErrorApiOMDbException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    /**
     * Constructor de la excepción ErrorApiOMDbException.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     *
     * @param mensaje El mensaje de error personalizado.
     */
    public ErrorApiOMDbException(String mensaje) {
        super(mensaje);
    }
}
