package DAO;

import java.util.ArrayList;
import java.util.List;

import DataBase.ConexionDB;
import Entes.Administrador;
import Entes.Cuenta;
import Entes.Datos_Personales;
import Entes.Usuario;

/**
 * Implementacion de la interfaz UsuarioDAO
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */

public class UsuarioDAOImpl implements UsuarioDAO {
    /**
     * Constructor por defecto.
     */
    public UsuarioDAOImpl() {
    }

    /**
     * Guarda un usuario nuevo a la base de datos.
     * Primero carga sus datos personales a la base de datos y luego el usuario
     * completo.
     * Se tomaron todas las precauciones en caso de errores.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param usr El usuario a guardar
     * @return true si se logro guardar y false en caso contrario.
     */
    @Override
    public boolean guardar(Usuario usr) {
        if (usr == null) {
            System.out.println("❌ El usuario es nulo. No se puede guardar.");
            return false;
        }
        // Primero guardo Datos_Personales a la base de datos.
        Datos_PersonalesDAOImpl DPdao = new Datos_PersonalesDAOImpl();
        int ID_DatosPersonales = DPdao.guardar(usr.getDatosPersonales());
        if (ID_DatosPersonales > 0) {
            String sql = "INSERT INTO USUARIO (NOMBRE_USUARIO,EMAIL, CONTRASENA, ID_DATOS_PERSONALES, ROL) VALUES (?, ?, ?, ?,?)";
            try (java.sql.Connection conn = ConexionDB.conectar();
                    java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, usr.getNombreUsuario());
                pstmt.setString(2, usr.getEmail());
                pstmt.setString(3, usr.getContrasena());
                pstmt.setInt(4, ID_DatosPersonales);
                pstmt.setString(5, usr.getRol());
                if (pstmt.executeUpdate() > 0) {
                    System.out.println("✅ Usuario guardado exitosamente.");
                    return true;
                } else {
                    System.out.println("❌ ¡Error! No se pudo guardar el usuario.");
                    return false;
                }
            } catch (java.sql.SQLException e) {
                System.out.println("❌ Error al guardar el usuario: " + e.getMessage());
                return false;
            }

        } else {
            System.out.println("❌ No se pudo cargar usuario. Error al guardar los datos personales.");
            return false;
        }
    }

    /**
     * Borra un usuario de la base de datos
     * Primero borra los datos personales y luego al usuario.
     * Usuario y datos personales estan ligados fuertemente, si elimino uno elimino
     * a los dos
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param usr usuario a borrar.
     * @return true si se borro correctamente, false en caso contrario.
     * 
     */
    @Override
    public boolean borrar(Entes.Usuario usr) {
        if (usr == null || usr.getIdDB() <= 0) {
            System.out.println("❌ El usuario es nulo o inválido. No se puede borrar.");
            return false;
        }
        Datos_PersonalesDAOImpl Datos_PersonalesDAOImpl = new Datos_PersonalesDAOImpl();
        if (!Datos_PersonalesDAOImpl.borrar(usr.getDatosPersonales())) {
            System.out.println(
                    "❌ Error al borrar los datos personales. No se pudo continuar con el borrado del usuario.");
            return false;
        } // Si logra borrar datos personales es seguro que hay un usuario a borrar.
        String sql = "DELETE FROM USUARIO WHERE ID = ?";
        try (java.sql.Connection conn = ConexionDB.conectar();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usr.getIdDB());
            if (pstmt.executeUpdate() > 0) {
                System.out.println("✅ Usuario borrado correctamente.");
                return true;
            } else {
                System.out.println("⚠️ No se encontraron usuarios con el ID proporcionado.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("❌Error al borrar el usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un usuario validando su ingreso y lo retorna.
     * Es inteligente directamente devuelve un administrador o una cuenta!
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.3
     * 
     * @param email Email el usuario a buscar.
     * @param contrasena Constraseña del usuario a busca.
     * @return Usuario si lo encuentra, null en caso contrario.
     */
    @Override
    public Entes.Usuario buscarPorEmailyContrasena(String email, String contrasena) {
        String sql = "SELECT * FROM USUARIO WHERE EMAIL = ? AND CONTRASENA = ?";
        try (java.sql.Connection conn = ConexionDB.conectar();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, contrasena);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                String nombreUsuario = rs.getString("NOMBRE_USUARIO");
                String rol = rs.getString("ROL");
                int idDB = rs.getInt("ID");
                Datos_PersonalesDAOImpl dpImpl = new Datos_PersonalesDAOImpl(); // Para recuperar los datos personales
                Datos_Personales dp = dpImpl.buscarPorID(rs.getInt("ID_DATOS_PERSONALES"));
                if (rol.equals("ADMINISTRADOR")) {
                    System.out.println("ℹ️ Administrador encontrado [" + nombreUsuario + "]");
                    return new Entes.Administrador(idDB, nombreUsuario, email, contrasena, dp, rol);
                } else if (rol.equals("CUENTA")) {
                    System.out.println("ℹ️ Cuenta encontrada [" + nombreUsuario + "]");
                    return new Entes.Cuenta(idDB, nombreUsuario, email, contrasena, dp, rol);
                } else
                    return null;
            }
            System.out.println("❌ Usuario no encontrado.");
            return null;
        } catch (Exception e) {
            System.out.println("❌ Error al buscar el usuario: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca un usuario por su email, util para que los administradores borren.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param email Email del usuario a buscar
     * @return El usuario encontrado, caso contrario retorna null.
     */
    @Override
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM USUARIO WHERE EMAIL = ?";
        try (java.sql.Connection conn = ConexionDB.conectar();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                int idDB = rs.getInt("ID");
                String nombreUsuario = rs.getString("NOMBRE_USUARIO");
                String contrasena = rs.getString("CONTRASENA");
                String rol = rs.getString("ROL");
                Datos_PersonalesDAOImpl dpImpl = new Datos_PersonalesDAOImpl();
                Datos_Personales dp = dpImpl.buscarPorID(rs.getInt("ID_DATOS_PERSONALES"));
                if (rol.equals("ADMINISTRADOR")) {
                    System.out.println("ℹ️ Administrador encontrado [" + nombreUsuario + "]");
                    return new Entes.Administrador(idDB, nombreUsuario, email, contrasena, dp, rol);
                } else if (rol.equals("CUENTA")) {
                    System.out.println("ℹ️ Cuenta encontrada [" + nombreUsuario + "]");
                    return new Entes.Cuenta(idDB, nombreUsuario, email, contrasena, dp, rol);
                } else
                    return null;
            }
            System.out.println("❌ Usuario no encontrado.");
            return null;
        } catch (Exception e) {
            System.out.println("❌ Error al buscar el usuario: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los Usuarios guardados en la DB y los muestra en pantalla
     * Mejoramos la eficiencia con el JOIN
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.2
     * 
     */
    @Override
    public void ListarTodosLosUsuariosEnPantalla() {
        int i = 0;
        // SQL con JOIN para traer todos los datos en UNA SOLA consulta
        String sql = """
                    SELECT u.ID AS usuario_id, u.NOMBRE_USUARIO, u.EMAIL, u.CONTRASENA, u.ROL,
                           dp.ID AS dp_id, dp.NOMBRE, dp.APELLIDO, dp.DNI
                    FROM USUARIO u
                    JOIN DATOS_PERSONALES dp ON u.ID_DATOS_PERSONALES = dp.ID
                """;

        try (java.sql.Connection conn = ConexionDB.conectar();
                java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Datos del Usuario
                int id = rs.getInt("usuario_id");
                String nombreUsuario = rs.getString("NOMBRE_USUARIO");
                String email = rs.getString("EMAIL");
                String contrasena = rs.getString("CONTRASENA");
                String rol = rs.getString("ROL");

                // Se crea el objeto Datos_Personales usando su constructor.
                int idDP = rs.getInt("dp_id");
                String dpNombre = rs.getString("NOMBRE");
                String dpApellido = rs.getString("APELLIDO");
                int dpDni = rs.getInt("DNI");
                Datos_Personales dp = new Datos_Personales(idDP, dpNombre, dpApellido, dpDni);

                i++;
                // Se crean solo para mostrar
                if (rol.equals("ADMINISTRADOR")) {
                    Administrador auxAdm = new Administrador(id, nombreUsuario, email, contrasena, dp, rol);
                    System.out.println(i + ". \n" + auxAdm);
                } else {
                    Cuenta auxCta = new Cuenta(id, nombreUsuario, email, contrasena, dp, rol);
                    System.out.println(i + ". \n" + auxCta);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar los usuarios: " + e.getMessage());
        }
    }

    /**
     * Busca un usuario por su ID de base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param id El ID del usuario a buscar.
     * @return El objeto Usuario (Administrador o Cuenta) si se encuentra, o null
     *         en caso contrario.
     */
    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM USUARIO WHERE ID = ?";
        try (java.sql.Connection conn = ConexionDB.conectar();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                String nombreUsuario = rs.getString("NOMBRE_USUARIO");
                String email = rs.getString("EMAIL");
                String contrasena = rs.getString("CONTRASENA");
                String rol = rs.getString("ROL");
                Datos_PersonalesDAOImpl dpImpl = new Datos_PersonalesDAOImpl();
                Datos_Personales dp = dpImpl.buscarPorID(rs.getInt("ID_DATOS_PERSONALES"));

                if ("ADMINISTRADOR".equals(rol)) {
                    return new Administrador(id, nombreUsuario, email, contrasena, dp, rol);
                } else if ("CUENTA".equals(rol)) {
                    return new Cuenta(id, nombreUsuario, email, contrasena, dp, rol);
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("❌ Error al buscar el usuario por ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     * NO IMPLEMENTADO - NO LO NECESITAMOS EN EL ENTREGABLE 2.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param usr El usuario con los datos a actualizar.
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    @Override
    public boolean actualizar(Usuario usr) {
        // Implementacion no necesaria para el entregable Nro 2.
        System.out.println("FUNCIONALIDAD NO IMPLEMENTADA");
        return false;
    }

    /**
     * Devuelve una lista con todos los usuarios de la base de datos cargados.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return Lista cargada con todos los usuarios de la base de datos.
     */
    @Override
    public List<Usuario> devolverListaUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = """
                    SELECT u.ID AS usuario_id, u.NOMBRE_USUARIO, u.EMAIL, u.CONTRASENA, u.ROL,
                           dp.ID AS dp_id, dp.NOMBRE, dp.APELLIDO, dp.DNI
                    FROM USUARIO u
                    JOIN DATOS_PERSONALES dp ON u.ID_DATOS_PERSONALES = dp.ID
                """;
        try (java.sql.Connection conn = ConexionDB.conectar();
                java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Datos del Usuario
                int id = rs.getInt("usuario_id");
                String nombreUsuario = rs.getString("NOMBRE_USUARIO");
                String email = rs.getString("EMAIL");
                String contrasena = rs.getString("CONTRASENA");
                String rol = rs.getString("ROL");

                // Se crea el objeto Datos_Personales usando su constructor.
                int idDP = rs.getInt("dp_id");
                String dpNombre = rs.getString("NOMBRE");
                String dpApellido = rs.getString("APELLIDO");
                int dpDni = rs.getInt("DNI");
                Datos_Personales dp = new Datos_Personales(idDP, dpNombre, dpApellido, dpDni);

                // Se carga en la lista el tipo de usuario correcto.
                if ("ADMINISTRADOR".equals(rol)) {
                    lista.add(new Administrador(id, nombreUsuario, email, contrasena, dp, rol));
                } else if ("CUENTA".equals(rol)) {
                    lista.add(new Cuenta(id, nombreUsuario, email, contrasena, dp, rol));
                }

            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar los usuarios: " + e.getMessage());
            return null;
        }
        return lista;

    }

}