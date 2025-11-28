//Verificacion JavaDoc -> Realizada.
package controlador;

import excepciones.CampoVacio;
import excepciones.DatosInvalidosException;
import servicio.ServicioResenia;
import vista.VistaResenia;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Color;
import javax.swing.border.Border;
import modelo.catalogo.Contenido;
import modelo.ente.Usuario;

/**
 * Controlador para la vista de creación de reseñas.
 * Gestiona la interacción del usuario para puntuar y comentar un contenido,
 * y guarda la reseña a través del servicio correspondiente.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ControladorResenia {
    private VistaResenia vista;
    private ServicioResenia servicioResenia;
    private Usuario usrActual;
    private Contenido contenidoActual;
    private int puntuacionActual;

    /**
     * Constructor del ControladorResenia.
     * Inicializa los componentes y asigna los listeners a los botones de la vista.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param vista           La vista de reseña (VistaResenia) que este controlador
     *                        gestiona.
     * @param servicioResenia El servicio para la lógica de negocio relacionada con
     *                        las reseñas.
     * @param usrActual       El usuario que está creando la reseña.
     * @param contenidoActual El contenido (película) que se está reseñando.
     */
    public ControladorResenia(VistaResenia vista, ServicioResenia servicioResenia, Usuario usrActual,
            Contenido contenidoActual) {
        this.vista = vista;
        this.servicioResenia = servicioResenia;
        this.vista.getBotonGuardar().addActionListener(new BotonGuardarListener());
        // Asignar el listener a cada botón de puntuación
        for (JButton boton : vista.getBotonesPuntuacion()) {
            boton.addActionListener(new BotonesPuntuacion());
        }
        this.usrActual = usrActual;
        this.contenidoActual = contenidoActual;
        this.puntuacionActual = 0;
    }

    /**
     * Clase interna que implementa ActionListener para el botón de guardar.
     * Se encarga de procesar el guardado de la reseña cuando el usuario hace clic
     * en el botón.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    class BotonGuardarListener implements ActionListener {
        /**
         * Maneja el evento de clic en el botón "Guardar".
         * Intenta crear una nueva reseña y muestra mensajes de éxito o error.
         * 
         * @author Grupo 4 - Proyecto TDL2
         * @version 1.0
         * @param e El evento de acción que se ha producido.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                servicioResenia.crearNuevaResenia(usrActual, contenidoActual, puntuacionActual,
                        vista.getTxtComentario());
                javax.swing.JOptionPane.showMessageDialog(vista, "Reseña guardada. Será revisada por un administrador.",
                        "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
            } catch (CampoVacio | DatosInvalidosException ex) {
                javax.swing.JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error al guardar",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Clase interna que implementa ActionListener para los botones de puntuación.
     * Gestiona la selección de la puntuación y actualiza la interfaz para reflejar
     * la selección.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    class BotonesPuntuacion implements ActionListener {
        // Creo un borde "vacío" o por defecto para cuando no están seleccionados
        private final Border bordeNormal = UIManager.getBorder("Button.border");
        private final Border bordeSeleccionado = BorderFactory.createLineBorder(Color.ORANGE, 3);

        /**
         * Maneja el evento de clic en cualquiera de los botones de puntuación (1-5).
         * Actualiza la puntuación seleccionada y resalta visualmente el botón
         * presionado.
         * 
         * @author Grupo 4 - Proyecto TDL2
         * @version 1.0
         * @param e El evento de acción que se ha producido.
         */
        public void actionPerformed(ActionEvent e) {
            JButton botonPresionado = (JButton) e.getSource();
            puntuacionActual = Integer.parseInt(botonPresionado.getText());
            // Resaltar el botón seleccionado
            for (JButton b : vista.getBotonesPuntuacion()) {
                b.setBorder(bordeNormal);
                b.setBackground(null); // Resetear color de todos
                b.setOpaque(false);
            }
            // para enmarcar solo el presionado.
            botonPresionado.setBorder(bordeSeleccionado);
            botonPresionado.setOpaque(true);
        }
    }

}
