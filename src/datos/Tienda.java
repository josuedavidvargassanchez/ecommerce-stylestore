package datos;

import excepciones.AutenticacionException;
import excepciones.ProductoNoEncontradoException;
import excepciones.StockInsuficienteException;
import modelo.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Tienda {

    private ArrayList<Producto> productos;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Pedido> pedidos;
    private Usuario sesionActual;
    private Carrito carritoActual;

    public Tienda() {
        cargarDatos();
        inicializarDatosPorDefecto();
    }

    private void cargarDatos() {
        productos = GestorDatos.cargarProductos();
        usuarios = GestorDatos.cargarUsuarios();
        pedidos = GestorDatos.cargarPedidos();
    }

    private void inicializarDatosPorDefecto() {
        if (usuarios.isEmpty()) {
            usuarios.add(new Administrador("A001", "Admin Principal",
                    "admin@stylestore.com", "admin123", "ADMIN2026"));
            GestorDatos.guardarUsuarios(usuarios);
        }
        if (productos.isEmpty()) {
            agregarProductosMuestra();
        }
    }

    private void agregarProductosMuestra() {
        productos.add(new Ropa("R001", "Camiseta Básica Blanca", "Camiseta de algodón 100%",
                29900, 50, "Camisetas", "StyleBasic", "M", "Blanco", "Algodón", "Unisex"));
        productos.add(new Ropa("R002", "Jean Slim Fit", "Jean de corte slim moderno",
                89900, 30, "Pantalones", "DenimCo", "32", "Azul Oscuro", "Denim", "Hombre"));
        productos.add(new Ropa("R003", "Vestido Floral", "Vestido veraniego con estampado floral",
                75000, 20, "Vestidos", "FlowerMode", "S", "Rosa", "Poliéster", "Mujer"));
        productos.add(new Ropa("R004", "Hoodie Oversize", "Sudadera oversize con capucha",
                95000, 25, "Sudaderas", "UrbanWear", "L", "Negro", "Algodón/Poliéster", "Unisex"));
        productos.add(new Accesorio("A001", "Bolso de Cuero Marrón", "Bolso tote de cuero genuino",
                150000, 15, "Bolsos", "LeatherLux", "Bolso", "Marrón", "Cuero", "35cm x 28cm"));
        productos.add(new Accesorio("A002", "Gorra Negra", "Gorra snapback ajustable",
                35000, 40, "Gorras", "CapCity", "Gorra", "Negro", "Algodón", "Talla única"));
        productos.add(new Accesorio("A003", "Cinturón de Cuero", "Cinturón elegante con hebilla dorada",
                45000, 25, "Cinturones", "StyleBelt", "Cinturón", "Negro", "Cuero", "110cm"));
        productos.add(new Calzado("C001", "Zapatillas Blancas", "Sneakers casuales todo terreno",
                120000, 20, "Zapatillas", "WhiteStep", 40, "Blanco", "Cuero sintético", "Unisex"));
        productos.add(new Calzado("C002", "Botas Chelsea", "Botas de cuero estilo Chelsea",
                180000, 12, "Botas", "BootLab", 38, "Negro", "Cuero", "Mujer"));
        productos.add(new Calzado("C003", "Sandalias de Playa", "Sandalias cómodas para verano",
                40000, 35, "Sandalias", "BeachWalk", 42, "Café", "Caucho/Tela", "Hombre"));
        GestorDatos.guardarProductos(productos);
    }

    public Usuario iniciarSesion(String email, String password)
            throws AutenticacionException {
        try {
            for (Usuario u : usuarios) {
                if (u.getEmail().equalsIgnoreCase(email) &&
                        u.getPassword().equals(password)) {
                    sesionActual = u;
                    carritoActual = new Carrito();
                    return u;
                }
            }
            throw new AutenticacionException("Email o contraseña incorrectos.");
        } catch (AutenticacionException e) {
            throw e;
        } catch (Exception e) {
            throw new AutenticacionException("Error al iniciar sesión: " + e.getMessage());
        }
    }

    public Cliente registrarCliente(String nombre, String email, String password,
                                    String direccion, String telefono)
            throws AutenticacionException {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                throw new AutenticacionException("Ya existe un usuario con ese email.");
            }
        }
        String id = "C" + String.format("%03d", usuarios.size() + 1);
        Cliente cliente = new Cliente(id, nombre, email, password, direccion, telefono);
        usuarios.add(cliente);
        GestorDatos.guardarUsuarios(usuarios);
        return cliente;
    }

    public void cerrarSesion() {
        sesionActual = null;
        carritoActual = null;
    }

    public ArrayList<Producto> getProductos() { return productos; }

    public ArrayList<Producto> buscarProductos(String texto) {
        ArrayList<Producto> resultado = new ArrayList<>();
        String textoBusqueda = texto.toLowerCase();
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(textoBusqueda) ||
                    p.getMarca().toLowerCase().contains(textoBusqueda) ||
                    p.getCategoria().toLowerCase().contains(textoBusqueda) ||
                    p.getTipoProducto().toLowerCase().contains(textoBusqueda)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public ArrayList<Producto> filtrarPorTipo(String tipo) {
        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getTipoProducto().equalsIgnoreCase(tipo)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        GestorDatos.guardarProductos(productos);
    }

    public void actualizarProducto(Producto productoActualizado)
            throws ProductoNoEncontradoException {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId().equals(productoActualizado.getId())) {
                productos.set(i, productoActualizado);
                GestorDatos.guardarProductos(productos);
                return;
            }
        }
        throw new ProductoNoEncontradoException(
                "Producto con ID " + productoActualizado.getId() + " no encontrado.");
    }

    public void eliminarProducto(String id) throws ProductoNoEncontradoException {
        boolean eliminado = productos.removeIf(p -> p.getId().equals(id));
        if (!eliminado) {
            throw new ProductoNoEncontradoException("Producto con ID " + id + " no encontrado.");
        }
        GestorDatos.guardarProductos(productos);
    }

    public Producto buscarProductoPorId(String id) throws ProductoNoEncontradoException {
        for (Producto p : productos) {
            if (p.getId().equals(id)) return p;
        }
        throw new ProductoNoEncontradoException("Producto no encontrado: " + id);
    }

    public Carrito getCarrito() { return carritoActual; }

    public void agregarAlCarrito(Producto producto, int cantidad)
            throws StockInsuficienteException {
        carritoActual.agregarProducto(producto, cantidad);
    }

    public void quitarDelCarrito(String productoId) {
        carritoActual.eliminarProducto(productoId);
    }

    public void actualizarCantidadCarrito(String productoId, int cantidad)
            throws StockInsuficienteException {
        carritoActual.actualizarCantidad(productoId, cantidad);
    }

    public Pedido confirmarPedido(Pago pago, String direccionEntrega)
            throws StockInsuficienteException {
        if (carritoActual.estaVacio()) {
            throw new IllegalStateException("El carrito está vacío.");
        }
        for (ItemCarrito item : carritoActual.getItems()) {
            if (!item.getProducto().hayStock(item.getCantidad())) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para: " + item.getProducto().getNombre());
            }
        }
        for (ItemCarrito item : carritoActual.getItems()) {
            item.getProducto().reducirStock(item.getCantidad());
        }
        String idPedido = "PED-" + System.currentTimeMillis();
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        Pedido pedido = new Pedido(idPedido, sesionActual.getId(),
                sesionActual.getNombre(), carritoActual.getItems(),
                carritoActual.getTotal(), pago, fecha, direccionEntrega);

        if (sesionActual instanceof Cliente) {
            ((Cliente) sesionActual).agregarPedido(pedido);
        }

        pedidos.add(pedido);
        GestorDatos.guardarPedidos(pedidos);
        GestorDatos.guardarProductos(productos);
        GestorDatos.guardarUsuarios(usuarios);

        carritoActual.vaciar();
        return pedido;
    }

    public ArrayList<Pedido> getPedidosCliente() {
        ArrayList<Pedido> misPedidos = new ArrayList<>();
        if (sesionActual instanceof Cliente) {
            misPedidos.addAll(((Cliente) sesionActual).getHistorialPedidos());
        }
        return misPedidos;
    }

    public ArrayList<Pedido> getTodosPedidos() { return pedidos; }

    public void actualizarEstadoPedido(String pedidoId, Pedido.EstadoPedido nuevoEstado)
            throws ProductoNoEncontradoException {
        for (Pedido p : pedidos) {
            if (p.getId().equals(pedidoId)) {
                p.setEstado(nuevoEstado);
                GestorDatos.guardarPedidos(pedidos);
                return;
            }
        }
        throw new ProductoNoEncontradoException("Pedido no encontrado: " + pedidoId);
    }

    public ArrayList<Usuario> getUsuarios() { return usuarios; }
    public Usuario getSesionActual() { return sesionActual; }

    public String generarIdProducto(String prefijo) {
        return prefijo + String.format("%03d", productos.size() + 1);
    }
}
