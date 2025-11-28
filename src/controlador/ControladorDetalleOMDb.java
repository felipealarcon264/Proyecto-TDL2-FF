////Verificacion JavaDoc -> Realizada.
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import modelo.catalogo.Pelicula;
import modelo.ente.Usuario;
import servicio.ServicioDetalleOMDb;
import servicio.ServicioResenia;
import vista.VistaDetalleOMDb;
import vista.VistaResenia;

/**
 * Controlador para la vista de detalles de una película obtenida de OMDb.
 * Gestiona las acciones del usuario, como cerrar la vista o iniciar el proceso
 * de crear una reseña.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ControladorDetalleOMDb implements ActionListener {

    private VistaDetalleOMDb vista;
    private ServicioDetalleOMDb servicioDetalleOMDb;
    private Pelicula PeliculaActual;
    private Usuario usrActual;
    private JFrame framePrincipal;

    /**
     * Constructor para el ControladorDetalleOMDb.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param vista               La vista de detalles de la película
     *                            (VistaDetalleOMDb).
     * @param servicioDetalleOMDb El servicio para gestionar la lógica de negocio de
     *                            los detalles de la película.
     * @param usrActual           El usuario que ha iniciado sesión.
     * @param PeliculaActual      La película cuyos detalles se están mostrando.
     * @param framePrincipal      El marco principal de la aplicación, usado para la
     *                            modalidad de los diálogos.
     */
    public ControladorDetalleOMDb(VistaDetalleOMDb vista, ServicioDetalleOMDb servicioDetalleOMDb, Usuario usrActual,
            Pelicula PeliculaActual, JFrame framePrincipal) {
        this.vista = vista;
        this.vista.getBotonCerrar().addActionListener(this);
        this.vista.getBotonMiResenia().addActionListener(this);
        this.servicioDetalleOMDb = servicioDetalleOMDb;
        this.PeliculaActual = PeliculaActual;
        this.usrActual = usrActual;
        this.framePrincipal = framePrincipal;
    }

    /**
     * Maneja los eventos de acción de los botones en la vista de detalles.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param e El evento de acción que se ha producido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonCerrar()) {
            // La lógica es simplemente cerrar la ventana
            vista.dispose();
        }
        if (e.getSource() == vista.getBotonMiResenia()) {
            // 1. Verificar/guardar la película en la BD
            Pelicula peliculaConId = servicioDetalleOMDb
                    .VerificarSiLaPeliculaExiste((modelo.catalogo.Pelicula) PeliculaActual);

            // Instanciamos el servicio
            ServicioResenia servicioResenia = new ServicioResenia();

            // --- VALIDACIÓN DE DUPLICADOS ---
            if (servicioResenia.existeResenia(usrActual.getIdDB(), peliculaConId.getIdDB())) {
                javax.swing.JOptionPane.showMessageDialog(vista,
                        "¡Ya has calificado esta película!\nSolo se permite una reseña por título.",
                        "Acción no permitida",
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                return; // <-- Detiene la ejecución
            }
            // -----------------------------------------------

            // 2. Crear la vista... (resto del código igual)
            VistaResenia vistaResenia = new VistaResenia(framePrincipal);

            vistaResenia.cargarDatosPelicula(peliculaConId);

            new ControladorResenia(vistaResenia, servicioResenia, usrActual, peliculaConId);
            vistaResenia.setVisible(true);
        }

    }
}