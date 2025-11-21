
package controlador;

import excepciones.CampoVacio;
import excepciones.DatosInvalidosException;
import servicio.ServicioResenia;
import vista.VistaResenia;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

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
        public void actionPerformed(ActionEvent e) {
            JButton boton = (JButton) e.getSource();
            puntuacionActual = Integer.parseInt(boton.getText());
            // Resaltar el botón seleccionado
            for (JButton b : vista.getBotonesPuntuacion()) {
                b.setBackground(null); // Resetear color de todos
            }
            boton.setBackground(java.awt.Color.YELLOW); // Resaltar el seleccionado
        }
    }

}
