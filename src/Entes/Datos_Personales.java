package Entes;

/**
 * Clase que representa los datos personales de una persona.
 */
public class Datos_Personales {
    private String nombre;
    private String apellido;
    private int dni;

    /**
     * Constructor completo de la clase Datos_Personales
     * 
     * @param nombre
     * @param apellido
     * @param dni
     */
    public Datos_Personales(String nombre, String apellido, int dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    /**
     * Constructor por defecto de la clase Datos_Personales
     */
    public Datos_Personales() {
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

    @Override  
    public String toString() {
        return "Datos_Personales [nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + "]";
    }
}
