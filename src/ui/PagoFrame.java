package ui;

import datos.Tienda;
import excepciones.StockInsuficienteException;
import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Pantalla de pago y confirmación del pedido.
 */
public class PagoFrame extends JDialog {

    private Tienda tienda;
    private ClienteFrame clienteFrame;
    private JComboBox<String> cmbMetodo;
    private JPanel panelDatosPago;
    private JTextField txtDireccion;

    // Campos tarjeta
    private JTextField txtNumTarjeta, txtNombreTitular;
    private JComboBox<String> cmbTipoTarjeta;

    // Campos efectivo
    private JTextField txtMontoEntregado;

    // Campos transferencia
    private JTextField txtBanco, txtReferencia;

    public PagoFrame(Tienda tienda, ClienteFrame clienteFrame) {
        this.tienda = tienda;
        this.clienteFrame = clienteFrame;
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("Confirmar pago");
        setModal(true);
        setSize(480, 550);
        setLocationRelativeTo(clienteFrame);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 25, 25));

        // Resumen del carrito
        JPanel panelResumen = new JPanel(new GridLayout(0, 1, 0, 2));
        panelResumen.setBackground(new Color(35, 35, 35));
        panelResumen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(16, 16, 8, 16),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(212, 175, 55)),
                        "  Resumen del pedido", 0, 0,
                        new Font("Arial", Font.BOLD, 13),
                        new Color(212, 175, 55))));

        for (ItemCarrito item : tienda.getCarrito().getItems()) {
            JLabel lbl = new JLabel("  • " + item.toString());
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Arial", Font.PLAIN, 13));
            panelResumen.add(lbl);
        }
        JLabel lblTotal = new JLabel("  TOTAL: $" +
                String.format("%.0f", tienda.getCarrito().getTotal()));
        lblTotal.setForeground(new Color(212, 175, 55));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 15));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
        panelResumen.add(lblTotal);

        // Dirección
        JPanel panelDir = new JPanel(new BorderLayout());
        panelDir.setBackground(new Color(25, 25, 25));
        panelDir.setBorder(BorderFactory.createEmptyBorder(8, 16, 4, 16));
        JLabel lblDir = new JLabel("Dirección de entrega:");
        lblDir.setForeground(Color.WHITE);
        panelDir.add(lblDir, BorderLayout.NORTH);
        txtDireccion = new JTextField();
        if (tienda.getSesionActual() instanceof Cliente) {
            txtDireccion.setText(((Cliente) tienda.getSesionActual()).getDireccion());
        }
        estilizarCampo(txtDireccion);
        panelDir.add(txtDireccion, BorderLayout.CENTER);

        // Método de pago
        JPanel panelMetodo = new JPanel(new BorderLayout());
        panelMetodo.setBackground(new Color(25, 25, 25));
        panelMetodo.setBorder(BorderFactory.createEmptyBorder(8, 16, 4, 16));
        JLabel lblMetodo = new JLabel("Método de pago:");
        lblMetodo.setForeground(Color.WHITE);
        panelMetodo.add(lblMetodo, BorderLayout.NORTH);

        cmbMetodo = new JComboBox<>(new String[]{
                "Tarjeta de crédito", "Tarjeta de débito", "Efectivo", "Transferencia"});
        cmbMetodo.setBackground(new Color(45, 45, 45));
        cmbMetodo.setForeground(Color.WHITE);
        panelMetodo.add(cmbMetodo, BorderLayout.CENTER);

        // Panel dinámico según método
        panelDatosPago = new JPanel(new CardLayout());
        panelDatosPago.setBackground(new Color(25, 25, 25));
        panelDatosPago.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));

        // Tarjeta
        JPanel panelTarjeta = new JPanel(new GridLayout(0, 1, 0, 6));
        panelTarjeta.setBackground(new Color(25, 25, 25));
        panelTarjeta.add(crearLabel("Número (últimos 4 dígitos):"));
        txtNumTarjeta = crearTextField();
        panelTarjeta.add(txtNumTarjeta);
        panelTarjeta.add(crearLabel("Nombre en la tarjeta:"));
        txtNombreTitular = crearTextField();
        panelTarjeta.add(txtNombreTitular);
        cmbTipoTarjeta = new JComboBox<>(new String[]{"Crédito", "Débito"});
        cmbTipoTarjeta.setBackground(new Color(45, 45, 45));
        cmbTipoTarjeta.setForeground(Color.WHITE);
        panelTarjeta.add(cmbTipoTarjeta);

        // Efectivo
        JPanel panelEfectivo = new JPanel(new GridLayout(0, 1, 0, 6));
        panelEfectivo.setBackground(new Color(25, 25, 25));
        panelEfectivo.add(crearLabel("Monto entregado ($):"));
        txtMontoEntregado = crearTextField();
        panelEfectivo.add(txtMontoEntregado);

        // Transferencia
        JPanel panelTransferencia = new JPanel(new GridLayout(0, 1, 0, 6));
        panelTransferencia.setBackground(new Color(25, 25, 25));
        panelTransferencia.add(crearLabel("Banco:"));
        txtBanco = crearTextField();
        panelTransferencia.add(txtBanco);
        panelTransferencia.add(crearLabel("Número de referencia:"));
        txtReferencia = crearTextField();
        panelTransferencia.add(txtReferencia);

        panelDatosPago.add(panelTarjeta, "Tarjeta de crédito");
        panelDatosPago.add(panelTarjeta, "Tarjeta de débito");
        panelDatosPago.add(panelEfectivo, "Efectivo");
        panelDatosPago.add(panelTransferencia, "Transferencia");

        cmbMetodo.addActionListener(e -> {
            String seleccion = (String) cmbMetodo.getSelectedItem();
            ((CardLayout) panelDatosPago.getLayout()).show(panelDatosPago, seleccion);
        });

        // Botón confirmar
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.setBackground(new Color(25, 25, 25));
        panelBtn.setBorder(BorderFactory.createEmptyBorder(10, 0, 16, 0));
        JButton btnConfirmar = new JButton("✅ Confirmar pedido");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirmar.setBackground(new Color(40, 120, 60));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        btnConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelBtn.add(btnConfirmar);

        // Armar layout
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBackground(new Color(25, 25, 25));
        panelCentro.add(panelDir);
        panelCentro.add(panelMetodo);
        panelCentro.add(Box.createVerticalStrut(8));
        panelCentro.add(panelDatosPago);

        panel.add(panelResumen, BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelBtn, BorderLayout.SOUTH);
        add(panel);

        btnConfirmar.addActionListener(e -> confirmarPedido());
    }

    private void confirmarPedido() {
        String direccion = txtDireccion.getText().trim();
        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa la dirección de entrega.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String metodo = (String) cmbMetodo.getSelectedItem();
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        double total = tienda.getCarrito().getTotal();
        Pago pago;

        try {
            switch (metodo) {
                case "Tarjeta de crédito":
                case "Tarjeta de débito":
                    String num = txtNumTarjeta.getText().trim();
                    String titular = txtNombreTitular.getText().trim();
                    if (num.isEmpty() || titular.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Completa los datos de la tarjeta.",
                                "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    String tipo = (String) cmbTipoTarjeta.getSelectedItem();
                    pago = new PagoTarjeta(total, fecha, num, titular, tipo);
                    break;
                case "Efectivo":
                    String montoStr = txtMontoEntregado.getText().trim();
                    if (montoStr.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Ingresa el monto entregado.",
                                "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    double montoEnt = Double.parseDouble(montoStr);
                    if (montoEnt < total) {
                        JOptionPane.showMessageDialog(this, "El monto entregado es insuficiente.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    pago = new PagoEfectivo(total, fecha, montoEnt);
                    break;
                case "Transferencia":
                    String banco = txtBanco.getText().trim();
                    String ref = txtReferencia.getText().trim();
                    if (banco.isEmpty() || ref.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Completa los datos de transferencia.",
                                "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    pago = new PagoTransferencia(total, fecha, banco, ref);
                    break;
                default:
                    return;
            }

            Pedido pedido = tienda.confirmarPedido(pago, direccion);
            JOptionPane.showMessageDialog(this,
                    "🎉 ¡Pedido confirmado!\n\n" + pedido.getResumen(),
                    "Pedido exitoso", JOptionPane.INFORMATION_MESSAGE);
            clienteFrame.actualizarCarrito();
            clienteFrame.cargarProductos(tienda.getProductos());
            dispose();

        } catch (StockInsuficienteException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Stock insuficiente", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un monto válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(new Color(200, 200, 200));
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        return lbl;
    }

    private JTextField crearTextField() {
        JTextField campo = new JTextField();
        estilizarCampo(campo);
        return campo;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setPreferredSize(new Dimension(0, 32));
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        campo.setBackground(new Color(45, 45, 45));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
    }
}
