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
import java.util.List;

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

    private Datos_PersonalesDAOImpl dpDAO;
    private PeliculaDAOImpl peliDAO;
    private UsuarioDAOImpl usrDAO;
    private CargadoresyComunicacionDB cargadoresyComunDB;
    private List<Usuario> listaUSuario; // Se manaejara esta lista para disminuir la entrada a la base de datos

    /**
     * Constructor de la Plataforma.
     */
    public Plataforma() {
        this.dpDAO = new Datos_PersonalesDAOImpl();
        this.peliDAO = new PeliculaDAOImpl();
        this.usrDAO = new UsuarioDAOImpl();
        this.cargadoresyComunDB = new CargadoresyComunicacionDB();
        this.listaUSuario = usrDAO.devolverListaUsuarios();
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
        Cuenta cta = cargadoresyComunDB.cargaCuenta(scanner,this.listaUSuario);
        if (cta == null) {
            System.out.println("No se guardo nada en la base de datos.");
            return;
        }
        usrDAO.guardar(cta);
        listaUSuario.add(cta);
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
        Administrador adm = cargadoresyComunDB.cargaAdministrador(scanner,this.listaUSuario);
        if (adm == null) {
            System.out.println("No se guardo nada en la base de datos.");
            return;
        }
        usrDAO.guardar(adm);
        listaUSuario.add(adm);
        System.out.println("✅Administrador guardado exitosamente!");
    }

    /**
     * Elimina un usuario existente de la base de datos y la lista de plataforma.
     * Los mensajes seran emitidos por el metodo borrar.
     * 
     * @param usuario El usuario a eliminar.
     * @return true si se pudo borrar y false si ocurrio un error/imprevisto.
     */
    public boolean eliminarUsuario(Usuario usuario) {
        if (usrDAO.borrar(usuario)) {
            listaUSuario.remove(usuario);
            return true;
        } else
            return false;
    }

    /**
     * Verifica si un correo electrónico ya está registrado en la plataforma.
     * Siempre suponemos que un correo no se puede ingresar dos veces por lo que
     * a la primera coincidencia retorna true.
     * 
     * @param correo El correo a validar.
     * @return true si el correo está registrado, false en caso
     *         contrario.
     */
    public boolean validarUsuario(String correo,String contrasena) {
        // Maneja el caso de que la lista sea nula.
        if (this.listaUSuario == null) {
            System.out.println("Error: No se pudo obtener la lista de usuarios para validar.");
            return false;
        }
        // Busca coincidencia.
        for (Usuario usuario : this.listaUSuario) {
            if (usuario.getEmail() != null && usuario.getEmail().equals(correo) && usuario.getContrasena().equals(contrasena)) {
                return true;
            }
        }
        // Si no encontro.
        return false;
    }

    public boolean validarCorreo(String correo) {
        // Maneja el caso de que la lista sea nula.
        if (this.listaUSuario == null) {
            System.out.println("Error: No se pudo obtener la lista de usuarios para validar.");
            return false;
        }
        // Busca coincidencia.
        for (Usuario usuario : this.listaUSuario) {
            if (usuario.getEmail() != null && usuario.getEmail().equals(correo)) {
                return true;
            }
        }
        // Si no encontro.
        return false;
    }

    /**
     * YA ESTA HECHO EN SERVICIO
     * Crea una nueva instancia de Cuenta (Usuario tipo cliente).
     * Nota: Este método probablemente solo crea el objeto; el guardado se haría
     * aparte.
     * NO IMPLEMENTADO.
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
     * NO IMPLEMENTADO.
     * 
     * @param plan El nuevo plan a asignar.
     */
    public void actualizarPlan(Plan plan /* , Usuario usuarioAActualizar // Necesitarás saber a quién actualizar */) {
        // Implementación pendiente...
    }

    /**
     * Inicia la reproducción de un contenido para un perfil específico.
     * Podría crear y devolver una instancia de Reproductor.
     * NO IMPLEMENTADO.
     * 
     * @param perfil El perfil que inicia la reproducción.
     */
    public void Reproducir(Perfil perfil /* , Contenido contenido // Probablemente necesites el contenido */) {
        // Implementación pendiente...
    }


    /**
    * Getters and setters
     */
    public Datos_PersonalesDAOImpl getDpDAO() {
        return dpDAO;
    }

    public void setDpDAO(Datos_PersonalesDAOImpl dpDAO) {
        this.dpDAO = dpDAO;
    }
    public PeliculaDAOImpl getPeliDAO() {
        return peliDAO;
    }
    public void setPeliDAO(PeliculaDAOImpl peliDAO) {
        this.peliDAO = peliDAO;
    }
    public UsuarioDAOImpl getUsrDAO() {
        return usrDAO;
    }
    public void setUsrDAO(UsuarioDAOImpl usrDAO) {
        this.usrDAO = usrDAO;
    }
    public CargadoresyComunicacionDB getCargadoresyComunDB() {
        return cargadoresyComunDB;
    }
    public void setCargadoresyComunDB(CargadoresyComunicacionDB cargadoresyComunDB) {
        this.cargadoresyComunDB = cargadoresyComunDB;
    }
    public List<Usuario> getListaUSuario() {
        return listaUSuario;
    }
    public void setListaUSuario(List<Usuario> listaUSuario) {
        this.listaUSuario = listaUSuario;
    }

}
