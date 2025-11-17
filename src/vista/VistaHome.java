package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VistaHome extends JPanel {

    // Componentes que el Controlador necesitará escuchar o leer
    private JTextField campoBusqueda;
    private JButton botonBuscar;
    private JButton botonCerrarSesion;
    private JLabel labelUsuario;
    private JPanel panelPeliculas; // Acá agregaremos las "tarjetas" de películas dinámicamente

    public VistaHome() {
        // Norte para barra, Centro para contenido.
        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);

        // --- PANEL SUPERIOR ---
        // Logo (West) - Buscador (Center) - Usuario y Salir (East)
        JPanel panelSuperior = new JPanel(new BorderLayout(20, 0));
        panelSuperior.setBackground(new Color(30, 30, 30)); // Gris oscuro
        panelSuperior.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding
        panelSuperior.setPreferredSize(new Dimension(0, 70)); // Alto fijo
        // Logo.
        JLabel labelLogo = new JLabel("TDL2");
        labelLogo.setFont(new Font("Arial", Font.BOLD, 24));
        labelLogo.setForeground(Color.ORANGE); // Estilo tipo Netflix
        // Buscador.
        JPanel panelBuscador = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBuscador.setOpaque(false); // Para que se vea el fondo gris
        campoBusqueda = new JTextField(30);
        botonBuscar = new JButton("Buscar");
        panelBuscador.add(campoBusqueda);
        panelBuscador.add(botonBuscar);
        // Usuario y Salir
        JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelUsuario.setOpaque(false);
        labelUsuario = new JLabel("Hola, Usuario"); // Inicializamos el campo
        labelUsuario.setForeground(Color.WHITE);
        labelUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        botonCerrarSesion = new JButton("Salir");
        panelUsuario.add(labelUsuario);
        panelUsuario.add(botonCerrarSesion);

        // Agregamos al panelSuperior
        panelSuperior.add(labelLogo, BorderLayout.WEST);
        panelSuperior.add(panelBuscador, BorderLayout.CENTER);
        panelSuperior.add(panelUsuario, BorderLayout.EAST);

        // Agregamos el panelSuperior al Norte de la vista principal
        this.add(panelSuperior, BorderLayout.NORTH);

        // --- PANEL CENTRAL (Peliculas) ---
        // Creamos el panel que tendrá la grilla
        panelPeliculas = new JPanel(new GridLayout(0, 4, 15, 15));
        panelPeliculas.setBackground(Color.DARK_GRAY);
        panelPeliculas.setBorder(new EmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(panelPeliculas);//Envolvemos el panel en un Scroll.
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Velocidad de scroll
        scrollPane.setBorder(null); // Quitar borde del scroll

        // Agregamos el Scroll al Centro
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // --- MÉTODOS PARA EL CONTROLADOR ---

    // 1. Getters para los botones
    public JButton getBotonCerrarSesion() {
        return botonCerrarSesion;
    }

    public JButton getBotonBuscar() {
        return botonBuscar;
    }

    public String getTextoBusqueda() {
        return campoBusqueda.getText();
    }

    // 2. Método para limpiar la vista (MVC)
    public void limpiarVista() {
        campoBusqueda.setText("");
        panelPeliculas.removeAll(); // Borra todas las películas viejas
        panelPeliculas.revalidate(); // Refresca el panel
        panelPeliculas.repaint();
    }

    // 3. Método CLAVE: Agregar una "Tarjeta" de película
    // El Controlador llamará a esto dentro de un bucle para llenar la grilla
    public void agregarTarjetaPelicula(String titulo, String urlPoster, String rating) {

        // Creamos una instancia de nuestra nueva tarjeta inteligente
        TarjetaPelicula tarjeta = new TarjetaPelicula(titulo, urlPoster, rating);
        panelPeliculas.add(tarjeta); // La añadimos al panel

        panelPeliculas.revalidate(); // Avisamos al layout manager que hay un nuevo componente
        panelPeliculas.repaint(); // Repintamos el panel
    }

    /**
     * Actualiza el nombre de usuario que se muestra en la barra superior.
     * 
     * @param nombre El nombre de usuario a mostrar.
     */
    public void setNombreUsuario(String nombre) {
        labelUsuario.setText("Hola, " + nombre);
    }
}