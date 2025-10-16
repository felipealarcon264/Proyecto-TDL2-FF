package Reportes;

import java.util.List;
import Entes.Cuenta;
import Entes.Usuario;
import Enums.Plan;

/**
 * Representa un reporte financiero de la plataforma.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ReporteFinanciero {

    private int subsGratuitas;
    private int subsEstandar;
    private int subsPremium;
    // private double ingresoTotal; // Para Parte 2 (BD)

    /**
     * Constructor que procesa la lista de usuarios para generar las estadisticas.
     * @param usuarios La lista de todos los usuarios de la plataforma.
     */
    public ReporteFinanciero(List<Usuario> usuarios) {
        determinarCantidades(usuarios);
    }

    /**
     * Cuenta la cantidad de suscriptores por cada tipo de plan.
     * @param usuarios Lista de usuarios a procesar.
     */
    public void determinarCantidades(List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            if (u instanceof Cuenta) {
                Cuenta cuenta = (Cuenta) u;
                if (cuenta.getPlan() == Plan.GRATIS) {
                    this.subsGratuitas++;
                } else if (cuenta.getPlan() == Plan.ESTANDAR) {
                    this.subsEstandar++;
                } else if (cuenta.getPlan() == Plan.PREMIUM) {
                    this.subsPremium++;
                }
            }
        }
    }

    /*
    public void determinarIngresoTotal() {
        // Logica futura para calcular ingresos
    }
    */

    // --- Getters y Setters ---

    public int getSubsGratuitas() {
        return subsGratuitas;
    }

    public void setSubsGratuitas(int subsGratuitas) {
        this.subsGratuitas = subsGratuitas;
    }

    public int getSubsEstandar() {
        return subsEstandar;
    }

    public void setSubsEstandar(int subsEstandar) {
        this.subsEstandar = subsEstandar;
    }

    public int getSubsPremium() {
        return subsPremium;
    }

    public void setSubsPremium(int subsPremium) {
        this.subsPremium = subsPremium;
    }
}

