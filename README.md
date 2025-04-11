# 🧩 Microservicio de Gestión de Usuarios

## ✍️ Descripción general

Este microservicio fue desarrollado como parte del proceso técnico de selección (Abril 2025) por Alba Núñez Arranz. 

Su objetivo es gestionar usuarios dentro de una aplicación de control de acceso a un local.

Permite:
- Crear usuarios
- Consultarlos por identificador (UUID)
- Realizar búsquedas filtradas por nombre o edad, con paginación
- Documentación interactiva de la API
- Estructura de código clara, orientada a buenas prácticas
- Diseño funcional de un consumidor Kafka para el procesamiento asincrónico de altas

---

## 🧪 Tecnologías utilizadas

| Tecnología        | Versión      | Uso principal                                                 |
|-------------------|--------------|---------------------------------------------------------------|
| Java              | 11+          | Lenguaje principal                                            |
| Spring Boot       | 2.6.x        | Framework principal                                           |
| Spring Data JPA   | -            | Acceso a base de datos H2                                     |
| H2 Database       | embebida     | Persistencia de datos en archivo local                        |
| Swagger/OpenAPI 3 | springdoc    | Documentación de la API REST                                  |
| Maven             | 3.8+         | Gestión del proyecto y dependencias                           |
| JUnit 5 + Mockito | -            | Pruebas unitarias                                             |
| Kafka             | 2.8.0        | Enviar y procesar eventos de forma desacoplada y asincrónica. |

---

## 👤 Entidad: `User.java`

Ubicación: `src/main/java/com/ibm/userapi/model/User.java`

Campos:
- `uuid`: generado automáticamente (16 caracteres alfanuméricos)
- `nombre`, `apellidos`, `edad`
- `suscripcion`: booleano
- `codigo_postal`
- `fecha_creacion`: asignado automáticamente

Se utiliza JPA para mapear esta clase a una tabla H2.

---

## 🎯 Servicios

Controlador: `UserController.java`  
Ruta base: `/api/users`

| Método | Endpoint                | Descripción                               |
|--------|-------------------------|-------------------------------------------|
| POST   | `/api/users`            | Crear un nuevo usuario                    |
| GET    | `/api/users/{uuid}`     | Obtener usuario por UUID                  |
| GET    | `/api/users?nombre=X`   | Buscar por nombre                         |
| GET    | `/api/users?edad=30`    | Buscar por edad                           |

Soporta paginación (`?page=` y `?size=`) en las búsquedas filtradas.

---

## 📚 Documentación de la API (Swagger)

- Habilitada con `springdoc-openapi`
- Campos documentados en inglés y usando snake_case
- URL: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 💾 Acceso a la base de datos (H2)

Consola H2 disponible en:

- [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

Credenciales:
- JDBC URL: `jdbc:h2:file:./database/testdb`
- Usuario: `sa`  
- Contraseña: *(vacía)*

La base de datos está embebida y persistente a disco (no in-memory).

---

## ✅ Pruebas unitarias

Ubicación: `src/test/java/com/ibm/userapi/service/UserServiceTest.java`

Tecnologías: `JUnit 5`, `Mockito`  
Cubre:
- Creación de usuarios
- Consulta por UUID
- Búsqueda filtrada por nombre

Para ejecutar:
```bash
mvn test
```

---

## 🔒 Auditoría de Usuarios

Se implementa un sistema de auditoría para registrar las acciones realizadas sobre los usuarios en la aplicación cuando es creado o actualizado, se guarda un registro.

Ubicación: `src/main/java/com/ibm/userapi/controller/UserAuditCont.java`

Este controlador maneja las solicitudes HTTP para obtener el historial de auditoría de un usuario específico. Se expone a través del endpoint `/api/auditoria/{uuid}`.

| Método   | Endpoint                   | Descripción                                 |
|----------|----------------------------|---------------------------------------------|
| `GET`    | `/api/auditoria/{uuid}`    | Obtiene el historial de auditoría por UUID. |

- **Funcionamiento**: Cuando se realiza una solicitud GET con el UUID de un usuario, el controlador llama al repositorio `UserAuditRepo` para obtener el historial de auditoría de ese usuario.


---

## 📧 POSTMAN Endpoint : `POST /api/kafka/publish`

Ruta base: `/api/kafka`

| Método | Endpoint                | Descripción                               |
|--------|-------------------------|-------------------------------------------|
| POST   | `/api/kafka/publish`    | Publicar un evento de usuario en Kafka    |


Header de la solicitud:

| Key          | Value              |
|--------------|--------------------|
| Content-Type | application/json   |

Cuerpo de la Solicitud (BODY)


```json
{
  "nombre": "Lucía",
  "apellidos": "Gómez",
  "edad": 28,
  "suscripcion": true,
  "codigo_postal": "28001"
}
```

---

## 📈 Posible monitorización de Métricas del Consumidor

Para monitorizar las métricas del consumidor (éxitos, fallos, latencia):

- **Prometheus**: Configuramos **JMX Exporter** en Kafka para recolectar métricas, como el número de mensajes procesados correctamente, fallidos, y la latencia. Prometheus consulta estas métricas a intervalos definidos.
- **Grafana**: Utilizamos Grafana para visualizar estas métricas en tiempo real. Creamos dashboards que muestran:
  - **Kafka Consumer Metrics**: `kafka_consumer_fetch_manager_metrics_fetch_latency_ms`
  - **Mensajes procesados correctamente**: `kafka_consumer_consume_success_count`
  - **Errores en el consumidor**: `kafka_consumer_consume_error_count`
- **Alertas**: 
  - La latencia (`kafka_consumer_fetch_manager_metrics_fetch_latency_ms`) para que notifique cuando sea demasiado alta.
  - El número de fallos (`kafka_consumer_consume_error_count`) cuando supere un umbral crítico.

---

## 🔗 Posible integración del Consumidor con un Servicio Externo

Para integrar el consumidor con un servicio externo:

- **Petición HTTP**: Cuando el consumidor recibe un mensaje de Kafka, hace una **petición HTTP** (como `GET` o `POST`) a un servicio externo para validar datos adicionales.
- **Procesamiento de Respuestas**: Dependiendo de la respuesta del servicio externo, si es válido o no, el consumidor decide si procesa o si se maneja como un error.
- **Manejo de Errores**: Si la validación falla, el mensaje puede ser reenviado a un topic de errores para su análisis.