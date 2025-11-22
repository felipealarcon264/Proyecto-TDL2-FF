package servicio;

import dao.FactoryDAO;
import dao.interfaces.Datos_PersonalesDAO;
import dao.interfaces.UsuarioDAO;
import modelo.ente.Cuenta;
import modelo.ente.Datos_Personales;
import modelo.ente.Usuario;
import excepciones.*;
import java.util.List;

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
    public Usuario DelvolverUsuario(String correo, String contraseña) {
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
            throws DatosInvalidosException, DniYaRegistradosException, EmailYaRegistradoException, CampoVacio {

        // Validacion de datos vacios.
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty() || dniStr.trim().isEmpty() || nomUsr.trim().isEmpty()
                || email.trim().isEmpty() || password.isEmpty()) {
            throw new CampoVacio("Todos los campos son obligatorios.");
        }

        // Validacion de nombre y apellido valido.
        if (!(new ServicioDatos_Personales().contieneSoloLetras(nombre))) {
            throw new DatosInvalidosException("Nombre invalido.");
        }
        if (!(new ServicioDatos_Personales().contieneSoloLetras(apellido))) {
            throw new DatosInvalidosException("Apellido invalido.");
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

        // Validacion de unicidad del nombre de usuario y tamaño.
        if (nombreUsrExistente(nomUsr, usuarioDAO.devolverListaUsuarios()))
            throw new DatosInvalidosException("El nombre de usuario " + nomUsr + " ya está registrado.");
        if (nomUsr.length() < 4)
            throw new DatosInvalidosException("El nombre de usuario debe tener al menos 4 caracteres.");
        if (nomUsr.length() > 20)
            throw new DatosInvalidosException("El nombre de usuario debe tener máximo 20 caracteres.");

        // Validacion formato E-Mail y unicidad.
        if (!esFormatoEmailSimpleValido(email))
            throw new DatosInvalidosException("Formato de correo invalido.");
        else if (usuarioDAO.buscarPorEmail(email) != null)
            throw new EmailYaRegistradoException("El email " + email + " ya está registrado.");

        // Si todo esta correcto se guarda
        Datos_Personales dp = new Datos_Personales(-1, nombre, apellido, dni);
        Cuenta cta = new Cuenta(-1, nomUsr, email.toLowerCase(), password, dp, "CUENTA", 1);
        // El E-Mail se guarda en minusculas.

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

    public void actualizarEstadoUsuario(Usuario usuario) {
        usuarioDAO.actualizar(usuario);
    }

    public UsuarioDAO getUsuarioDao() {
        return usuarioDAO;
    }

    public void setUsuarioDao(UsuarioDAO usuarioDao) {
        this.usuarioDAO = usuarioDao;
    }
}
