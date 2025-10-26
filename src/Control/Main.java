package Control;

import java.util.Scanner;

import Catalogo.Pelicula;
import DAO.UsuarioDAOImpl;

import DataBase.InicializadorDB;
import Entes.Administrador;
import Entes.Usuario;
import Entes.Cuenta;
import Servicio.CargadoresyComunicacionDB;

/**
 * Clase principal que inicia la aplicación y gestiona el flujo de interacción
 * con el usuario.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */
public class Main {
    public static void main(String[] args) {
        // SIEMPRE SE SUPONE QUE LA DB ESTA CREADA!
        Scanner in = new Scanner(System.in);

        // inicializador de tablas
        InicializadorDB inicializadorDB = new InicializadorDB();
        inicializadorDB.crearTablas();
        // fin inicializador de tablas

        simulacion(in);

        in.close();

    }

    /**
     * Metodo que simula al programa.
     * Pide ingresar los datos y dependiendo entra a Administrador o Cuenta a su vez
     * si no existen los datos pregunta si quieres crear una cuenta.
     * Los Administradores se cargaran directamente desde la base de datos.
     * Usuarios de prueba: //NO SOL VALIDOS REALMENTE.
     * Administrador: Correo: 1, Contraseña: 1.
     * Cuanta: Correo: 2, Contraseña: 2.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param in Scanner para leer la entrada del usuario.
     */
    public static void simulacion(Scanner in) {
        Plataforma plataforma = new Plataforma();// Creacion de la clase plataforma
        CargadoresyComunicacionDB cargadoresyComunDB = new CargadoresyComunicacionDB();// Sirve para los cargadores.
        UsuarioDAOImpl usrDAO = new UsuarioDAOImpl();

        System.out.println("🎬 Bienvenido a la plataforma de streaming TDL2 🎬");
        while (true) {// infinito

            boolean confirmar;
            System.out.println("\n▶️ Ingrese sus datos para ingresar:");
            System.out.print("📧 Correo: ");
            String correo = in.nextLine().toLowerCase();
            System.out.print("🔑 Contraseña: ");
            String contrasena = in.nextLine();
            if (plataforma.validarCorreo(correo)) {// Primero pregunto si el usuario existe con su correo.

                while (!plataforma.validarUsuario(correo, contrasena)) {// Valida la contraseña si o si se debe colocar,
                                                                        // damos entendido que si conoces el correo
                                                                        // tambien la contraseña.
                    System.out.print("❌ Contraseña incorrecta. Intente de nuevo: ");
                    contrasena = in.nextLine();
                }

                Usuario usuario = usrDAO.buscarPorEmailyContrasena(correo, contrasena);

                System.out.println("✅ ¡Ingreso exitoso como " + usuario.getRol() + "!");

                if (usuario.getRol().equals("ADMINISTRADOR"))
                    simulacionAdm(in, (Administrador) usuario, plataforma);// Lo envia como Administrador
                else
                    simulacionCta(in, (Cuenta) usuario, plataforma);// Lo envia como cuenta.

            } else {// el correo no exite
                System.out.println("❌ Usuario inexistente.");
                System.out.println("¿Desea crear una nueva cuenta?");// SUPONEMOS QUE LOS ADM SE CREAN EN OTRO LADO.
                confirmar = cargadoresyComunDB.confirmacion(in);
                if (confirmar) {
                    plataforma.cargarYguardarCuenta(in);
                }
            }
        }
    }

