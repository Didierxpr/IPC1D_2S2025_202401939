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
 * Ventana para gestión de pedidos (Vendedor)
 */
public class VentanaGestionPedidosVendedor extends JFrame {

    private ControladorSistema controladorSistema;
    private Usuario usuarioActual;
    private ControladorPedido controladorPedido;

    // Componentes
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JButton btnConfirmar;
    private JButton btnVerDetalle;
    private JButton btnRefrescar;
    private JButton btnCerrar;

    public VentanaGestionPedidosVendedor(ControladorSistema controladorSistema, Usuario usuarioActual) {
        this.controladorSistema = controladorSistema;
        this.usuarioActual = usuarioActual;
        this.controladorPedido = controladorSistema.getControladorPedido();

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
        panelTitulo.setBackground(new Color(230, 126, 34));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("📋 GESTIÓN DE PEDIDOS PENDIENTES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        // Tabla de pedidos pendientes
        String[] columnas = {"Código Pedido", "Cliente", "Fecha", "Hora", "Total", "Productos", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPedidos.setRowHeight(25);
        tablaPedidos.getTableHeader().setReorderingAllowed(false);
        tablaPedidos.getTableHeader().setBackground(new Color(230, 126, 34));
        tablaPedidos.getTableHeader().setForeground(Color.WHITE);
        tablaPedidos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaPedidos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnConfirmar = crearBoton("✅ Confirmar Pedido", new Color(46, 204, 113));
        btnVerDetalle = crearBoton("👁️ Ver Detalle", new Color(52, 152, 219));
        btnRefrescar = crearBoton("🔄 Refrescar", new Color(241, 196, 15));
        btnCerrar = crearBoton("❌ Cerrar", new Color(149, 165, 166));

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnVerDetalle);
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
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarPedido();
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

        Pedido[] pedidosPendientes = controladorPedido.obtenerPedidosPendientes();

        for (Pedido p : pedidosPendientes) {
            Object[] fila = {
                    p.getCodigoPedido(),
                    p.getNombreCliente(),
                    p.getFechaPedido(),
                    p.getHoraPedido(),
                    String.format("$%.2f", p.getTotalPedido()),
                    p.getCantidadProductos(),
                    p.getEstado()
            };
            modeloTabla.addRow(fila);
        }

        // Actualizar título con cantidad
        if (pedidosPendientes.length > 0) {
            setTitle("Gestión de Pedidos (" + pedidosPendientes.length + " pendientes)");
        } else {
            setTitle("Gestión de Pedidos");
        }
    }

    private void confirmarPedido() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();

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
        String cliente = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String total = (String) modeloTabla.getValueAt(filaSeleccionada, 4);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Confirmar el siguiente pedido?\n\n" +
                        "Código: " + codigoPedido + "\n" +
                        "Cliente: " + cliente + "\n" +
                        "Total: " + total + "\n\n" +
                        "Nota: Esto reducirá el stock automáticamente.",
                "Confirmar Pedido",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (controladorPedido.confirmarPedido(codigoPedido)) {
                JOptionPane.showMessageDialog(
                        this,
                        "✓ Pedido confirmado exitosamente\n\n" +
                                "El stock ha sido actualizado automáticamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
                controladorSistema.getGestorHilos().notificarPedidoConfirmado();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al confirmar pedido.\n\n" +
                                "Posibles causas:\n" +
                                "- Stock insuficiente\n" +
                                "- Pedido ya confirmado\n" +
                                "- Error en el sistema",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void verDetalle() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();

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
            JTextArea textArea = new JTextArea(pedido.obtenerDetalleCompleto());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 400));

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Detalle del Pedido: " + codigoPedido,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void configurarVentana() {
        setTitle("Gestión de Pedidos");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
