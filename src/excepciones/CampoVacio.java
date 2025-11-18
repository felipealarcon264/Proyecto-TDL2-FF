package excepciones;


public class CampoVacio extends Exception {
    public CampoVacio (String mensaje) {
        super(mensaje); //msj de la clase padre
    }
}