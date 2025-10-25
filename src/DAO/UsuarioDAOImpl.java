package DAO;

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
     * Guarda un usuario nuevo a la base de datos.
     * Primero carga sus datos personales a la base de datos y luego el usuario
     * completo.
     * Se tomaron todas las precauciones en caso de errores.
     * 
     * @author Grupo 4 - Taller de lenguajes II
     * @version 1.0
     * 
     * @param usr El usuario a guardar
     * @return true si se logro guardar y false en caso contrario.
     */
    @Override
    public boolean guardar(Usuario usr) {
        if (usr == null) {
            System.out.println("El usuario es nulo. No se puede guardar.");
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
                    System.out.println("Usuario guardado exitosamente.");
                    return true;
                } else {
                    System.out.println("❌Error! No se pudo guardar el usuario.");
                    return false;
                }
            } catch (java.sql.SQLException e) {
                System.out.println("Error al guardar el usuario: " + e.getMessage());
                return false;
            }

        } else {
            System.out.println("No se pudo cargar usuario. ❌Error al guardar los datos personales.");
            return false;
        }
    }

    /**
     * Borra un usuario de la base de datos
     * Primero borra los datos personales y luego al usuario.
     * Usuario y datos personales estan ligados fuertemente, si elimino uno elimino
     * a los dos
     * 
     * @author Grupo 4 - Taller de lenguajes II
     * @version 1.0
     * 
     * @param usr usuario a borrar.
     * 
     */
    @Override
    public boolean borrar(Entes.Usuario usr) {
        if (usr == null || usr.getIdDB() <= 0) {
            System.out.println("El usuario es nulo o invalido. No se puede borrar.");
            return false;
        }
        Datos_PersonalesDAOImpl Datos_PersonalesDAOImpl = new Datos_PersonalesDAOImpl();
        if (!Datos_PersonalesDAOImpl.borrar(usr.getDatosPersonales())) {
            System.out.println("Error al borrar los datos personales. ❌Error al borrar el usuario");
            return false;
        }
        String sql = "DELETE FROM USUARIO WHERE ID = ?";
        try (java.sql.Connection conn = ConexionDB.conectar();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usr.getIdDB());
            if (pstmt.executeUpdate() == 0) {
                System.out.println("No se encontraron usuarios con el ID proporcionado.");
                return false;
            } else {
                System.out.println("Usuario borrado correctamente.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al borrar el usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un usuario validando su ingreso y lo retorna.
     * 
     * @author Grupo 4 - Taller de lenguajes II
     * @version 1.0
     * 
     * @param email
     * @param contrasena
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
                    System.out.println("Administrador encontrado [" + nombreUsuario + "]");
                    return new Entes.Administrador(idDB, nombreUsuario, email, contrasena, dp, rol);
                } else if (rol.equals("CUENTA")) {
                    System.out.println("Cuenta encontrada [" + nombreUsuario + "]");
                    return new Entes.Cuenta(idDB, nombreUsuario, email, contrasena, dp, rol);
                } else
                    return null;
            }
            System.out.println("Usuario no encontrado.");
            return null;
        } catch (Exception e) {
            System.out.println("Error al buscar el usuario: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los Usuarios guardados en la DB y los muestra en pantalla
     * Mejoramos la eficiencia con el JOIN
     * 
     * @author Grupo 4 - Taller de lenguajes II
     * @version 1.2
     * 
     */
    @Override
    public void ListarTodosLosUsuariosEnPantalla() {
        int i=0;
        // SQL con JOIN para traer todos los datos en UNA SOLA consulta
        String sql = """
            SELECT u.ID, u.NOMBRE_USUARIO, u.EMAIL, u.CONTRASENA, u.ROL,
                   dp.NOMBRE, dp.APELLIDO, dp.DNI
            FROM USUARIO u
            JOIN DATOS_PERSONALES dp ON u.ID_DATOS_PERSONALES = dp.ID
        """;
        
        try (java.sql.Connection conn = ConexionDB.conectar();
                java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                // Datos del Usuario
                int id = rs.getInt("ID");
                String nombreUsuario = rs.getString("NOMBRE_USUARIO");
                String email = rs.getString("EMAIL");
                String contrasena = rs.getString("CONTRASENA");
                String rol = rs.getString("ROL");

                // --- AJUSTE REALIZADO ---
                // Creamos el objeto Datos_Personales usando tu constructor
                String dpNombre = rs.getString("NOMBRE");
                String dpApellido = rs.getString("APELLIDO");
                int dpDni = rs.getInt("DNI");
                Datos_Personales dp = new Datos_Personales(dpNombre, dpApellido, dpDni);
                
                i++;
                // Se crean solo para mostrar
                if (rol.equals("ADMINISTRADOR")) {
                    Administrador auxAdm = new Administrador(id, nombreUsuario, email, contrasena, dp, rol);
                    System.out.println(i+". \n"+auxAdm);
                } else {
                    Cuenta auxCta = new Cuenta(id, nombreUsuario, email, contrasena, dp, rol);
                    System.out.println(auxCta);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar los usuarios: " + e.getMessage());
        }
    }
}