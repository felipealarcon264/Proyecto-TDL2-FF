package Control;

import java.util.ArrayList;
import java.util.List;
import Catalogo.Contenido;
import Entes.Usuario;
import Reportes.ReporteManager;

/**
 * Clase principal que representa la plataforma de streaming.
 * Gestiona los usuarios, el catalogo de contenidos y el acceso
 * al sistema de reportes.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.1
 */
public class Plataforma {

    private List<Usuario> usuarios;
    private List<Contenido> contenidos;
    private ReporteManager controladorReportes;

    /**
     * Constructor de la Plataforma.
     * Inicializa las listas de usuarios y contenidos, y el gestor de reportes.
     */
    public Plataforma() {
        this.usuarios = new ArrayList<>();
        this.contenidos = new ArrayList<>();
        this.controladorReportes = new ReporteManager();
    }

    /**
     * Valida las credenciales de un usuario para iniciar sesion.
     * @param email El email del usuario.
     * @param contrasena La contrasena del usuario.
     * @return El objeto Usuario si las credenciales son correctas, de lo contrario null.
     */
    public Usuario validarUsuario(String email, String contrasena) {
        for (Usuario usuario : this.usuarios) {
            if (usuario.iniciarSesion(email, contrasena)) {
                return usuario;
            }
        }
        return null;
    }
    /* 
    public void mostrarCatalogo() {
        System.out.println("\n--- CATALOGO DE CONTENIDO ---");
        for (Contenido contenido : contenidos) {
            System.out.println("ID: " + contenido.getIdContenido() + " | Titulo: " + contenido.getTitulo() + " (" + contenido.getClass().getSimpleName() + ")");
        }
        System.out.println("---------------------------");
    }

    
    public Contenido buscarContenidoPorId(int id) {
        for (Contenido contenido : contenidos) {
            if (contenido.getIdContenido() == id) {
                return contenido;
            }
        }
        return null;
    }
    */
    

    // --- Getters y Setters ---

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public List<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public void agregarContenido(Contenido contenido) {
        this.contenidos.add(contenido);
    }

    public ReporteManager getControladorReportes() {
        return controladorReportes;
    }

    public void setControladorReportes(ReporteManager controladorReportes) {
        this.controladorReportes = controladorReportes;
    }
}
