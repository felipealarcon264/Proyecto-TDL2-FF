package Servicio;

import java.util.Scanner;

import Entes.Administrador;
import Entes.Cuenta;
import Entes.Datos_Personales;
import DAO.Datos_PersonalesDAOImpl;
import DAO.UsuarioDAOImpl;

public class ComunicacionDB {
    int x;


/**
 * Basicamente se encarga de cargar y guardar directamente en la DB.
 * @param scanner
 */
public void cargarYGuardarEnDBDatos_Personales(Scanner scanner) {
        Datos_Personales dp = cargaDatosPersonales(scanner);
        Datos_PersonalesDAOImpl dpImpl = new Datos_PersonalesDAOImpl();
        dpImpl.guardar(dp);
    }

/**
 * Basicamente se encarga de cargar y guardar directamente en la DB.
 * @param scanner
 */
    public void cargarYGuardarEnDBAdministrador(Scanner scanner) {
        Administrador admin = cargaAdministrador(scanner);
        UsuarioDAOImpl usrImpl = new UsuarioDAOImpl();
        usrImpl.guardar(admin);
    }

/**
 * Basicamente se encarga de cargar y guardar directamente en la DB.
 * @param scanner
 */
    public void cargarYGuardarEnDBCuenta(Scanner scanner) {
        Cuenta cta = cargaCuenta(scanner);
        UsuarioDAOImpl usrImpl = new UsuarioDAOImpl();
        usrImpl.guardar(cta);
    }



    /**
     * Carga los datos personales desde la entrada estándar (consola).
     * Verifica que los nombres y apellidos contengan solo letras y que el DNI sea
     * un número entero positivo.
     * En caso de datos inválidos, solicita al usuario que reingrese los datos.
     * Verifica que el dni no haya sigo ingresado previamente a la base de datos.
     * finalmente solicita confirmación de los datos ingresados caso contrario
     * reinicia el proceso.
     * 
     * @author Grupo 4 - Taller de lenguajes II
     * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
     * @return Un objeto {@link Datos_Personales} con los datos ingresados.
     */
    public static Datos_Personales cargaDatosPersonales(Scanner scanner) {
        System.out.println("Carga de datos personales: ");
        boolean datosValidos = false;
        String nombre = "";
        String apellido = "";
        int dni = -1;
        Datos_PersonalesDAOImpl DPdao = new Datos_PersonalesDAOImpl();
        while (!datosValidos) {
            System.out.print("Ingrese el nombre: ");
            nombre = scanner.nextLine();
            while (!contieneSoloLetras(nombre)) {
                System.out.print("Nombre inválido. Ingrese solo letras: ");
                nombre = scanner.nextLine();
            }
            System.out.print("Ingrese el apellido: ");
            apellido = scanner.nextLine();
            while (!contieneSoloLetras(apellido)) {
                System.out.print("Apellido inválido. Ingrese solo letras: ");
                apellido = scanner.nextLine();
            }
            System.out.print("Ingrese el DNI: ");
            boolean dniValido = false;
            while (!dniValido) {
                try {
                    dni = scanner.nextInt();
                    dniValido = true;
                } catch (Exception e) {
                    System.out.print("DNI inválido. Ingrese un número entero: ");
                    dniValido = false;
                }
                if (dni < 0 && dniValido) {
                    System.out.print("DNI inválido. Ingrese un número entero positivo: ");
                    dniValido = false;
                }
                if (DPdao.buscarPorDNI(dni) != null && dniValido) {
                    System.out.print("DNI ya ingresado previamente. Ingrese un DNI diferente: ");
                    dniValido = false;
                }
                scanner.nextLine(); // Limpiar el buffer

            }
            System.out.println("Datos ingresados:" +
                    "\nNombre: " + nombre +
                    "\nApellido: " + apellido +
                    "\nDNI: " + dni);
            System.out.println("CONFIRMACION DE CARGA -> DATOS PERSONALES.");
            datosValidos = confirmacionDatos(scanner);
        }
        System.out.println("Datos confirmados...");
        return new Datos_Personales(nombre, apellido, dni);
    }

    /**
     * Solicita al usuario la confirmación de los datos ingresados.
     * 
     * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
     * @return {@code true} si el usuario confirma los datos, {@code false} si desea
     *         reingresar los datos.
     */
    private static boolean confirmacionDatos(Scanner scanner) {
        System.out.print("Confirmar datos (S/N): ");
        String confirmacion = scanner.nextLine();
        while (!confirmacion.equalsIgnoreCase("S") && !confirmacion.equalsIgnoreCase("N")) {
            System.out.print("Entrada inválida. Ingrese 'S' para confirmar o 'N' para reingresar los datos: ");
            confirmacion = scanner.nextLine();
        }
        return confirmacion.equalsIgnoreCase("S");
    }

