package vista;

import javax.swing.*;
import java.awt.*;

public class VistaCarga extends JPanel {

    public VistaCarga() {
        setLayout(new GridBagLayout()); // Centrado perfecto

        // TRUCO: Si tu GIF tiene fondo negro, pon el panel en NEGRO.
        // Si el GIF tiene transparencia, puedes usar tu gris (30, 30, 30).
        // Ante la duda, BLACK suele disimular mejor los bordes de los gifs.
        this.setBackground(Color.BLACK); 

        // Crear el icono (TIENE QUE SER .GIF, no .mp4)
        // Asegúrate de tener el archivo en src/imagenes/
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