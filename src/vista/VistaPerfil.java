package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class VistaPerfil extends JPanel {

    private JLabel lblNombreUsuario;
    private JLabel lblEmail;
    private JLabel lblRol;
    private JPanel panelResenias; // Aquí agregaremos las tarjetas
    private JButton botonVolver; // Renombramos "Cerrar" a "Volver" para que tenga más sentido en navegación

    private final Color COLOR_FONDO = Color.BLACK;
    private final Color COLOR_HEADER = new Color(20, 20, 20); // Gris casi negro
    private final Color COLOR_TEXTO = Color.WHITE;

    public VistaPerfil() {
        setLayout(new BorderLayout());
        setBackground(COLOR_FONDO);

        // --- 1. HEADER (Datos del Usuario) ---
        JPanel panelHeader = new JPanel(new GridBagLayout());
        panelHeader.setBackground(COLOR_HEADER);
        panelHeader.setBorder(new EmptyBorder(25, 30, 25, 30));

        panelHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(50, 50, 50)),
                new EmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // Icono (Genérico)
        JLabel lblIcono = new JLabel();
        // Intenta cargar tu icono de usuario si tienes uno, o usa uno por defecto
        // lblIcono.setIcon(new ImageIcon(...)); 
        
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 65));
        lblIcono.setForeground(Color.WHITE);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 3;
        panelHeader.add(lblIcono, gbc);

        // Panel para los datos del usuario, para centrarlos juntos
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setOpaque(false);
        
        lblNombreUsuario = new JLabel("Usuario");
        lblNombreUsuario.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblNombreUsuario.setForeground(COLOR_TEXTO);
        lblNombreUsuario.setAlignmentX(Component.LEFT_ALIGNMENT); // Alineación correcta para BoxLayout

        lblEmail = new JLabel("email@ejemplo.com");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmail.setForeground(Color.GRAY);
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        lblRol = new JLabel("CUENTA");
        lblRol.setForeground(new Color(100, 180, 255)); // Azulito
        lblRol.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblRol.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelDatos.add(lblNombreUsuario);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 5))); // Pequeño espacio
        panelDatos.add(lblEmail);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 8)));
        panelDatos.add(lblRol);

        gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 3; gbc.anchor = GridBagConstraints.CENTER;
        panelHeader.add(panelDatos, gbc);

        this.add(panelHeader, BorderLayout.NORTH);

        // --- 2. LISTA DE RESEÑAS (Centro) ---
        JLabel lblTituloResenias = new JLabel("Mis Reseñas:");
        lblTituloResenias.setForeground(COLOR_TEXTO);
        lblTituloResenias.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTituloResenias.setBorder(new EmptyBorder(20, 30, 10, 30));
        
        // Panel contenedor vertical
        panelResenias = new JPanel();
        panelResenias.setLayout(new BoxLayout(panelResenias, BoxLayout.Y_AXIS));
        panelResenias.setBackground(COLOR_FONDO);
        panelResenias.setBorder(new EmptyBorder(10, 30, 10, 30));

        JScrollPane scrollPane = new JScrollPane(panelResenias);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(COLOR_FONDO);
        scrollPane.getViewport().setBackground(COLOR_FONDO);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(COLOR_FONDO);
        panelCentro.add(lblTituloResenias, BorderLayout.NORTH);
        panelCentro.add(scrollPane, BorderLayout.CENTER);

        this.add(panelCentro, BorderLayout.CENTER);

        // --- 3. BOTÓN CERRAR (Sur) ---
        JPanel panelSur = new JPanel();
        panelSur.setBackground(COLOR_FONDO);
        panelSur.setBorder(new EmptyBorder(15, 0, 20, 0));
        
        botonVolver = new JButton("Volver al Home"); // Texto más claro
        botonVolver.setPreferredSize(new Dimension(160, 40));
        estilizarBoton(botonVolver);
        
        panelSur.add(botonVolver);
        this.add(panelSur, BorderLayout.SOUTH);
    }

    // --- MÉTODOS DE GESTIÓN ---

    private void estilizarBoton(JButton btn) {
        btn.setBackground(new Color(45, 45, 45));
        btn.setForeground(COLOR_TEXTO);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(80, 80, 80)));
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void setDatosUsuario(String nombre, String email, String rol) {
        lblNombreUsuario.setText(nombre);
        lblEmail.setText(email);
        lblRol.setText(rol);
    }

    public void agregarTarjetaResenia(TarjetaResenia tarjeta) {
        panelResenias.add(tarjeta);
        // Espacio entre tarjetas
        panelResenias.add(Box.createRigidArea(new Dimension(0, 10))); 
        panelResenias.revalidate();
        panelResenias.repaint();
    }

    public void limpiarResenias() {
        panelResenias.removeAll();
        panelResenias.revalidate();
        panelResenias.repaint();
    }

    public JButton getBotonVolver() { // Getter actualizado
        return botonVolver;
    }
}