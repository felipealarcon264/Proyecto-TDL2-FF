package Reportes;

import java.util.ArrayList;
import java.util.List;
import Catalogo.Contenido;
import Entes.Cuenta;
import Enums.Genero;

/**
 * Representa un reporte de actividad para una cuenta individual.
 * Contiene estadisticas personalizadas de un usuario.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ReporteIndividual {

    private int tiempoVisto;
    private List<Genero> generosFavoritos;
    private List<Contenido> contenidoVisto;
    private Cuenta cuentaAsociada;

    /**
     * Constructor para el reporte individual.
     * @param cuenta La cuenta a la que pertenece el reporte.
     */
    public ReporteIndividual(Cuenta cuenta) {
        this.cuentaAsociada = cuenta;
        this.generosFavoritos = new ArrayList<>();
        this.contenidoVisto = new ArrayList<>();
        this.tiempoVisto = 0; // Se calcularia con la logica del historial.
    }

    // --- Getters y Setters ---

    public int getTiempoVisto() {
        return tiempoVisto;
    }

    public void setTiempoVisto(int tiempoVisto) {
        this.tiempoVisto = tiempoVisto;
    }

    public List<Genero> getGenerosFavoritos() {
        return generosFavoritos;
    }

    public void setGenerosFavoritos(List<Genero> generosFavoritos) {
        this.generosFavoritos = generosFavoritos;
    }

    public List<Contenido> getContenidoVisto() {
        return contenidoVisto;
    }

    public void setContenidoVisto(List<Contenido> contenidoVisto) {
        this.contenidoVisto = contenidoVisto;
    }

    public Cuenta getCuentaAsociada() {
        return cuentaAsociada;
    }

    public void setCuentaAsociada(Cuenta cuentaAsociada) {
        this.cuentaAsociada = cuentaAsociada;
    }
}

