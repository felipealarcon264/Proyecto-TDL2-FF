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
            // La lógica es crear una vistaResenia con logica validando que la pelicula este
            // guardada en la base de datos

            // 1. Verificar/guardar la película en la BD y obtener el objeto con el ID correcto.
            Pelicula peliculaConId = servicioDetalleOMDb.VerificarSiLaPeliculaExiste((modelo.catalogo.Pelicula) PeliculaActual);

            // 2. Crear la vista y el controlador de la reseña con la película ya persistida.
            VistaResenia vistaResenia = new VistaResenia(framePrincipal);
            ServicioResenia servicioResenia = new ServicioResenia();

            // 3. ¡PASO FALTANTE! Cargar los datos de la película en la vista de reseña.
            vistaResenia.cargarDatosPelicula(peliculaConId);

            new ControladorResenia(vistaResenia, servicioResenia, usrActual, peliculaConId);
            vistaResenia.setVisible(true); // 4. Mostrar la ventana al final
        }

    }
}