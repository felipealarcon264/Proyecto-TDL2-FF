//Verificacion JavaDoc -> Realizada.
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Un JDialog modal que muestra los resultados de una búsqueda de películas de
 * la API de OMDb.
 * Permite al usuario seleccionar una película de una grilla de resultados para
 * ver sus detalles.
 * Incluye una vista de carga mientras se obtienen los datos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class VistaSeleccionOMDb extends JDialog {

    private JPanel panelCard; // Panel principal con CardLayout
    private CardLayout cardLayout;

    private JPanel panelGrilla; // Panel de resultados
    private JButton botonSeleccionar;
    private JButton botonCancelar;
    private JPanel panelBotones; // Panel sur

    /**
     * Constructor de VistaSeleccionOMDb.
     * Inicializa y configura la ventana de diálogo y sus componentes.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param propietario El Frame padre sobre el cual este diálogo será modal.
     */
    public VistaSeleccionOMDb(Frame propietario) {
        super(propietario, "RESULTADOS DE BÚSQUEDA", true); // Modal

        // tamaño fijo
        setSize(850, 600);
        setLocationRelativeTo(propietario);
        setResizable(false);

        getContentPane().setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());

        // configuramos el CardLayout para la Carga y Grilla
        cardLayout = new CardLayout();
        panelCard = new JPanel(cardLayout);
        panelCard.setBackground(Color.DARK_GRAY);

        // Reutilizamos tu VistaCarga directamente
        VistaCarga vistaCarga = new VistaCarga();
        panelCard.add(vistaCarga, "CARGA");

        // Panel contenedor de la grilla
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.setBackground(Color.DARK_GRAY);

        // Etiqueta superior
        JLabel labelInfo = new JLabel("🍿¡Encontramos varias coincidencias! Selecciona una. 🍿");
        labelInfo.setForeground(Color.WHITE);
        labelInfo.setFont(new Font("Arial", Font.BOLD, 14));
        labelInfo.setBorder(new EmptyBorder(10, 15, 10, 15));
        labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        panelResultados.add(labelInfo, BorderLayout.NORTH);

        // La Grilla con Scroll
        panelGrilla = new JPanel(new GridLayout(0, 3, 15, 15));
        panelGrilla.setBackground(Color.DARK_GRAY);
        panelGrilla.setBorder(new EmptyBorder(10, 15, 10, 15));

        JScrollPane scrollPane = new JScrollPane(panelGrilla);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panelResultados.add(scrollPane, BorderLayout.CENTER);

        panelCard.add(panelResultados, "RESULTADOS DE BÚSQUEDA");

        // Agregamos el panel de cartas al centro de la ventana
        add(panelCard, BorderLayout.CENTER);

        // Panel de Botones (Sur) - Inicialmente oculto o deshabilitado
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelBotones.setBackground(new Color(30, 30, 30));

        botonSeleccionar = new JButton("Ver Detalle");
        botonCancelar = new JButton("Cancelar");

        Dimension btnSize = new Dimension(120, 35);
        botonSeleccionar.setPreferredSize(btnSize);
        botonCancelar.setPreferredSize(btnSize);

        panelBotones.add(botonSeleccionar);
        panelBotones.add(botonCancelar);

        // Lo agregamos al Sur
        add(panelBotones, BorderLayout.SOUTH);
        panelBotones.setVisible(false); // Oculto al inicio (durante carga)
    }

    // --- MÉTODOS DE CAMBIO DE VISTA ---

    /**
     * Muestra el panel de carga y oculta los botones de acción.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public void mostrarCarga() {
        cardLayout.show(panelCard, "CARGA");
        panelBotones.setVisible(false); // Ocultar botones mientras carga
    }

    /**
     * Muestra el panel con la grilla de resultados y los botones de acción.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public void mostrarResultados() {
        cardLayout.show(panelCard, "RESULTADOS DE BÚSQUEDA");
        panelBotones.setVisible(true); // Mostrar botones cuando hay resultados
    }

    // --- MÉTODOS DE LA GRILLA ---

    /**
     * Agrega una tarjeta de película a la grilla de resultados.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param tarjeta La TarjetaPelicula a agregar.
     */
    public void agregarTarjeta(TarjetaPelicula tarjeta) {
        panelGrilla.add(tarjeta);
        panelGrilla.revalidate();
        panelGrilla.repaint();
    }

    /**
     * Desmarca visualmente todas las tarjetas de la grilla, quitándoles el borde de
     * selección.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public void limpiarSeleccionVisual() {
        for (Component c : panelGrilla.getComponents()) {
            if (c instanceof TarjetaPelicula) {
                ((TarjetaPelicula) c).setSeleccionada(false);
            }
        }
    }

    /**
     * Marca visualmente una tarjeta específica como seleccionada.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param tarjeta La TarjetaPelicula a marcar.
     */
    public void marcarTarjetaComoSeleccionada(TarjetaPelicula tarjeta) {
        tarjeta.setSeleccionada(true);
    }

    /**
     * Obtiene el botón para seleccionar una película y ver sus detalles.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El JButton para la acción de seleccionar.
     */
    public JButton getBtnSeleccionar() {
        return botonSeleccionar;
    }

    /**
     * Obtiene el botón para cancelar la selección y cerrar el diálogo.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El JButton para la acción de cancelar.
     */
    public JButton getBtnCancelar() {
        return botonCancelar;
    }

    /**
     * Muestra un cuadro de diálogo de advertencia.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param mensaje El mensaje de advertencia a mostrar.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Atención", JOptionPane.WARNING_MESSAGE);
    }
}