package vista.gui;

import javax.swing.*;
import java.awt.*;
import modelo.Sistema;

public class PanelEstadisticas extends JPanel {

    private JLabel lblUsuariosActivos;
    private JLabel lblPedidosPendientes;
    private JLabel lblTotalVentas;

    public PanelEstadisticas() {
        setLayout(new GridLayout(3, 1, 10, 10));
        setBorder(BorderFactory.createTitledBorder("📈 Estadísticas del Sistema"));
        setBackground(new Color(245, 245, 255));

        lblUsuariosActivos = new JLabel("Usuarios activos: 0", SwingConstants.CENTER);
        lblPedidosPendientes = new JLabel("Pedidos registrados: 0", SwingConstants.CENTER);
        lblTotalVentas = new JLabel("Total de ventas: Q0.00", SwingConstants.CENTER);

        Font f = new Font("Segoe UI", Font.BOLD, 16);
        lblUsuariosActivos.setFont(f);
        lblPedidosPendientes.setFont(f);
        lblTotalVentas.setFont(f);

        add(lblUsuariosActivos);
        add(lblPedidosPendientes);
        add(lblTotalVentas);

        iniciarHilos();
    }

    private void iniciarHilos() {
        // 🧍‍♂️ Usuarios activos (cada 10 segundos)
        new Timer(10000, e -> {
            int activos = Sistema.getListaUsuarios().getCantidad();
            lblUsuariosActivos.setText("Usuarios activos: " + activos);
        }).start();

        // 📦 Pedidos (cada 8 segundos)
        new Timer(8000, e -> {
            int pedidos = Sistema.getListaPedidos().getCantidad();
            lblPedidosPendientes.setText("Pedidos registrados: " + pedidos);
        }).start();

        // 💰 Ventas totales (cada 15 segundos)
        new Timer(15000, e -> {
            double total = 0;
            var lista = Sistema.getListaPedidos();
            for (int i = 0; i < lista.getCantidad(); i++) {
                total += lista.getPedidos()[i].getTotal();
            }
            lblTotalVentas.setText("Total de ventas: Q" + String.format("%.2f", total));
        }).start();
    }
}

