package vista;

import controlador.ControladorSistema;
import controlador.ControladorAdministrador;
import modelo.Usuario;
import modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para gestión de productos (CRUD)
 */
public class VentanaGestionProductos extends JFrame {

    private ControladorSistema controladorSistema;
    private Usuario usuarioActual;
    private ControladorAdministrador controladorAdmin;

    // Componentes
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnCrear;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnVerDetalle;
    private JButton btnCargarCSV;
    private JButton btnRefrescar;
    private JButton btnCerrar;

    public VentanaGestionProductos(ControladorSistema controladorSistema, Usuario usuarioActual) {
        this.controladorSistema = controladorSistema;
        this.usuarioActual = usuarioActual;
        this.controladorAdmin = controladorSistema.getControladorAdministrador();

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
        panelTitulo.setBackground(new Color(46, 204, 113));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("GESTIÓN DE PRODUCTOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        // Tabla de productos
        String[] columnas = {"Código", "Nombre", "Categoría", "Precio", "Stock", "Vendidos"};
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
        tablaProductos.getTableHeader().setBackground(new Color(46, 204, 113));
        tablaProductos.getTableHeader().setForeground(Color.WHITE);
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnCrear = crearBoton("➕ Crear", new Color(46, 204, 113));
        btnActualizar = crearBoton("✏️ Actualizar", new Color(241, 196, 15));
        btnEliminar = crearBoton("🗑️ Eliminar", new Color(231, 76, 60));
        btnVerDetalle = crearBoton("👁️ Ver Detalle", new Color(52, 152, 219));
        btnCargarCSV = crearBoton("📁 Cargar CSV", new Color(155, 89, 182));
        btnRefrescar = crearBoton("🔄 Refrescar", new Color(52, 152, 219));
        btnCerrar = crearBoton("❌ Cerrar", new Color(149, 165, 166));

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnCargarCSV);
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
        boton.setPreferredSize(new Dimension(140, 35));

        return boton;
    }

    private void configurarEventos() {
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearProducto();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        btnVerDetalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verDetalle();
            }
        });

        btnCargarCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDesdeCSV();
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

        Producto[] productos = controladorAdmin.obtenerTodosProductos();

        for (Producto p : productos) {
            Object[] fila = {
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCategoria(),
                    String.format("$%.2f", p.getPrecio()),
                    p.getStockDisponible(),
                    p.getCantidadVendida()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void crearProducto() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtPrecio = new JTextField();
        JComboBox<String> cmbCategoria = new JComboBox<>(new String[]{"TECNOLOGIA", "ALIMENTO", "GENERAL"});
        JTextField txtAtributo = new JTextField();

        JLabel lblAtributo = new JLabel("Meses de Garantía:");

        // Cambiar label según categoría
        cmbCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoria = (String) cmbCategoria.getSelectedItem();
                switch (categoria) {
                    case "TECNOLOGIA":
                        lblAtributo.setText("Meses de Garantía:");
                        break;
                    case "ALIMENTO":
                        lblAtributo.setText("Fecha Caducidad (DD/MM/YYYY):");
                        break;
                    case "GENERAL":
                        lblAtributo.setText("Material:");
                        break;
                }
            }
        });

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Categoría:"));
        panel.add(cmbCategoria);
        panel.add(lblAtributo);
        panel.add(txtAtributo);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Crear Producto",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String codigo = txtCodigo.getText().trim();
                String nombre = txtNombre.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                String categoria = (String) cmbCategoria.getSelectedItem();
                String atributo = txtAtributo.getText().trim();

                if (codigo.isEmpty() || nombre.isEmpty() || atributo.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Todos los campos son obligatorios",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                if (controladorAdmin.crearProducto(codigo, nombre, categoria, precio, atributo)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Producto creado exitosamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    cargarDatos();
                    controladorSistema.getGestorHilos().notificarActividad("Producto creado: " + codigo);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error al crear producto. Verifique los datos.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "El precio debe ser un número válido",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void actualizarProducto() {
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
        String nombreActual = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String categoria = (String) modeloTabla.getValueAt(filaSeleccionada, 2);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        JTextField txtNombre = new JTextField(nombreActual);
        JTextField txtAtributo = new JTextField();

        String labelAtributo = "";
        switch (categoria) {
            case "TECNOLOGIA":
                labelAtributo = "Nuevos Meses de Garantía:";
                break;
            case "ALIMENTO":
                labelAtributo = "Nueva Fecha Caducidad:";
                break;
            case "GENERAL":
                labelAtributo = "Nuevo Material:";
                break;
        }

        panel.add(new JLabel("Nuevo Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel(labelAtributo));
        panel.add(txtAtributo);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Actualizar Producto: " + codigo,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNombre.getText().trim();
            String nuevoAtributo = txtAtributo.getText().trim();

            if (controladorAdmin.actualizarProducto(codigo, nuevoNombre, nuevoAtributo)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Producto actualizado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
                controladorSistema.getGestorHilos().notificarActividad("Producto actualizado: " + codigo);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al actualizar producto",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void eliminarProducto() {
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

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar el producto?\n\n" +
                        "Código: " + codigo + "\n" +
                        "Nombre: " + nombre,
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (controladorAdmin.eliminarProducto(codigo)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Producto eliminado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
                controladorSistema.getGestorHilos().notificarActividad("Producto eliminado: " + codigo);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar producto",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
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
        Producto producto = controladorAdmin.buscarProducto(codigo);

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

    private void cargarDesdeCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();

            if (controladorAdmin.cargarProductosCSV(rutaArchivo)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Productos cargados exitosamente desde CSV",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
                controladorSistema.getGestorHilos().notificarActividad("Productos cargados desde CSV");
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

    private void configurarVentana() {
        setTitle("Gestión de Productos");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
