package dao.interfaces;

import java.util.List;

import modelo.ente.Usuario;

/**
 * Interfaz DAO para la gestión de usuarios.
 * Define los métodos para guardar, borrar y buscar usuarios en la base de
 * datos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */

public interface UsuarioDAO {
    /**
     * Guarda un usuario en la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param usr Dato a guardar.
     * @return Exito.
     */

    public boolean guardar(Usuario usr);

    /**
     * Borra un usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param usr Dato a borrar,
     * @return Exito.
     */
    public boolean borrar(Usuario usr);

    /**
     * Busca un usuario con su email y contraseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param email      Email del dato a buscar.
     * @param contrasena Contraseña del dato a buscar.
     * @return Usuario encontrado.
     */
    public Usuario buscarPorEmailyContrasena(String email, String contrasena);

    /**
     * Busca un usuario con su email.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param email Email del dato a buscar.
     * @return Usuario encontrado.
     */
    public Usuario buscarPorEmail(String email);

    /**
     * Lista todos los Usuarios guardados en la DB y los muestra en pantalla.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public void ListarTodosLosUsuariosEnPantalla();

    /**
     * Busca un usuario por su ID.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param id Id del dato a buscar.
     * @return Usuario encontrado.
     */
    public Usuario buscarPorId(int id);

    /**
     * Actualiza un usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param usr Dato a actualizar.
     * @return Exito.
     */
    public boolean actualizarEsNuevo(Usuario usr);

    /**
     * Devuelve la lista con todos los datos de la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return Lista con los datos.
     */
    public List<Usuario> devolverListaUsuarios();
}
