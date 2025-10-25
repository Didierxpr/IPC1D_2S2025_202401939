package vista;

import javax.swing.*;
import java.awt.*;
import modelo.Sistema;
import modelo.usuarios.Usuario;
import controlador.ControladorVentanas;
import vista.gui.VentanaLogin;

public class MenuVendedor extends JPanel {

    private Usuario usuario;
    private JLabel lblVentas, lblProductos, lblStock;

    public MenuVendedor(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 255, 245));

        JLabel lblTitulo = new JLabel("Panel del Vendedor", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(lblTitulo, BorderLayout.NORTH);

        var dash = new vista.gui.PanelDashboard();
        add(dash, BorderLayout.EAST);
        revalidate();
        repaint();


        // ======================================================
        // 🔹 Botones del vendedor
        // ======================================================
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JButton btnVentas = new JButton("💰 Registrar Venta");
        JButton btnProductos = new JButton("📦 Ver Productos");
        JButton btnReportes = new JButton("📊 Ver Reportes");
        JButton btnCerrar = new JButton("🚪 Cerrar Sesión");

        btnVentas.addActionListener(e -> ControladorVentanas.abrirVentanaVentas(usuario));
        btnProductos.addActionListener(e -> ControladorVentanas.abrirVentanaProductos(usuario));
        btnReportes.addActionListener(e -> ControladorVentanas.abrirVentanaReportes(usuario));

        btnCerrar.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
            Sistema.inicializar();
            SwingUtilities.invokeLater(() -> new VentanaLogin().setVisible(true));
        });

        panelBotones.add(btnVentas);
        panelBotones.add(btnProductos);
        panelBotones.add(btnReportes);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.CENTER);

        // ======================================================
        // 🔹 Panel de estadísticas en vivo
        // ======================================================
        JPanel panelStats = new JPanel(new GridLayout(3, 1, 5, 5));
        panelStats.setBorder(BorderFactory.createTitledBorder("📈 Estadísticas del Vendedor"));
        panelStats.setBackground(new Color(230, 255, 230));

        lblVentas = new JLabel("Ventas realizadas: 0", SwingConstants.CENTER);
        lblProductos = new JLabel("Productos gestionados: 0", SwingConstants.CENTER);
        lblStock = new JLabel("Stock total disponible: 0", SwingConstants.CENTER);

        Font f = new Font("Segoe UI", Font.BOLD, 15);
        lblVentas.setFont(f);
        lblProductos.setFont(f);
        lblStock.setFont(f);

        panelStats.add(lblVentas);
        panelStats.add(lblProductos);
        panelStats.add(lblStock);
        add(panelStats, BorderLayout.SOUTH);

        iniciarHilos();
    }

    private void iniciarHilos() {
        // 💰 Ventas cada 10s
        new javax.swing.Timer(10000, e -> {
            int ventas = Sistema.getListaPedidos().getCantidad();
            lblVentas.setText("Ventas realizadas: " + ventas);
        }).start();

        // 📦 Productos cada 12s
        new javax.swing.Timer(12000, e -> {
            int productos = Sistema.getListaProductos().getCantidad();
            lblProductos.setText("Productos gestionados: " + productos);
        }).start();

        // 📦 Stock total cada 15s
        new javax.swing.Timer(15000, e -> {
            var lista = Sistema.getListaProductos();
            int total = 0;
            for (int i = 0; i < lista.getCantidad(); i++) {
                if (lista.getProducto(i) != null)
                    total += lista.getProducto(i).getStock();
            }
            lblStock.setText("Stock total disponible: " + total);
        }).start();
    }
}



