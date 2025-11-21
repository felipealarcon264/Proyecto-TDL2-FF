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

public class ControladorPerfil implements ActionListener {

    private VistaPerfil vista;
    private Usuario usuario;
    private ServicioResenia servicioResenia;

    public ControladorPerfil(VistaPerfil vista, ServicioResenia servicioResenia, Usuario usuario) {
        this.vista = vista;
        this.servicioResenia = servicioResenia;
        this.usuario = usuario;

        // Inicializar Vista
        // Inicializar Vista con datos frescos
        this.vista.setDatosUsuario(usuario.getNombreUsuario(), usuario.getEmail(), usuario.getRol());
        
        // Limpiar listeners previos si se reutiliza la vista (buena práctica en Singleton/CardLayout)
        for(java.awt.event.ActionListener al : this.vista.getBotonVolver().getActionListeners()) {
            this.vista.getBotonVolver().removeActionListener(al);
        }
        this.vista.getBotonVolver().addActionListener(this);

        // Cargar Reseñas
        cargarResenias();
    }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonVolver()) {
            // CAMBIO: Navegar al Home
            Aplicacion.mostrarVista("HOME");
        }
    }
}