package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Una vista simple que se muestra mientras la aplicación realiza tareas pesadas
 * en segundo plano, como cargar el catálogo de películas.
 */
public class VistaCarga extends JPanel {

    public VistaCarga() {
        // Usamos GridBagLayout para centrar fácilmente el componente en el panel
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 30)); // Mismo fondo oscuro que el panel superior de Home

        JLabel etiquetaCarga = new JLabel("Cargando catálogo, por favor espere...");
        etiquetaCarga.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaCarga.setForeground(Color.WHITE);

        // Opcional: Si tuvieras un GIF de carga en /imagenes/loading.gif
        // ImageIcon loadingIcon = new ImageIcon(getClass().getResource("/imagenes/loading.gif"));
        // etiquetaCarga.setIcon(loadingIcon);

        add(etiquetaCarga); // Lo añadimos al panel centrado
    }
}