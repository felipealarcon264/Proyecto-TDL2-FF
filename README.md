ACLARACION IMPORTANTE: LAS ANOTACIONES SE HICIERON MEDIANTE EL USO DE NOTAS DE VOZ POR LO QUE SE LE PIDIO A LA IA HACER UNA ESTRUCTURA ADECUADA PARA PODER IMPLEMENTARLO EN ESTE ARCHIVO, AL FINAL SE ADJUNTA UN CODIGO CON COMANDOS SQL QUE USAMOS PARA LA CARGA DE NUESTRA BASE DE DATOS DE PRUEBA.

---

# ## Registro de Decisiones de Diseño y Avance (Entregable 2)

**Proyecto:** Plataforma de Streaming TDL2

**Objetivo:** Demostrar la persistencia de datos (DAO y JDBC) mediante la simplificación del modelo del Entregable 1 para cumplir estrictamente con los requisitos del Entregable 2.

---

## ## I. Metodología de Diseño y Simplificación

La filosofía de este entregable fue la **simplificación extrema** para enfocarse en el requisito de persistencia.

* **Simplificación del Modelo (Principio General):** Se simplificó la lógica para que las clases y métodos contengan solo lo *estrictamente necesario* para la entrega 2. Las secciones de código o los atributos que se consideran esenciales para el proyecto final pero irrelevantes para esta etapa de persistencia han sido **comentados** (marcados con `//No se necesita en la segunda entrega.`).
* **Decisión de Géneros y Listas:** Para la entidad `Pelicula`, se simplificó el manejo de listas (como géneros, elenco, etc.) para guardar/leer **solo un elemento clave** (el primer género, el primer actor) directamente en las columnas `TEXT` de la tabla `PELICULA`. Esto evita la complejidad de las tablas intermedias para las relaciones **muchos a muchos**, lo cual no es requerido en esta POC.
* **Métodos Internos (Login):** Se eliminaron métodos como `iniciarSesion()` de las clases `Administrador` y `Cuenta`, ya que la lógica de validación ahora reside en el `UsuarioDAO` (al buscar por credenciales) y en la capa de servicio (`Plataforma` o `Main`).

---

## ## II. Estrategia de Persistencia y JDBC

### A. Mapeo de Herencia (Usuario)

Se tomó la decisión de **no crear tablas separadas** para `Administrador` y `Cuenta`. En su lugar, se adoptó el patrón de Herencia de una Sola Tabla Lógica:

* **Tabla Única:** Todos los datos de `Administrador` y `Cuenta` se guardan en una sola tabla, `USUARIO`.
* **Columna Discriminadora:** Se añadió el campo `rol` (String) a la clase `Usuario` y a la tabla, el cual indica si la instancia recuperada es un **'ADMINISTRADOR'** o una **'CUENTA'**.
* **DAO Polimórfico:** El método `buscarPorEmailyContrasena` (o similar) en el `UsuarioDAO` lee este campo `rol` y, en función de su valor, devuelve la instancia concreta correcta (`new Entes.Administrador(...)` o `new Entes.Cuenta(...)`).

### B. Gestión de Identidad (ID y Claves Foráneas)

Se implementó una estrategia robusta para manejar la identidad de los objetos:

* **Asignación de ID al Recuperar:** El campo `idDB` se agregó a la clase `Usuario` (y por ende a `Administrador`/`Cuenta`). Este campo **no se carga** al crear el objeto por teclado. En su lugar, **la clase DAO** es la única responsable de leer el `ID` generado por la base de datos (columna `ID` de la tabla) y asignárselo al objeto (`usuario.setIdDB(rs.getInt("ID"))`).
* **`try-with-resources`:** Se utiliza esta estructura en todos los métodos DAO para garantizar que los recursos de JDBC (`Connection`, `PreparedStatement`, `ResultSet`) se cierren automáticamente, previniendo errores de bloqueo (`SQLITE_BUSY`) y fugas de memoria.

### C. Integridad Referencial de Datos Personales

Se estableció una regla de negocio estricta entre las entidades `Usuario` y `Datos_Personales`:

