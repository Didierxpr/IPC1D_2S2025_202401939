import controlador.ControladorSistema;
import modelo.Administrador;
import utilidades.SistemaArchivos;
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
            boolean modoOscuro = false; // 🔁 cambia a true para modo oscuro

            if (modoOscuro) {
                FlatDarkLaf.setup();
                UIManager.put("Button.arc", 10);
            } else {
                FlatLightLaf.setup();
                UIManager.put("Button.arc", 10);
            }

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
        // 🧠 VERIFICAR / RECREAR ADMIN IPC1D
        // ================================
        try {
            Administrador[] admins = SistemaArchivos.cargarAdministradores();

            boolean necesitaActualizar = false;

            if (admins == null || admins.length == 0) {
                necesitaActualizar = true;
            } else {
                Administrador admin = admins[0];
                if (!"IPC1D".equals(admin.getContrasenia())) {
                    necesitaActualizar = true;
                }
            }

            if (necesitaActualizar) {
                Administrador adminNuevo = new Administrador(
                        "admin",
                        "Administrador del Sistema",
                        "Masculino",
                        "IPC1D"
                );

                Administrador[] nuevoArray = { adminNuevo };
                SistemaArchivos.guardarAdministradores(nuevoArray);
                controladorSistema.recargarUsuarios();

                System.out.println("✅ Administrador");
            } else {
                System.out.println("🟢 Administrador existente válido (IPC1D)");
            }

        } catch (Exception e) {
            System.err.println("❌ Error al verificar o crear el administrador:");
            e.printStackTrace();
        }

        // ================================
        // 🪟 ABRIR VENTANA DE LOGIN
        // ================================
        SwingUtilities.invokeLater(() -> {
            new VentanaLogin(controladorSistema).setVisible(true);
        });
    }
}









