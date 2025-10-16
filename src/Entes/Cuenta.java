package Entes;

import java.util.ArrayList;
import java.util.List;
import Enums.Pais;
import Enums.Plan;

/**
 * Representa la cuenta de un cliente final de la plataforma.
 * Esta clase hereda de Usuario y gestiona el plan, los perfiles y
 * las estadísticas asociadas a la cuenta.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.5
 */
public class Cuenta extends Usuario {

    private Plan plan;
    private List<Perfil> perfiles;
    private Pais pais;
    // private ReporteIndividual estadisticas;

    /**
     * Constructor vacío.
     * Inicializa la lista de perfiles y establece un país por defecto.
     */
    public Cuenta() {
        super();
        this.perfiles = new ArrayList<>();
        this.pais = Pais.NO_ESPECIFICADO;
        // this.estadisticas = new ReporteIndividual(); // Se inicializan las estadísticas
    }

    /**
     * Constructor para crear una instancia de Cuenta con datos iniciales.
     * @param email El correo electrónico para el inicio de sesión.
     * @param contrasena La clave de acceso de la cuenta.
     * @param plan El plan de suscripción asociado a la cuenta.
     * @param pais El país de residencia de la cuenta.
     */
    public Cuenta(String email, String contrasena, Plan plan, Pais pais) {
        super(email, contrasena);
        this.plan = plan;
        this.pais = pais;
        this.perfiles = new ArrayList<>();
        // this.estadisticas = new ReporteIndividual(); // Se inicializan las estadísticas
    }

    // --- Métodos de Gestión de Perfiles ---

    /**
     * Crea un nuevo perfil asociado a esta cuenta.
     * @param nombre El nombre para el nuevo perfil.
     * @return El nuevo perfil creado y añadido a la lista.
     */
    public Perfil crearPerfil(String nombre) {
        Perfil nuevoPerfil = new Perfil(nombre);
        this.perfiles.add(nuevoPerfil);
        System.out.println("Perfil '" + nombre + "' creado y añadido a la cuenta.");
        return nuevoPerfil;
    }

    /**
     * Elimina un perfil existente de la cuenta.
     * @param perfil El perfil a eliminar.
     */
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
     */
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
}

