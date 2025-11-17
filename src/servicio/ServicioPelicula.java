package servicio;

import modelo.catalogo.Pelicula;
import comparadores.ComparadorPeliculaPorDuracion;
import comparadores.ComparadorPeliculaPorRating;
import comparadores.ComparadorPeliculaPorGenero;
import comparadores.ComparadorPeliculaPorTitulo;
// para el FactoryDAO
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
     * @throws ErrorDeInicializacionException si el archivo CSV no se encuentra o no se puede leer.
     */
    public void inicializarCatalogo() {
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
            throw new ErrorDeInicializacionException("Error crítico: El archivo '" + nombreArchivo + "' no se encontró en los recursos del proyecto.", null);
        }

        // 2. Leemos el archivo desde 'src/movies_database.csv'
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            
            String linea = br.readLine(); // Saltamos la cabecera
            
            while ((linea = br.readLine()) != null) {
                // EXPRESIÓN REGULAR MÁGICA: Separa por comas PERO ignora las comas que están dentro de comillas ""
                // Esto es vital porque el campo "Genre" viene como "Action, Adventure"
                String[] datos = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Verificamos tener las columnas necesarias (el archivo tiene 9 columnas)
                if (datos.length >= 9) {
                    try {
                        // Col 0: Release_Date (2021-12-15) -> Sacamos el año
                        int anio = 0;
                        if(datos[0].contains("-")) {
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
                        Pelicula p = new Pelicula(-1,titulo,"Director",0,resumen,genero,rating,anio,poster);

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
     * @return lista con 10 o menos peliculas mezcladas.
     */
    public List<Pelicula> obtener10Aleatorias() {
        List<Pelicula> todas = peliculaDAO.devolverListaPelicula();
        // Mezclamos la lista
        Collections.shuffle(todas);
        return todas.subList(0, Math.min(10, todas.size()));
    }
    
    /**
     * Carga una Pelicula desde la entrada estándar (consola).
     * Permite al usuario administrador ingresar los datos y confirmarlos.
     * * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     * * @param scanner El Scanner para leer la entrada del usuario.
     * @return Una pelicula o null en caso de cancelar la carga.
     */
    public Pelicula cargaPelicula(Scanner scanner) {
        System.out.println(" 🎬CARGA DE PELÍCULA🎬");
        // título resumen director duracion género
        String titulo = "";
        String resumen = "";
        String director = "";
        int duracion = -1;
        String genero = ""; // CAMBIO: Ahora es String
        int anio = 0;
        double rating = 0.0;
        String poster = "";
        boolean datosValidos = false;
        while (!datosValidos) {
            System.out.print("Ingrese el titulo: ");
            titulo = scanner.nextLine();
            System.out.print("Ingrese el resumen: ");
            resumen = scanner.nextLine();
            System.out.print("Ingrese el director: ");
            director = scanner.nextLine();
            duracion = ingresarNumeroValido(scanner, "Ingrese la duración: ");
            genero = seleccionarGenero(scanner); // CAMBIO: Devuelve String
            anio = ingresarNumeroValido(scanner, "Ingrese el año de lanzamiento: ");
            rating = ingresarDoubleValido(scanner, "Ingrese el rating promedio (ej: 7.5): ");
            System.out.print("Ingrese la URL del poster: ");
            poster = scanner.nextLine();
            System.out.println("CONFIRMACIÓN DE CARGA -> PELÍCULA.");
            System.out.println("Datos ingresados:" +
                    "\nTitulo: " + titulo +
                    "\nResumen: " + resumen +
                    "\nDirector: " + director +
                    "\nDuración: " + duracion +
                    "\nGenero: " + genero +
                    "\nAño: " + anio + "\nRating: " + rating + "\nPoster: " + poster);
            datosValidos = confirmacion(scanner);
            if (!datosValidos) {
                System.out.println("Quieres cancelar la carga? ");
                datosValidos = confirmacion(scanner);
                if (datosValidos) {
                    System.out.println("Carga cancelada.💡");
                    return null;
                }
            }
        }
        System.out.println("Datos confirmados...");
        // Pasamos -1 como ID temporal, ya que la DB asignará el real.
        // CAMBIO: El constructor de Pelicula debe aceptar 'genero' como String
        return new Pelicula(-1, titulo, director, duracion, resumen, genero, rating, anio, poster);
    }

    /**
     * Se encarga de cargar y guardar una pelicula en la base de datos, se puede
     * cancelar.
     * Todos los mensajes se indican en guardar y cargaPelicula.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     * * @param scanner El Scanner para leer la entrada del usuario.
     */
    public void cargarYGuardarPelicula(Scanner scanner) {
        Pelicula pelicula = this.cargaPelicula(scanner);
        this.peliculaDAO.guardar(pelicula); // Si es null, el DAO se encarga de dar error.
    }

    /**
     * Elimina una pelicula existente de la base de datos/
     * Los mensajes seran emitidos por el metodo borrar.
     * * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     * * @param pelicula La película a eliminar.
     * @return true si se pudo borrar de la DB y de la lista, false en caso
     * contrario.
     */
    public boolean eliminarPelicula(Pelicula pelicula) {
        return peliculaDAO.borrar(pelicula);
    }

    /**
     * Pregunta por pantalla qué manera de ordenación de la lista de Películas se
     * requiere y la muestra en pantalla.
     * * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     * * @param in El Scanner para leer la entrada del usuario.
     */
    public void ordenaryVerListaPelicula(Scanner in) {
        System.out.println("\n--- Ordenamiento de lista de películas ---");
        System.out.println("1. Ordenar por Título (A-Z).");
        System.out.println("2. Ordenar por Duración (menor a mayor).");
        System.out.println("3. Ordenar por Género (alfabético).");
        System.out.print("Ingrese su opción (1-3): ");
        List<Pelicula> listaPelicula = this.peliculaDAO.devolverListaPelicula(); // Se obtiene la lista de la DB

        while (true) {
            String opcion = in.nextLine();
            switch (opcion) {
                case "1":
                    listaPelicula.sort(new ComparadorPeliculaPorTitulo());
                    System.out.println("\n✅ Lista de películas ordenada por título:");
                    imprimirListaPeliculas(listaPelicula);
                    return;
                case "2":
                    listaPelicula.sort(new ComparadorPeliculaPorDuracion());
                    System.out.println("\n✅ Lista de películas ordenada por duración:");
                    imprimirListaPeliculas(listaPelicula);
                    return;
                case "3":
                    listaPelicula.sort(new ComparadorPeliculaPorGenero());
                    System.out.println("\n✅ Lista de películas ordenada por género:");
                    imprimirListaPeliculas(listaPelicula);
                    return;
                default:
                    System.out.print("❌ Error: Opción no válida. Intente de nuevo: ");
            }
        }
    }

    /**
     * Solicita al usuario la confirmación de los datos ingresados.
     * * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     * * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
     * @return true si el usuario confirma, false en caso contrario.
     */
    private boolean confirmacion(Scanner scanner) {
        System.out.print(" (S/N): ");
        String confirmacion = scanner.nextLine();
        while (!confirmacion.equalsIgnoreCase("S") && !confirmacion.equalsIgnoreCase("N")) {
            System.out.print("Entrada inválida. Ingrese 'S' para confirmar o 'N' para denegar: ");
            confirmacion = scanner.nextLine();
        }
        return confirmacion.equalsIgnoreCase("S");
    }

    /**
     * Pide al usuario que ingrese un número entero y valida la entrada.
     * Pide reintentar si se ingresa algo que no es un número.
     * * @author Grupo 4 - Proyecto TDL2
     * @version 4.0.
     * * @param scanner El objeto Scanner ya inicializado.
     * @param mensaje El mensaje a mostrar al usuario para solicitar la entrada.
     * @return El número entero válido ingresado por el usuario.
     */
    private int ingresarNumeroValido(Scanner scanner, String mensaje) {
        int numero;
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine(); // Leer siempre la línea completa.
            try {
                numero = Integer.parseInt(linea); // Intentar convertir la línea a entero.
                return numero; // Si tiene éxito, devolver el número y salir del método.
            } catch (NumberFormatException e) {
                // Si la conversión falla, es porque no se ingresó un número válido.
                System.out.println("❌ Entrada no válida. Por favor, ingrese solo números enteros.");
            }
        }
    }

    /**
     * Pide al usuario que ingrese un número decimal y valida la entrada.
     * Pide reintentar si se ingresa algo que no es un número.
     * * @author Grupo 4 - Proyecto TDL2
     * @version 4.0.
     * * @param scanner El objeto Scanner ya inicializado.
     * @param mensaje El mensaje a mostrar al usuario para solicitar la entrada.
     * @return El número double válido ingresado por el usuario.
     */
    private double ingresarDoubleValido(Scanner scanner, String mensaje) {
        double numero;
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine();
            try {
                numero = Double.parseDouble(linea.replace(',', '.')); // Reemplaza coma por punto
                return numero;
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada no válida. Por favor, ingrese un número decimal (ej: 8.5).");
            }
        }
    }

    /**
     * Solicita al usuario que seleccione un género de la lista de forma segura.
     * Devuelve el género como un String.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 4.0
     * * @param scanner El Scanner para leer la entrada del usuario.
     * @return El género seleccionado como String.
     */
    private String seleccionarGenero(Scanner scanner) {

        // Bucle infinito que solo se sale cuando hay un 'return'
        while (true) {
            System.out.println("\n--- Seleccione un Género ---");
            System.out.println("1. Acción");
            System.out.println("2. Anime");
            System.out.println("3. Drama");
            System.out.println("4. Comedia");
            System.out.println("5. Terror");
            System.out.print("Ingrese su opción (1-5): ");

            // 1. Leer la entrada SIEMPRE como String para evitar errores
            String opcion = scanner.nextLine();

            // 2. Usar un 'switch' para evaluar el String y retornar el género correspondiente
            switch (opcion) {
                case "1":
                    return "ACCION"; // Opción válida, devolvemos String
                case "2":
                    return "ANIME";
                case "3":
                    return "DRAMA";
                case "4":
                    return "COMEDIA";
                case "5":
                    return "TERROR";

                // 3. Si no coincide con 1-5, se ejecuta el 'default'
                default:
                    System.out.println("-------------------------------------------------");
                    System.out.println("Error: Opción no válida. Debe ingresar un número del 1 al 5.");
                    System.out.println("-------------------------------------------------");
                    // El bucle 'while(true)' vuelve a empezar
            }
        }
    }

    public PeliculaDAO getPeliculaDao() {
        return peliculaDAO;
    }

    public void setPeliculaDao(PeliculaDAO peliculaDao) {
        this.peliculaDAO = peliculaDao;
    }

    /**
     * Imprime una lista de películas con un índice numérico y un espacio entre
     * cada una.
     *
     * @param peliculas La lista de películas a imprimir.
     */
    private void imprimirListaPeliculas(List<Pelicula> peliculas) {
        if (peliculas == null || peliculas.isEmpty()) {
            System.out.println("ℹ️ No hay películas para mostrar.");
            return;
        }
        for (int i = 0; i < peliculas.size(); i++) {
            System.out.println("\n--- Película N°" + (i + 1) + " ---");
            System.out.println(peliculas.get(i));
        }
    }
}