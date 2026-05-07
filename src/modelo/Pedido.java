package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum EstadoPedido {
        PENDIENTE, CONFIRMADO, EN_CAMINO, ENTREGADO, CANCELADO
    }

    private String id;
    private String clienteId;
    private String clienteNombre;
    private ArrayList<ItemCarrito> items;
    private double total;
    private Pago pago;
    private EstadoPedido estado;
    private String fecha;
    private String direccionEntrega;

    public Pedido(String id, String clienteId, String clienteNombre,
                  ArrayList<ItemCarrito> items, double total,
                  Pago pago, String fecha, String direccionEntrega) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.items = new ArrayList<>(items);
        this.total = total;
        this.pago = pago;
        this.estado = EstadoPedido.CONFIRMADO;
        this.fecha = fecha;
        this.direccionEntrega = direccionEntrega;
    }

    public String getId() { return id; }
    public String getClienteId() { return clienteId; }
    public String getClienteNombre() { return clienteNombre; }
    public ArrayList<ItemCarrito> getItems() { return items; }
    public double getTotal() { return total; }
    public Pago getPago() { return pago; }
    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }
    public String getFecha() { return fecha; }
    public String getDireccionEntrega() { return direccionEntrega; }

    public String getResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(id).append("\n");
        sb.append("Fecha: ").append(fecha).append("\n");
        sb.append("Cliente: ").append(clienteNombre).append("\n");
        sb.append("Estado: ").append(estado).append("\n");
        sb.append("Productos:\n");
        for (ItemCarrito item : items) {
            sb.append("  • ").append(item.toString()).append("\n");
        }
        sb.append("Total: $").append(String.format("%.2f", total)).append("\n");
        sb.append("Pago: ").append(pago.getTipoPago()).append("\n");
        sb.append("Dirección: ").append(direccionEntrega);
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Pedido #" + id + " | " + fecha + " | $" +
               String.format("%.2f", total) + " | " + estado;
    }
}
