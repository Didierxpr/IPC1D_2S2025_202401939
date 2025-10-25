package vista.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

import modelo.Sistema;
import modelo.usuarios.*;

public class PanelUsuarios extends JPanel {

    private final JTextField txtCodigo;
    private final JTextField txtNombre;
    private final JTextField txtPassword;
    private final JComboBox<String> cbTipo;
    private final JComboBox<String> cbGenero;
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    public PanelUsuarios() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // =====================================================
        // 🔹 PANEL SUPERIOR: FORMULARIO
        // =====================================================
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Agregar Usuario"));
        panelFormulario.setBackground(new Color(250, 250, 250));

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelFormulario.add(txtPassword);

        panelFormulario.add(new JLabel("Género:"));
        cbGenero = new JComboBox<>(new String[]{"M", "F"});
        panelFormulario.add(cbGenero);

        panelFormulario.add(new JLabel("Tipo de usuario:"));
        cbTipo = new JComboBox<>(new String[]{"ADMIN", "VENDEDOR", "CLIENTE"});
        panelFormulario.add(cbTipo);

        JButton btnAgregar = new JButton("Agregar Usuario");
        btnAgregar.addActionListener(this::agregarUsuario);
        panelFormulario.add(btnAgregar);

        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> cargarUsuarios());
        panelFormulario.add(btnActualizar);

        add(panelFormulario, BorderLayout.NORTH);

        // =====================================================
        // 🔹 PANEL CENTRAL: TABLA
        // =====================================================
        modeloTabla = new DefaultTableModel(new Object[]{"Código", "Nombre", "Género", "Tipo"}, 0);
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Usuarios"));
        add(scroll, BorderLayout.CENTER);

        // Cargar usuarios al inicio
        cargarUsuarios();
    }

    // =====================================================
    // 🔸 MÉTODO: AGREGAR USUARIO
    // =====================================================
    private void agregarUsuario(ActionEvent e) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String contrasena = txtPassword.getText().trim();
        String genero = cbGenero.getSelectedItem().toString();
        String tipo = cbTipo.getSelectedItem().toString();

        if (codigo.isEmpty() || nombre.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor completa todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario nuevo;

        switch (tipo.toUpperCase()) {
            case "ADMIN":
                nuevo = new Administrador(codigo, nombre, genero, contrasena);
                break;
            case "VENDEDOR":
                nuevo = new Vendedor(codigo, nombre, genero, contrasena);
                break;
            case "CLIENTE":
                nuevo = new Cliente(codigo, nombre, genero, contrasena);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo de usuario no reconocido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }

        Sistema.getListaUsuarios().agregarUsuario(nuevo);
        Sistema.getRegistroBitacora().registrarEvento("USUARIO", "Agregado " + nombre + " (" + tipo + ")");
        JOptionPane.showMessageDialog(this, "✅ Usuario agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        limpiarCampos();
        cargarUsuarios();
    }

    // =====================================================
    // 🔸 MÉTODO: CARGAR USUARIOS EN TABLA
    // =====================================================
    private void cargarUsuarios() {
        for (Usuario u : Sistema.getListaUsuarios().getUsuarios()) {
            if (u == null) continue;
        }
        modeloTabla.setRowCount(0);
        for (Usuario u : Sistema.getListaUsuarios().getUsuarios()) {
            modeloTabla.addRow(new Object[]{
                    u.getCodigo(),
                    u.getNombre(),
                    u.getGenero(),
                    u.getTipo()
            });
        }
    }

    // =====================================================
    // 🔸 MÉTODO: LIMPIAR CAMPOS
    // =====================================================
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPassword.setText("");
        cbGenero.setSelectedIndex(0);
        cbTipo.setSelectedIndex(0);
    }
}