    /**
     * Es lo que puede hacer un administrador.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param in         El Scanner para leer la entrada del usuario.
     * @param adm        El objeto Administrador que ha iniciado sesión.
     * @param plataforma La instancia principal de la plataforma.
     */
    public static void simulacionAdm(Scanner in, Administrador adm, Plataforma plataforma) {
        System.out.println("👋 ¡Hola, Administrador " + adm.getDatosPersonales().getNombre() + "!");
        while (true) {
            System.out.println("\n--- Menú de Administrador ---");
            System.out.println("1. Agregar película");
            System.out.println("2. Borrar película");// Muestra primero la lista de peliculas.
            System.out.println("3. Elegir como mostrar la lista de usuarios");
            System.out.println("4. Borrar suario");
            System.out.println("5. Salir (Volver al menú anterior)");
            System.out.print("Ingrese su opción (1-5): ");

            String opcion = in.nextLine();
            String aux;

            switch (opcion) {
                case "1":
                    plataforma.cargarYguardarPelicula(in);
                    break;

                case "2":
                    System.out.println("\n--- 🎬 Lista de Películas 🎬 ---");
                    if (plataforma.getListaPelicula().size() != 0) {
                        for (Pelicula pelicula : plataforma.getListaPelicula()) {
                            System.out.println(pelicula + "\n");
                        }
                        System.out.print("👉 Ingresa el título de la película a eliminar: ");
                        aux = in.nextLine();
                        // Buscamos la película UNA SOLA VEZ y guardamos el objeto.
                        Pelicula peliculaAEliminar = plataforma.getPeliDAO().buscarPorTitulo(aux);
                        if (peliculaAEliminar != null) {
                            // Usamos el objeto ya encontrado para eliminarlo.
                            plataforma.eliminarPelicula(peliculaAEliminar);
                        }
                    } else
                        System.out.println("ℹ️ No hay películas para mostrar.");
                    break;
                case "3":
                    plataforma.ordenarListaUsuario(in);// Pregunta que metodo de ordenacion se prefiere.
                    for (Usuario usuario : plataforma.getListaUSuario()) {
                        System.out.println(usuario + "\n");
                    }
                    break;
                case "4":
                    System.out.println("\n--- 👥 Lista de Usuarios 👥 ---");
                    for (Usuario usuario : plataforma.getListaUSuario()) {
                        System.out.println(usuario + "\n");
                    }
                    System.out.print("👉 Ingresa el email del usuario a eliminar: ");
                    aux = in.nextLine();
                    // Hacemos lo mismo para el usuario: buscar una vez y reutilizar.
                    Usuario usuarioAEliminar = plataforma.getUsrDAO().buscarPorEmail(aux);
                    if (usuarioAEliminar != null) {
                        plataforma.eliminarUsuario(usuarioAEliminar);
                    }
                    break;

                case "5":
                    System.out.println("🚪 Cerrando sesión...");
                    return;
                default:
                    // --- Validación de entrada (si no es 1, 2 , 3 , 4 o 5) ---
                    System.out.println("-------------------------------------");
                    System.out.println("Error: Opción no válida. Intente de nuevo.");
                    System.out.println("-------------------------------------");
                    // El bucle 'while(true)' se repite automáticamente
            }
        }
    }

    /**
     * Es lo que puede hacer una cuenta
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param in         El Scanner para leer la entrada del usuario.
     * @param cta        El objeto Cuenta que ha iniciado sesión.
     * @param plataforma La instancia principal de la plataforma.
     */
    public static void simulacionCta(Scanner in, Cuenta cta, Plataforma plataforma) {
        System.out.println("👋 ¡Hola, " + cta.getDatosPersonales().getNombre() + "!");
        while (true) {
            System.out.println("\n--- Menú de Cuenta ---");
            System.out.println("1. Ver y ordenar lista de películas.");
            System.out.println("2. Crear una reseña.");
            System.out.println("3. Salir");
            System.out.print("Ingrese su opción (1-3): ");

            String opcion = in.nextLine();

            switch (opcion) {
                case "1":
                    plataforma.ordenarListaPelicula(in);
                    for (Pelicula pelicula : plataforma.getListaPelicula()) {
                        System.out.println(pelicula + "\n");
                    }
                    break;
                case "2":
                    plataforma.cargarYguardarReseña(in, cta);
                    break;
                case "3":
                    System.out.println("🚪 Cerrando sesión...");
                    return;
                default:
                    // --- Validación de entrada (si no es 1, 2 o 3) ---
                    System.out.println("-------------------------------------");
                    System.out.println("Error: Opción no válida. Intente de nuevo.");
                    System.out.println("-------------------------------------");
                    // El bucle 'while(true)' se repite automáticamente
            }
        }
    }
}
