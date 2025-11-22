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

public class ControladorDetalleOMDb implements ActionListener {

    private VistaDetalleOMDb vista;
    private ServicioDetalleOMDb servicioDetalleOMDb;
    private Pelicula PeliculaActual;
    private Usuario usrActual;
    private JFrame framePrincipal;

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