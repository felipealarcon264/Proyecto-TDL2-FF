//Verificacion JavaDoc -> Realizada.
package servicio;
import modelo.catalogo.Pelicula;
import comparadores.ComparadorPeliculaPorRating;
import dao.interfaces.PeliculaDAO;
import dao.FactoryDAO;
import excepciones.ErrorDeInicializacionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.*;

/**
 * Servicio para operaciones relacionadas con las películas.
 * Utiliza PeliculaDAO para interactuar con la base de datos.
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ServicioPelicula {
    private PeliculaDAO peliculaDAO; // No se usa externamente.
    private final int MINIMO_PELICULAS = 10;// Cuantas peliculas mostrar.

    /**
     * Constructor de ServicioPelicula.
     * Crea una instancia de PeliculaDAO.
     */
    public ServicioPelicula() {
        this.peliculaDAO = FactoryDAO.getPeliculaDAO();
    }

    /**
     * Importa las películas del CSV a la base de datos usando FileReader.
     * Verifica si la película ya existe en la DB por título y resumen para evitar
     * duplicados.
     * Si no existe en la base de datos la agrega, si existe pasa a la siguente.
     * Suponemos que las peliculas del csv y de la API externa tienen mismo nombre y
     * resumen.
     * 
     * @throws ErrorDeInicializacionException si el archivo CSV no se encuentra o
     *                                        no se puede leer.
     */
    public void importarPeliculaConCSV() {
        System.out.println("Verificando catálogo de películas...");

        String nombreArchivo = "src/movies_database.csv";

        int nuevasPeliculas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {

            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {
                // Regex para separar CSV respetando comillas
                String[] datos = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (datos.length >= 9) {
                    try {
                        // Datos clave para la búsqueda
                        String titulo = datos[1].replace("\"", "").trim();
                        String resumen = datos[2].replace("\"", "").trim();

                        // --- VALIDACIÓN ---
                        if (peliculaDAO.buscarPorTituloyResumen(titulo, resumen) != null) {
                            continue; // Ya existe.
                        }
                        // ------------------

                        // Pelicula nueva.

                        // Col 0: Año
                        int anio = 0;
                        if (datos[0].contains("-")) {
                            try {
                                anio = Integer.parseInt(datos[0].split("-")[0]);
                            } catch (Exception e) {
                            }
                        }

                        // Col 5: Rating
                        double rating = 0.0;
                        try {
                            rating = Double.parseDouble(datos[5]);
                        } catch (Exception e) {
                        }

                        // Col 7: Genero
                        String genero = datos[7].replace("\"", "").split(",")[0].trim();

                        // Col 8: Poster
                        String poster = datos[8];

                        // 3. Crear y Guardar
                        Pelicula p = new Pelicula(-1, titulo, "Director", 0, resumen, genero, rating, anio, poster);

                        if (peliculaDAO.guardar(p)) {
                            nuevasPeliculas++;
                            // Opcional: Imprimir cada 100 para no saturar la consola
                            if (nuevasPeliculas % 100 == 0)
                                System.out.println("Cargando... " + nuevasPeliculas);
                        }

                    } catch (Exception e) {
                        // Si una línea falla, la mostramos pero seguimos con la siguiente
                        System.err.println("Error línea CSV: " + e.getMessage());
                    }
                }
            }

            if (nuevasPeliculas > 0) {
                System.out
                        .println("✅ Actualización completa. Se agregaron " + nuevasPeliculas + " películas faltantes.");
            } else {
                System.out.println("✅ El catálogo ya estaba sincronizado (Total coincidió).");
            }

        } catch (java.io.FileNotFoundException e) {
            // Excepción específica si no encuentra el archivo en la ruta dada
            throw new ErrorDeInicializacionException("No se encontró el archivo CSV en la ruta: " + nombreArchivo, e);
        } catch (IOException e) {
            throw new ErrorDeInicializacionException("Error de lectura del CSV.", e);
        }
    }

    /**
     * Obtiene las 10 películas con el rating más alto de la base de datos.
     * Si hay menos de 10, devuelve todas.
     * 
     * @return Una lista de las 10 películas con el rating más alto.
     */
    public List<Pelicula> obtenerTop10() {
        List<Pelicula> todas = peliculaDAO.devolverListaPelicula();
        todas.sort(new ComparadorPeliculaPorRating());
        return todas.subList(0, Math.min(MINIMO_PELICULAS, todas.size()));
    }

    /**
     * Obtiene 10 películas aleatorias de la base de datos.
     * Si hay menos de 10, devuelve todas.
     * 
     * @return Una lista de 10 películas aleatorias.
     */
    public List<Pelicula> obtener10Aleatorias() {
        List<Pelicula> todas = peliculaDAO.devolverListaPelicula();
        Collections.shuffle(todas);
        return todas.subList(0, Math.min(MINIMO_PELICULAS, todas.size()));
    }

}