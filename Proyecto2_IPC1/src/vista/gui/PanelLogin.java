package vista.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import controlador.ControladorAutenticacion;
import modelo.Sistema;
import modelo.usuarios.Usuario;

public class PanelLogin extends JPanel {

    private JTextField txtCodigo;
    private JPasswordField txtPassword;
    private JButton btnIniciar;
    private JLabel lblMensaje;
    private ControladorAutenticacion controlador;

    public PanelLogin() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245));

        controlador = new ControladorAutenticacion(Sistema.getListaUsuarios());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Código:"), gbc);

        gbc.gridx = 1;
        txtCodigo = new JTextField(15);
        add(txtCodigo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        add(txtPassword, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnIniciar = new JButton("Iniciar sesión");
        btnIniciar.setBackground(new Color(0, 120, 215));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIniciar.addActionListener(this::iniciarSesion);
        add(btnIniciar, gbc);

        gbc.gridy++;
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        add(lblMensaje, gbc);
    }

    private void iniciarSesion(ActionEvent e) {
        String codigo = txtCodigo.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (codigo.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("⚠️ Debe ingresar ambos campos.");
            return;
        }

        Usuario usuario = controlador.iniciarSesion(codigo, password);

        if (usuario != null) {
            lblMensaje.setForeground(new Color(0, 128, 0));
            lblMensaje.setText("✅ Bienvenido " + usuario.getNombre());

            // Cierra la ventana actual
            SwingUtilities.getWindowAncestor(this).dispose();

            // Abre la ventana principal según el usuario autenticado
            SwingUtilities.invokeLater(() -> {
                new VentanaPrincipal(usuario).setVisible(true);
            });

        } else {
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText("❌ Credenciales incorrectas.");
        }
    }
}




