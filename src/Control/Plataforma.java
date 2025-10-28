package Control;

import DAO.Datos_PersonalesDAOImpl;
import DAO.PeliculaDAOImpl;
import DAO.ReseniaDAOImpl;
import DAO.UsuarioDAOImpl;
import Entes.Usuario;
import Entes.Cuenta;
import Entes.Administrador;
import Servicio.CargadoresyComunicacionDB;
import java.util.Scanner;
import java.util.List;
import Catalogo.Pelicula;
import Catalogo.Resenia;
import Utilidades.Comparadores.*;

/**
 * Clase principal que representa la plataforma de streaming.
 * Gestiona los usuarios, el catalogo de contenidos y el acceso
 * al sistema de reportes.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.1
 */
public class Plataforma {

    private Datos_PersonalesDAOImpl dpDAO;
    private PeliculaDAOImpl peliDAO;
    private UsuarioDAOImpl usrDAO;
    private ReseniaDAOImpl resDAO;
    private CargadoresyComunicacionDB cargadoresyComunDB;

    /**
     * Constructor de la Plataforma.
     */
    public Plataforma() {
        this.dpDAO = new Datos_PersonalesDAOImpl();
        this.peliDAO = new PeliculaDAOImpl();
        this.usrDAO = new UsuarioDAOImpl();
        this.resDAO = new ReseniaDAOImpl();
        this.cargadoresyComunDB = new CargadoresyComunicacionDB();
    }

