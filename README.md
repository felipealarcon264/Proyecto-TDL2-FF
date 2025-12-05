# 🎬 Plataforma de Streaming TDL2 - Entregable 3

Este proyecto es una aplicación de escritorio desarrollada en **Java** utilizando **Swing** para la interfaz gráfica. Simula una plataforma de streaming de películas completa, cumpliendo con los requisitos del **Trabajo Final Integrador** de la cátedra **Taller de Lenguajes II (2025)**.

El sistema implementa una arquitectura **MVC (Modelo-Vista-Controlador)** estricta, persistencia de datos con **SQLite**, consumo de servicios web externos (API OMDb) y administración eficiente de la concurrencia.

---

## ✨ Funcionalidades Principales

### 1. Gestión de Usuarios y Acceso
* **Registro Unificado:** Se optimizó el flujo de alta combinando la carga de *Datos Personales* y datos de la *Cuenta* en una única vista, mejorando la experiencia del usuario.
* **Validaciones Robustas:**
    * **Unicidad:** Se verifica en tiempo real contra la base de datos que el *Nombre de Usuario*, *Email* y *DNI* no estén duplicados.
    * **Integridad:** Se valida que no existan campos vacíos, que los datos numéricos sean correctos y que el formato de email sea válido.
    * **Manejo de Errores:** Uso de excepciones propias (`DatosInvalidosException`, `EmailYaRegistradoException`, etc.) para feedback preciso.
* **Login:** Autenticación segura validando credenciales contra la base de datos.

### 2. Interfaz Gráfica y Experiencia de Usuario (UX)
* **Navegación Centralizada (CardLayout):** La aplicación opera sobre una única ventana (`JFrame`) que intercambia paneles (`Login`, `Registro`, `Home`, `Perfil`), evitando la proliferación de ventanas emergentes y ofreciendo una navegación fluida.
* **Splash Screen:** Pantalla de bienvenida inicial (`JWindow`) al arrancar la aplicación.
* **Pantalla de Carga Inteligente:**
    * Se utiliza un `SwingWorker` para operaciones pesadas (login, carga de datos, búsquedas).
    * **Lógica de Espera Mínima:** Para evitar parpadeos molestos si la carga es demasiado rápida, se fuerza una espera de 2 segundos, asegurando que el usuario visualice el feedback de "Cargando...".
* **Estética:** Diseño visual consistente con modo oscuro ("Dark Mode") y colores de acento (Naranja/Rojo).

### 3. Catálogo y Lógica de Negocio ("Usuario Nuevo")
El sistema personaliza la experiencia según el historial del usuario, persistiendo un estado `ES_NUEVO` en la base de datos:

* **Usuario Nuevo (Primera vez):** Se muestra el **Top 10** de películas mejor valoradas junto con un mensaje de bienvenida especial. Tras el acceso, el estado se actualiza automáticamente en la BD.
* **Usuario Recurrente:** En visitas posteriores, se muestran **10 películas aleatorias** (10 Random) para fomentar el descubrimiento de contenido.
* **Configuración:** Se utiliza la constante `MINIMO_PELICULAS` para facilitar cambios futuros en la cantidad de items a mostrar.

### 4. Sistema de Reseñas y Perfil
* **Restricción de Unicidad:** Un usuario solo puede escribir **una reseña por película**. El sistema valida la existencia previa antes de abrir el formulario para evitar duplicados e inconsistencias.
* **Gestión de Memoria en Perfil:**
    * **Decisión de Diseño:** La vista `VistaPerfil` se **crea y destruye bajo demanda** cada vez que se accede a ella. Esto soluciona problemas de inconsistencia de datos (perfiles "fantasma") al cerrar sesión e ingresar con otro usuario inmediatamente.
    * **Eliminación:** El usuario tiene control total para borrar sus propias reseñas desde su perfil.

---

## 📂 Importación de Datos (CSV)

La aplicación incluye un motor de importación automática desde `movies_database.csv` con las siguientes características:

1.  **Sincronización al Inicio:** La verificación se ejecuta en el arranque (`Aplicacion.iniciar()`) para no penalizar el tiempo de login.
2.  **Validación de Duplicados:** Se compara cada línea del CSV contra la base de datos utilizando **Título y Resumen** como clave compuesta. Si la película ya existe, se omite; si es nueva, se inserta. Esto permite actualizar el archivo fuente sin perder datos previos.
3.  **Adaptación de Datos:**
    * **Género:** Se optó por usar `String` en lugar de `Enum` en el modelo para soportar la gran variedad de géneros presentes en el archivo CSV sin errores de parseo.
    * **Datos Faltantes:** Dado que el CSV original no provee información de "Director" ni "Duración", se completan con valores por defecto durante la importación para mantener la integridad del objeto.

---

## 🌐 Integración con API OMDb

Para la búsqueda en tiempo real, la aplicación sale del entorno local:

* **Búsqueda Online:** Utiliza `java.net.http.HttpClient` para consultar la API pública de OMDb.
* **Manejo de JSON:** Se emplea la librería `org.json` para interpretar las respuestas.
* **Concurrencia:** Las búsquedas corren en un hilo secundario (`SwingWorker`) para no congelar la interfaz gráfica mientras se espera la respuesta de internet.
* **Manejo de Errores API:** Se capturan excepciones específicas (`ErrorApiOMDbException`) para casos como "Película no encontrada", fallos de conexión o respuestas vacías, mostrando mensajes claros al usuario.

---

## 🛠️ Arquitectura Técnica

El proyecto sigue estrictamente el patrón **MVC**:

* `control`: Punto de entrada. `Main.java` (Launcher limpio) y `Aplicacion.java` (Gestor de Ventanas).
* `controlador`: Lógica que une la Vista y el Modelo (`ControladorHome`, `ControladorLogin`, etc.).
* `modelo`: Entidades (`Usuario`, `Pelicula`, `Resenia`).
* `vista`: Clases `JPanel` y `JFrame`.
* `servicio`: Lógica de negocio pura (Validaciones, Importación CSV, Conexión API).
* `dao`: Acceso a datos mediante interfaces y `FactoryDAO`.
* `basededatos`: Gestión de conexión SQLite e inicialización de tablas.

### Excepciones Propias Implementadas
* `CampoVacioException`
* `DatosInvalidosException`
* `DniYaRegistradosException`
* `EmailYaRegistradoException`
* `ErrorApiOMDbException`
* `ErrorDeInicializacionException`

---

## 🚀 Instrucciones de Ejecución

1.  **Requisitos:** JDK 17 o superior.
2.  **Base de Datos:** No requiere configuración manual. La clase `InicializadorDB` crea automáticamente el archivo `plataforma.db` y sus tablas si no existen.
3.  **Punto de Entrada:** Ejecutar la clase `control.Main`.

> **Nota de Desarrollo:** Aunque la aplicación es 100% gráfica, se han mantenido algunos métodos en el código que operan por consola (legacy) con fines de depuración y testing interno.

---
**Trabajo Práctico - Taller de Lenguajes II - 2025**