package vista.gui;

import modelo.usuarios.Usuario;
import modelo.usuarios.Administrador;
import modelo.usuarios.Vendedor;
import modelo.usuarios.Cliente;
import controlador.ControladorVentanas;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private Usuario usuario;

    public VentanaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        inicializar();
    }

    private void inicializar() {
        setTitle("Sistema de Ventas - Usuario: " + usuario.getNombre() + " (" + usuario.getTipo() + ")");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // =============================================================
        // 🔹 MENÚ ADMINISTRADOR
        // =============================================================
        if (usuario instanceof Administrador) {
            panel.add(crearBoton("👥 Gestionar Usuarios", () -> ControladorVentanas.abrirVentanaUsuarios(usuario)));
            panel.add(crearBoton("📦 Gestionar Productos", () -> ControladorVentanas.abrirVentanaProductos(usuario)));
            panel.add(crearBoton("📊 Ver Reportes", () -> ControladorVentanas.abrirVentanaReportes(usuario)));
            panel.add(crearBoton("🧾 Ver Bitácora", () -> ControladorVentanas.abrirVentanaBitacora(usuario)));
            panel.add(crearBoton("Datos del estudiante", () -> ControladorVentanas.abrirVentanaAcerca()));
        }

        // =============================================================
        // 🔹 MENÚ VENDEDOR
        // =============================================================
        else if (usuario instanceof Vendedor) {
            panel.add(crearBoton("🛒 Registrar Venta", () -> ControladorVentanas.abrirVentanaVentas(usuario)));
            panel.add(crearBoton("📦 Gestionar Productos", () -> ControladorVentanas.abrirVentanaProductos(usuario)));
            panel.add(crearBoton("📊 Reportes", () -> ControladorVentanas.abrirVentanaReportes(usuario)));
        }

        // =============================================================
        // 🔹 MENÚ CLIENTE
        // =============================================================
        else if (usuario instanceof Cliente) {
            panel.add(crearBoton("🛍️ Ver Catálogo", () -> ControladorVentanas.abrirVentanaCatalogo(usuario)));
            panel.add(crearBoton("🧾 Ver Mis Compras", () ->
                    JOptionPane.showMessageDialog(this, "Aquí se mostrará el historial de compras.", "Información", JOptionPane.INFORMATION_MESSAGE)));
        }

        // Botón de cierre de sesión (común)
        panel.add(crearBoton("🚪 Cerrar Sesión", this::cerrarSesion));

        add(panel);
    }

    private JButton crearBoton(String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.addActionListener(e -> accion.run());
        return boton;
    }

    private void cerrarSesion() {
        dispose();
        new VentanaLogin().setVisible(true);
    }
}





