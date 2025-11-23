package vista;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import modelo.catalogo.Pelicula;
import modelo.catalogo.Resenia;

public class TarjetaResenia extends JPanel {

    private Resenia resenia;
    private JButton botonEliminar;
    private JLabel labelPoster;

    public TarjetaResenia(Resenia resenia) {
        this.resenia = resenia;

        // Configuración visual de la tarjeta
        this.setLayout(new BorderLayout(15, 15));
        this.setBackground(new Color(40, 40, 40)); // Gris un poco más claro que el fondo
        this.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(60, 60, 60),1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        // Altura fija para que la imagen se vea bien, aprox 140px

        this.setPreferredSize(new Dimension(0,140));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        // diseño para el poster
        labelPoster = new JLabel();
        labelPoster.setPreferredSize(new Dimension(80, 120)); // Tamaño "Miniatura"
        labelPoster.setMinimumSize(new Dimension(80, 120));
        labelPoster.setHorizontalAlignment(SwingConstants.CENTER);
        labelPoster.setBorder(new LineBorder(Color.BLACK));
        labelPoster.setOpaque(true);
        labelPoster.setBackground(new Color(30, 30, 30));

        //Cargamos la imagen
        if (resenia.getContenido() instanceof Pelicula) {
            Pelicula peli = (Pelicula) resenia.getContenido();
            cargarImagenMiniatura(peli.getPoster());
        }
        this.add(labelPoster, BorderLayout.WEST);

        // --- Seccion central para titulo, comentario y boton ---
        JPanel panelInfo = new JPanel(new BorderLayout(5, 5));
        panelInfo.setOpaque(false); // Transparente para ver el fondo de la tarjeta

        // A. Encabezado (Título y Rating)
        JPanel panelEncabezado = new JPanel(new BorderLayout());
        panelEncabezado.setOpaque(false);

        JLabel lblPelicula = new JLabel(resenia.getContenido().getTitulo());
        lblPelicula.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblPelicula.setForeground(new Color(255, 140, 0)); // Naranja TDL2

        JLabel lblRatingUsuario = new JLabel("Tu nota: " + resenia.getCalificacion() + "/10 ⭐");
        lblRatingUsuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblRatingUsuario.setForeground(Color.WHITE);

        panelEncabezado.add(lblPelicula, BorderLayout.CENTER);
        panelEncabezado.add(lblRatingUsuario, BorderLayout.EAST);

        panelInfo.add(panelEncabezado, BorderLayout.NORTH);

        // --- El Comentario ---
        JTextArea txtComentario = new JTextArea(resenia.getComentario());
        txtComentario.setWrapStyleWord(true);
        txtComentario.setLineWrap(true);
        txtComentario.setEditable(false);
        txtComentario.setOpaque(false);
        txtComentario.setForeground(Color.LIGHT_GRAY);
        txtComentario.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        txtComentario.setBorder(null);
        
        this.add(txtComentario, BorderLayout.CENTER);

        // --- Estado y Botón Eliminar ---
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);

        // Estado (Aprobado/Pendiente)
        String estadoTexto = (resenia.getAprobado() == 1) ? "✅ Publicada" : "⏳ Pendiente de revisión";
        Color estadoColor = (resenia.getAprobado() == 1) ? new Color(100, 200, 100) : Color.GRAY;
        
        JLabel lblEstado = new JLabel(estadoTexto);
        lblEstado.setForeground(estadoColor);
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        // Botón Eliminar
        botonEliminar = new JButton("Eliminar");
        botonEliminar.setBackground(new Color(180, 60, 60));
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.setFocusPainted(false);
        botonEliminar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        botonEliminar.setPreferredSize(new Dimension(100, 25));
        botonEliminar.setBorder(BorderFactory.createLineBorder(new Color(100, 30 ,30)));
        
        // Guardamos el ID de la reseña en el botón para el controlador
        botonEliminar.putClientProperty("ID_RESENIA", resenia.getIdDB());

        panelSur.add(lblEstado, BorderLayout.WEST);
        panelSur.add(botonEliminar, BorderLayout.EAST);

        this.add(panelSur, BorderLayout.SOUTH);
        this.add(panelInfo, BorderLayout.CENTER);
    }
    // logica para la imagen en miniatura por Gemini.
    private void cargarImagenMiniatura(String urlPoster) {
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                try {
                    if (urlPoster == null || urlPoster.isEmpty() || urlPoster.equals("N/A")) return null;
                    URL url = new URI(urlPoster).toURL();
                    ImageIcon originalIcon = new ImageIcon(url);
                    Image imagenOriginal = originalIcon.getImage();

                    // Escalamos a 80x120 (Proporción póster pequeño)
                    return new ImageIcon(imagenOriginal.getScaledInstance(80, 120, Image.SCALE_SMOOTH));
                } catch (Exception e) { return null; }
            }

            @Override
            protected void done() {
                try {
                    ImageIcon imagen = get();
                    if (imagen != null) {
                        labelPoster.setIcon(imagen);
                    } else {
                        cargarImagenPorDefecto();
                    }
                } catch (Exception e) { cargarImagenPorDefecto(); }
            }
        };
        worker.execute();
    }

    private void cargarImagenPorDefecto() {
        try {
            // Usamos tu imagen de fallback
            URL urlDefault = getClass().getResource("/imagenes/PosterNoEncontrado.png");
            if (urlDefault != null) {
                ImageIcon icono = new ImageIcon(urlDefault);
                Image imagen = icono.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Un poco más chico si es icono
                labelPoster.setIcon(new ImageIcon(imagen));
            } else {
                labelPoster.setText("Sin imagen");
                labelPoster.setForeground(Color.WHITE);
            }
        } catch (Exception e) {
            labelPoster.setText("Error");
        }
    }
    public JButton getBotonEliminar() {
        return botonEliminar;
    }

    public Resenia getResenia() {
        return resenia;
    }
}