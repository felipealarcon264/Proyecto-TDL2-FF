package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VistaPerfil extends JPanel {

    private JLabel lblNombreUsuario;
    private JLabel lblEmail;
    private JLabel lblRol;
    private JPanel panelResenias; // Aquí agregaremos las tarjetas
    private JButton botonVolver; // Renombramos "Cerrar" a "Volver" para que tenga más sentido en navegación

    public VistaPerfil() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));

        // --- 1. HEADER (Datos del Usuario) ---
        JPanel panelHeader = new JPanel(new GridBagLayout());
        panelHeader.setBackground(new Color(40, 40, 40));
        panelHeader.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // Icono (Genérico)
        JLabel lblIcono = new JLabel();
        // Intenta cargar tu icono de usuario si tienes uno, o usa uno por defecto
        // lblIcono.setIcon(new ImageIcon(...)); 
        lblIcono.setText("👤"); // Emoji simple si no hay icono
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblIcono.setForeground(Color.WHITE);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 3;
        panelHeader.add(lblIcono, gbc);

        // Panel para los datos del usuario, para centrarlos juntos
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setOpaque(false);
        
        lblNombreUsuario = new JLabel("Usuario");
        lblNombreUsuario.setFont(new Font("Arial", Font.BOLD, 22));
        lblNombreUsuario.setForeground(Color.WHITE);
        lblNombreUsuario.setAlignmentX(Component.LEFT_ALIGNMENT); // Alineación correcta para BoxLayout

        lblEmail = new JLabel("email@ejemplo.com");
        lblEmail.setForeground(Color.GRAY);
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        lblRol = new JLabel("CUENTA");
        lblRol.setForeground(new Color(100, 180, 255)); // Azulito
        lblRol.setFont(new Font("Arial", Font.BOLD, 12));
        lblRol.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelDatos.add(lblNombreUsuario);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 5))); // Pequeño espacio
        panelDatos.add(lblEmail);
        panelDatos.add(lblRol);

        gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 3; gbc.anchor = GridBagConstraints.CENTER;
        panelHeader.add(panelDatos, gbc);

        this.add(panelHeader, BorderLayout.NORTH);

        // --- 2. LISTA DE RESEÑAS (Centro) ---
        JLabel lblTituloResenias = new JLabel("Mis Reseñas:");
        lblTituloResenias.setForeground(Color.WHITE);
        lblTituloResenias.setFont(new Font("Arial", Font.BOLD, 16));
        lblTituloResenias.setBorder(new EmptyBorder(15, 20, 5, 20));
        
        // Panel contenedor vertical
        panelResenias = new JPanel();
        panelResenias.setLayout(new BoxLayout(panelResenias, BoxLayout.Y_AXIS));
        panelResenias.setBackground(new Color(30, 30, 30));
        panelResenias.setBorder(new EmptyBorder(10, 15, 10, 15));

        JScrollPane scrollPane = new JScrollPane(panelResenias);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(new Color(30, 30, 30));

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(new Color(30, 30, 30));
        panelCentro.add(lblTituloResenias, BorderLayout.NORTH);
        panelCentro.add(scrollPane, BorderLayout.CENTER);

        this.add(panelCentro, BorderLayout.CENTER);

        // --- 3. BOTÓN CERRAR (Sur) ---
        JPanel panelSur = new JPanel();
        panelSur.setBackground(new Color(30, 30, 30));
        panelSur.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        botonVolver = new JButton("Volver al Home"); // Texto más claro
        botonVolver.setPreferredSize(new Dimension(150, 30));
        botonVolver.setBackground(new Color(60, 60, 60));
        botonVolver.setFocusPainted(false);
        
        panelSur.add(botonVolver);
        this.add(panelSur, BorderLayout.SOUTH);
    }

    // --- MÉTODOS DE GESTIÓN ---

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