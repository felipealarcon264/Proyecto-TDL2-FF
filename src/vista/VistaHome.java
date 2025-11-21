package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VistaHome extends JPanel {

    // Componentes que el Controlador necesitará escuchar o leer
    private JTextField campoBusqueda;
    private JButton botonBuscar;
    private JButton botonCerrarSesion;
    private JButton botonMostrarOtras;
    private JComboBox<String> comboOrdenar; // Nuevo componente para ordenar
    private JLabel labelUsuario;
    private JPanel panelPeliculas; // Acá agregaremos las "tarjetas" de películas dinámicamente
    private JButton botonPerfil;

    public VistaHome() {
        // Norte para barra, Centro para contenido.
        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);

        // --- PANEL SUPERIOR ---
        // Logo (West) - Centro (Center) - Usuario y Salir (East)
        JPanel panelSuperior = new JPanel(new BorderLayout(20, 0));
        panelSuperior.setBackground(new Color(30, 30, 30)); // Gris oscuro
        panelSuperior.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding
        panelSuperior.setPreferredSize(new Dimension(0, 85)); // Alto fijo
        // -Logo-
        JLabel labelLogo = new JLabel("TDL2");
        labelLogo.setFont(new Font("Arial", Font.BOLD, 24));
        labelLogo.setForeground(Color.ORANGE); // Estilo tipo Netflix
        // -Centro-
        JPanel panelCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelCentral.setOpaque(false); // Para que se vea el fondo del panel superior
        // Panel para Ordenar y Refrescar (vertical)
        JPanel panelOpciones = new JPanel(new GridLayout(2, 1, 0, 5));
        panelOpciones.setOpaque(false);
        comboOrdenar = new JComboBox<>(new String[] { "Ordenar por...", "Título (A-Z)", "Género (A-Z)" });
        botonMostrarOtras = new JButton("Ver otras 10");
        panelOpciones.add(comboOrdenar);
        panelOpciones.add(botonMostrarOtras);
        // Panel para el campo de búsqueda y botón
        JPanel panelCampoBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelCampoBusqueda.setOpaque(false);
        campoBusqueda = new JTextField(30);
        botonBuscar = new JButton("Buscar");
        panelCampoBusqueda.add(campoBusqueda);
        panelCampoBusqueda.add(botonBuscar);
        // Agregamos los sub-paneles
        panelCentral.add(panelOpciones);
        panelCentral.add(panelCampoBusqueda);
        // -Usuario y Salir-
        // Usamos GridLayout de 3 filas para: Hola, Perfil, Salir
        JPanel panelUsuario = new JPanel(new GridLayout(3, 1, 0, 5));
        panelUsuario.setOpaque(false);

        labelUsuario = new JLabel("Hola, Usuario");
        labelUsuario.setForeground(Color.WHITE);
        labelUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        labelUsuario.setHorizontalAlignment(SwingConstants.RIGHT);

        botonPerfil = new JButton("Mi Perfil"); // Creamos el botón
        botonCerrarSesion = new JButton("Salir");

        panelUsuario.add(labelUsuario);
        panelUsuario.add(botonPerfil); // Agregamos Perfil
        panelUsuario.add(botonCerrarSesion); // Agregamos Salir

        // Agregamos al panelSuperior
        panelSuperior.add(labelLogo, BorderLayout.WEST);
        panelSuperior.add(panelCentral, BorderLayout.CENTER);
        panelSuperior.add(panelUsuario, BorderLayout.EAST);

        // Agregamos el panelSuperior al Norte de la vista principal
        this.add(panelSuperior, BorderLayout.NORTH);

        // --- PANEL CENTRAL (Peliculas) ---
        // Creamos el panel que tendrá la grilla
        panelPeliculas = new JPanel(new GridLayout(0, 4, 15, 15));
        panelPeliculas.setBackground(Color.DARK_GRAY);
        panelPeliculas.setBorder(new EmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(panelPeliculas);// Envolvemos el panel en un Scroll.
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Velocidad de scroll
        scrollPane.setBorder(null); // Quitar borde del scroll

        // Agregamos el Scroll al Centro
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // --- MÉTODOS PARA EL CONTROLADOR ---

    public JButton getBotonCerrarSesion() {
        return botonCerrarSesion;
    }

    public JButton getBotonBuscar() {
        return botonBuscar;
    }

    public String getTextoBusqueda() {
        return campoBusqueda.getText();
    }

    public JComboBox<String> getComboOrdenar() {
        return comboOrdenar;
    }

    public JButton getBotonMostrarOtras() {
        return botonMostrarOtras;
    }

    // Metodo para limpiar la vista.
    public void limpiarVista() {
        campoBusqueda.setText("");
        panelPeliculas.removeAll(); // Borra todas las películas viejas
        panelPeliculas.revalidate(); // Refresca el panel
        panelPeliculas.repaint();
    }

    /**
     * Crea una tarjeta para mostrar la pelicula y la agrega a la grilla.
     * El controlador crea la tarjeta y la pasa a este método para ser añadida.
     */
    public void agregarTarjetaPelicula(TarjetaPelicula tarjeta) {
        panelPeliculas.add(tarjeta);
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
    public JButton getBotonPerfil() {
    return botonPerfil;
}
}