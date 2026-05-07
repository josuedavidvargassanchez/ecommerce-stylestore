package ui;

import datos.Tienda;
import excepciones.ProductoNoEncontradoException;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel de administración — gestión de productos, usuarios y pedidos.
 */
public class AdminFrame extends JFrame {

    private Tienda tienda;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTabbedPane tabbedPane;

    public AdminFrame(Tienda tienda) {
        this.tienda = tienda;
        inicializarUI();
        cargarProductos();
    }

    private void inicializarUI() {
        setTitle("StyleStore — Panel de Administración");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        // Barra superior
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBackground(new Color(18, 18, 18));
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JLabel titulo = new JLabel("⚙️ Panel Admin — " + tienda.getSesionActual().getNombre());
        titulo.setForeground(new Color(212, 175, 55));
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelTop.add(titulo, BorderLayout.WEST);
        JButton btnCerrar = new JButton("Cerrar sesión");
        btnCerrar.setBackground(new Color(80, 30, 30));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        panelTop.add(btnCerrar, BorderLayout.EAST);

        // Tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(25, 25, 25));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.addTab("📦 Productos", crearPanelProductos());
        tabbedPane.addTab("📋 Pedidos", crearPanelPedidos());
        tabbedPane.addTab("👥 Usuarios", crearPanelUsuarios());

        add(panelTop, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        btnCerrar.addActionListener(e -> {
            tienda.cerrarSesion();
            dispose();
            new LoginFrame(tienda).setVisible(true);
        });
    }

    // ─── TAB PRODUCTOS ──────────────────────────────────────────
    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(22, 22, 22));

        String[] cols = {"ID", "Tipo", "Nombre", "Marca", "Categoría", "Precio", "Stock", "Detalles"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaProductos = new JTable(modeloTabla);
        estilizarTabla(tablaProductos);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        panelBotones.setBackground(new Color(22, 22, 22));

        JButton btnNuevo = crearBtn("➕ Nuevo producto", new Color(40, 100, 60));
        JButton btnEditar = crearBtn("✏️ Editar", new Color(60, 80, 130));
        JButton btnEliminar = crearBtn("🗑️ Eliminar", new Color(100, 30, 30));
        JButton btnRefresh = crearBtn("🔄 Actualizar", new Color(60, 60, 60));

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefresh);

        panel.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        btnNuevo.addActionListener(e -> new ProductoFormFrame(tienda, null, this).setVisible(true));

        btnEditar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila < 0) { JOptionPane.showMessageDialog(this,
                    "Selecciona un producto.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
            String id = (String) modeloTabla.getValueAt(fila, 0);
            try {
                Producto p = tienda.buscarProductoPorId(id);
                new ProductoFormFrame(tienda, p, this).setVisible(true);
            } catch (ProductoNoEncontradoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila < 0) return;
            String id = (String) modeloTabla.getValueAt(fila, 0);
            String nombre = (String) modeloTabla.getValueAt(fila, 2);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar \"" + nombre + "\"?", "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    tienda.eliminarProducto(id);
                    cargarProductos();
                    JOptionPane.showMessageDialog(this, "Producto eliminado.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (ProductoNoEncontradoException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRefresh.addActionListener(e -> cargarProductos());
        return panel;
    }

    // ─── TAB PEDIDOS ────────────────────────────────────────────
    private JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(22, 22, 22));

        String[] cols = {"# Pedido", "Cliente", "Fecha", "Total", "Pago", "Estado"};
        DefaultTableModel modeloPedidos = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tablaPedidos = new JTable(modeloPedidos);
        estilizarTabla(tablaPedidos);

        JTextArea areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        areaDetalle.setBackground(new Color(35, 35, 35));
        areaDetalle.setForeground(Color.WHITE);
        areaDetalle.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaDetalle.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(tablaPedidos), new JScrollPane(areaDetalle));
        split.setDividerLocation(260);

        // Cargar pedidos
        for (Pedido p : tienda.getTodosPedidos()) {
            modeloPedidos.addRow(new Object[]{
                    p.getId(), p.getClienteNombre(), p.getFecha(),
                    "$" + String.format("%.0f", p.getTotal()),
                    p.getPago().getTipoPago(), p.getEstado()
            });
        }

        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaPedidos.getSelectedRow();
            ArrayList<Pedido> pedidos = tienda.getTodosPedidos();
            if (fila >= 0 && fila < pedidos.size()) {
                areaDetalle.setText(pedidos.get(fila).getResumen());
            }
        });

        // Cambiar estado
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        panelBotones.setBackground(new Color(22, 22, 22));
        JComboBox<Pedido.EstadoPedido> cmbEstado = new JComboBox<>(Pedido.EstadoPedido.values());
        cmbEstado.setBackground(new Color(45, 45, 45));
        cmbEstado.setForeground(Color.WHITE);
        JButton btnCambiarEstado = crearBtn("Cambiar estado", new Color(60, 80, 130));
        panelBotones.add(new JLabel("Estado:") {{
            setForeground(Color.WHITE);
        }});
        panelBotones.add(cmbEstado);
        panelBotones.add(btnCambiarEstado);

        btnCambiarEstado.addActionListener(e -> {
            int fila = tablaPedidos.getSelectedRow();
            if (fila < 0) return;
            ArrayList<Pedido> pedidos = tienda.getTodosPedidos();
            if (fila < pedidos.size()) {
                Pedido.EstadoPedido nuevoEstado = (Pedido.EstadoPedido) cmbEstado.getSelectedItem();
                try {
                    tienda.actualizarEstadoPedido(pedidos.get(fila).getId(), nuevoEstado);
                    modeloPedidos.setValueAt(nuevoEstado, fila, 5);
                    JOptionPane.showMessageDialog(this, "Estado actualizado.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (ProductoNoEncontradoException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(split, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        return panel;
    }

    // ─── TAB USUARIOS ───────────────────────────────────────────
    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(22, 22, 22));

        String[] cols = {"ID", "Nombre", "Email", "Rol"};
        DefaultTableModel modeloUsuarios = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tablaUsuarios = new JTable(modeloUsuarios);
        estilizarTabla(tablaUsuarios);

        for (Usuario u : tienda.getUsuarios()) {
            modeloUsuarios.addRow(new Object[]{
                    u.getId(), u.getNombre(), u.getEmail(), u.getRol()
            });
        }

        panel.add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);
        return panel;
    }

    // ─── UTILIDADES ─────────────────────────────────────────────
    public void cargarProductos() {
        modeloTabla.setRowCount(0);
        for (Producto p : tienda.getProductos()) {
            modeloTabla.addRow(new Object[]{
                    p.getId(), p.getTipoProducto(), p.getNombre(), p.getMarca(),
                    p.getCategoria(), String.format("$%.0f", p.getPrecio()),
                    p.getStock(), p.getDetallesEspecificos()
            });
        }
    }

    private void estilizarTabla(JTable tabla) {
        tabla.setBackground(new Color(30, 30, 30));
        tabla.setForeground(Color.WHITE);
        tabla.setSelectionBackground(new Color(212, 175, 55, 80));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(new Color(50, 50, 50));
        tabla.setRowHeight(26);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.getTableHeader().setBackground(new Color(40, 40, 40));
        tabla.getTableHeader().setForeground(new Color(212, 175, 55));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private JButton crearBtn(String texto, Color bg) {
        JButton btn = new JButton(texto);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(7, 14, 7, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
