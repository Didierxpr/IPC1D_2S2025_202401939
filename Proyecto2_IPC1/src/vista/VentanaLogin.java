package vista;

import controlador.ControladorSistema;
import modelo.Usuario;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;

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
    private JButton btnModo; // 🔹 Botón modo oscuro
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private boolean modoOscuro = false; // 🔹 Estado del tema

    public VentanaLogin(ControladorSistema controladorSistema) {
        this.controladorSistema = controladorSistema;
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(UIManager.getColor("Panel.background"));

        // Panel superior (Título)
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(41, 128, 185)); // azul institucional
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

        // Panel formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(UIManager.getColor("Panel.background"));
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
        gbc.gridy = 3;
        panelFormulario.add(txtContrasenia, gbc);

        // Panel botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setBackground(UIManager.getColor("Panel.background"));

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIngresar.setFocusPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        estilizarBotonVerde(btnIngresar);

        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        estilizarBotonRojo(btnSalir);

        // 🔹 Botón de modo oscuro
        btnModo = new JButton("🌙 Modo oscuro");
        btnModo.setFont(new Font("Arial", Font.BOLD, 12));
        btnModo.setFocusPainted(false);
        btnModo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        estilizarBotonNeutro(btnModo);

        panelBotones.add(btnIngresar);
        panelBotones.add(btnSalir);
        panelBotones.add(btnModo);

        gbc.gridy = 4;
        gbc.insets = new Insets(20, 10, 10, 10);
        panelFormulario.add(panelBotones, gbc);

        // Añadir paneles al principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        add(panelPrincipal);
        configurarEventos();
    }

    private void configurarEventos() {
        // Botón Ingresar
        btnIngresar.addActionListener(e -> realizarLogin());

        // Enter en campo de contraseña
        txtContrasenia.addActionListener(e -> realizarLogin());

        // Botón Salir
        btnSalir.addActionListener(e -> {
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
        });

        // 🔹 Evento modo oscuro / claro
        btnModo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!modoOscuro) {
                        FlatDarkLaf.setup();
                        btnModo.setText("☀️ Modo claro");
                    } else {
                        FlatLightLaf.setup();
                        btnModo.setText("🌙 Modo oscuro");
                    }
                    modoOscuro = !modoOscuro;

                    // 🔹 Actualiza TODAS las ventanas abiertas
                    for (Window window : Window.getWindows()) {
                        SwingUtilities.updateComponentTreeUI(window);
                    }

                    // 🔹 Reaplica estilos dinámicos
                    estilizarBotonVerde(btnIngresar);
                    estilizarBotonRojo(btnSalir);
                    estilizarBotonNeutro(btnModo);

                } catch (Exception ex) {
                    ex.printStackTrace();
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

        Usuario usuarioAutenticado = controladorSistema.login(usuario, contrasenia);

        if (usuarioAutenticado != null) {
            this.dispose();

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
        setLocationRelativeTo(null);
        setResizable(false);
    }

    // 🔹 Métodos de estilo dinámico
    private void estilizarBotonVerde(JButton boton) {
        if (modoOscuro) {
            boton.setBackground(new Color(46, 204, 113));
            boton.setForeground(Color.BLACK);
        } else {
            boton.setBackground(new Color(10, 239, 108));
            boton.setForeground(Color.WHITE);
        }
        boton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
    }

    private void estilizarBotonRojo(JButton boton) {
        if (modoOscuro) {
            boton.setBackground(new Color(231, 76, 60));
            boton.setForeground(Color.BLACK);
        } else {
            boton.setBackground(new Color(227, 51, 33));
            boton.setForeground(Color.WHITE);
        }
        boton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
    }

    private void estilizarBotonNeutro(JButton boton) {
        if (modoOscuro) {
            boton.setBackground(new Color(149, 165, 166));
            boton.setForeground(Color.BLACK);
        } else {
            boton.setBackground(new Color(189, 195, 199));
            boton.setForeground(Color.WHITE);
        }
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
}

