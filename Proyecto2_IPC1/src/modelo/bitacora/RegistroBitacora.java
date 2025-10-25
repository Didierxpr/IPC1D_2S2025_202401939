package modelo.bitacora;

import java.io.Serializable;

public class RegistroBitacora implements Serializable {

    private Bitacora[] eventos;
    private int contador;

    public RegistroBitacora(int capacidad) {
        eventos = new Bitacora[capacidad];
        contador = 0;
    }

    // =========================================================
    // 🔹 Registrar nuevo evento
    // =========================================================
    public void registrarEvento(String tipo, String descripcion) {
        if (contador >= eventos.length) {
            System.out.println("⚠️ Capacidad máxima de bitácora alcanzada.");
            return;
        }
        eventos[contador++] = new Bitacora(tipo, descripcion);
    }

    // =========================================================
    // 🔹 Obtener evento por índice
    // =========================================================
    public Bitacora getEvento(int index) {
        if (index >= 0 && index < contador) {
            return eventos[index];
        }
        return null;
    }

    // =========================================================
    // 🔹 Obtener lista completa de eventos
    // =========================================================
    public Bitacora[] getEventos() {
        return eventos;
    }

    // =========================================================
    // 🔹 Cantidad de eventos registrados
    // =========================================================
    public int getCantidad() {
        return contador;
    }

    // =========================================================
    // 🔹 Mostrar en consola
    // =========================================================
    public void mostrarEventos() {
        System.out.println("\n=== BITÁCORA DEL SISTEMA ===");
        if (contador == 0) {
            System.out.println("⚠️ No hay eventos registrados.");
            return;
        }
        for (int i = 0; i < contador; i++) {
            System.out.println(eventos[i]);
        }
    }

    // =========================================================
    // 🔹 Limpiar bitácora (opcional)
    // =========================================================
    public void limpiar() {
        for (int i = 0; i < contador; i++) {
            eventos[i] = null;
        }
        contador = 0;
    }
    public void exportarBitacoraLegible() {
        try (java.io.FileWriter fw = new java.io.FileWriter("data/reportes/bitacora_legible.html")) {
            fw.write("<html><head><meta charset='UTF-8'><title>Bitácora del Sistema</title></head><body>");
            fw.write("<h1>📜 Bitácora del Sistema</h1>");
            fw.write("<table border='1' cellspacing='0' cellpadding='5'>");
            fw.write("<tr><th>Fecha y Hora</th><th>Tipo</th><th>Descripción</th></tr>");
            for (var evento : eventos) {
                if (evento != null) {
                    fw.write("<tr><td>" + evento.getFecha() + "</td><td>"
                            + evento.getHora() + "</td><td>"
                            + evento.getTipo() + "</td><td>"
                            + evento.getDescripcion() + "</td></tr>");
                }
            }
            fw.write("</table></body></html>");
            fw.flush();
            System.out.println("✅ Bitácora exportada como bitacora_legible.html");
        } catch (Exception e) {
            System.out.println("❌ Error al exportar bitácora: " + e.getMessage());
        }
    }

}



