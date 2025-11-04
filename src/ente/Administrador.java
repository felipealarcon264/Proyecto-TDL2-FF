package ente;

/**
 * Representa a un usuario con privilegios de administrador en la plataforma.
 * Esta clase hereda de Usuario y añade funcionalidades para la gestión
 * de contenidos y usuarios.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Administrador extends Usuario {

    /**
     * Constructor vacío.
     */
    public Administrador() {
        super();
    }

    /**
     * Constructor para crear una instancia de Administrador con datos iniciales.
     * 
     * @param idDB            ID que propio en la base de datos.
     * @param nombreUsuario   El nombre de usuario para la plataforma.
     * @param email           El correo electrónico para el inicio de sesión.
     * @param contrasena      La clave de acceso del administrador.
     * @param datosPersonales Los datos personales asociados al administrador.
     * @param rol             Determina si es administrador o cuenta.
     */
    public Administrador(int idDB, String nombreUsuario, String email, String contrasena,
            Datos_Personales datosPersonales, String rol) {
        super(idDB, nombreUsuario, email, contrasena, datosPersonales, rol);
    }

    @Override
    /**
     * Devuelve una representación en cadena del objeto Administrador.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     */
    public String toString() {
        return super.toString();
    }
}
