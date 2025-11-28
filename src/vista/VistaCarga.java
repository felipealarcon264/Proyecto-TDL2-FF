//Verificacion JavaDoc -> Realizada.
package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Vista para mostrar la pantalla de carga.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class VistaCarga extends JPanel {

    /**
     * Constructor de la vista de carga.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public VistaCarga() {
        setLayout(new GridBagLayout()); // Centrado perfecto

        this.setBackground(Color.BLACK);

        // Crear icono gif.
        ImageIcon loadingIcon = new ImageIcon(getClass().getResource("/gif/Logo-corriendo.gif"));

        // Creamos la etiqueta con Texto E Imagen
        JLabel etiquetaCarga = new JLabel("Cargando catálogo...", loadingIcon, JLabel.CENTER);

        // Ajustamos texto
        etiquetaCarga.setFont(new Font("Arial", Font.BOLD, 18));
        etiquetaCarga.setForeground(Color.WHITE);

        // Pone el texto DEBAJO de la imagen (más estético)
        etiquetaCarga.setHorizontalTextPosition(JLabel.CENTER);
        etiquetaCarga.setVerticalTextPosition(JLabel.BOTTOM);

        add(etiquetaCarga);
    }
}