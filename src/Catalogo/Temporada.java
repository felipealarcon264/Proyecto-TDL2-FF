package Catalogo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una temporada especifica dentro de una Serie.
 * Contiene un numero de temporada y una lista de Capitulos.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Temporada {

    private int numero;
    private List<Capitulo> capitulos;

    /**
     * Constructor vacio.
     * Inicializa la lista de capitulos.
     */
    public Temporada() {
        this.capitulos = new ArrayList<>();
    }

    /**
     * Constructor para crear una Temporada con su numero.
     * @param numero El numero de la temporada (ej: 1, 2, 3...).
     */
    public Temporada(int numero) {
        this.numero = numero;
        this.capitulos = new ArrayList<>();
    }

    /**
     * Agrega un nuevo capitulo a la lista de la temporada.
     * @param capitulo El capitulo a agregar.
     */
    public void agregarCapitulo(Capitulo capitulo) {
        this.capitulos.add(capitulo);
    }

    // --- Getters y Setters ---

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public List<Capitulo> getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(List<Capitulo> capitulos) {
        this.capitulos = capitulos;
    }
}
