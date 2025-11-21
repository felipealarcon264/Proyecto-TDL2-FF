package servicio;

import dao.implementaciones.PeliculaDAOImpl;
import modelo.catalogo.Pelicula;

public class ServicioDetalleOMDb {
    private PeliculaDAOImpl peliDao;
    public ServicioDetalleOMDb() {
        peliDao = new PeliculaDAOImpl();
    }

    /**
     * Se encarga de determinar si la pelicula ya esta guardada en nuestra base de datos
     * Si no lo está la guarda, retorna la pelicula ya guardada con el fin de determinar el ID.
     * @return
     */ 
    public Pelicula VerificarSiLaPeliculaExiste(Pelicula pelicula){
        if(peliDao.buscarPorTitulo(pelicula.getTitulo()) != null){
            //La pelicula existe en la base de datos.
            return peliDao.buscarPorTitulo(pelicula.getTitulo());
        }
        else{
            //La pelicula no existe en la base de datos.
            peliDao.guardar(pelicula);
            return peliDao.buscarPorTitulo(pelicula.getTitulo());//Devuelve la pelicula con el ID.
        }
    }




    public PeliculaDAOImpl getPeliDao() {
        return peliDao;
    }

}
