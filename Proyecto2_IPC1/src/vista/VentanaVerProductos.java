package vista;

import controlador.ControladorSistema;
import modelo.Producto;
import utilidades.SistemaArchivos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para ver todos los productos (Vendedor)
 */
public class VentanaVerProductos extends JFrame {

    private ControladorSistema controladorSistema;

    // Componentes
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnVerDetalle;
    private JButton btnRefrescar;
    private JButton btnCerrar;
    private JComboBox<String> cmbFiltroCategoria;

    public VentanaVerProductos(ControladorSistema controladorSistema) {
        this.controladorSistema = controladorSistema;

        inicializarComponentes();
        cargarDatos();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior con título y filtro
        JPanel panelSuperior = new JPanel(new BorderLayout());

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(155, 89, 182));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("🛍️ PRODUCTOS DEL SISTEMA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        // Panel de filtro
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltro.add(new JLabel("Filtrar por categoría:"));
        cmbFiltroCategoria = new JComboBox<>(new String[]{"TODAS", "TECNOLOGIA", "ALIMENTO", "GENERAL"});
        panelFiltro.add(cmbFiltroCategoria);

        JButton btnFiltrar = new JButton("🔍 Filtrar");
        btnFiltrar.setBackground(new Color(52, 152, 219));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFocusPainted(false);
        panelFiltro.add(btnFiltrar);

        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarFiltro();
            }
        });

        panelSuperior.add(panelTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelFiltro, BorderLayout.CENTER);

        // Tabla de productos
        String[] columnas = {"Código", "Nombre", "Categoría", "Precio", "Stock", "Vendidos", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.setRowHeight(25);
        tablaProductos.getTableHeader().setReorderingAllowed(false);
        tablaProductos.getTableHeader().setBackground(new Color(155, 89, 182));
        tablaProductos.getTableHeader().setForeground(Color.WHITE);
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnVerDetalle = crearBoton("👁️ Ver Detalle", new Color(52, 152, 219));
        btnRefrescar = crearBoton("🔄 Refrescar", new Color(46, 204, 113));
        btnCerrar = crearBoton("❌ Cerrar", new Color(149, 165, 166));

        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnCerrar);

        // Agregar componentes
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
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
        boton.setPreferredSize(new Dimension(140, 35));

        return boton;
    }

    private void configurarEventos() {
        btnVerDetalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verDetalle();
            }
        });

        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmbFiltroCategoria.setSelectedIndex(0);
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

        Producto[] productos = SistemaArchivos.cargarProductos();

        if (productos != null) {
            for (Producto p : productos) {
                Object[] fila = {
                        p.getCodigo(),
                        p.getNombre(),
                        p.getCategoria(),
                        String.format("$%.2f", p.getPrecio()),
                        p.getStockDisponible(),
                        p.getCantidadVendida(),
                        p.obtenerEstadoStock()
                };
                modeloTabla.addRow(fila);
            }
        }
    }

    private void aplicarFiltro() {
        modeloTabla.setRowCount(0);

        String categoriaSeleccionada = (String) cmbFiltroCategoria.getSelectedItem();
        Producto[] productos = SistemaArchivos.cargarProductos();

        if (productos != null) {
            for (Producto p : productos) {
                if (categoriaSeleccionada.equals("TODAS") || p.getCategoria().equals(categoriaSeleccionada)) {
                    Object[] fila = {
                            p.getCodigo(),
                            p.getNombre(),
                            p.getCategoria(),
                            String.format("$%.2f", p.getPrecio()),
                            p.getStockDisponible(),
                            p.getCantidadVendida(),
                            p.obtenerEstadoStock()
                    };
                    modeloTabla.addRow(fila);
                }
            }
        }
    }

    private void verDetalle() {
        int filaSeleccionada = tablaProductos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un producto de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String codigo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        Producto[] productos = SistemaArchivos.cargarProductos();

        Producto producto = null;
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                producto = p;
                break;
            }
        }

        if (producto != null) {
            JTextArea textArea = new JTextArea(producto.obtenerDetalleCompleto());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(450, 350));

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Detalle del Producto: " + codigo,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void configurarVentana() {
        setTitle("Ver Productos");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
