package vista.gui;

import modelo.Sistema;
import modelo.pedidos.Pedido;
import modelo.productos.Producto;
import modelo.usuarios.Cliente;
import modelo.usuarios.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelVentas extends JPanel {

    private JComboBox<String> cbClientes;
    private JComboBox<String> cbProductos;
    private JTextField txtCantidad;
    private JLabel lblTotal;
    private JButton btnRegistrarVenta;
    private Usuario vendedor;

    public PanelVentas(Usuario vendedor) {
        this.vendedor = vendedor;
        inicializarComponentes();
    }

    // =========================================================
    // 🔹 Configuración inicial del panel
    // =========================================================
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("🛒 Registrar Nueva Venta", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBackground(Color.WHITE);

        // Campos
        panelForm.add(new JLabel("Cliente:"));
        cbClientes = new JComboBox<>();
        cargarClientes();
        panelForm.add(cbClientes);

        panelForm.add(new JLabel("Producto:"));
        cbProductos = new JComboBox<>();
        cargarProductos();
        panelForm.add(cbProductos);

        panelForm.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelForm.add(txtCantidad);

        panelForm.add(new JLabel("Total:"));
        lblTotal = new JLabel("Q0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelForm.add(lblTotal);

        add(panelForm, BorderLayout.CENTER);

        // Botón registrar
        btnRegistrarVenta = new JButton("Registrar Venta");
        btnRegistrarVenta.setBackground(new Color(34, 139, 34));
        btnRegistrarVenta.setForeground(Color.WHITE);
        btnRegistrarVenta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrarVenta.setFocusPainted(false);
        btnRegistrarVenta.addActionListener(this::registrarVenta);

        add(btnRegistrarVenta, BorderLayout.SOUTH);
    }

    // =========================================================
    // 🔹 Cargar listas dinámicas
    // =========================================================
    private void cargarClientes() {
        cbClientes.removeAllItems();
        for (Usuario u : Sistema.getListaUsuarios().getUsuarios()) {
            if (u instanceof Cliente) {
                cbClientes.addItem(u.getCodigo() + " - " + u.getNombre());
            }
        }
    }

    private void cargarProductos() {
        cbProductos.removeAllItems();
        for (Producto p : Sistema.getListaProductos().getProductos()) {
            if (p != null && p.getStock() > 0) {
                cbProductos.addItem(p.getCodigo() + " - " + p.getNombre());
            }
        }
    }

    // =========================================================
    // 🔹 Lógica de registro de venta
    // =========================================================
    private void registrarVenta(ActionEvent e) {
        String clienteSel = (String) cbClientes.getSelectedItem();
        String productoSel = (String) cbProductos.getSelectedItem();

        if (clienteSel == null || productoSel == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Debes seleccionar cliente y producto.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) throw new NumberFormatException();

            Cliente cliente = (Cliente) Sistema.getListaUsuarios().buscarUsuario(clienteSel.split(" - ")[0]);
            Producto producto = Sistema.getListaProductos().buscarPorCodigo(productoSel.split(" - ")[0]);

            if (producto == null || cliente == null) {
                JOptionPane.showMessageDialog(this, "❌ No se encontró el cliente o producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (producto.getStock() < cantidad) {
                JOptionPane.showMessageDialog(this, "❌ No hay suficiente stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double total = cantidad * producto.getPrecio();
            lblTotal.setText("Q" + String.format("%.2f", total));

            // Crear pedido y actualizar stock
            Pedido pedido = new Pedido(cliente, producto, cantidad, total, vendedor);
            Sistema.getListaPedidos().agregarPedido(pedido);
            producto.setStock(producto.getStock() - cantidad);

            // Registrar en bitácora
            Sistema.getRegistroBitacora().registrarEvento(
                    "VENTA",
                    "Vendedor " + vendedor.getNombre() + " vendió " + cantidad + "x " +
                            producto.getNombre() + " a " + cliente.getNombre() + " (Q" + total + ")"
            );

            JOptionPane.showMessageDialog(this, "✅ Venta registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            cargarProductos(); // refresca stock
            txtCantidad.setText("");
            lblTotal.setText("Q0.00");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingrese una cantidad válida.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}


