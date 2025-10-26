package Control;

import DAO.Datos_PersonalesDAOImpl;
import DAO.PeliculaDAOImpl;
import DAO.ReseniaDAOImpl;
import DAO.UsuarioDAOImpl;
import Entes.Perfil;
import Entes.Usuario;
import Enums.Plan;
import Entes.Cuenta;
import Entes.Administrador;
import Servicio.CargadoresyComunicacionDB;
import java.util.Scanner;
import java.util.List;
import Catalogo.Pelicula;
import Catalogo.Resenia;
import Utilidades.Comparadores.*;

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
    private ReseniaDAOImpl resDAO;
    private CargadoresyComunicacionDB cargadoresyComunDB;
    private List<Usuario> listaUSuario; // Se manaejara esta lista para disminuir la entrada a la base de datos, se
                                        // actualiza con cada guardar o eliminar.
    private List<Pelicula> listaPelicula; // Se manaejara esta lista para disminuir la entrada a la base de datos, se
                                          // actualiza con cada guardar o eliminar.

    private List<Resenia> listaResenia;
    /**
     * Constructor de la Plataforma.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public Plataforma() {
        this.dpDAO = new Datos_PersonalesDAOImpl();
        this.peliDAO = new PeliculaDAOImpl();
        this.usrDAO = new UsuarioDAOImpl();
        this.resDAO = new ReseniaDAOImpl();
        this.cargadoresyComunDB = new CargadoresyComunicacionDB();
        this.listaUSuario = usrDAO.devolverListaUsuarios();
        this.listaPelicula = peliDAO.devolverListaPelicula();
        this.listaResenia = resDAO.devolverListaResenia();
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
        Cuenta cta = cargadoresyComunDB.cargaCuenta(scanner, this.listaUSuario);
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
     * @param scanner El Scanner para leer la entrada del usuario.
     */
    public void cargarYguardarAdministrador(Scanner scanner) {
        Administrador adm = cargadoresyComunDB.cargaAdministrador(scanner, this.listaUSuario);
        if (adm == null) {
            System.out.println("No se guardo nada en la base de datos.");
            return;
        }
        usrDAO.guardar(adm);
        listaUSuario.add(adm);
        System.out.println("✅Administrador guardado exitosamente!");
    }

    /**
     * Se encarga de cargar y guardar una pelicula en la base de datos, se puede
     * cancelar.
     * Todos los mensajes se indican en guardar y cargaPelicula.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param scanner El Scanner para leer la entrada del usuario.
     */
    public void cargarYguardarPelicula(Scanner scanner) {
        Pelicula pelicula = this.cargadoresyComunDB.cargaPelicula(scanner);
        if (this.peliDAO.guardar(pelicula))// Si es null se encarga de dar error.
            listaPelicula.add(pelicula); // Si es != de null tambien lo carga en la lista.
    }

   /**
     * Se encarga de cargar y guardar una reseña en la base de datos, se puede
     * cancelar.
     * Todos los mensajes se indican en guardar y cargaPelicula.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param scanner El Scanner para leer la entrada del usuario.
     */
    public void cargarYguardarReseña(Scanner scanner,Usuario usuario) {
        Resenia reseña = this.cargadoresyComunDB.cargaResenia(scanner, this.listaPelicula, usuario);

        if (this.resDAO.guardar(reseña))// Si es null se encarga de dar error.
            listaResenia.add(reseña); // Si es != de null tambien lo carga en la lista.
    }



    /**
     * Elimina una pelicula existente de la base de datos tambien en la lista de
     * plataforma.
     * Los mensajes seran emitidos por el metodo borrar.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param pelicula La película a eliminar.
     * @return true si se pudo borrar de la DB y de la lista, false en caso contrario.
     */
    public boolean eliminarPelicula(Pelicula pelicula) {
        return peliDAO.borrar(pelicula) && this.listaPelicula.remove(pelicula);
    }

    /**
     * Pregunta por pantalla que manera de ordenacion de la lista de Usuarios se
     * requiere.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @param in El Scanner para leer la entrada del usuario.
     */
    public void ordenarListaUsuario(Scanner in) {
        System.out.println("\n--- Ordenamiento de lista usuarios ---");
        System.out.println("1. Ordenar por Email (A-Z).");
        System.out.println("2. Ordenar por Nombre de usuario (A-Z).");
        System.out.print("Ingrese su opción (1-2): ");

        while (true) {
            String opcion = in.nextLine();
            switch (opcion) {
                case "1":
                    ComparadorUsuarioPorEmail comparadorPorEmail = new ComparadorUsuarioPorEmail();
                    listaUSuario.sort(comparadorPorEmail);
                    System.out.println("✅ Lista de usuarios ordenada por email.");
                    return;
                case "2":
                    ComparadorUsuarioPorNombreUsuario comparadorPorNombreUsuario = new ComparadorUsuarioPorNombreUsuario();
                    listaUSuario.sort(comparadorPorNombreUsuario);
                    System.out.println("✅ Lista de usuarios ordenada por nombre de usuario.");
                    return;
                default:
                    System.out.print("❌ Error: Opción no válida. Intente de nuevo: ");
            }
        }
    }

    /**
     * Pregunta por pantalla qué manera de ordenación de la lista de Películas se
     * requiere.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param in El Scanner para leer la entrada del usuario.
     */
    public void ordenarListaPelicula(Scanner in) {
        System.out.println("\n--- Ordenamiento de lista de películas ---");
        System.out.println("1. Ordenar por Título (A-Z).");
        System.out.println("2. Ordenar por Duración (menor a mayor).");
        System.out.println("3. Ordenar por Género (alfabético).");
        System.out.print("Ingrese su opción (1-3): ");

        while (true) {
            String opcion = in.nextLine();
            switch (opcion) {
                case "1":
                    listaPelicula.sort(new ComparadorPeliculaPorTitulo());
                    System.out.println("✅ Lista de películas ordenada por título.");
                    return;
                case "2":
                    listaPelicula.sort(new ComparadorPeliculaPorDuracion());
                    System.out.println("✅ Lista de películas ordenada por duración.");
                    return;
                case "3":
                    listaPelicula.sort(new ComparadorPeliculaPorGenero());
                    System.out.println("✅ Lista de películas ordenada por género.");
                    return;
                default:
                    System.out.print("❌ Error: Opción no válida. Intente de nuevo: ");
            }
        }
    }

    /**
     * Elimina un usuario existente de la base de datos y la lista de plataforma.
     * Los mensajes seran emitidos por el metodo borrar.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param usuario El usuario a eliminar.
     * @return true si se pudo borrar de la DB y de la lista, false en caso contrario.
     */
    public boolean eliminarUsuario(Usuario usuario) {
        // Intenta borrar de la DB y si tiene éxito, intenta borrar de la lista en memoria.
        return usrDAO.borrar(usuario) && this.listaUSuario.remove(usuario);
    }

    /**
     * Verifica si un correo electrónico ya está registrado en la plataforma.
     * Siempre suponemos que un correo no se puede ingresar dos veces por lo que
     * a la primera coincidencia retorna true.
     * 
     * @param correo El correo a validar.
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return true si el correo está registrado, false en caso
     *         contrario.
     */
    public boolean validarUsuario(String correo, String contrasena) {
        // Maneja el caso de que la lista sea nula.
        if (this.listaUSuario == null) {
            System.out.println("Error: No se pudo obtener la lista de usuarios para validar.");
            return false;
        }
        // Busca coincidencia.
        for (Usuario usuario : this.listaUSuario) {
            if (usuario.getEmail() != null && usuario.getEmail().equals(correo)
                    && usuario.getContrasena().equals(contrasena)) {
                return true;
            }
        }
        // Si no encontro.
        return false;
    }

    /**
     * Valida si un correo existe en la lista de usuarios de la plataforma.
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param correo El correo a verificar.
     * @return true si el correo existe, false en caso contrario.
     */
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
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
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
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
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
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
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

    public List<Pelicula> getListaPelicula() {
        return listaPelicula;
    }

    public void setListaPelicula(List<Pelicula> listaPelicula) {
        this.listaPelicula = listaPelicula;
    }
}