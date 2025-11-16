package servicio;

import comparadores.ComparadorUsuarioPorEmail;
import comparadores.ComparadorUsuarioPorNombreUsuario;

import dao.FactoryDAO;
import dao.interfaces.Datos_PersonalesDAO;
import dao.interfaces.UsuarioDAO;

import modelo.ente.Administrador;
import modelo.ente.Cuenta;
import modelo.ente.Datos_Personales;
import modelo.ente.Usuario;

import excepciones.DniYaRegistradosException;
import excepciones.EmailYaRegistradoException;
import excepciones.DatosInvalidosException;

import java.util.List;
import java.util.Scanner;

public class ServicioUsuario {
    // usa las interfaces;
    UsuarioDAO usuarioDAO;
    Datos_PersonalesDAO datosPersonalesDAO;

    public ServicioUsuario() {
        this.usuarioDAO = FactoryDAO.getUsuarioDAO();
        this.datosPersonalesDAO = FactoryDAO.getDatosPersonalesDAO();
    }

    /**
     * Busca en la base de datos que tipo de Usuario ingresa a la plataforma
     * (Administrador o Cuenta).
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     *
     * @param correo     El correo a verificar.
     * @param contraseña La contraseña a verificar.
     * @return el tipo de Usuario.
     */
    public Usuario DelvolverTipoUsuario(String correo, String contraseña) {
        return usuarioDAO.buscarPorEmailyContrasena(correo, contraseña);
    }

    /**
     * Crea y guarda una nueva Cuenta y sus Datos Personales.
     * Este método es llamado por el ControladorRegistro (GUI).
     * Lanza excepciones personalizadas si la validación falla.
     *
     * @param nombre   Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param dniStr   DNI (como String, para ser validado).
     * @param email    Email (debe ser único).
     * @param password Contraseña.
     * @throws DatosInvalidosException    Si los campos están vacíos o el DNI no es
     *                                    un número.
     * @throws DniYaRegistradosException  Si el DNI ya existe.
     * @throws EmailYaRegistradoException Si el email ya existe.
     */
    public void crearNuevaCuenta(String nombre, String apellido, String dniStr, String nomUsr, String email,
            String password)
            throws DatosInvalidosException, DniYaRegistradosException, EmailYaRegistradoException {

        // Validacion de datos vacios.
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || dniStr.trim().isEmpty() ||
                email.trim().isEmpty() || password.isEmpty()) {
            throw new DatosInvalidosException("Todos los campos son obligatorios.");
        }

        // Validacion de nombre y apellido valido.
        if (!(new ServicioDatos_Personales().contieneSoloLetras(nombre))) {
            new DatosInvalidosException("Nombre invalido.");
        }
        if (!(new ServicioDatos_Personales().contieneSoloLetras(apellido))) {
            new DatosInvalidosException("Apellido invalido.");
        }

        // Validacion formato DNI y unicidad.
        int dni;
        try {
            dni = Integer.parseInt(dniStr.trim());
            if (datosPersonalesDAO.buscarPorDNI(dni) != null)// DNI unico.
                throw new DniYaRegistradosException("El DNI " + dni + " ya está registrado.");
        } catch (NumberFormatException ex) {
            throw new DatosInvalidosException("El DNI debe ser un número válido.");
        }

        // Validacion de unicidad del nombre de usuario.
        if (nombreUsrExistente(nomUsr, usuarioDAO.devolverListaUsuarios()))
            throw new DatosInvalidosException("El nombre de usuario " + nomUsr + " ya está registrado.");

        // Validacion formato E-Mail y unicidad.
        if (!esFormatoEmailSimpleValido(email))
            throw new DatosInvalidosException("Formato de correo invalido.");
        else if (usuarioDAO.buscarPorEmail(email) != null)
            throw new EmailYaRegistradoException("El email " + email + " ya está registrado.");

        // Si todo esta correcto se guarda
        Datos_Personales dp = new Datos_Personales(-1, nombre, apellido, dni);
        Cuenta cta = new Cuenta(-1, nomUsr, email, password, dp, "CUENTA");

        boolean exito = usuarioDAO.guardar(cta);

