package vista;

import controlador.ControladorSistema;
import controlador.ControladorCarrito;
import controlador.ControladorPedido;
import modelo.Usuario;
import modelo.Cliente;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para gestionar el carrito de compras del cliente
 */
public class VentanaCarritoCompras extends JFrame {

    private ControladorSistema controladorSistema;
    private Usuario usuarioActual;
    private ControladorCarrito controladorCarrito;
    private ControladorPedido controladorPedido;

    // Componentes
    private JTable tablaCarrito;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;
    private JLabel lblCantidadItems;
    private JButton btnActualizarCantidad;
    private JButton btnEliminarProducto;
    private JButton btnVaciarCarrito;
    private JButton btnRealizarPedido;
    private JButton btnRefrescar;
    private JButton btnCerrar;

    public VentanaCarritoCompras(ControladorSistema controladorSistema, Usuario usuarioActual, ControladorCarrito controladorCarrito) {
        this.controladorSistema = controladorSistema;
        this.usuarioActual = usuarioActual;
        this.controladorCarrito = controladorCarrito;
        this.controladorPedido = new ControladorPedido(usuarioActual);

        inicializarComponentes();
        cargarDatos();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(46, 204, 113));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("🛒 MI CARRITO DE COMPRAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        // Tabla del carrito
        String[] columnas = {"Código", "Producto", "Precio Unit.", "Cantidad", "Subtotal"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCarrito = new JTable(modeloTabla);
        tablaCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCarrito.setRowHeight(30);
        tablaCarrito.getTableHeader().setReorderingAllowed(false);
        tablaCarrito.getTableHeader().setBackground(new Color(46, 204, 113));
        tablaCarrito.getTableHeader().setForeground(Color.WHITE);
        tablaCarrito.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaCarrito);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

        // Panel de resumen
        JPanel panelResumen = new JPanel();
        panelResumen.setLayout(new GridLayout(2, 1, 10, 10));
        panelResumen.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
                "Resumen",
                0,
                0,
                new Font("Arial", Font.BOLD, 14)
        ));
        panelResumen.setBackground(Color.WHITE);

        lblCantidadItems = new JLabel("Items en carrito: 0");
        lblCantidadItems.setFont(new Font("Arial", Font.PLAIN, 14));

        lblTotal = new JLabel("TOTAL: $0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setForeground(new Color(46, 204, 113));

        panelResumen.add(lblCantidadItems);
        panelResumen.add(lblTotal);

        // Panel de botones de acciones
        JPanel panelAcciones = new JPanel();
        panelAcciones.setLayout(new GridLayout(2, 2, 10, 10));
        panelAcciones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        btnActualizarCantidad = crearBoton("✏️ Actualizar Cantidad", new Color(52, 152, 219));
        btnEliminarProducto = crearBoton("🗑️ Eliminar Producto", new Color(231, 76, 60));
        btnVaciarCarrito = crearBoton("🧹 Vaciar Carrito", new Color(230, 126, 34));
        btnRefrescar = crearBoton("🔄 Refrescar", new Color(149, 165, 166));

        panelAcciones.add(btnActualizarCantidad);
        panelAcciones.add(btnEliminarProducto);
        panelAcciones.add(btnVaciarCarrito);
        panelAcciones.add(btnRefrescar);

        // Panel de botones principales
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnRealizarPedido = crearBoton("✅ REALIZAR PEDIDO", new Color(46, 204, 113));
        btnRealizarPedido.setPreferredSize(new Dimension(200, 45));
        btnRealizarPedido.setFont(new Font("Arial", Font.BOLD, 14));

        btnCerrar = crearBoton("❌ Cerrar", new Color(149, 165, 166));

        panelBotones.add(btnRealizarPedido);
        panelBotones.add(btnCerrar);

        // Panel inferior que contiene resumen, acciones y botones
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout(10, 10));
        panelInferior.add(panelResumen, BorderLayout.NORTH);
        panelInferior.add(panelAcciones, BorderLayout.CENTER);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        // Agregar componentes al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

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
        return boton;
    }

    private void configurarEventos() {
        btnActualizarCantidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCantidad();
            }
        });

        btnEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        btnVaciarCarrito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vaciarCarrito();
            }
        });

        btnRealizarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarPedido();
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

        String[] codigos = controladorCarrito.getCodigosProductos();
        String[] nombres = controladorCarrito.getNombresProductos();
        int[] cantidades = controladorCarrito.getCantidades();
        double[] precios = controladorCarrito.getPreciosUnitarios();
        double[] subtotales = controladorCarrito.getSubtotales();

        for (int i = 0; i < codigos.length; i++) {
            Object[] fila = {
                    codigos[i],
                    nombres[i],
                    String.format("$%.2f", precios[i]),
                    cantidades[i],
                    String.format("$%.2f", subtotales[i])
            };
            modeloTabla.addRow(fila);
        }

        actualizarResumen();
    }

    private void actualizarResumen() {
        int totalItems = controladorCarrito.getCantidadTotalItems();
        double total = controladorCarrito.calcularTotal();

        lblCantidadItems.setText("Items en carrito: " + totalItems);
        lblTotal.setText("TOTAL: $" + String.format("%.2f", total));
    }

    private void actualizarCantidad() {
        int filaSeleccionada = tablaCarrito.getSelectedRow();

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
        int cantidadActual = (int) modeloTabla.getValueAt(filaSeleccionada, 3);

        String cantidadStr = JOptionPane.showInputDialog(
                this,
                "Ingrese la nueva cantidad:\n" +
                        "Producto: " + nombre + "\n" +
                        "Cantidad actual: " + cantidadActual,
                "Actualizar Cantidad",
                JOptionPane.QUESTION_MESSAGE
        );

        if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
            return;
        }

        try {
            int nuevaCantidad = Integer.parseInt(cantidadStr.trim());

            if (nuevaCantidad <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "La cantidad debe ser mayor a 0",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            boolean actualizado = controladorCarrito.actualizarCantidad(codigo, nuevaCantidad);

            if (actualizado) {
                cargarDatos();
                JOptionPane.showMessageDialog(
                        this,
                        "Cantidad actualizada exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al actualizar la cantidad. Verifique el stock disponible",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
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

    private void eliminarProducto() {
        int filaSeleccionada = tablaCarrito.getSelectedRow();

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

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este producto del carrito?\n\n" +
                        "Producto: " + nombre,
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = controladorCarrito.eliminarProducto(codigo);

            if (eliminado) {
                cargarDatos();
                JOptionPane.showMessageDialog(
                        this,
                        "Producto eliminado del carrito",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar el producto",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void vaciarCarrito() {
        if (controladorCarrito.estaVacio()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El carrito ya está vacío",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de vaciar completamente el carrito?\n" +
                        "Esta acción eliminará todos los productos.",
                "Confirmar Vaciar Carrito",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            controladorCarrito.vaciarCarrito();
            cargarDatos();
            JOptionPane.showMessageDialog(
                    this,
                    "Carrito vaciado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void realizarPedido() {
        if (controladorCarrito.estaVacio()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El carrito está vacío.\nAgregue productos antes de realizar un pedido.",
                    "Carrito Vacío",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Mostrar resumen del pedido
        String resumen = controladorCarrito.obtenerResumen();
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                resumen + "\n\n¿Desea confirmar el pedido?",
                "Confirmar Pedido",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                // Generar código de pedido
                String codigoPedido = controladorPedido.generarCodigoPedido();

                // Crear pedido desde el carrito
                Cliente cliente = (Cliente) usuarioActual;
                Pedido pedido = controladorCarrito.generarPedido(codigoPedido);

                if (pedido != null) {
                    // Guardar el pedido
                    boolean pedidoCreado = controladorPedido.crear(pedido);

                    if (pedidoCreado) {
                        // Vaciar el carrito
                        controladorCarrito.vaciarCarrito();
                        cargarDatos();

                        JOptionPane.showMessageDialog(
                                this,
                                "¡Pedido realizado exitosamente!\n\n" +
                                        "Código de pedido: " + codigoPedido + "\n" +
                                        "Total: $" + String.format("%.2f", pedido.getTotalPedido()) + "\n\n" +
                                        "Su pedido será procesado por el vendedor.",
                                "Pedido Exitoso",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Error al crear el pedido. Intente nuevamente.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error al generar el pedido",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al procesar el pedido: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void configurarVentana() {
        setTitle("Carrito de Compras - " + usuarioActual.getNombre());
        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}