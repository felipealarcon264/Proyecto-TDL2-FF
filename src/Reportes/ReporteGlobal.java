package Reportes;

import java.util.ArrayList;
import java.util.List;

import Catalogo.Contenido;
import Entes.Usuario;

/**
 * Representa un reporte con estadisticas globales de la plataforma.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ReporteGlobal {

    private int cantSubActivos;
    private List<Contenido> contenidosMasVistos;
    private List<Contenido> tendencias;

    /**
     * Constructor para el reporte global.
     */
    public ReporteGlobal() {
        this.contenidosMasVistos = new ArrayList<>();
        this.tendencias = new ArrayList<>();
    }

    /**
     * Calcula y establece el numero de cuentas activas.
     * @param usuarios La lista de todos los usuarios de la plataforma.
     */
    public void determinarCuentasActivas(List<Usuario> usuarios) {
        int contador = 0;
        for (Usuario u : usuarios) {
            if (u.getActivo()) {
                contador++;
            }
        }
        this.cantSubActivos = contador;
        System.out.println("Total de suscriptores activos: " + this.cantSubActivos);
    }

    // --- Getters y Setters ---

    public int getCantSubActivos() {
        return cantSubActivos;
    }

    public void setCantSubActivos(int cantSubActivos) {
        this.cantSubActivos = cantSubActivos;
    }

    public List<Contenido> getContenidosMasVistos() {
        return contenidosMasVistos;
    }

    public void setContenidosMasVistos(List<Contenido> contenidosMasVistos) {
        this.contenidosMasVistos = contenidosMasVistos;
    }

    public List<Contenido> getTendencias() {
        return tendencias;
    }

    public void setTendencias(List<Contenido> tendencias) {
        this.tendencias = tendencias;
    }
}

