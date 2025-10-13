
package Cuentass;


import java.util.List;
/**
 * Una cuenta puede tener multiple perfiles los cuales son independientes uno del otro.
 * Aclaramos que solo hicimos getters y setters de variables conocidas
 */
public class Perfil {
    private String nombre;
    private String idiomaPreferido;
    private List<String> generosPreferidos;
    
    /**
     * Constructor vacio
     */
    public Perfil(){}
    /**
     * Constructor con todas las variables
     * @param nombre
     * @param idiomaPreferido
     * @param generosPreferidos
     */
    public Perfil(String nombre, String idiomaPreferido, List<String> generosPreferidos) {
        this.nombre = nombre;
        this.idiomaPreferido = idiomaPreferido;
        this.generosPreferidos = generosPreferidos;
    }
    
    

    // Métodos
    
    // Getters y Setters
    



    /**
     * Getters and Setters de todas las variables.
     */
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getIdiomaPreferido() {
        return idiomaPreferido;
    }
    public void setIdiomaPreferido(String idiomaPreferido) {
        this.idiomaPreferido = idiomaPreferido;
    }
    public List<String> getGenerosPreferidos() {
        return generosPreferidos;
    }
    public void setGenerosPreferidos(List<String> generosPreferidos) {
        this.generosPreferidos = generosPreferidos;
    }
    
    
    
}