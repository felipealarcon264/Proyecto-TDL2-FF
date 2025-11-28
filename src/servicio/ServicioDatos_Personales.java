//Verificacion JavaDoc -> Realizada.

package servicio;

import dao.FactoryDAO;
import dao.interfaces.Datos_PersonalesDAO;

/**
 * Servicio para operaciones relacionadas con los datos personales.
 * Utiliza Datos_PersonalesDAO para interactuar con la base de datos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ServicioDatos_Personales {
    private Datos_PersonalesDAO datosPersonalesDAO;// Uso externo.

    /**
     * Constructor de ServicioDatos_Personales.
     * Crea una instancia de Datos_PersonalesDAO.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public ServicioDatos_Personales() {
        this.datosPersonalesDAO = FactoryDAO.getDatosPersonalesDAO();
    }

    /**
     * Verifica si una cadena de texto contiene únicamente letras (mayúsculas y/o
     * minúsculas).
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     * 
     * @param texto La cadena de texto a verificar.
     * @return true si la cadena contiene solo letras, false en caso contrario.
     */
    public boolean contieneSoloLetras(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        for (char caracter : texto.toCharArray()) {
            if (!Character.isLetter(caracter)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtiene el DAO de Datos_Personales.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return El DAO de Datos_Personales.
     */
    public Datos_PersonalesDAO getDatosPersonalesDAOImpl() {
        return datosPersonalesDAO;
    }

}
