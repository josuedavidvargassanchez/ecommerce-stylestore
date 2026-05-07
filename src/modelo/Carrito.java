package modelo;

import excepciones.StockInsuficienteException;
import java.io.Serializable;
import java.util.ArrayList;

public class Carrito implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<ItemCarrito> items;

    public Carrito() {
        this.items = new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad)
            throws StockInsuficienteException {
        if (!producto.hayStock(cantidad)) {
            throw new StockInsuficienteException(
                "Stock insuficiente para \"" + producto.getNombre() +
                "\". Disponible: " + producto.getStock()
            );
        }
        for (ItemCarrito item : items) {
            if (item.getProducto().getId().equals(producto.getId())) {
                int nuevaCantidad = item.getCantidad() + cantidad;
                if (!producto.hayStock(nuevaCantidad)) {
                    throw new StockInsuficienteException(
                        "No hay suficiente stock. Ya tienes " + item.getCantidad() +
                        " en el carrito. Disponible: " + producto.getStock()
                    );
                }
                item.setCantidad(nuevaCantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarProducto(String productoId) {
        items.removeIf(item -> item.getProducto().getId().equals(productoId));
    }

    public void actualizarCantidad(String productoId, int nuevaCantidad)
            throws StockInsuficienteException {
        for (ItemCarrito item : items) {
            if (item.getProducto().getId().equals(productoId)) {
                if (!item.getProducto().hayStock(nuevaCantidad)) {
                    throw new StockInsuficienteException(
                        "Stock insuficiente. Disponible: " + item.getProducto().getStock()
                    );
                }
                item.setCantidad(nuevaCantidad);
                return;
            }
        }
    }

    public double getTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void vaciar() {
        items.clear();
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public ArrayList<ItemCarrito> getItems() { return items; }

    public int getTotalProductos() {
        int total = 0;
        for (ItemCarrito item : items) {
            total += item.getCantidad();
        }
        return total;
    }
}
