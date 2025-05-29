# EcoMarketSPA Producto API

API RESTful para la gestión de productos en EcoMarketSPA. Permite crear, listar, actualizar, eliminar y buscar productos por diferentes criterios.

## Descripción del Modelo de Datos

- **Usuario** tiene un **Carrito** (1 a 1).
- **Usuario** puede tener muchos **Pedidos** (1 a muchos).
- **Pedido** puede tener muchos **DetallePedido** (1 a muchos).
- **Carrito** puede tener muchos **CarritoItem** (1 a muchos).
- **Producto** puede estar en muchos **CarritoItem** y **DetallePedido** (1 a muchos).

## Modelo

- **Producto**
  - `id`: Identificador único del producto.
  - `nombre`: Nombre del producto (obligatorio).
  - `marca`: Marca del producto (obligatorio).
  - `descripcion`: Descripción del producto (opcional).
  - `categoria`: Categoría del producto (obligatorio).
  - `precio`: Precio del producto (obligatorio).
  - `stock`: Cantidad disponible en inventario (obligatorio).
  - `imagenUrl`: URL de la imagen del producto (opcional).

## Endpoints

Base URL: `/api/v1/productos`

| Método | Endpoint                              | Descripción                                      | Body (JSON)                |
|--------|---------------------------------------|--------------------------------------------------|----------------------------|
| GET    | `/`                                   | Listar todos los productos                       | -                          |
| GET    | `/{id}`                               | Obtener producto por ID                          | -                          |
| GET    | `/nombre/{nombre}`                    | Buscar productos por nombre                      | -                          |
| GET    | `/marca/{marca}`                      | Buscar productos por marca                       | -                          |
| GET    | `/categoria/{categoria}`              | Buscar productos por categoría                   | -                          |
| GET    | `/precio/menor-que/{precio}`          | Buscar productos con precio menor que X          | -                          |
| GET    | `/precio/mayor-que/{precio}`          | Buscar productos con precio mayor que X          | -                          |
| GET    | `/stock/menor-que/{stock}`            | Buscar productos con stock menor que X           | -                          |
| GET    | `/stock/mayor-que/{stock}`            | Buscar productos con stock mayor que X           | -                          |
| POST   | `/`                                   | Crear nuevo producto                             | `{...ver ejemplo...}`      |
| PUT    | `/{id}`                               | Actualizar producto existente                    | `{...ver ejemplo...}`      |
| DELETE | `/{id}`                               | Eliminar producto por ID                         | -                          |

### Ejemplo de JSON para POST/PUT

```json
{
  "nombre": "Manzana Roja",
  "marca": "EcoFrut",
  "descripcion": "Manzanas rojas orgánicas, frescas y crujientes.",
  "categoria": "Frutas",
  "precio": 1.20,
  "stock": 150,
  "imagenUrl": "https://ejemplo.com/img/manzana-roja.jpg"
}
```

## Ejemplo de Respuesta

```json
{
  "id": 1,
  "nombre": "Manzana Roja",
  "marca": "EcoFrut",
  "descripcion": "Manzanas rojas orgánicas, frescas y crujientes.",
  "categoria": "Frutas",
  "precio": 1.20,
  "stock": 150,
  "imagenUrl": "https://ejemplo.com/img/manzana-roja.jpg"
}
```

## Requisitos

- Java 17+
- Maven
- MySQL (configurado en AWS)

## Configuración

Configura la conexión a MySQL en `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:mysql://<host>:<port>/<database>
spring.datasource.username=<usuario>
spring.datasource.password=<contraseña>
spring.jpa.hibernate.ddl-auto=update
```

## Ejecución

1. Clona el repositorio.
2. Configura el archivo `application.properties`.
3. Ejecuta la aplicación: `ProductoServiceApplication`

La API estará disponible en `http://localhost:8080/api/v1/productos`.

---

> Proyecto de aprendizaje desarrollado con Spring Boot.