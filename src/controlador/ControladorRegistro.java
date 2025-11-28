//Verificacion JavaDoc -> Realizada.
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Aplicacion;
import servicio.ServicioUsuario; // Usaremos el servicio
import vista.VistaRegistro;
import excepciones.DatosInvalidosException;
import excepciones.DniYaRegistradosException;
import excepciones.EmailYaRegistradoException;

/**
 * Controlador para la vista de registro de nuevos usuarios.
 * Gestiona la lógica de creación de cuentas y la navegación.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ControladorRegistro implements ActionListener {
    private VistaRegistro vistaRegistro;
    private ServicioUsuario servicioUsuario;

    /**
     * Constructor del ControladorRegistro.
     * Inicializa la vista y el servicio, y asigna los listeners a los botones.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param vistaRegistro   La vista de registro (VistaRegistro) que este
     *                        controlador gestiona.
     * @param servicioUsuario El servicio para la lógica de negocio relacionada con
     *                        los usuarios.
     */
    public ControladorRegistro(VistaRegistro vistaRegistro, ServicioUsuario servicioUsuario) {
        this.vistaRegistro = vistaRegistro;
        this.servicioUsuario = servicioUsuario;
        this.vistaRegistro.getBotonRegistrar().addActionListener(this);
        this.vistaRegistro.getBotonVolver().addActionListener(this);
    }

    /**
     * Maneja los eventos de acción de los botones en la vista de registro.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param e El evento de acción que se ha producido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object botonPresionado = e.getSource();
        if (botonPresionado == vistaRegistro.getBotonRegistrar()) {
            // Si el registro es exitoso, limpiamos y volvemos al login.
            if (manejarBotonRegistro()) {
                vistaRegistro.limpiarCampos();
                Aplicacion.mostrarVista("LOGIN");
            }
            // Si no es exitoso, no hacemos nada más, para que el usuario pueda corregir.
        } else if (botonPresionado == vistaRegistro.getBotonVolver()) {
            vistaRegistro.limpiarCampos(); // Limpiamos al volver
            Aplicacion.mostrarVista("LOGIN");
        }
    }

    /**
     * Lógica para manejar el clic en el botón "Registrarme".
     * Recoge los datos de la vista, los valida a través del servicio de usuario
     * y maneja las posibles excepciones mostrando mensajes de error o éxito.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return true si el registro fue exitoso, false en caso de error.
     */
    private boolean manejarBotonRegistro() {
        // obtenemos los datos de la pantalla (vista).
        String nombre = vistaRegistro.getCampoNombre();
        String apellido = vistaRegistro.getCampoApellido();
        String dniStr = vistaRegistro.getCampoDNI();
        String nomUsr = vistaRegistro.getCampoNombreUsr();
        String correoElectronico = vistaRegistro.getCampoCorreoElectronico();
        String contrasenia = vistaRegistro.getCampoContraseña();

        // Usamos el Servicio para crear y guardar la cuenta.
        // (Modificaremos ServicioUsuario para que acepte los datos)
        // Usamos nuestas excepciones
        try {
            servicioUsuario.crearNuevaCuenta(nombre, apellido, dniStr, nomUsr, correoElectronico, contrasenia);

            // Si todo sale bien:
            vistaRegistro.mostrarExito("¡Cuenta creada exitosamente! Ya puedes iniciar sesión.");
            return true;

        } catch (DatosInvalidosException | DniYaRegistradosException | EmailYaRegistradoException e) {
            // ATRAPAMOS las excepciones de negocio y las mostramos
            vistaRegistro.mostrarError(e.getMessage());
            return false;

        } catch (Exception e_gen) {
            // Atrapamos cualquier otro error inesperado (BD desconectada)
            vistaRegistro.mostrarError("Ocurrió un error inesperado: " + e_gen.getMessage());
            return false;
        }
    }

}
