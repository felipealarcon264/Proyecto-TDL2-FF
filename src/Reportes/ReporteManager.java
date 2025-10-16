package Reportes;

import Entes.Usuario;

/**
 * Gestiona la creacion de diferentes tipos de reportes sobre la actividad
 * de la plataforma. Esta clase actua como un servicio que puede ser utilizado
 * para generar estadisticas.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ReporteManager {

    /**
     * Genera un reporte detallado sobre la actividad de un usuario especifico.
     * La logica se implementara en futuras entregas.
     * @param usuario El usuario del cual se quiere el reporte.
     */
    public void generarReporteIndividual(Usuario usuario) {
        System.out.println("Iniciando generacion de reporte para el usuario: " + usuario.getEmail());
    }

    /**
     * Genera un reporte con estadisticas globales de la plataforma.
     * La logica se implementara en futuras entregas.
     */
    public void generalReporteGlobal() {
        System.out.println("Iniciando generacion de reporte global...");
    }

    /**
     * Genera un reporte financiero sobre las suscripciones y otros datos economicos.
     * La logica se implementara en futuras entregas.
     */
    public void generarReporteFinanciero() {
        System.out.println("Iniciando generacion de reporte financiero...");
    }
}

