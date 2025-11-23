package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class VistaRegistro extends JPanel {

    private JTextField campoNombre, campoApellido, campoDNI, campoNomUsr, campoEmail;
    private JPasswordField campoPass;
    private JButton botonRegistrar, botonVolver;

    // Theme Colors
    private final Color COLOR_BG = Color.BLACK;
    private final Color COLOR_INPUT = new Color(40, 40, 40);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_ACENTO = new Color(255, 140, 0);

    public VistaRegistro() {
        // 1. Layout Principal: 2 Columnas
        this.setLayout(new GridLayout(1, 2));
        this.setBackground(COLOR_BG);

        // --- A. PANEL IZQUIERDO (IMAGEN) ---
        JPanel panelIzquierdo = new JPanel(new GridBagLayout());
        panelIzquierdo.setBackground(COLOR_BG);

        JLabel etiquetaImagen = new JLabel();
        try {
            ImageIcon original = new ImageIcon(getClass().getResource("/imagenes/ImageRegister.png"));
            // Tamaño del perrito
            Image img = original.getImage().getScaledInstance(420, 420, Image.SCALE_SMOOTH);
            etiquetaImagen.setIcon(new ImageIcon(img));
        } catch (Exception e) { /* Ignorar */ }


        GridBagConstraints gbcImg = new GridBagConstraints();
        gbcImg.gridx = 0;
        gbcImg.gridy = 0;
        gbcImg.weightx = 1.0;
        gbcImg.weighty = 1.0;
        gbcImg.anchor = GridBagConstraints.EAST;
        gbcImg.insets = new Insets(0, 0, 50, 10);

        panelIzquierdo.add(etiquetaImagen, gbcImg);

        // --- B. PANEL DERECHO (FORMULARIO) ---
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBackground(COLOR_BG);

        // Contenedor vertical del contenido
        JPanel panelContenidoDerecho = new JPanel();
        panelContenidoDerecho.setLayout(new BoxLayout(panelContenidoDerecho, BoxLayout.Y_AXIS));
        panelContenidoDerecho.setBackground(COLOR_BG);

        // CAMBIO CLAVE: Margen izquierdo de 50px para separarlo un poco del centro, pero mantenerlo cerca
        panelContenidoDerecho.setBorder(new EmptyBorder(0, 50, 0, 0));

        // 1. Título
        JLabel etiquetaTitulo = new JLabel("🍿 ¡Únete a TDL2! 🍿");
        etiquetaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        etiquetaTitulo.setForeground(COLOR_ACENTO);
        etiquetaTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 2. Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 15, 25));
        panelFormulario.setBackground(COLOR_BG);
        panelFormulario.setMaximumSize(new Dimension(600, 450));

        campoNombre = crearInput();
        agregarCampo(panelFormulario, "Nombre:", campoNombre);

        campoApellido = crearInput();
        agregarCampo(panelFormulario, "Apellido:", campoApellido);

        campoDNI = crearInput();
        agregarCampo(panelFormulario, "DNI:", campoDNI);

        campoNomUsr = crearInput();
        agregarCampo(panelFormulario, "Usuario:", campoNomUsr);

        campoEmail = crearInput();
        agregarCampo(panelFormulario, "Email:", campoEmail);

        campoPass = new JPasswordField();
        estilizarInput(campoPass);
        agregarCampo(panelFormulario, "Contraseña:", campoPass);

        // 3. Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panelBotones.setBackground(COLOR_BG);
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);

        botonVolver = new JButton("Volver");
        estilizarBotonSecundario(botonVolver);
        botonVolver.setPreferredSize(new Dimension(130, 50));

        botonRegistrar = new JButton("REGISTRARME");
        botonRegistrar.setBackground(COLOR_ACENTO);
        botonRegistrar.setForeground(Color.BLACK);
        botonRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botonRegistrar.setPreferredSize(new Dimension(200, 50));

        botonRegistrar.setFocusPainted(false);
        botonRegistrar.setOpaque(true);
        botonRegistrar.setBorderPainted(false);
        botonRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBotones.add(botonVolver);
        panelBotones.add(botonRegistrar);

        // Armado del lado derecho
        panelContenidoDerecho.add(etiquetaTitulo);
        panelContenidoDerecho.add(Box.createRigidArea(new Dimension(0, 40)));
        panelContenidoDerecho.add(panelFormulario);
        panelContenidoDerecho.add(Box.createRigidArea(new Dimension(0, 30)));
        panelContenidoDerecho.add(panelBotones);

        // Anclar el contenido al OESTE (Izquierda) del panel derecho
        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.gridx = 0;
        gbcForm.gridy = 0;
        gbcForm.weightx = 4.0;
        gbcForm.weighty = 4.0;
        gbcForm.anchor = GridBagConstraints.WEST;

        panelDerecho.add(panelContenidoDerecho, gbcForm);

        // Añadimos los dos grandes paneles
        this.add(panelIzquierdo);
        this.add(panelDerecho);
    }

    private void agregarCampo(JPanel panel, String titulo, JComponent input) {
        JLabel lbl = new JLabel(titulo);
        lbl.setForeground(Color.LIGHT_GRAY);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl.setBorder(new EmptyBorder(0, 0, 0, 20));

        panel.add(lbl);
        panel.add(input);
    }

    private JTextField crearInput() {
        JTextField txt = new JTextField();
        estilizarInput(txt);
        return txt;
    }

    private void estilizarInput(JComponent input) {
        input.setBackground(COLOR_INPUT);
        input.setForeground(COLOR_TEXTO);
        input.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        input.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(80, 80, 80)),
                new EmptyBorder(8, 10, 8, 10)
        ));
        if (input instanceof JTextField) {
            ((JTextField)input).setCaretColor(Color.WHITE);
        }
    }

    private void estilizarBotonSecundario(JButton btn) {
        btn.setBackground(new Color(60, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(Color.GRAY));
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Getters
    public String getCampoNombre() { return campoNombre.getText(); }
    public String getCampoApellido() { return campoApellido.getText(); }
    public String getCampoDNI() { return campoDNI.getText(); }
    public String getCampoNombreUsr() { return campoNomUsr.getText(); }
    public String getCampoCorreoElectronico() { return campoEmail.getText(); }
    public String getCampoContraseña() { return new String(campoPass.getPassword()); }
    public JButton getBotonRegistrar() { return botonRegistrar; }
    public JButton getBotonVolver() { return botonVolver; }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }
    public void limpiarCampos() {
        campoNombre.setText(""); campoApellido.setText(""); campoDNI.setText("");
        campoEmail.setText(""); campoPass.setText(""); campoNomUsr.setText("");
    }
}