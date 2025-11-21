package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import modelo.catalogo.Resenia;

public class TarjetaResenia extends JPanel {

    private Resenia resenia;
    private JButton botonEliminar;

    public TarjetaResenia(Resenia resenia) {
        this.resenia = resenia;

        // Configuración visual de la tarjeta
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(new Color(50, 50, 50)); // Gris un poco más claro que el fondo
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); // Altura fija aprox

        // --- 1. Título de la Película y Rating ---
        JPanel panelEncabezado = new JPanel(new BorderLayout());
        panelEncabezado.setOpaque(false);

        JLabel lblPelicula = new JLabel(resenia.getContenido().getTitulo());
        lblPelicula.setFont(new Font("Arial", Font.BOLD, 16));
        lblPelicula.setForeground(Color.ORANGE);

        panelEncabezado.add(lblPelicula, BorderLayout.CENTER);

        this.add(panelEncabezado, BorderLayout.NORTH);

        // --- 2. El Comentario ---
        JTextArea txtComentario = new JTextArea(resenia.getComentario());
        txtComentario.setWrapStyleWord(true);
        txtComentario.setLineWrap(true);
        txtComentario.setEditable(false);
        txtComentario.setOpaque(false);
        txtComentario.setForeground(Color.LIGHT_GRAY);
        txtComentario.setFont(new Font("Arial", Font.ITALIC, 12));
        
        this.add(txtComentario, BorderLayout.CENTER);

        // --- 3. Estado y Botón Eliminar ---
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);

        // Estado (Aprobado/Pendiente)
        String estadoTexto = (resenia.getAprobado() == 1) ? "✅ Publicada" : "⏳ Pendiente de revisión";
        Color estadoColor = (resenia.getAprobado() == 1) ? Color.GREEN : Color.GRAY;
        
        JLabel lblEstado = new JLabel(estadoTexto);
        lblEstado.setForeground(estadoColor);
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 11));

        // Botón Eliminar
        botonEliminar = new JButton("Eliminar");
        botonEliminar.setBackground(new Color(180, 50, 50));
        //botonEliminar.setForeground(Color.WHITE);
        botonEliminar.setFocusPainted(false);
        botonEliminar.setFont(new Font("Arial", Font.BOLD, 11));
        botonEliminar.setPreferredSize(new Dimension(90, 25));
        
        // Guardamos el ID de la reseña en el botón para el controlador
        botonEliminar.putClientProperty("ID_RESENIA", resenia.getIdDB());

        panelSur.add(lblEstado, BorderLayout.WEST);
        panelSur.add(botonEliminar, BorderLayout.EAST);

        this.add(panelSur, BorderLayout.SOUTH);
    }

    public JButton getBotonEliminar() {
        return botonEliminar;
    }

    public Resenia getResenia() {
        return resenia;
    }
}