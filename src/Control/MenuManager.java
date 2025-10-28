package Control;

import java.util.Scanner;

import Catalogo.Pelicula;
import Catalogo.Resenia;
import Entes.Administrador;
import Entes.Cuenta;
import Entes.Usuario;
import Servicio.CargadoresyComunicacionDB;

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
    public MenuManager(){
    }

    /**
     * Simula la sesión de un administrador, mostrando su menú de opciones.
     * 
     * @param in         El Scanner para leer la entrada del usuario.
     * @param adm        El objeto Administrador que ha iniciado sesión.
     * @param plataforma La instancia principal de la plataforma.
     */
    public static void simulacionAdm(Scanner in, Administrador adm, Plataforma plataforma) {
        System.out.println("👋 ¡Hola, Administrador " + adm.getDatosPersonales().getNombre() + "!");
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
            CargadoresyComunicacionDB cargadores = new CargadoresyComunicacionDB();

            switch (opcion) {
                case "1":
                    plataforma.cargarYguardarPelicula(in);
                    break;

                case "2":
                    System.out.println("\n--- 🎬 Lista de Películas 🎬 ---");
                    java.util.List<Pelicula> peliculas = plataforma.getPeliDAO().devolverListaPelicula();
                    if (peliculas != null && !peliculas.isEmpty()) {
                        for (Pelicula pelicula : peliculas) {
                            System.out.println(pelicula + "\n");
                        }
                        System.out.print("👉 Ingresa el título EXACTO de la película a eliminar: ");
                        aux = in.nextLine();
                        Pelicula peliculaAEliminar = plataforma.getPeliDAO().buscarPorTitulo(aux);
                        if (peliculaAEliminar != null) {
                            plataforma.eliminarPelicula(peliculaAEliminar);
                        }
                    } else
                        System.out.println("ℹ️ No hay películas para mostrar.");
                    break;
                case "3":
                    plataforma.ordenarListaUsuario(in);
                    // La lista ya se muestra dentro del método ordenarListaUsuario.
                    break;
                case "4":
                    System.out.println("\n--- 👥 Lista de Usuarios 👥 ---");
                    java.util.List<Usuario> usuarios = plataforma.getUsrDAO().devolverListaUsuarios();
                    for (Usuario usuario : usuarios) {
                        System.out.println(usuario + "\n");
                    }
                    System.out.print("👉 Ingresa el email EXACTO del usuario a eliminar: ");
                    aux = in.nextLine();
                    Usuario usuarioAEliminar = plataforma.getUsrDAO().buscarPorEmail(aux);
                    if (usuarioAEliminar != null) {
                        plataforma.eliminarUsuario(usuarioAEliminar);
                    }
                    break;

                case "5":
                    System.out.println("\n--- ⚖️ Gestionar Reseñas ⚖️ ---");
                    java.util.List<Resenia> todasLasResenias = plataforma.getResDAO().devolverListaResenia();

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

                    int seleccion = cargadores.ingresarNumeroValido(in,
                            "👉 Ingresa el número de la reseña (0 para cancelar): ", 0, todasLasResenias.size());

                    if (seleccion == 0) {
                        System.out.println("Operación cancelada.");
                        break;
                    }

                    Resenia reseniaSeleccionada = todasLasResenias.get(seleccion - 1);
                    reseniaSeleccionada.setAprobado(1 - reseniaSeleccionada.getAprobado());
                    plataforma.actualizarEstadoResenia(reseniaSeleccionada);
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
    public static void simulacionCta(Scanner in, Cuenta cta, Plataforma plataforma) {
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
            CargadoresyComunicacionDB cargadores = new CargadoresyComunicacionDB();

            switch (opcion) {
                case "1":
                    plataforma.ordenarListaPelicula(in);
                    java.util.List<Pelicula> listaPeliculas = plataforma.getPeliDAO().devolverListaPelicula();
                    for (Pelicula pelicula : listaPeliculas) {
                        System.out.println(pelicula + "\n");
                    } // La lista se muestra aquí también por si el usuario no quiere ordenar.
                    break;
                case "2":
                    plataforma.cargarYguardarReseña(in, cta);
                    break;
                case "3":
                    System.out.println("\n--- ✍️ Mis Reseñas ✍️ ---");
                    boolean encontradas = plataforma.mostrarReseniasDeUsuario(cta.getIdDB());
                    if (!encontradas) {
                        System.out.println("ℹ️ Aún no has creado ninguna reseña.");
                    }
                    break;
                case "4":
                    System.out.println("\n--- 🗑️ Eliminar Reseña 🗑️ ---");
                    java.util.List<Resenia> misResenias = plataforma.obtenerReseniasDeUsuario(cta.getIdDB());

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

                    int seleccion = cargadores.ingresarNumeroValido(in,
                            "👉 Ingresa el número de la reseña a eliminar (0 para cancelar): ", 0, misResenias.size());

                    if (seleccion == 0) {
                        System.out.println("Operación cancelada.");
                        break;
                    }

                    plataforma.eliminarResenia(misResenias.get(seleccion - 1));
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
}