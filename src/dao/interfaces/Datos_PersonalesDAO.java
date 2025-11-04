package dao.interfaces;

import java.util.List;

import ente.Datos_Personales;

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
     * @param datosPersonales Dato a guardar.
     * @return id generado.
     */
    public int guardar(Datos_Personales datosPersonales);

    /**
     * Borrar datos personales
     * 
     * @param datosPersonales Dato a borrar.
     * @return exito.
     */
    public boolean borrar(Datos_Personales datosPersonales);

    /**
     * Buscar datos personales por DNI
     * 
     * @param dni DNI a buscar.
     * @return Clase dato personal.
     */
    public Datos_Personales buscarPorDNI(int dni);

    /**
     * Buscar datos personales por ID
     * 
     * @param id ID a buscar.
     * @return Clase dato personal.
     */
    public Datos_Personales buscarPorID(int id);

    /**
     * Actualizar.
     * 
     * @param datosPersonales Dato a actualizar.
     * @return exito.
     */
    public boolean actualizar(Datos_Personales datosPersonales);

    /**
     * Devuelve la lista con todos los datos de la base de datos.
     * 
     * @return Lista con los datos.
     */
    public List<Datos_Personales> devolverListaDatosPersonales();
}
