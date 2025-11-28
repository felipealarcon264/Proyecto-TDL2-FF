//Verificacion JavaDoc -> Realizada.
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import modelo.catalogo.Pelicula;
import vista.TarjetaPelicula;
import vista.VistaSeleccionOMDb;

/**
 * Controlador para la ventana de selección de películas de OMDb.
 * Gestiona la visualización de los resultados de búsqueda y la elección del
 * usuario.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ControladorSeleccionOMDb implements ActionListener {

    private VistaSeleccionOMDb vista;
    private Pelicula peliculaElegida; // El resultado final tras elección.

    /**
     * Constructor del ControladorSeleccionOMDb.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param vista La vista de selección (VistaSeleccionOMDb) que este controlador
     *              gestiona.
     */
    public ControladorSeleccionOMDb(VistaSeleccionOMDb vista) {
        this.vista = vista;

        // Escuchar los botones principales
        this.vista.getBtnSeleccionar().addActionListener(this);
        this.vista.getBtnCancelar().addActionListener(this);
    }

    /**
     * Carga y muestra los resultados de la búsqueda en la vista.
     * Crea una tarjeta para cada película y le añade un listener para la selección.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param resultados La lista de películas encontradas en la búsqueda.
     */
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

    /**
     * Lógica que se ejecuta cuando el usuario hace clic en una tarjeta de película.
     * Almacena la película seleccionada y actualiza la vista para resaltarla.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param tarjetaSeleccionada La tarjeta de película en la que se hizo clic.
     */
    private void seleccionarTarjeta(TarjetaPelicula tarjetaSeleccionada) {
        this.peliculaElegida = tarjetaSeleccionada.getPelicula();
        vista.limpiarSeleccionVisual(); // Quitamos borde a todas
        vista.marcarTarjetaComoSeleccionada(tarjetaSeleccionada); // Ponemos borde a esta
    }

    /**
     * Maneja los eventos de acción de los botones "Seleccionar" y "Cancelar".
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param e El evento de acción que se ha producido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnSeleccionar()) {
            confirmarSeleccion();
        } else if (e.getSource() == vista.getBtnCancelar()) {
            cancelar();
        }
    }

    /**
     * Confirma la selección de la película. Si no se ha elegido ninguna,
     * muestra un error. Si se ha elegido una, cierra la ventana de selección.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    private void confirmarSeleccion() {
        if (this.peliculaElegida == null) {
            vista.mostrarError("Por favor, haz clic en una película para seleccionarla.");
            return;
        }
        // Cerramos la ventana, el ControladorHome recuperará 'peliculaElegida'
        vista.dispose();
    }

    /**
     * Cancela la operación de selección, establece la película elegida como nula
     * y cierra la ventana.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    private void cancelar() {
        this.peliculaElegida = null;
        vista.dispose();
    }

    /**
     * Obtiene la película que el usuario ha seleccionado.
     * Este método es llamado por el controlador que inició la selección
     * después de que la ventana se cierra.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return La Pelicula seleccionada, o null si se canceló la operación.
     */
    public Pelicula getResultado() {
        return peliculaElegida;
    }
}