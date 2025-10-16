package Enums;

/**
 * Define los planes de suscripción (GRATIS, ESTANDAR, PREMIUM)
 * y encapsula las características de cada uno.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public enum Plan {

    GRATIS("Gratis", "480p", false, "Acceso a una selección de nuestro catálogo con anuncios."),
    ESTANDAR("Estándar", "1080p", true, "Acceso ilimitado a todo el catálogo en calidad HD."),
    PREMIUM("Premium", "4K+HDR", true, "Acceso ilimitado con la mejor calidad de video y audio.");

    private final String nombre;
    private final String calidadMaxima;
    private final boolean accesoTotal;
    private final String descripcion;

    /**
     * Constructor para inicializar cada plan con sus atributos específicos.
     * @param nombre El nombre amigable del plan.
     * @param calidadMaxima La resolución máxima de video.
     * @param accesoTotal Si permite ver todo el catálogo.
     * @param descripcion Una breve explicación del plan.
     */
    private Plan(String nombre, String calidadMaxima, boolean accesoTotal, String descripcion) {
        this.nombre = nombre;
        this.calidadMaxima = calidadMaxima;
        this.accesoTotal = accesoTotal;
        this.descripcion = descripcion;
    }

    /**
     * @return El nombre del plan.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return La calidad máxima de video del plan.
     */
    public String getCalidadMaxima() {
        return calidadMaxima;
    }

    /**
     * @return true si el plan otorga acceso total.
     */
    public boolean tieneAccesoTotal() {
        return accesoTotal;
    }

    /**
     * @return La descripción del plan.
     */
    public String getDescripcion() {
        return descripcion;
    }
}

