package vista;

import javax.swing.*;
import java.awt.*;
import modelo.Sistema;
import modelo.usuarios.Usuario;
import controlador.ControladorVentanas;
import vista.gui.VentanaLogin;

public class MenuCliente extends JPanel {

    private Usuario usuario;
    private JLabel lblPedidos, lblTotalGastado, lblProductosDisponibles;

    public MenuCliente(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(255, 245, 245));

        JLabel lblTitulo = new JLabel("Panel del Cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(lblTitulo, BorderLayout.NORTH);

        var dash = new vista.gui.PanelDashboard();
        add(dash, BorderLayout.EAST);
        revalidate();
        repaint();


        // ======================================================
        // 🔹 Botones del cliente
        // ======================================================
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JButton btnCatalogo = new JButton("🛒 Ver Catálogo");
        JButton btnPedidos = new JButton("📦 Mis Pedidos");
        JButton btnReportes = new JButton("📊 Mis Reportes");
        JButton btnCerrar = new JButton("🚪 Cerrar Sesión");

        btnCatalogo.addActionListener(e -> ControladorVentanas.abrirVentanaProductos(usuario));
        btnPedidos.addActionListener(e -> ControladorVentanas.abrirVentanaVentas(usuario));
        btnReportes.addActionListener(e -> ControladorVentanas.abrirVentanaReportes(usuario));

        btnCerrar.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
            Sistema.inicializar();
            SwingUtilities.invokeLater(() -> new VentanaLogin().setVisible(true));
        });

        panelBotones.add(btnCatalogo);
        panelBotones.add(btnPedidos);
        panelBotones.add(btnReportes);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.CENTER);

        // ======================================================
        // 🔹 Estadísticas en tiempo real
        // ======================================================
        JPanel panelStats = new JPanel(new GridLayout(3, 1, 5, 5));
        panelStats.setBorder(BorderFactory.createTitledBorder("📈 Actividad del Cliente"));
        panelStats.setBackground(new Color(255, 235, 235));

        lblPedidos = new JLabel("Pedidos realizados: 0", SwingConstants.CENTER);
        lblTotalGastado = new JLabel("Total gastado: Q0.00", SwingConstants.CENTER);
        lblProductosDisponibles = new JLabel("Productos disponibles: 0", SwingConstants.CENTER);

        Font f = new Font("Segoe UI", Font.BOLD, 15);
        lblPedidos.setFont(f);
        lblTotalGastado.setFont(f);
        lblProductosDisponibles.setFont(f);

        panelStats.add(lblPedidos);
        panelStats.add(lblTotalGastado);
        panelStats.add(lblProductosDisponibles);
        add(panelStats, BorderLayout.SOUTH);

        iniciarHilos();
    }

    private void iniciarHilos() {
        // 📦 Pedidos cada 8s
        new javax.swing.Timer(8000, e -> {
            int pedidos = Sistema.getListaPedidos().getCantidad();
            lblPedidos.setText("Pedidos realizados: " + pedidos);
        }).start();

        // 💰 Total gastado cada 12s
        new javax.swing.Timer(12000, e -> {
            double total = 0;
            var pedidos = Sistema.getListaPedidos().getPedidos();
            for (int i = 0; i < Sistema.getListaPedidos().getCantidad(); i++) {
                if (pedidos[i].getCliente().getCodigo().equals(usuario.getCodigo()))
                    total += pedidos[i].getTotal();
            }
            lblTotalGastado.setText("Total gastado: Q" + String.format("%.2f", total));
        }).start();

        // 🛍️ Productos disponibles cada 15s
        new javax.swing.Timer(15000, e -> {
            int disponibles = Sistema.getListaProductos().getCantidad();
            lblProductosDisponibles.setText("Productos disponibles: " + disponibles);
        }).start();
    }
}



