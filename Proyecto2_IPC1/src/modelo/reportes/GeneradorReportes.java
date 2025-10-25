package modelo.reportes;

import modelo.estructuras.*;
import modelo.bitacora.RegistroBitacora;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Genera reportes HTML del sistema (productos, usuarios, bitácora, etc.)
 */
public class GeneradorReportes {

    private static final String RUTA_BASE = "reportes/";

    // =========================================================
    // 🔹 Reporte de Productos
    // =========================================================
    public static void generarReporteProductos(ListaProductos lista, RegistroBitacora bitacora) {
        String archivo = RUTA_BASE + "reporte_productos.html";

        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write("<html><head><meta charset='UTF-8'><title>Reporte de Productos</title>");
            writer.write("<style>table{width:100%;border-collapse:collapse;}th,td{border:1px solid #ccc;padding:8px;text-align:left;}th{background:#e8e8e8;}</style>");
            writer.write("</head><body>");
            writer.write("<h2>📦 Reporte de Productos</h2>");
            writer.write("<table><tr><th>Código</th><th>Nombre</th><th>Categoría</th><th>Precio</th><th>Stock</th></tr>");

            for (int i = 0; i < lista.getCantidad(); i++) {
                var p = lista.getProducto(i);
                writer.write(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%.2f</td><td>%d</td></tr>",
                        p.getCodigo(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()));
            }

            writer.write("</table>");
            writer.write("<p><i>Generado el " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "</i></p>");
            writer.write("</body></html>");

            bitacora.registrarEvento("REPORTE", "Se generó el reporte de productos");
            System.out.println("✅ Reporte de productos generado en: " + archivo);

        } catch (IOException e) {
            System.err.println("❌ Error al generar reporte de productos: " + e.getMessage());
        }
    }

    // =========================================================
    // 🔹 Reporte de Bitácora
    // =========================================================
    public static void generarReporteBitacora(RegistroBitacora bitacora) {
        String archivo = RUTA_BASE + "reporte_bitacora.html";

        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write("<html><head><meta charset='UTF-8'><title>Reporte de Bitácora</title>");
            writer.write("<style>table{width:100%;border-collapse:collapse;}th,td{border:1px solid #ccc;padding:8px;text-align:left;}th{background:#e8e8e8;}</style>");
            writer.write("</head><body>");
            writer.write("<h2>🧾 Reporte de Bitácora del Sistema</h2>");
            writer.write("<table><tr><th>Fecha</th><th>Hora</th><th>Tipo</th><th>Descripción</th></tr>");

            for (int i = 0; i < bitacora.getCantidad(); i++) {
                var e = bitacora.getEventos()[i];
                writer.write(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                        e.getFecha(), e.getHora(), e.getTipo(), e.getDescripcion()));
            }

            writer.write("</table>");
            writer.write("<p><i>Generado el " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "</i></p>");
            writer.write("</body></html>");

            bitacora.registrarEvento("REPORTE", "Se generó el reporte de bitácora");
            System.out.println("✅ Reporte de bitácora generado en: " + archivo);

        } catch (IOException e) {
            System.err.println("❌ Error al generar reporte de bitácora: " + e.getMessage());
        }
    }
}
