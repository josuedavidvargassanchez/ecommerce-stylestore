package modelo;

/**
 * Producto de tipo Calzado.
 * Aplica: Herencia (extends Producto), Polimorfismo (@Override)
 */
public class Calzado extends Producto {
    private static final long serialVersionUID = 1L;

    private int numeracion;   // 36, 37, 38... 45
    private String color;
    private String material;  // cuero, lona, sintético
    private String genero;    // Hombre, Mujer, Unisex, Niño

    public Calzado(String id, String nombre, String descripcion, double precio,
                   int stock, String categoria, String marca,
                   int numeracion, String color, String material, String genero) {
        super(id, nombre, descripcion, precio, stock, categoria, marca);
        this.numeracion = numeracion;
        this.color = color;
        this.material = material;
        this.genero = genero;
    }

    @Override
    public String getTipoProducto() {
        return "CALZADO";
    }

    @Override
    public String getDetallesEspecificos() {
        return "Talla: " + numeracion + " | Color: " + color +
               " | Material: " + material + " | Género: " + genero;
    }

    // Getters y Setters
    public int getNumeracion() { return numeracion; }
    public void setNumeracion(int numeracion) { this.numeracion = numeracion; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
}
