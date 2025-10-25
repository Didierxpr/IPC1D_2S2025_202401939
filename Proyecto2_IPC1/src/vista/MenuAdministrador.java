package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import modelo.Sistema;
import modelo.usuarios.Usuario;
import controlador.ControladorVentanas;
import vista.gui.VentanaLogin;

public class MenuAdministrador extends JPanel {

    private Usuario usuario;
    private JLabel lblUsuariosActivos, lblPedidos, lblTotalVentas;

    public MenuAdministrador(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 255));

        JLabel lblTitulo = new JLabel("Panel del Administrador", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(lblTitulo, BorderLayout.NORTH);
        var dash = new vista.gui.PanelDashboard();
        add(dash, BorderLayout.EAST);
        revalidate();
        repaint();



        // ================================================================
        // 🔹 PANEL DE BOTONES PRINCIPALES
        // ================================================================
        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JButton btnUsuarios = new JButton("👥 Gestionar Usuarios");
        JButton btnProductos = new JButton("📦 Gestionar Productos");
        JButton btnBitacora = new JButton("🧾 Ver Bitácora");
        JButton btnReportes = new JButton("📊 Generar Reportes");
        JButton btnAcerca = new JButton("👨‍🎓 Acerca del Estudiante");
        JButton btnCerrar = new JButton("🚪 Cerrar Sesión");

        // Eventos
        btnUsuarios.addActionListener((ActionEvent e) -> ControladorVentanas.abrirVentanaUsuarios(usuario));
        btnProductos.addActionListener((ActionEvent e) -> ControladorVentanas.abrirVentanaProductos(usuario));
        btnBitacora.addActionListener((ActionEvent e) -> ControladorVentanas.abrirVentanaBitacora(usuario));
        btnReportes.addActionListener((ActionEvent e) -> ControladorVentanas.abrirVentanaReportes(usuario));
        btnAcerca.addActionListener((ActionEvent e) -> ControladorVentanas.abrirVentanaAcerca());

        btnCerrar.addActionListener((ActionEvent e) -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
            Sistema.inicializar();
            SwingUtilities.invokeLater(() -> new VentanaLogin().setVisible(true));
        });

        panelBotones.add(btnUsuarios);
        panelBotones.add(btnProductos);
        panelBotones.add(btnBitacora);
        panelBotones.add(btnReportes);
        panelBotones.add(btnAcerca);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.CENTER);

        // ================================================================
        // 🔹 PANEL DE ESTADÍSTICAS CON HILOS VISUALES
        // ================================================================
        JPanel panelStats = new JPanel(new GridLayout(3, 1, 5, 5));
        panelStats.setBorder(BorderFactory.createTitledBorder("📈 Estadísticas del Sistema"));
        panelStats.setBackground(new Color(235, 240, 255));

        lblUsuariosActivos = new JLabel("Usuarios activos: 0", SwingConstants.CENTER);
        lblPedidos = new JLabel("Pedidos registrados: 0", SwingConstants.CENTER);
        lblTotalVentas = new JLabel("Total de ventas: Q0.00", SwingConstants.CENTER);

        Font f = new Font("Segoe UI", Font.BOLD, 15);
        lblUsuariosActivos.setFont(f);
        lblPedidos.setFont(f);
        lblTotalVentas.setFont(f);

        panelStats.add(lblUsuariosActivos);
        panelStats.add(lblPedidos);
        panelStats.add(lblTotalVentas);

        add(panelStats, BorderLayout.SOUTH);

        iniciarActualizacionEnVivo();

        add(new JLabel("Bienvenido, " + usuario.getNombre(), SwingConstants.CENTER), BorderLayout.PAGE_START);
    }

    // ================================================================
    // 🔸 MÉTODO: ACTUALIZAR ESTADÍSTICAS AUTOMÁTICAMENTE CON HILOS
    // ================================================================
    private void iniciarActualizacionEnVivo() {
        // 🧍 Usuarios activos cada 10 segundos
        new javax.swing.Timer(10000, e -> {
            int activos = Sistema.getListaUsuarios().getCantidad();
            lblUsuariosActivos.setText("Usuarios activos: " + activos);
        }).start();

        // 📦 Pedidos registrados cada 8 segundos
        new javax.swing.Timer(8000, e -> {
            int pedidos = Sistema.getListaPedidos().getCantidad();
            lblPedidos.setText("Pedidos registrados: " + pedidos);
        }).start();

        // 💰 Total ventas cada 15 segundos
        new javax.swing.Timer(15000, e -> {
            double total = 0;
            var lista = Sistema.getListaPedidos();
            for (int i = 0; i < lista.getCantidad(); i++) {
                total += lista.getPedidos()[i].getTotal();
            }
            lblTotalVentas.setText("Total de ventas: Q" + String.format("%.2f", total));
        }).start();
    }
}



