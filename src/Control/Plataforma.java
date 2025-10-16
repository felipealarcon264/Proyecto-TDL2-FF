package Control;

import java.util.ArrayList;
import java.util.List;
import Entes.Usuario;

/**
 * Representa la plataforma principal de streaming.
 * Esta clase actúa como el punto de entrada y controlador central, gestionando
 * las listas de usuarios y contenidos.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Plataforma {

    private List<Usuario> usuarios;
    // private List<Contenido> contenidos;
    // private ReporteManager controladorReportes;

    /**
     * Constructor para inicializar la plataforma.
     * Crea las listas necesarias para su funcionamiento.
     */
    public Plataforma() {
        this.usuarios = new ArrayList<>();
        // this.contenidos = new ArrayList<>();
    }

    // --- Métodos de gestión ---

    /**
     * Registra un nuevo usuario en la plataforma.
     * @param usuario El usuario a agregar.
     */
    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        System.out.println("Usuario añadido a la plataforma.");
    }

    /**
     * Elimina un usuario existente de la plataforma.
     * @param usuario El usuario a eliminar.
     */
    public void eliminarUsuario(Usuario usuario) {
        if (this.usuarios.remove(usuario)) {
            System.out.println("Usuario eliminado de la plataforma.");
        } else {
            System.out.println("El usuario no se encontró.");
        }
    }


    // --- Getters y Setters ---

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    // public List<Contenido> getContenidos() {
    //     return contenidos;
    // }

    // public void setContenidos(List<Contenido> contenidos) {
    //     this.contenidos = contenidos;
    // }

    // public ReporteManager getControladorReportes() {
    //     return controladorReportes;
    // }

    // public void setControladorReportes(ReporteManager controladorReportes) {
    //     this.controladorReportes = controladorReportes;
    // }
}
