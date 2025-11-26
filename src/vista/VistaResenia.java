package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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

    private final Color COLOR_FONDO = Color.BLACK; // Negro puro
    private final Color COLOR_PANEL_TEXTO = new Color(20, 20, 20); // Gris muy oscuro
    private final Color COLOR_BOTON = new Color(45, 45, 45);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_ACENTO = new Color(206, 80, 84);//Distintivo de la app.

    /**
     * Constructor que inicializa la vista de la reseña.
     * 
     * @param owner El Frame padre sobre el cual este diálogo será modal.
     */
    public VistaResenia(Frame owner) {
        super(owner, "Escribir Reseña", true); // Título y "true" para hacerlo modal

        // --- CONFIGURACIÓN BÁSICA DE LA VENTANA ---
        // setTitle("Escribir Reseña"); // Se establece en el super()
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setSize(400, 650);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        getContentPane().setBackground(COLOR_FONDO);

        // --- PANEL PRINCIPAL Y LAYOUT ---
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20)); // Margen
        panelPrincipal.setBackground(COLOR_FONDO);
        add(panelPrincipal);

        // --- POSTER DE LA PELÍCULA (SIMULADO) ---
        lblPoster = new JLabel("Poster de la Película");
        lblPoster.setPreferredSize(new Dimension(180, 270));
        lblPoster.setMaximumSize(new Dimension(180, 270));
        lblPoster.setBorder(new LineBorder(new Color(60, 60, 60)));
        lblPoster.setHorizontalAlignment(SwingConstants.CENTER);
        lblPoster.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPoster.setForeground(Color.GRAY);
        panelPrincipal.add(lblPoster);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15))); // Espacio vertical

        // --- TÍTULO DE LA PELÍCULA ---
        lblTitulo = new JLabel("Título de la Película");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(COLOR_ACENTO);
        panelPrincipal.add(lblTitulo);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5))); // Espacio vertical

        // --- RATING PROMEDIO ---
        lblRatingPromedio = new JLabel("Rating Promedio: 8.5/10");
        lblRatingPromedio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRatingPromedio.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRatingPromedio.setForeground(Color.LIGHT_GRAY);
        panelPrincipal.add(lblRatingPromedio);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio vertical

        // --- PANEL DE PUNTUACIÓN (BOTONES 1-10) ---
        // --- PUNTUACIÓN (BOTONES) ---
        JLabel lblTuPuntuacion = new JLabel("Tu Puntuación");
        lblTuPuntuacion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTuPuntuacion.setForeground(COLOR_TEXTO);
        lblTuPuntuacion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblTuPuntuacion);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel panelPuntuacion = new JPanel(new GridLayout(1, 10, 2, 0)); // Menos espacio entre botones
        panelPuntuacion.setBackground(COLOR_FONDO);
        panelPuntuacion.setMaximumSize(new Dimension(400, 35));

        botonesPuntuacion = new JButton[10];
        for (int i = 0; i < 10; i++) {
            botonesPuntuacion[i] = new JButton(String.valueOf(i + 1));
            estilizarBoton(botonesPuntuacion[i]); // Aplicamos estilo manual
            botonesPuntuacion[i].setFont(new Font("Segoe UI", Font.PLAIN, 12));
            panelPuntuacion.add(botonesPuntuacion[i]);
        }
        panelPrincipal.add(panelPuntuacion);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- ÁREA DE TEXTO PARA EL COMENTARIO ---
        JLabel lblComentario = new JLabel("Tu Opinión");
        lblComentario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblComentario.setForeground(COLOR_TEXTO);
        lblComentario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblComentario);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));

        txtComentario = new JTextArea(5, 20);
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);
        txtComentario.setBackground(COLOR_PANEL_TEXTO); // Gris muy oscuro
        txtComentario.setForeground(COLOR_TEXTO);
        txtComentario.setCaretColor(COLOR_ACENTO); // Cursor naranja
        txtComentario.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollComentario = new JScrollPane(txtComentario);
        scrollComentario.setBorder(new LineBorder(new Color(60, 60, 60))); // Borde sutil
        // Truco para que la barra de scroll sea oscura (depende del LookAndFeel, pero
        // ayuda)
        scrollComentario.getViewport().setBackground(COLOR_PANEL_TEXTO);
        panelPrincipal.add(scrollComentario);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- BOTÓN GUARDAR ---
        btnGuardar = new JButton("PUBLICAR RESEÑA");
        estilizarBoton(btnGuardar);
        btnGuardar.setBackground(COLOR_ACENTO); // Botón Naranja
        btnGuardar.setForeground(Color.BLACK); // Texto Negro
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setPreferredSize(new Dimension(200, 40));
        btnGuardar.setMaximumSize(new Dimension(200, 40));
        btnGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(btnGuardar);

        // --- ESTADO ---
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        lblEstado = new JLabel("Pendiente de aprobación");
        lblEstado.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblEstado.setForeground(Color.GRAY);
        lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblEstado);
    }

    private void estilizarBoton(JButton boton) {
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(COLOR_TEXTO);
        boton.setFocusPainted(false); // Quita el recuadro de foco al hacer clic
        boton.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80))); // Borde plano
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
    }

    /**
     * Carga los datos de una película en la vista.
     * 
     * @param pelicula La película a mostrar.
     */
    public void cargarDatosPelicula(Pelicula pelicula) {
        if (pelicula == null)
            return;

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
     * 
     * @param urlPoster La URL de la imagen a cargar.
     */
    private void cargarImagen(String urlPoster) {
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                try {
                    URL url = new URI(urlPoster).toURL();
                    ImageIcon originalIcon = new ImageIcon(url);
                    Image imagenOriginal = originalIcon.getImage();
                    // Redimensionar a 180x270
                    return new ImageIcon(imagenOriginal.getScaledInstance(180, 270, Image.SCALE_SMOOTH));
                } catch (Exception e) {
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
                    }
                } catch (Exception e) {
                }
            }
        };
        worker.execute();
    }

    public JButton getBotonGuardar() {
        return btnGuardar;
    }

    public JButton[] getBotonesPuntuacion() {
        return botonesPuntuacion;
    }

    public String getTxtComentario() {
        return txtComentario.getText();
    }

}