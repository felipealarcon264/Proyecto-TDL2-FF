package Cuentass;

import java.util.List;
import Entes.Usuario;

/**
 * Representa aun cliente en especifico con su historia y preferencias.
 */
public class Cuenta extends Usuario {
    //private Plan plan;
    /**
     * Relacion con perfiles
     */
    private List<Perfil> perfiles;

    /**
     * Constructor que se acopa a el de la clase padre Usuario
     */
    public Cuenta(String email, String contraseña) {
        super(email, contraseña);
        //Falta instanciar perfiles y plan.
    }

    /**
     * Getters and setters de todas las variables de Cuenta
     */
    public List<Perfil> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<Perfil> perfiles) {
        this.perfiles = perfiles;
        
    }

    // Métodos
    


    
}
