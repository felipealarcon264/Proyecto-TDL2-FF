package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * La VISTA (V) del Login.
 * Hereda de JPanel y no contiene lógica de negocio, solo componentes visuales.
 * Proporciona getters para que el Controlador (C) pueda acceder a los botones y
 * campos.
 */
public class VistaLogin extends JPanel {

    // Conponentes visuales.
    private JTextField campoEmail;
    private JPasswordField campoPass;
    private JButton botonIngresar;
    private JButton botonRegistrarse;

    private final Color COLOR_FONDO_FORM = Color.BLACK;
    private final Color COLOR_INPUT = new Color(40, 40, 40);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_ACENTO = new Color(206, 80, 84);//Distintivo de app.

    public VistaLogin() {
        // 1 fila con 2 columnas para la imagen y los datos.
        this.setLayout(new GridLayout(1, 2));
        this.setBackground(Color.BLACK);

        // Panel izquierdo -> Imagen.
        // --- PANEL IZQUIERDO (IMAGEN - ESTIRADA AL 100%) ---
        // Creamos un panel anónimo que sabe pintarse a sí mismo
        JPanel panelImagen = new JPanel() {
            // Cargamos la imagen UNA sola vez en memoria
            private Image imagenFondo;
            {
                try {
                    imagenFondo = new ImageIcon(getClass().getResource("/imagenes/TDL2.png")).getImage();
                } catch (Exception e) {
                    imagenFondo = null; // Por si falla
                }
                setBackground(Color.BLACK);
            }

            // Sobreescribimos el método de pintado del panel
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Si la imagen cargó bien, la dibujamos estirada
                if (imagenFondo != null) {
                    // g.drawImage(imagen, x, y, ancho_destino, alto_destino, observador)
                    // Usamos getWidth() y getHeight() para ocupar todo el panel actual
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        // Ya no hace falta agregar JLabels ni layouts a este panel. Se pinta solo.

        // Lado derecho -> Formulario del login.
        JPanel panelFormulario = new JPanel(new GridLayout(3, 1));
        panelFormulario.setBorder(new EmptyBorder(50, 50, 25, 25)); // Espaciado interno
        panelFormulario.setBackground(COLOR_FONDO_FORM); // Color fondo

        // Titulo Iniciar Sesion.
        JLabel etiquetaTitulo = new JLabel("Iniciar Sesión");
        etiquetaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 40));
        etiquetaTitulo.setForeground(COLOR_ACENTO);
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER); // Centramos el texto

        // Campo Email y titulo
        JPanel panelCampos = new JPanel(new GridLayout(4, 1, 0, 10));
        panelCampos.setBackground(COLOR_FONDO_FORM);
        panelCampos.setBorder(new EmptyBorder(20, 0, 20, 0));

        JLabel etiquetaEmail = new JLabel("Correo Electrónico:");
        etiquetaEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        etiquetaEmail.setForeground(Color.LIGHT_GRAY);

        campoEmail = new JTextField();
        estilizarInput(campoEmail);

        JLabel etiquetaPass = new JLabel("Contraseña:");
        etiquetaPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        etiquetaPass.setForeground(Color.LIGHT_GRAY);

        campoPass = new JPasswordField();
        estilizarInput(campoPass);

        panelCampos.add(etiquetaEmail);
        panelCampos.add(campoEmail);
        panelCampos.add(etiquetaPass);
        panelCampos.add(campoPass);

        // Botones y registros.
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 1));
        panelBotones.setBackground(COLOR_FONDO_FORM);

        botonIngresar = new JButton("Ingresar");
        botonIngresar.setBackground(COLOR_ACENTO);
        botonIngresar.setForeground(Color.BLACK);
        botonIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botonIngresar.setFocusPainted(false);
        botonIngresar.setOpaque(true);
        botonIngresar.setBorderPainted(false);
        botonIngresar.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel etiquetaRegistro = new JLabel("¿No tienes una cuenta?");
        etiquetaRegistro.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaRegistro.setForeground(Color.GRAY);

        botonRegistrarse = new JButton("Registrarse ahora");
        botonRegistrarse.setBackground(COLOR_FONDO_FORM); // Fondo negro
        botonRegistrarse.setForeground(COLOR_ACENTO); // Texto naranja
        botonRegistrarse.setOpaque(true);
        botonRegistrarse.setBorderPainted(false);
        botonRegistrarse.setBorder(null);
        botonRegistrarse.setFocusPainted(false);
        botonRegistrarse.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBotones.add(botonIngresar);
        panelBotones.add(etiquetaRegistro);
        panelBotones.add(botonRegistrarse);

        // Armamos el panel de formulario.
        panelFormulario.add(etiquetaTitulo);
        panelFormulario.add(panelCampos);
        panelFormulario.add(panelBotones);

        // Se añaden ambas partes del panel.
        this.add(panelImagen);
        this.add(panelFormulario);

    }

    private void estilizarInput(JComponent input) {
        input.setBackground(COLOR_INPUT);
        input.setForeground(COLOR_TEXTO);
        input.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        input.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(60, 60, 60)),
                new EmptyBorder(5, 10, 5, 10)
        ));
        if (input instanceof JTextField) {
            JTextField campo = (JTextField) input;
            // Cursor BLANCO para que se vea en el fondo oscuro
            campo.setCaretColor(Color.WHITE);
            campo.getCaret().setBlinkRate(0);
        }
    }

    // Getters and Setters para el uso del controlador.

    public String getEmail() {
        return campoEmail.getText();
    }

    public String getPassword() {
        return new String(campoPass.getPassword());
    }

    public JButton getBotonIngresar() {
        return botonIngresar;
    }

    public JButton getBotonRegistrarse() {
        return botonRegistrarse;
    }

    public void mostrarError(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Error de Ingreso de Sesión",
                javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Sobrescribimos addNotify para establecer el tamaño mínimo y máximo
     * de la ventana contenedora una vez que este panel es añadido.
     */
    @Override
    public void addNotify() {
        super.addNotify(); // Es importante llamar al método de la superclase

        // Obtenemos la ventana padre (el JFrame)
        java.awt.Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            // Establecemos el tamaño mínimo y máximo para esa ventana
            Dimension dimensionUnica = new Dimension(1100,660);
            window.setMinimumSize(dimensionUnica);
            window.setPreferredSize(dimensionUnica);
            window.setBackground(Color.BLACK);
        }
    }

    /**
     * Limpia los campos de la vista.
     */
    public void limpiarCampos() {
        campoEmail.setText("");
        campoPass.setText("");
    }

}
