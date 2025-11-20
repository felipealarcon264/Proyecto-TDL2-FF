# Plataforma de Streaming TDL2 - Entregable 3

Este proyecto es una aplicación de escritorio desarrollada en Java con Swing que simula una plataforma de streaming de películas. Cumple con los requisitos del Entregable 3 del curso Taller de Lenguajes II, incorporando una interfaz gráfica de usuario (GUI), manejo de concurrencia para tareas pesadas, persistencia de datos con SQLite y una arquitectura MVC (Modelo-Vista-Controlador) bien definida.

## ✨ Funcionalidades Implementadas

- **Interfaz Gráfica con Swing**:
  - **Navegación por Tarjetas (`CardLayout`)**: La aplicación utiliza un `CardLayout` para gestionar las diferentes pantallas (Login, Registro, Home, Carga) dentro de una única ventana principal (`JFrame`).
  - **Splash Screen**: Al iniciar, se muestra una pantalla de bienvenida (`JWindow`) durante 3 segundos.
  - **Login de Usuario**: Permite a los usuarios ingresar con su email y contraseña. La validación se realiza contra la base de datos.
  - **Registro de Nuevos Usuarios**: Un formulario completo que valida los datos (campos no vacíos, formato de email, DNI numérico) y verifica que el DNI y el email no estén ya registrados. Utiliza excepciones personalizadas para gestionar los errores de negocio.
  - **Pantalla Principal (Home)**: Muestra un catálogo de películas en una grilla con scroll. Incluye una barra de búsqueda, un botón para refrescar con 10 películas nuevas, un menú para ordenar la vista actual (por título o género), un saludo personalizado ("Hola, [usuario]") y un botón para cerrar sesión.
  - **Pantalla de Carga**: Después de un login exitoso, se muestra una vista de "Cargando..." para indicar que se están preparando los datos en segundo plano, mejorando la experiencia de usuario.
  - **Tarjetas de Película Interactivas**: Cada película en la grilla es un componente que muestra su póster, título, género y rating. Son interactivas, resaltando al pasar el ratón y respondiendo a los clics.

- **Concurrencia y Carga Asíncrona**:
  - **Carga de Catálogo (`SwingWorker`)**: La importación de películas desde el archivo CSV y la preparación de la vista principal se ejecutan en un hilo de trabajo (`SwingWorker`) para no congelar la interfaz.
  - **Carga de Imágenes de Pósters**: Cada tarjeta de película (`TarjetaPelicula`) carga su imagen desde una URL de internet de forma asíncrona, también usando `SwingWorker`. Esto permite que la interfaz se mantenga fluida mientras se descargan las imágenes.

- **Persistencia de Datos (DAO y SQLite)**:
  - **Base de Datos SQLite**: Se utiliza un archivo `plataforma.db` para almacenar toda la información.
  - **Inicialización Automática**: La clase `InicializadorDB` crea toda la estructura de tablas (`USUARIO`, `PELICULA`, etc.) si no existen al arrancar la aplicación.
  - **Patrón DAO (Data Access Object)**: La lógica de acceso a la base de datos está completamente separada de la lógica de negocio. Las interfaces (`PeliculaDAO`, `UsuarioDAO`) definen los contratos, y las implementaciones (`PeliculaDAOImpl`, `UsuarioDAOImpl`) contienen el código SQL.

- **Importación de Datos desde CSV**:
  - Al iniciar sesión por primera vez, el `ServicioPelicula` lee el archivo `movies_database.csv`, procesa cada línea y guarda las películas en la base de datos. Esta operación solo se realiza si la tabla de películas está vacía.
  
  ## 🌐 Integración con Servicios Externos (API OMDb)

Para cumplir con el requisito de búsqueda de contenido en línea, la aplicación se conecta a la API pública de **OMDb (Open Movie Database)**.

- **Arquitectura de Conexión**:
  - **`ServicioOMDb`**: Se implementó un servicio dedicado que encapsula la comunicación HTTP usando `java.net.http.HttpClient`.
  - **Parseo JSON**: Se utiliza la librería externa `org.json` para interpretar las respuestas de la API y convertirlas en objetos `Pelicula`.
  - **Manejo de Datos "Sucios"**: El servicio es robusto ante datos faltantes o formatos inconsistentes de la API (como años con guiones o valores "N/A"), asegurando que la aplicación no falle.

- **Flujo de Búsqueda y UX**:
  - **Búsqueda en Segundo Plano**: Las consultas a la API se ejecutan en hilos separados (`SwingWorker`) para evitar que la interfaz se congele ("freeze") durante la petición web.
  - **Feedback Visual**: Se reutiliza la `VistaCarga` (GIF animado) dentro de un diálogo modal para indicar al usuario que la búsqueda está en curso.
  - **Selección de Coincidencias**: Si la búsqueda arroja múltiples resultados, se abre una ventana de **Selección** (`VistaSeleccionOMDb`) que reutiliza el componente `TarjetaPelicula` en una grilla, permitiendo al usuario elegir visualmente el póster correcto.
  - **Vista de Detalle**: Al seleccionar una película, se hace una segunda petición para traer la metadata completa (Sinopsis, Rating, Director) y se muestra en una **Vista de Detalle** (`VistaDetalleOMDb`) con diseño oscuro.

