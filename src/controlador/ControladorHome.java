package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import control.Aplicacion;
import modelo.ente.Usuario;
import modelo.catalogo.Pelicula;
import servicio.ServicioPelicula;
import vista.VistaHome;

public class ControladorHome implements ActionListener {

    private VistaHome vista;
    private ServicioPelicula servicioPelicula;
    
    // Bandera estática: recuerda si ya entramos en esta ejecución
    private static boolean esPrimeraVez = true;

    public ControladorHome(VistaHome vista, ServicioPelicula servicioPelicula, Usuario usuarioLogueado) {
        this.vista = vista;
        this.servicioPelicula = servicioPelicula;

        // Escuchamos los botones
        this.vista.getBotonCerrarSesion().addActionListener(this);
        this.vista.getBotonBuscar().addActionListener(this);

        // Actualizamos el nombre de usuario en la vista.
        this.vista.setNombreUsuario(usuarioLogueado.getNombreUsuario());
        
        // Importar datos(Si hace falta)
        this.servicioPelicula.inicializarCatalogo();
        
        // 2. CARGAR LA VISTA
        cargarContenido();
    }

    private void cargarContenido() {
        vista.limpiarVista(); // Borra lo anterior
        List<Pelicula> listaMostrar;

        if (esPrimeraVez) {
            System.out.println("Primera visita: Mostrando Top 10...");
            listaMostrar = servicioPelicula.obtenerTop10();
            esPrimeraVez = false; // Ya no es la primera vez
        } else {
            System.out.println("Visita recurrente: Mostrando Aleatorias...");
            listaMostrar = servicioPelicula.obtener10Aleatorias();
        }

        // Mandamos las películas a la vista
        for (Pelicula p : listaMostrar) {
            // Usamos el método que creaste en VistaHome
            vista.agregarTarjetaPelicula(
                p.getTitulo(), 
                p.getPoster(), 
                String.valueOf(p.getRatingPromedio())
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();

        if (fuente == vista.getBotonCerrarSesion()) {
            Aplicacion.mostrarVista("LOGIN");
            
        } else if (fuente == vista.getBotonBuscar()) {
            // Lógica futura de búsqueda
            System.out.println("Buscando: " + vista.getTextoBusqueda());
        }
    }
}