package control;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controlador.ControladorLogin;
import controlador.ControladorRegistro;
import servicio.ServicioUsuario;
import vista.VistaLogin;
import vista.VistaRegistro;

public class Aplicacion {

    // Componentes globales para navegación
    public static JFrame ventana;
    public static CardLayout cardLayout;
    public static JPanel panelContenedor;

    public static void main(String[] args) {
        // Ejecutamos el Splash primero
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
                    // Simular tiempo de carga (3 segundos)
                    Thread.sleep(3000);
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
        //Se crea el marco principal.
        ventana = new JFrame("Plataforma TDL2");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600);
        ventana.setLocationRelativeTo(null);

        // Crea el gestor de cartas (Ventanas) con CardLayout.
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Lugar para instanciar los servicios.
        ServicioUsuario servicioUsuario = new ServicioUsuario();

        // --- CARTA 1: LOGIN ---
        VistaLogin vistaLogin = new VistaLogin();
        new ControladorLogin(vistaLogin, servicioUsuario); // Conecta C con V y M
        panelContenedor.add(vistaLogin, "LOGIN");

        // --- CARTA 2: REGISTRO ---
        VistaRegistro vistaRegistro = new VistaRegistro();
        new ControladorRegistro(vistaRegistro, servicioUsuario); // Conecta C con V y M
        panelContenedor.add(vistaRegistro, "REGISTRO");

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