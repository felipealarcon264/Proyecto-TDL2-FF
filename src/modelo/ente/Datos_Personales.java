package modelo.ente;

/**
 * Clase que representa los datos personales de una persona.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */
public class Datos_Personales {
    private int idDB;
    private String nombre;
    private String apellido;
    private int dni;

    /**
     * Constructor completo de la clase Datos_Personales
     * 
     * @param inDB     ID propio de la base de datos.
     * @param nombre   Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param dni      DNI del usuario, debe ser unico en la base de datos.
     */
    public Datos_Personales(int inDB, String nombre, String apellido, int dni) {
        this.idDB = inDB;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    /**
     * Constructor por defecto de la clase Datos_Personales
     */
    public Datos_Personales() {
    }

    /**
     * Obtiene el ID de la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return El ID de la base de datos.
     */
    public int getIdDB() {
        return idDB;
    }

    /**
     * Establece el ID de la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param idDB El nuevo ID de la base de datos.
     */
    public void setIdDB(int idDB) {
        this.idDB = idDB;
    }

    /**
     * Obtiene el nombre de la persona.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return El nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la persona.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param nombre El nuevo nombre de la persona.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido de la persona.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return El apellido de la persona.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido de la persona.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param apellido El nuevo apellido de la persona.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el DNI de la persona.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return El DNI de la persona.
     */
    public int getDni() {
        return dni;
    }

    /**
     * Establece el DNI de la persona.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param dni El nuevo DNI de la persona.
     */
    public void setDni(int dni) {
        this.dni = dni;
    }

    /**
     * Representación en cadena de los datos personales
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return Cadena con los datos personales.
     */
    @Override
    public String toString() {
        return "[" + nombre + ", apellido=" + apellido + ", dni=" + dni + "]";
    }
}
