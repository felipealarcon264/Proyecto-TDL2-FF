//Verificacion JavaDoc -> Realizada.
package vista;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import modelo.catalogo.Pelicula;
import modelo.catalogo.Resenia;

/**
 * Un componente de tarjeta que muestra una reseña de un usuario sobre una
 * película.
 * Incluye el póster de la película, el título, la calificación dada por el
 * usuario,
 * el comentario, el estado de la reseña (publicada o pendiente) y un botón para
 * eliminarla.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class TarjetaResenia extends JPanel {

    private Resenia resenia;
    private JButton botonEliminar;
    private JLabel labelPoster;
    private final Color COLOR_TITULO = new Color(206, 80, 84);// Distintivo de la app.
    private final Color COLOR_FONDO = new Color(40, 40, 40);

    /**
     * Constructor de TarjetaResenia.
     * Configura la apariencia y los componentes de la tarjeta para mostrar la
     * información de una reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param resenia El objeto Resenia que contiene los datos a mostrar.
     */
    public TarjetaResenia(Resenia resenia) {
        this.resenia = resenia;

        // Configuración visual de la tarjeta
        this.setLayout(new BorderLayout(15, 15));
        this.setBackground(COLOR_FONDO); // Gris un poco más claro que el fondo
        this.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(60, 60, 60), 1),
                new EmptyBorder(10, 10, 10, 10)));
        // Altura fija para que la imagen se vea bien, aprox 140px

        this.setPreferredSize(new Dimension(0, 140));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        // diseño para el poster
        labelPoster = new JLabel();
        labelPoster.setPreferredSize(new Dimension(80, 120)); // Tamaño "Miniatura"
        labelPoster.setMinimumSize(new Dimension(80, 120));
        labelPoster.setHorizontalAlignment(SwingConstants.CENTER);
        labelPoster.setBorder(new LineBorder(Color.BLACK));
        labelPoster.setOpaque(true);
        labelPoster.setBackground(new Color(30, 30, 30));

        // Cargamos la imagen
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
        lblPelicula.setForeground(COLOR_TITULO);

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
        txtComentario.setBackground(new Color(40, 40, 40));
        txtComentario.setForeground(Color.WHITE);
        txtComentario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtComentario.setBorder(null);

        // Usamos ScrollPane para asegurar el renderizado
        JScrollPane scrollComentario = new JScrollPane(txtComentario);
        scrollComentario.setBorder(null); // Sin bordes
        scrollComentario.setOpaque(false);
        scrollComentario.getViewport().setOpaque(false); // Transparente para ver el fondo gris

        panelInfo.add(scrollComentario, BorderLayout.CENTER);

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
        botonEliminar.setBorder(BorderFactory.createLineBorder(new Color(100, 30, 30)));

        // Guardamos el ID de la reseña en el botón para el controlador
        botonEliminar.putClientProperty("ID_RESENIA", resenia.getIdDB());

        panelSur.add(lblEstado, BorderLayout.WEST);
        panelSur.add(botonEliminar, BorderLayout.EAST);

        this.add(panelSur, BorderLayout.SOUTH);
        this.add(panelInfo, BorderLayout.CENTER);
    }

    /**
     * Carga la imagen del póster de la película en miniatura desde una URL en un
     * hilo de fondo
     * para no bloquear la interfaz de usuario.
     * Lógica para la imagen en miniatura por Gemini.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param urlPoster La URL de la imagen del póster.
     */
    private void cargarImagenMiniatura(String urlPoster) {
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                try {
                    if (urlPoster == null || urlPoster.isEmpty() || urlPoster.equals("N/A"))
                        return null;
                    URL url = new URI(urlPoster).toURL();
                    ImageIcon originalIcon = new ImageIcon(url);
                    Image imagenOriginal = originalIcon.getImage();

                    // Escalamos a 80x120 (Proporción póster pequeño)
                    return new ImageIcon(imagenOriginal.getScaledInstance(80, 120, Image.SCALE_SMOOTH));
                } catch (Exception e) {
                    return null;
                }
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
                } catch (Exception e) {
                    cargarImagenPorDefecto();
                }
            }
        };
        worker.execute();
    }

    /**
     * Carga una imagen por defecto cuando no se puede encontrar o cargar el póster
     * de la película desde la URL proporcionada.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    private void cargarImagenPorDefecto() {
        try {
            // Usamos tu imagen de fallback
            URL urlDefault = getClass().getResource("/imagenes/PosterNoEncontrado.png");
            if (urlDefault != null) {
                ImageIcon icono = new ImageIcon(urlDefault);
                Image imagen = icono.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Un poco más chico si
                                                                                               // es icono
                labelPoster.setIcon(new ImageIcon(imagen));
            } else {
                labelPoster.setText("Sin imagen");
                labelPoster.setForeground(Color.WHITE);
            }
        } catch (Exception e) {
            labelPoster.setText("Error");
        }
    }

    /**
     * Obtiene el botón para eliminar la reseña.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El JButton utilizado para la acción de eliminar.
     */
    public JButton getBotonEliminar() {
        return botonEliminar;
    }

    /**
     * Obtiene el objeto Resenia asociado a esta tarjeta.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El objeto Resenia.
     */
    public Resenia getResenia() {
        return resenia;
    }
}