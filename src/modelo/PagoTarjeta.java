package modelo;

/**
 * Pago con tarjeta de crédito/débito.
 * Aplica: Herencia, Polimorfismo (@Override)
 */
public class PagoTarjeta extends Pago {
    private static final long serialVersionUID = 1L;

    private String numeroTarjeta; // Solo últimos 4 dígitos por seguridad
    private String nombreTitular;
    private String tipoTarjeta; // Crédito / Débito

    public PagoTarjeta(double monto, String fecha, String numeroTarjeta,
                       String nombreTitular, String tipoTarjeta) {
        super(monto, fecha);
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTitular = nombreTitular;
        this.tipoTarjeta = tipoTarjeta;
    }

    @Override
    public String procesarPago() {
        return "Pago con tarjeta " + tipoTarjeta + " aprobado. " +
               "Tarjeta terminada en " + numeroTarjeta;
    }

    @Override
    public String getTipoPago() {
        return "Tarjeta " + tipoTarjeta;
    }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public String getNombreTitular() { return nombreTitular; }
    public String getTipoTarjeta() { return tipoTarjeta; }
}
