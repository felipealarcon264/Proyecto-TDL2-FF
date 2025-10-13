package Entes;

/**
 * Se encargara de la administracion de las cuentas y contenidos de la plataforma
 */
public class Administrador extends Usuario {

    /**
     * Constructor heredado de Usuario.
     * @param email
     * @param contrasena
     */
    public Administrador(String email, String contrasena) {
        super(email, contrasena);
    }
    public Administrador(){}

}