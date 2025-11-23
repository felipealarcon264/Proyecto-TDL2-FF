package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class VistaHome extends JPanel {

    private JTextField campoBusqueda;
    private JButton botonBuscar;
    private JButton botonCerrarSesion;
    private JButton botonMostrarOtras;
    private JComboBox<String> comboOrdenar;
    private JLabel labelUsuario;
    private JPanel panelPeliculas;
    private JButton botonPerfil;

    // --- PALETA DE COLORES ---
    private final Color COLOR_FONDO = Color.BLACK;
    private final Color COLOR_BARRA = new Color(15, 15, 15); // Casi negro
    private final Color COLOR_BOTON = new Color(45, 45, 45);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_ACENTO = new Color(255, 140, 0); // Naranja

    public VistaHome() {
        this.setLayout(new BorderLayout());
        this.setBackground(COLOR_FONDO); // Fondo negro general

        // --- PANEL SUPERIOR ---
        JPanel panelSuperior = new JPanel(new BorderLayout(20, 0));
        panelSuperior.setBackground(COLOR_BARRA);
        panelSuperior.setBorder(new EmptyBorder(10, 20, 10, 20));
        panelSuperior.setPreferredSize(new Dimension(0, 85));

        // - Logo -
        JLabel labelLogo = new JLabel("TDL2");
        labelLogo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        labelLogo.setForeground(COLOR_ACENTO); // Naranja

        // - Centro (Búsqueda y Filtros) -
        JPanel panelCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelCentral.setOpaque(false);

        // Panel Opciones (Ordenar / Ver otras)
        JPanel panelOpciones = new JPanel(new GridLayout(2, 1, 0, 5));
        panelOpciones.setOpaque(false);

        comboOrdenar = new JComboBox<>(new String[] { "Ordenar por...", "Título (A-Z)", "Género (A-Z)" });
        estilizarCombo(comboOrdenar);

        botonMostrarOtras = new JButton("Ver otras 10");
        estilizarBoton(botonMostrarOtras);

        panelOpciones.add(comboOrdenar);
        panelOpciones.add(botonMostrarOtras);

        // Panel Búsqueda
        JPanel panelCampoBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelCampoBusqueda.setOpaque(false);

        campoBusqueda = new JTextField(30);
        estilizarCampoTexto(campoBusqueda);

        botonBuscar = new JButton("Buscar");
        estilizarBoton(botonBuscar);
        botonBuscar.setBackground(COLOR_ACENTO); // Botón de búsqueda naranja
        botonBuscar.setForeground(Color.BLACK);
        botonBuscar.setPreferredSize(new Dimension(100, 30));

        panelCampoBusqueda.add(campoBusqueda);
        panelCampoBusqueda.add(botonBuscar);

        panelCentral.add(panelOpciones);
        panelCentral.add(panelCampoBusqueda);

        // - Usuario y Salir -
        JPanel panelUsuario = new JPanel(new GridLayout(3, 1, 0, 5));
        panelUsuario.setOpaque(false);

        labelUsuario = new JLabel("Hola, Usuario");
        labelUsuario.setForeground(COLOR_TEXTO);
        labelUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelUsuario.setHorizontalAlignment(SwingConstants.RIGHT);

        botonPerfil = new JButton("Mi Perfil");
        estilizarBoton(botonPerfil);

        botonCerrarSesion = new JButton("Salir");
        estilizarBoton(botonCerrarSesion);
        botonCerrarSesion.setBackground(new Color(180, 50, 50)); // Rojo oscuro para salir
        botonCerrarSesion.setBorder(new LineBorder(new Color(100, 0, 0)));

        panelUsuario.add(labelUsuario);
        panelUsuario.add(botonPerfil);
        panelUsuario.add(botonCerrarSesion);

        panelSuperior.add(labelLogo, BorderLayout.WEST);
        panelSuperior.add(panelCentral, BorderLayout.CENTER);
        panelSuperior.add(panelUsuario, BorderLayout.EAST);

        this.add(panelSuperior, BorderLayout.NORTH);

        // --- PANEL CENTRAL (Grilla Peliculas) ---
        panelPeliculas = new JPanel(new GridLayout(0, 4, 15, 15));
        panelPeliculas.setBackground(COLOR_FONDO); // Fondo negro
        panelPeliculas.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(panelPeliculas);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(COLOR_FONDO); // Fondo negro también en el scroll

        this.add(scrollPane, BorderLayout.CENTER);
    }

    // --- MÉTODOS DE ESTILO ---
    private void estilizarBoton(JButton btn) {
        btn.setBackground(COLOR_BOTON);
        btn.setForeground(COLOR_TEXTO);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        btn.setOpaque(true);
    }

    private void estilizarCampoTexto(JTextField txt) {
        txt.setBackground(new Color(40, 40, 40));
        txt.setForeground(COLOR_TEXTO);
        txt.setCaretColor(COLOR_ACENTO);
        txt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void estilizarCombo(JComboBox<String> cmb) {
        cmb.setBackground(COLOR_BOTON);
        cmb.setForeground(COLOR_TEXTO);
        cmb.setFocusable(false);
    }

    // --- GETTERS Y MÉTODOS PÚBLICOS ---
    public JButton getBotonCerrarSesion() { return botonCerrarSesion; }
    public JButton getBotonBuscar() { return botonBuscar; }
    public String getTextoBusqueda() { return campoBusqueda.getText(); }
    public JComboBox<String> getComboOrdenar() { return comboOrdenar; }
    public JButton getBotonMostrarOtras() { return botonMostrarOtras; }
    public JButton getBotonPerfil() { return botonPerfil; }


    public void limpiarVista() {
        campoBusqueda.setText("");
        panelPeliculas.removeAll();
        panelPeliculas.revalidate();
        panelPeliculas.repaint();
    }

    public void agregarTarjetaPelicula(TarjetaPelicula tarjeta) {
        panelPeliculas.add(tarjeta);
        panelPeliculas.revalidate();
        panelPeliculas.repaint();
    }

    public void setNombreUsuario(String nombre) {
        labelUsuario.setText("Hola, " + nombre);
    }
}