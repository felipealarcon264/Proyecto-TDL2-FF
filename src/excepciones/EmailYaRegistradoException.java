package excepciones;

/**
 * Excepción que se lanza al intentar registrar un Email
 * que ya existe en la base de datos.
 */
public class EmailYaRegistradoException extends Exception {

    public EmailYaRegistradoException(String mensaje) {
        super(mensaje);
    }
}