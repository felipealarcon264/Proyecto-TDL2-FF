package DAO;


import Entes.Usuario;
public interface UsuarioDAO {
    public boolean guardar(Usuario usr);
    public boolean borrar(Usuario usr);
    public Usuario buscarPorEmailyContrasena(String email, String contrasena);
}
