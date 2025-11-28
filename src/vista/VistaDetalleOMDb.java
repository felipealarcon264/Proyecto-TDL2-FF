//Verificacion JavaDoc -> Realizada.
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import modelo.catalogo.Pelicula;

import java.awt.*;

/**
 * Un JDialog que muestra los detalles completos de una película seleccionada.
 * Incluye el póster, título, año, duración, director, género, rating y
 * sinopsis.
 * Proporciona botones para cerrar la vista y para acceder a la funcionalidad de
 * reseña.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class VistaDetalleOMDb extends JDialog {
    private JButton botonCerrar;
    private JButton botonMiResenia;
    private final Color COLOR_ACENTO = new Color(206, 80, 84);// Distintivo de la app.

    /**
     * Constructor de VistaDetalleOMDb.
     * Configura la ventana de diálogo con toda la información de la película
     * proporcionada.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param propietario El Frame padre sobre el cual este diálogo será modal.
     * @param pelicula    El objeto Pelicula cuyos detalles se mostrarán.
     */
    public VistaDetalleOMDb(Frame propietario, Pelicula pelicula) {
        super(propietario, "Detalle de la Película", true);
        setSize(600, 450);
        setLocationRelativeTo(propietario);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 30, 30));

        // ---Seccion poster con boton Mi reseña---
        JPanel panelIzq = new JPanel();
        panelIzq.setBackground(new Color(30, 30, 30));
        panelIzq.setLayout(new BorderLayout());

        // implementacion del posterr
        JLabel labelPoster = new JLabel();
        labelPoster.setPreferredSize(new Dimension(250, 0));
        labelPoster.setHorizontalAlignment(SwingConstants.CENTER);
        labelPoster.setBorder(new EmptyBorder(10, 10, 10, 10));

        // se reutiliza la lógica de TarjetaPelicula
        try {
            ImageIcon iconoImagen = new ImageIcon(new java.net.URI(pelicula.getPoster()).toURL());
            Image img = iconoImagen.getImage().getScaledInstance(230, 340, Image.SCALE_SMOOTH);
            labelPoster.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            labelPoster.setText("Sin Imagen");
            labelPoster.setForeground(Color.WHITE);
        }
        panelIzq.add(labelPoster, BorderLayout.CENTER); // Póster en el centro del panel izquierdo
        botonMiResenia = new JButton("Mi reseña");
        panelIzq.add(botonMiResenia, BorderLayout.SOUTH); // Botón abajo del póster

        add(panelIzq, BorderLayout.WEST);

        // se muestra la info de la Pelicula
        JPanel panelInformacion = new JPanel();
        panelInformacion.setLayout(new BoxLayout(panelInformacion, BoxLayout.Y_AXIS));
        panelInformacion.setBackground(new Color(30, 30, 30));
        panelInformacion.setBorder(new EmptyBorder(20, 10, 20, 20));

        JLabel labelTitulo = new JLabel("<html><h1>" + pelicula.getTitulo() + "</h1></html>");
        labelTitulo.setForeground(COLOR_ACENTO);

        JLabel labelDatos = new JLabel("<html><p><b>Año:</b> " + pelicula.getAnio() +
                "<br><b>Duración:</b> " + pelicula.getDuracion() + " min" +
                "<br><b>Director:</b> " + pelicula.getDirector() +
                "<br><b>Género:</b> " + pelicula.getGenero() +
                "<br><b>Rating:</b> " + pelicula.getRatingPromedio() + " ⭐</p></html>");
        labelDatos.setForeground(Color.WHITE);
        labelDatos.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextArea txtResumen = new JTextArea(pelicula.getResumen());
        txtResumen.setLineWrap(true);
        txtResumen.setWrapStyleWord(true);
        txtResumen.setEditable(false);
        txtResumen.setBackground(new Color(50, 50, 50));
        txtResumen.setForeground(Color.WHITE);
        txtResumen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        panelInformacion.add(labelTitulo);
        panelInformacion.add(Box.createRigidArea(new Dimension(0, 10)));
        panelInformacion.add(labelDatos);
        panelInformacion.add(Box.createRigidArea(new Dimension(0, 15)));
        panelInformacion.add(new JLabel("Sinopsis:") {
            {
                setForeground(Color.ORANGE);
            }
        });
        panelInformacion.add(new JScrollPane(txtResumen));

        add(panelInformacion, BorderLayout.CENTER);

        // el boton para cerrar
        botonCerrar = new JButton("Cerrar");

        JPanel panelSur = new JPanel();
        panelSur.setBackground(new Color(30, 30, 30));
        panelSur.add(botonCerrar);
        add(panelSur, BorderLayout.SOUTH);
    }

    /**
     * Obtiene el botón para cerrar la ventana de detalles.
     * Permite al controlador añadir un ActionListener.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El JButton para la acción de cerrar.
     */
    public JButton getBotonCerrar() {
        return botonCerrar;
    }

    /**
     * Obtiene el botón para escribir o ver una reseña de la película.
     * Permite al controlador añadir un ActionListener.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return El JButton para la acción de reseña.
     */
    public JButton getBotonMiResenia() {
        return botonMiResenia;
    }

}
