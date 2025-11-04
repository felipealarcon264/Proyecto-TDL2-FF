package control;

import java.util.Scanner;

import ente.Administrador;
import ente.Cuenta;

import catalogo.Pelicula;
import catalogo.Resenia;
import ente.Usuario;
import servicio.ServicioPelicula;
import servicio.ServicioResenia;
import servicio.ServicioUsuario;


/**
 * Gestiona los menús y la interacción con el usuario para las diferentes
 * sesiones (Administrador y Cuenta).
 * 
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 * 
 */
public class MenuManager {
    /**
     * Constructor por defecto.
     */
    public MenuManager() {
    }

    /**
     * Simula la sesión de un administrador, mostrando su menú de opciones.
     * 
     * @param in  El Scanner para leer la entrada del usuario.
     * @param adm El objeto Administrador que ha iniciado sesión.
     */
    public void simulacionAdm(Scanner in, Administrador adm) {
        System.out.println("👋 ¡Hola, Administrador " + adm.getDatosPersonales().getNombre() + "!");
        ServicioPelicula servicioPelicula = new ServicioPelicula();
        ServicioUsuario servicioUsuario = new ServicioUsuario();
        ServicioResenia servicioResenia = new ServicioResenia();

        while (true) {
            System.out.println("\n--- Menú de Administrador ---");
            System.out.println("1. Agregar película");
            System.out.println("2. Borrar película");
            System.out.println("3. Ver y ordenar lista de usuarios");
            System.out.println("4. Borrar usuario");
            System.out.println("5. Gestionar reseñas");
            System.out.println("6. Salir (Volver al menú anterior)");
            System.out.print("Ingrese su opción (1-6): ");

            String opcion = in.nextLine();
            String aux;
            switch (opcion) {
                case "1":
                    servicioPelicula.cargarYguardarPelicula(in);
                    break;

                case "2":
                    System.out.println("\n--- 🎬 Lista de Películas 🎬 ---");
                    java.util.List<Pelicula> peliculas = servicioPelicula.getPeliculaDao().devolverListaPelicula();
                    if (peliculas != null && !peliculas.isEmpty()) {
                        for (Pelicula pelicula : peliculas) {
                            System.out.println(pelicula + "\n");
                        }
                        System.out.print("👉 Ingresa el título EXACTO de la película a eliminar: ");
                        aux = in.nextLine();
                        Pelicula peliculaAEliminar = servicioPelicula.getPeliculaDao().buscarPorTitulo(aux);
                        if (peliculaAEliminar != null) {
                            servicioPelicula.eliminarPelicula(peliculaAEliminar);
                        }
                    } else
                        System.out.println("ℹ️ No hay películas para mostrar.");
                    break;
                case "3":
                    servicioUsuario.ordenarListaUsuario(in);
                    // La lista ya se muestra dentro del método ordenarListaUsuario.
                    break;
                case "4":
                    System.out.println("\n--- 👥 Lista de Usuarios 👥 ---");
                    java.util.List<Usuario> usuarios = servicioUsuario.getUsuarioDao().devolverListaUsuarios();
                    for (Usuario usuario : usuarios) {
                        System.out.println(usuario + "\n");
                    }
                    System.out.print("👉 Ingresa el email EXACTO del usuario a eliminar: ");
                    aux = in.nextLine();
                    Usuario usuarioAEliminar = servicioUsuario.getUsuarioDao().buscarPorEmail(aux);
                    if (usuarioAEliminar != null) {
                        servicioUsuario.eliminarUsuario(usuarioAEliminar);
                    }
                    break;

                case "5":
                    System.out.println("\n--- ⚖️ Gestionar Reseñas ⚖️ ---");
                    java.util.List<Resenia> todasLasResenias = servicioResenia.getReseniaDAOImpl()
                            .devolverListaResenia();

                    if (todasLasResenias == null || todasLasResenias.isEmpty()) {
                        System.out.println("ℹ️ No hay reseñas para gestionar.");
                        break;
                    }

                    System.out.println("Selecciona una reseña para cambiar su estado (Aprobado/Pendiente):");
                    for (int i = 0; i < todasLasResenias.size(); i++) {
                        System.out.println("\n--- Reseña N°" + (i + 1) + " ---");
                        System.out.println(todasLasResenias.get(i));
                    }
                    System.out.println("\n0. Cancelar operación");

                    int seleccion = ingresarNumeroValido(in,
                            "👉 Ingresa el número de la reseña (0 para cancelar): ", 0, todasLasResenias.size());

                    if (seleccion == 0) {
                        System.out.println("Operación cancelada.");
                        break;
                    }

                    Resenia reseniaSeleccionada = todasLasResenias.get(seleccion - 1);
                    reseniaSeleccionada.setAprobado(1 - reseniaSeleccionada.getAprobado());
                    servicioResenia.actualizarEstadoResenia(reseniaSeleccionada);
                    break;
                case "6":
                    System.out.println("🚪 Cerrando sesión...");
                    return;
                default:
                    System.out.println("-------------------------------------");
                    System.out.println("Error: Opción no válida. Intente de nuevo.");
                    System.out.println("-------------------------------------");
            }
        }
    }

