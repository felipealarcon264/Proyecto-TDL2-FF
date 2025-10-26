package DAO;


import Entes.Usuario;
import java.util.List;

/**
 * Interfaz DAO para la gestión de usuarios.
 * Define los métodos para guardar, borrar y buscar usuarios en la base de datos.
 * 
 * @author Grupo 4 - Taller de lenguajes II
 * @version 1.0
 */

public interface UsuarioDAO {
    public boolean guardar(Usuario usr);
    public boolean borrar(Usuario usr);
    public Usuario buscarPorEmailyContrasena(String email, String contrasena);
    public Usuario buscarPorEmail(String email);
    public void ListarTodosLosUsuariosEnPantalla();
    public List<Usuario> devolverListaUsuarios();
}
