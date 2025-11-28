//Verificacion JavaDoc -> Realizada.

package vista;

import javax.swing.*;
import modelo.catalogo.Pelicula;
import javax.swing.border.Border;

import java.awt.*;
import java.net.URL;
import java.net.URI;

/**
 * Un componente de tarjeta que muestra el póster y el título de una
 * película.
 * Se encarga de cargar la imagen del póster desde una URL en un hilo separado
 * para no congelar la interfaz de usuario.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class TarjetaPelicula extends JPanel {

    private JLabel etiquetaPoster;
    private Pelicula pelicula; // Guardamos la referencia a la película completa
    private final Border bordeNormal = BorderFactory.createLineBorder(Color.GRAY, 1);
    private final Border bordeResaltado = BorderFactory.createLineBorder(Color.ORANGE, 2);
    private final Border bordeSeleccionado = BorderFactory.createLineBorder(Color.GREEN, 4);
    private boolean seleccionada = false;

    /**
     * Constructor de TarjetaPelicula.
     * Configura la apariencia y los componentes de la tarjeta para mostrar la
     * información de una película.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param pelicula El objeto Pelicula que contiene los datos a mostrar.
     */
    public TarjetaPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;

        // Configuración del Layout y Borde
        setLayout(new BorderLayout(0, 0));
        setBorder(bordeNormal); // Borde inicial
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el cursor a una mano
        setPreferredSize(new Dimension(180, 320)); // Tamaño fijo para la tarjeta
        setBackground(Color.DARK_GRAY);

        // Etiqueta para el Título, Género y Rating
        String textoTitulo = "<html><body style='width: 120px; text-align: center; color: white;'>"
                + pelicula.getTitulo();

        // Si tenemos género real (no es null ni "Desconocido"), lo mostramos
        if (pelicula.getGenero() != null && !pelicula.getGenero().equals("Desconocido")
                && !pelicula.getGenero().equals("null")) {
            textoTitulo += "<br><i style='color:gray'>" + pelicula.getGenero() + "</i>";
        }

        // Si tenemos rating real (mayor a 0), lo mostramos
        if (pelicula.getRatingPromedio() > 0) {
            String rating = String.format("%.1f", pelicula.getRatingPromedio());
            textoTitulo += "<br><b style='color:orange'>" + rating + " ⭐</b>";
        } else {
            // Si no hay rating mostramos el año que sí suele venir
            if (pelicula.getAnio() > 0) {
                textoTitulo += "<br><b style='color:lightgray'>(" + pelicula.getAnio() + ")</b>";
            }
        }

        textoTitulo += "</body></html>";

        JLabel etiquetaTitulo = new JLabel(textoTitulo);
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaTitulo.setOpaque(true);
        etiquetaTitulo.setBackground(new Color(40, 40, 40));
        add(etiquetaTitulo, BorderLayout.SOUTH);

        // Etiqueta para el Póster (inicialmente con texto de carga)
        etiquetaPoster = new JLabel("Cargando imagen...");
        etiquetaPoster.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaPoster.setVerticalAlignment(SwingConstants.CENTER);
        etiquetaPoster.setOpaque(true);
        etiquetaPoster.setBackground(new Color(50, 50, 50)); // gris oscuro
        add(etiquetaPoster, BorderLayout.CENTER);

        // Cargar la imagen en segundo plano
        cargarImagen(pelicula.getPoster());

        // --- LÓGICA PARA HACERLA CLICABLE ---
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!seleccionada) {
                    setBorder(bordeResaltado); // Cambia el borde al entrar el ratón
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!seleccionada) {
                    setBorder(bordeNormal); // Vuelve al borde normal al salir
                }
            }
        });
    }

    /**
     * Cambia el estado visual de la tarjeta para indicar si está seleccionada.
     * Un estado seleccionado se resalta con un borde y fondo distintos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param seleccionada true para marcar como seleccionada, false para volver al
     *                     estado normal.
     */
    public void setSeleccionada(Boolean seleccionada) {
        this.seleccionada = seleccionada;
        if (seleccionada) {
            setBorder(bordeSeleccionado);
            setBackground(new Color(60, 60, 60));
        } else {
            setBorder(bordeNormal);
            setBackground(Color.DARK_GRAY);
        }
    }

    /**
     * Obtiene el objeto Pelicula asociado a esta tarjeta.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El objeto Pelicula.
     */
    public Pelicula getPelicula() {
        return pelicula;
    }

    /**
     * Carga la imagen del póster de la película desde una URL en un hilo de fondo
     * para no bloquear la interfaz de usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param urlPoster La URL de la imagen del póster.
     */
    private void cargarImagen(String urlPoster) {
        // SwingWorker es la herramienta de Swing para tareas en segundo plano
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                // Esta parte se ejecuta en un hilo separado (NO en el de la UI)
                try {
                    if (urlPoster == null || urlPoster.isEmpty() || urlPoster.equals("N/A"))
                        return null;
                    // Forma moderna y segura de crear una URL desde un String
                    URL url = new URI(urlPoster).toURL();
                    ImageIcon originalIcon = new ImageIcon(url);

                    if (originalIcon.getIconWidth() <= 0) {
                        return null; // Esto forzará a cargar la imagen por defecto
                    }

                    // Redimensionamos la imagen para que quepa en la tarjeta
                    Image imagenOriginal = originalIcon.getImage();
                    // Redimensionamos para llenar la tarjeta
                    int anchoTarjeta = 180;
                    int altoTarjeta = 250;

                    Image imagenRedimensionada = imagenOriginal.getScaledInstance(anchoTarjeta, altoTarjeta,
                            Image.SCALE_SMOOTH);
                    return new ImageIcon(imagenRedimensionada);
                } catch (Exception ex) {
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
                        cargarImagenPorDefecto();
                    }
                } catch (Exception e) {
                    cargarImagenPorDefecto();
                    e.printStackTrace();
                }
            }
        };
        // ¡Iniciamos el trabajador!
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
            // Buscamos la imagen.
            URL urlImg = getClass().getResource("/imagenes/PosterNoEncontrado.png");

            ImageIcon icon;
            if (urlImg != null) {
                icon = new ImageIcon(urlImg);
            } else {
                // Fallback de seguridad si no encuentra el archivo PNG
                etiquetaPoster.setText("Sin Imagen");
                etiquetaPoster.setForeground(Color.LIGHT_GRAY);
                return;
            }

            Image img = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);

            etiquetaPoster.setIcon(new ImageIcon(img));
            etiquetaPoster.setText(""); // Borrar texto "Cargando..."

            // Aseguramos que el fondo coincida con la tarjeta para que no se vea el cuadro
            // gris
            etiquetaPoster.setBackground(Color.DARK_GRAY);

        } catch (Exception e) {
            etiquetaPoster.setText("Error");
        }
    }
}