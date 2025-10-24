package Entes;

/**
 * Representa a un usuario con privilegios de administrador en la plataforma.
 * Esta clase hereda de Usuario y añade funcionalidades para la gestión
 * de contenidos y usuarios.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Administrador extends Usuario {

    /**
     * Constructor vacío.
     */
    public Administrador() {
        super();
    }

    /**
     * Constructor para crear una instancia de Administrador con datos iniciales.
     * @param nombreUsuario El nombre de usuario para la plataforma.
     * @param email El correo electrónico para el inicio de sesión.
     * @param contrasena La clave de acceso del administrador.
     * @param datosPersonales Los datos personales asociados al administrador.
     */
    public Administrador(int idDB, String nombreUsuario, String email, String contrasena, Datos_Personales datosPersonales, String rol) {
        super(idDB, nombreUsuario, email, contrasena, datosPersonales, rol);
    }

    // --- Métodos de Gestión ---

    /**
     * Agrega un nuevo contenido a la plataforma.
     * (La implementación dependerá de la clase Contenido).
     */
    public void agregarContenido(/*Contenido contenido*/) {
        System.out.println("Funcionalidad para agregar contenido."); //Luego eliminar
    }

    /**
     * Elimina un contenido existente de la plataforma.
     * (La implementación dependerá de la clase Contenido).
     */
    public void eliminarContenido(/*Contenido contenido*/) {
        System.out.println("Funcionalidad para eliminar contenido."); //Luego eliminar
    }

    /**
     * Modifica los metadatos de un contenido existente.
     * (La implementación dependerá de la clase Contenido).
     */
    public void modificarContenido(/*Contenido contenido*/) {
        System.out.println("Funcionalidad para modificar contenido."); //Luego eliminar
    }

    /**
     * Elimina la cuenta de un usuario de la plataforma.
     * (La implementación dependerá de las clases Plataforma y Cuenta).
     */
    public void eliminarCuenta(/*Plataforma plataforma, Cuenta cuenta*/) {
        System.out.println("Funcionalidad para eliminar una cuenta."); //Luego eliminar
    }

    /**
     * Genera un reporte específico sobre el uso de la plataforma.
     * (La implementación dependerá de las clases Plataforma y Reporte).
     */
    public void generarReporte(/*Plataforma plataforma, Reporte reporte*/) {
        System.out.println("Funcionalidad para generar un reporte."); //Luego eliminar
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
