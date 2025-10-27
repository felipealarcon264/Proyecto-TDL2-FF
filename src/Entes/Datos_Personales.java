package Entes;

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
     * @param inDB
     * @param nombre
     * @param apellido
     * @param dni
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
     * Getters y Setters
     * 
     * @return
     */

    public int getIdDB() {
        return idDB;
    }

    public void setIdDB(int idDB) {
        this.idDB = idDB;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    /**
     * Representación en cadena de los datos personales
     */
    @Override
    public String toString() {
        return "[" + nombre + ", apellido=" + apellido + ", dni=" + dni + "]";
    }
}