* **Relación Obligatoria (1:1):** Se asume que un `Usuario` no puede existir sin su `Datos_Personales` asociado, y se trata a ambos como una unidad indisoluble.
* **Borrado en Cascada Lógico:** Cuando se elimina un `Usuario`, el código DAO se encarga de **eliminar manualmente** el registro asociado en la tabla `DATOS_PERSONALES` para mantener la integridad de la base de datos.

---

## ## III. Reglas de Negocio, Validaciones y Flujos

### A. Restricciones de Unicidad

* **Email Único:** Se impone que solo puede haber un mismo correo electrónico registrado en la base de datos.
* **DNI Único:** Solo puede haber un registro de `Datos_Personales` asociado a un número de DNI.

### B. Comportamiento en el Programa

* **Acceso Rápido a Usuarios (Cache):** La clase `Plataforma` mantiene una copia en memoria (`List<Usuario>`,`List<Pelicula>`,`List<Resenia>`) de todos los usuarios cargados desde la base de datos. Se prefieren los métodos de búsqueda sobre esta lista (más rápidos) a los accesos repetitivos a la base de datos.
* **`equals()` Sobreescrito:** Se modificó el método `equals()` en las clases `Usuario` y `Contenido` para que la comparación se base en sus atributos identificadores (ej: `ID` o `DNI`/`Título`). Esto es fundamental para que las operaciones de colección (`list.remove()`) y las eliminaciones en cascada funcionen correctamente.
* **Validación de Login:** La validación de credenciales (`validarUsuario`) verifica la existencia del correo electrónico y la **corrección de la contraseña** simultáneamente.
* **Creación de Administrador:** Se implementó una restricción lógica en el simulador (capa de servicio) para que la creación de administradores no esté disponible para usuarios comunes, asumiendo que debe ocurrir mediante un proceso interno.

### C. Flujo de Reseñas

* **Precondición:** Se asume que el usuario que llega al método de crear reseñas es una `Cuenta` y que ya fue validada previamente, eliminando la necesidad de revalidar dentro del método `Reseña`.
* **Lógica de Aprobación:** Al registrarse, la reseña se guarda en la base de datos en estado **sin aprobar** (mediante la columna `APROBADO` en la tabla `RESENIA`).
* **Permisos (Roles):** La creación de la reseña y su posterior eliminación son exclusivas de la `Cuenta` que la creó. La aprobación o desaprobación queda a cargo del `Administrador`.




COMANDO SQL DE PRUEBA.


INSERT INTO DATOS_PERSONALES (NOMBRE, APELLIDO, DNI) VALUES
('uno','uno',1), -- Corregidas: comillas y formato
('dos','dos',2), -- Corregidas: comillas y formato
('Felipe', 'Alarcon', 45764848),
('Fernando', 'Mendez', 4382736),
('Carla', 'Gutierrez', 23453434),
('Carlos', 'Chales', 23453647);


INSERT INTO USUARIO (NOMBRE_USUARIO, EMAIL, CONTRASENA, ID_DATOS_PERSONALES, ROL) VALUES
('1','1','1', 1,'ADMINISTRADOR'), -- Corregidas: comillas, comas y estructura
('2','2','2', 2,'CUENTA'), -- Corregidas: comillas, comas y estructura
('felipe264', 'felipe264@mail.com', 'contr123', 3, 'ADMINISTRADOR'), -- El ID_DATOS_PERSONALES es 3, 4, 5, 6 asumiendo auto-incremento
('fernando264', 'fernando264@mail.com', 'contr123', 4, 'CUENTA'),
('carla264', 'carla264@mail.com', 'contr123', 5, 'ADMINISTRADOR'),
('carlos264', 'carlos264@mail.com', 'contr123', 6, 'CUENTA');

