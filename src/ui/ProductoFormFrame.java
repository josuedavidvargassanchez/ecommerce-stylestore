package ui;

import datos.Tienda;
import excepciones.ProductoNoEncontradoException;
import modelo.*;
import javax.swing.*;
import java.awt.*;

/**
 * Formulario para crear o editar un producto (Admin).
 */
public class ProductoFormFrame extends JDialog {

    private Tienda tienda;
    private Producto productoEditar;
    private AdminFrame adminFrame;

    private JComboBox<String> cmbTipo;
    private JTextField txtId, txtNombre, txtDescripcion, txtMarca,
            txtCategoria, txtPrecio, txtStock;
    private JTextField txtEspecifico1, txtEspecifico2, txtEspecifico3, txtEspecifico4;
    private JLabel lblEsp1, lblEsp2, lblEsp3, lblEsp4;

    public ProductoFormFrame(Tienda tienda, Producto productoEditar, AdminFrame adminFrame) {
        this.tienda = tienda;
        this.productoEditar = productoEditar;
        this.adminFrame = adminFrame;
        inicializarUI();
        if (productoEditar != null) rellenarDatos();
    }

    private void inicializarUI() {
        setTitle(productoEditar == null ? "Nuevo producto" : "Editar producto");
        setModal(true);
        setSize(480, 620);
        setLocationRelativeTo(adminFrame);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(28, 28, 28));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 2, 2, 2);

        // Tipo
        gbc.gridy = 0;
        panel.add(lbl("Tipo de producto:"), gbc);
        gbc.gridy = 1;
        cmbTipo = new JComboBox<>(new String[]{"ROPA", "ACCESORIO", "CALZADO"});
        cmbTipo.setBackground(new Color(45, 45, 45));
        cmbTipo.setForeground(Color.WHITE);
        panel.add(cmbTipo, gbc);

        // ID
        gbc.gridy = 2; panel.add(lbl("ID (ej: R001, A001, C001):"), gbc);
        gbc.gridy = 3; txtId = campo(); panel.add(txtId, gbc);
        if (productoEditar != null) txtId.setEnabled(false);

        // Campos comunes
        gbc.gridy = 4; panel.add(lbl("Nombre:"), gbc);
        gbc.gridy = 5; txtNombre = campo(); panel.add(txtNombre, gbc);
        gbc.gridy = 6; panel.add(lbl("Descripción:"), gbc);
        gbc.gridy = 7; txtDescripcion = campo(); panel.add(txtDescripcion, gbc);
        gbc.gridy = 8; panel.add(lbl("Marca:"), gbc);
        gbc.gridy = 9; txtMarca = campo(); panel.add(txtMarca, gbc);
        gbc.gridy = 10; panel.add(lbl("Categoría:"), gbc);
        gbc.gridy = 11; txtCategoria = campo(); panel.add(txtCategoria, gbc);
        gbc.gridy = 12; panel.add(lbl("Precio:"), gbc);
        gbc.gridy = 13; txtPrecio = campo(); panel.add(txtPrecio, gbc);
        gbc.gridy = 14; panel.add(lbl("Stock:"), gbc);
        gbc.gridy = 15; txtStock = campo(); panel.add(txtStock, gbc);

        // Campos específicos dinámicos
        lblEsp1 = lbl("Talla / Tipo:"); gbc.gridy = 16; panel.add(lblEsp1, gbc);
        gbc.gridy = 17; txtEspecifico1 = campo(); panel.add(txtEspecifico1, gbc);
        lblEsp2 = lbl("Color:"); gbc.gridy = 18; panel.add(lblEsp2, gbc);
        gbc.gridy = 19; txtEspecifico2 = campo(); panel.add(txtEspecifico2, gbc);
        lblEsp3 = lbl("Material:"); gbc.gridy = 20; panel.add(lblEsp3, gbc);
        gbc.gridy = 21; txtEspecifico3 = campo(); panel.add(txtEspecifico3, gbc);
        lblEsp4 = lbl("Género / Dimensiones:"); gbc.gridy = 22; panel.add(lblEsp4, gbc);
        gbc.gridy = 23; txtEspecifico4 = campo(); panel.add(txtEspecifico4, gbc);

        // Botón guardar
        gbc.gridy = 24; gbc.insets = new Insets(18, 2, 2, 2);
        JButton btnGuardar = new JButton(productoEditar == null ? "➕ Crear producto" : "💾 Guardar cambios");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setBackground(new Color(212, 175, 55));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new Dimension(0, 42));
        btnGuardar.setBorder(BorderFactory.createEmptyBorder());
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(btnGuardar, gbc);

        // Actualizar labels al cambiar tipo
        cmbTipo.addActionListener(e -> actualizarLabels());
        actualizarLabels();

        getContentPane().setBackground(new Color(28, 28, 28));
        add(new JScrollPane(panel));
        btnGuardar.addActionListener(e -> guardar());
    }

    private void actualizarLabels() {
        String tipo = (String) cmbTipo.getSelectedItem();
        if ("ROPA".equals(tipo)) {
            lblEsp1.setText("Talla (XS/S/M/L/XL):");
            lblEsp4.setText("Género (Hombre/Mujer/Unisex/Niño):");
        } else if ("ACCESORIO".equals(tipo)) {
            lblEsp1.setText("Tipo (Bolso/Gorra/etc.):");
            lblEsp4.setText("Dimensiones (ej: 30cm x 20cm):");
        } else {
            lblEsp1.setText("Numeración (36-45):");
            lblEsp4.setText("Género (Hombre/Mujer/Unisex/Niño):");
        }
    }

    private void rellenarDatos() {
        if (productoEditar instanceof Ropa) cmbTipo.setSelectedItem("ROPA");
        else if (productoEditar instanceof Accesorio) cmbTipo.setSelectedItem("ACCESORIO");
        else cmbTipo.setSelectedItem("CALZADO");
        actualizarLabels();

        txtId.setText(productoEditar.getId());
        txtNombre.setText(productoEditar.getNombre());
        txtDescripcion.setText(productoEditar.getDescripcion());
        txtMarca.setText(productoEditar.getMarca());
        txtCategoria.setText(productoEditar.getCategoria());
        txtPrecio.setText(String.valueOf(productoEditar.getPrecio()));
        txtStock.setText(String.valueOf(productoEditar.getStock()));

        if (productoEditar instanceof Ropa r) {
            txtEspecifico1.setText(r.getTalla());
            txtEspecifico2.setText(r.getColor());
            txtEspecifico3.setText(r.getMaterial());
            txtEspecifico4.setText(r.getGenero());
        } else if (productoEditar instanceof Accesorio a) {
            txtEspecifico1.setText(a.getTipo());
            txtEspecifico2.setText(a.getColor());
            txtEspecifico3.setText(a.getMaterial());
            txtEspecifico4.setText(a.getDimensiones());
        } else if (productoEditar instanceof Calzado c) {
            txtEspecifico1.setText(String.valueOf(c.getNumeracion()));
            txtEspecifico2.setText(c.getColor());
            txtEspecifico3.setText(c.getMaterial());
            txtEspecifico4.setText(c.getGenero());
        }
    }

    private void guardar() {
        try {
            String tipo = (String) cmbTipo.getSelectedItem();
            String id = txtId.getText().trim();
            String nombre = txtNombre.getText().trim();
            String desc = txtDescripcion.getText().trim();
            String marca = txtMarca.getText().trim();
            String cat = txtCategoria.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());
            String e1 = txtEspecifico1.getText().trim();
            String e2 = txtEspecifico2.getText().trim();
            String e3 = txtEspecifico3.getText().trim();
            String e4 = txtEspecifico4.getText().trim();

            if (id.isEmpty() || nombre.isEmpty() || marca.isEmpty() ||
                    e1.isEmpty() || e2.isEmpty() || e3.isEmpty() || e4.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.",
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Producto nuevo;
            if ("ROPA".equals(tipo)) {
                nuevo = new Ropa(id, nombre, desc, precio, stock, cat, marca, e1, e2, e3, e4);
            } else if ("ACCESORIO".equals(tipo)) {
                nuevo = new Accesorio(id, nombre, desc, precio, stock, cat, marca, e1, e2, e3, e4);
            } else {
                nuevo = new Calzado(id, nombre, desc, precio, stock, cat, marca,
                        Integer.parseInt(e1), e2, e3, e4);
            }

            if (productoEditar == null) {
                tienda.agregarProducto(nuevo);
                JOptionPane.showMessageDialog(this, "✅ Producto creado exitosamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                tienda.actualizarProducto(nuevo);
                JOptionPane.showMessageDialog(this, "✅ Producto actualizado.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            adminFrame.cargarProductos();
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio, stock y numeración deben ser números.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (ProductoNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel lbl(String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(new Color(200, 200, 200));
        l.setFont(new Font("Arial", Font.PLAIN, 13));
        return l;
    }

    private JTextField campo() {
        JTextField t = new JTextField();
        t.setPreferredSize(new Dimension(0, 32));
        t.setFont(new Font("Arial", Font.PLAIN, 13));
        t.setBackground(new Color(45, 45, 45));
        t.setForeground(Color.WHITE);
        t.setCaretColor(Color.WHITE);
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        return t;
    }
}
