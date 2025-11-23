
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

public class ControladorResenia {
    private VistaResenia vista;
    private ServicioResenia servicioResenia;
    private Usuario usrActual;
    private Contenido contenidoActual;
    private int puntuacionActual;

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

    class BotonGuardarListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                servicioResenia.crearNuevaResenia(usrActual, contenidoActual, puntuacionActual,
                        vista.getTxtComentario());
                // Aquí podrías mostrar un mensaje de éxito y cerrar la ventana si lo deseas.
                javax.swing.JOptionPane.showMessageDialog(vista, "Reseña guardada. Será revisada por un administrador.",
                        "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
            } catch (CampoVacio | DatosInvalidosException ex) {
                javax.swing.JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error al guardar",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class BotonesPuntuacion implements ActionListener{
        // Creo un borde "vacío" o por defecto para cuando no están seleccionados
        private final Border bordeNormal = UIManager.getBorder("Button.border");
        private final Border bordeSeleccionado = BorderFactory.createLineBorder(Color.ORANGE, 3);

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
