package vista;

import controlador.ControladorSistema;
import controlador.ControladorBitacora;
import modelo.Bitacora;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para visualizar la bitácora del sistema
 */
public class VentanaBitacora extends JFrame {

    private ControladorSistema controladorSistema;
    private ControladorBitacora controladorBitacora;

    // Componentes
    private JTable tablaBitacora;
    private DefaultTableModel modeloTabla;
    private JButton btnRefrescar;
    private JButton btnExportarCSV;
    private JButton btnFiltrar;
    private JButton btnLimpiarFiltros;
    private JButton btnCerrar;
    private JComboBox<String> cmbTipoUsuario;
    private JComboBox<String> cmbEstado;

    public VentanaBitacora(ControladorSistema controladorSistema) {
        this.controladorSistema = controladorSistema;
        this.controladorBitacora = controladorSistema.getControladorBitacora();

        inicializarComponentes();
        cargarDatos();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior con título y filtros
        JPanel panelSuperior = new JPanel(new BorderLayout());

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(52, 73, 94));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("📋 BITÁCORA DEL SISTEMA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));

        panelFiltros.add(new JLabel("Tipo Usuario:"));
        cmbTipoUsuario = new JComboBox<>(new String[]{"TODOS", "ADMINISTRADOR", "VENDEDOR", "CLIENTE", "SISTEMA"});
        panelFiltros.add(cmbTipoUsuario);

        panelFiltros.add(new JLabel("Estado:"));
        cmbEstado = new JComboBox<>(new String[]{"TODOS", "EXITOSA", "FALLIDA"});
        panelFiltros.add(cmbEstado);

        btnFiltrar = new JButton("🔍 Aplicar Filtros");
        btnFiltrar.setBackground(new Color(52, 152, 219));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFocusPainted(false);
        panelFiltros.add(btnFiltrar);

        btnLimpiarFiltros = new JButton("🔄 Limpiar");
        btnLimpiarFiltros.setBackground(new Color(149, 165, 166));
        btnLimpiarFiltros.setForeground(Color.WHITE);
        btnLimpiarFiltros.setFocusPainted(false);
        panelFiltros.add(btnLimpiarFiltros);

        panelSuperior.add(panelTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelFiltros, BorderLayout.CENTER);

        // Tabla de bitácora
        String[] columnas = {"Fecha", "Hora", "Tipo Usuario", "Código", "Operación", "Estado", "Descripción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaBitacora = new JTable(modeloTabla);
        tablaBitacora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaBitacora.setRowHeight(25);
        tablaBitacora.getTableHeader().setReorderingAllowed(false);
        tablaBitacora.getTableHeader().setBackground(new Color(52, 73, 94));
        tablaBitacora.getTableHeader().setForeground(Color.WHITE);
        tablaBitacora.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));

        // Ajustar anchos de columnas
        tablaBitacora.getColumnModel().getColumn(0).setPreferredWidth(80);  // Fecha
        tablaBitacora.getColumnModel().getColumn(1).setPreferredWidth(70);  // Hora
        tablaBitacora.getColumnModel().getColumn(2).setPreferredWidth(120); // Tipo Usuario
        tablaBitacora.getColumnModel().getColumn(3).setPreferredWidth(80);  // Código
        tablaBitacora.getColumnModel().getColumn(4).setPreferredWidth(150); // Operación
        tablaBitacora.getColumnModel().getColumn(5).setPreferredWidth(80);  // Estado
        tablaBitacora.getColumnModel().getColumn(6).setPreferredWidth(300); // Descripción

        JScrollPane scrollPane = new JScrollPane(tablaBitacora);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnRefrescar = crearBoton("🔄 Refrescar", new Color(52, 152, 219));
        btnExportarCSV = crearBoton("📄 Exportar CSV", new Color(46, 204, 113));
        btnCerrar = crearBoton("❌ Cerrar", new Color(149, 165, 166));

        panelBotones.add(btnRefrescar);
        panelBotones.add(btnExportarCSV);
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
        boton.setPreferredSize(new Dimension(150, 35));

        return boton;
    }

    private void configurarEventos() {
        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatos();
            }
        });

        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarFiltros();
            }
        });

        btnLimpiarFiltros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmbTipoUsuario.setSelectedIndex(0);
                cmbEstado.setSelectedIndex(0);
                cargarDatos();
            }
        });

        btnExportarCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarCSV();
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

        Bitacora[] registros = controladorBitacora.obtenerTodos();

        // Mostrar los últimos 100 registros (o todos si hay menos)
        int inicio = Math.max(0, registros.length - 100);

        for (int i = inicio; i < registros.length; i++) {
            Bitacora b = registros[i];
            Object[] fila = {
                    b.getFecha(),
                    b.getHora(),
                    b.getTipoUsuario(),
                    b.getCodigoUsuario(),
                    b.getOperacion(),
                    b.getEstado(),
                    truncar(b.getDescripcion(), 50)
            };
            modeloTabla.addRow(fila);
        }

        // Scroll al final
        if (tablaBitacora.getRowCount() > 0) {
            tablaBitacora.scrollRectToVisible(
                    tablaBitacora.getCellRect(tablaBitacora.getRowCount() - 1, 0, true)
            );
        }
    }

    private void aplicarFiltros() {
        modeloTabla.setRowCount(0);

        String tipoUsuario = (String) cmbTipoUsuario.getSelectedItem();
        String estado = (String) cmbEstado.getSelectedItem();

        Bitacora[] registros = controladorBitacora.obtenerTodos();

        // Aplicar filtros
        if (!tipoUsuario.equals("TODOS")) {
            registros = controladorBitacora.filtrarPorTipoUsuario(tipoUsuario);
        }

        for (Bitacora b : registros) {
            // Filtrar por estado si no es "TODOS"
            if (!estado.equals("TODOS") && !b.getEstado().equals(estado)) {
                continue;
            }

            Object[] fila = {
                    b.getFecha(),
                    b.getHora(),
                    b.getTipoUsuario(),
                    b.getCodigoUsuario(),
                    b.getOperacion(),
                    b.getEstado(),
                    truncar(b.getDescripcion(), 50)
            };
            modeloTabla.addRow(fila);
        }
    }

    private void exportarCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Bitácora como CSV");
        fileChooser.setSelectedFile(new java.io.File("bitacora_" +
                new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date()) + ".csv"));

        int resultado = fileChooser.showSaveDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();

            if (!rutaArchivo.toLowerCase().endsWith(".csv")) {
                rutaArchivo += ".csv";
            }

            if (controladorBitacora.exportarCSV(rutaArchivo)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Bitácora exportada exitosamente a:\n" + rutaArchivo,
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al exportar la bitácora",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private String truncar(String texto, int longitud) {
        if (texto == null) return "";
        if (texto.length() <= longitud) return texto;
        return texto.substring(0, longitud) + "...";
    }

    private void configurarVentana() {
        setTitle("Bitácora del Sistema");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}