- **Gestión de Errores**:
  - Se implementó la excepción `ErrorApiOMDbException` para encapsular problemas de conectividad o de la API, permitiendo que los Controladores muestren mensajes amigables al usuario.

### Decisión de Diseño: Carga de Recursos con `getResourceAsStream`

Para la carga del archivo `.csv`, se eligió `getClass().getResourceAsStream()` en lugar del tradicional `FileReader`.

- **`FileReader`**: Depende del sistema de archivos. Si el `.jar` se mueve, la ruta al archivo se rompe.
- **`getResourceAsStream()`**: Carga el archivo desde el classpath. Esto significa que el `.csv` se empaqueta **dentro del `.jar`**, creando una aplicación **autocontenida y portable** que funciona en cualquier máquina sin depender de archivos externos.

Esta es una mejor práctica que garantiza la robustez y facilidad de distribución de la aplicación.

- **Lógica de Negocio en Servicios**:
  - **Primera Visita vs. Visitas Recurrentes**: La primera vez que un usuario ingresa, se le muestran las 10 películas con mejor rating. En los accesos posteriores (dentro de la misma sesión de la aplicación), se muestran 10 películas aleatorias.

## 🚀 Cómo Ejecutar

1.  Asegúrate de tener el JDK de Java instalado (versión 17 o superior).
2.  El proyecto está configurado para ser ejecutado en un IDE como VS Code, Eclipse o IntelliJ.
3.  El punto de entrada de la aplicación es el método `main` en la clase `control.Aplicacion`.
4.  Al ejecutar, se creará automáticamente el archivo de base de datos `plataforma.db` en la raíz del proyecto con todas las tablas necesarias.

## 📂 Estructura del Proyecto

El proyecto sigue una arquitectura por capas para separar responsabilidades:

-   `src/`
    -   `control/`: **Controladores** que actúan como intermediarios entre la vista y el modelo.
        -   `Aplicacion.java`: Clase principal que inicializa y gestiona la navegación.
        -   `ControladorLogin.java`: Maneja la lógica de inicio de sesión.
        -   `ControladorRegistro.java`: Maneja la lógica de registro.
        -   `ControladorHome.java`: Gestiona el contenido de la pantalla principal.
    -   `vista/`: **Vistas** (componentes de la GUI en Swing). No contienen lógica de negocio.
        -   `VistaLogin.java`, `VistaRegistro.java`, `VistaHome.java`, `VistaCarga.java`.
        -   `TarjetaPelicula.java`: Componente personalizado para mostrar una película y su póster.
    -   `modelo/`: **Clases del Modelo** que representan las entidades del dominio.
        -   `catalogo/`: Clases como `Pelicula`, `Contenido`, `Resenia`.
        -   `ente/`: Clases como `Usuario`, `Cuenta`, `Datos_Personales`.
    -   `servicio/`: **Capa de Servicio** que contiene la lógica de negocio principal.
        -   `ServicioUsuario.java`: Lógica de validación y creación de usuarios.
        -   `ServicioPelicula.java`: Lógica de importación de CSV y obtención de listas de películas.
    -   `dao/`: **Patrón DAO** para el acceso a datos.
        -   `interfaces/`: Contratos para las operaciones de la base de datos.
        -   `implementaciones/`: Clases con las consultas SQL (JDBC) para SQLite.
    -   `basededatos/`: Clases relacionadas con la configuración de la base de datos.
        -   `ConexionDB.java`: Gestiona la conexión a SQLite.
        -   `InicializadorDB.java`: Crea el esquema de la base de datos.
    -   `excepciones/`: **Excepciones personalizadas** para un mejor manejo de errores de negocio.
    -   `comparadores/`: Clases `Comparator` para ordenar listas de objetos.
    -   `resources/`: Contiene recursos como imágenes y el archivo `movies_database.csv`.

## 🛠️ Tecnologías Utilizadas

-   **Lenguaje**: Java
-   **Interfaz Gráfica**: Java Swing
-   **Base de Datos**: SQLite
-   **Conector**: JDBC para SQLite
-   **Patrones de Diseño**:
    -   Modelo-Vista-Controlador (MVC)
    -   Data Access Object (DAO)
    -   Factory (en `FactoryDAO`)
    -   Singleton (implícito en la gestión de la conexión a la BD)

---
*Proyecto desarrollado para el Taller de Lenguajes II.*
