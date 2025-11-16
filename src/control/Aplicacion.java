package control;

import basededatos.InicializadorDB;
import controlador.ControladorLogin;
import servicio.ServicioUsuario;
import vista.VistaLogin;

// Importaciones necesarias para el Splash Screen
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer; //  Usar el timer de Swing
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Punto de entrada principal (main) de la aplicación GUI (Entregable 3).
 * Reemplaza a la antigua 'Plataforma.java' de consola.
 * Su única tarea es inicializar y conectar las piezas del MVC.
 */
public class Aplicacion {

    public static void main(String[] args) {

        // 'invokeLater' es la forma correcta y segura de iniciar
        // una aplicación gráfica en Java (Swing).
        SwingUtilities.invokeLater(() -> {
            JWindow splashVentana = new JWindow();
            ImageIcon splashImagen = new ImageIcon(Aplicacion.class.getResource("/imagenes/ImageStart.png"));
            splashVentana.getContentPane().add(new JLabel(splashImagen), BorderLayout.CENTER);
            splashVentana.pack(); // Ajusta la ventana al tamaño de la imagen
            splashVentana.setLocationRelativeTo(null); // Centra en pantalla
            splashVentana.setVisible(true); // La mostramos

            // 2. Definir la duración del Splash
            int duracionSplash = 1000; // 1 segundos
            Timer splashTimer = new Timer(duracionSplash, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    // 1. Inicializar la Base de Datos (como en la app de consola)
                    InicializadorDB inicializadorDB = new InicializadorDB();
                    inicializadorDB.crearTablas();
                    splashVentana.setVisible(false);
                    splashVentana.dispose();
                    // 2. Crear las piezas clave del MVC

                    // M (Modelo): Creamos el servicio que usará el controlador
                    ServicioUsuario servicio = new ServicioUsuario();

                    // V (Vista): Creamos la vista (el panel)
                    VistaLogin vista = new VistaLogin();

                    // C (Controlador): Creamos el controlador y le pasamos la Vista y el Modelo
                    // El controlador se "suscribe" a los botones de la vista automáticamente.
                    new ControladorLogin(vista, servicio);

                    // 3. Crear la Ventana Principal (el JFrame)
                    JFrame ventana = new JFrame("Plataforma TDL2 - Ingreso a Cuenta");
                    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    // 4. Añadir la Vista (el panel) a la Ventana
                    ventana.add(vista);

                    // 5. Ajustar y mostrar la Ventana
                    ventana.pack(); // Ajusta el tamaño de la ventana al contenido (tu VistaLogin)
                    ventana.setLocationRelativeTo(null); // Centra la ventana en pantalla
                    ventana.setResizable(false);
                    ventana.setVisible(true); // ¡La mostramos!
                }
            });
            splashTimer.setRepeats(false);
            splashTimer.start();
        });
    }
}