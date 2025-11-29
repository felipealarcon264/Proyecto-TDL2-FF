
//Verificacion JavaDoc -> Realizada.
package excepciones;
/**
 * Excepción que se lanza cuando ocurre un error crítico durante la
 * inicialización de datos de la aplicación, como la imposibilidad de leer
 * un archivo CSV esencial.
 *
 * Al ser una RuntimeException, no es obligatorio capturarla, pero puede
 * usarse para detener la ejecución de la aplicación de forma controlada.
 *
 * @author Gemini
 */
public class ErrorDeInicializacionException extends RuntimeException {
    /**
     * Constructor de la excepción ErrorDeInicializacionException.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param message El mensaje de error personalizado.
     * @param cause   La causa del error, si es necesario.
     */
    public ErrorDeInicializacionException(String message, Throwable cause) {
        super(message, cause);
    }
}