package Control;

import java.util.Scanner;

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
 * @version 1.3
 * 
 */
public class Main {
    /**
     * Constructor por defecto.
     */
    public Main() {
    }

    /**
     * Código de escape ANSI para cambiar el color del texto a azul en la consola.
     * NO ES NECESARIO
     */
    public static final String ANSI_BLUE = "\u001B[34m";
    /**
     * Código de escape ANSI para resetear el color del texto en la consola al
     * predeterminado.
     * NO ES NECESARIO
     */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * Punto de entrada principal de la aplicación.
     * Inicializa la base de datos, muestra un mensaje de bienvenida y de prueba,
     * e inicia la simulación principal.
     * 
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        // SIEMPRE SE SUPONE QUE LA DB ESTA CREADA!
        Scanner in = new Scanner(System.in);

        // inicializador de tablas
        InicializadorDB inicializadorDB = new InicializadorDB();
        inicializadorDB.crearTablas(); // Comentado para no borrar la DB en cada ejecución
        // fin inicializador de tablas

        System.out.println();
        System.out.println();
        System.out.println(
                ANSI_BLUE + "ALCLARACION: ESTE MENSAJE NO PERTENECERIA REALMENTE A LA APLICACION" + '\n' +
                        "USTED PUEDE INGRESAR SU CORREO Y CONTRASEÑA Y DEPENDIENDO SI ES" + '\n' +
                        "ADMINISTRADOR O CUENTA TIENE DIFERENTES OPCIONES." + '\n' +
                        "INICIALMENTE LE PERMITE INICIAR SECION, REGISTRARSE O SALIR DE LA APP." + '\n' +
                        "SI CIERRA LA SESION VUELVE AL INICIO." + '\n' +
                        "BASE DE DATOS CARGADA, EJECUTAR CARPETA CON LA BASE DE DATOS, SQL EN README." + '\n' +
                        "HAY VARIAS ACLARACIONES EN EL README." + '\n' +
                        "USUARIOS DE PRUEBA:" + '\n' +
                        "ADMINISTRADOR: Correo: 1, Contraseña: 1." + '\n' +
                        "CUENTA: Correo: 2, Contraseña: 2." + '\n' + ANSI_RESET);
        simulacion(in);

        in.close();
    }

    /**
     * Metodo que simula al programa.
     * Pide ingresar los datos y dependiendo entra a Administrador o Cuenta a su vez
     * si no existen los datos pregunta si quieres crear una cuenta.
     * Los Administradores se cargaran directamente desde la base de datos.
     * Usuarios de prueba: //NO SON VALIDOS REALMENTE, NO CUMPLE CON EL STANDARD.
     * Administrador: Correo: 1, Contraseña: 1.
     * Cuanta: Correo: 2, Contraseña: 2.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param in Scanner para leer la entrada del usuario.
     */
    public static void simulacion(Scanner in) {
        Plataforma plataforma = new Plataforma();// Creacion de la clase plataforma
        CargadoresyComunicacionDB cargadoresyComunDB = new CargadoresyComunicacionDB();// Sirve para los cargadores.

        while (true) {// infinito
            System.out.println("\n🎬 Bienvenido a la plataforma de streaming TDL2 🎬");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrar una nueva cuenta");
            System.out.println("3. Salir");
            System.out.print("👉 Ingrese su opción (1-3): ");
            String opcionMenu = in.nextLine();

            switch (opcionMenu) {
                case "1": // Iniciar sesión
                    System.out.println("\n▶️ Ingrese sus datos para ingresar:");
                    System.out.print("📧 Correo: ");
                    String correo = in.nextLine().toLowerCase();
                    System.out.print("🔑 Contraseña: ");
                    String contrasena = in.nextLine();

                    if (plataforma.validarCorreo(correo)) { // El correo existe
                        boolean loginExitoso = false;
                        while (!loginExitoso) {
                            if (plataforma.validarUsuario(correo, contrasena)) {
                                // Obtenemos el usuario a través del DAO de la plataforma
                                Usuario usuario = plataforma.getUsrDAO().buscarPorEmailyContrasena(correo, contrasena);
                                System.out.println("✅ ¡Ingreso exitoso como " + usuario.getRol() + "!");
                                if (usuario.getRol().equals("ADMINISTRADOR")) {
                                    MenuManager.simulacionAdm(in, (Administrador) usuario, plataforma);
                                } else {
                                    MenuManager.simulacionCta(in, (Cuenta) usuario, plataforma);
                                }
                                loginExitoso = true; // Para salir del bucle de reintentos
                            } else {
                                System.out.println("❌ Contraseña incorrecta.");
                                System.out.print("¿Desea reintentar (R) o volver al menú principal (S)?: ");
                                String opcionReintento = in.nextLine();
                                if (opcionReintento.equalsIgnoreCase("S")) {
                                    break; // Sale del bucle de reintentos y vuelve al menú principal
                                }
                                System.out.print("🔑 Contraseña: ");
                                contrasena = in.nextLine();
                            }
                        }
                    } else { // El correo no existe
                        System.out.println("❌ Usuario inexistente.");
                        System.out.println("¿Desea crear una nueva cuenta?");
                        if (cargadoresyComunDB.confirmacion(in)) {
                            plataforma.cargarYguardarCuenta(in);
                        }
                    }
                    break;

                case "2": // Registrar cuenta
                    plataforma.cargarYguardarCuenta(in);
                    break;

                case "3": // Salir
                    System.out.println("👋 ¡Hasta luego!");
                    return; // Termina el método y la aplicación

                default:
                    System.out.println("❌ Opción no válida. Por favor, elija 1, 2 o 3.");
                    break;
            }
        }
    }
}
