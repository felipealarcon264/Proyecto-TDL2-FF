package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

    public VistaLogin() {
        // 1 fila con 2 columnas para la imagen y los datos.
        this.setLayout(new GridLayout(1, 2));
        this.setBackground(Color.DARK_GRAY);

        // Panel izquierdo -> Imagen.
        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.setBackground(Color.WHITE); // Fondo oscuro

        // Se carga la imagen.
        JLabel imagen;
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/TDL2.png"));
            imagen = new JLabel(iconoOriginal);
            panelImagen.add(imagen, BorderLayout.CENTER);
        } catch (Exception e) {
            imagen = new JLabel("Error al cargar la imagen");
            imagen.setHorizontalAlignment(SwingConstants.CENTER);
            panelImagen.add(imagen, BorderLayout.CENTER);
        }

        // Lado derecho -> Formulario del login.
        JPanel panelFormulario = new JPanel(new GridLayout(3, 1));
        panelFormulario.setBorder(new EmptyBorder(50, 50, 25, 25)); // Espaciado interno
        panelFormulario.setBackground(Color.WHITE); // Color fondo

        // Titulo Iniciar Sesion.
        JLabel etiquetaTitulo = new JLabel("Iniciar Sesión");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 50));
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER); // Centramos el texto

        // Campo Email y titulo
        JPanel panelCampos = new JPanel(new GridLayout(2, 1, 1, 1)); // 2 filas, 1 columna
        panelCampos.setBackground(Color.WHITE);
        // Parte E-Mail
        JPanel panelFilaEmail = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Alineado a la izq.
        panelFilaEmail.setBackground(Color.WHITE);
        JLabel etiquetaEmail = new JLabel("Correo Electrónico:");
        etiquetaEmail.setFont(new Font("Arial", Font.BOLD, 16));
        campoEmail = new JTextField();
        Dimension tamFijoCampos = new Dimension(250, 30); // Un ancho fijo
        campoEmail.setPreferredSize(tamFijoCampos);// Tamaño fijo.
        campoEmail.setMinimumSize(tamFijoCampos);// Tamaño fijo.
        campoEmail.setMaximumSize(tamFijoCampos);// Tamaño fijo.
        panelFilaEmail.add(etiquetaEmail);
        panelFilaEmail.add(campoEmail);
        // Parte contraseña.
        JPanel panelFilaPass = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelFilaPass.setBackground(Color.WHITE);
        JLabel etiquetaPass = new JLabel("Contraseña:");
        etiquetaPass.setFont(new Font("Arial", Font.BOLD, 16));
        campoPass = new JPasswordField();
        campoPass.setPreferredSize(tamFijoCampos);// Tamaño fijo.
        campoPass.setMinimumSize(tamFijoCampos);// Tamaño fijo.
        campoPass.setMaximumSize(tamFijoCampos);// Tamaño fijo.
        panelFilaPass.add(etiquetaPass);
        panelFilaPass.add(campoPass);
        // Se añaden los campos.
        panelCampos.add(panelFilaEmail);
        panelCampos.add(panelFilaPass);

        // Botones y registros.
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 1));
        panelBotones.setBackground(Color.WHITE);
        botonIngresar = new JButton("Ingresar");
        JLabel etiquetaRegistro = new JLabel("¿No tienes una cuenta?");
        etiquetaRegistro.setHorizontalAlignment(SwingConstants.CENTER);
        botonRegistrarse = new JButton("Registrarse ahora");
        panelBotones.add(botonIngresar);
        panelBotones.add(etiquetaRegistro);
        panelBotones.add(botonRegistrarse);

        // Armamos el panel de formulario.
        panelFormulario.add(etiquetaTitulo, BorderLayout.NORTH);
        panelFormulario.add(panelCampos, BorderLayout.CENTER);
        panelFormulario.add(panelBotones, BorderLayout.SOUTH);

        // Se añaden ambas partes del panel.
        this.add(panelImagen);
        this.add(panelFormulario);

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
            Dimension dimensionUnica = new Dimension(1100,659);
            window.setMinimumSize(dimensionUnica);
            window.setPreferredSize(dimensionUnica);
            window.setMaximumSize(dimensionUnica);
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
