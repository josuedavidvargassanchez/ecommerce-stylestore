package modelo;

import java.io.Serializable;

/**
 * Clase abstracta para métodos de pago.
 * Aplica: Abstracción, Polimorfismo
 */
public abstract class Pago implements Serializable {
    private static final long serialVersionUID = 1L;

    private double monto;
    private String fecha;

    public Pago(double monto, String fecha) {
        this.monto = monto;
        this.fecha = fecha;
    }

    // Método abstracto — cada tipo de pago lo implementa
    public abstract String procesarPago();

    public abstract String getTipoPago();

    public double getMonto() { return monto; }
    public String getFecha() { return fecha; }

    @Override
    public String toString() {
        return getTipoPago() + " — $" + String.format("%.2f", monto) + " | " + fecha;
    }
}
