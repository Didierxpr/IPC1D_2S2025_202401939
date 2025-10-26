package vista;

import controlador.ControladorSistema;
import utilidades.GeneradorPDF;
import utilidades.SistemaArchivos;
import modelo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para generación de reportes PDF
 */
public class VentanaReportes extends JFrame {

    private ControladorSistema controladorSistema;

    // Componentes
    private JButton btnProductosMasVendidos;
    private JButton btnProductosMenosVendidos;
    private JButton btnInventario;
    private JButton btnVentasPorVendedor;
    private JButton btnClientesActivos;
    private JButton btnFinanciero;
    private JButton btnProductosPorCaducar;
    private JButton btnCerrar;

    public VentanaReportes(ControladorSistema controladorSistema) {
        this.controladorSistema = controladorSistema;

        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(155, 89, 182));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("📊 GENERACIÓN DE REPORTES PDF");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        // Panel central con botones de reportes
        JPanel panelReportes = new JPanel();
        panelReportes.setLayout(new GridLayout(4, 2, 15, 15));
        panelReportes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnProductosMasVendidos = crearBotonReporte("📈 Productos Más Vendidos", new Color(46, 204, 113));
        btnProductosMenosVendidos = crearBotonReporte("📉 Productos Menos Vendidos", new Color(231, 76, 60));
        btnInventario = crearBotonReporte("📦 Reporte de Inventario", new Color(52, 152, 219));
        btnVentasPorVendedor = crearBotonReporte("👥 Ventas por Vendedor", new Color(241, 196, 15));
        btnClientesActivos = crearBotonReporte("👤 Clientes Activos", new Color(26, 188, 156));
        btnFinanciero = crearBotonReporte("💰 Reporte Financiero", new Color(155, 89, 182));
        btnProductosPorCaducar = crearBotonReporte("⏰ Productos por Caducar", new Color(230, 126, 34));
        btnCerrar = crearBotonReporte("❌ Cerrar", new Color(149, 165, 166));

        panelReportes.add(btnProductosMasVendidos);
        panelReportes.add(btnProductosMenosVendidos);
        panelReportes.add(btnInventario);
        panelReportes.add(btnVentasPorVendedor);
        panelReportes.add(btnClientesActivos);
        panelReportes.add(btnFinanciero);
        panelReportes.add(btnProductosPorCaducar);
        panelReportes.add(btnCerrar);

        // Agregar componentes
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelReportes, BorderLayout.CENTER);

        add(panelPrincipal);

        // Configurar eventos
        configurarEventos();
    }

    private JButton crearBotonReporte(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(200, 60));

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
        btnProductosMasVendidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte("ProductosMasVendidos", 1);
            }
        });

        btnProductosMenosVendidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte("ProductosMenosVendidos", 2);
            }
        });

        btnInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte("Inventario", 3);
            }
        });

        btnVentasPorVendedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte("VentasPorVendedor", 4);
            }
        });

        btnClientesActivos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte("ClientesActivos", 5);
            }
        });

        btnFinanciero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte("Financiero", 6);
            }
        });

        btnProductosPorCaducar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte("ProductosPorCaducar", 7);
            }
        });

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void generarReporte(String nombreReporte, int tipo) {
        // Mostrar diálogo de espera
        JDialog dialogoEspera = new JDialog(this, "Generando Reporte", true);
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblMensaje = new JLabel("Generando reporte PDF, por favor espere...");
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 14));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        panel.add(lblMensaje, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);

        dialogoEspera.add(panel);
        dialogoEspera.setSize(400, 120);
        dialogoEspera.setLocationRelativeTo(this);
        dialogoEspera.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        // Generar reporte en hilo separado
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            String rutaArchivo = "";

            @Override
            protected Boolean doInBackground() throws Exception {
                Producto[] productos = SistemaArchivos.cargarProductos();
                Vendedor[] vendedores = SistemaArchivos.cargarVendedores();
                Cliente[] clientes = SistemaArchivos.cargarClientes();
                Pedido[] pedidos = SistemaArchivos.cargarPedidos();

                rutaArchivo = GeneradorPDF.generarNombreArchivo(nombreReporte);

                boolean exito = false;

                switch (tipo) {
                    case 1:
                        exito = GeneradorPDF.generarReporteProductosMasVendidos(rutaArchivo, productos);
                        break;
                    case 2:
                        exito = GeneradorPDF.generarReporteProductosMenosVendidos(rutaArchivo, productos);
                        break;
                    case 3:
                        exito = GeneradorPDF.generarReporteInventario(rutaArchivo, productos);
                        break;
                    case 4:
                        exito = GeneradorPDF.generarReporteVentasPorVendedor(rutaArchivo, vendedores, pedidos);
                        break;
                    case 5:
                        exito = GeneradorPDF.generarReporteClientesActivos(rutaArchivo, clientes);
                        break;
                    case 6:
                        exito = GeneradorPDF.generarReporteFinanciero(rutaArchivo, productos);
                        break;
                    case 7:
                        exito = GeneradorPDF.generarReporteProductosPorCaducar(rutaArchivo, productos);
                        break;
                }

                return exito;
            }

            @Override
            protected void done() {
                dialogoEspera.dispose();

                try {
                    boolean exito = get();

                    if (exito) {
                        int respuesta = JOptionPane.showConfirmDialog(
                                VentanaReportes.this,
                                "✓ Reporte generado exitosamente\n\n" +
                                        "Ubicación: " + rutaArchivo + "\n\n" +
                                        "¿Desea abrir el archivo?",
                                "Reporte Generado",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        if (respuesta == JOptionPane.YES_OPTION) {
                            try {
                                Desktop.getDesktop().open(new java.io.File(rutaArchivo));
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(
                                        VentanaReportes.this,
                                        "No se pudo abrir el archivo automáticamente.\n" +
                                                "Búsquelo en: " + rutaArchivo,
                                        "Información",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                            }
                        }

                        controladorSistema.getGestorHilos().notificarActividad("Reporte PDF generado: " + nombreReporte);
                    } else {
                        JOptionPane.showMessageDialog(
                                VentanaReportes.this,
                                "Error al generar el reporte",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            VentanaReportes.this,
                            "Error: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
        dialogoEspera.setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Generación de Reportes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
