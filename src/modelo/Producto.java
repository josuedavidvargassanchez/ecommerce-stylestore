package modelo;

import java.io.Serializable;

public abstract class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String categoria;
    private String marca;

    public Producto(String id, String nombre, String descripcion,
                    double precio, int stock, String categoria, String marca) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.marca = marca;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public abstract String getTipoProducto();

    public abstract String getDetallesEspecificos();

    public void reducirStock(int cantidad) {
        this.stock -= cantidad;
    }

    public boolean hayStock(int cantidad) {
        return this.stock >= cantidad;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s — %s | $%.2f | Stock: %d",
                getTipoProducto(), nombre, marca, precio, stock);
    }
}
