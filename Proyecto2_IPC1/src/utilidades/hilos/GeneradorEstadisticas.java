package utilidades.hilos;

import utilidades.SistemaArchivos;
import modelo.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hilo que genera estadísticas en vivo del sistema
 * Se actualiza cada 15 segundos
 */
public class GeneradorEstadisticas extends Thread {

    private boolean ejecutando;
    private int ventasDelDia;
    private int productosRegistrados;
    private int clientesActivos;
    private double ingresosDelDia;

    public GeneradorEstadisticas() {
        this.ejecutando = true;
        this.ventasDelDia = 0;
        this.productosRegistrados = 0;
        this.clientesActivos = 0;
        this.ingresosDelDia = 0.0;
        this.setName("Generador-Estadisticas");
        this.setDaemon(true);
    }

    @Override
    public void run() {
        System.out.println("✓ Generador de Estadísticas iniciado");

        while (ejecutando) {
            try {
                // Actualizar estadísticas desde el sistema
                actualizarEstadisticas();

                // Timestamp actual
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String timestamp = sdf.format(new Date());

                // Mostrar información en consola
                System.out.println("\n┌─────────────────────────────────────────────┐");
                System.out.println("│ 📊 ESTADÍSTICAS EN VIVO                    │");
                System.out.println("├─────────────────────────────────────────────┤");
                System.out.println(String.format("│ Ventas del día: %-27d│", ventasDelDia));
                System.out.println(String.format("│ Productos registrados: %-20d│", productosRegistrados));
                System.out.println(String.format("│ Clientes activos: %-23d│", clientesActivos));
                System.out.println(String.format("│ Ingresos del día: $%-24.2f│", ingresosDelDia));
                System.out.println(String.format("│ Timestamp: %-31s│", timestamp));
                System.out.println("└─────────────────────────────────────────────┘");

                // Esperar 15 segundos
                Thread.sleep(15000);

            } catch (InterruptedException e) {
                System.out.println("⚠ Generador de Estadísticas interrumpido");
                break;
            }
        }

        System.out.println("✗ Generador de Estadísticas detenido");
    }

    /**
     * Actualiza las estadísticas desde el sistema de archivos
     */
    private void actualizarEstadisticas() {
        try {
            // Contar productos
            if (SistemaArchivos.existenProductos()) {
                Producto[] productos = SistemaArchivos.cargarProductos();
                productosRegistrados = productos != null ? productos.length : 0;
            }

            // Contar clientes
            if (SistemaArchivos.existenClientes()) {
                Cliente[] clientes = SistemaArchivos.cargarClientes();
                if (clientes != null) {
                    clientesActivos = 0;
                    for (Cliente c : clientes) {
                        if (c.getComprasRealizadas() > 0) {
                            clientesActivos++;
                        }
                    }
                }
            }

            // Contar ventas del día e ingresos
            if (SistemaArchivos.existenPedidos()) {
                Pedido[] pedidos = SistemaArchivos.cargarPedidos();
                if (pedidos != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String fechaHoy = sdf.format(new Date());

                    ventasDelDia = 0;
                    ingresosDelDia = 0.0;

                    for (Pedido p : pedidos) {
                        if (p.estaConfirmado() && p.getFechaPedido().equals(fechaHoy)) {
                            ventasDelDia++;
                            ingresosDelDia += p.getTotalPedido();
                        }
                    }
                }
            }

        } catch (Exception e) {
            // Error silencioso para no interrumpir el hilo
        }
    }

    /**
     * Detiene el hilo
     */
    public void detener() {
        this.ejecutando = false;
        this.interrupt();
    }

    // Getters
    public int getVentasDelDia() {
        return ventasDelDia;
    }

    public int getProductosRegistrados() {
        return productosRegistrados;
    }

    public int getClientesActivos() {
        return clientesActivos;
    }

    public double getIngresosDelDia() {
        return ingresosDelDia;
    }
}
