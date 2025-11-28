//Verificacion JavaDoc -> Realizada.

package servicio;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import excepciones.ErrorApiOMDbException;
import org.json.JSONArray;
import org.json.JSONObject;
import modelo.catalogo.Pelicula;

/**
 * Este servicio se va a encargar de comunicarse con el servicio OMDb de las
 * peliculas a buscar.
 * Se crea una llave de acceso para el servicio en la web (API_KEY)
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public class ServicioOMDb {
    private static final String API_KEY = "897ebd22";
    private static final String URL_BASE = "https://www.omdbapi.com/";

    /**
     * Constructor de ServicioOMDb.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     */
    public ServicioOMDb() {
    }
    

    /**
     * Busca películas en el servicio OMDb y devuelve una lista de coincidencias.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param tituloBusqueda El título de la película a buscar.
     * @return Una lista de coincidencias de películas.
     * @throws ErrorApiOMDbException si ocurre un error de conexión o formato de
     *                                respuesta inválido.
     */
    public List<Pelicula> buscarPeliculas(String tituloBusqueda) throws ErrorApiOMDbException {
        try {
            String tituloCodificado = tituloBusqueda.replace(" ", "+");
            // Es la implementación de la URL de búsqueda de la pelicula escrita por el
            // cliente en el servicio
            String url = URL_BASE + "?s=" + tituloCodificado + "&apikey=" + API_KEY;

            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest requerimiento = HttpRequest.newBuilder().uri(URI.create(url)).build();

            HttpResponse<String> respuesta = cliente.send(requerimiento, HttpResponse.BodyHandlers.ofString());

            List<Pelicula> resultados = new ArrayList<>();
            JSONObject json = new JSONObject(respuesta.body());

            if (json.has("Response") && "False".equals(json.getString("Response"))) {
                throw new ErrorApiOMDbException("Película no encontrada 🙁");
            }
            if (json.has("Search")) {
                JSONArray elementos = json.getJSONArray("Search");
                for (int i = 0; i < elementos.length(); i++) {
                    JSONObject elemento = elementos.getJSONObject(i);
                    // creamos una pelicula "ligera" solo con lo básico para las coincidencias
                    String titulo = elemento.getString("Title");
                    String anioStr = elemento.getString("Year");
                    int anio = 0;
                    try {
                        anio = Integer.parseInt(anioStr.split("-")[0]);
                    } catch (Exception ex) {
                        //no pasa nada si ignoramos el error del año 
                    }

                    String poster = elemento.has("Poster") ? elemento.getString("Poster") : "";
                    String imdbID = elemento.getString("imdbID"); // guardamos el ID para buscar detalles luego
                    Pelicula peliculaCoincidente = new Pelicula(-1, titulo, "N/A", 0, imdbID, null, 0.0, anio, poster);
                    resultados.add(peliculaCoincidente);
                }
            }
            return resultados;

        } catch (Exception ex) {
            // envuelve cualquier error (IOException, JSONException) a la excepcion
            // personalizada.
            if (ex instanceof ErrorApiOMDbException) {
                throw (ErrorApiOMDbException) ex;
            }
            throw new ErrorApiOMDbException("Error de conexión con OMDb: " + ex.getMessage());
        }
    }

    /** 
     * Se obtiene el detalle completo de una película usando su IMDb ID
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param imdbID El IMDb ID de la película a obtener detalles.
     * @return Los detalles completos de la película.
     */
    public Pelicula obtenerDetallePelicula(String imdbID) throws Exception {
        try {
            String url = URL_BASE + "?i=" + imdbID + "&plot=full&apikey=" + API_KEY;

            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest peticion = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());

            String titulo = json.getString("Title");
            String director = json.optString("Director", "Desconocido");
            String resumen = json.optString("Plot", "Sin resumen disponible.");
            String poster = json.optString("Poster", "");

            // Parsear duración "120 min" -> 120
            int duracion = 0;
            try {
                String runtime = json.optString("Runtime", "0 min");
                duracion = Integer.parseInt(runtime.split(" ")[0]);
            } catch (Exception e) {
            }

            // Parsear Rating
            double rating = 0.0;
            try {
                rating = Double.parseDouble(json.optString("imdbRating", "0.0"));
            } catch (Exception e) {
            }

            // Parsear Año
            int anio = 0;
            try {
                anio = Integer.parseInt(json.optString("Year", "0").split("–")[0]);
            } catch (Exception e) {
            }

            String genero = json.optString("Genre", "Drama").split(",")[0].trim();

            return new Pelicula(-1, titulo, director, duracion, resumen, genero, rating, anio, poster);
        } catch (Exception ex) {
            throw new ErrorApiOMDbException("Error al obtener detalles de la película", ex);
        }
    }
}
