package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import comparadores.ComparadorPeliculaPorGenero;
import comparadores.ComparadorPeliculaPorTitulo;

import control.Aplicacion;
import modelo.ente.Usuario;
import modelo.catalogo.Pelicula;
import servicio.ServicioPelicula;
import servicio.ServicioResenia;
import vista.*;
import servicio.ServicioDetalleOMDb;
import servicio.ServicioOMDb;
import excepciones.ErrorApiOMDbException;

public class ControladorHome implements ActionListener {

    private VistaHome vista;
    private ServicioPelicula servicioPelicula;
    private List<Pelicula> peliculasMostradas; // Guardamos la lista actual
    private final JFrame framePrincipal; // Necesario para la modalidad del JDialog

    private Usuario usuarioLogueado; // Guardar el usuario logueado
    private static boolean esPrimeraVez = true;

    private final ServicioOMDb servicioOMDb;

    public ControladorHome(VistaHome vista, ServicioPelicula servicioPelicula, Usuario usuarioLogueado,
            JFrame framePrincipal) {
        this.vista = vista;
        this.servicioPelicula = servicioPelicula;
        this.framePrincipal = framePrincipal;
        this.usuarioLogueado = usuarioLogueado; // Guardar el usuario
        this.servicioOMDb = new ServicioOMDb();

        // Escuchamos los botones
        this.vista.getBotonPerfil().addActionListener(this);
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
        for (Pelicula pelicula : peliculasMostradas) {
            // 1. Creamos la tarjeta (componente de la vista)
            TarjetaPelicula tarjeta = new TarjetaPelicula(pelicula);

            // 2. Le añadimos la lógica del clic (responsabilidad del controlador)
            tarjeta.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    abrirVistaResenia(pelicula);
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
        // Creamos la vista de reseña, pasándole el frame principal para que sea modal a
        // él
        VistaResenia vistaResenia = new VistaResenia(framePrincipal);
        ServicioResenia servicioResenia = new ServicioResenia();

        // Cargamos los datos de la película en la nueva vista
        vistaResenia.cargarDatosPelicula(pelicula);

        // Creamos y conectamos el ControladorResenia
        new ControladorResenia(vistaResenia, servicioResenia, usuarioLogueado, pelicula);

        // Hacemos visible la ventana. La ejecución se bloqueará aquí hasta que se
        // cierre.
        vistaResenia.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();

        if (fuente == vista.getBotonCerrarSesion()) {
            Aplicacion.mostrarVista("LOGIN");

        } else if (fuente == vista.getBotonPerfil()) { // <--- PEGA ESTE BLOQUE
            // 1. Buscamos la vista Perfil en el mazo de cartas
            vista.VistaPerfil vistaPerfil = null;
            for (java.awt.Component c : control.Aplicacion.panelContenedor.getComponents()) {
                if (c instanceof vista.VistaPerfil) {
                    vistaPerfil = (vista.VistaPerfil) c;
                    break;
                }
            }
            // 2. Si la encontramos, le cargamos los datos frescos
            if (vistaPerfil != null) {
                servicio.ServicioResenia servicioResenia = new servicio.ServicioResenia();
                new ControladorPerfil(vistaPerfil, servicioResenia, usuarioLogueado);

                // 3. Mostramos la pantalla
                control.Aplicacion.mostrarVista("PERFIL");
            }

        } else if (fuente == vista.getBotonBuscar()) {
            realizarBusquedaOMDb();
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

    private void realizarBusquedaOMDb() {
        String busqueda = vista.getTextoBusqueda();
        if (busqueda.isEmpty())
            return;

        Aplicacion.mostrarVista("CARGA");

        // "WORKER" para buscar en segundo plano.
        SwingWorker<List<Pelicula>, Void> busquedaWorker = new SwingWorker<>() {
            @Override
            protected List<Pelicula> doInBackground() throws Exception {
                Thread.sleep(1000); // Pequeña pausa para apreciar el GIF
                return servicioOMDb.buscarPeliculas(busqueda);
            }

            @Override
            protected void done() {
                Aplicacion.mostrarVista("HOME");

                try {
                    List<Pelicula> resultados = get(); // Obtener resultado del hilo

                    if (resultados == null || resultados.isEmpty()) {
                        JOptionPane.showMessageDialog(framePrincipal, "No se encontraron películas con ese nombre.");
                        return;
                    }

                    Pelicula peliculaElegida = null;

                    // Sí hay muchas en coincidencias
                    if (resultados.size() > 1) {
                        // Creamos la Vista
                        vista.VistaSeleccionOMDb vistaSeleccion = new vista.VistaSeleccionOMDb(framePrincipal);

                        // pasamos la vista Y la lista de resultados
                        controlador.ControladorSeleccionOMDb ctrlSeleccion = new controlador.ControladorSeleccionOMDb(
                                vistaSeleccion, resultados);

                        vistaSeleccion.setVisible(true); // Modal

                        peliculaElegida = ctrlSeleccion.getResultado();
                    }

                    if (peliculaElegida != null) {
                        // El objeto actual es "ligero", buscamos el detalle completo por ID
                        String imdbID = peliculaElegida.getResumen(); // Usamos 'resumen' donde guardamos el ID

                        // Llamada directa a API
                        Pelicula detalleFull = servicioOMDb.obtenerDetallePelicula(imdbID);

                        // Vista y Controlador de Detalle
                        VistaDetalleOMDb vistaDetalle = new VistaDetalleOMDb(framePrincipal, detalleFull);
                        new ControladorDetalleOMDb(vistaDetalle, new ServicioDetalleOMDb(), usuarioLogueado,
                                detalleFull, framePrincipal); // Conecta botón cerrar

                        vistaDetalle.setVisible(true);
                    }

                } catch (Exception ex) {
                    // Manejo de nuestra excepción personalizada
                    if (ex.getCause() instanceof ErrorApiOMDbException) {
                        JOptionPane.showMessageDialog(framePrincipal, ex.getCause().getMessage(),
                                "Error De Busqueda 🚨", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(framePrincipal,
                                "Ocurrió un error inesperado: " + ex.getMessage());
                    }
                }
            }
        };

        busquedaWorker.execute(); // ¡Arrancar!
    }
}