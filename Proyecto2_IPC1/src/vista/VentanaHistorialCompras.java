package vista;

import controlador.ControladorSistema;
import controlador.ControladorPedido;
import modelo.Usuario;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para visualizar el historial de compras confirmadas del cliente
 */
public class VentanaHistorialCompras extends JFrame {

    private ControladorSistema controladorSistema;
    private Usuario usuarioActual;
    private ControladorPedido controladorPedido;

    // Componentes
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private JButton btnVerDetalle;
    private JButton btnRefrescar;
    private JButton btnCerrar;
    private JLabel lblTotalCompras;
    private JLabel lblGastoTotal;

    public VentanaHistorialCompras(ControladorSistema controladorSistema, Usuario usuarioActual) {
        this.controladorSistema = controladorSistema;
        this.usuarioActual = usuarioActual;
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
        panelTitulo.setBackground(new Color(142, 68, 173));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("📜 HISTORIAL DE COMPRAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        // Panel de estadísticas
        JPanel panelEstadisticas = new JPanel();
        panelEstadisticas.setLayout(new GridLayout(1, 2, 15, 10));
        panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Panel de total de compras
        JPanel panelTotalCompras = new JPanel();
        panelTotalCompras.setLayout(new BorderLayout());
        panelTotalCompras.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Total de Compras",
                0,
                0,
                new Font("Arial", Font.BOLD, 12)
        ));
        panelTotalCompras.setBackground(Color.WHITE);

        lblTotalCompras = new JLabel("0", SwingConstants.CENTER);
        lblTotalCompras.setFont(new Font("Arial", Font.BOLD, 32));
        lblTotalCompras.setForeground(new Color(52, 152, 219));
        panelTotalCompras.add(lblTotalCompras, BorderLayout.CENTER);

