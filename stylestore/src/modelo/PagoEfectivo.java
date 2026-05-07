package modelo;

public class PagoEfectivo extends Pago {
    private static final long serialVersionUID = 1L;

    private double montoEntregado;

    public PagoEfectivo(double monto, String fecha, double montoEntregado) {
        super(monto, fecha);
        this.montoEntregado = montoEntregado;
    }

    @Override
    public String procesarPago() {
        double cambio = montoEntregado - getMonto();
        return "Pago en efectivo registrado. " +
               "Cambio a devolver: $" + String.format("%.2f", cambio);
    }

    @Override
    public String getTipoPago() {
        return "Efectivo";
    }

    public double getMontoEntregado() { return montoEntregado; }
    public double getCambio() { return montoEntregado - getMonto(); }
}
