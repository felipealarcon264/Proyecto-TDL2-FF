package Control;

import java.util.List;

import Entes.Usuario;

/**
 * La clase plataforma se encargara de gestionar a los usuarios y saber
 * identificarlos entre cuentas y administradores
 */
public class Plataform {
    private List<Usuario> usuarios;
    // private List<Contenido> contenidos;
    // private List<Reporte> reportes;

    /**
     * Constructor nulo
     */
    public Plataform() {
    }

    /**
     * Getters ands setters de cada variable de la clase Plataforma
     */

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
