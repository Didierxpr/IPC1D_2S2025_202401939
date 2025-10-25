package vista.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.Sistema;
import modelo.productos.Producto;
import modelo.usuarios.Cliente;
import modelo.usuarios.Usuario;
import modelo.pedidos.Pedido;

public class PanelCliente extends JPanel {

    private final Usuario cliente; // debe ser instancia de Cliente
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private final JTextField txtCantidad = new JTextField();

    public PanelCliente(Usuario cliente) {
        this.cliente = cliente;
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("🛒 Catálogo de Productos", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[]{"Código","Nombre","Categoría","Precio","Stock"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel sur = new JPanel(new GridLayout(2,3,8,8));
        sur.add(new JLabel("Cantidad:", SwingConstants.RIGHT));
        sur.add(txtCantidad);
        JButton btnComprar = new JButton("Comprar");
        sur.add(btnComprar);
        JButton btnRefrescar = new JButton("Refrescar");
        sur.add(new JLabel()); // filler
        sur.add(btnRefrescar);
        sur.add(new JLabel()); // filler
        add(sur, BorderLayout.SOUTH);

        cargarProductos();

        btnRefrescar.addActionListener(e -> cargarProductos());
        btnComprar.addActionListener(e -> comprarSeleccionado());
    }

    private void cargarProductos() {
        modelo.setRowCount(0);
        var lp = Sistema.getListaProductos();
        for (int i = 0; i < lp.getCantidad(); i++) {
            Producto p = lp.getProducto(i);
            if (p != null) {
                modelo.addRow(new Object[]{
                        p.getCodigo(), p.getNombre(), p.getCategoria(),
                        p.getPrecio(), p.getStock()
                });
            }
        }
    }

    private void comprarSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cod = String.valueOf(modelo.getValueAt(row, 0));
        Producto p = Sistema.getListaProductos().buscarPorCodigo(cod);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (p.getStock() < cantidad) {
            JOptionPane.showMessageDialog(this, "Stock insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // total = precio * cantidad (usa tu precio real del producto)
        double total = p.getPrecio() * cantidad;

        // descuento de stock
        p.setStock(p.getStock() - cantidad);

        // crear pedido (usa tu forma de generar código)
        String codigoPedido = "PED" + (Sistema.getListaPedidos().getCantidad() + 1);
        Pedido ped = new Pedido(codigoPedido, p, (Cliente) cliente, cantidad, total);
        Sistema.getListaPedidos().agregarPedido(ped);

        // bitácora
        Sistema.getRegistroBitacora().registrarEvento("VENTA",
                "Cliente " + cliente.getCodigo() + " compró " + cantidad + "x " + p.getNombre() +
                        " (Q" + String.format("%.2f", total) + ")");

        JOptionPane.showMessageDialog(this, "Compra realizada.\nCódigo: " + codigoPedido, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        cargarProductos();
        txtCantidad.setText("");
    }
}

