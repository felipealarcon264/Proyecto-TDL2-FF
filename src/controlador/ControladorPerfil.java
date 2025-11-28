//Verificacion JavaDoc -> Realizada.
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import control.Aplicacion; // Importante para navegar
import modelo.catalogo.Resenia;
import modelo.ente.Usuario;
import servicio.ServicioResenia;
import vista.TarjetaResenia;
import vista.VistaPerfil;

/**
 * Controlador para la vista del perfil de usuario.
 * Gestiona la visualización de los datos del usuario y sus reseñas,
 * así como las acciones de volver a la pantalla principal y eliminar reseñas.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ControladorPerfil implements ActionListener {

    private VistaPerfil vista;
    private Usuario usuario;
    private ServicioResenia servicioResenia;

    /**
     * Constructor del ControladorPerfil.
     * Inicializa la vista con los datos del usuario, asigna los listeners
     * y carga las reseñas existentes del usuario.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param vista           La vista del perfil (VistaPerfil) que este controlador
     *                        gestiona.
     * @param servicioResenia El servicio para la lógica de negocio relacionada con
     *                        las reseñas.
     * @param usuario         El usuario que ha iniciado sesión y cuyos datos se
     *                        mostrarán.
     */
    public ControladorPerfil(VistaPerfil vista, ServicioResenia servicioResenia, Usuario usuario) {
        this.vista = vista;
        this.servicioResenia = servicioResenia;
        this.usuario = usuario;

        // Inicializar Vista
        // Inicializar Vista con datos frescos
        this.vista.setDatosUsuario(usuario.getNombreUsuario(), usuario.getEmail(), usuario.getRol());

        // Limpiar listeners previos si se reutiliza la vista (buena práctica en
        // Singleton/CardLayout)
        for (java.awt.event.ActionListener al : this.vista.getBotonVolver().getActionListeners()) {
            this.vista.getBotonVolver().removeActionListener(al);
        }
        this.vista.getBotonVolver().addActionListener(this);

        // Cargar Reseñas
        cargarResenias();
    }

    /**
     * Carga las reseñas del usuario en la vista.
     * Limpia las reseñas anteriores, obtiene la lista actualizada desde el servicio
     * y crea una tarjeta para cada una, añadiéndole la funcionalidad de
     * eliminación.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    private void cargarResenias() {
        vista.limpiarResenias();

        List<Resenia> misResenias = servicioResenia.obtenerReseniasDeUsuario(usuario.getIdDB());

        if (misResenias.isEmpty()) {
            // Opcional: Mostrar mensaje "Sin reseñas"
        }

        for (Resenia r : misResenias) {
            TarjetaResenia tarjeta = new TarjetaResenia(r);

            // Conectamos el botón eliminar DE CADA TARJETA al controlador
            tarjeta.getBotonEliminar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eliminarResenia(r);
                }
            });

            vista.agregarTarjetaResenia(tarjeta);
        }
    }

    /**
     * Gestiona la eliminación de una reseña específica.
     * Muestra un diálogo de confirmación y, si el usuario acepta,
     * procede a borrar la reseña a través del servicio y recarga la vista.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param resenia La reseña que se va a eliminar.
     */
    private void eliminarResenia(Resenia resenia) {
        int confirm = JOptionPane.showConfirmDialog(vista,
                "¿Estás seguro de que quieres eliminar tu reseña de '" + resenia.getContenido().getTitulo() + "'?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean exito = servicioResenia.getReseniaDAOImpl().borrar(resenia);
            if (exito) {
                cargarResenias(); // Recargamos la lista para que desaparezca visualmente
                JOptionPane.showMessageDialog(vista, "Reseña eliminada.");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar.");
            }
        }
    }

    /**
     * Maneja los eventos de acción, específicamente el clic en el botón "Volver",
     * para navegar de regreso a la pantalla principal (HOME).
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @param e El evento de acción que se ha producido.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonVolver()) {
            // CAMBIO: Navegar al Home
            Aplicacion.mostrarVista("HOME");
        }
    }
}