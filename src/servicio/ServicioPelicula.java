package servicio;

import modelo.catalogo.Pelicula;
import comparadores.ComparadorPeliculaPorRating;
import dao.interfaces.PeliculaDAO;
import dao.FactoryDAO;

import excepciones.ErrorDeInicializacionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.*;

public class ServicioPelicula {
    PeliculaDAO peliculaDAO;

    public ServicioPelicula() {
        this.peliculaDAO = FactoryDAO.getPeliculaDAO();
    }

    /**
     * Importa las películas del CSV a la base de datos.
     * Solo se ejecuta si la base de datos está vacía.
     * 
     * @throws ErrorDeInicializacionException si el archivo CSV no se encuentra o no
     *                                        se puede leer.
     */
    public void inicializarCatalogo() {// Rescatar error desde donde se invoca
        // 1. Si ya hay películas, no perdemos tiempo importando
        if (!peliculaDAO.devolverListaPelicula().isEmpty()) {
            return;
        }

        System.out.println("Iniciando importación de películas...");

        final String nombreArchivo = "/movies_database.csv";
        // Obtenemos el stream del recurso
        InputStream is = getClass().getResourceAsStream(nombreArchivo);

        // Verificamos si el archivo existe. Si no, lanzamos nuestra excepción.
        if (is == null) {
            System.out.println("Error");
            throw new ErrorDeInicializacionException(
                    "Error crítico: El archivo '" + nombreArchivo + "' no se encontró en los recursos del proyecto.",
                    null);
        }

        // 2. Leemos el archivo desde 'src/movies_database.csv'
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String linea = br.readLine(); // Saltamos la cabecera

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Verificamos tener las columnas necesarias (el archivo tiene 9 columnas)
                if (datos.length >= 9) {
                    try {
                        // Col 0: Release_Date (2021-12-15) -> Sacamos el año
                        int anio = 0;
                        if (datos[0].contains("-")) {
                            anio = Integer.parseInt(datos[0].split("-")[0]);
                        }

                        // Col 1: Título
                        String titulo = datos[1].replace("\"", ""); // Quitamos comillas si las tiene

                        // Col 2: Resumen (Overview)
                        String resumen = datos[2].replace("\"", "");

                        // Col 5: Vote_Average (8.3)
                        double rating = Double.parseDouble(datos[5]);

                        // Col 7: Genre ("Action, Adventure") -> Tomamos solo el primero
                        String generoStr = datos[7].replace("\"", "");
                        if (generoStr.contains(",")) {
                            generoStr = generoStr.split(",")[0].trim();
                        }

                        // CAMBIO: Ahora usamos el String directamente
                        String genero = generoStr;

                        // Col 8: Poster_Url
                        String poster = datos[8];

                        // 3. Creamos el objeto Película
                        Pelicula p = new Pelicula(-1, titulo, "Director", 0, resumen, genero, rating, anio, poster);

                        // 4. Guardamos en la BD
                        peliculaDAO.guardar(p);

                    } catch (Exception e) {
                        System.err.println("Error al procesar línea: " + datos[1] + " -> " + e.getMessage());
                    }
                }
            }
            System.out.println("¡Importación finalizada con éxito!");

        } catch (IOException e) {
            throw new ErrorDeInicializacionException("Error de lectura/escritura al procesar el archivo CSV.", e);
        }
    }

    // --- LÓGICA DEL TOP 10 vs RANDOM ---

    public List<Pelicula> obtenerTop10() {
        List<Pelicula> todas = peliculaDAO.devolverListaPelicula();
        // Ordenamos por rating de mayor a menor
        todas.sort(new ComparadorPeliculaPorRating());
        // Devolvemos las primeras 10 (o menos si no hay tantas)
        return todas.subList(0, Math.min(10, todas.size()));
    }

    /**
     * Retorna una lista de 10 o menos peliculas ordenadas al azar.
     * 
     * @return lista con 10 o menos peliculas mezcladas.
     */
    public List<Pelicula> obtener10Aleatorias() {
        List<Pelicula> todas = peliculaDAO.devolverListaPelicula();
        // Mezclamos la lista
        Collections.shuffle(todas);
        return todas.subList(0, Math.min(10, todas.size()));
    }

    public PeliculaDAO getPeliculaDao() {
        return peliculaDAO;
    }

    public void setPeliculaDao(PeliculaDAO peliculaDao) {
        this.peliculaDAO = peliculaDao;
    }

}