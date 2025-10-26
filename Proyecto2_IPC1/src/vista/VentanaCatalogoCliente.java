package vista;

import controlador.ControladorSistema;
import controlador.ControladorProducto;
import controlador.ControladorCarrito;
import modelo.Usuario;
import modelo.Cliente;
import modelo.Producto;
import modelo.ProductoTecnologia;
import modelo.ProductoAlimento;
import modelo.ProductoGeneral;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana de catálogo de productos para clientes
 */
public class VentanaCatalogoCliente extends JFrame {

    private ControladorSistema controladorSistema;
    private Usuario usuarioActual;
    private ControladorProducto controladorProducto;
    private ControladorCarrito controladorCarrito;

    // Componentes
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregarCarrito;
    private JButton btnVerDetalle;
    private JButton btnRefrescar;
    private JButton btnCerrar;
    private JComboBox<String> cmbFiltroCategoria;

    public VentanaCatalogoCliente(ControladorSistema controladorSistema, Usuario usuarioActual) {
        this.controladorSistema = controladorSistema;
        this.usuarioActual = usuarioActual;
        this.controladorProducto = controladorSistema.getControladorProducto();
        this.controladorCarrito = controladorSistema.getControladorCarrito();

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
        panelTitulo.setBackground(new Color(52, 152, 219));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("🛍️ CATÁLOGO DE PRODUCTOS");
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
        String[] columnas = {"Código", "Nombre", "Categoría", "Precio", "Stock Disponible"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.setRowHeight(30);
        tablaProductos.getTableHeader().setReorderingAllowed(false);
        tablaProductos.getTableHeader().setBackground(new Color(52, 152, 219));
        tablaProductos.getTableHeader().setForeground(Color.WHITE);
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnAgregarCarrito = crearBoton("🛒 Agregar al Carrito", new Color(46, 204, 113));
        btnVerDetalle = crearBoton("👁️ Ver Detalle", new Color(52, 152, 219));
        btnRefrescar = crearBoton("🔄 Refrescar", new Color(241, 196, 15));
        btnCerrar = crearBoton("❌ Cerrar", new Color(149, 165, 166));

        panelBotones.add(btnAgregarCarrito);
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
        boton.setPreferredSize(new Dimension(170, 35));

        return boton;
    }

    private void configurarEventos() {
        btnAgregarCarrito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarAlCarrito();
            }
        });

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

        Producto[] productos = controladorProducto.obtenerConStock();

        for (Producto p : productos) {
            Object[] fila = {
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCategoria(),
                    String.format("$%.2f", p.getPrecio()),
                    p.getStockDisponible()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void aplicarFiltro() {
        modeloTabla.setRowCount(0);

        String categoriaSeleccionada = (String) cmbFiltroCategoria.getSelectedItem();
        Producto[] productos;

        if (categoriaSeleccionada.equals("TODAS")) {
            productos = controladorProducto.obtenerConStock();
        } else {
            productos = controladorProducto.obtenerPorCategoria(categoriaSeleccionada);
        }

        for (Producto p : productos) {
            if (p.getStockDisponible() > 0) {
                Object[] fila = {
                        p.getCodigo(),
                        p.getNombre(),
                        p.getCategoria(),
                        String.format("$%.2f", p.getPrecio()),
                        p.getStockDisponible()
                };
                modeloTabla.addRow(fila);
            }
        }
    }

    private void agregarAlCarrito() {
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
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        int stockDisponible = (int) modeloTabla.getValueAt(filaSeleccionada, 4);

        // Solicitar cantidad
        String cantidadStr = JOptionPane.showInputDialog(
                this,
                "Ingrese la cantidad a agregar:\n" +
                        "Producto: " + nombre + "\n" +
                        "Stock disponible: " + stockDisponible,
                "Cantidad",
                JOptionPane.QUESTION_MESSAGE
        );

        if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr.trim());

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "La cantidad debe ser mayor a 0",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (cantidad > stockDisponible) {
                JOptionPane.showMessageDialog(
                        this,
                        "La cantidad solicitada supera el stock disponible",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Agregar al carrito
            Producto producto = controladorProducto.buscarPorCodigo(codigo);
            if (producto != null) {
                boolean agregado = controladorCarrito.agregarProducto(
                        codigo,
                        cantidad
                );

                if (agregado) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Producto agregado al carrito exitosamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error al agregar el producto al carrito",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese una cantidad válida",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
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
        Producto producto = controladorProducto.buscarPorCodigo(codigo);

        if (producto != null) {
            String detalle = obtenerDetalleProducto(producto);
            JOptionPane.showMessageDialog(
                    this,
                    detalle,
                    "Detalle del Producto",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private String obtenerDetalleProducto(Producto producto) {
        StringBuilder detalle = new StringBuilder();
        detalle.append("═══════════════════════════════════\n");
        detalle.append("  INFORMACIÓN DEL PRODUCTO\n");
        detalle.append("═══════════════════════════════════\n\n");
        detalle.append("Código: ").append(producto.getCodigo()).append("\n");
        detalle.append("Nombre: ").append(producto.getNombre()).append("\n");
        detalle.append("Categoría: ").append(producto.getCategoria()).append("\n");
        detalle.append("Precio: $").append(String.format("%.2f", producto.getPrecio())).append("\n");
        detalle.append("Stock Disponible: ").append(producto.getStockDisponible()).append("\n\n");

        // Atributo específico según categoría
        if (producto instanceof ProductoTecnologia) {
            ProductoTecnologia pt = (ProductoTecnologia) producto;
            detalle.append("🔧 Garantía: ").append(pt.getMesesGarantia()).append(" meses\n");
        } else if (producto instanceof ProductoAlimento) {
            ProductoAlimento pa = (ProductoAlimento) producto;
            detalle.append("📅 Fecha de Caducidad: ").append(pa.getFechaCaducidad()).append("\n");
        } else if (producto instanceof ProductoGeneral) {
            ProductoGeneral pg = (ProductoGeneral) producto;
            detalle.append("🏷️ Material: ").append(pg.getMaterial()).append("\n");
        }

        detalle.append("\n═══════════════════════════════════");

        return detalle.toString();
    }

    private void configurarVentana() {
        setTitle("Catálogo de Productos - " + usuarioActual.getNombre());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}