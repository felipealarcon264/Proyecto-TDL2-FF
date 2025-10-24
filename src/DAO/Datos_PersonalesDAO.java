package DAO;

import Entes.Datos_Personales;

/**
 * Interfaz DAO para la gestión de datos personales.
 * Define los métodos para guardar, borrar y buscar datos personales en la base de datos.
 * 
 * @author Grupo 4 - Taller de lenguajes II
 */

public interface Datos_PersonalesDAO {
    public int guardar(Datos_Personales datosPersonales);
    public boolean borrar(Datos_Personales datosPersonales);
    public Datos_Personales buscarPorDNI(int dni);
    public Datos_Personales buscarPorID(int id);
}
