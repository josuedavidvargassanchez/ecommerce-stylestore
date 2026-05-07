package ui;

import datos.Tienda;
import excepciones.AutenticacionException;
import javax.swing.*;
import java.awt.*;

/**
 * Pantalla de registro de nuevo cliente.
 */
public class RegistroFrame extends JFrame {

    private Tienda tienda;
    private JTextField txtNombre, txtEmail, txtDireccion, txtTelefono;
    private JPasswordField txtPassword, txtConfirmar;

    public RegistroFrame(Tienda tienda) {
        this.tienda = tienda;
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("StyleStore — Registro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 4, 5, 4);
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Crear cuenta", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(212, 175, 55));
        gbc.gridy = 0; gbc.insets = new Insets(0, 4, 20, 4);
        panel.add(titulo, gbc);

        String[] labels = {"Nombre completo:", "Email:", "Contraseña:", "Confirmar contraseña:", "Dirección:", "Teléfono:"};
        gbc.insets = new Insets(5, 4, 2, 4);

        // Nombre
        gbc.gridy = 1;
        panel.add(crearLabel("Nombre completo:"), gbc);
        gbc.gridy = 2;
        txtNombre = crearTextField();
        panel.add(txtNombre, gbc);

        // Email
        gbc.gridy = 3;
        panel.add(crearLabel("Email:"), gbc);
        gbc.gridy = 4;
        txtEmail = crearTextField();
        panel.add(txtEmail, gbc);

        // Password
        gbc.gridy = 5;
        panel.add(crearLabel("Contraseña:"), gbc);
        gbc.gridy = 6;
        txtPassword = new JPasswordField();
        estilizarCampo(txtPassword);
        panel.add(txtPassword, gbc);

        // Confirmar password
        gbc.gridy = 7;
        panel.add(crearLabel("Confirmar contraseña:"), gbc);
        gbc.gridy = 8;
        txtConfirmar = new JPasswordField();
        estilizarCampo(txtConfirmar);
        panel.add(txtConfirmar, gbc);

        // Dirección
        gbc.gridy = 9;
        panel.add(crearLabel("Dirección:"), gbc);
        gbc.gridy = 10;
        txtDireccion = crearTextField();
        panel.add(txtDireccion, gbc);

        // Teléfono
        gbc.gridy = 11;
        panel.add(crearLabel("Teléfono:"), gbc);
        gbc.gridy = 12;
        txtTelefono = crearTextField();
        panel.add(txtTelefono, gbc);

        // Botón registrar
        gbc.gridy = 13;
        gbc.insets = new Insets(20, 4, 6, 4);
        JButton btnRegistrar = new JButton("Crear cuenta");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setBackground(new Color(212, 175, 55));
        btnRegistrar.setForeground(Color.BLACK);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setPreferredSize(new Dimension(0, 40));
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder());
        btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(btnRegistrar, gbc);

        // Volver al login
        gbc.gridy = 14;
        gbc.insets = new Insets(4, 4, 4, 4);
        JButton btnVolver = new JButton("Ya tengo cuenta — Iniciar sesión");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 12));
        btnVolver.setBackground(new Color(30, 30, 30));
        btnVolver.setForeground(new Color(150, 150, 255));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(btnVolver, gbc);

        getContentPane().setBackground(new Color(30, 30, 30));
        add(new JScrollPane(panel));

        btnRegistrar.addActionListener(e -> registrar());
        btnVolver.addActionListener(e -> {
            dispose();
            new LoginFrame(tienda).setVisible(true);
        });
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
        campo.setPreferredSize(new Dimension(0, 34));
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        campo.setBackground(new Color(45, 45, 45));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
    }

    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String pass = new String(txtPassword.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty() ||
                direccion.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!pass.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (pass.length() < 6) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            tienda.registrarCliente(nombre, email, pass, direccion, telefono);
            JOptionPane.showMessageDialog(this,
                    "¡Cuenta creada exitosamente! Ya puedes iniciar sesión.",
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginFrame(tienda).setVisible(true);
        } catch (AutenticacionException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error en registro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
