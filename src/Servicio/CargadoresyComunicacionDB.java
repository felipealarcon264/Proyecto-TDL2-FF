package Servicio;

import java.util.Scanner;

import Entes.Administrador;
import Entes.Cuenta;
import Entes.Datos_Personales;
import Entes.Usuario;
import DAO.Datos_PersonalesDAOImpl;
import java.util.List;

/**
 * CARGADORES Y COMUNICACION CON BASE DE DATOS CON DICHOS CARGADORES.
 * 
 * @author Grupo 4 - Taller de lenguajes II
 * @version 1.0
 * 
 */
public class CargadoresyComunicacionDB {

    /**
     * Carga los datos personales desde la entrada estándar (consola).
     * Verifica que los nombres y apellidos contengan solo letras y que el DNI sea
     * un número entero positivo.
     * En caso de datos inválidos, solicita al usuario que reingrese los datos.
     * Verifica que el dni no haya sigo ingresado previamente a la base de datos.
     * finalmente solicita confirmación de los datos ingresados caso contrario
     * reinicia el proceso.
     * Se puede cancelar.
     * 
     * @author Grupo 4 - Taller de lenguajes II
     * @version 1.0
     * 
     * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
     * @return Un objeto {@link Datos_Personales} con los datos ingresados. o null
     *         si se cancela la carga.
     */
    public Datos_Personales cargaDatosPersonales(Scanner scanner) {
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
            datosValidos = confirmacion(scanner);
            if (!datosValidos) {
                System.out.println("Quieres cancelar la carga? ");
                datosValidos = confirmacion(scanner);
                if (datosValidos) {
                    System.out.println("Carga cancelada.💡");
                    return null;
                }
            }
        }
        System.out.println("Datos confirmados...");
        return new Datos_Personales(nombre, apellido, dni);
    }

    /**
     * Carga un administrador desde la entrada estándar (consola).
     * Se supone que se utilizara si se quiere guardar un Administrador -> Usuario a
     * la base de datos.
     * Dentro del proceso luego de confirmar Datos_Personales primero los carga en
     * la base da datos. De alguna manera si o si se tiene que lograr la carga sino
     * puede generar errores futuros.
     * Toma todos los requerimientos de validaciones.
     * Se puede cancelar.
     * 
     * @author Grupo 4 - Taller de lenguajes II
     * @version 1.0
     * 
     * @param scanner
     * @return Administrador generado, si se cancela retorna null.
     */
    public Administrador cargaAdministrador(Scanner scanner, List<Usuario> listaUSuario) {
        System.out.println("[CARGA DE UN ADMINISTRADOR]");
        String nombreUsuario = "";
        String email = "";
        String contrasena = "";
        Datos_Personales dp = null;
        String rol = "ADMINISTRADOR";
        boolean datosValidos = false;

        // Carga de datos por teclado.
        while (!datosValidos) {
            dp = cargaDatosPersonales(scanner);
            if (dp == null) { // Si cancelo la carga de datos desde la carga de Datos_Personales
                return null;
            }
            System.out.print("Ingrese el nombre de usuario: ");
            nombreUsuario = scanner.nextLine();
            System.out.print("Ingrese el email: ");
            email = scanner.nextLine();
            while (correoExistente(email, listaUSuario)) {
                System.out.print("Email ya registrado. Ingrese otro email: ");
                email = scanner.nextLine();
                }
            while (!esFormatoEmailSimpleValido(email)) {
                System.out.print("Email inválido. Ingrese un email válido: ");
                email = scanner.nextLine();
            }
            System.out.print("Ingrese la contraseña: ");
            contrasena = scanner.nextLine();
            System.out.println("CONFIRMACION DE CARGA -> ADMINISTRADOR.");
            datosValidos = confirmacion(scanner);
            if (!datosValidos) {
                System.out.println("Quieres cancelar la carga? ");
                datosValidos = confirmacion(scanner);
                if (datosValidos) {
                    System.out.println("Carga cancelada.💡");
                    return null;
                }
            }
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
     * la base da datos. De alguna manera si o si se tiene que lograr la carga sino
     * puede generar errores futuros.
     * Toma todos los requerimientos de validaciones.
     * Se puede cancelar.
     * 
     * @author Grupo 4 - Taller de lenguajes II
     * @version 1.0
     * 
     * @param scanner
     * @return Cuenta generado, si se cancela retorna null.
     */
    public Cuenta cargaCuenta(Scanner scanner, List<Usuario> listaUSuario) {
        System.out.println("[CARGA DE UNA CUENTA]");
        String nombreUsuario = "";
        String email = "";
        String contrasena = "";
        Datos_Personales dp = null;
        String rol = "CUENTA";
        boolean datosValidos = false;

        // Carga de datos por teclado.
        while (!datosValidos) {
            dp = cargaDatosPersonales(scanner);
            if (dp == null) { // Si cancelo la carga de datos desde la carga de Datos_Personales
                return null;
            }
            System.out.print("Ingrese el nombre de usuario: ");
            nombreUsuario = scanner.nextLine();
            System.out.print("Ingrese el email: ");
            email = scanner.nextLine();
            while (correoExistente(email, listaUSuario)) {
                System.out.print("Email ya registrado. Ingrese otro email: ");
                email = scanner.nextLine();
                }
            while (!esFormatoEmailSimpleValido(email)) {
                System.out.print("Email inválido. Ingrese un email válido: ");
                email = scanner.nextLine();
            }
            System.out.print("Ingrese la contraseña: ");
            contrasena = scanner.nextLine();
            System.out.println("CONFIRMACION DE CARGA -> CUENTA.");
            datosValidos = confirmacion(scanner);
            if (!datosValidos) {
                System.out.println("Quieres cancelar la carga? ");
                datosValidos = confirmacion(scanner);
                if (datosValidos) {
                    System.out.println("Carga cancelada.💡");
                    return null;
                }
            }
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
     * @author Grupo 4 - Taller de lenguajes II
     * @version 1.0
     * 
     * @param email El correo electrónico (String) a verificar.
     * @return {@code true} si el email cumple las condiciones simples,
     *         {@code false} en caso contrario.
     */
    private boolean esFormatoEmailSimpleValido(String email) {
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

    /**
     * Verifica si un correo electrónico ya está registrado en la base de datos.
     * Siempre suponemos que un correo no se puede ingresar dos veces por lo que
     * a la primera coincidencia retorna true.
     * 
     * @param correo       El correo a validar.
     * @param listaUsuario Suponemos que lo envia la plataforma actualizada!
     * @return true si el correo está registrado, false en caso
     *         contrario.
     */
    private boolean correoExistente(String correo, List<Usuario> listaUSuario) {
        // Maneja el caso de que la lista sea nula.
        if (listaUSuario == null) {
            System.out.println("Error: No se pudo obtener la lista de usuarios para validar.");
            return false;
        }
        // Busca coincidencia.
        for (Usuario usuario : listaUSuario) {
            if (usuario.getEmail() != null && usuario.getEmail().equals(correo)) {
                return true;
            }
        }
        // Si no encontro.
        return false;
    }

    /**
     * Verifica si una cadena de texto contiene únicamente letras (mayúsculas y/o
     * minúsculas).
     * 
     * @author Grupo 4 - Taller de lenguajes II
     * @version 1.0
     * 
     * @param texto La cadena de texto a verificar.
     * @return {@code true} si la cadena contiene solo letras , {@code false} en
     *         caso contrario.
     */
    private boolean contieneSoloLetras(String texto) {
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
     * Solicita al usuario la confirmación de los datos ingresados.
     * 
     * @author Grupo 4 - Taller de lenguajes II
     * @version 1.0
     * 
     * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
     * @return {@code true} si el usuario confirma los datos, {@code false} si desea
     *         reingresar los datos.
     */
    private boolean confirmacion(Scanner scanner) {
        System.out.print(" (S/N): ");
        String confirmacion = scanner.nextLine();
        while (!confirmacion.equalsIgnoreCase("S") && !confirmacion.equalsIgnoreCase("N")) {
            System.out.print("Entrada inválida. Ingrese 'S' para confirmar o 'N' para denegar: ");
            confirmacion = scanner.nextLine();
        }
        return confirmacion.equalsIgnoreCase("S");
    }
}
