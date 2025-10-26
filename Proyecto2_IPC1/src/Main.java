import controlador.ControladorSistema;
import vista.VentanaLogin;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;

/**
 * Clase principal del sistema Sancarlista Shop
 * Versión con Interfaz Gráfica
 */
public class Main {

    private static final String SECCION = "D";

    public static void main(String[] args) {
        // ================================
        // 🎨 CONFIGURAR LOOK & FEEL (FlatLaf)
        // ================================

        try {
            // 🌓 Puedes cambiar fácilmente entre:
            // FlatLightLaf -> tema claro moderno
            // FlatDarkLaf  -> tema oscuro elegante

            boolean modoOscuro = false; // 🔁 cambia a true para modo oscuro

            if (modoOscuro) {
                FlatDarkLaf.setup();
                UIManager.put("Button.arc", 10); // Bordes redondeados
            } else {
                FlatLightLaf.setup();
                UIManager.put("Button.arc", 10);
            }

            // Colores base globales (opcional)
            UIManager.put("Component.focusWidth", 1);
            UIManager.put("Button.focusedBackground", null);
            UIManager.put("Button.hoverBackground", null);

        } catch (Exception e) {
            System.err.println("⚠️ No se pudo aplicar FlatLaf, usando LAF por defecto");
            e.printStackTrace();
        }

        // ================================
        // 🚀 INICIALIZAR SISTEMA
        // ================================
        ControladorSistema controladorSistema = new ControladorSistema();

        if (!controladorSistema.inicializarSistema(SECCION)) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al inicializar el sistema",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // ================================
        // 🪟 ABRIR VENTANA DE LOGIN
        // ================================
        SwingUtilities.invokeLater(() -> {
            new VentanaLogin(controladorSistema).setVisible(true);
        });
    }

    
}