INSERT INTO PELICULA (TITULO, GENERO, RESUMEN, DIRECTOR, DURACION) VALUES 
('Misión Imposible', 'ACCION', 'Un agente debe probar su inocencia.', 'Brian De Palma', 6600.0), -- 110.0 * 60
('Mi Vecino Totoro', 'ANIME', 'Dos hermanas conocen espíritus del bosque.', 'Hayao Miyazaki', 5190.0), -- 86.5 * 60
('Forrest Gump', 'DRAMA', 'La vida de un hombre simple a través de la historia.', 'Robert Zemeckis', 8520.0), -- 142.0 * 60
('Superbad', 'COMEDIA', 'Adolescentes buscan alcohol para una fiesta.', 'Greg Mottola', 6780.0), -- 113.0 * 60
('El Conjuro', 'TERROR', 'Investigadores ayudan a una familia aterrorizada.', 'James Wan', 6750.0), -- 112.5 * 60
('John Wick', 'ACCION', 'Un exasesino busca venganza por su perro.', 'Chad Stahelski', 6060.0), -- 101.0 * 60
('Your Name', 'ANIME', 'Dos adolescentes intercambian cuerpos misteriosamente.', 'Makoto Shinkai', 6360.0), -- 106.0 * 60
('Parasite', 'DRAMA', 'Una familia pobre se infiltra en la vida de una familia rica.', 'Bong Joon-ho', 7920.0), -- 132.0 * 60
('Qué Pasó Ayer?', 'COMEDIA', 'Tres amigos despiertan sin recordar la noche anterior.', 'Todd Phillips', 6000.0), -- 100.0 * 60
('Hereditary', 'TERROR', 'Una familia descubre secretos oscuros tras una muerte.', 'Ari Aster', 7650.0), -- 127.5 * 60
('Duro de Matar', 'ACCION', 'Un policía lucha contra terroristas en un rascacielos.', 'John McTiernan', 7920.0), -- 132.0 * 60
('El Viaje de Chihiro', 'ANIME', 'Una niña entra en un mundo mágico de espíritus.', 'Hayao Miyazaki', 7500.0), -- 125.0 * 60
('El Padrino', 'DRAMA', 'El patriarca de una familia mafiosa transfiere el control.', 'Francis Ford Coppola', 10500.0), -- 175.0 * 60
('Anchorman', 'COMEDIA', 'Un presentador de noticias de los 70 enfrenta cambios.', 'Adam McKay', 5640.0), -- 94.0 * 60
('Psicosis', 'TERROR', 'Una secretaria desaparece tras robar dinero.', 'Alfred Hitchcock', 6540.0), -- 109.0 * 60
('Ataque a los Titanes', 'ANIME', 'Humanos luchan por sobrevivir contra gigantes.', 'Tetsurō Araki', 5400.0), -- 90.0 * 60
('Million Dollar Baby', 'DRAMA', 'Una boxeadora es entrenada por un veterano.', 'Clint Eastwood', 7920.0), -- 132.0 * 60
('Zombieland', 'COMEDIA', 'Dos supervivientes viajan por EE. UU. en busca de seguridad.', 'Ruben Fleischer', 5280.0), -- 88.0 * 60
('El Exorcista', 'TERROR', 'Dos sacerdotes intentan salvar a una niña poseída.', 'William Friedkin', 7320.0), -- 122.0 * 60
('Mad Max: Furia', 'ACCION', 'Persecución postapocalíptica en el desierto.', 'George Miller', 7200.0); -- 120.0 * 60
INSERT INTO RESENIA (CALIFICACION, COMENTARIO, APROBADO, FECHA_HORA, ID_USUARIO, ID_PELICULA) VALUES
(5, '¡Increíble! La mejor película de acción que he visto en años.', 1, '2025-10-01 10:30:00', 2, 1),
(4, 'Una joya de la animación. Pura magia y corazón.', 1, '2025-10-01 11:15:00', 4, 2),
(5, 'Conmovedora hasta las lágrimas. Una obra maestra.', 1, '2025-10-01 12:00:00', 6, 3),
(4, 'No paré de reír. Comedia adolescente en su máxima expresión.', 1, '2025-10-02 14:20:00', 2, 4),
(5, 'Genuinamente aterradora. La tensión es insoportable.', 1, '2025-10-02 15:00:00', 4, 5),
(5, 'Las coreografías de pelea son de otro nivel. Brutal.', 1, '2025-10-03 16:10:00', 6, 6),
(5, 'Visualmente hermosa y una historia de amor inolvidable.', 1, '2025-10-03 17:00:00', 2, 7),
(5, 'Merecidísimo Oscar. Un guion perfecto y actuaciones brillantes.', 1, '2025-10-04 18:00:00', 4, 8),
(4, 'Una locura de película, ideal para ver con amigos.', 1, '2025-10-04 19:30:00', 6, 9),
(4, 'Terror psicológico del bueno. Te deja perturbado.', 1, '2025-10-05 20:00:00', 2, 10),
(5, 'El clásico de acción por excelencia. John McClane es el mejor.', 1, '2025-10-05 21:00:00', 4, 11),
(5, 'La mejor película de Ghibli. Un mundo fantástico y único.', 1, '2025-10-06 09:00:00', 6, 12),
(5, 'Cine en mayúsculas. Una oferta que no puedes rechazar.', 1, '2025-10-06 10:30:00', 2, 13),
(4, 'Humor absurdo que funciona perfectamente. Ron Burgundy es un ídolo.', 1, '2025-10-07 11:00:00', 4, 14),
(5, 'El giro de guion original. Hitchcock era un genio.', 1, '2025-10-07 12:45:00', 6, 15),
(4, 'Intensa, oscura y con una animación espectacular.', 1, '2025-10-08 14:00:00', 2, 16),
(5, 'Un drama desgarrador. Las actuaciones son increíbles.', 1, '2025-10-08 15:30:00', 4, 17),
(4, 'Divertida, rápida y con reglas claras para sobrevivir.', 1, '2025-10-09 16:00:00', 6, 18),
(5, 'El terror en su estado más puro. Sigue dando miedo hoy.', 1, '2025-10-09 17:40:00', 2, 19),
(5, 'Adrenalina pura. Una persecución de dos horas que no da respiro.', 1, '2025-10-10 19:00:00', 4, 20);

