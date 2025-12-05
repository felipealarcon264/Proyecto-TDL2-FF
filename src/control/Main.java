//Verificacion JavaDoc -> Realizada.
package control;
import basededatos.InicializadorDB;

/**
 * Clase principal del programa.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class Main {
    /**
     * Crea la base de datos y arranca la app.
     *
     * @author Grupo 4 - Proyecto TDL2*
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        new InicializadorDB().crearTablas();// Inicializa la base de datos.
        Aplicacion.iniciar();// Arranca la app.
    }
}