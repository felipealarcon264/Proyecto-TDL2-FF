package modelo.ente;

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
    private Datos_Personales datosPersonales;
    private String rol;
    private int esNuevo;

    /**
     * Constructor vacío.
     */
    public Usuario() {
    }

    /**
     * Constructor para crear una instancia de Usuario con datos iniciales.
     * 
     * @param idDB            ID propio en la base de datos.
     * @param nombreUsuario   El nombre de usuario para la plataforma.
     * @param email           El correo electrónico para el inicio de sesión.
     * @param contrasena      La clave de acceso del usuario.
     * @param datosPersonales Los datos personales asociados al usuario.
     * @param rol             Determina si un usuario es 'ADMINSITRADOR' o 'CUENTA'.
     * @param esNuevo         Indica si el usuario es nuevo o no.
     */
    public Usuario(int idDB, String nombreUsuario, String email, String contrasena, Datos_Personales datosPersonales, String rol, int esNuevo) {
        this.idDB = idDB;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.datosPersonales = datosPersonales;
        this.rol = rol;
        this.esNuevo = esNuevo;
    }

    /**
     * Obtiene el rol del usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El rol del usuario ('ADMINISTRADOR' o 'CUENTA').
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param rol El nuevo rol del usuario.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el ID del usuario en la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El ID del usuario.
     */
    public int getIdDB() {
        return idDB;
    }

    /**
     * Establece el ID del usuario en la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param idDB El nuevo ID del usuario.
     */
    public void setIdDB(int idDB) {
        this.idDB = idDB;
    }

    /**
     * Obtiene el nombre de usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El nombre de usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre de usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param nombreUsuario El nuevo nombre de usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene el email del usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param email El nuevo email del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return La contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param contrasena La nueva contraseña del usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene los datos personales asociados al usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return Un objeto {@link Datos_Personales}.
     */
    public Datos_Personales getDatosPersonales() {
        return this.datosPersonales;
    }

    /**
     * Establece los datos personales asociados al usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param datosPersonales El nuevo objeto {@link Datos_Personales}.
     */
    public void setDatosPersonales(Datos_Personales datosPersonales) {
        this.datosPersonales = datosPersonales;
    }

    public int getEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(int esNuevo) {
        this.esNuevo = esNuevo;
    }

    /**
     * Muestra la informacion cargada.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
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
        if (this == o)
            return true;
        // Usamos 'instanceof' en lugar de 'getClass()' para permitir la comparación
        // entre subclases (ej. Usuario vs Administrador)
        if (o == null || !(o instanceof Usuario))
            return false;
        Usuario usuario = (Usuario) o;
        // Comparamos por email, que es el identificador único.
        return java.util.Objects.equals(email, usuario.email);
    }

    /**
     * Devuelve un código hash para este usuario, basado en el email.
     * 
     * @author Gemini
     * @version 1.0
     * 
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(email);
    }
}
