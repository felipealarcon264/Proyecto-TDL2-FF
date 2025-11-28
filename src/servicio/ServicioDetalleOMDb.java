//Verificacion JavaDoc -> Realizada.

package servicio;
import dao.FactoryDAO;
import dao.interfaces.PeliculaDAO;
import modelo.catalogo.Pelicula;

/**
 * Servicio para operaciones relacionadas con las películas.
 * Utiliza PeliculaDAO para interactuar con la base de datos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ServicioDetalleOMDb {
    private PeliculaDAO peliDao; //Uso local.

    /**
     * Constructor de ServicioDetalleOMDb.
     * Crea una instancia de PeliculaDAO.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public ServicioDetalleOMDb() {
        this.peliDao = FactoryDAO.getPeliculaDAO();
    }

    /**
     * Se encarga de determinar si la pelicula ya esta guardada en nuestra base de datos
     * Si no lo está la guarda, retorna la pelicula ya guardada con el fin de determinar el ID.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param pelicula Pelicula a verificar
     * @return Pelicula
     */ 
    public Pelicula VerificarSiLaPeliculaExiste(Pelicula pelicula){
       //Si el objeto ya tiene un ID válido (>0).
        // No hace falta buscar ni guardar.
        if (pelicula.getIdDB() > 0) {
            return pelicula;
        }
        //Busqueda en la DB.
        Pelicula aux = peliDao.buscarPorTitulo(pelicula.getTitulo());

        if(aux!= null){
            //La pelicula existe en la base de datos.
            return aux;//La devuelve con su id.
        }
        else{
            //La pelicula no existe en la base de datos.
            peliDao.guardar(pelicula);//Se agrega
            return peliDao.buscarPorTitulo(pelicula.getTitulo());//Devuelve la pelicula con el ID.
        }
    }


    

}
