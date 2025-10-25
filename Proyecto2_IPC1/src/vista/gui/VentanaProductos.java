package vista.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

import modelo.Sistema;
import modelo.productos.*;
import modelo.usuarios.Usuario;

public class VentanaProductos extends JFrame {

    private final JTextField txtCodigo;
    private final JTextField txtNombre;
    private final JTextField txtPrecio;
    private final JTextField txtStock;
    private final JComboBox<String> cbCategoria;
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;
    private final Usuario usuario;

    public VentanaProductos(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Gestión de Productos");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ================================
        // 🔹 PANEL SUPERIOR: FORMULARIO
        // ================================
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Agregar Producto"));
        panelFormulario.setBackground(new Color(245, 245, 245));

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Precio (Q):"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panelFormulario.add(txtStock);

        panelFormulario.add(new JLabel("Categoría:"));
        cbCategoria = new JComboBox<>(new String[]{"ALIMENTO", "GENERAL", "TECNOLOGIA"});
        panelFormulario.add(cbCategoria);

        JButton btnAgregar = new JButton("Agregar Producto");
        btnAgregar.addActionListener(this::agregarProducto);
        panelFormulario.add(btnAgregar);

        JButton btnRefrescar = new JButton("Actualizar Lista");
        btnRefrescar.addActionListener(e -> cargarProductos());
        panelFormulario.add(btnRefrescar);

        add(panelFormulario, BorderLayout.NORTH);

        // ================================
        // 🔹 PANEL CENTRAL: TABLA
        // ================================
        modeloTabla = new DefaultTableModel(new Object[]{"Código", "Nombre", "Categoría", "Stock", "Precio"}, 0);
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Productos"));
        add(scroll, BorderLayout.CENTER);

        cargarProductos();
    }

    // ====================================================
    // 🔸 MÉTODO: AGREGAR PRODUCTO
    // ====================================================
    private void agregarProducto(ActionEvent e) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String categoria = cbCategoria.getSelectedItem().toString();
        String precioTxt = txtPrecio.getText().trim();
        String stockTxt = txtStock.getText().trim();

        // Validaciones básicas
        if (codigo.isEmpty() || nombre.isEmpty() || precioTxt.isEmpty() || stockTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor completa todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precio;
        int stock;

        try {
            precio = Double.parseDouble(precioTxt);
            stock = Integer.parseInt(stockTxt);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio o stock inválido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear producto según categoría
        Producto nuevo = null;
        switch (categoria.toUpperCase()) {
            case "ALIMENTO":
                nuevo = new ProductoAlimento(codigo, nombre, stock, precio, "2026-12-31");
                break;
            case "GENERAL":
                nuevo = new ProductoGeneral(codigo, nombre, stock, precio, "Plástico");
                break;
            case "TECNOLOGIA":
                nuevo = new ProductoTecnologia(codigo, nombre, stock, precio, 12);
                break;
        }

        if (nuevo != null) {
            Sistema.getListaProductos().agregarProducto(nuevo);
            Sistema.getRegistroBitacora().registrarEvento("PRODUCTO", "Agregado " + nombre);
            JOptionPane.showMessageDialog(this, "✅ Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarProductos();
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Error al crear producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ====================================================
    // 🔸 MÉTODO: CARGAR PRODUCTOS A LA TABLA
    // ====================================================
    private void cargarProductos() {
        modeloTabla.setRowCount(0); // limpiar tabla
        for (Producto p : Sistema.getListaProductos().getProductos()) {
            modeloTabla.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCategoria(),
                    p.getStock(),
                    "Q " + p.getPrecio()
            });
        }
    }

    // ====================================================
    // 🔸 MÉTODO: LIMPIAR FORMULARIO
    // ====================================================
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        cbCategoria.setSelectedIndex(0);
    }
}


