package vista;


public class MainEliminarPruebas {

    public static void main(String[] args) {
        // Crear una instancia de VistaLogin
        VistaLogin vistaLogin = new VistaLogin();

        // Crear un JFrame para contener la vista
        javax.swing.JFrame ventana = new javax.swing.JFrame("Login de Usuario");
        ventana.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        ventana.setSize(1000, 600); // Tamaño adecuado para la ventana de login
        ventana.setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Añadir la VistaLogin al JFrame
        ventana.add(vistaLogin);

        // Hacer visible el JFrame
        ventana.setVisible(true);
    }
}
