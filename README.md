# 📦 Sistema de Gestión de Inventario y Órdenes de Compra

Este proyecto es una API REST que ha sido desarrollada con **Java Spring Boot** y base de datos **PostgreSQL**, que permite gestionar proveedores, productos, inventario y órdenes de compra.

---

## 🚀 Tecnologías usadas

- Java 21+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven

---

## ⚙️ Configuración inicial

### 1. Clonar el repositorio

```bash
git clone https://github.com/rafafunes/GestionInventario.git
cd GestionInventario
```

### 2. Configurar base de datos

Edita `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gestioninventario
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Asegúrate de que PostgreSQL esté ejecutando la base de datos `gestioninventario` y que contenga las siguientes tablas:

- `proveedor`
- `producto`
- `inventario`
- `orden_compra`
- `orden_compra_detalle`

### 3. Compilar y ejecutar

```bash
./mvnw clean install
./mvnw spring-boot:run
```

El servidor iniciará en: (http://localhost:8080)

---

## 📮 Endpoints disponibles

> Base URL: `/api`

### 📦 Inventario

- `GET /api/inventario`  
  Consulta el stock actual por producto (DTO con cantidades, nombres, etc.)

---

### 🧾 Órdenes de compra

- `GET /api/ordenes-compra?estado=PENDIENTE`  
  Lista órdenes por estado (`PENDIENTE`, `RECIBIDA`, etc.)

- `POST /api/crear-orden-compra`  
  Crea una nueva orden de compra con detalles

  ```json
  {
    "idProveedor": 1,
    "estado": "PENDIENTE",
    "detalles": [
      {
        "productoId": 1,
        "cantidad": 5,
        "precioUnitario": 100.00
      }
    ]
  }
  ```

- `POST /api/ordenes-compra/{id}/recibir`  
  Marca una orden como `RECIBIDA` y actualiza el inventario

---

### 🏢 Proveedores

- `POST /api/crear-proveedor`  
  Crea un proveedor

  ```json
  {
    "nombre": "Distribuidora XYZ",
    "contacto": "Juan Pérez",
    "direccion": "Calle 123"
  }
  ```

---

### 🛒 Productos

- `POST /api/crear-producto`  
  Crea un producto.  
  Si ya existe por `codigo`, **solo actualiza el inventario**.

  ```json
  {
    "codigo": "PROD001",
    "descripcion": "Mouse Logitech",
    "precioUnitario": 20.00,
    "cantidadInicial": 15
  }
  ```

---

## 📁 Estructura del proyecto

```
├── controller
│   └── InventarioOrdenCompraController.java
├── dto
│   └── OrdenCompraRequestDTO.java, OrdenCompraResponseDTO.java, InventarioDTO.java
├── model
│   └── Producto.java, Proveedor.java, OrdenCompra.java, OrdenCompraDetalle.java, Inventario.java
├── repository
│   └── ProductoRepository.java, InventarioRepository.java, etc.
├── service
│   └── interfaces y servicios implementados
```

---

## 🛡️ Notas

- No se permite crear órdenes sin productos.
- Las órdenes ya `RECIBIDAS` no pueden ser marcadas de nuevo.
- Las relaciones entre entidades están protegidas contra bucles con `@JsonManagedReference` / `@JsonBackReference`.

---

## 👨‍💻 Autor

Desarrollado por [Rafael Funes].
