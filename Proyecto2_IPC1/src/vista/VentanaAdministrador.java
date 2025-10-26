package vista;

import controlador.ControladorSistema;
import modelo.Usuario;
import modelo.Administrador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana principal del Administrador
 */
public class VentanaAdministrador extends JFrame {

    private ControladorSistema controladorSistema;
    private Usuario usuarioActual;

    // Componentes
    private JLabel lblBienvenida;
    private JButton btnGestionVendedores;
    private JButton btnGestionProductos;
    private JButton btnGenerarReportes;
    private JButton btnVerEstadisticas;
    private JButton btnVerBitacora;
    private JButton btnDatosEstudiante;
    private JButton btnCerrarSesion;

    public VentanaAdministrador(ControladorSistema controladorSistema, Usuario usuarioActual) {
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
        panelHeader.setBackground(new Color(41, 128, 185));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("PANEL DE ADMINISTRADOR");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        lblBienvenida = new JLabel("Bienvenido/a: " + usuarioActual.getNombre());
        lblBienvenida.setFont(new Font("Arial", Font.PLAIN, 14));
        lblBienvenida.setForeground(Color.WHITE);

        panelHeader.add(lblTitulo, BorderLayout.NORTH);
        panelHeader.add(lblBienvenida, BorderLayout.SOUTH);

        // Panel central con botones
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(4, 2, 20, 20));
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Crear botones con iconos y colores
        btnGestionVendedores = crearBoton("👥 Gestión de Vendedores", new Color(52, 152, 219));
        btnGestionProductos = crearBoton("📦 Gestión de Productos", new Color(46, 204, 113));
        btnGenerarReportes = crearBoton("📊 Generar Reportes", new Color(155, 89, 182));
        btnVerEstadisticas = crearBoton("📈 Ver Estadísticas", new Color(241, 196, 15));
        btnVerBitacora = crearBoton("📋 Ver Bitácora", new Color(52, 73, 94));
        btnDatosEstudiante = crearBoton("👨‍🎓 Datos de Estudiante", new Color(26, 188, 156));
        btnCerrarSesion = crearBoton("🚪 Cerrar Sesión", new Color(231, 76, 60));

        // Agregar botones al panel
        panelCentral.add(btnGestionVendedores);
        panelCentral.add(btnGestionProductos);
        panelCentral.add(btnGenerarReportes);
        panelCentral.add(btnVerEstadisticas);
        panelCentral.add(btnVerBitacora);
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
        btnGestionVendedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaGestionVendedores(controladorSistema, usuarioActual).setVisible(true);
            }
        });

        btnGestionProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaGestionProductos(controladorSistema, usuarioActual).setVisible(true);
            }
        });

        btnGenerarReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaReportes(controladorSistema).setVisible(true);
            }
        });

        btnVerEstadisticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarEstadisticas();
            }
        });

        btnVerBitacora.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaBitacora(controladorSistema).setVisible(true);
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
        String estadisticas = controladorSistema.obtenerEstadisticasGenerales() + "\n\n" +
                controladorSistema.getControladorAdministrador().obtenerEstadisticas();

        JTextArea textArea = new JTextArea(estadisticas);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Estadísticas del Sistema",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarDatosEstudiante() {
        JOptionPane.showMessageDialog(
                this,
                "════════════════════════════════\n" +
                        "    DATOS DEL ESTUDIANTE\n" +
                        "════════════════════════════════\n\n" +
                        "Nombre: [TU NOMBRE COMPLETO]\n" +
                        "Carné: [TU CARNÉ]\n" +
                        "Sección: A\n" +
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
        setTitle("Sancarlista Shop - Administrador");
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
