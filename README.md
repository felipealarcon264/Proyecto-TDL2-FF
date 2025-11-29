# 🎬 Plataforma de Streaming TDL2

Este proyecto es una aplicación de escritorio que simula una plataforma de streaming de películas. Desarrollada en Java con Swing para la interfaz gráfica, la aplicación implementa una arquitectura MVC, gestiona la concurrencia para tareas pesadas, utiliza SQLite para la persistencia de datos y consume la API de OMDb para la búsqueda de películas en línea.

## 📜 Índice

1.  [Tecnologías Utilizadas](#-tecnologías-utilizadas)
2.  [Funcionalidades Principales](#-funcionalidades-principales)
3.  [Estructura del Proyecto](#-estructura-del-proyecto)
4.  [Persistencia de Datos](#-persistencia-de-datos)

## 🛠️ Tecnologías Utilizadas

*   **Lenguaje:** Java
*   **Interfaz Gráfica:** Java Swing
*   **Base de Datos:** SQLite
*   **API Externa:** [OMDb API](https://www.omdbapi.com/) para búsqueda de películas.
*   **Concurrencia:** `SwingWorker` para tareas en segundo plano (cargas de datos, llamadas a API).
*   **Manejo de Datos:**
    *   Librería `org.json` para el parseo de respuestas JSON.
    *   `java.net.http.HttpClient` para las peticiones HTTP.

## ✨ Funcionalidades Principales

### 🎨 Interfaz Gráfica y Experiencia de Usuario (UX)

*   **Navegación Fluida:** Se utiliza `CardLayout` para gestionar las diferentes pantallas (Login, Registro, Home, Perfil) en una única ventana, permitiendo transiciones suaves.
*   **Splash Screen:** Una pantalla de bienvenida (`JWindow`) simula la carga inicial de recursos.
*   **Pantalla de Carga Inteligente:** Un `SwingWorker` muestra un GIF de carga durante tareas pesadas. Se asegura una duración mínima para evitar parpadeos.
*   **Modo Oscuro:** Toda la interfaz está diseñada con una paleta de colores oscuros, inspirada en las plataformas de streaming modernas y el Dark Mode.

### 👤 Gestión de Usuarios

*   **Registro Unificado:** El formulario de alta combina la carga de datos personales y de la cuenta en un solo paso.
*   **Validaciones Robustas:**
    *   Se asegura que el DNI, email y nombre de usuario sean únicos en la base de datos.
    *   Se valida el formato del email y que todos los campos obligatorios estén completos.
*   **Lógica de "Usuario Nuevo":**
    *   La primera vez que un usuario inicia sesión, se le muestra el Top 10 de películas y un mensaje de bienvenida.
    *   En inicios de sesión posteriores, ve una selección de películas aleatorias.
    *   Se agrego una columna en la base de datos del apartado usuario con el fin de determinar si es nuevo usuario.
    *   El estado del usuario se actualiza en la base de datos tras el primer login.

### 🎬 Gestión de Contenido y Catálogo

*   **Importación desde CSV:** El sistema lee el archivo `movies_database.csv` y lo sincroniza con la base de datos local.
*   **Estrategia de Actualización:** Al iniciar sesion, se verifica si cada película del CSV ya existe en la BD (comparando título y resumen) para evitar duplicados y permitir actualizaciones del archivo fuente. Se hizo de esta manera con el fin de asegurarnos que siempre estamos actualizados, no se realizó directamente con los tamaños porque tenemos conocimientos que la API externa puede contener mas peliculas que el propio archivo.
*   **Adaptación del Modelo:** El género de las películas se maneja como `String` para mayor flexibilidad. Los datos faltantes en el CSV (como director o duración) se completan con valores por defecto, de esta manera tampoco limitamos los datos externos de la API.

### 📝 Sistema de Reseñas y Perfil

*   **Mi Perfil:** Una vista dedicada donde el usuario puede ver sus datos y gestionar sus reseñas.
*   **Gestión de Memoria:** La vista de perfil se crea y destruye bajo demanda para evitar la persistencia de datos entre sesiones de diferentes usuarios.
*   **Realizacion de una Reseña desde la API externa** Luego de buscar una pelicula desde la API externa se puede realizar una reseña de ella primero verifica si esa pelicula ya está cargada en la base de datos, caso contrario la carga y luego guarda la reseña con los ID correspondientes.
*   **Reseñas Únicas:** Se impide que un usuario cree más de una reseña para la misma película.
*   **Validación de una Reseña:** Con el fin de seguir el rumbo de nuestro código original decidimos que la puntuación de una reseña no
se verá reflejada en la base de datos, pues una reseña debe ser validada por un administrador el cual se encargaría de esa lógica lo cual excede el alcance del entregable.
*   **Eliminación de Reseñas:** Los usuarios pueden eliminar sus propias reseñas desde su perfil.

### 🌐 Integración con API OMDb

*   **Búsqueda Externa:** La funcionalidad de búsqueda consulta directamente a la API de OMDb en lugar de la base de datos local.
*   **Búsqueda Asíncrona:** Las consultas a la API se realizan con `SwingWorker` para no congelar la interfaz de usuario.
*   **Manejo de Errores:** El sistema gestiona respuestas sin póster (mostrando una imagen por defecto) y errores de conexión.

## 📂 Estructura del Proyecto

El proyecto sigue estrictamente el patrón de diseño **Modelo-Vista-Controlador (MVC)**, complementado con una capa de Servicios y una capa de Acceso a Datos (DAO).

```
src
├── control/
│   ├── Main.java           # Punto de entrada de la aplicación.
│   └── Aplicacion.java     # Gestor principal de la ventana y el CardLayout.
│
├── controlador/
│   └── ...                 # Controladores que conectan vistas y modelos.
│
├── dao/
│   ├── interfaces/         # Interfaces del patrón DAO.
│   └── sqlite/             # Implementaciones DAO para SQLite.
│
├── excepciones/
│   └── ...                 # Excepciones personalizadas.
│
├── modelo/
│   ├── catalogo/           # Clases del dominio (Pelicula, Resenia).
│   └── ente/               # Clases del dominio (Usuario, Cuenta).
│
├── servicio/
│   └── ...                 # Lógica de negocio (validaciones, conexión a API).
│
└── vista/
    └── ...                 # Clases de la interfaz gráfica (JFrame, JPanel).
```

## 💾 Persistencia de Datos

*   **Motor de Base de Datos:** SQLite (archivo `plataforma.db`).
*   **Patrón de Diseño:** Se utiliza el patrón **Data Access Object (DAO)** para separar la lógica de negocio del acceso a datos.
*   **Factory DAO:** Una clase `FactoryDAO` centraliza la creación de las instancias DAO.
*   **Inicializador Automático:** La clase `InicializadorDB` verifica y crea las tablas necesarias (`USUARIO`, `PELICULA`, `RESENIA`, `DATOS_PERSONALES`) al arrancar la aplicación si estas no existen.