    /**
     * Verifica el tipo de usuario (ej: "ADMINISTRADOR", "CUENTA").
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
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
     * @param scanner Entrada por teclado.
     * 
     */
    public void cargarYguardarCuenta(Scanner scanner) {
        Cuenta cta = cargadoresyComunDB.cargaCuenta(scanner);
        if (cta == null) {
            System.out.println("No se guardo nada en la base de datos.");
            return;
        }
        usrDAO.guardar(cta); // El DAO ya imprime el mensaje de éxito.
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
        Administrador adm = cargadoresyComunDB.cargaAdministrador(scanner);
        if (adm == null) {
            System.out.println("No se guardo nada en la base de datos.");
            return;
        }
        usrDAO.guardar(adm); // El DAO ya imprime el mensaje de éxito.
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
        this.peliDAO.guardar(pelicula); // Si es null, el DAO se encarga de dar error.
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
     * @param usuario Usuario que realiza la reseña.
     */
    public void cargarYguardarReseña(Scanner scanner, Usuario usuario) {
        Resenia reseña = this.cargadoresyComunDB.cargaResenia(scanner, usuario);
        this.resDAO.guardar(reseña); // Si es null, el DAO se encarga de dar error.
    }

    /**
     * Elimina una pelicula existente de la base de datos tambien en la lista de
     * plataforma.
     * Los mensajes seran emitidos por el metodo borrar.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param pelicula La película a eliminar.
     * @return true si se pudo borrar de la DB y de la lista, false en caso
     *         contrario.
     */
    public boolean eliminarPelicula(Pelicula pelicula) {
        return peliDAO.borrar(pelicula);
    }

    /**
     * Elimina una reseña existente de la base de datos y de la lista de
     * plataforma.
     * Los mensajes seran emitidos por el metodo borrar de ReseniaDAO.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.2
     * 
     * @param resenia La reseña a eliminar.
     * @return true si se pudo borrar de la DB y de la lista, false en caso
     *         contrario.
     */
    public boolean eliminarResenia(Resenia resenia) {
        return resDAO.borrar(resenia);
    }

    /**
     * Actualiza el estado de una reseña en la base de datos y en la lista de
     * plataforma.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param resenia La reseña a actualizar su estado aprobado||desaprobado.
     * @return true si actualizo la resenia, false en caso contrario.
     */
    public boolean actualizarEstadoResenia(Resenia resenia) {
        return resDAO.actualizar(resenia);
    }

    /**
     * Filtra y devuelve una lista de reseñas que pertenecen a un usuario
     * específico.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param idUsuario El ID del usuario cuyas reseñas se quieren obtener.
     * @return Una lista de objetos {@link Resenia}.
     */
    public List<Resenia> obtenerReseniasDeUsuario(int idUsuario) {
        List<Resenia> reseniasDelUsuario = new java.util.ArrayList<>();
        List<Resenia> todasLasResenias = resDAO.devolverListaResenia(); // Se busca en la DB
        if (todasLasResenias == null)
            return reseniasDelUsuario; // En caso de error en DAO
        for (Resenia resenia : todasLasResenias) {
            if (resenia.getUsuario() != null && resenia.getUsuario().getIdDB() == idUsuario) {
                reseniasDelUsuario.add(resenia);
            }
        }
        return reseniasDelUsuario;
    }

    /**
     * Muestra por consola todas las reseñas de un usuario específico.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param idUsuario El ID del usuario.
     * @return true si se encontró y mostró al menos una reseña, false en caso
     *         contrario.
     */
    public boolean mostrarReseniasDeUsuario(int idUsuario) {
        List<Resenia> misResenias = obtenerReseniasDeUsuario(idUsuario);
        for (Resenia resenia : misResenias) {
            System.out.println(resenia);
            System.out.println(); // Espacio extra
        }
        return !misResenias.isEmpty();
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
        List<Usuario> listaUsuario = usrDAO.devolverListaUsuarios(); // Se obtiene la lista de la DB

        while (true) {
            String opcion = in.nextLine();
            switch (opcion) {
                case "1":
                    ComparadorUsuarioPorEmail comparadorPorEmail = new ComparadorUsuarioPorEmail();
                    listaUsuario.sort(comparadorPorEmail);
                    System.out.println("✅ Lista de usuarios ordenada por email.");
                    listaUsuario.forEach(System.out::println); // Mostramos la lista ordenada
                    return;
                case "2":
                    ComparadorUsuarioPorNombreUsuario comparadorPorNombreUsuario = new ComparadorUsuarioPorNombreUsuario();
                    listaUsuario.sort(comparadorPorNombreUsuario);
                    System.out.println("✅ Lista de usuarios ordenada por nombre de usuario.");
                    listaUsuario.forEach(System.out::println); // Mostramos la lista ordenada
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
        List<Pelicula> listaPelicula = peliDAO.devolverListaPelicula(); // Se obtiene la lista de la DB

        while (true) {
            String opcion = in.nextLine();
            switch (opcion) {
                case "1":
                    listaPelicula.sort(new ComparadorPeliculaPorTitulo());
                    System.out.println("✅ Lista de películas ordenada por título.");
                    listaPelicula.forEach(System.out::println);
                    return;
                case "2":
                    listaPelicula.sort(new ComparadorPeliculaPorDuracion());
                    System.out.println("✅ Lista de películas ordenada por duración.");
                    listaPelicula.forEach(System.out::println);
                    return;
                case "3":
                    listaPelicula.sort(new ComparadorPeliculaPorGenero());
                    System.out.println("✅ Lista de películas ordenada por género.");
                    listaPelicula.forEach(System.out::println);
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
     * 
     * @param usuario El usuario a eliminar.
     * @return true si se pudo borrar de la DB y de la lista, false en caso
     *         contrario.
     */
    public boolean eliminarUsuario(Usuario usuario) {
        return usrDAO.borrar(usuario);
    }

    /**
     * Verifica si un correo electrónico ya está registrado en la plataforma.
     * Siempre suponemos que un correo no se puede ingresar dos veces por lo que
     * a la primera coincidencia retorna true.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param correo     El correo a validar.
     * @param contrasena La contraseña a validar.
     * @return true si el correo está registrado, false en caso
     *         contrario.
     */
    public boolean validarUsuario(String correo, String contrasena) {
        // Se obtiene la lista directamente de la DB para validar
        List<Usuario> listaUsuario = usrDAO.devolverListaUsuarios();
        if (listaUsuario == null) {
            System.out.println("Error: No se pudo obtener la lista de usuarios para validar.");
            return false;
        }

        for (Usuario usuario : listaUsuario) {
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
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param correo El correo a verificar.
     * @return true si el correo existe, false en caso contrario.
     */
    public boolean validarCorreo(String correo) {
        // Se obtiene la lista directamente de la DB para validar
        List<Usuario> listaUsuario = usrDAO.devolverListaUsuarios();
        if (listaUsuario == null) {
            System.out.println("Error: No se pudo obtener la lista de usuarios para validar.");
            return false;
        }

        for (Usuario usuario : listaUsuario) {
            if (usuario.getEmail() != null && usuario.getEmail().equals(correo)) {
                return true;
            }
        }
        // Si no encontro.
        return false;
    }

    /**
     * Obtiene el DAO para los datos personales.
     * 
     * @return El DAO de datos personales.
     */
    public Datos_PersonalesDAOImpl getDpDAO() {
        return dpDAO;
    }

    /**
     * Establece el DAO para los datos personales.
     * 
     * @param dpDAO El nuevo DAO de datos personales.
     */
    public void setDpDAO(Datos_PersonalesDAOImpl dpDAO) {
        this.dpDAO = dpDAO;
    }

    /**
     * Obtiene el DAO para las películas.
     * 
     * @return El DAO de películas.
     */
    public PeliculaDAOImpl getPeliDAO() {
        return peliDAO;
    }

    /**
     * Establece el DAO para las películas.
     * 
     * @param peliDAO El nuevo DAO de películas.
     */
    public void setPeliDAO(PeliculaDAOImpl peliDAO) {
        this.peliDAO = peliDAO;
    }

    /**
     * Obtiene el DAO para los usuarios.
     * 
     * @return El DAO de usuarios.
     */
    public UsuarioDAOImpl getUsrDAO() {
        return usrDAO;
    }

    /**
     * Establece el DAO para los usuarios.
     * 
     * @param usrDAO El nuevo DAO de usuarios.
     */
    public void setUsrDAO(UsuarioDAOImpl usrDAO) {
        this.usrDAO = usrDAO;
    }

    /**
     * Obtiene el gestor de cargadores y comunicación con la DB.
     * 
     * @return El objeto CargadoresyComunicacionDB.
     */
    public CargadoresyComunicacionDB getCargadoresyComunDB() {
        return cargadoresyComunDB;
    }

    /**
     * Establece el gestor de cargadores y comunicación con la DB.
     * 
     * @param cargadoresyComunDB El nuevo objeto CargadoresyComunicacionDB.
     */
    public void setCargadoresyComunDB(CargadoresyComunicacionDB cargadoresyComunDB) {
        this.cargadoresyComunDB = cargadoresyComunDB;
    }

    /**
     * Obtiene el DAO para las reseñas.
     * 
     * @return El DAO de reseñas.
     */
    public ReseniaDAOImpl getResDAO() {
        return resDAO;
    }

    /**
     * Establece el DAO para las reseñas.
     * 
     * @param resDAO El nuevo DAO de reseñas.
     */
    public void setResDAO(ReseniaDAOImpl resDAO) {
        this.resDAO = resDAO;
    }
}