        // Panel de gasto total
        JPanel panelGastoTotal = new JPanel();
        panelGastoTotal.setLayout(new BorderLayout());
        panelGastoTotal.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
                "Gasto Total",
                0,
                0,
                new Font("Arial", Font.BOLD, 12)
        ));
        panelGastoTotal.setBackground(Color.WHITE);

        lblGastoTotal = new JLabel("$0.00", SwingConstants.CENTER);
        lblGastoTotal.setFont(new Font("Arial", Font.BOLD, 32));
        lblGastoTotal.setForeground(new Color(46, 204, 113));
        panelGastoTotal.add(lblGastoTotal, BorderLayout.CENTER);

        panelEstadisticas.add(panelTotalCompras);
        panelEstadisticas.add(panelGastoTotal);

        // Tabla de historial
        String[] columnas = {"Código Pedido", "Fecha", "Hora", "Total", "Productos", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaHistorial.setRowHeight(30);
        tablaHistorial.getTableHeader().setReorderingAllowed(false);
        tablaHistorial.getTableHeader().setBackground(new Color(142, 68, 173));
        tablaHistorial.getTableHeader().setForeground(Color.WHITE);
        tablaHistorial.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Ajustar anchos de columna
        tablaHistorial.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablaHistorial.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaHistorial.getColumnModel().getColumn(2).setPreferredWidth(80);
        tablaHistorial.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaHistorial.getColumnModel().getColumn(4).setPreferredWidth(80);
        tablaHistorial.getColumnModel().getColumn(5).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnVerDetalle = crearBoton("👁️ Ver Detalle", new Color(52, 152, 219));
        btnRefrescar = crearBoton("🔄 Refrescar", new Color(241, 196, 15));
        btnCerrar = crearBoton("❌ Cerrar", new Color(149, 165, 166));

        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnCerrar);

        // Agregar componentes al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelEstadisticas, BorderLayout.AFTER_LAST_LINE);

        // Panel central con tabla
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(panelEstadisticas, BorderLayout.NORTH);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
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
        boton.setPreferredSize(new Dimension(150, 35));
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
                controladorPedido.recargar();
                cargarDatos();
                JOptionPane.showMessageDialog(
                        VentanaHistorialCompras.this,
                        "Datos actualizados",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Doble clic en la tabla para ver detalle
        tablaHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    verDetalle();
                }
            }
        });
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);

        // Obtener historial de compras del cliente
        Pedido[] historial = controladorPedido.obtenerHistorialCompras(usuarioActual.getCodigo());

        double gastoTotal = 0.0;

        for (Pedido pedido : historial) {
            Object[] fila = {
                    pedido.getCodigoPedido(),
                    pedido.getFechaPedido(),
                    pedido.getHoraPedido(),
                    String.format("$%.2f", pedido.getTotalPedido()),
                    pedido.getCantidadProductos(),
                    pedido.getEstado()
            };
            modeloTabla.addRow(fila);
            gastoTotal += pedido.getTotalPedido();
        }

        // Actualizar estadísticas
        lblTotalCompras.setText(String.valueOf(historial.length));
        lblGastoTotal.setText(String.format("$%.2f", gastoTotal));
    }

    private void verDetalle() {
        int filaSeleccionada = tablaHistorial.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String codigoPedido = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        Pedido pedido = controladorPedido.buscarPorCodigo(codigoPedido);

        if (pedido != null) {
            // Crear ventana de detalle
            mostrarDetalleCompleto(pedido);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo cargar el detalle del pedido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void mostrarDetalleCompleto(Pedido pedido) {
        // Crear diálogo personalizado
        JDialog dialogo = new JDialog(this, "Detalle del Pedido", true);
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.setSize(600, 500);
        dialogo.setLocationRelativeTo(this);

        // Panel de información del pedido
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(6, 2, 10, 10));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelInfo.setBackground(Color.WHITE);

        panelInfo.add(crearEtiquetaNegrita("Código Pedido:"));
        panelInfo.add(new JLabel(pedido.getCodigoPedido()));

        panelInfo.add(crearEtiquetaNegrita("Cliente:"));
        panelInfo.add(new JLabel(pedido.getNombreCliente()));

        panelInfo.add(crearEtiquetaNegrita("Fecha:"));
        panelInfo.add(new JLabel(pedido.getFechaPedido()));

        panelInfo.add(crearEtiquetaNegrita("Hora:"));
        panelInfo.add(new JLabel(pedido.getHoraPedido()));

        panelInfo.add(crearEtiquetaNegrita("Estado:"));
        JLabel lblEstado = new JLabel(pedido.getEstado());
        lblEstado.setForeground(new Color(46, 204, 113));
        lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
        panelInfo.add(lblEstado);

        panelInfo.add(crearEtiquetaNegrita("Total:"));
        JLabel lblTotal = new JLabel(String.format("$%.2f", pedido.getTotalPedido()));
        lblTotal.setForeground(new Color(46, 204, 113));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        panelInfo.add(lblTotal);

        // Tabla de productos del pedido
        String[] columnas = {"Producto", "Cantidad", "Precio Unit.", "Subtotal"};
        DefaultTableModel modeloDetalle = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaDetalle = new JTable(modeloDetalle);
        tablaDetalle.setRowHeight(25);
        tablaDetalle.getTableHeader().setBackground(new Color(142, 68, 173));
        tablaDetalle.getTableHeader().setForeground(Color.WHITE);
        tablaDetalle.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));

        // Llenar tabla con productos
        String[] nombres = pedido.getNombresProductos();
        int[] cantidades = pedido.getCantidades();
        double[] precios = pedido.getPreciosUnitarios();
        double[] subtotales = pedido.getSubtotales();

        for (int i = 0; i < nombres.length; i++) {
            Object[] fila = {
                    nombres[i],
                    cantidades[i],
                    String.format("$%.2f", precios[i]),
                    String.format("$%.2f", subtotales[i])
            };
            modeloDetalle.addRow(fila);
        }

        JScrollPane scrollDetalle = new JScrollPane(tablaDetalle);
        scrollDetalle.setBorder(BorderFactory.createTitledBorder("Productos"));

        // Botón cerrar
        JButton btnCerrarDialogo = new JButton("Cerrar");
        btnCerrarDialogo.setBackground(new Color(149, 165, 166));
        btnCerrarDialogo.setForeground(Color.WHITE);
        btnCerrarDialogo.setFocusPainted(false);
        btnCerrarDialogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogo.dispose();
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrarDialogo);

        // Agregar componentes al diálogo
        dialogo.add(panelInfo, BorderLayout.NORTH);
        dialogo.add(scrollDetalle, BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private JLabel crearEtiquetaNegrita(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 12));
        return etiqueta;
    }

    private void configurarVentana() {
        setTitle("Historial de Compras - " + usuarioActual.getNombre());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
