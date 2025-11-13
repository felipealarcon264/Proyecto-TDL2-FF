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
        // 1 fila y 2 columnas para tener [Imagen | Formulario]
        this.setLayout(new GridLayout(1, 2));
        this.setBackground(Color.DARK_GRAY);

        // Panel izquierdo -> Imagen.
        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.setBackground(Color.WHITE); // Fondo oscuro

        // Se carga la imagen.
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/TDL2.png"));

        // Escalamos la imagen para que se ajuste.
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(600, 700, Image.SCALE_SMOOTH);
        JLabel etiquetaImagen = new JLabel(new ImageIcon(imagenEscalada));
        panelImagen.add(etiquetaImagen, BorderLayout.CENTER);

        // Lado derecho - Formulario del login.
        JPanel panelFormulario = new JPanel(new BorderLayout(10, 10));
        panelFormulario.setBorder(new EmptyBorder(50, 50, 50, 50)); // Espaciado interno
        panelFormulario.setBackground(Color.WHITE); // Color fondo

        // --- 3a. Título ---
        JLabel etiquetaTitulo = new JLabel("Iniciar Sesión");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER); // Centramos el texto

        // Campo Email y titulo
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCampos.setBackground(Color.WHITE);

        panelCampos.add(new JLabel("Email:")).setFont(new Font("Arial", Font.BOLD, 24));
        campoEmail = new JTextField();
        panelCampos.add(campoEmail);
        panelCampos.add(new JLabel("Contraseña:")).setFont(new Font("Arial", Font.BOLD, 24));
        campoPass = new JPasswordField();
        panelCampos.add(campoPass);

        // Botones y registros
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBackground(Color.WHITE);

        botonIngresar = new JButton("Ingresar");

        JLabel etiquetaRegistro = new JLabel("¿No tienes una cuenta?");
        etiquetaRegistro.setHorizontalAlignment(SwingConstants.CENTER);

        botonRegistrarse = new JButton("Registrarse ahora");

        panelBotones.add(botonIngresar);
        panelBotones.add(etiquetaRegistro);
        panelBotones.add(botonRegistrarse);

        // Armamos el panel de formulario
        panelFormulario.add(etiquetaTitulo, BorderLayout.NORTH);
        panelFormulario.add(panelCampos, BorderLayout.CENTER);
        panelFormulario.add(panelBotones, BorderLayout.SOUTH);

        // --- 4. Armado Final ---
        // Añadimos el panel de imagen (izquierda) y el de formulario (derecha)
        this.add(panelImagen);
        this.add(panelFormulario);

    }

    // --- GETTERS PARA EL CONTROLADOR ---
    // Métodos para que el Controlador pueda leer los datos de la Vista

    public String getEmail() {
        return campoEmail.getText();
    }

    public String getPassword() {
        // JPasswordField devuelve char[], lo convertimos a String
        return new String(campoPass.getPassword());
    }

    // Métodos para que el Controlador pueda "escuchar" los botones de la Vista

    public JButton getBotonIngresar() {
        return botonIngresar;
    }

    public JButton getBotonRegistrarse() {
        return botonRegistrarse;
    }

    // Método para que el Controlador pueda mostrar errores
    public void mostrarError(String mensaje) {
        // JOptionPane es un componente de Swing
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Error de Login",
                javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}