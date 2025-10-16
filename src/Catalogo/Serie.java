package Catalogo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una Serie dentro de la plataforma.
 * Una Serie es un tipo de Contenido que se organiza en Temporadas.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Serie extends Contenido {

    private List<Temporada> temporadas;

    /**
     * Constructor vacio.
     * Inicializa la lista de temporadas.
     */
    public Serie() {
        super();
        this.temporadas = new ArrayList<>();
    }

    /**
     * Constructor para crear una Serie con datos esenciales.
     * @param idContenido Identificador unico de la serie.
     * @param titulo Titulo de la serie.
     * @param director Director principal de la serie.
     */
    public Serie(int idContenido, String titulo, String director) {
        // La duracion de una serie puede ser la suma de sus capitulos,
        // por ahora se inicializa en 0.
        super(idContenido, titulo, director, 0);
        this.temporadas = new ArrayList<>();
    }

    /**
     * Agrega una nueva temporada a la lista de la serie.
     * @param temporada La temporada a agregar.
     */
    public void agregarTemporada(Temporada temporada) {
        this.temporadas.add(temporada);
    }

    // --- Getters y Setters ---

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(List<Temporada> temporadas) {
        this.temporadas = temporadas;
    }
}