    /**
     * Simula la sesión de una cuenta de usuario, mostrando su menú de opciones.
     * 
     * @param in         El Scanner para leer la entrada del usuario.
     * @param cta        El objeto Cuenta que ha iniciado sesión.
     * @param plataforma La instancia principal de la plataforma.
     */
    public void simulacionCta(Scanner in, Cuenta cta) {
        ServicioPelicula servicioPelicula = new ServicioPelicula();
        ServicioResenia servicioResenia = new ServicioResenia();

        System.out.println("👋 ¡Hola, " + cta.getDatosPersonales().getNombre() + "!");
        while (true) {
            System.out.println("\n--- Menú de Cuenta ---");
            System.out.println("1. Ver y ordenar lista de películas.");
            System.out.println("2. Crear una reseña.");
            System.out.println("3. Ver mis reseñas.");
            System.out.println("4. Eliminar una reseña.");
            System.out.println("5. Salir");
            System.out.print("Ingrese su opción (1-5): ");

            String opcion = in.nextLine();
            switch (opcion) {
                case "1":
                    servicioPelicula.ordenarListaPelicula(in);
                    java.util.List<Pelicula> listaPeliculas = servicioPelicula.getPeliculaDao().devolverListaPelicula();
                    for (Pelicula pelicula : listaPeliculas) {
                        System.out.println(pelicula + "\n");
                    } // La lista se muestra aquí también por si el usuario no quiere ordenar.
                    break;
                case "2":
                    servicioResenia.cargarYguardarReseña(in, cta);
                    break;
                case "3":
                    System.out.println("\n--- ✍️ Mis Reseñas ✍️ ---");
                    boolean encontradas = servicioResenia.mostrarReseniasDeUsuario(cta.getIdDB());
                    if (!encontradas) {
                        System.out.println("ℹ️ Aún no has creado ninguna reseña.");
                    }
                    break;
                case "4":
                    System.out.println("\n--- 🗑️ Eliminar Reseña 🗑️ ---");
                    java.util.List<Resenia> misResenias = servicioResenia.obtenerReseniasDeUsuario(cta.getIdDB());

                    if (misResenias.isEmpty()) {
                        System.out.println("ℹ️ No tienes reseñas para eliminar.");
                        break;
                    }

                    System.out.println("Selecciona la reseña que deseas eliminar:");
                    for (int i = 0; i < misResenias.size(); i++) {
                        System.out.println("\n--- Reseña N°" + (i + 1) + " ---");
                        System.out.println(misResenias.get(i));
                    }
                    System.out.println("\n0. Cancelar operación");

                    int seleccion = ingresarNumeroValido(in,
                            "👉 Ingresa el número de la reseña a eliminar (0 para cancelar): ", 0, misResenias.size());

                    if (seleccion == 0) {
                        System.out.println("Operación cancelada.");
                        break;
                    }

                    servicioResenia.eliminarResenia(misResenias.get(seleccion - 1));
                    break;
                case "5":
                    System.out.println("🚪 Cerrando sesión...");
                    return;
                default:
                    System.out.println("-------------------------------------");
                    System.out.println("Error: Opción no válida. Intente de nuevo.");
                    System.out.println("-------------------------------------");
            }
        }
    }

    /**
     * Pide al usuario que ingrese un número entero y valida que esté en un rango.
     * Pide reintentar si se ingresa algo que no es un número o está fuera de rango.
     * 
     * @author Grupo 4 - Proyecto TDL2
     * @version 1.0
     * 
     * @param scanner El objeto Scanner ya inicializado.
     * @param mensaje El mensaje a mostrar al usuario.
     * @param min     El valor mínimo inclusivo.
     * @param max     El valor máximo inclusivo.
     * @return El número entero válido ingresado por el usuario.
     */
    private int ingresarNumeroValido(Scanner scanner, String mensaje, int min, int max) {
        int numero;
        while (true) {
            numero = ingresarNumeroValido(scanner, mensaje);
            if (numero >= min && numero <= max) {
                return numero;
            }
            System.out.println("❌ Número fuera de rango. Debe ser entre " + min + " y " + max + ". Intente de nuevo.");
        }
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
}