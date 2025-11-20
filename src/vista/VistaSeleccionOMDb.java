package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Ventana modal que muestra los resultados de la búsqueda
 */
public class VistaSeleccionOMDb extends JDialog {

    private JPanel panelGrilla; // Aquí irán las tarjetas
    private JButton botonSeleccionar;
    private JButton botonCancelar;

    public VistaSeleccionOMDb(Frame propietario) {
        super(propietario, "Resultados de Búsqueda", true); // Modal
        setLayout(new BorderLayout(0, 0));
        // Hacemos la ventana más ancha para que quepan 3 tarjetas por fila
        setSize(700, 500);
        setLocationRelativeTo(propietario);
        getContentPane().setBackground(Color.DARK_GRAY); // Fondo oscuro acorde al tema

        // Para la etiqueta superior
        JLabel labelInformacion = new JLabel("🍿 ¡Encontramos varias coincidencias! Selecciona una 🍿");
        labelInformacion.setForeground(Color.WHITE);
        labelInformacion.setFont(new Font("Arial", Font.BOLD, 14));
        labelInformacion.setBorder(new EmptyBorder(15, 15, 10, 15));
        labelInformacion.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelInformacion, BorderLayout.NORTH);

        // Para el panel Central usando la Grilla con ScrollPane
        panelGrilla = new JPanel(new GridLayout(0, 3, 15, 15)); // 3 columnas, espacio de 15px
        panelGrilla.setBackground(Color.DARK_GRAY);
        panelGrilla.setBorder(new EmptyBorder(10, 15, 10, 15));

        JScrollPane scrollPane = new JScrollPane(panelGrilla);
        scrollPane.setBorder(null); // Sin borde
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll suave
        add(scrollPane, BorderLayout.CENTER);

        // Para el panel de Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelBotones.setBackground(new Color(30, 30, 30)); // Un poco más oscuro

        botonSeleccionar = new JButton("Ver Detalle");
        botonCancelar = new JButton("Cancelar");

        // Estilo simple a los botones
        Dimension btnSize = new Dimension(120, 35);
        botonSeleccionar.setPreferredSize(btnSize);
        botonCancelar.setPreferredSize(btnSize);

        panelBotones.add(botonSeleccionar);
        panelBotones.add(botonCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

     // Agrega una tarjeta de película a la grilla.
    public void agregarTarjeta(TarjetaPelicula tarjeta) {
        panelGrilla.add(tarjeta);
    }

    //Limpia la selección visual (bordes) de todas las tarjetas del panel.
    public void limpiarSeleccionVisual() {
        for (Component c : panelGrilla.getComponents()) {
            if (c instanceof TarjetaPelicula) {
                // Reseteamos al borde gris normal (definido en TarjetaPelicula)
                ((TarjetaPelicula)c).setSeleccionada(false);
            }
        }
    }

    // Marca visualmente una tarjeta como seleccionada.
    public void marcarTarjetaComoSeleccionada(TarjetaPelicula tarjeta) {
        // se marca la tarjeta seleccionada.
        tarjeta.setSeleccionada(true);
    }
    // Los getters para su respectivo controlador.
    public JButton getBtnSeleccionar() {
        return botonSeleccionar;
    }

    public JButton getBtnCancelar() {
        return botonCancelar;
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Atención", JOptionPane.WARNING_MESSAGE);
    }
}