    /**
     * Verifica si una cadena de texto contiene únicamente letras (mayúsculas y/o
     * minúsculas).
     * 
     * @param texto La cadena de texto a verificar.
     * @return {@code true} si la cadena contiene solo letras , {@code false} en
     *         caso contrario.
     */
    private static boolean contieneSoloLetras(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        for (char caracter : texto.toCharArray()) {
            if (!Character.isLetter(caracter)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Carga un administrador desde la entrada estándar (consola).
     * Se supone que se utilizara si se quiere guardar un Administrador -> Usuario a
     * la base de datos.
     * Dentro del proceso luego de confirmar Datos_Personales primero los carga en
     * la base da datos.
     * Toma todos los requerimientos de validaciones.
     * 
     * @param scanner
     * @return Administrador generado.
     */
    public static Administrador cargaAdministrador(Scanner scanner) {
        System.out.println("Carga de un administrador: ");
        String nombreUsuario = "";
        String email = "";
        String contrasena = "";
        Datos_Personales dp = null;
        String rol = "ADMINISTRADOR";
        boolean datosValidos = false;

        // Carga de datos por teclado.
        while (!datosValidos) {
            System.out.print("Ingrese el nombre de usuario: ");
            nombreUsuario = scanner.nextLine();
            System.out.print("Ingrese el email: ");
            email = scanner.nextLine();
            while (!esFormatoEmailSimpleValido(email)) {
                System.out.print("Email inválido. Ingrese un email válido: ");
                email = scanner.nextLine();
            }
            System.out.print("Ingrese la contraseña: ");
            contrasena = scanner.nextLine();
            dp = cargaDatosPersonales(scanner);
            System.out.println("CONFIRMACION DE CARGA -> ADMINISTRADOR.");
            datosValidos = confirmacionDatos(scanner);
        }

        // Todo confirmado se prodece crear el objeto Administrador.

        return new Administrador(-1, nombreUsuario, email, contrasena, dp, rol);// -1 corresponde a un valor invalido de
                                                                                // id "Se lo da realmente DB"
    }

    /**
     * Carga una Cuenta desde la entrada estándar (consola).
     * Se supone que se utilizara si se quiere guardar una Cuenta -> Usuario a la
     * base de datos.
     * Dentro del proceso luego de confirmar Datos_Personales primero los carga en
     * la base da datos.
     * Toma todos los requerimientos de validaciones.
     * 
     * @param scanner
     * @return Cuenta generado.
     */
    public static Cuenta cargaCuenta(Scanner scanner) {
        System.out.println("Carga de una cuenta: ");
        String nombreUsuario = "";
        String email = "";
        String contrasena = "";
        Datos_Personales dp = null;
        String rol = "CUENTA";
        boolean datosValidos = false;

        // Carga de datos por teclado.
        while (!datosValidos) {
            System.out.print("Ingrese el nombre de usuario: ");
            nombreUsuario = scanner.nextLine();
            System.out.print("Ingrese el email: ");
            email = scanner.nextLine();
            while (!esFormatoEmailSimpleValido(email)) {
                System.out.print("Email inválido. Ingrese un email válido: ");
                email = scanner.nextLine();
            }
            System.out.print("Ingrese la contraseña: ");
            contrasena = scanner.nextLine();
            dp = cargaDatosPersonales(scanner);
            System.out.println("CONFIRMACION DE CARGA -> CUENTA.");
            datosValidos = confirmacionDatos(scanner);
        }

        // Todo confirmado se prodece crear el objeto Cuenta.

        return new Cuenta(-1, nombreUsuario, email, contrasena, dp, rol); // -1 corresponde a un valor invalido de id
                                                                          // "Se lo da realmente DB"
    }

    /**
     * Verifica de forma simple si un correo electrónico tiene un formato básico
     * válido:
     * contiene exactamente un '@' y este no es el último carácter.
     * No valida la estructura completa xxx@yyy.zzz.
     * HECHO TOTALMENTE CON IA.
     *
     * @param email El correo electrónico (String) a verificar.
     * @return {@code true} si el email cumple las condiciones simples,
     *         {@code false} en caso contrario.
     */
    public static boolean esFormatoEmailSimpleValido(String email) {
        // 1. Verifica si es nulo o vacío
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        // 2. Busca la posición del primer '@'
        int arrobaIndex = email.indexOf('@');

        // 3. Verifica si no hay '@' o si hay más de uno
        // (si la primera posición no es igual a la última, hay más de uno)
        if (arrobaIndex == -1 || arrobaIndex != email.lastIndexOf('@')) {
            return false;
        }

        // 4. Verifica si el '@' es el último carácter
        if (arrobaIndex == email.length() - 1) {
            return false; // No hay nada después del '@'
        }

        // 5. Si pasó todas las verificaciones, es válido (según estas reglas simples)
        return true;
    }


}

