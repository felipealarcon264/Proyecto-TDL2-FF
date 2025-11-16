package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import servicio.ServicioUsuario; // Usaremos el servicio
import vista.VistaRegistro;
import excepciones.DatosInvalidosException;
import excepciones.DniYaRegistradosException;
import excepciones.EmailYaRegistradoException;

public class ControladorRegistro implements ActionListener {
    private final VistaRegistro vistaRegistro;
    private final ServicioUsuario servicioUsuario;

    public ControladorRegistro(VistaRegistro vistaRegistro, ServicioUsuario servicioUsuario) {
        this.vistaRegistro = vistaRegistro;
        this.servicioUsuario = servicioUsuario;
        this.vistaRegistro.getBotonRegistrar().addActionListener(this);
        this.vistaRegistro.getBotonVolver().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object botonPresionado = e.getSource();
        if (botonPresionado == vistaRegistro.getBotonRegistrar()) {
            manejarBotonRegistro();
        } else if (botonPresionado == vistaRegistro.getBotonVolver()) {
            manejarBotonVolver();
        }
    }

    /**
     * Logica para manejar el clic "Registrarme"
     */
    private void manejarBotonRegistro() {
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

            // Volvemos al Login
            manejarBotonVolver();

        } catch (DatosInvalidosException | DniYaRegistradosException | EmailYaRegistradoException e) {
            // 4. ATRAPAMOS las excepciones de negocio y las mostramos
            vistaRegistro.mostrarError(e.getMessage());

        } catch (Exception e_gen) {
            // 5. Atrapamos cualquier otro error inesperado (BD desconectada)
            vistaRegistro.mostrarError("Ocurrió un error inesperado: " + e_gen.getMessage());
        }
    }

    private void manejarBotonVolver() {
        // Cierra la ventana de Registro
        JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(vistaRegistro);
        ventanaActual.dispose();
        control.Aplicacion.main(null);
    }
}
