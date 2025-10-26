package vista;

import controlador.ControladorSistema;
import modelo.Usuario;
import modelo.Vendedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana principal del Vendedor
 */
public class VentanaVendedor extends JFrame {

    private ControladorSistema controladorSistema;
    private Usuario usuarioActual;

    // Componentes
    private JLabel lblBienvenida;
    private JButton btnGestionStock;
    private JButton btnGestionClientes;
    private JButton btnGestionPedidos;
    private JButton btnVerProductos;
    private JButton btnEstadisticas;
    private JButton btnDatosEstudiante;
    private JButton btnCerrarSesion;

    public VentanaVendedor(ControladorSistema controladorSistema, Usuario usuarioActual) {
        this.controladorSistema = controladorSistema;
        this.usuarioActual = usuarioActual;

        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);

        // Panel superior (Header)
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new BorderLayout());
        panelHeader.setBackground(new Color(241, 196, 15));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("PANEL DE VENDEDOR");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        lblBienvenida = new JLabel("Bienvenido/a: " + usuarioActual.getNombre());
        lblBienvenida.setFont(new Font("Arial", Font.PLAIN, 14));
        lblBienvenida.setForeground(Color.WHITE);

        // Mostrar estadísticas del vendedor
        if (usuarioActual instanceof Vendedor) {
            Vendedor vendedor = (Vendedor) usuarioActual;
            JLabel lblStats = new JLabel(String.format(
                    "Ventas Confirmadas: %d | Clientes Registrados: %d",
                    vendedor.getVentasConfirmadas(),
                    vendedor.getClientesRegistrados()
            ));
            lblStats.setFont(new Font("Arial", Font.PLAIN, 12));
            lblStats.setForeground(Color.WHITE);

            JPanel panelInfo = new JPanel(new GridLayout(2, 1, 5, 5));
            panelInfo.setBackground(new Color(241, 196, 15));
            panelInfo.add(lblBienvenida);
            panelInfo.add(lblStats);

            panelHeader.add(lblTitulo, BorderLayout.NORTH);
            panelHeader.add(panelInfo, BorderLayout.SOUTH);
        } else {
            panelHeader.add(lblTitulo, BorderLayout.NORTH);
            panelHeader.add(lblBienvenida, BorderLayout.SOUTH);
        }

        // Panel central con botones
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(4, 2, 20, 20));
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Crear botones
        btnGestionStock = crearBoton("📦 Gestión de Stock", new Color(52, 152, 219));
        btnGestionClientes = crearBoton("👥 Gestión de Clientes", new Color(46, 204, 113));
        btnGestionPedidos = crearBoton("📋 Gestión de Pedidos", new Color(230, 126, 34));
        btnVerProductos = crearBoton("🛍️ Ver Productos", new Color(155, 89, 182));
        btnEstadisticas = crearBoton("📊 Mis Estadísticas", new Color(26, 188, 156));
        btnDatosEstudiante = crearBoton("👨‍🎓 Datos de Estudiante", new Color(52, 73, 94));
        btnCerrarSesion = crearBoton("🚪 Cerrar Sesión", new Color(231, 76, 60));

        // Agregar botones al panel
        panelCentral.add(btnGestionStock);
        panelCentral.add(btnGestionClientes);
        panelCentral.add(btnGestionPedidos);
        panelCentral.add(btnVerProductos);
        panelCentral.add(btnEstadisticas);
        panelCentral.add(btnDatosEstudiante);
        panelCentral.add(new JLabel()); // Espacio vacío
        panelCentral.add(btnCerrarSesion);

        // Agregar componentes al panel principal
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        add(panelPrincipal);

        // Configurar eventos
        configurarEventos();
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(250, 80));

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });

        return boton;
    }

    private void configurarEventos() {
        btnGestionStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaGestionStock(controladorSistema, usuarioActual).setVisible(true);
            }
        });

        btnGestionClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaGestionClientesVendedor(controladorSistema, usuarioActual).setVisible(true);
            }
        });

        btnGestionPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // new VentanaGestionPedidosVendedor(controladorSistema, usuarioActual).setVisible(true);
            }
        });

        btnVerProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // new VentanaVerProductos(controladorSistema).setVisible(true);
            }
        });

        btnEstadisticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarEstadisticas();
            }
        });

        btnDatosEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDatosEstudiante();
            }
        });

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
    }

    private void mostrarEstadisticas() {
        if (!(usuarioActual instanceof Vendedor)) return;

        Vendedor vendedor = (Vendedor) usuarioActual;

        String estadisticas = String.format(
                "════════════════════════════════\n" +
                        "   MIS ESTADÍSTICAS\n" +
                        "════════════════════════════════\n\n" +
                        "Ventas Confirmadas: %d\n" +
                        "Clientes Registrados: %d\n" +
                        "Comisión Total: $%.2f\n\n" +
                        "════════════════════════════════",
                vendedor.getVentasConfirmadas(),
                vendedor.getClientesRegistrados(),
                vendedor.getComisionTotal()
        );

        JOptionPane.showMessageDialog(
                this,
                estadisticas,
                "Mis Estadísticas",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarDatosEstudiante() {
        JOptionPane.showMessageDialog(
                this,
                "════════════════════════════════\n" +
                        "    DATOS DEL ESTUDIANTE\n" +
                        "════════════════════════════════\n\n" +
                        "Nombre: Carlos Didiere Cabrera Rodriguez\n" +
                        "Carné: 202401939\n" +
                        "Sección: D\n" +
                        "Curso: Introducción a la Programación\n" +
                        "         y Computación 1\n" +
                        "Proyecto: Sancarlista Shop v1.0\n\n" +
                        "════════════════════════════════",
                "Datos del Estudiante",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void cerrarSesion() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de cerrar sesión?",
                "Confirmar Cierre de Sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            controladorSistema.logout();
            this.dispose();
            new VentanaLogin(controladorSistema).setVisible(true);
        }
    }

    private void configurarVentana() {
        setTitle("Sancarlista Shop - Vendedor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // Manejar cierre de ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                cerrarSesion();
            }
        });
    }
}