INSERT INTO RESENIA (CALIFICACION, COMENTARIO, APROBADO, FECHA_HORA, ID_USUARIO, ID_PELICULA) VALUES
(2, 'Trama muy predecible. Las escenas de acción son inverosímiles.', 0, '2025-10-11 10:00:00', 6, 1),
(3, 'Es bonita, pero demasiado lenta. Casi me duermo.', 0, '2025-10-11 11:00:00', 2, 2),
(1, 'No entiendo por qué le gusta a tanta gente. Es aburridísima.', 0, '2025-10-11 12:15:00', 4, 3),
(2, 'Humor muy básico y grosero. No me hizo gracia.', 0, '2025-10-12 14:00:00', 6, 4),
(3, 'Más de lo mismo. Sustos predecibles y personajes planos.', 0, '2025-10-12 15:30:00', 2, 5),
(2, 'Violencia excesiva solo por un perro. El guion es nulo.', 0, '2025-10-13 16:45:00', 4, 6),
(3, 'La premisa del intercambio de cuerpos está muy vista.', 0, '2025-10-13 17:30:00', 6, 7),
(2, 'Interesante, pero muy deprimente. No es para todo el mundo.', 0, '2025-10-14 18:00:00', 2, 8),
(1, 'Un desastre. No recordaba lo mala que era.', 0, '2025-10-14 19:10:00', 4, 9),
(1, 'No entendí nada. Demasiado extraña y perturbadora sin motivo.', 0, '2025-10-15 20:20:00', 6, 10),
(3, 'Se nota que es antigua. Las películas de acción modernas son mejores.', 0, '2025-10-15 21:00:00', 2, 11),
(2, 'Los diseños de los espíritus son raros. No conecté con la protagonista.', 0, '2025-10-16 09:30:00', 4, 12),
(3, 'Demasiado larga. Entiendo que es un clásico, pero me costó terminarla.', 0, '2025-10-16 10:45:00', 6, 13),
(1, 'No me reí ni una sola vez. Humor estúpido.', 0, '2025-10-17 11:50:00', 2, 14),
(3, 'Para la época seguro fue impactante, pero hoy ya no tanto.', 0, '2025-10-17 12:30:00', 4, 15),
(2, 'Demasiado diálogo y poca acción de titanes. Esperaba más.', 0, '2025-10-18 14:00:00', 6, 16),
(3, 'Muy triste. No me gustan las películas de boxeo.', 0, '2025-10-18 15:25:00', 2, 17),
(2, 'Intenta ser graciosa pero no lo logra. Los zombies son de chiste.', 0, '2025-10-19 16:30:00', 4, 18),
(3, 'Ha envejecido mal. Los efectos especiales dan risa.', 0, '2025-10-19 17:45:00', 6, 19),
(2, 'Dolor de cabeza. Puro ruido y explosiones sin sentido.', 0, '2025-10-20 19:00:00', 6, 20);

