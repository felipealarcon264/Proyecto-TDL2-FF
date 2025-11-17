package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.Aplicacion;
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
     * Logica para manejar el clic "Registrarme"
     * Para volver a la ventana de Login lo tenemos que hacer desde fuera del
     * metodo.
     * 
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
            // 4. ATRAPAMOS las excepciones de negocio y las mostramos
            vistaRegistro.mostrarError(e.getMessage());
            return false;

        } catch (Exception e_gen) {
            // 5. Atrapamos cualquier otro error inesperado (BD desconectada)
            vistaRegistro.mostrarError("Ocurrió un error inesperado: " + e_gen.getMessage());
            return false;
        }
    }

}
