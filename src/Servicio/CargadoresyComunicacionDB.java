package Servicio;

import java.util.Scanner;

import Entes.Administrador;
import Entes.Cuenta;
import Entes.Datos_Personales;
import Entes.Usuario;
import Enums.Genero;
import DAO.PeliculaDAOImpl;
import DAO.UsuarioDAOImpl;
import DAO.Datos_PersonalesDAOImpl;

import java.util.List;
import Catalogo.Pelicula;
import Catalogo.Resenia;

/**
 * CARGADORES Y COMUNICACION CON BASE DE DATOS CON DICHOS CARGADORES.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.1
 * 
 */
public class CargadoresyComunicacionDB {
    /*
     * Ingresar un usuario y una contraseña que el sistema valida.
     * -
     * Si la validación es exitosa, mostrar un listado numerado con los títulos de
     * las
     * películas disponibles.
     * -
     * El usuario indica un número de película válido.
     * -
     * A continuación se solicitan todos los datos de la reseña (calificación,
     * comentario).
     * -
     * Si el usuario confirma, se guarda en la base de datos.
     */

    /**
     * Carga de una resenia por teclado.
     * Importante se le envia una lista de peliculas/contenidos para decidir a cual
     * hacer la resenia.
     * se asegura que el indice seleccionado sea valido.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * @param scanner        El Scanner para leer la entrada del usuario.
     * @param listaPelicula La lista de peliculas disponibles para reseñar.
     * @param usuario        El usuario que está realizando la reseña.
     * @return Un objeto {@link Resenia} con los datos cargados, o null si el
     *         usuario cancela la operación o la lista de películas está vacía.
     */    
    public Resenia cargaResenia(Scanner scanner, Usuario usuario) {
        String comentario;
        int seleccionPelicula;
        List<Pelicula> listaPelicula = new PeliculaDAOImpl().devolverListaPelicula(); // Se obtiene la lista de la DB
        System.out.println("\n--- ✍️ Carga de Reseña ✍️ ---");
        if (listaPelicula == null || listaPelicula.isEmpty()) {
            System.out.println("ℹ️ No hay películas disponibles para reseñar.");
            return null;
        }
        for (int i = 0; i < listaPelicula.size(); i++) {
            System.out.println((i + 1) + ". " + listaPelicula.get(i).getTitulo());
        }
        System.out.println();// Espacio.

        // Bucle para asegurar que el número de película esté en el rango correcto.
        do {
            seleccionPelicula = this.ingresarNumeroValido(scanner,
                    "👉 Ingrese el número de la película (1 a " + listaPelicula.size() + "): ");
            if (seleccionPelicula < 1 || seleccionPelicula > listaPelicula.size()) {
                System.out.println("❌ Número fuera de rango. Intente de nuevo.");
            }
        } while (seleccionPelicula < 1 || seleccionPelicula > listaPelicula.size());

        // Indice real en la lista.
        int indiceSeleccionado = seleccionPelicula - 1;
        System.out.println("Resenia para la pelicula [" + listaPelicula.get(indiceSeleccionado).getTitulo() + "]");
        int calificacion;
        do {
            calificacion = this.ingresarNumeroValido(scanner, "Ingrese la calificacion (0-5): ");
            if (calificacion < 0 || calificacion > 5) {
                System.out.println("❌ Calificación fuera de rango. Debe ser entre 0 y 5. Intente de nuevo.");
            }
        } while (calificacion < 0 || calificacion > 5); //Calificaciones 0-5
        System.out.println("Ingrese el comentario: ");
        comentario = scanner.nextLine();
        System.out.println("\n--- Confirmación de Carga: Reseña ---");
        System.out.println("Datos ingresados:" +
                "\nCalificacion: " + calificacion +
                "\nComentario: " + comentario);
        boolean datosValidos = confirmacion(scanner);
        if (datosValidos)
            return new Resenia(-1,calificacion, comentario, 0, usuario, listaPelicula.get(indiceSeleccionado));
        else
            return null;
    }

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
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
     * @return Un objeto {@link Datos_Personales} con los datos ingresados, o null
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
        return new Datos_Personales(-1,nombre, apellido, dni); //Retorna un id no valido eso se asegura de brindarselo la base de datos.
    }

    /**
     * Carga un administrador desde la entrada estándar (consola).
     * Se supone que se utilizara si se quiere guardar un Administrador -> Usuario a
     * la base de datos.
     * Dentro del proceso luego de confirmar Datos_Personales primero los carga en
     * la base da datos. De alguna manera si o si se tiene que lograr la carga sino
     * puede generar errores futuros.
     * Toma todos los requerimientos de validaciones.
     * Los correos se guardan en minuscula.
     * Se puede cancelar.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @param scanner      El Scanner para leer la entrada del usuario.
     *                     existentes. (Ya no se pasa la lista)
     * @return Un objeto Administrador generado, o null si se cancela la carga.
     */
    public Administrador cargaAdministrador(Scanner scanner) {
        System.out.println("[CARGA DE UN ADMINISTRADOR]");
        UsuarioDAOImpl usrDAO = new UsuarioDAOImpl(); // Para validar correo
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
            email = scanner.nextLine().toLowerCase();
            while (correoExistente(email, usrDAO.devolverListaUsuarios())) { // Se consulta la DB
                System.out.print("Email ya registrado. Ingrese otro email: ");
                email = scanner.nextLine().toLowerCase();
            }
            while (!esFormatoEmailSimpleValido(email)) {
                System.out.print("Email inválido. Ingrese un email válido: ");
                email = scanner.nextLine().toLowerCase();
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
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @param scanner      El Scanner para leer la entrada del usuario.
     *                     existentes. (Ya no se pasa la lista)
     * @return Un objeto Cuenta generado, o null si se cancela la carga.
     */
    public Cuenta cargaCuenta(Scanner scanner) {
        System.out.println("[CARGA DE UNA CUENTA]");
        UsuarioDAOImpl usrDAO = new UsuarioDAOImpl(); // Para validar correo
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
            email = scanner.nextLine().toLowerCase();
            while (correoExistente(email, usrDAO.devolverListaUsuarios())) { // Se consulta la DB
                System.out.print("Email ya registrado. Ingrese otro email: ");
                email = scanner.nextLine().toLowerCase();
            }
            while (!esFormatoEmailSimpleValido(email)) {
                System.out.print("Email inválido. Ingrese un email válido: ");
                email = scanner.nextLine().toLowerCase();
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
     * Carga una Pelicula desde la entrada estándar (consola).
     * Permite al usuario ingresar los datos y confirmarlos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param scanner El Scanner para leer la entrada del usuario.
     * @return Una pelicula o null en caso de cancelar la carga.
     */
    public Pelicula cargaPelicula(Scanner scanner) {
        System.out.println(" 🎬CARGA DE PELICULA🎬");
        // titulo resumen director duracion genero
        String titulo = "";
        String resumen = "";
        String director = "";
        int duracion = -1;
        Genero genero = null;
        boolean datosValidos = false;
        while (!datosValidos) {
            System.out.print("Ingrese el titulo: ");
            titulo = scanner.nextLine();
            System.out.print("Ingrese el resumen: ");
            resumen = scanner.nextLine();
            System.out.print("Ingrese el director: ");
            director = scanner.nextLine();
            duracion = ingresarNumeroValido(scanner,"Ingrese la duracion: ");
            genero = seleccionarGenero(scanner);
            System.out.println("CONFIRMACION DE CARGA -> PELICULA.");
            System.out.println("Datos ingresados:" +
                    "\nTitulo: " + titulo +
                    "\nResumen: " + resumen +
                    "\nDirector: " + director +
                    "\nDuracion: " + duracion +
                    "\nGenero: " + genero);
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
        // Pasamos -1 como ID temporal, ya que la DB asignará el real.
        return new Pelicula(-1, titulo, director, duracion, resumen, genero);
    }

    /**
     * Solicita al usuario la confirmación de los datos ingresados.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
     * @return true si el usuario confirma, false en caso contrario.
     */
    public boolean confirmacion(Scanner scanner) {
        System.out.print(" (S/N): ");
        String confirmacion = scanner.nextLine();
        while (!confirmacion.equalsIgnoreCase("S") && !confirmacion.equalsIgnoreCase("N")) {
            System.out.print("Entrada inválida. Ingrese 'S' para confirmar o 'N' para denegar: ");
            confirmacion = scanner.nextLine();
        }
        return confirmacion.equalsIgnoreCase("S");
    }

    /**
     * Solicita al usuario que seleccione un género de la lista de forma segura.
     * El método mostrará un menú y se repetirá indefinidamente hasta que el
     * usuario ingrese una opción válida (un número del 1 al 5).
     * 
     * Lee la entrada como un String para prevenir errores de tipo .
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param scanner El Scanner para leer la entrada del usuario.
     * @return El enum Genero seleccionado por el usuario.
     */
    private Genero seleccionarGenero(Scanner scanner) {

        // Bucle infinito que solo se sale cuando hay un 'return'
        while (true) {
            System.out.println("\n--- Seleccione un Género ---");
            System.out.println("1. Acción");
            System.out.println("2. Anime");
            System.out.println("3. Drama");
            System.out.println("4. Comedia");
            System.out.println("5. Terror");
            System.out.print("Ingrese su opción (1-5): ");

            // 1. Leer la entrada SIEMPRE como String para evitar errores
            String opcion = scanner.nextLine();

            // 2. Usar un 'switch' para evaluar el String
            switch (opcion) {
                case "1":
                    return Genero.ACCION; // Opción válida, salimos del método
                case "2":
                    return Genero.ANIME;
                case "3":
                    return Genero.DRAMA;
                case "4":
                    return Genero.COMEDIA;
                case "5":
                    return Genero.TERROR;

                // 3. Si no coincide con 1-5, se ejecuta el 'default'
                default:
                    System.out.println("-------------------------------------------------");
                    System.out.println("Error: Opción no válida. Debe ingresar un número del 1 al 5.");
                    System.out.println("-------------------------------------------------");
                    // El bucle 'while(true)' vuelve a empezar
            }
        }
    }

    /**
     * Verifica de forma simple si un correo electrónico tiene un formato básico
     * válido:
     * contiene exactamente un '@' y este no es el último carácter.
     * No valida la estructura completa xxx@yyy.zzz.
     * HECHO TOTALMENTE CON IA.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @param email El correo electrónico (String) a verificar.
     * @return true si el email cumple las condiciones simples, false en caso
     *         contrario.
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
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param correo       El correo a validar.
     * @param listaUSuario Suponemos que lo envia la plataforma actualizada!
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
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @param texto La cadena de texto a verificar.
     * @return true si la cadena contiene solo letras, false en caso contrario.
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
     * Pide al usuario que ingrese un número entero y valida la entrada.
     * Pide reintentar si se ingresa algo que no es un número.
     * 
     * @author Gemini.
     * @version 1.0.
     * 
     * @param scanner El objeto Scanner ya inicializado.
     * @param mensaje El mensaje a mostrar al usuario para solicitar la entrada.
     * @return El número entero válido ingresado por el usuario.
     */
    public int ingresarNumeroValido(Scanner scanner, String mensaje) {
        int numero;
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine(); // Leer siempre la línea completa.
            try {
                numero = Integer.parseInt(linea); // Intentar convertir la línea a entero.
                return numero; // Si tiene éxito, devolver el número y salir del método.
            } catch (NumberFormatException e) {
                // Si la conversión falla, es porque no se ingresó un número válido.
                System.out.println("❌ Entrada no válida. Por favor, ingrese solo números enteros.");
            }
        }
    }

    /**
     * Pide al usuario que ingrese un número entero y valida que esté en un rango.
     * Pide reintentar si se ingresa algo que no es un número o está fuera de rango.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param scanner El objeto Scanner ya inicializado.
     * @param mensaje El mensaje a mostrar al usuario.
     * @param min     El valor mínimo inclusivo.
     * @param max     El valor máximo inclusivo.
     * @return El número entero válido ingresado por el usuario.
     */
    public int ingresarNumeroValido(Scanner scanner, String mensaje, int min, int max) {
        int numero;
        while (true) {
            numero = ingresarNumeroValido(scanner, mensaje);
            if (numero >= min && numero <= max) {
                return numero;
            }
            System.out.println("❌ Número fuera de rango. Debe ser entre " + min + " y " + max + ". Intente de nuevo.");
        }
    }
}
