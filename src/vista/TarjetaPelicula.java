package vista;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.net.URL;
import java.net.URI;

/**
 * Un componente de tarjeta que muestra el póster y el título de una
 * película.
 * Se encarga de cargar la imagen del póster desde una URL en un hilo separado
 * para no congelar la interfaz de usuario.
 */
public class TarjetaPelicula extends JPanel {

    private JLabel etiquetaPoster;
    private final Border bordeNormal = BorderFactory.createLineBorder(Color.GRAY, 1);
    private final Border bordeResaltado = BorderFactory.createLineBorder(Color.ORANGE, 2);

    public TarjetaPelicula(String titulo, String urlPoster, String rating, String genero) {
        // Configuración del Layout y Borde
        setLayout(new BorderLayout(5, 5));
        setBorder(bordeNormal); // Borde inicial
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el cursor a una mano
        setPreferredSize(new Dimension(180, 320)); // Tamaño fijo para la tarjeta

        // Etiqueta para el Título, Género y Rating
        JLabel etiquetaTitulo = new JLabel("<html><body style='width: 120px; text-align: center;'>" + titulo + "<br><i>"
                + genero + "</i><br><b>" + rating + " ⭐</b></body></html>");
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(etiquetaTitulo, BorderLayout.SOUTH);

        // Etiqueta para el Póster (inicialmente con texto de carga)
        etiquetaPoster = new JLabel("Cargando imagen...");
        etiquetaPoster.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaPoster.setVerticalAlignment(SwingConstants.CENTER);
        etiquetaPoster.setOpaque(true);
        etiquetaPoster.setBackground(Color.LIGHT_GRAY);
        add(etiquetaPoster, BorderLayout.CENTER);

        // Cargar la imagen en segundo plano
        cargarImagen(urlPoster);

        // --- LÓGICA PARA HACERLA CLICABLE ---
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBorder(bordeResaltado); // Cambia el borde al entrar el ratón
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBorder(bordeNormal); // Vuelve al borde normal al salir
            }

            // El controlador se encargará del mouseClicked
        });
    }

    public String getTitulo() {
        return ((JLabel) getComponent(0)).getText();
    }

    private void cargarImagen(String urlPoster) {
        // SwingWorker es la herramienta de Swing para tareas en segundo plano
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                // Esta parte se ejecuta en un hilo separado (NO en el de la UI)
                try {
                    // Forma moderna y segura de crear una URL desde un String
                    URL url = new URI(urlPoster).toURL();
                    ImageIcon originalIcon = new ImageIcon(url);
                    // Redimensionamos la imagen para que quepa en la tarjeta
                    Image imagenOriginal = originalIcon.getImage();
                    // Calculamos el alto manteniendo la proporción (ancho 170px)
                    int nuevoAncho = 170;
                    int nuevoAlto = (int) ((double) nuevoAncho / imagenOriginal.getWidth(null)
                            * imagenOriginal.getHeight(null));
                    Image imagenRedimensionada = imagenOriginal.getScaledInstance(nuevoAncho, nuevoAlto,
                            Image.SCALE_SMOOTH);

                    return new ImageIcon(imagenRedimensionada);
                } catch (Exception e) {
                    System.err.println("No se pudo cargar la imagen: " + urlPoster);
                    // Devolvemos null si hay un error para manejarlo en done()
                    return null;
                }
            }
            @Override
            protected void done() {
                // Esta parte se ejecuta de vuelta en el hilo de la UI cuando doInBackground()
                // termina
                try {
                    ImageIcon imagen = get(); // Obtenemos el resultado de doInBackground()
                    if (imagen != null) {
                        etiquetaPoster.setIcon(imagen);
                        etiquetaPoster.setText(""); // Quitamos el texto "Cargando..."
                    } else {
                        // Si hubo un error, mostramos un mensaje en la tarjeta
                        etiquetaPoster.setText("<html><center>Error al<br>cargar</center></html>");
                        etiquetaPoster.setIcon(null);
                    }
                } catch (Exception e) {
                    etiquetaPoster.setText("<html><center>Error al<br>cargar</center></html>");
                    etiquetaPoster.setIcon(null);
                    e.printStackTrace();
                }
            }
        };
        // ¡Iniciamos el trabajador!
        worker.execute();
    }
}