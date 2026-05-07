package ui;

import datos.Tienda;
import excepciones.AutenticacionException;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private Tienda tienda;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegistrar;
    private JLabel lblTitulo;

    public LoginFrame(Tienda tienda) {
        this.tienda = tienda;
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("StyleStore — Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 380);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(18, 18, 18));

        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(18, 18, 18));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        lblTitulo = new JLabel("👗 StyleStore", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(212, 175, 55)); 
        panelHeader.add(lblTitulo, BorderLayout.CENTER);

        JLabel lblSubtitulo = new JLabel("Tu tienda de moda online", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(150, 150, 150));
        panelHeader.add(lblSubtitulo, BorderLayout.SOUTH);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(new Color(30, 30, 30));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 4, 6, 4);

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 13));
        panelForm.add(lblEmail, gbc);

        gbc.gridy = 1;
        txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(300, 36));
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 13));
        txtEmail.setBackground(new Color(45, 45, 45));
        txtEmail.setForeground(Color.WHITE);
        txtEmail.setCaretColor(Color.WHITE);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        panelForm.add(txtEmail, gbc);

        gbc.gridy = 2;
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("Arial", Font.PLAIN, 13));
        panelForm.add(lblPass, gbc);

        gbc.gridy = 3;
        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(300, 36));
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 13));
        txtPassword.setBackground(new Color(45, 45, 45));
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(Color.WHITE);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        panelForm.add(txtPassword, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(16, 4, 4, 4);
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setPreferredSize(new Dimension(300, 40));
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(212, 175, 55));
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setBorder(BorderFactory.createEmptyBorder());
        panelForm.add(btnLogin, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(4, 4, 4, 4);
        btnRegistrar = new JButton("¿No tienes cuenta? Regístrate");
        btnRegistrar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnRegistrar.setBackground(new Color(30, 30, 30));
        btnRegistrar.setForeground(new Color(150, 150, 255));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelForm.add(btnRegistrar, gbc);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelForm, BorderLayout.CENTER);
        add(panelPrincipal);

        btnLogin.addActionListener(e -> intentarLogin());
        txtPassword.addActionListener(e -> intentarLogin());

        btnRegistrar.addActionListener(e -> {
            dispose();
            new RegistroFrame(tienda).setVisible(true);
        });
    }

    private void intentarLogin() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor completa todos los campos.", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario usuario = tienda.iniciarSesion(email, password);
            dispose();
            if (usuario.getRol().equals("ADMINISTRADOR")) {
                new AdminFrame(tienda).setVisible(true);
            } else {
                new ClienteFrame(tienda).setVisible(true);
            }
        } catch (AutenticacionException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(), "Error de autenticación",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }
}
