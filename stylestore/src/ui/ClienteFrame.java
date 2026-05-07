package ui;

import datos.Tienda;
import excepciones.StockInsuficienteException;
import modelo.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ClienteFrame extends JFrame {

    private Tienda tienda;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTable tablaCarrito;
    private DefaultTableModel modeloCarrito;
    private JLabel lblTotal;
    private JLabel lblBienvenida;
    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltro;

    public ClienteFrame(Tienda tienda) {
        this.tienda = tienda;
        inicializarUI();
        cargarProductos(tienda.getProductos());
    }

    private void inicializarUI() {
        setTitle("StyleStore — Tienda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBackground(new Color(18, 18, 18));
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblBienvenida = new JLabel("👗 StyleStore | Hola, " +
                tienda.getSesionActual().getNombre());
        lblBienvenida.setForeground(new Color(212, 175, 55));
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        panelTop.add(lblBienvenida, BorderLayout.WEST);

        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setBackground(new Color(80, 30, 30));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        btnCerrarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelTop.add(btnCerrarSesion, BorderLayout.EAST);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        panelBusqueda.setBackground(new Color(25, 25, 25));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JLabel lblBuscar = new JLabel("🔍 Buscar:");
        lblBuscar.setForeground(Color.WHITE);
        panelBusqueda.add(lblBuscar);

        txtBuscar = new JTextField(20);
        txtBuscar.setBackground(new Color(45, 45, 45));
        txtBuscar.setForeground(Color.WHITE);
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        panelBusqueda.add(txtBuscar);

        JLabel lblFiltro = new JLabel("Tipo:");
        lblFiltro.setForeground(Color.WHITE);
        panelBusqueda.add(lblFiltro);

        cmbFiltro = new JComboBox<>(new String[]{"Todos", "ROPA", "ACCESORIO", "CALZADO"});
        cmbFiltro.setBackground(new Color(45, 45, 45));
        cmbFiltro.setForeground(Color.WHITE);
        panelBusqueda.add(cmbFiltro);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(212, 175, 55));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        panelBusqueda.add(btnBuscar);

        JButton btnTodos = new JButton("Ver todos");
        btnTodos.setBackground(new Color(50, 50, 50));
        btnTodos.setForeground(Color.WHITE);
        btnTodos.setFocusPainted(false);
        btnTodos.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        panelBusqueda.add(btnTodos);

        JButton btnHistorial = new JButton("📦 Mis pedidos");
        btnHistorial.setBackground(new Color(40, 60, 100));
        btnHistorial.setForeground(Color.WHITE);
        btnHistorial.setFocusPainted(false);
        btnHistorial.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        panelBusqueda.add(btnHistorial);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(660);
        splitPane.setDividerSize(4);

        String[] colsProductos = {"ID", "Tipo", "Nombre", "Marca", "Precio", "Stock", "Detalles"};
        modeloTabla = new DefaultTableModel(colsProductos, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaProductos = new JTable(modeloTabla);
        estilizarTabla(tablaProductos);
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(55);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(70);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(160);
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(80);
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(80);
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(50);
        tablaProductos.getColumnModel().getColumn(6).setPreferredWidth(160);

        JPanel panelIzq = new JPanel(new BorderLayout());
        panelIzq.setBackground(new Color(22, 22, 22));
        JLabel lblCatalogo = new JLabel("  🛍️ Catálogo de productos");
        lblCatalogo.setForeground(Color.WHITE);
        lblCatalogo.setFont(new Font("Arial", Font.BOLD, 14));
        lblCatalogo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panelIzq.add(lblCatalogo, BorderLayout.NORTH);
        panelIzq.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        JPanel panelBtnAgregar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panelBtnAgregar.setBackground(new Color(22, 22, 22));

        JLabel lblCant = new JLabel("Cantidad:");
        lblCant.setForeground(Color.WHITE);
        panelBtnAgregar.add(lblCant);

        JSpinner spinCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        spinCantidad.setPreferredSize(new Dimension(60, 30));
        panelBtnAgregar.add(spinCantidad);

        JButton btnAgregar = new JButton("➕ Agregar al carrito");
        btnAgregar.setBackground(new Color(212, 175, 55));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 13));
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelBtnAgregar.add(btnAgregar);

        panelIzq.add(panelBtnAgregar, BorderLayout.SOUTH);

        JPanel panelDer = new JPanel(new BorderLayout());
        panelDer.setBackground(new Color(22, 22, 22));

        JLabel lblCarrito = new JLabel("  🛒 Mi carrito");
        lblCarrito.setForeground(Color.WHITE);
        lblCarrito.setFont(new Font("Arial", Font.BOLD, 14));
        lblCarrito.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panelDer.add(lblCarrito, BorderLayout.NORTH);

        String[] colsCarrito = {"Producto", "Precio unit.", "Cant.", "Subtotal"};
        modeloCarrito = new DefaultTableModel(colsCarrito, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaCarrito = new JTable(modeloCarrito);
        estilizarTabla(tablaCarrito);
        panelDer.add(new JScrollPane(tablaCarrito), BorderLayout.CENTER);

        JPanel panelCarritoBottom = new JPanel(new BorderLayout());
        panelCarritoBottom.setBackground(new Color(22, 22, 22));
        panelCarritoBottom.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setForeground(new Color(212, 175, 55));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        panelCarritoBottom.add(lblTotal, BorderLayout.WEST);

        JPanel panelBotonesCarrito = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelBotonesCarrito.setBackground(new Color(22, 22, 22));

        JButton btnEliminar = new JButton("❌ Quitar");
        btnEliminar.setBackground(new Color(80, 30, 30));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        panelBotonesCarrito.add(btnEliminar);

        JButton btnPagar = new JButton("💳 Pagar");
        btnPagar.setBackground(new Color(40, 120, 60));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setFont(new Font("Arial", Font.BOLD, 13));
        btnPagar.setFocusPainted(false);
        btnPagar.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btnPagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelBotonesCarrito.add(btnPagar);

        panelCarritoBottom.add(panelBotonesCarrito, BorderLayout.EAST);
        panelDer.add(panelCarritoBottom, BorderLayout.SOUTH);

        splitPane.setLeftComponent(panelIzq);
        splitPane.setRightComponent(panelDer);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelTop, BorderLayout.NORTH);
        panelNorte.add(panelBusqueda, BorderLayout.SOUTH);

        add(panelNorte, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> realizarBusqueda());
        txtBuscar.addActionListener(e -> realizarBusqueda());
        btnTodos.addActionListener(e -> {
            txtBuscar.setText("");
            cmbFiltro.setSelectedIndex(0);
            cargarProductos(tienda.getProductos());
        });

        btnAgregar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            String idProducto = (String) modeloTabla.getValueAt(fila, 0);
            try {
                Producto p = tienda.buscarProductoPorId(idProducto);
                int cantidad = (int) spinCantidad.getValue();
                tienda.agregarAlCarrito(p, cantidad);
                actualizarCarrito();
                JOptionPane.showMessageDialog(this,
                        "✅ \"" + p.getNombre() + "\" agregado al carrito.",
                        "Carrito", JOptionPane.INFORMATION_MESSAGE);
            } catch (StockInsuficienteException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Stock insuficiente", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaCarrito.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un ítem del carrito.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nombreProducto = (String) modeloCarrito.getValueAt(fila, 0);
            for (ItemCarrito item : tienda.getCarrito().getItems()) {
                if (item.getProducto().getNombre().equals(nombreProducto)) {
                    tienda.quitarDelCarrito(item.getProducto().getId());
                    break;
                }
            }
            actualizarCarrito();
        });

        btnPagar.addActionListener(e -> {
            if (tienda.getCarrito().estaVacio()) {
                JOptionPane.showMessageDialog(this, "El carrito está vacío.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            new PagoFrame(tienda, this).setVisible(true);
        });

        btnHistorial.addActionListener(e ->
                new HistorialFrame(tienda).setVisible(true));

        btnCerrarSesion.addActionListener(e -> {
            tienda.cerrarSesion();
            dispose();
            new LoginFrame(tienda).setVisible(true);
        });
    }

    private void realizarBusqueda() {
        String texto = txtBuscar.getText().trim();
        String filtro = (String) cmbFiltro.getSelectedItem();
        ArrayList<Producto> resultado;

        if (!texto.isEmpty()) {
            resultado = tienda.buscarProductos(texto);
        } else {
            resultado = tienda.getProductos();
        }

        if (!"Todos".equals(filtro)) {
            ArrayList<Producto> filtrados = new ArrayList<>();
            for (Producto p : resultado) {
                if (p.getTipoProducto().equals(filtro)) filtrados.add(p);
            }
            resultado = filtrados;
        }
        cargarProductos(resultado);
    }

    public void cargarProductos(ArrayList<Producto> lista) {
        modeloTabla.setRowCount(0);
        for (Producto p : lista) {
            modeloTabla.addRow(new Object[]{
                    p.getId(), p.getTipoProducto(), p.getNombre(),
                    p.getMarca(), String.format("$%.0f", p.getPrecio()),
                    p.getStock(), p.getDetallesEspecificos()
            });
        }
    }

    public void actualizarCarrito() {
        modeloCarrito.setRowCount(0);
        for (ItemCarrito item : tienda.getCarrito().getItems()) {
            modeloCarrito.addRow(new Object[]{
                    item.getProducto().getNombre(),
                    String.format("$%.0f", item.getProducto().getPrecio()),
                    item.getCantidad(),
                    String.format("$%.0f", item.getSubtotal())
            });
        }
        lblTotal.setText("Total: $" + String.format("%.0f", tienda.getCarrito().getTotal()));
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
}
