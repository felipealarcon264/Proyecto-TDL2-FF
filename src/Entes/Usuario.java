package Entes;

/**
 * Clase abstracta que representa la base para cualquier tipo de usuario
 * en la plataforma. Define los atributos y comportamientos comunes.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.2
 */
public abstract class Usuario {

    private int idDB;
    private String nombreUsuario;
    private String email;
    private String contrasena;
    // No se necesita en la segunda entrega.private boolean activo;
    private Datos_Personales datosPersonales;
    private String rol;

    /**
     * Constructor vacío.
     */
    public Usuario() {
    }

    /**
     * Constructor para crear una instancia de Usuario con datos iniciales.
     * 
     * @param nombreUsuario   El nombre de usuario para la plataforma.
     * @param email           El correo electrónico para el inicio de sesión.
     * @param contrasena      La clave de acceso del usuario.
     * @param datosPersonales Los datos personales asociados al usuario.
     */
    public Usuario(int idDB, String nombreUsuario, String email, String contrasena, Datos_Personales datosPersonales,
            String rol) {
        this.idDB = idDB;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        // No se necesita en la segunda entrega.this.activo = false; // Un usuario
        // siempre empieza con la sesión cerrada.
        this.datosPersonales = datosPersonales;
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // --- Getters y Setters ---
    public int getIdDB() {
        return idDB;
    }

    public void setIdDB(int idDB) {
        this.idDB = idDB;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

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

    // No se necesita en la segunda entrega.public boolean getActivo() {
    // No se necesita en la segunda entrega. return activo;
    // No se necesita en la segunda entrega.}

    // No se necesita en la segunda entrega.public void setActivo(boolean activo) {
    // No se necesita en la segunda entrega. this.activo = activo;
    // No se necesita en la segunda entrega.}
    public Datos_Personales getDatosPersonales() {
        return this.datosPersonales;
    }

    public void setDatosPersonales(Datos_Personales datosPersonales) {
        this.datosPersonales = datosPersonales;
    }

    /**
     * Muestra la informacion cargada.
     * HECHO TOTALMENTE CON IA
     */
    @Override
    public String toString() {
        // Usa StringBuilder para construir la cadena eficientemente
        StringBuilder sb = new StringBuilder();
        sb.append("Usuario{\n"); // Nombre de la clase y llave de apertura
        sb.append("  Rol='").append(rol).append("',\n"); // Muestra el rol
        sb.append("  Nombre Usuario='").append(nombreUsuario).append("',\n"); // Muestra el nombre de usuario
        sb.append("  Email='").append(email).append("',\n"); // Muestra el email
        // NO incluimos la contraseña por seguridad
        // sb.append(" Contrasena='********',\n"); // Podrías mostrarla oculta si fuera
        // necesario

        // Incluye la representación toString() de Datos_Personales, manejando si es
        // null
        sb.append("  Datos Personales= ").append(datosPersonales != null ? datosPersonales.toString() : "N/A")
                .append("\n");

        sb.append("}"); // Llave de cierre
        return sb.toString(); // Devuelve la cadena construida
    }

    /**
     * Compara este usuario con otro objeto para ver si son iguales.
     * Dos usuarios se consideran iguales si tienen el mismo email.
     * Se asume que el email es un identificador único en el sistema.
     * 
     * @author Gemini.
     * @version 1.0
     *
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Usamos 'instanceof' en lugar de 'getClass()' para permitir la comparación entre subclases (ej. Usuario vs Administrador)
        if (o == null || !(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        // Comparamos por email, que es el identificador único.
        return java.util.Objects.equals(email, usuario.email);
    }

    /**
     * Devuelve un código hash para este usuario, basado en el email.
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(email);
    }
}
