package DAO;

import Entes.Datos_Personales;

public interface Datos_PersonalesDAO {
    public void guardar(Datos_Personales datosPersonales);
    public void borrar(Datos_Personales datosPersonales);
    public Datos_Personales buscarPorDNI(int dni);

}
