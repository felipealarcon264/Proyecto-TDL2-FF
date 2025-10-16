package Catalogo;

/**
 * Representa un Capitulo individual de una Serie.
 * Un Capitulo es un tipo de Contenido con su propio titulo, duracion
 * y numero de capitulo.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Capitulo extends Contenido {

    private int numero;

    /**
     * Constructor vacio.
     */
    public Capitulo() {
        super();
    }

    /**
     * Constructor para crear un Capitulo con sus datos.
     * @param idContenido Identificador unico del capitulo.
     * @param titulo Titulo especifico del capitulo.
     * @param director Director del capitulo.
     * @param duracion Duracion en minutos del capitulo.
     * @param numero El numero del capitulo dentro de la temporada.
     */
    public Capitulo(int idContenido, String titulo, String director, int duracion, int numero) {
        super(idContenido, titulo, director, duracion);
        this.numero = numero;
    }

    // --- Getters y Setters ---

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
