package ente;

/**
 * Representa la cuenta de un cliente final de la plataforma.
 * Esta clase hereda de Usuario y gestiona el plan, los perfiles y
 * las estadísticas asociadas a la cuenta.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Cuenta extends Usuario {

    /**
     * Constructor vacío.
     * Inicializa la lista de perfiles y establece un país por defecto.
     */
    public Cuenta() {
        super();
    }

    /**
     * Constructor para crear una instancia de Cuenta con datos iniciales.
     * 
     * @param idDB            ID propio en la base de datos.
     * @param nombreUsuario   El nombre de usuario para la plataforma.
     * @param email           El correo electrónico para el inicio de sesión.
     * @param contrasena      La clave de acceso de la cuenta.
     * @param datosPersonales Los datos personales asociados a la cuenta.
     * @param rol             Determina si es administrador o cuenta.
     */
    public Cuenta(int idDB, String nombreUsuario, String email, String contrasena, Datos_Personales datosPersonales,
            String rol) {
        super(idDB, nombreUsuario, email, contrasena, datosPersonales, rol);
    }

    /**
     * Devuelve una representación en cadena del objeto cuenta.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
