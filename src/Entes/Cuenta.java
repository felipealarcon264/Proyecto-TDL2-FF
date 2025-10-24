package Entes;

   //No se necesita en la segunda entrega.import java.util.ArrayList;
   //No se necesita en la segunda entrega.import java.util.List;
   //No se necesita en la segunda entrega.import Enums.Pais;
   //No se necesita en la segunda entrega.import Enums.Plan;

/**
 * Representa la cuenta de un cliente final de la plataforma.
 * Esta clase hereda de Usuario y gestiona el plan, los perfiles y
 * las estadísticas asociadas a la cuenta.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.5
 */
public class Cuenta extends Usuario {

    //private Plan plan;   //No se necesita en la segunda entrega.
    //private List<Perfil> perfiles;   //No se necesita en la segunda entrega.
    //private Pais pais;   //No se necesita en la segunda entrega.
    // private ReporteIndividual estadisticas;

    /**
     * Constructor vacío.
     * Inicializa la lista de perfiles y establece un país por defecto.
     */
    public Cuenta() {
        super();
           //No se necesita en la segunda entrega.this.perfiles = new ArrayList<>();
           //No se necesita en la segunda entrega.this.pais = Pais.NO_ESPECIFICADO;
        // this.estadisticas = new ReporteIndividual(); // Se inicializan las estadísticas
    }

    /**
     * Constructor para crear una instancia de Cuenta con datos iniciales.
     * @param nombreUsuario El nombre de usuario para la plataforma.
     * @param email El correo electrónico para el inicio de sesión.
     * @param contrasena La clave de acceso de la cuenta.
     * @param datosPersonales Los datos personales asociados a la cuenta.
     */
    public Cuenta(int idDB, String nombreUsuario, String email, String contrasena, Datos_Personales datosPersonales, String rol) {
        super(idDB,nombreUsuario, email, contrasena, datosPersonales, rol);
           //No se necesita en la segunda entrega.this.plan = plan;
           //No se necesita en la segunda entrega.this.pais = pais;
           //No se necesita en la segunda entrega.this.perfiles = new ArrayList<>();
        // this.estadisticas = new ReporteIndividual(); // Se inicializan las estadísticas
    }

    // --- Métodos de Gestión de Perfiles ---

       //No se necesita en la segunda entrega.

     /*   
    //**
     * Crea un nuevo perfil asociado a esta cuenta.
     * @param nombre El nombre para el nuevo perfil.
     * @return El nuevo perfil creado y añadido a la lista.
     
    public Perfil crearPerfil(String nombre) {
        Perfil nuevoPerfil = new Perfil(nombre);
        this.perfiles.add(nuevoPerfil);
        System.out.println("Perfil '" + nombre + "' creado y añadido a la cuenta.");
        return nuevoPerfil;
    }

    /**
     * Elimina un perfil existente de la cuenta.
     * @param perfil El perfil a eliminar.
     
    public void eliminarPerfil(Perfil perfil) {
        if (this.perfiles.remove(perfil)) {
            System.out.println("Perfil '" + perfil.getNombre() + "' eliminado.");
        } else {
            System.out.println("El perfil no se encontró en esta cuenta.");
        }
    }

    /**
     * Permite cambiar al perfil seleccionado.
     * (La implementación futura podría establecer este perfil como activo).
     * @param perfil El perfil al que se desea cambiar.
     
    public void cambiarPerfil(Perfil perfil) {
        System.out.println("Cambiando al perfil: " + perfil.getNombre());
    }


    // --- Getters y Setters ---

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<Perfil> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
      this.pais = pais;
    }

    // public ReporteIndividual getEstadisticas() {
    //     return estadisticas;
    // }

    // public void setEstadisticas(ReporteIndividual estadisticas) {
    //     this.estadisticas = estadisticas;
    // }
    */
    @Override
    public String toString() {
        return super.toString();
    }
}

