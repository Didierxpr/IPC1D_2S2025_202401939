package vista;

import controlador.ControladorSistema;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana de Login del sistema
 */
public class VentanaLogin extends JFrame {

    private ControladorSistema controladorSistema;

    // Componentes
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JButton btnIngresar;
    private JButton btnSalir;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;

    public VentanaLogin(ControladorSistema controladorSistema) {
        this.controladorSistema = controladorSistema;
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Panel principal con color de fondo
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(new Color(41, 128, 185));

        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(41, 128, 185));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        lblTitulo = new JLabel("SANCARLISTA SHOP");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSubtitulo = new JLabel("Sistema de Gestión de Tienda");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelTitulo.add(lblSubtitulo);

        // Panel central con formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Label Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelFormulario.add(lblUsuario, gbc);

        // Campo Usuario
        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridy = 1;
        panelFormulario.add(txtUsuario, gbc);

        // Label Contraseña
        JLabel lblContrasenia = new JLabel("Contraseña:");
        lblContrasenia.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 2;
        panelFormulario.add(lblContrasenia, gbc);

        // Campo Contraseña
        txtContrasenia = new JPasswordField(20);
        txtContrasenia.setFont(new Font("Arial", Font.PLAIN, 14));
        txtContrasenia.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridy = 3;
        panelFormulario.add(txtContrasenia, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setBackground(Color.WHITE);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIngresar.setBackground(new Color(46, 204, 113));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setBackground(new Color(231, 76, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnIngresar);
        panelBotones.add(btnSalir);

        gbc.gridy = 4;
        gbc.insets = new Insets(20, 10, 10, 10);
        panelFormulario.add(panelBotones, gbc);

        // Agregar paneles al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Agregar al frame
        add(panelPrincipal);

        // Event Listeners
        configurarEventos();
    }

    private void configurarEventos() {
        // Botón Ingresar
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        // Enter en campo de contraseña
        txtContrasenia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        // Botón Salir
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(
                        VentanaLogin.this,
                        "¿Está seguro de salir del sistema?",
                        "Confirmar Salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    controladorSistema.cerrarSistema();
                    System.exit(0);
                }
            }
        });
    }

    private void realizarLogin() {
        String usuario = txtUsuario.getText().trim();
        String contrasenia = new String(txtContrasenia.getPassword());

        if (usuario.isEmpty() || contrasenia.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor, complete todos los campos",
                    "Campos Vacíos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Intentar login
        Usuario usuarioAutenticado = controladorSistema.login(usuario, contrasenia);

        if (usuarioAutenticado != null) {
            // Login exitoso
            this.dispose(); // Cerrar ventana de login

            // Abrir ventana según tipo de usuario
            String tipoUsuario = usuarioAutenticado.getTipoUsuario();

            switch (tipoUsuario) {
                case "ADMINISTRADOR":
                    new VentanaAdministrador(controladorSistema, usuarioAutenticado).setVisible(true);
                    break;
                case "VENDEDOR":
                    new VentanaVendedor(controladorSistema, usuarioAutenticado).setVisible(true);
                    break;
                case "CLIENTE":
                    new VentanaCliente(controladorSistema, usuarioAutenticado).setVisible(true);
                    break;
            }
        } else {
            // Login fallido
            JOptionPane.showMessageDialog(
                    this,
                    "Usuario o contraseña incorrectos",
                    "Error de Autenticación",
                    JOptionPane.ERROR_MESSAGE
            );
            txtContrasenia.setText("");
            txtUsuario.requestFocus();
        }
    }

    private void configurarVentana() {
        setTitle("Sancarlista Shop - Login");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(false);
    }
}
