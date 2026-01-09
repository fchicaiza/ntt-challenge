# Sistema de Microservicios Bancarios - Reto Técnico NTT Data

Este proyecto implementa una solución de backend para la gestión de clientes, cuentas y movimientos bancarios, utilizando una arquitectura de microservicios, programación reactiva y comunicación asincrona.

---

## 🏗️ Arquitectura y Principios de Diseño

El sistema está diseñado bajo los siguientes estándares:
- **Arquitectura Hexagonal (Clean Architecture)**: Separación estricta entre dominio, aplicación e infraestructura.
- **Contract-First**: La API se define primero en OpenAPI (`openapi.yaml`) y el código se genera a partir de ella.
- **Microservicios**:
  - `customer-service`: Gestión de clientes.
  - `account-service`: Gestión de cuentas bancarias y transacciones.
- **Comunicación Asíncrona**: Uso de **RabbitMQ** para el registro de auditoría de movimientos.
- **Programación Reactiva**: Implementado con **Spring WebFlux** para un manejo eficiente de recursos.

---

## 🛠️ Stack Tecnológico

- **Lenguaje**: Java 17
- **Framework**: Spring Boot 3.1.3 (WebFlux)
- **Base de Datos**: H2 (En memoria para cada microservicio)
- **Mensajería**: RabbitMQ
- **Contenedores**: Docker & Docker Compose
- **Documentación**: Swagger UI / OpenAPI 3.0

---

## 🚀 Cómo Ejecutar el Proyecto

La forma más sencilla de levantar todo el ecosistema (Servicios, Base de Datos y RabbitMQ) es mediante Docker Compose:

1. Asegúrate de tener instalado **Docker** y **Docker Compose**.
2. En la raíz del proyecto, ejecuta:
   ```bash
   docker compose up -d --build
   ```
3. Los servicios estarán disponibles en:
   - **Customer Service**: http://localhost:8080
   - **Account Service**: http://localhost:8081
   - **RabbitMQ Management**: http://localhost:15672 (guest/guest)

---

## 📖 Documentación de la API

### Swagger UI (Interactiva)
Puedes probar los endpoints directamente desde tu navegador:
- [Customer Service Swagger](http://localhost:8080/swagger-ui.html)
- [Account Service Swagger](http://localhost:8081/swagger-ui.html)

### Especificación OpenAPI
- La especificación completa se encuentra en el archivo raíz: `openapi.yaml`.
- También disponible dinámicamente en: `http://localhost:8080/v3/api-docs.yaml`

---

## 🧪 Pruebas y Verificación

### Colección de Postman
Para facilitar las pruebas, se incluye el archivo **`Banking-API.postman_collection.json`** en la raíz. Solo impórtalo en Postman para tener todos los flujos listos (Crear cliente -> Crear cuenta -> Movimientos -> Reportes).

### Script de Base de Datos
El archivo **`BaseDatos.sql`** contiene el DDL inicial y los datos de prueba del caso de estudio (Jose Lema, Marianela Montalvo, etc.).

### Verificación de Auditoría (RabbitMQ)
Cada vez que se realiza un movimiento en el `account-service`, se envía un evento a RabbitMQ. El `customer-service` lo consume y lo registra en sus logs. Puedes verificarlo con:
```bash
docker compose logs customer-service | grep "AUDIT"
```

---

## ✅ Cumplimiento de Requerimientos

- [x] **F1**: CRUD de Clientes y Cuentas.
- [x] **F2**: Registro de movimientos (Débitos/Créditos) con validación de saldo.
- [x] **F3**: Manejo de excepciones con mensajes personalizados ("Saldo no disponible", etc.).
- [x] **F4**: Reporte de estado de cuenta por rango de fechas (Integración de servicios).
- [x] **F5**: Pruebas Unitarias (JUnit/Mockito).
- [x] **F6**: Pruebas de Integración.
- [x] **F7**: Despliegue en contenedores (Docker Compose).
