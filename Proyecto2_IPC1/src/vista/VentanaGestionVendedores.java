package vista;

import controlador.ControladorSistema;
import controlador.ControladorAdministrador;
import modelo.Usuario;
import modelo.Vendedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para gestión de vendedores (CRUD)
 */
public class VentanaGestionVendedores extends JFrame {

    private ControladorSistema controladorSistema;
    private Usuario usuarioActual;
    private ControladorAdministrador controladorAdmin;

    // Componentes
    private JTable tablaVendedores;
    private DefaultTableModel modeloTabla;
    private JButton btnCrear;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnCargarCSV;
    private JButton btnRefrescar;
    private JButton btnCerrar;

    public VentanaGestionVendedores(ControladorSistema controladorSistema, Usuario usuarioActual) {
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
        panelTitulo.setBackground(new Color(52, 152, 219));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("GESTIÓN DE VENDEDORES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        // Tabla de vendedores
        String[] columnas = {"Código", "Nombre", "Género", "Ventas Confirmadas", "Clientes Registrados"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }
        };

        tablaVendedores = new JTable(modeloTabla);
        tablaVendedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaVendedores.setRowHeight(25);
        tablaVendedores.getTableHeader().setReorderingAllowed(false);
        tablaVendedores.getTableHeader().setBackground(new Color(52, 152, 219));
        tablaVendedores.getTableHeader().setForeground(Color.WHITE);
        tablaVendedores.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaVendedores);
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
                crearVendedor();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarVendedor();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarVendedor();
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
        modeloTabla.setRowCount(0); // Limpiar tabla

        Vendedor[] vendedores = controladorAdmin.obtenerTodosVendedores();

        for (Vendedor v : vendedores) {
            Object[] fila = {
                    v.getCodigo(),
                    v.getNombre(),
                    v.getGenero(),
                    v.getVentasConfirmadas(),
                    v.getClientesRegistrados()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void crearVendedor() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();
        JComboBox<String> cmbGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        JPasswordField txtContrasenia = new JPasswordField();

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Género:"));
        panel.add(cmbGenero);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtContrasenia);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Crear Vendedor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String genero = (String) cmbGenero.getSelectedItem();
            String contrasenia = new String(txtContrasenia.getPassword());

            if (codigo.isEmpty() || nombre.isEmpty() || contrasenia.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (controladorAdmin.crearVendedor(codigo, nombre, genero, contrasenia)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vendedor creado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
                controladorSistema.getGestorHilos().notificarActividad("Vendedor creado: " + codigo);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al crear vendedor. Verifique que el código sea único.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void actualizarVendedor() {
        int filaSeleccionada = tablaVendedores.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un vendedor de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String codigo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombreActual = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        JTextField txtNombre = new JTextField(nombreActual);
        JPasswordField txtContrasenia = new JPasswordField();

        panel.add(new JLabel("Nuevo Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Nueva Contraseña:"));
        panel.add(txtContrasenia);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Actualizar Vendedor: " + codigo,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNombre.getText().trim();
            String nuevaContrasenia = new String(txtContrasenia.getPassword());

            if (controladorAdmin.actualizarVendedor(codigo, nuevoNombre, nuevaContrasenia)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vendedor actualizado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
                controladorSistema.getGestorHilos().notificarActividad("Vendedor actualizado: " + codigo);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al actualizar vendedor",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void eliminarVendedor() {
        int filaSeleccionada = tablaVendedores.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un vendedor de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String codigo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar al vendedor?\n\n" +
                        "Código: " + codigo + "\n" +
                        "Nombre: " + nombre,
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (controladorAdmin.eliminarVendedor(codigo)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vendedor eliminado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
                controladorSistema.getGestorHilos().notificarActividad("Vendedor eliminado: " + codigo);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar vendedor",
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

            if (controladorAdmin.cargarVendedoresCSV(rutaArchivo)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vendedores cargados exitosamente desde CSV",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
                controladorSistema.getGestorHilos().notificarActividad("Vendedores cargados desde CSV");
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
        setTitle("Gestión de Vendedores");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
