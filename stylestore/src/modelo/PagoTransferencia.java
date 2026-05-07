package modelo;

public class PagoTransferencia extends Pago {
    private static final long serialVersionUID = 1L;

    private String banco;
    private String numeroReferencia;

    public PagoTransferencia(double monto, String fecha,
                              String banco, String numeroReferencia) {
        super(monto, fecha);
        this.banco = banco;
        this.numeroReferencia = numeroReferencia;
    }

    @Override
    public String procesarPago() {
        return "Transferencia registrada desde " + banco +
               ". Referencia: " + numeroReferencia;
    }

    @Override
    public String getTipoPago() {
        return "Transferencia — " + banco;
    }

    public String getBanco() { return banco; }
    public String getNumeroReferencia() { return numeroReferencia; }
}
