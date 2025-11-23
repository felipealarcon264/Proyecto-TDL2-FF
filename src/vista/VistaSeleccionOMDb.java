package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Ventana modal que maneja dos estados:
 * 1. Cargando (gif)
 * 2. Resultados (muestra la grilla de las peliculas coincidentes a la busqueda
 */
public class VistaSeleccionOMDb extends JDialog {

    private JPanel panelCard; // Panel principal con CardLayout
    private CardLayout cardLayout;

    private JPanel panelGrilla; // Panel de resultados
    private JButton botonSeleccionar;
    private JButton botonCancelar;
    private JPanel panelBotones; // Panel sur

    public VistaSeleccionOMDb(Frame propietario) {
        super(propietario, "RESULTADOS DE BÚSQUEDA", true); // Modal

        // tamaño fijo
        setSize(850, 600);
        setLocationRelativeTo(propietario);
        setResizable(false);

        getContentPane().setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());

        //configuramos el CardLayout para la Carga y Grilla
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

    public void mostrarCarga() {
        cardLayout.show(panelCard, "CARGA");
        panelBotones.setVisible(false); // Ocultar botones mientras carga
    }

    public void mostrarResultados() {
        cardLayout.show(panelCard, "RESULTADOS DE BÚSQUEDA");
        panelBotones.setVisible(true); // Mostrar botones cuando hay resultados
    }

    // --- MÉTODOS DE LA GRILLA ---

    public void agregarTarjeta(TarjetaPelicula tarjeta) {
        panelGrilla.add(tarjeta);
        panelGrilla.revalidate();
        panelGrilla.repaint();
    }

    public void limpiarSeleccionVisual() {
        for (Component c : panelGrilla.getComponents()) {
            if (c instanceof TarjetaPelicula) {
                ((TarjetaPelicula)c).setSeleccionada(false);
            }
        }
    }

    public void marcarTarjetaComoSeleccionada(TarjetaPelicula tarjeta) {
        tarjeta.setSeleccionada(true);
    }

    public JButton getBtnSeleccionar() { return botonSeleccionar; }
    public JButton getBtnCancelar() { return botonCancelar; }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Atención", JOptionPane.WARNING_MESSAGE);
    }
}