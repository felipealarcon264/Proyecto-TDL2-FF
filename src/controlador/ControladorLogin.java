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
            if (procesarIngresoCuenta())
                vista.limpiarCampos();//Solo limpia si ingresamos bien los datos.
        } else if (botonPresionado == vista.getBotonRegistrarse()) {
            vista.limpiarCampos();
            Aplicacion.mostrarVista("REGISTRO");
        }
    }

    /**
     * Lógica para manejar el clic en "Ingresar".
     * Desde este metodo podemos ingresar a otra vista con mostrarVista!
     */
    private boolean procesarIngresoCuenta() {
        // Se obtienen los datos de pantalla(Vista).
        String correo = vista.getEmail();
        String contraseña = vista.getPassword();

        // Valida el usuario y que rol tiene.
        Usuario usuarioLogueado = servicioUsuario.DelvolverUsuario(correo, contraseña);
        if (usuarioLogueado == null) {
            vista.mostrarError("Correo o contraseña incorrectos.");
            return false;
        } else {
            // Se puede escalar para abrir una ventana en caso de ser adm.
            // No corresponde al entregable 3.

            if (usuarioLogueado.getRol().equals("CUENTA")) {
                System.out.println("¡El ingreso del Usuario " +
                        usuarioLogueado.getNombreUsuario() + " ha sido exitoso!");

                // 1. Mostramos la pantalla de carga INMEDIATAMENTE.
                Aplicacion.mostrarVista("CARGA");

                // 2. Creamos un SwingWorker para hacer el trabajo pesado en otro hilo.
                SwingWorker<ControladorHome, Void> worker = new SwingWorker<ControladorHome, Void>() {
                    @Override
                    protected ControladorHome doInBackground() throws Exception {
                        // --- INICIO SECCIÓN DE CAMBIO: LÓGICA DE ESPERA ---

                        long tiempoInicio = System.currentTimeMillis(); // Tomamos la hora de inicio

                        // Tarea Pesada: Cargar el controlador y la DB
                        ControladorHome controladorHome = new ControladorHome(Aplicacion.vistaHome, servicioPelicula, usuarioLogueado,
                                Aplicacion.ventana);

                        long tiempoFin = System.currentTimeMillis();
                        long duracion = tiempoFin - tiempoInicio;

                        // Lógica de Espera: Si fue muy rápido, esperamos lo que falta para completar
                        // mínimoTiempo
                        final int mínimoTiempo = 2000; // 2seg
                        if (duracion < mínimoTiempo) {
                            Thread.sleep(mínimoTiempo - duracion);
                        }
                        return controladorHome;
                    }

                    @Override
                    protected void done() {
                        try {
                            // Recuperamos el controlador creado en el hilo de fondo
                            ControladorHome controladorHome = get();

                            // Cambiamos la vista a HOME
                            Aplicacion.mostrarVista("HOME");

                            // Pequeño truco: SwingUtilities.invokeLater asegura que el cartel
                            // salte DESPUÉS de que la interfaz termine de pintarse por completo.
                            javax.swing.SwingUtilities.invokeLater(() -> {
                                controladorHome.mostrarBienvenidaUsuarioNuevo();
                            });

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
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
        return true;
    }
}