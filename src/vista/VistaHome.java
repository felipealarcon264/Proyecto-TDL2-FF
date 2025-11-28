//Verificacion JavaDoc -> Realizada.
package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * La vista principal o "Home" de la aplicación una vez que el usuario ha
 * iniciado sesión.
 * Muestra una barra superior con opciones de búsqueda, filtrado y gestión de
 * usuario,
 * y un panel central donde se despliegan las tarjetas de las películas.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class VistaHome extends JPanel {

    private JTextField campoBusqueda;
    private JButton botonBuscar;
    private JButton botonCerrarSesion;
    private JComboBox<String> comboMostrarOtras;
    private JComboBox<String> comboOrdenar;
    private JLabel labelUsuario;
    private JPanel panelPeliculas;
    private JButton botonPerfil;

    // --- PALETA DE COLORES ---
    private final Color COLOR_FONDO = Color.BLACK;
    private final Color COLOR_BARRA = new Color(15, 15, 15); // Casi negro
    private final Color COLOR_BOTON = new Color(45, 45, 45);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_ACENTO = new Color(206, 80, 84);// Distintivo de la app.

    /**
     * Constructor de VistaHome.
     * Inicializa y configura todos los componentes de la interfaz de usuario de la
     * pantalla principal.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
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

        comboMostrarOtras = new JComboBox<>(new String[] { "Mostrar otras...", "Top 10", "10 random" });
        estilizarCombo(comboMostrarOtras);

        panelOpciones.add(comboOrdenar);
        panelOpciones.add(comboMostrarOtras);

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

    /**
     * Aplica un estilo visual consistente a un botón.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param btn El JButton al que se le aplicará el estilo.
     */
    private void estilizarBoton(JButton btn) {
        btn.setBackground(COLOR_BOTON);
        btn.setForeground(COLOR_TEXTO);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        btn.setOpaque(true);
    }

    /**
     * Aplica un estilo visual consistente a un campo de texto.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param txt El JTextField al que se le aplicará el estilo.
     */
    private void estilizarCampoTexto(JTextField txt) {
        txt.setBackground(new Color(40, 40, 40));
        txt.setForeground(COLOR_TEXTO);
        txt.setCaretColor(COLOR_ACENTO);
        txt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    /**
     * Aplica un estilo visual consistente a un JComboBox.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param cmb El JComboBox al que se le aplicará el estilo.
     */
    private void estilizarCombo(JComboBox<String> cmb) {
        cmb.setBackground(Color.WHITE);
        cmb.setFocusable(false);
    }

    /**
     * Obtiene el botón para cerrar sesión.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El JButton para la acción de cerrar sesión.
     */
    public JButton getBotonCerrarSesion() {
        return botonCerrarSesion;
    }

    /**
     * Obtiene el botón para iniciar una búsqueda.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El JButton para la acción de buscar.
     */
    public JButton getBotonBuscar() {
        return botonBuscar;
    }

    /**
     * Obtiene el texto ingresado en el campo de búsqueda.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El String con el término de búsqueda.
     */
    public String getTextoBusqueda() {
        return campoBusqueda.getText();
    }

    /**
     * Obtiene el JComboBox para las opciones de ordenamiento.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El JComboBox de ordenamiento.
     */
    public JComboBox<String> getComboOrdenar() {
        return comboOrdenar;
    }

    /**
     * Obtiene el JComboBox para mostrar otras listas de películas (Top 10, etc.).
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El JComboBox para mostrar otras listas.
     */
    public JComboBox<String> getComboMostrarOtras() {
        return comboMostrarOtras;
    }

    /**
     * Obtiene el botón para acceder al perfil del usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El JButton para la acción de ver perfil.
     */
    public JButton getBotonPerfil() {
        return botonPerfil;
    }

    /**
     * Limpia la vista, eliminando el texto de búsqueda y todas las tarjetas de
     * película mostradas.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public void limpiarVistaHome() {
        campoBusqueda.setText("");
        panelPeliculas.removeAll();
        panelPeliculas.revalidate();
        panelPeliculas.repaint();
    }

    /**
     * Agrega una tarjeta de película al panel principal de la grilla.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param tarjeta La TarjetaPelicula a agregar.
     */
    public void agregarTarjetaPelicula(TarjetaPelicula tarjeta) {
        panelPeliculas.add(tarjeta);
        panelPeliculas.revalidate();
        panelPeliculas.repaint();
    }

    /**
     * Establece el nombre de usuario que se muestra en la barra superior.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param nombre El nombre del usuario a mostrar.
     */
    public void setNombreUsuario(String nombre) {
        labelUsuario.setText("Hola, " + nombre);
    }
}