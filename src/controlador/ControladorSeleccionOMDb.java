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

    // Solo recibe la vista
    public ControladorSeleccionOMDb(VistaSeleccionOMDb vista) {
        this.vista = vista;

        // Escuchar los botones principales
        this.vista.getBtnSeleccionar().addActionListener(this);
        this.vista.getBtnCancelar().addActionListener(this);
    }
    // metodo para cargar los resultados (grilla de las pelis)
    public void mostrarResultados(List<Pelicula> resultados) {
        for (Pelicula peli : resultados) {
            TarjetaPelicula tarjetaPelicula = new TarjetaPelicula(peli);
            tarjetaPelicula.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evento) {
                    seleccionarTarjeta(tarjetaPelicula);
                }
            });
            vista.agregarTarjeta(tarjetaPelicula);
        }
        vista.mostrarResultados();
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