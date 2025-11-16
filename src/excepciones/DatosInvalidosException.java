package excepciones;


public class DatosInvalidosException extends Exception {
    public DatosInvalidosException(String mensaje) {
        super(mensaje); //msj de la clase padre
    }
}
