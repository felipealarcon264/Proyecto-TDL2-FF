package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import modelo.catalogo.Pelicula;
import vista.TarjetaPelicula;
import vista.VistaSeleccionOMDb;

public class ControladorSeleccionOMDb implements ActionListener {

    private VistaSeleccionOMDb vista;
    private Pelicula peliculaElegida; // El resultado final tras elección.

    public ControladorSeleccionOMDb(VistaSeleccionOMDb vista, List<Pelicula> resultados) {
        this.vista = vista;

        // Se carga la vista con tarjetas
        cargarTarjetas(resultados);

        // Escuchar los botones principales
        this.vista.getBtnSeleccionar().addActionListener(this);
        this.vista.getBtnCancelar().addActionListener(this);
    }

    private void cargarTarjetas(List<Pelicula> resultados) {
        for (Pelicula peli : resultados) {
            // Reutilizamos tu componente TarjetaPelicula
            TarjetaPelicula tarjetaPelicula = new TarjetaPelicula(peli);

            // Le añadimos un "escucha" a CADA tarjeta para saber cuándo la clickean
            tarjetaPelicula.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    seleccionarTarjeta(tarjetaPelicula);
                }
            });

            // La añadimos a la vista
            vista.agregarTarjeta(tarjetaPelicula);
        }
    }

    //Lógica cuando el usuario hace clic en una tarjeta.
    private void seleccionarTarjeta(TarjetaPelicula tarjetaSeleccionada) {
        this.peliculaElegida = tarjetaSeleccionada.getPelicula();
        vista.limpiarSeleccionVisual(); // Quitamos borde a todas
        vista.marcarTarjetaComoSeleccionada(tarjetaSeleccionada); // Ponemos borde a esta
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnSeleccionar()) {
            confirmarSeleccion();
        } else if (e.getSource() == vista.getBtnCancelar()) {
            cancelar();
        }
    }

    private void confirmarSeleccion() {
        if (this.peliculaElegida == null) {
            vista.mostrarError("Por favor, haz clic en una película para seleccionarla.");
            return;
        }
        // Cerramos la ventana, el ControladorHome recuperará 'peliculaElegida'
        vista.dispose();
    }

    private void cancelar() {
        this.peliculaElegida = null;
        vista.dispose();
    }

    public Pelicula getResultado() {
        return peliculaElegida;
    }
}