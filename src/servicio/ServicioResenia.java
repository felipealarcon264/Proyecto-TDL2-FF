package servicio;

import dao.interfaces.PeliculaDAO;
import modelo.catalogo.Pelicula;
import modelo.catalogo.Resenia;
import dao.FactoryDAO;
import dao.interfaces.ReseniaDAO;
import modelo.ente.Usuario;


import java.util.List;
import java.util.Scanner;

public class ServicioResenia {
    ReseniaDAO reseniaDAO;
    PeliculaDAO peliculaDAO;

    public ServicioResenia() {
        this.reseniaDAO = FactoryDAO.getReseniaDAO();
        this.peliculaDAO = FactoryDAO.getPeliculaDAO();
    }

    /**
     * Se encarga de cargar y guardar una reseña en la base de datos, se puede
     * cancelar.
     * Todos los mensajes se indican en guardar y cargaPelicula.
     *
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param scanner El Scanner para leer la entrada del usuario.
     * @param usuario Usuario que realiza la reseña.
     */
    public void cargarYguardarReseña(Scanner scanner, Usuario usuario) {
        Resenia reseña = cargaResenia(scanner, usuario);
        reseniaDAO.guardar(reseña); // Si es null, el DAO se encarga de dar error.
    }

    /**
     * Carga de una reseña por teclado.
     * Importante se le envia una lista de peliculas/contenidos para decidir a cual
     * hacer la reseña.
     * se asegura que el indice seleccionado sea valido.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param scanner El Scanner para leer la entrada del usuario.
     * @param usuario El usuario que está realizando la reseña.
     * @return Un objeto {@link Resenia} con los datos cargados, o null si el
     *         usuario cancela la operación o la lista de películas está vacía.
     */
    public Resenia cargaResenia(Scanner scanner, Usuario usuario) {
        String comentario;
        int seleccionPelicula;
        List<Pelicula> listaPelicula = peliculaDAO.devolverListaPelicula(); // Se obtiene la lista de la DB
        System.out.println("\n--- ✍️ Carga de Reseña ✍️ ---");
        if (listaPelicula == null || listaPelicula.isEmpty()) {
            System.out.println("ℹ️ No hay películas disponibles para reseñar.");
            return null;
        }
        for (int i = 0; i < listaPelicula.size(); i++) {
            System.out.println((i + 1) + ". " + listaPelicula.get(i).getTitulo());
        }
        System.out.println();// Espacio.

        // Bucle para asegurar que el número de película esté en el rango correcto.
        do {
            seleccionPelicula = this.ingresarNumeroValido(scanner,
                    "👉 Ingrese el número de la película (1 a " + listaPelicula.size() + "): ");
            if (seleccionPelicula < 1 || seleccionPelicula > listaPelicula.size()) {
                System.out.println("❌ Número fuera de rango. Intente de nuevo.");
            }
        } while (seleccionPelicula < 1 || seleccionPelicula > listaPelicula.size());

        // Indice real en la lista.
        int indiceSeleccionado = seleccionPelicula - 1;
        System.out.println("Resenia para la pelicula [" + listaPelicula.get(indiceSeleccionado).getTitulo() + "]");
        int calificacion;
        do {
            calificacion = this.ingresarNumeroValido(scanner, "Ingrese la calificación (0-5): ");
            if (calificacion < 0 || calificacion > 5) {
                System.out.println("❌ Calificación fuera de rango. Debe ser entre 0 y 5. Intente de nuevo.");
            }
        } while (calificacion < 0 || calificacion > 5); // Calificaciones 0-5
        System.out.println("Ingrese el comentario: ");
        comentario = scanner.nextLine();
        System.out.println("\n--- Confirmación de Carga: Reseña ---");
        System.out.println("Datos ingresados:" +
                "\nCalificación: " + calificacion +
                "\nComentario: " + comentario);
        boolean datosValidos = confirmacion(scanner);
        if (datosValidos)
            return new Resenia(-1, calificacion, comentario, 0, usuario, listaPelicula.get(indiceSeleccionado));
        else
            return null;
    }

    /**
     * Elimina una reseña existente de la base de datos.
     * Los mensajes seran emitidos por el metodo borrar de ReseniaDAO.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.2
     * 
     * @param resenia La reseña a eliminar.
     * @return true si se pudo borrar de la DB y de la lista, false en caso
     *         contrario.
     */
    public boolean eliminarResenia(Resenia resenia) {
        return reseniaDAO.borrar(resenia);
    }

    /**
     * Actualiza el estado de una reseña en la base de datos.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param resenia La reseña a actualizar su estado aprobado||desaprobado.
     * @return true si actualizo la resenia, false en caso contrario.
     */
    public boolean actualizarEstadoResenia(Resenia resenia) {
        return reseniaDAO.actualizar(resenia);
    }

    /**
     * Muestra por consola todas las reseñas de un usuario específico.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param idUsuario El ID del usuario.
     * @return true si se encontró y mostró al menos una reseña, false en caso
     *         contrario.
     */
    public boolean mostrarReseniasDeUsuario(int idUsuario) {
        List<Resenia> misResenias = obtenerReseniasDeUsuario(idUsuario);
        for (int i = 0; i < misResenias.size(); i++) {
            System.out.println("\n--- Reseña N°" + (i + 1) + " ---");
            System.out.println(misResenias.get(i));
        }
        return !misResenias.isEmpty();
    }

    /**
     * Filtra y devuelve una lista de reseñas que pertenecen a un usuario
     * específico.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param idUsuario El ID del usuario cuyas reseñas se quieren obtener.
     * @return Una lista de objetos {@link Resenia}.
     */
    public List<Resenia> obtenerReseniasDeUsuario(int idUsuario) {
        List<Resenia> reseniasDelUsuario = new java.util.ArrayList<>();
        List<Resenia> todasLasResenias = reseniaDAO.devolverListaResenia(); // Se busca en la DB
        if (todasLasResenias == null)
            return reseniasDelUsuario; // En caso de error en DAO
        for (Resenia resenia : todasLasResenias) {
            if (resenia.getUsuario() != null && resenia.getUsuario().getIdDB() == idUsuario) {
                reseniasDelUsuario.add(resenia);
            }
        }
        return reseniasDelUsuario;
    }

    /**
     * Pide al usuario que ingrese un número entero y valida la entrada.
     * Pide reintentar si se ingresa algo que no es un número.
     * 
     * @author Gemini.
     * @version 1.0.
     * 
     * @param scanner El objeto Scanner ya inicializado.
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
     * Solicita al usuario la confirmación de los datos ingresados.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.1
     * 
     * @param scanner El objeto {@link Scanner} para leer la entrada del usuario.
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

    public ReseniaDAO getReseniaDAOImpl() {
        return reseniaDAO;
    }

    public void setReseniaDAOImpl(ReseniaDAO reseniaDAOImpl) {
        this.reseniaDAO = reseniaDAOImpl;
    }

}
