package dao;

import dao.interfaces.*;
import dao.implementaciones.*;

/**
 * Implementación del Patrón Factory DAO.
 * Es el único punto de entrada para que la capa de Servicio
 * obtenga las implementaciones concretas de los DAO's.
 * * Esto desacopla a los Servicios de las implementaciones (JDBC),
 * permitiendo cambiar la tecnología de persistencia (ej. a JPA)
 * modificando únicamente esta clase.
 * En el futuro, podríamos inyectar las dependencias de otros DAO's dentro de
 * cada clase DAO y en sus constructores.
 * 
 * @author Grupo 4 - Proyecto TDL2 (Sugerido por Cátedra)
 * @version 4.0
 */
public class FactoryDAO {

    /**
     * Devuelve una implementación concreta del DAO para Datos_Personales.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return una instancia de Datos_PersonalesDAO.
     * 
     */
    public static Datos_PersonalesDAO getDatosPersonalesDAO() {
        return new Datos_PersonalesDAOImpl();
    }

    /**
     * Devuelve una implementación concreta del DAO para PeliculaDAO.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return una instancia de PeliculaDAO.
     * 
     */
    public static PeliculaDAO getPeliculaDAO() {
        return new PeliculaDAOImpl();
    }

    /**
     * Devuelve una implementación concreta del DAO para ReseñaDAO.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @return una instancia de ReseñaDAO.
     */
    public static ReseniaDAO getReseniaDAO() {
        return new ReseniaDAOImpl();
    }

    /**
     * Devuelve una implementación concreta del DAO para UsuarioDAO.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * @return una instancia de UsuarioDAO.
     */
    public static UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOImpl();
    }
}
