package Entes;

/**
 * Clase abstracta que representa la base para cualquier tipo de usuario
 * en la plataforma. Define los atributos y comportamientos comunes.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.2
 */
public abstract class Usuario {

    private String email;
    private String contrasena;
    private boolean activo;

    /**
     * Constructor vacío.
     */
    public Usuario() {
    }

    /**
     * Constructor para crear una instancia de Usuario con datos iniciales.
     * @param email El correo electrónico para el inicio de sesión.
     * @param contrasena La clave de acceso del usuario.
     */
    public Usuario(String email, String contrasena) {
        this.email = email;
        this.contrasena = contrasena;
        this.activo = false; // Un usuario siempre empieza con la sesión cerrada.
    }

    /**
     * Valida las credenciales y, si son correctas, marca la sesión como activa.
     * @param email El email a verificar.
     * @param contrasena La contraseña a verificar.
     * @return true si las credenciales son correctas, de lo contrario false.
     */
    public boolean iniciarSesion(String email, String contrasena) {
        if (this.email.equals(email) && this.contrasena.equals(contrasena)) {
            this.activo = true;
            return true;
        }
        return false;
    }

    /**
     * Cierra la sesión del usuario estableciendo su estado como inactivo.
     */
    public void cerrarSesion() {
        this.activo = false;
    }

    // --- Getters y Setters ---

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}

