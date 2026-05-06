package modelo;

/**
 * Producto de tipo Ropa.
 * Aplica: Herencia (extends Producto), Polimorfismo (@Override)
 */
public class Ropa extends Producto {
    private static final long serialVersionUID = 1L;

    private String talla;   // XS, S, M, L, XL, XXL
    private String color;
    private String material; // algodón, poliéster, lino, etc.
    private String genero;   // Hombre, Mujer, Unisex, Niño

    public Ropa(String id, String nombre, String descripcion, double precio,
                int stock, String categoria, String marca,
                String talla, String color, String material, String genero) {
        super(id, nombre, descripcion, precio, stock, categoria, marca);
        this.talla = talla;
        this.color = color;
        this.material = material;
        this.genero = genero;
    }

    @Override
    public String getTipoProducto() {
        return "ROPA";
    }

    @Override
    public String getDetallesEspecificos() {
        return "Talla: " + talla + " | Color: " + color +
               " | Material: " + material + " | Género: " + genero;
    }

    // Getters y Setters
    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
}
