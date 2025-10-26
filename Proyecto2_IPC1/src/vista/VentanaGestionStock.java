package vista;

import controlador.ControladorSistema;
import controlador.ControladorStock;
import modelo.Usuario;
import modelo.Stock;
import modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para gestión de stock (Vendedor)
 */
public class VentanaGestionStock extends JFrame {

    private ControladorSistema controladorSistema;
    private Usuario usuarioActual;
    private ControladorStock controladorStock;

    // Componentes
    private JTable tablaStock;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregarStock;
    private JButton btnCargarCSV;
    private JButton btnExportarHistorial;
    private JButton btnRefrescar;
    private JButton btnCerrar;

    public VentanaGestionStock(ControladorSistema controladorSistema, Usuario usuarioActual) {
        this.controladorSistema = controladorSistema;
        this.usuarioActual = usuarioActual;
        this.controladorStock = controladorSistema.getControladorStock();

        inicializarComponentes();
        cargarDatos();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(52, 152, 219));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("📦 GESTIÓN DE STOCK");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        // Tabla de historial de stock
        String[] columnas = {"Fecha", "Hora", "Producto", "Cantidad", "Stock Anterior", "Stock Actual"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaStock = new JTable(modeloTabla);
        tablaStock.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaStock.setRowHeight(25);
        tablaStock.getTableHeader().setReorderingAllowed(false);
        tablaStock.getTableHeader().setBackground(new Color(52, 152, 219));
        tablaStock.getTableHeader().setForeground(Color.WHITE);
        tablaStock.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaStock);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnAgregarStock = crearBoton("➕ Agregar Stock", new Color(46, 204, 113));
        btnCargarCSV = crearBoton("📁 Cargar CSV", new Color(155, 89, 182));
        btnExportarHistorial = crearBoton("📄 Exportar Historial", new Color(52, 152, 219));
        btnRefrescar = crearBoton("🔄 Refrescar", new Color(241, 196, 15));
        btnCerrar = crearBoton("❌ Cerrar", new Color(149, 165, 166));

        panelBotones.add(btnAgregarStock);
        panelBotones.add(btnCargarCSV);
        panelBotones.add(btnExportarHistorial);
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnCerrar);

        // Agregar componentes
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Configurar eventos
        configurarEventos();
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(160, 35));

        return boton;
    }

    private void configurarEventos() {
        btnAgregarStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarStock();
            }
        });

        btnCargarCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDesdeCSV();
            }
        });

        btnExportarHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarHistorial();
            }
        });

        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatos();
            }
        });

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);

        Stock[] movimientos = controladorStock.obtenerMovimientosPorVendedor(usuarioActual.getCodigo());

        // Mostrar los últimos 50 movimientos
        int inicio = Math.max(0, movimientos.length - 50);

        for (int i = inicio; i < movimientos.length; i++) {
            Stock s = movimientos[i];
            Object[] fila = {
                    s.getFechaIngreso(),
                    s.getHoraIngreso(),
                    s.getNombreProducto(),
                    s.getCantidad(),
                    s.getStockAnterior(),
                    s.getStockActual()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void agregarStock() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        JTextField txtCodigoProducto = new JTextField();
        JTextField txtCantidad = new JTextField();

        panel.add(new JLabel("Código del Producto:"));
        panel.add(txtCodigoProducto);
        panel.add(new JLabel("Cantidad a Agregar:"));
        panel.add(txtCantidad);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Agregar Stock",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String codigoProducto = txtCodigoProducto.getText().trim();
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());

                if (codigoProducto.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "El código del producto es obligatorio",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "La cantidad debe ser mayor que cero",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                if (controladorStock.agregarStock(codigoProducto, cantidad)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Stock agregado exitosamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    cargarDatos();
                    controladorSistema.getGestorHilos().notificarActividad("Stock agregado: " + codigoProducto);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error al agregar stock. Verifique el código del producto.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "La cantidad debe ser un número válido",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void cargarDesdeCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();

            if (controladorStock.cargarStockCSV(rutaArchivo)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Stock cargado exitosamente desde CSV",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
                controladorSistema.getGestorHilos().notificarActividad("Stock cargado desde CSV");
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al cargar archivo CSV",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void exportarHistorial() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Historial como CSV");
        fileChooser.setSelectedFile(new java.io.File("historial_stock_" +
                new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date()) + ".csv"));

        int resultado = fileChooser.showSaveDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();

            if (!rutaArchivo.toLowerCase().endsWith(".csv")) {
                rutaArchivo += ".csv";
            }

            if (controladorStock.exportarHistorialCSV(rutaArchivo)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Historial exportado exitosamente a:\n" + rutaArchivo,
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al exportar historial",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void configurarVentana() {
        setTitle("Gestión de Stock");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}