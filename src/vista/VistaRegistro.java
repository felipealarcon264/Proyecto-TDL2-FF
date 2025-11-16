package vista;

import java.awt.*;
import javax.swing.ImageIcon; // Importación nueva
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Vista del Registro.
 * Contiene los campos para registrar un nuevo usuario, con una imagen de
 * bienvenida.
 */
public class VistaRegistro extends JPanel {

    // Campos para Datos_Personales
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoDNI;

    // Campos para Usuario (Cuenta)
    private JTextField campoNomUsr;
    private JTextField campoEmail;
    private JPasswordField campoPass;

    // Botones
    private JButton botonRegistrar;
    private JButton botonVolver;

    public VistaRegistro() {
        // Establecemos el BorderLayout como layout principal para tener más control
        this.setLayout(new BorderLayout(20, 20)); // Espaciado entre componentes
        this.setBorder(new EmptyBorder(20, 20, 20, 20)); // Margen exterior
        this.setPreferredSize(new Dimension(800, 550));
        // --- Panel Superior: Imagen y Título ---
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10)); // Otro BorderLayout interno

        // 1. Imagen del perrito
        // Asegúrate que la ruta "/imagenes/TDL2.png" sea correcta y la imagen exista
        ImageIcon perritoIcon;
        try {
            perritoIcon = new ImageIcon(getClass().getResource("/imagenes/ImageRegister.png"));
            // Redimensionar si es necesario (ejemplo: 100x100)
            Image imagenRedimensionada = perritoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            perritoIcon = new ImageIcon(imagenRedimensionada);
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen del perrito: /imagenes/TDL2.png");
            perritoIcon = new ImageIcon(); // Imagen vacía para evitar NPE
        }
        JLabel etiquetaPerrito = new JLabel(perritoIcon);
        etiquetaPerrito.setHorizontalAlignment(JLabel.CENTER);
        panelSuperior.add(etiquetaPerrito, BorderLayout.WEST);

        // 2. Título principal
        JLabel etiquetaTitulo = new JLabel("¡Únete a TDL2 y Empieza la Diversión!");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaTitulo.setHorizontalAlignment(JLabel.CENTER); // Centrar el texto
        panelSuperior.add(etiquetaTitulo, BorderLayout.CENTER); // Colocar en el centro

        this.add(panelSuperior, BorderLayout.NORTH); // Añadir el panel superior a la parte superior de la vista

        // --- Panel Central: El Formulario ---
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));

        panelFormulario.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        panelFormulario.add(campoNombre);

        panelFormulario.add(new JLabel("Apellido:"));
        campoApellido = new JTextField();
        panelFormulario.add(campoApellido);

        panelFormulario.add(new JLabel("DNI:"));
        campoDNI = new JTextField();
        panelFormulario.add(campoDNI);

        panelFormulario.add(new JLabel("Nombre de usuario:"));
        campoNomUsr = new JTextField();
        panelFormulario.add(campoNomUsr);

        panelFormulario.add(new JLabel("Email:"));
        campoEmail = new JTextField();
        panelFormulario.add(campoEmail);

        panelFormulario.add(new JLabel("Contraseña:"));
        campoPass = new JPasswordField();
        panelFormulario.add(campoPass);

        this.add(panelFormulario, BorderLayout.CENTER); // Añadir el formulario al centro de la vista

        // --- Panel Inferior: Los Botones ---
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10)); // GridLayout para los botones
        botonRegistrar = new JButton("Registrarme");
        panelBotones.add(botonRegistrar);

        botonVolver = new JButton("Volver al Inicio");
        panelBotones.add(botonVolver);

        this.add(panelBotones, BorderLayout.SOUTH); // Añadir los botones a la parte inferior de la vista

    }

    // --- Getters para el Controlador ---

    public String getCampoNombre() {
        return campoNombre.getText();
    }

    public String getCampoApellido() {
        return campoApellido.getText();
    }

    public String getCampoDNI() {
        return campoDNI.getText();
    }

    public String getCampoNombreUsr() {
        return campoNomUsr.getText();
    }

    public String getCampoCorreoElectronico() {
        return campoEmail.getText();
    }

    public String getCampoContraseña() {
        return new String(campoPass.getPassword());
    }

    public JButton getBotonRegistrar() {
        return botonRegistrar;
    }

    public JButton getBotonVolver() {
        return botonVolver;
    }

    // --- Métodos para Feedback (igual que en Login) ---
    public void mostrarError(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Error de Registro",
                javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Registro Exitoso",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
}