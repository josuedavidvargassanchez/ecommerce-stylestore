package ui;

import datos.Tienda;
import modelo.ItemCarrito;
import modelo.Pedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class HistorialFrame extends JFrame {

    private Tienda tienda;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JTextArea areaDetalle;

    public HistorialFrame(Tienda tienda) {
        this.tienda = tienda;
        inicializarUI();
        cargarPedidos();
    }

    private void inicializarUI() {
        setTitle("Mis pedidos — StyleStore");
        setSize(750, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(22, 22, 22));

        JLabel titulo = new JLabel("  📦 Historial de pedidos");
        titulo.setForeground(new Color(212, 175, 55));
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        panel.add(titulo, BorderLayout.NORTH);

        String[] cols = {"# Pedido", "Fecha", "Estado", "Total", "Pago"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setBackground(new Color(30, 30, 30));
        tablaPedidos.setForeground(Color.WHITE);
        tablaPedidos.setSelectionBackground(new Color(212, 175, 55, 80));
        tablaPedidos.setSelectionForeground(Color.WHITE);
        tablaPedidos.setGridColor(new Color(50, 50, 50));
        tablaPedidos.setRowHeight(26);
        tablaPedidos.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaPedidos.getTableHeader().setBackground(new Color(40, 40, 40));
        tablaPedidos.getTableHeader().setForeground(new Color(212, 175, 55));
        tablaPedidos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        areaDetalle.setBackground(new Color(35, 35, 35));
        areaDetalle.setForeground(Color.WHITE);
        areaDetalle.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaDetalle.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(tablaPedidos), new JScrollPane(areaDetalle));
        split.setDividerLocation(250);
        split.setDividerSize(4);
        split.setBackground(new Color(22, 22, 22));

        panel.add(split, BorderLayout.CENTER);
        add(panel);

        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaPedidos.getSelectedRow();
            if (fila >= 0) {
                mostrarDetalle(fila);
            }
        });
    }

    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        ArrayList<Pedido> pedidos = tienda.getPedidosCliente();
        if (pedidos.isEmpty()) {
            areaDetalle.setText("Aún no tienes pedidos realizados.");
        }
        for (Pedido p : pedidos) {
            modeloTabla.addRow(new Object[]{
                    p.getId(), p.getFecha(), p.getEstado(),
                    "$" + String.format("%.0f", p.getTotal()),
                    p.getPago().getTipoPago()
            });
        }
    }

    private void mostrarDetalle(int fila) {
        ArrayList<Pedido> pedidos = tienda.getPedidosCliente();
        if (fila < pedidos.size()) {
            areaDetalle.setText(pedidos.get(fila).getResumen());
        }
    }
}
