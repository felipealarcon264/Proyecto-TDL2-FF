package excepciones;

/**
 * Excepción que se lanza al intentar registrar un DNI
 * que ya existe en la base de datos.
 */
public class DniYaRegistradosException extends Exception {

    /**
     * Constructor de la excepción DniYaRegistradosException.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param mensaje El mensaje de error personalizado.
     */
    public DniYaRegistradosException(String mensaje) {
        super(mensaje);
    }
}