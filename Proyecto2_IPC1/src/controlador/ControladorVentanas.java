package controlador;

import javax.swing.*;
import modelo.usuarios.Usuario;
import vista.gui.*;

public class ControladorVentanas {

    // ===========================================
    // 🔹 Ventana: Gestión de Productos
    // ===========================================
    public static void abrirVentanaProductos(Usuario usuario) {
        JFrame frame = new JFrame("Gestión de Productos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new vista.gui.PanelProductos(usuario));  // 👈 importante
        frame.setVisible(true);
    }


    // ===========================================
    // 🔹 Ventana: Gestión de Usuarios
    // ===========================================
    public static void abrirVentanaUsuarios(Usuario usuario) {
        JFrame frame = new JFrame("Gestión de Usuarios");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new vista.gui.PanelUsuarios());
        frame.setVisible(true);
    }

// =============================================================
// 🌐 Ventana de Reportes
// =============================================================
    public static void abrirVentanaReportes(modelo.usuarios.Usuario usuario) {
        JFrame frame = new JFrame("📊 Reportes del Sistema");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new vista.gui.PanelReportes());
        frame.setVisible(true);
    }

    // ===========================================
    // 🔹 Ventana: Bitácora de Eventos
    // ===========================================
    public static void abrirVentanaBitacora(Usuario usuario) {
        JFrame frame = new JFrame("Bitácora del Sistema");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new vista.gui.PanelBitacora());
        frame.setVisible(true);
    }


    // ===========================================
    // 🔹 Ventana: Registro de Ventas
    // ===========================================
    public static void abrirVentanaVentas(Usuario usuario) {
        JFrame frame = new JFrame("Registrar Venta");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new PanelVentas(usuario));
        frame.setVisible(true);
    }


    // ===========================================
    // 🔹 Ventana: Catálogo de Productos
    // ===========================================
    public static void abrirVentanaCatalogo(Usuario usuario) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Catálogo de Productos");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            frame.setContentPane(new PanelCatalogo(usuario));
            frame.setVisible(true);
        });
    }
    public static void abrirVentanaAcerca() {
        JFrame frame = new JFrame("👨‍🎓 Información del Estudiante");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new vista.gui.PanelAcercaDe());
        frame.setVisible(true);
    }
    public static void abrirVentanaClienteShop(Usuario cliente) {
        JFrame f = new JFrame("Tienda del Cliente");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(800, 500);
        f.setLocationRelativeTo(null);
        f.setContentPane(new vista.gui.PanelCliente(cliente));
        f.setVisible(true);
    }

}


