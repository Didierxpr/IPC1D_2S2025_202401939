package controlador;

import modelo.Sistema;
import modelo.bitacora.Bitacora;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Controlador para manejar la visualización y exportación
 * de la bitácora del sistema.
 */
public class ControladorBitacora {

    // =========================================================
    // 🔹 Mostrar bitácora en consola
    // =========================================================
    public void mostrarBitacora() {
        System.out.println("\n=== BITÁCORA DEL SISTEMA ===");

        var bitacora = Sistema.getRegistroBitacora();

        if (bitacora.getCantidad() == 0) {
            System.out.println("⚠️ No hay eventos registrados.");
            return;
        }

        System.out.printf("%-5s %-15s %-10s %-40s\n", "ID", "Fecha", "Tipo", "Descripción");
        System.out.println("---------------------------------------------------------------------");

        for (int i = 0; i < bitacora.getCantidad(); i++) {
            Bitacora evento = bitacora.getEvento(i);
            System.out.printf("%-5d %-15s %-10s %-40s\n",
                    (i + 1),
                    evento.getFecha(),
                    evento.getTipo(),
                    evento.getDescripcion());
        }

        Sistema.getRegistroBitacora().registrarEvento("BITÁCORA", "Visualización de eventos en consola");
    }

    // =========================================================
    // 🔹 Exportar bitácora a CSV
    // =========================================================
    public void exportarBitacoraCSV() {
        String ruta = "data/bitacora.csv";

        try (FileWriter fw = new FileWriter(ruta)) {
            fw.write("ID,Fecha,Tipo,Descripción\n");

            var bitacora = Sistema.getRegistroBitacora();

            for (int i = 0; i < bitacora.getCantidad(); i++) {
                Bitacora evento = bitacora.getEvento(i);
                fw.write((i + 1) + "," +
                        evento.getFecha() + "," +
                        evento.getTipo() + "," +
                        evento.getDescripcion() + "\n");
            }

            fw.flush();
            System.out.println("✅ Bitácora exportada correctamente en: " + ruta);

            Sistema.getRegistroBitacora().registrarEvento("BITÁCORA", "Exportación de bitácora a CSV");

        } catch (IOException e) {
            System.out.println("❌ Error al exportar bitácora: " + e.getMessage());
        }
    }

    // =========================================================
    // 🔹 Limpiar bitácora
    // =========================================================
    public void limpiarBitacora() {
        Sistema.getRegistroBitacora().limpiar();
        System.out.println("🧹 Bitácora limpiada correctamente.");
        Sistema.getRegistroBitacora().registrarEvento("BITÁCORA", "Bitácora limpiada manualmente");
    }
}

