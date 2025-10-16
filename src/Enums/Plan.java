package Enums;

/**
 * Representa los diferentes planes de suscripción que ofrece la plataforma.
 * Cada plan define el nivel de acceso y la calidad máxima de streaming.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.1
 */
public enum Plan {

    GRATIS("Gratis", Resolucion.HD_720P, false, "Acceso limitado a una selección de nuestro catálogo."),
    ESTANDAR("Estándar", Resolucion.FULL_HD_1080P, true, "Acceso completo a todo el catálogo en HD."),
    PREMIUM("Premium", Resolucion.UHD_4K, true, "Todo el catálogo en la mejor calidad de video y audio.");

    private final String nombre;
    private final Resolucion calidadMaxima;
    private final boolean accesoTotal;
    private final String descripcion;

    /**
     * Constructor para cada constante del enum.
     * @param nombre El nombre comercial del plan.
     * @param calidadMaxima La resolución máxima de video permitida por el plan.
     * @param accesoTotal Verdadero si el plan da acceso a todo el catálogo.
     * @param descripcion Una breve descripción del plan.
     */
    Plan(String nombre, Resolucion calidadMaxima, boolean accesoTotal, String descripcion) {
        this.nombre = nombre;
        this.calidadMaxima = calidadMaxima;
        this.accesoTotal = accesoTotal;
        this.descripcion = descripcion;
    }

    // --- Getters ---

    public String getNombre() {
        return nombre;
    }

    public Resolucion getCalidadMaxima() {
        return calidadMaxima;
    }

    public boolean getAccesoTotal() {
        return accesoTotal;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
