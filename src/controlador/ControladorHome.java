package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;

import comparadores.ComparadorPeliculaPorGenero;
import comparadores.ComparadorPeliculaPorTitulo;

import control.Aplicacion;
import modelo.ente.Usuario;
import modelo.catalogo.Pelicula;
import servicio.ServicioPelicula;
import vista.TarjetaPelicula;
import vista.VistaHome;
import vista.VistaResenia;

public class ControladorHome implements ActionListener {

    private VistaHome vista;
    private ServicioPelicula servicioPelicula;
    private List<Pelicula> peliculasMostradas; // Guardamos la lista actual
    private final JFrame framePrincipal; // Necesario para la modalidad del JDialog

    private static boolean esPrimeraVez = true;

    public ControladorHome(VistaHome vista, ServicioPelicula servicioPelicula, Usuario usuarioLogueado, JFrame framePrincipal) {
        this.vista = vista;
        this.servicioPelicula = servicioPelicula;
        this.framePrincipal = framePrincipal;

        // Escuchamos los botones
        this.vista.getBotonCerrarSesion().addActionListener(this);
        this.vista.getBotonBuscar().addActionListener(this);
        this.vista.getBotonMostrarOtras().addActionListener(this);
        this.vista.getComboOrdenar().addActionListener(this); // Escuchamos el nuevo ComboBox

        // Actualizamos el nombre de usuario en la vista.
        this.vista.setNombreUsuario(usuarioLogueado.getNombreUsuario());
        
        try {
            // Importar datos(Si hace falta)
            this.servicioPelicula.inicializarCatalogo();
            
            // 2. CARGAR LA VISTA
            cargarContenido();

        } catch (excepciones.ErrorDeInicializacionException e) {
            // Si falla la carga del CSV, mostramos un error y cerramos.
            javax.swing.JOptionPane.showMessageDialog(
                null, 
                e.getMessage() + "\nLa aplicación se cerrará.", 
                "Error Crítico", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Cierra la aplicación con un código de error.
        }
    }

    private void cargarContenido() {
        if (esPrimeraVez) {
            System.out.println("Primera visita: Mostrando Top 10...");
            peliculasMostradas = servicioPelicula.obtenerTop10();
            esPrimeraVez = false; // Ya no es la primera vez
        } else {
            System.out.println("Visita recurrente: Mostrando Aleatorias...");
            peliculasMostradas = servicioPelicula.obtener10Aleatorias();
        }

        // Repintamos la vista con la lista obtenida
        repintarPeliculas();
    }

    /**
     * Limpia la vista y la vuelve a poblar con la lista de películas actual.
     * Se usa para la carga inicial y para cuando se ordena la lista.
     */
    private void repintarPeliculas() {
        vista.limpiarVista(); // Borra las tarjetas anteriores

        // Mandamos las películas a la vista
        for (Pelicula p : peliculasMostradas) {
            // 1. Creamos la tarjeta (componente de la vista)
            TarjetaPelicula tarjeta = new TarjetaPelicula(p);

            // 2. Le añadimos la lógica del clic (responsabilidad del controlador)
            tarjeta.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    abrirVistaResenia(p);
                }
            });
            // 3. Le pasamos la tarjeta ya lista a la vista para que la muestre
            vista.agregarTarjetaPelicula(tarjeta);
        }
    }

    /**
     * Abre la ventana de reseña para una película específica.
     * La ventana se crea como un JDialog modal.
     *
     * @param pelicula La película sobre la que se hará la reseña.
     */
    private void abrirVistaResenia(Pelicula pelicula) {
        // Creamos la vista de reseña, pasándole el frame principal para que sea modal a él
        VistaResenia vistaResenia = new VistaResenia(framePrincipal);

        // Cargamos los datos de la película en la nueva vista
        vistaResenia.cargarDatosPelicula(pelicula);

        // Hacemos visible la ventana. La ejecución se bloqueará aquí hasta que se cierre.
        vistaResenia.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();

        if (fuente == vista.getBotonCerrarSesion()) {
            Aplicacion.mostrarVista("LOGIN");
            
        } else if (fuente == vista.getBotonBuscar()) { 
            // Lógica futura de búsqueda
            System.out.println("Buscando: " + vista.getTextoBusqueda());

        } else if (fuente == vista.getBotonMostrarOtras()) {
            System.out.println("Mostrando 10 películas aleatorias nuevas...");
            peliculasMostradas = servicioPelicula.obtener10Aleatorias();
            repintarPeliculas();

        } else if (fuente == vista.getComboOrdenar()) {
            // Lógica para ordenar la lista
            String opcion = (String) vista.getComboOrdenar().getSelectedItem();
            if (opcion == null || opcion.equals("Ordenar por...")) {
                return; // No hacer nada si es la opción por defecto
            }

            switch (opcion) {
                case "Título (A-Z)":
                    peliculasMostradas.sort(new ComparadorPeliculaPorTitulo());
                    break;
                case "Género (A-Z)":
                    peliculasMostradas.sort(new ComparadorPeliculaPorGenero());
                    break;
            }
            repintarPeliculas(); // Volvemos a pintar la vista con la lista ya ordenada
        }
    }
}