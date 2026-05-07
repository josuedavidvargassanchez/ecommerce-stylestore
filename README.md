# 🛒 StyleStore

Proyecto final — Java POO · Curso 1603 · 2026

---

## 👥 Integrantes

| Nombre | GitHub |
|---|---|
| Josué David Vargas Sánchez | [@josuedavidvargassanchez](https://github.com/josuedavidvargassanchez) |
|   Jhojaner Gueto Arevalo   | [
---

## 📋 Descripción

StyleStore es un sistema de comercio electrónico desarrollado en Java que permite gestionar productos de moda (ropa, calzado y accesorios), registrar usuarios, realizar compras mediante un carrito y procesar pedidos con distintos métodos de pago. Los datos persisten entre sesiones mediante serialización de archivos `.dat`.

---

## 🚀 Cómo ejecutar

### Requisitos

- Java JDK 17 o superior
- NetBeans IDE

### Pasos

1. Clonar el repositorio
2. Abrir NetBeans → `File` → `Open Project` → seleccionar la carpeta `StyleStore`
3. Clic derecho en el proyecto → `Clean and Build`
4. Presionar `Run` o `F6`

> ⚠️ La primera vez se crea automáticamente un administrador por defecto:
> - **Email:** `admin@stylestore.com`
> - **Contraseña:** `admin123`

---

## 🏗️ Tecnologías usadas

| Categoría | Tecnología |
|---|---|
| Lenguaje | Java JDK 17 |
| UI | Swing (javax.swing) |
| Persistencia | Serialización Java (archivos `.dat`) |
| Build | Ant (NetBeans) |
| IDE | NetBeans |
| Control de versiones | Git + GitHub |

---

## 🧩 Diagrama de clases UML

![Diagrama de clases](docs/uml/DiagramaClases.png)

---

## 📐 Diagrama de casos de uso

![Diagrama de casos de uso](docs/uml/DiagramaCasosUso.png)

---

## 🎯 Funcionalidades implementadas

- [x] Gestión de productos (CRUD con control de stock)
- [x] Gestión de usuarios — registro e inicio de sesión (Cliente / Administrador)
- [x] Carrito de compras — agregar, eliminar, modificar cantidades y calcular totales
- [x] Flujo completo de pedido — carrito → confirmación → método de pago → pedido generado
- [x] Historial de pedidos del cliente
- [x] Interfaz gráfica funcional con Swing
- [x] Persistencia de datos

---

## 📐 Conceptos POO aplicados

| Concepto | Clase / método donde se aplica |
|---|---|
| **Herencia** | `Cliente extends Usuario`, `Administrador extends Usuario`, `Ropa / Calzado / Accesorio extends Producto`, `PagoEfectivo / PagoTarjeta / PagoTransferencia extends Pago` |
| **Encapsulación** | Todos los atributos son `private` con getters/setters en todas las entidades |
| **Polimorfismo** | `@Override` en `getRol()`, `getTipoProducto()`, `getDetallesEspecificos()`, `procesarPago()` |
| **Abstracción** | Clases abstractas `Producto`, `Usuario` y `Pago` con métodos abstractos |
| **Colecciones** | `ArrayList` en `Carrito`, `Cliente` y `Tienda` |
| **Excepciones** | `StockInsuficienteException`, `AutenticacionException`, `ProductoNoEncontradoException` con `try/catch` |
