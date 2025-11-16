package controlador;

import modelo.ente.Usuario;
import servicio.ServicioUsuario;
import vista.VistaLogin;
import vista.VistaRegistro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * El CONTROLADOR (C) del Login.
 * Implementa ActionListener para escuchar los botones de la Vista.
 * Conecta la Vista (VistaLogin) con el Modelo (ServicioUsuario).
 */
public class ControladorLogin implements ActionListener {

    private final VistaLogin vista;
    private final ServicioUsuario servicio;

    /**
     * Constructor que recibe la Vista y el Modelo.
     * Se registra a sí mismo (this) para escuchar los botones de la vista.
     */
    public ControladorLogin(VistaLogin vista, ServicioUsuario servicio) {
        this.vista = vista;
        this.servicio = servicio;

        // Se suscribe a los eventos (clics) de los botones en la Vista
        this.vista.getBotonIngresar().addActionListener(this);
        this.vista.getBotonRegistrarse().addActionListener(this);
    }

    /**
     * Este método se llama automáticamente cuando se hace clic en un botón.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // 1. Averiguar qué botón se presionó
        Object botonPresionado = e.getSource();

        if (botonPresionado == vista.getBotonIngresar()) { procesarIngresoCuenta();
        } else if (botonPresionado == vista.getBotonRegistrarse()) { procesarRegistroCuenta(); }
    }

    /**
     * Lógica para manejar el clic en "Ingresar".
     */
    private void procesarIngresoCuenta() {
        // 1. Obtenemos los datos de la Vista
        String correo = vista.getEmail();
        String contraseña = vista.getPassword();

        // 2. Validamos con el Servicio (El Modelo) y cambiamos la lógica de verificarUsuario para que nos devuelva el usuario
        // asi poder identificarlo entre Administrador o Cuenta.
        Usuario usuarioLogueado = servicio.DelvolverTipoUsuario(correo, contraseña);
        if (usuarioLogueado == null) {
            vista.mostrarError("Correo o contraseña incorrectos.");
        } else {
            // podemos implementar que tipo de ventana abrir (Administrador no necesario para el Entregable 3)

            if (usuarioLogueado.getRol().equals("CUENTA")) {
                System.out.println("¡El ingreso del Usuario " + usuarioLogueado.getNombreUsuario() + " ha sido exitoso!");

                //abrir la nueva vista de bienvenida

            } else {
                /** Para el ADMINISTRADOR no se requiere de una interfaz gráfica basándonos en el entregable 3 */
                System.out.println("¡El ingreso del Administrador(a) " + usuarioLogueado.getNombreUsuario() + " ha sido exitoso!");
            }
            //cierra esa ventana y libera sus recursos.
            javax.swing.SwingUtilities.getWindowAncestor(vista).dispose();
        }

    }

    /**
     * Lógica para manejar el clic en "Registrarse".
     * @author Grupo 4 - Proyecto TDL2
     * @version 4.4
     */
    private void procesarRegistroCuenta() {
        System.out.println("Botón 'Registrarse' presionado.");

        // 1. Crear las nuevas piezas del MVC de Registro
        vista.VistaRegistro vistaRegistro = new vista.VistaRegistro();
        // Reutilizamos el servicio de usuario que ya teníamos
        new ControladorRegistro(vistaRegistro, this.servicio);

        // 2. Crear la nueva ventana de Registro
        JFrame ventanaRegistro = new JFrame("Plataforma TDL2 - Nuevo Usuario");
        ventanaRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaRegistro.add(vistaRegistro);
        ventanaRegistro.pack(); // Ajusta al tamaño de VistaRegistro
        ventanaRegistro.setLocationRelativeTo(null); // Centra
        ventanaRegistro.setResizable(false);
        ventanaRegistro.setVisible(true);

        // 3. Cerrar la ventana de Login actual
        javax.swing.SwingUtilities.getWindowAncestor(vista).dispose();
    }
}