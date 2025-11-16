package excepciones;

/**
 * Excepción que se lanza al intentar registrar un DNI
 * que ya existe en la base de datos.
 */
public class DniYaRegistradosException extends Exception {

    public DniYaRegistradosException(String mensaje) {
        super(mensaje);
    }
}