package Entes;

// import java.util.ArrayList;
// import java.util.List;

/**
 * Representa un perfil de usuario dentro de una Cuenta.
 * Hereda de Cuenta para manejar sus propias listas y preferencias.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.1
 */
public class Perfil extends Cuenta {

    private String nombre;
    // private Idioma idiomaPreferido;
    // private List<Genero> generosPreferidos;
    // private List<Reseña> listReseña;
    // private List<SesionDeStreaming> historial;
    // private List<Contenido> favoritos;

    /**
     * Constructor vacío.
     * Inicializa las listas de preferencias del perfil.
     */
    public Perfil() {
        super();
        // this.generosPreferidos = new ArrayList<>();
        // this.listReseña = new ArrayList<>();
        // this.historial = new ArrayList<>();
        // this.favoritos = new ArrayList<>();
    }

    /**
     * Constructor para crear un perfil con un nombre específico.
     * @param nombre El nombre que identificará al perfil.
     */
    public Perfil(String nombre) {
        this(); // Llama al constructor vacío para inicializar las listas
        this.nombre = nombre;
    }

    // --- Métodos de Gestión de Preferencias ---

    public void añadirGeneroPreferido(/*Genero genero*/) {
        System.out.println("Añadiendo género preferido...");
    }

    public void eliminarGeneroPreferido(/*Genero genero*/) {
        System.out.println("Eliminando género preferido...");
    }

    public void añadirFavorito(/*Contenido contenido*/) {
        System.out.println("Añadiendo contenido a favoritos...");
    }

    public void eliminarFavorito(/*Contenido contenido*/) {
        System.out.println("Eliminando contenido de favoritos...");
    }

    public void crearReseña(/*Contenido contenido, int calificacion, String comentario*/) {
        System.out.println("Creando reseña...");
    }

    public void añadirAlHistorial(/*Contenido contenido*/) {
        System.out.println("Añadiendo contenido al historial...");
    }

    public void eliminarDelHistorial(/*Contenido contenido*/) {
        System.out.println("Eliminando contenido del historial...");
    }

    public void crearReproductor(/*Contenido contenido*/) {
        System.out.println("Creando reproductor para el contenido...");
    }

    public void eliminarReproductor(/*Reproductor reproductor*/) {
        System.out.println("Eliminando reproductor...");
    }

    public void actualizarHistorial(/*Contenido contenido, int tiempo*/) {
        System.out.println("Actualizando historial de visualización...");
    }


    // --- Getters y Setters ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // public List<Genero> getGenerosPreferidos() {
    //     return generosPreferidos;
    // }

    // public void setGenerosPreferidos(List<Genero> generosPreferidos) {
    //     this.generosPreferidos = generosPreferidos;
    // }

    // public List<Reseña> getListReseña() {
    //     return listReseña;
    // }

    // public void setListReseña(List<Reseña> listReseña) {
    //     this.listReseña = listReseña;
    // }

    // public List<SesionDeStreaming> getHistorial() {
    //     return historial;
    // }

    // public void setHistorial(List<SesionDeStreaming> historial) {
    //     this.historial = historial;
    // }

    // public List<Contenido> getFavoritos() {
    //     return favoritos;
    // }

    // public void setFavoritos(List<Contenido> favoritos) {
    //     this.favoritos = favoritos;
    // }
}

