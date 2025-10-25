package vista.gui;

import modelo.Sistema;
import modelo.productos.Producto;
import modelo.productos.ProductoAlimento;
import modelo.productos.ProductoGeneral;
import modelo.productos.ProductoTecnologia;
import modelo.usuarios.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelProductos extends JPanel {

    private JTextField txtCodigo, txtNombre, txtCategoria, txtPrecio, txtStock;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private final Usuario usuario;

    public PanelProductos(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));

        // Panel superior con formulario
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Agregar Producto"));

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Categoría:"));
        txtCategoria = new JTextField();
        panelFormulario.add(txtCategoria);

        panelFormulario.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panelFormulario.add(txtStock);

        JButton btnAgregar = new JButton("Agregar Producto");
        btnAgregar.addActionListener(this::agregarProducto);
        panelFormulario.add(btnAgregar);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla inferior para mostrar productos
        modeloTabla = new DefaultTableModel(new String[]{"Código", "Nombre", "Categoría", "Precio", "Stock"}, 0);
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaProductos);
        add(scroll, BorderLayout.CENTER);

        cargarProductos();
    }

    // ============================================================
    // 🔹 Método principal para agregar un producto
    // ============================================================
    private void agregarProducto(ActionEvent e) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precioTxt = txtPrecio.getText().trim();
        String stockTxt = txtStock.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || categoria.isEmpty() || precioTxt.isEmpty() || stockTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Debes llenar todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double precio = Double.parseDouble(precioTxt);
            int stock = Integer.parseInt(stockTxt);
            Producto nuevo;

            // 🔹 Pedimos datos adicionales según la categoría
            switch (categoria.toLowerCase()) {
                case "tecnologia" -> {
                    String mesesTxt = JOptionPane.showInputDialog(this, "Ingrese los meses de garantía:");
                    int mesesGarantia = Integer.parseInt(mesesTxt);
                    nuevo = new ProductoTecnologia(codigo, nombre, stock, precio, mesesGarantia);
                }
                case "alimento" -> {
                    String fechaExpiracion = JOptionPane.showInputDialog(this, "Ingrese la fecha de expiración (dd/mm/aaaa):");
                    nuevo = new ProductoAlimento(codigo, nombre, stock, precio, fechaExpiracion);
                }
                default -> {
                    String material = JOptionPane.showInputDialog(this, "Ingrese el material del producto:");
                    nuevo = new ProductoGeneral(codigo, nombre, stock, precio, material);
                }
            }

            // 🔹 Intentamos agregarlo al sistema
            if (Sistema.getListaProductos().agregarProducto(nuevo)) {
                Sistema.getRegistroBitacora().registrarEvento("PRODUCTO", "Agregado producto: " + nombre);
                cargarProductos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "✅ Producto agregado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "❌ El código ya existe o no hay espacio disponible.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingrese números válidos para precio y stock.");
        }
    }

    // ============================================================
    // 🔹 Cargar todos los productos a la tabla
    // ============================================================
    private void cargarProductos() {
        modeloTabla.setRowCount(0);
        Producto[] productos = Sistema.getListaProductos().getProductos();

        if (productos != null) {
            for (Producto p : productos) {
                if (p != null) {
                    modeloTabla.addRow(new Object[]{
                            p.getCodigo(), p.getNombre(), p.getCategoria(),
                            p.getPrecio(), p.getStock()
                    });
                }
            }
        }
    }

    // ============================================================
    // 🔹 Limpiar campos
    // ============================================================
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
    }
}




