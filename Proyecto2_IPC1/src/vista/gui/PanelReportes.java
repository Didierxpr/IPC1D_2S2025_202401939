package vista.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

import controlador.ControladorReportes;


import modelo.Sistema;
import modelo.pedidos.Pedido;

public class PanelReportes extends JPanel {

    private final DefaultTableModel modeloTabla;
    private final JTable tabla;
    private final JLabel lblTotalVentas;
    private final JLabel lblTopProducto;
    private final ControladorReportes controlador;


    public PanelReportes() {
        this.controlador = new ControladorReportes();

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // =====================================================
        // 🔹 PANEL SUPERIOR: BOTONES DE ACCIÓN
        // =====================================================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JButton btnTop = new JButton("Ver Top 3 Productos");
        JButton btnExportar = new JButton("Exportar a CSV");
        JButton btnExportarHTML = new JButton("Exportar a HTML");
        JButton btnReporteFinal = new JButton("Generar Reporte Final");

        btnTop.addActionListener(this::mostrarTopProductos);
        btnExportar.addActionListener(this::exportarCSV);
        btnExportarHTML.addActionListener(this::exportarHTML);
        btnReporteFinal.addActionListener(this::generarReporteFinal);

        panelBotones.add(btnTop);
        panelBotones.add(btnExportar);
        panelBotones.add(btnExportarHTML);
        panelBotones.add(btnReporteFinal);

        add(panelBotones, BorderLayout.NORTH);

        // =====================================================
        // 🔹 PANEL CENTRAL: TABLA DE PEDIDOS
        // =====================================================
        modeloTabla = new DefaultTableModel(
                new Object[]{"Código Pedido", "Cliente", "Producto", "Cantidad", "Total (Q)", "Fecha"}, 0
        );
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Ventas"));
        add(scroll, BorderLayout.CENTER);

        // =====================================================
        // 🔹 PANEL INFERIOR: ESTADÍSTICAS
        // =====================================================
        JPanel panelStats = new JPanel(new GridLayout(2, 1));
        lblTotalVentas = new JLabel("💰 Total de ventas: Q0.00", SwingConstants.CENTER);
        lblTopProducto = new JLabel("🏆 Producto más vendido: N/A", SwingConstants.CENTER);
        panelStats.add(lblTotalVentas);
        panelStats.add(lblTopProducto);
        add(panelStats, BorderLayout.SOUTH);

        cargarPedidos();
    }

    // =====================================================
    // 🔸 Cargar todos los pedidos en tabla
    // =====================================================
    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        double totalGeneral = 0;

        Pedido[] pedidos = Sistema.getListaPedidos().getPedidos();
        for (int i = 0; i < Sistema.getListaPedidos().getCantidad(); i++) {
            Pedido p = pedidos[i];
            modeloTabla.addRow(new Object[]{
                    p.getCodigo(),
                    p.getCliente().getNombre(),
                    p.getProducto().getNombre(),
                    p.getCantidad(),
                    p.getTotal(),
                    p.getFecha()
            });
            totalGeneral += p.getTotal();
        }

        lblTotalVentas.setText("💰 Total de ventas: Q" + String.format("%.2f", totalGeneral));
    }

    // =====================================================
    // 🔸 Mostrar Top 3 productos más vendidos
    // =====================================================
    private void mostrarTopProductos(ActionEvent e) {
        controlador.generarReporteGeneral();
        JOptionPane.showMessageDialog(this,
                "🏆 Reporte generado correctamente.\nConsulta la carpeta /reportes/ o revisa la consola.",
                "Reporte generado",
                JOptionPane.INFORMATION_MESSAGE);
    }


    // =====================================================
    // 🔸 Exportar pedidos a CSV
    // =====================================================
    private void exportarCSV(ActionEvent e) {
        controlador.exportarCSV();
        JOptionPane.showMessageDialog(this,
                "✅ Reporte CSV generado en carpeta /reportes.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportarHTML(ActionEvent e) {
        controlador.generarReporteGeneral();
        JOptionPane.showMessageDialog(this,
                "✅ Reporte HTML generado en carpeta /reportes.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    private void generarReporteFinal(ActionEvent e) {
        try {
            controlador.generarReporteGeneral();
            JOptionPane.showMessageDialog(this,
                    "✅ Reporte final generado en: data/reporte_final.html",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error al generar reporte final: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}


