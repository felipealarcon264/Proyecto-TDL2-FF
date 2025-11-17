package controlador;

import modelo.ente.Usuario;
import servicio.ServicioPelicula;
import servicio.ServicioUsuario;
import vista.VistaLogin;

import control.Aplicacion;
import javax.swing.SwingWorker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador del Login.
 * Implementa ActionListener para escuchar los botones de la Vista.
 * Conecta la Vista (VistaLogin) con el Modelo (ServicioUsuario).
 */
public class ControladorLogin implements ActionListener {

    private final VistaLogin vista;
    private final ServicioUsuario servicioUsuario;
    private final ServicioPelicula servicioPelicula;

    /**
     * Constructor que recibe la Vista y el Modelo.
     * Se registra a sí mismo (this) para escuchar los botones de la vista.
     */
    public ControladorLogin(VistaLogin vista, ServicioUsuario servicioUsuario, ServicioPelicula servicioPelicula) {
        this.vista = vista;
        this.servicioUsuario = servicioUsuario;
        this.servicioPelicula = servicioPelicula;

        // Se suscribe a los eventos (clics) de los botones en la Vista
        this.vista.getBotonIngresar().addActionListener(this);
        this.vista.getBotonRegistrarse().addActionListener(this);
    }

    /**
     * Este método se llama automáticamente cuando se hace clic en un botón.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Determinamos que boton se presiona.
        Object botonPresionado = e.getSource();

        if (botonPresionado == vista.getBotonIngresar()) {
            procesarIngresoCuenta();
            vista.limpiarCampos();
        } else if (botonPresionado == vista.getBotonRegistrarse()) {
            vista.limpiarCampos();
            Aplicacion.mostrarVista("REGISTRO");
        }
    }

    /**
     * Lógica para manejar el clic en "Ingresar".
     * Desde este metodo podemos ingresar a otra vista con mostrarVista!
     */
    private void procesarIngresoCuenta() {
        // Se obtienen los datos de pantalla(Vista).
        String correo = vista.getEmail();
        String contraseña = vista.getPassword();

        // Valida el usuario y que rol tiene.
        Usuario usuarioLogueado = servicioUsuario.DelvolverTipoUsuario(correo, contraseña);
        if (usuarioLogueado == null) {
            vista.mostrarError("Correo o contraseña incorrectos.");
        } else {
            // Se puede escalar para abrir una ventana en caso de ser adm.
            // No corresponde al entregable 3.

            if (usuarioLogueado.getRol().equals("CUENTA")) {
                System.out.println("¡El ingreso del Usuario " +
                        usuarioLogueado.getNombreUsuario() + " ha sido exitoso!");

                // 1. Mostramos la pantalla de carga INMEDIATAMENTE.
                Aplicacion.mostrarVista("CARGA");

                // 2. Creamos un SwingWorker para hacer el trabajo pesado en otro hilo.
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        // ESTO SE EJECUTA EN SEGUNDO PLANO (NO CONGELA LA PANTALLA DE CARGA)
                        // Creamos el ControladorHome, que carga la DB y las películas.
                        new ControladorHome(Aplicacion.vistaHome, servicioPelicula, usuarioLogueado);
                        return null;
                    }

                    @Override
                    protected void done() {
                        // ESTO SE EJECUTA DE VUELTA EN EL HILO DE LA INTERFAZ CUANDO doInBackground TERMINA
                        // Ahora que todo está cargado, mostramos la vista Home.
                        Aplicacion.mostrarVista("HOME");
                    }
                };

                // 3. ¡Iniciamos el trabajador!
                worker.execute();

            } else {
                /**
                 * Para el ADMINISTRADOR no se requiere de una interfaz gráfica basándonos en el
                 * entregable 3
                 */
                System.out.println(
                        "¡El ingreso del Administrador(a) " + usuarioLogueado.getNombreUsuario() + " ha sido exitoso!");
            }
        }
    }

    /*
     * MOMENTANEAMENTE NO SE USA
     * 
     * 
     * Lógica para manejar el clic en "Registrarse".
     * 
     * @author Grupo 4 - Proyecto TDL2
     * 
     * @version 1.0
     * 
     * private void procesarRegistroCuenta() {
     * System.out.println("Botón 'Registrarse' presionado.");
     * vista.VistaRegistro vistaRegistro = new vista.VistaRegistro();
     * // Reutilizamos el servicio de usuario que ya teníamos
     * new ControladorRegistro(vistaRegistro, this.servicio);
     * 
     * // Se crea la ventana con la vista registro.
     * JFrame ventanaRegistro = new JFrame("Plataforma TDL2 - Nuevo Usuario");
     * ventanaRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     * ventanaRegistro.add(vistaRegistro);
     * ventanaRegistro.pack(); // Ajusta al tamaño de VistaRegistro
     * ventanaRegistro.setLocationRelativeTo(null); // Centra
     * ventanaRegistro.setResizable(false);
     * ventanaRegistro.setVisible(true);
     * 
     * // 3. Cerrar la ventana de Login actual
     * javax.swing.SwingUtilities.getWindowAncestor(vista).dispose();
     * }
     */
}