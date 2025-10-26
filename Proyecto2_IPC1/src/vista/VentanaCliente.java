package vista;

import controlador.ControladorSistema;
import modelo.Usuario;
import modelo.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana principal del Cliente
 */
public class VentanaCliente extends JFrame {

    private ControladorSistema controladorSistema;
    private Usuario usuarioActual;

    // Componentes
    private JLabel lblBienvenida;
    private JButton btnCatalogo;
    private JButton btnCarrito;
    private JButton btnHistorial;
    private JButton btnDatosEstudiante;
    private JButton btnCerrarSesion;

    public VentanaCliente(ControladorSistema controladorSistema, Usuario usuarioActual) {
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
        panelHeader.setBackground(new Color(26, 188, 156));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("PANEL DE CLIENTE");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        lblBienvenida = new JLabel("Bienvenido/a: " + usuarioActual.getNombre());
        lblBienvenida.setFont(new Font("Arial", Font.PLAIN, 14));
        lblBienvenida.setForeground(Color.WHITE);

        // Mostrar información del cliente
        if (usuarioActual instanceof Cliente) {
            Cliente cliente = (Cliente) usuarioActual;
            JLabel lblInfo = new JLabel(String.format(
                    "Compras Realizadas: %d | Total Gastado: $%.2f | Clasificación: %s",
                    cliente.getComprasRealizadas(),
                    cliente.getTotalGastado(),
                    cliente.getClasificacion()
            ));
            lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
            lblInfo.setForeground(Color.WHITE);

            JPanel panelInfo = new JPanel(new GridLayout(2, 1, 5, 5));
            panelInfo.setBackground(new Color(26, 188, 156));
            panelInfo.add(lblBienvenida);
            panelInfo.add(lblInfo);

            panelHeader.add(lblTitulo, BorderLayout.NORTH);
            panelHeader.add(panelInfo, BorderLayout.SOUTH);
        } else {
            panelHeader.add(lblTitulo, BorderLayout.NORTH);
            panelHeader.add(lblBienvenida, BorderLayout.SOUTH);
        }

        // Panel central con botones
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(3, 2, 20, 20));
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Crear botones grandes
        btnCatalogo = crearBoton("🛍️ Catálogo de Productos", new Color(52, 152, 219));
        btnCarrito = crearBoton("🛒 Mi Carrito de Compras", new Color(46, 204, 113));
        btnHistorial = crearBoton("📋 Historial de Compras", new Color(155, 89, 182));
        btnDatosEstudiante = crearBoton("👨‍🎓 Datos de Estudiante", new Color(52, 73, 94));
        btnCerrarSesion = crearBoton("🚪 Cerrar Sesión", new Color(231, 76, 60));

        // Agregar botones al panel
        panelCentral.add(btnCatalogo);
        panelCentral.add(btnCarrito);
        panelCentral.add(btnHistorial);
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
        boton.setPreferredSize(new Dimension(280, 100));

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
        btnCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new VentanaCatalogoCliente(controladorSistema, usuarioActual).setVisible(true);
            }
        });

        btnCarrito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  new VentanaCarritoCompras(controladorSistema, usuarioActual).setVisible(true);
            }
        });

        btnHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  new VentanaHistorialCompras(controladorSistema, usuarioActual).setVisible(true);
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
        setTitle("Sancarlista Shop - Cliente");
        setSize(750, 550);
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
