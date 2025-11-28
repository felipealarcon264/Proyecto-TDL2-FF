//Verificacion JavaDoc -> Realizada.
package dao.interfaces;

import java.util.List;
import modelo.ente.Datos_Personales;

/**
 * Interfaz DAO para la gestión de datos personales.
 * Define los métodos para guardar, borrar y buscar datos personales en la base
 * de datos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.2
 * 
 */

public interface Datos_PersonalesDAO {

    /**
     * Guarda datos personales en la base de datos y devuelve el ID generado.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     *
     * @param datosPersonales Dato a guardar.
     * @return id generado.
     */
    public int guardar(Datos_Personales datosPersonales);

    /**
     * Borrar datos personales
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param datosPersonales Dato a borrar.
     * @return exito.
     */
    public boolean borrar(Datos_Personales datosPersonales);

    /**
     * Buscar datos personales por DNI
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param dni DNI a buscar.
     * @return Clase dato personal.
     */
    public Datos_Personales buscarPorDNI(int dni);

    /**
     * Buscar datos personales por ID
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param id ID a buscar.
     * @return Clase dato personal.
     */
    public Datos_Personales buscarPorID(int id);

    /**
     * Actualizar.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param datosPersonales Dato a actualizar.
     * @return exito.
     */
    public boolean actualizar(Datos_Personales datosPersonales);

    /**
     * Devuelve la lista con todos los datos de la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return Lista con los datos.
     */
    public List<Datos_Personales> devolverListaDatosPersonales();
}