        if (!exito) {
            // Un error genérico si la BD falla por otra razón
            throw new RuntimeException("Error desconocido al guardar en la base de datos.");
        }
    }

    /**
     * Verifica si un correo electrónico ya está registrado en la base de datos.
     * Siempre suponemos que un correo no se puede ingresar dos veces por lo que
     * a la primera coincidencia retorna true.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     *
     * @param correo     El correo a validar.
     * @param contrasena La contraseña a validar.
     * @return true si el correo está registrado, false en caso
     *         contrario.
     */
    public boolean verificarUsuario(String correo, String contrasena) {
        // Se obtiene la lista directamente de la DB para validar
        List<Usuario> listaUsuario = usuarioDAO.devolverListaUsuarios();
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
     * Reemplazo de agregarUsuario.
     * Utiliza la consola para ingresar los datos.
     * Crea un usario y lo carga en la base de datos.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     *
     * @param scanner El Scanner para leer la entrada del usuario.
     */
    public void cargarYguardarAdministrador(Scanner scanner) {
        Administrador adm = cargaAdministrador(scanner);
        if (adm == null) {
            System.out.println("No se guardo nada en la base de datos.");
            return;
        }
        usuarioDAO.guardar(adm); // El DAO ya imprime el mensaje de éxito.
    }

    /**
     * Carga un administrador desde la entrada estándar (consola).
     * Se supone que se utilizara si se quiere guardar un Administrador -> Usuario a
     * la base de datos.
     * Dentro del proceso luego de confirmar Datos_Personales primero los carga en
     * la base da datos. De alguna manera si o si se tiene que lograr la carga sino
     * puede generar errores futuros.
     * Los correos se guardan en minuscula.
     * Se puede cancelar.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     *
     * @param scanner El Scanner para leer la entrada del usuario.
     *                existentes. (Ya no se pasa la lista)
     * @return Un objeto Administrador generado, o null si se cancela la carga.
     */
    public Administrador cargaAdministrador(Scanner scanner) {
        System.out.println("[CARGA DE UN ADMINISTRADOR]");
        String nombreUsuario = "";
        String email = "";
        String contrasena = "";
        Datos_Personales dp = null;
        String rol = "ADMINISTRADOR";
        boolean datosValidos = false;

        // Carga de datos por teclado.
        while (!datosValidos) {
            dp = new ServicioDatos_Personales().cargaDatosPersonales(scanner);
            if (dp == null) { // Si cancelo la carga de datos desde la carga de Datos_Personales
                return null;
            }
            System.out.print("Ingrese el nombre de usuario: ");
            nombreUsuario = scanner.nextLine();
            System.out.print("Ingrese el email: ");
            email = scanner.nextLine().toLowerCase();
            while (correoExistente(email, usuarioDAO.devolverListaUsuarios())) { // Se consulta la DB
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
     * Crea un usario y lo carga en la base de datos.
     * Utiliza la consola para ingresar los datos.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     *
     * @param scanner Entrada por teclado.
     *
     */
    public void cargarYguardarCuenta(Scanner scanner) {
        Cuenta cta = cargaCuenta(scanner);
        if (cta == null) {
            System.out.println("No se guardo nada en la base de datos.");
            return;
        }
        usuarioDAO.guardar(cta); // El DAO ya imprime el mensaje de éxito.
    }

    /**
     * Carga una Cuenta desde la entrada estándar (consola).
     * Se supone que se utilizara si se quiere guardar una Cuenta -> Usuario a la
     * base de datos.
     * Dentro del proceso luego de confirmar Datos_Personales primero los carga en
     * la base da datos. De alguna manera si o si se tiene que lograr la carga sino
     * puede generar errores futuros.
     * Se puede cancelar.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     *
     * @param scanner El Scanner para leer la entrada del usuario.
     *                existentes. (Ya no se pasa la lista)
     * @return Un objeto Cuenta generado, o null si se cancela la carga.
     */
    public Cuenta cargaCuenta(Scanner scanner) {
        System.out.println("[CARGA DE UNA CUENTA]");
        String nombreUsuario = "";
        String email = "";
        String contrasena = "";
        Datos_Personales dp = null;
        String rol = "CUENTA";
        boolean datosValidos = false;

        // Carga de datos por teclado.
        while (!datosValidos) {
            dp = new ServicioDatos_Personales().cargaDatosPersonales(scanner);
            if (dp == null) { // Si cancelo la carga de datos desde la carga de Datos_Personales
                return null;
            }
            System.out.print("Ingrese el nombre de usuario: ");
            nombreUsuario = scanner.nextLine();
            System.out.print("Ingrese el email: ");
            email = scanner.nextLine().toLowerCase();
            while (correoExistente(email, usuarioDAO.devolverListaUsuarios())) { // Se consulta la DB
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
     * Pregunta por pantalla que manera de ordenacion de la lista de Usuarios se
     * requiere.
     * Lo muestra por consola.
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
        List<Usuario> listaUsuario = usuarioDAO.devolverListaUsuarios(); // Se obtiene la lista de la DB

        while (true) {
            String opcion = in.nextLine();
            switch (opcion) {
                case "1":
                    ComparadorUsuarioPorEmail comparadorPorEmail = new ComparadorUsuarioPorEmail();
                    listaUsuario.sort(comparadorPorEmail);
                    System.out.println("✅ Lista de usuarios ordenada por email.");
                    for (int i = 0; i < listaUsuario.size(); i++) {
                        System.out.println("\n--- Película N°" + (i + 1) + " ---");
                        System.out.println(listaUsuario.get(i));
                    } // Mostramos la lista ordenada
                    return;
                case "2":
                    ComparadorUsuarioPorNombreUsuario comparadorPorNombreUsuario = new ComparadorUsuarioPorNombreUsuario();
                    listaUsuario.sort(comparadorPorNombreUsuario);
                    System.out.println("✅ Lista de usuarios ordenada por nombre de usuario.");
                    for (int i = 0; i < listaUsuario.size(); i++) {
                        System.out.println("\n--- Película N°" + (i + 1) + " ---");
                        System.out.println(listaUsuario.get(i));
                    } // Mostramos la lista ordenada
                    return;
                default:
                    System.out.print("❌ Error: Opción no válida. Intente de nuevo: ");
            }
        }
    }

    /**
     * Elimina un usuario existente de la base de datos.
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
        return usuarioDAO.borrar(usuario);
    }

    /**
     * Solicita al usuario la confirmación de los datos ingresados por consola.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     *
     * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
     * @return true si el usuario confirma, false en caso contrario.
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
        return arrobaIndex != email.length() - 1; // No hay nada después del '@'

        // 5. Si pasó todas las verificaciones, es válido (según estas reglas simples)
    }

    /**
     * Verifica si un correo electrónico ya está registrado en la base de datos.
     * Siempre suponemos que un correo no se puede ingresar dos veces por lo que
     * a la primera coincidencia retorna true.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     *
     * @param correo       El correo a validar.
     * @param listaUSuario Se pide a la base de datos.
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
     * Verifica si un nombre de usuario ya está registrado en la base de datos.
     * Siempre suponemos que un nombre de usuario es unico.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     *
     * @param nombreUsr    El nombre de usuario a validar.
     * @param listaUSuario Se pide a la base de datos.
     * @return true si el correo está registrado, false en caso
     *         contrario.
     */
    private boolean nombreUsrExistente(String nombreUsr, List<Usuario> listaUSuario) {
        // Maneja el caso de que la lista sea nula.
        if (listaUSuario == null) {
            System.out.println("Error: No se pudo obtener la lista de usuarios para validar.");
            return false;
        }
        // Busca coincidencia.
        for (Usuario usuario : listaUSuario) {
            if (usuario.getNombreUsuario() != null && usuario.getNombreUsuario().equals(nombreUsr)) {
                return true;
            }
        }
        // Si no encontro.
        return false;
    }

    public UsuarioDAO getUsuarioDao() {
        return usuarioDAO;
    }

    public void setUsuarioDao(UsuarioDAO usuarioDao) {
        this.usuarioDAO = usuarioDao;
    }
}
