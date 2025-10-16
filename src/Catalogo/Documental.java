package Catalogo;

/**
 * Representa un Documental dentro de la plataforma.
 * Es una clase concreta que hereda todos los atributos y métodos
 * de la clase abstracta Contenido. Su propósito es permitir la instanciación
 * de contenidos que son específicamente documentales.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Documental extends Contenido {

    /**
     * Constructor vacío.
     * Llama al constructor vacío de la clase padre (Contenido).
     */
    public Documental() {
        super();
    }

    /**
     * Constructor para crear un Documental con datos esenciales.
     * Llama al constructor correspondiente de la clase padre para inicializar los datos.
     *
     * @param idContenido Identificador único del documental.
     * @param titulo Título del documental.
     * @param director Director del documental.
     * @param duracion Duración en minutos.
     */
    public Documental(int idContenido, String titulo, String director, int duracion) {
        super(idContenido, titulo, director, duracion);
    }
}
