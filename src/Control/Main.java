package Control;

import java.util.Scanner;
import java.util.List;

import Catalogo.Pelicula;
import DAO.UsuarioDAOImpl;

//import DataBase.InicializadorDB;
import Entes.Administrador;
import Entes.Usuario;
import Entes.Cuenta;
import Servicio.CargadoresyComunicacionDB;

public class Main {
    public static void main(String[] args) {
        // SIEMPRE SE SUPONE QUE LA DB ESTA CREADA!
        Scanner in = new Scanner(System.in);

        // inicializador de tablas
        // InicializadorDB inicializadorDB = new InicializadorDB();
        // inicializadorDB.crearTablas();
        // fin inicializador de tablas

        simulacion(in);

        in.close();

    }

    /**
     * Metodo que simula al programa.
     * Pide ingresar los datos y dependiendo entra a Administrador o Cuenta a su vez
     * si no existen los datos pregunta si quieres crear una cuenta.
     * Los Administradores se cargaran directamente desde la base de datos.
     * 
     * @param in
     */
    public static void simulacion(Scanner in) {
        Plataforma plataforma = new Plataforma();// Creacion de la clase plataforma
        CargadoresyComunicacionDB cargadoresyComunDB = new CargadoresyComunicacionDB();// Sirve para los cargadores.
        UsuarioDAOImpl usrDAO = new UsuarioDAOImpl();

        System.out.println("Bienvenido a la plataforma de streaming TDL2");
        while (true) {// infinito
            boolean confirmar;
            System.out.println("Ingrese sus datos para ingresar:");
            System.out.print("Correo: ");
            String correo = in.nextLine();
            System.out.print("Contraseña: ");
            String contrasena = in.nextLine();
            if (plataforma.validarCorreo(correo)) {// Primero pregunto si el usuario existe con su correo.

                while (!plataforma.validarUsuario(correo, contrasena)) {// Valida la contraseña si o si se debe colocar,
                                                                        // damos entendido que si conoces el correo
                                                                        // tambien la contraseña.
                    System.out.print("Contraseña incorrecta. Intente de nuevo: ");
                    contrasena = in.nextLine();
                }

                Usuario usuario = usrDAO.buscarPorEmailyContrasena(correo, contrasena);

                System.out.println("✌️" + usuario.getRol() + "✌️");

                if (usuario.getRol().equals("ADMINISTRADOR"))
                    simulacionAdm(in, (Administrador) usuario, plataforma);// Lo envia como Administrador
                else
                    simulacionCta(in, (Cuenta) usuario, plataforma);// Lo envia como cuenta.

            } else {// el correo no exite
                System.out.println("Usuario inexistente");
                System.out.println("Desea cargar una cuenta?");// SUPONEMOS QUE LOS ADM SE CREAN EN OTRO LADO.
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
     * @param in
     * @param adm
     */
    public static void simulacionAdm(Scanner in, Administrador adm, Plataforma plataforma) {
        System.out.println("Adminitrador: " + adm.getDatosPersonales().toString());
        while (true) {
            System.out.println("\n--- Menú de Administrador ---");
            System.out.println("1. Agregar película");
            System.out.println("2. Borrar película");
            System.out.println("3. Borrar cuenta de usuario");
            System.out.println("4. Salir (Volver al menú anterior)");
            System.out.print("Ingrese su opción (1-4): ");

            String opcion = in.nextLine();

            switch (opcion) {
                case "1":
                    plataforma.cargarYguardarPelicula(in);
                    break;

                case "2":
                    System.out.println("Ingresa el nombre de la pelicula a eliminar:");
                    String aux = in.nextLine();
                    plataforma.eliminarPelicula(plataforma.getPeliDAO().buscarPorTitulo(aux));
                    break;
                case "3":

                    break;

                case "4":
                    System.out.println("Cerrando sesion...");
                    return;
                default:
                    // --- Validación de entrada (si no es 1, 2 , 3 o 4) ---
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
     * @param in
     * @param cta
     */
    public static void simulacionCta(Scanner in, Cuenta cta, Plataforma plataforma) {
        System.out.println("Cuenta: " + cta.getDatosPersonales().toString());
        while (true) {
            System.out.println("\n--- Menú de Cuenta ---");
            System.out.println("1. Mostrar peliculas.");
            System.out.println("2. Salir");
            System.out.print("Ingrese su opción (1-2): ");

            String opcion = in.nextLine();

            switch (opcion) {
                case "1":
                    List<Pelicula> listaPelicula = plataforma.getPeliDAO().devolverListaPelicula();
                    for (Pelicula pelicula : listaPelicula) {
                        System.out.println(pelicula);
                    }
                    break;
                case "2":
                    System.out.println("Cerrando sesion...");
                    return;
                default:
                    // --- Validación de entrada (si no es 1, 2) ---
                    System.out.println("-------------------------------------");
                    System.out.println("Error: Opción no válida. Intente de nuevo.");
                    System.out.println("-------------------------------------");
                    // El bucle 'while(true)' se repite automáticamente
            }
        }
    }
}
