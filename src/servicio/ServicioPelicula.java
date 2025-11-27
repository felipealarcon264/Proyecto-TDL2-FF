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
    final int MINIMO_PELICULAS = 10;

    public ServicioPelicula() {
        this.peliculaDAO = FactoryDAO.getPeliculaDAO();
    }

    /**
     * Importa las películas del CSV a la base de datos.
     * Verifica si la película ya existe por título para evitar duplicados.
     * * @throws ErrorDeInicializacionException si el archivo CSV no se encuentra o no
     * se puede leer.
     */
    public void importarPeliculaCSV() {
        System.out.println("Verificando catálogo de películas...");

        final String nombreArchivo = "/movies_database.csv";
        InputStream is = getClass().getResourceAsStream(nombreArchivo);

        if (is == null) {
            throw new ErrorDeInicializacionException(
                    "Error crítico: El archivo '" + nombreArchivo + "' no se encontró en los recursos.", null);
        }

        int nuevasPeliculas = 0;
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String linea = br.readLine(); 

            while ((linea = br.readLine()) != null) {
                // Regex para separar CSV respetando comillas
                String[] datos = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (datos.length >= 9) {
                    try {
                        // Datos clave para la búsqueda
                        String titulo = datos[1].replace("\"", "").trim();
                        String resumen = datos[2].replace("\"", "").trim(); // Quitamos comillas y espacios

                        // --- VALIDACIÓN ---
                        if (peliculaDAO.buscarPorTituloyResumen(titulo, resumen) != null) {
                            // Ya existe.
                            continue; 
                        }
                        
                        // Pelicula nueva.

                        // Col 0: Año
                        int anio = 0;
                        if (datos[0].contains("-")) {
                            try { anio = Integer.parseInt(datos[0].split("-")[0]); } catch (Exception e) {}
                        }

                        // Col 5: Rating
                        double rating = 0.0;
                        try { rating = Double.parseDouble(datos[5]); } catch (Exception e) {}

                        // Col 7: Genero
                        String genero = datos[7].replace("\"", "").split(",")[0].trim();

                        // Col 8: Poster
                        String poster = datos[8];

                        // 3. Crear y Guardar
                        Pelicula p = new Pelicula(-1, titulo, "Director", 0, resumen, genero, rating, anio, poster);
                        
                        if (peliculaDAO.guardar(p)) {
                            nuevasPeliculas++;
                            // Opcional: Imprimir cada 100 para no saturar la consola
                            if (nuevasPeliculas % 100 == 0) System.out.println("Cargando... " + nuevasPeliculas);
                        }

                    } catch (Exception e) {
                        // Si una línea falla, la mostramos pero seguimos con la siguiente
                        System.err.println("Error línea CSV: " + e.getMessage());
                    }
                }
            }
            
            if (nuevasPeliculas > 0) {
                System.out.println("✅ Actualización completa. Se agregaron " + nuevasPeliculas + " películas faltantes.");
            } else {
                System.out.println("✅ El catálogo ya estaba sincronizado (Total coincidió).");
            }

        } catch (IOException e) {
            throw new ErrorDeInicializacionException("Error de lectura del CSV.", e);
        }
    }
    // --- LÓGICA DEL TOP 10 vs RANDOM ---

    public List<Pelicula> obtenerTop10() {
        List<Pelicula> todas = peliculaDAO.devolverListaPelicula();
        todas.sort(new ComparadorPeliculaPorRating());
        return todas.subList(0, Math.min(MINIMO_PELICULAS, todas.size()));
    }

    public List<Pelicula> obtener10Aleatorias() {
        List<Pelicula> todas = peliculaDAO.devolverListaPelicula();
        Collections.shuffle(todas);
        return todas.subList(0, Math.min(MINIMO_PELICULAS, todas.size()));
    }

    public PeliculaDAO getPeliculaDao() {
        return peliculaDAO;
    }

    public void setPeliculaDao(PeliculaDAO peliculaDao) {
        this.peliculaDAO = peliculaDao;
    }
}