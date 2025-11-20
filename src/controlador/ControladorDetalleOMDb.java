package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaDetalleOMDb;

public class ControladorDetalleOMDb implements ActionListener {

    private VistaDetalleOMDb vista;

    public ControladorDetalleOMDb(VistaDetalleOMDb vista) {
        this.vista = vista;
        this.vista.getBotonCerrar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonCerrar()) {
            // La lógica es simplemente cerrar la ventana
            vista.dispose();
        }
    }
}