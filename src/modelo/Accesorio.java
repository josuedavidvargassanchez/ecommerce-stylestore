package modelo;

/**
 * Producto de tipo Accesorio (bolsos, cinturones, joyería, etc.)
 * Aplica: Herencia (extends Producto), Polimorfismo (@Override)
 */
public class Accesorio extends Producto {
    private static final long serialVersionUID = 1L;

    private String tipo;       // Bolso, Cinturón, Gorra, Reloj, Collar, etc.
    private String color;
    private String material;
    private String dimensiones; // Ej: "30cm x 20cm"

    public Accesorio(String id, String nombre, String descripcion, double precio,
                     int stock, String categoria, String marca,
                     String tipo, String color, String material, String dimensiones) {
        super(id, nombre, descripcion, precio, stock, categoria, marca);
        this.tipo = tipo;
        this.color = color;
        this.material = material;
        this.dimensiones = dimensiones;
    }

    @Override
    public String getTipoProducto() {
        return "ACCESORIO";
    }

    @Override
    public String getDetallesEspecificos() {
        return "Tipo: " + tipo + " | Color: " + color +
               " | Material: " + material + " | Dim: " + dimensiones;
    }

    // Getters y Setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public String getDimensiones() { return dimensiones; }
    public void setDimensiones(String dimensiones) { this.dimensiones = dimensiones; }
}
