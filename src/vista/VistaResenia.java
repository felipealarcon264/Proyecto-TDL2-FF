package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import modelo.catalogo.Pelicula;

import java.net.URI;
import java.net.URL;
import java.awt.*;

/**
 * Vista para que un usuario pueda ingresar o visualizar una reseña sobre una
 * película.
 * Muestra la información de la película y proporciona campos para calificar y
 * comentar.
 *
 * @author Gemini Code Assist
 * @version 1.0
 */
public class VistaResenia extends JDialog {

    private JLabel lblPoster;
    private JLabel lblTitulo;
    private JLabel lblRatingPromedio;
    private JTextArea txtComentario;
    private JLabel lblEstado;
    private JButton[] botonesPuntuacion;
    private JButton btnGuardar;

    /**
     * Constructor que inicializa la vista de la reseña.
     * @param owner El Frame padre sobre el cual este diálogo será modal.
     */
    public VistaResenia(Frame owner) {
        super(owner, "Escribir Reseña", true); // Título y "true" para hacerlo modal

        // --- CONFIGURACIÓN BÁSICA DE LA VENTANA ---
        // setTitle("Escribir Reseña"); // Se establece en el super()
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setSize(400, 650);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // --- PANEL PRINCIPAL Y LAYOUT ---
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15)); // Margen
        add(panelPrincipal);

        // --- POSTER DE LA PELÍCULA (SIMULADO) ---
        lblPoster = new JLabel("Poster de la Película");
        lblPoster.setPreferredSize(new Dimension(200, 300));
        lblPoster.setMinimumSize(new Dimension(200, 300));
        lblPoster.setMaximumSize(new Dimension(200, 300));
        lblPoster.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblPoster.setHorizontalAlignment(SwingConstants.CENTER);
        lblPoster.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblPoster);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio vertical

        // --- TÍTULO DE LA PELÍCULA ---
        lblTitulo = new JLabel("Título de la Película");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblTitulo);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5))); // Espacio vertical

        // --- RATING PROMEDIO ---
        lblRatingPromedio = new JLabel("Rating Promedio: 8.5/10");
        lblRatingPromedio.setFont(new Font("Arial", Font.ITALIC, 14));
        lblRatingPromedio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblRatingPromedio);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15))); // Espacio vertical

        // --- PANEL DE PUNTUACIÓN (BOTONES 1-10) ---
        JPanel panelPuntuacion = new JPanel(new GridLayout(1, 10, 5, 5));
        panelPuntuacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Limita altura
        botonesPuntuacion = new JButton[10];
        for (int i = 0; i < 10; i++) {
            botonesPuntuacion[i] = new JButton(String.valueOf(i + 1));
            panelPuntuacion.add(botonesPuntuacion[i]);
        }
        JLabel lblTuPuntuacion = new JLabel("Tu Puntuación");
        lblTuPuntuacion.setFont(new Font("Arial", Font.BOLD, 16));
        lblTuPuntuacion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblTuPuntuacion);
        panelPrincipal.add(panelPuntuacion);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15))); // Espacio vertical

        // --- ÁREA DE TEXTO PARA EL COMENTARIO ---
        JLabel lblComentario = new JLabel("Comentario");
        lblComentario.setFont(new Font("Arial", Font.BOLD, 16));
        lblComentario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblComentario);
        txtComentario = new JTextArea(5, 20);
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);
        JScrollPane scrollComentario = new JScrollPane(txtComentario);
        panelPrincipal.add(scrollComentario);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio vertical

        // --- ESTADO DE LA RESEÑA (APROBADA/PENDIENTE) ---
        lblEstado = new JLabel("Estado: Pendiente");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
        lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblEstado);

        panelPrincipal.add(Box.createVerticalGlue()); // Espacio flexible

        // --- BOTÓN DE GUARDAR ---
        btnGuardar = new JButton("Guardar Reseña");
        btnGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(btnGuardar);
    }

    /**
     * Carga los datos de una película en la vista.
     * @param pelicula La película a mostrar.
     */
    public void cargarDatosPelicula(Pelicula pelicula) {
        if (pelicula == null) return;

        lblTitulo.setText(pelicula.getTitulo());
        lblRatingPromedio.setText(String.format("Rating Promedio: %.1f/10", pelicula.getRatingPromedio()));

        // Lógica para cargar la imagen del póster (similar a TarjetaPelicula)
        // Por ahora, solo ponemos la URL como texto para verificar.
        // En una implementación final, aquí iría un SwingWorker para cargar la imagen.
        if (pelicula.getPoster() != null && !pelicula.getPoster().isEmpty()) {
            cargarImagen(pelicula.getPoster());
        } else {
            lblPoster.setText("Póster no disponible");
            lblPoster.setIcon(null);
        }
    }

    /**
     * Carga una imagen desde una URL en segundo plano y la establece en lblPoster.
     * @param urlPoster La URL de la imagen a cargar.
     */
    private void cargarImagen(String urlPoster) {
        lblPoster.setText("Cargando imagen...");
        lblPoster.setIcon(null);

        SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                try {
                    URL url = new URI(urlPoster).toURL();
                    ImageIcon originalIcon = new ImageIcon(url);
                    Image imagenOriginal = originalIcon.getImage();

                    // Redimensionamos la imagen para que quepa en el JLabel (200x300)
                    int labelAncho = 200;
                    int labelAlto = 300;
                    
                    // Mantenemos la proporción de la imagen original
                    float ratioOriginal = (float) imagenOriginal.getWidth(null) / imagenOriginal.getHeight(null);
                    float ratioLabel = (float) labelAncho / labelAlto;

                    int nuevoAncho = labelAncho;
                    int nuevoAlto = labelAlto;

                    if (ratioOriginal > ratioLabel) {
                        nuevoAlto = (int) (labelAncho / ratioOriginal);
                    } else {
                        nuevoAncho = (int) (labelAlto * ratioOriginal);
                    }

                    Image imagenRedimensionada = imagenOriginal.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
                    return new ImageIcon(imagenRedimensionada);
                } catch (Exception e) {
                    System.err.println("No se pudo cargar la imagen para la reseña: " + urlPoster);
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    ImageIcon imagen = get();
                    if (imagen != null) {
                        lblPoster.setIcon(imagen);
                        lblPoster.setText("");
                    } else {
                        lblPoster.setText("<html><center>Error al<br>cargar póster</center></html>");
                        lblPoster.setIcon(null);
                    }
                } catch (Exception e) {
                    lblPoster.setText("<html><center>Error al<br>cargar póster</center></html>");
                    lblPoster.setIcon(null);
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    /**
     * Método main para probar y visualizar esta vista de forma independiente.
     * 
     * @param args Argumentos de la línea de comandos (no se usan).
     */
}