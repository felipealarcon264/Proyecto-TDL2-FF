//Verificacion JavaDoc -> Realizada.
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

/**
 * Controlador principal de la pantalla Home. Gestiona la visualización de
 * películas,
 * la navegación al perfil, el cierre de sesión y las búsquedas de películas.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ControladorHome implements ActionListener {

    private VistaHome vista;
    private ServicioPelicula servicioPelicula;
    private List<Pelicula> peliculasMostradas; // Guardamos la lista actual
    private final JFrame framePrincipal; // Necesario para la modalidad del JDialog

    private Usuario usuarioLogueado; // Guardar el usuario logueado

    private final ServicioOMDb servicioOMDb;

    /**
     * Constructor del ControladorHome.
     * Inicializa los servicios, configura los listeners de la vista y carga el
     * contenido inicial.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param vista            La vista principal (VistaHome) que este controlador
     *                         gestiona.
     * @param servicioPelicula El servicio para obtener datos de películas de la
     *                         base de datos local.
     * @param usuarioLogueado  El usuario que ha iniciado sesión.
     * @param framePrincipal   El marco principal de la aplicación, usado para la
     *                         modalidad de los diálogos.
     */
    public ControladorHome(VistaHome vista, ServicioPelicula servicioPelicula, Usuario usuarioLogueado,
            JFrame framePrincipal) {
        this.vista = vista;
        this.servicioPelicula = servicioPelicula;
        this.framePrincipal = framePrincipal;
        this.usuarioLogueado = usuarioLogueado;
        this.servicioOMDb = new ServicioOMDb();

        // --- LIMPIEZA DE LISTENERS VIEJOS ---
        // Esto evita que el usuario anterior siga "escuchando" los clics
        voidLimpiarTodosLosListeners();

        // --- AGREGAMOS LOS NUEVOS ---
        this.vista.getBotonPerfil().addActionListener(this);
        this.vista.getBotonCerrarSesion().addActionListener(this);
        this.vista.getBotonBuscar().addActionListener(this);
        this.vista.getComboMostrarOtras().addActionListener(this);
        this.vista.getComboOrdenar().addActionListener(this);

        // Actualizamos el nombre de usuario en la vista.
        this.vista.setNombreUsuario(usuarioLogueado.getNombreUsuario());

        try {
            this.servicioPelicula.importarPeliculaConCSV();
            cargarContenido();
        } catch (excepciones.ErrorDeInicializacionException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            javax.swing.JOptionPane.showMessageDialog(null, "Recomendamos reiniciar la aplicacion.");
            // System.exit(1); Decidimos que no se cierre la app.
        }
    }

    /**
     * Limpia todos los listeners de los componentes interactivos de la vista.
     * Es crucial para evitar acciones duplicadas al cambiar de usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    private void voidLimpiarTodosLosListeners() {
        limpiarListeners(this.vista.getBotonPerfil());
        limpiarListeners(this.vista.getBotonCerrarSesion());
        limpiarListeners(this.vista.getBotonBuscar());
        limpiarListeners(this.vista.getComboMostrarOtras());
        limpiarListeners(this.vista.getComboOrdenar());
    }

    /**
     * Método auxiliar para eliminar todos los ActionListeners de un botón.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param boton El botón del cual se eliminarán los listeners.
     */
    private void limpiarListeners(AbstractButton boton) {
        for (java.awt.event.ActionListener al : boton.getActionListeners()) {
            boton.removeActionListener(al);
        }
    }

    /**
     * Método auxiliar para eliminar todos los ActionListeners de un JComboBox.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param combo El JComboBox del cual se eliminarán los listeners.
     */
    private void limpiarListeners(JComboBox<?> combo) {
        for (java.awt.event.ActionListener al : combo.getActionListeners()) {
            combo.removeActionListener(al);
        }
    }

    /**
     * Carga el contenido inicial de películas en la vista.
     * Muestra el Top 10 si el usuario es nuevo, o 10 películas aleatorias
     * si es un usuario recurrente.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    private void cargarContenido() {
        // Verificamos si es usuario nuevo (1 = Nuevo)
        if (usuarioLogueado.getEsNuevo() == 1) {
            System.out.println("Usuario Nuevo detectado. Mostrando Top 10...");

            // 1. Obtener Top 10
            peliculasMostradas = servicioPelicula.obtenerTop10();
        } else {
            // Si no es nuevo (0), comportamiento normal
            System.out.println("Usuario Recurrente. Mostrando Aleatorias...");
            peliculasMostradas = servicioPelicula.obtener10Aleatorias();
        }

        // Repintamos la vista con la lista obtenida
        repintarPeliculas();
    }

    /**
     * Muestra un mensaje de bienvenida si el usuario es nuevo y actualiza su estado
     * en la base de datos para que no se le muestre de nuevo.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public void mostrarBienvenidaSiUsuarioNuevo() {
        if (usuarioLogueado.getEsNuevo() == 1) {
            // Mostrar Mensaje de Bienvenida
            JOptionPane.showMessageDialog(framePrincipal,
                    "¡Bienvenido a TDL2! 🎬\n\nComo eres nuevo, hemos seleccionado\nlas 10 películas mejor valoradas para ti.\n¡Disfrútalas y califícalas!",
                    "Bienvenida Especial",
                    JOptionPane.INFORMATION_MESSAGE);

            // Actualizar usuario en BD para que la próxima vez sea random
            usuarioLogueado.setEsNuevo(0); // En memoria
            new servicio.ServicioUsuario().actualizarEstadoUsuario(usuarioLogueado); // En BD
        }
    }

    /**
     * Limpia la vista y la vuelve a poblar con la lista de películas actual.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     *          Se usa para la carga inicial y para cuando se ordena la lista.
     */
    private void repintarPeliculas() {
        vista.limpiarVistaHome(); // Borra las tarjetas anteriores

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
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param pelicula La película sobre la que se hará la reseña.
     */
    private void abrirVistaResenia(Pelicula pelicula) {
        // Creamos la vista de reseña, pasándole el frame principal para que sea modal a
        // él
        ServicioResenia servicioResenia = new ServicioResenia();
        // --- VALIDACIÓN DE DUPLICADOS ---
        // Verificamos si ya existe la reseña antes de abrir la ventana
        if (servicioResenia.existeResenia(usuarioLogueado.getIdDB(), pelicula.getIdDB())) {
            JOptionPane.showMessageDialog(framePrincipal,
                    "¡Ya has calificado esta película!\nSolo se permite una reseña por título.",
                    "Acción no permitida",
                    JOptionPane.WARNING_MESSAGE);
            return; // <-- IMPORTANTE: Detiene la ejecución aquí.
        }
        // -----------------------------------------------
        VistaResenia vistaResenia = new VistaResenia(framePrincipal);

        // Cargamos los datos de la película en la nueva vista
        vistaResenia.cargarDatosPelicula(pelicula);

        // Creamos y conectamos el ControladorResenia
        new ControladorResenia(vistaResenia, servicioResenia, usuarioLogueado, pelicula);

        // Hacemos visible la ventana. La ejecución se bloqueará aquí hasta que se
        // cierre.
        vistaResenia.setVisible(true);
    }

    /**
     * Maneja los eventos de acción de los botones y combos en la vista Home.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param e El evento de acción que se ha producido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();

        if (fuente == vista.getBotonCerrarSesion()) {
            Aplicacion.mostrarVista("LOGIN");

        } else if (fuente == vista.getBotonPerfil()) {
            // --- CORRECCIÓN: SIEMPRE CREAR UNA VISTA NUEVA ---

            // 1. Instanciamos una vista totalmente nueva (vacía y limpia)
            vista.VistaPerfil nuevaVistaPerfil = new vista.VistaPerfil();

            // 2. Instanciamos el servicio necesario
            servicio.ServicioResenia servicioResenia = new servicio.ServicioResenia();

            // 3. Conectamos el controlador. Al crearse, este llenará la vista con los datos
            // del usuarioLogueado ACTUAL.
            new ControladorPerfil(nuevaVistaPerfil, servicioResenia, usuarioLogueado);

            // 4. Agregamos la nueva vista al panel contenedor con la etiqueta "PERFIL"
            // Esto "machaca" la referencia anterior en el CardLayout
            control.Aplicacion.panelContenedor.add(nuevaVistaPerfil, "PERFIL");

            // 5. Navegamos a la nueva vista
            control.Aplicacion.mostrarVista("PERFIL");

        } else if (fuente == vista.getBotonBuscar()) {
            realizarBusquedaOMDb();
        } else if (fuente == vista.getComboMostrarOtras()) {
            String opcion = (String) vista.getComboMostrarOtras().getSelectedItem();
            if (opcion == null || opcion.equals("Mostrar otras...")) {
                System.out.println("Otrasss");
                return; // No hacer nada si es la opción por defecto
            }
            switch (opcion) {
                case "Top 10":
                    peliculasMostradas = servicioPelicula.obtenerTop10();
                    repintarPeliculas();
                    break;
                case "10 random":
                    peliculasMostradas = servicioPelicula.obtener10Aleatorias();
                    repintarPeliculas();
                    break;
                default:
                    System.out.println("Opción no reconocida: " + opcion);
                    return; // No hacer nada si la opción no es reconocida
            }
        } else if (fuente == vista.getComboOrdenar()) {
            // Lógica para ordenar la lista
            String opcion = (String) vista.getComboOrdenar().getSelectedItem();
            if (opcion == null || opcion.equals("Ordenar por...")) {
                return; // No hacer nada si es la opción por defecto
            }

            switch (opcion) {
                case "Título (A-Z)":
                    peliculasMostradas.sort(new ComparadorPeliculaPorTitulo());
                    repintarPeliculas();
                    break;
                case "Género (A-Z)":
                    peliculasMostradas.sort(new ComparadorPeliculaPorGenero());
                    repintarPeliculas();
                    break;
                default:
                    System.out.println("Opción no reconocida: " + opcion);
                    return; // No hacer nada si la opción no es reconocida
            }
        }
    }

    /**
     * Inicia el proceso de búsqueda de una película en la API de OMDb.
     * Utiliza un SwingWorker para realizar la búsqueda en segundo plano y no
     * bloquear la interfaz.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    private void realizarBusquedaOMDb() {
        String busqueda = vista.getTextoBusqueda();
        if (busqueda.isEmpty())
            return;

        VistaSeleccionOMDb vistaSeleccionOMDb = new VistaSeleccionOMDb(framePrincipal);
        ControladorSeleccionOMDb controladorSeleccionOMDb = new ControladorSeleccionOMDb(vistaSeleccionOMDb);
        vistaSeleccionOMDb.mostrarCarga();

        // "WORKER" para buscar en segundo plano.
        SwingWorker<List<Pelicula>, Void> busquedaWorker = new SwingWorker<>() {
            @Override
            protected List<Pelicula> doInBackground() throws Exception {
                Thread.sleep(500); // Pequeña pausa para apreciar el GIF
                return servicioOMDb.buscarPeliculas(busqueda);
            }

            @Override
            protected void done() {
                try {
                    List<Pelicula> resultados = get(); // Obtener resultado del hilo

                    if (resultados == null || resultados.isEmpty()) {
                        vistaSeleccionOMDb.dispose();
                        JOptionPane.showMessageDialog(framePrincipal, "No se encontraron películas con ese nombre.");
                        return;
                    }
                    // Sí hay muchas en coincidencias
                    if (resultados.size() > 1) {
                        controladorSeleccionOMDb.mostrarResultados(resultados);
                    } else {
                        // si solo hay una coincidencia, cerramos seleccion y vamos a la pantalla de los
                        // detalles.
                        vistaSeleccionOMDb.dispose();
                        mostrarDetalle(resultados.get(0));
                    }
                } catch (Exception ex) {
                    // Manejo de nuestra excepción personalizada
                    vistaSeleccionOMDb.dispose();
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
        // cuando termine de actualizar la ventana el worker
        vistaSeleccionOMDb.setVisible(true);
        Pelicula peliulaElegida = controladorSeleccionOMDb.getResultado();
        if (peliulaElegida != null)
            mostrarDetalle(peliulaElegida);
    }

    /**
     * Muestra la ventana de detalles de una película específica obtenida de OMDb.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param peliculaAuxiliar La película (con datos básicos de la búsqueda) cuyo
     *                         detalle completo se va a mostrar.
     *                         Se usa su IMDb ID para obtener la información
     *                         completa.
     */
    private void mostrarDetalle(Pelicula peliculaAuxiliar) {
        try {
            String imdbID = peliculaAuxiliar.getResumen();
            Pelicula detalleFull = servicioOMDb.obtenerDetallePelicula(imdbID);
            VistaDetalleOMDb vistaDetalle = new VistaDetalleOMDb(framePrincipal, detalleFull);
            new ControladorDetalleOMDb(vistaDetalle, new ServicioDetalleOMDb(), usuarioLogueado, detalleFull,
                    framePrincipal);
            vistaDetalle.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(framePrincipal, "Error al cargar detalle: " + e.getMessage());
        }
    }
}