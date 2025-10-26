package vista;

import controlador.ControladorSistema;
import controlador.ControladorCliente;
import modelo.Usuario;
import modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para gestión de clientes (Vendedor)
 */
public class VentanaGestionClientesVendedor extends JFrame {

    private ControladorSistema controladorSistema;
    private Usuario usuarioActual;
    private ControladorCliente controladorCliente;

    // Componentes
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JButton btnCrear;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnCargarCSV;
    private JButton btnRefrescar;
    private JButton btnCerrar;

    public VentanaGestionClientesVendedor(ControladorSistema controladorSistema, Usuario usuarioActual) {
        this.controladorSistema = controladorSistema;
        this.usuarioActual = usuarioActual;
        this.controladorCliente = controladorSistema.getControladorCliente();

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

        JLabel lblTitulo = new JLabel("👥 GESTIÓN DE MIS CLIENTES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        // Tabla de clientes
        String[] columnas = {"Código", "Nombre", "Género", "Cumpleaños", "Compras", "Total Gastado", "Clasificación"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.setRowHeight(25);
        tablaClientes.getTableHeader().setReorderingAllowed(false);
        tablaClientes.getTableHeader().setBackground(new Color(46, 204, 113));
        tablaClientes.getTableHeader().setForeground(Color.WHITE);
        tablaClientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnCrear = crearBoton("➕ Crear", new Color(46, 204, 113));
        btnActualizar = crearBoton("✏️ Actualizar", new Color(241, 196, 15));
        btnEliminar = crearBoton("🗑️ Eliminar", new Color(231, 76, 60));
        btnCargarCSV = crearBoton("📁 Cargar CSV", new Color(155, 89, 182));
        btnRefrescar = crearBoton("🔄 Refrescar", new Color(52, 152, 219));
        btnCerrar = crearBoton("❌ Cerrar", new Color(149, 165, 166));

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
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
                crearCliente();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCliente();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
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

        Cliente[] clientes = controladorCliente.obtenerClientesPorVendedor(usuarioActual.getCodigo());

        for (Cliente c : clientes) {
            Object[] fila = {
                    c.getCodigo(),
                    c.getNombre(),
                    c.getGenero(),
                    c.getCumpleanios(),
                    c.getComprasRealizadas(),
                    String.format("$%.2f", c.getTotalGastado()),
                    c.getClasificacion()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void crearCliente() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();
        JComboBox<String> cmbGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        JTextField txtCumpleanios = new JTextField();
        JPasswordField txtContrasenia = new JPasswordField();

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Género:"));
        panel.add(cmbGenero);
        panel.add(new JLabel("Cumpleaños (DD/MM/YYYY):"));
        panel.add(txtCumpleanios);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtContrasenia);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Crear Cliente",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String genero = (String) cmbGenero.getSelectedItem();
            String cumpleanios = txtCumpleanios.getText().trim();
            String contrasenia = new String(txtContrasenia.getPassword());

            if (codigo.isEmpty() || nombre.isEmpty() || cumpleanios.isEmpty() || contrasenia.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Cliente cliente = new Cliente(codigo, nombre, genero, cumpleanios, contrasenia, usuarioActual.getCodigo());

            if (controladorCliente.crear(cliente)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cliente creado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
                controladorSistema.getGestorHilos().notificarActividad("Cliente creado: " + codigo);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al crear cliente. Verifique que el código sea único.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void actualizarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un cliente de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String codigo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        Cliente cliente = controladorCliente.buscarPorCodigo(codigo);

        if (cliente == null || !cliente.getVendedorAsignado().equals(usuarioActual.getCodigo())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Este cliente no le pertenece",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        JTextField txtNombre = new JTextField(cliente.getNombre());
        JPasswordField txtContrasenia = new JPasswordField();

        panel.add(new JLabel("Nuevo Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Nueva Contraseña:"));
        panel.add(txtContrasenia);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Actualizar Cliente: " + codigo,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNombre.getText().trim();
            String nuevaContrasenia = new String(txtContrasenia.getPassword());

            if (!nuevoNombre.isEmpty()) {
                cliente.setNombre(nuevoNombre);
            }

            if (!nuevaContrasenia.isEmpty()) {
                cliente.setContrasenia(nuevaContrasenia);
            }

            if (controladorCliente.actualizar(codigo, cliente)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cliente actualizado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
                controladorSistema.getGestorHilos().notificarActividad("Cliente actualizado: " + codigo);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al actualizar cliente",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void eliminarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un cliente de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String codigo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        Cliente cliente = controladorCliente.buscarPorCodigo(codigo);

        if (cliente == null || !cliente.getVendedorAsignado().equals(usuarioActual.getCodigo())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Este cliente no le pertenece",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar al cliente?\n\n" +
                        "Código: " + codigo + "\n" +
                        "Nombre: " + nombre,
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (controladorCliente.eliminar(codigo)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cliente eliminado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
                controladorSistema.getGestorHilos().notificarActividad("Cliente eliminado: " + codigo);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar cliente",
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

            String[] lineas = utilidades.ManejadorArchivos.leerCSV(rutaArchivo, true);

            if (lineas == null || lineas.length == 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al leer el archivo CSV",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int exitosos = 0;
            int errores = 0;

            for (String linea : lineas) {
                if (linea.trim().isEmpty()) continue;

                String[] datos = utilidades.ManejadorArchivos.parsearLineaCSV(linea);

                if (datos.length < 5) {
                    errores++;
                    continue;
                }

                Cliente cliente = new Cliente(
                        datos[0].trim(),
                        datos[1].trim(),
                        datos[2].trim(),
                        datos[3].trim(),
                        datos[4].trim(),
                        usuarioActual.getCodigo()
                );

                if (controladorCliente.crear(cliente)) {
                    exitosos++;
                } else {
                    errores++;
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Carga completada:\n" +
                            "Exitosos: " + exitosos + "\n" +
                            "Errores: " + errores,
                    "Resultado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarDatos();
            controladorSistema.getGestorHilos().notificarActividad("Clientes cargados desde CSV");
        }
    }

    private void configurarVentana() {
        setTitle("Gestión de Mis Clientes");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
