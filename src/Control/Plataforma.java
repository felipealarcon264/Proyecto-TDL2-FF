package Control;

import DAO.Datos_PersonalesDAOImpl;
import DAO.PeliculaDAOImpl;
import DAO.UsuarioDAOImpl;
import Entes.Perfil;
import Entes.Usuario;
import Enums.Plan;
import Entes.Cuenta;
import Entes.Administrador;
import Servicio.CargadoresyComunicacionDB;
import java.util.Scanner;

//No se necesita en la segunda entrega.import Reportes.ReporteManager;

/**
 * Clase principal que representa la plataforma de streaming.
 * Gestiona los usuarios, el catalogo de contenidos y el acceso
 * al sistema de reportes.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.1
 */
public class Plataforma {

    // Ver aclaraciones en Readme.
    // Quitamos el controlador de reportes y listas innecesarias.

    Datos_PersonalesDAOImpl dpDAO;
    PeliculaDAOImpl peliDAO;
    UsuarioDAOImpl usrDAO;
    CargadoresyComunicacionDB msjBD;

    /**
     * Constructor de la Plataforma.
     */
    public Plataforma() {
        this.dpDAO = new Datos_PersonalesDAOImpl();
        this.peliDAO = new PeliculaDAOImpl();
        this.usrDAO = new UsuarioDAOImpl();
        this.msjBD = new CargadoresyComunicacionDB();

    }

    /**
     * Verifica el tipo de usuario (ej: "ADMINISTRADOR", "CUENTA").
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @param usuario El usuario a verificar.
     * @return Un String indicando el tipo.
     */
    public String verificarTipoUsuario(Usuario usuario) {
        Usuario aux = usrDAO.buscarPorEmailyContrasena(usuario.getEmail(), usuario.getContrasena());
        if (aux == null)
            return "";
        return aux.getRol();
    } // Implementacion innecesaria.

    /**
     * Reemplazo de agregarUsuario.
     * Crea un usario y lo carga en la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     */
    public void cargarYguardarCuenta(Scanner scanner) {
        Cuenta cta = msjBD.cargaCuenta(scanner);
        if (cta == null) {
            System.out.println("No se guardo nada en la base de datos.");
            return;
        }
        usrDAO.guardar(cta);
        System.out.println("✅Cuenta guardada exitosamente!");
    }

    /**
     * Reemplazo de agregarUsuario.
     * Crea un usario y lo carga en la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @param usuario El usuario a agregar.
     */
    public void cargarYguardarAdministrador(Scanner scanner) {
        Administrador adm = msjBD.cargaAdministrador(scanner);
        if (adm == null) {
            System.out.println("No se guardo nada en la base de datos.");
            return;
        }
        usrDAO.guardar(adm);
        System.out.println("✅Administrador guardado exitosamente!");
    }

    /**
     * Elimina un usuario existente de la plataforma.
     * 
     * @param usuario El usuario a eliminar.
     */
    public void eliminarUsuario(Usuario usuario) {
        // Implementación pendiente...
    }

    /**
     * Inicia la reproducción de un contenido para un perfil específico.
     * Podría crear y devolver una instancia de Reproductor.
     * 
     * @param perfil El perfil que inicia la reproducción.
     */
    public void Reproducir(Perfil perfil /* , Contenido contenido // Probablemente necesites el contenido */) {
        // Implementación pendiente...
    }

    /**
     * Verifica si un correo electrónico ya está registrado en la plataforma.
     * 
     * @param correo El correo a validar.
     * @return El objeto Usuario si el correo está registrado, null en caso
     *         contrario.
     */
    public Usuario validarUsuario(String correo) {
        // Implementación pendiente...
        return null;
    }

    /**
     * YA ESTA HECHO EN SERVICIO
     * Crea una nueva instancia de Cuenta (Usuario tipo cliente).
     * Nota: Este método probablemente solo crea el objeto; el guardado se haría
     * aparte.
     * 
     * @param correo     El correo para la nueva cuenta.
     * @param contrasena La contraseña para la nueva cuenta.
     * @return El nuevo objeto Usuario (tipo Cuenta) creado.
     */
    public Usuario crearCuenta(String correo, String contrasena) {
        return null;
    }

    /**
     * Actualiza el plan de suscripción de un usuario (asumiendo que opera sobre un
     * usuario actual).
     * 
     * @param plan El nuevo plan a asignar.
     */
    public void actualizarPlan(Plan plan /* , Usuario usuarioAActualizar // Necesitarás saber a quién actualizar */) {
        // Implementación pendiente...
    }
}
