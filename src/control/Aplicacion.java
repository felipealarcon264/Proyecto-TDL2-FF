package control;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.*;

import servicio.ServicioPelicula;
import basededatos.InicializadorDB;
import controlador.ControladorLogin;
import controlador.ControladorRegistro;
import servicio.ServicioUsuario;
import vista.VistaHome;
import vista.VistaLogin;
import vista.VistaCarga;
import vista.VistaRegistro;

public class Aplicacion {

    // Componentes globales para navegación
    public static JFrame ventana;
    public static CardLayout cardLayout;
    public static JPanel panelContenedor;

    // Hacemos accesibles la vista y los servicios que se necesitarán más tarde
    public static VistaHome vistaHome;
    public static ServicioUsuario servicioUsuario;
    public static ServicioPelicula servicioPelicula;

    public static void main(String[] args) {
        // --- INICIALIZACIÓN DE LA BASE DE DATOS ---
        new InicializadorDB().crearTablas();
        // Ejecutamos el Splash
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mostrarSplashYArrancar();
            }
        });
    }

    private static void mostrarSplashYArrancar() {
        // 1. Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Crear y Mostrar el Splash (JWindow no tiene bordes)
        JWindow splash = new JWindow();

        // Cargar imagen de Splash
        ImageIcon imagenSplash = new ImageIcon(Aplicacion.class.getResource("/imagenes/ImageStart.png"));
        JLabel etiquetaSplash = new JLabel(imagenSplash);
        splash.getContentPane().add(etiquetaSplash, BorderLayout.CENTER);

        splash.pack();
        splash.setLocationRelativeTo(null); // Centrar
        splash.setVisible(true); // ¡Mostrar Splash!

        // 3. Usar un Thread separado para la "carga" y luego abrir la App
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Simular tiempo de carga (1.5 segundos)
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Cerrar Splash y abrir la Ventana Principal
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        splash.setVisible(false);
                        splash.dispose();
                        iniciarVentanaPrincipal(); // <-- Aquí arranca la lógica de CardLayout
                    }
                });
            }
        }).start();
    }

    private static void iniciarVentanaPrincipal() {
        // Se crea el marco principal.
        ventana = new JFrame("Plataforma TDL2");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600);
        ventana.setLocationRelativeTo(null);

        // Crea el gestor de cartas (Ventanas) con CardLayout.
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Lugar para instanciar los servicios.
        servicioUsuario = new ServicioUsuario();
        servicioPelicula = new ServicioPelicula();

        // --- CARTA 1: LOGIN ---
        VistaLogin vistaLogin = new VistaLogin();
        new ControladorLogin(vistaLogin, servicioUsuario, servicioPelicula); // Conecta C con V y M
        panelContenedor.add(vistaLogin, "LOGIN");

        // --- CARTA 2: REGISTRO ---
        VistaRegistro vistaRegistro = new VistaRegistro();
        new ControladorRegistro(vistaRegistro, servicioUsuario);
        panelContenedor.add(vistaRegistro, "REGISTRO");

        // --- CARTA 3: HOME ---
        vistaHome = new VistaHome();
        // El ControladorHome se instanciará desde el ControladorLogin al iniciar
        // sesión.
        panelContenedor.add(vistaHome, "HOME");

        // --- CARTA 4: PANTALLA DE CARGA ---
        VistaCarga vistaCarga = new VistaCarga();
        panelContenedor.add(vistaCarga, "CARGA");
        // --- CARTA 5: PERFIL ---
        vista.VistaPerfil vistaPerfil = new vista.VistaPerfil();
        panelContenedor.add(vistaPerfil, "PERFIL");

        // Agrega el "mazo" a la ventana.
        ventana.add(panelContenedor);

        // Muestra el login por defecto.
        cardLayout.show(panelContenedor, "LOGIN");

        ventana.setVisible(true);
    }

    // Método estático para navegar entre pantallas desde los Controladores
    public static void mostrarVista(String nombreVista) {
        cardLayout.show(panelContenedor, nombreVista);
    }
}