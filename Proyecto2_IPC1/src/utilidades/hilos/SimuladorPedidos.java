package utilidades.hilos;

import utilidades.SistemaArchivos;
import modelo.Pedido;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hilo que monitorea los pedidos pendientes
 * Se actualiza cada 8 segundos
 */
public class SimuladorPedidos extends Thread {

    private boolean ejecutando;
    private int pedidosPendientes;

    public SimuladorPedidos() {
        this.ejecutando = true;
        this.pedidosPendientes = 0;
        this.setName("Simulador-Pedidos");
        this.setDaemon(true);
    }

    @Override
    public void run() {
        System.out.println("✓ Simulador de Pedidos iniciado");

        while (ejecutando) {
            try {
                // Actualizar contador desde el sistema de archivos
                actualizarContador();

                // Timestamp actual
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String timestamp = sdf.format(new Date());

                // Mostrar información en consola
                System.out.println("\n┌─────────────────────────────────────────────┐");
                System.out.println("│ 📦 SIMULADOR DE PEDIDOS PENDIENTES         │");
                System.out.println("├─────────────────────────────────────────────┤");
                System.out.println(String.format("│ Pedidos Pendientes: %-23d│", pedidosPendientes));
                System.out.println(String.format("│ Estado: %-35s│", pedidosPendientes > 0 ? "Procesando..." : "Sin pedidos"));
                System.out.println(String.format("│ Timestamp: %-31s│", timestamp));
                System.out.println("└─────────────────────────────────────────────┘");

                // Esperar 8 segundos
                Thread.sleep(8000);

            } catch (InterruptedException e) {
                System.out.println("⚠ Simulador de Pedidos interrumpido");
                break;
            }
        }

        System.out.println("✗ Simulador de Pedidos detenido");
    }

    /**
     * Actualiza el contador de pedidos pendientes desde el sistema
     */
    private void actualizarContador() {
        try {
            if (SistemaArchivos.existenPedidos()) {
                Pedido[] pedidos = SistemaArchivos.cargarPedidos();

                if (pedidos != null) {
                    int contador = 0;
                    for (Pedido p : pedidos) {
                        if (p.estaPendiente()) {
                            contador++;
                        }
                    }
                    pedidosPendientes = contador;
                }
            } else {
                pedidosPendientes = 0;
            }
        } catch (Exception e) {
            // Error silencioso para no interrumpir el hilo
        }
    }

    /**
     * Incrementa manualmente el contador (cuando se crea un pedido)
     */
    public void incrementarPedidos() {
        pedidosPendientes++;
    }

    /**
     * Decrementa manualmente el contador (cuando se confirma un pedido)
     */
    public void decrementarPedidos() {
        if (pedidosPendientes > 0) {
            pedidosPendientes--;
        }
    }

    /**
     * Detiene el hilo
     */
    public void detener() {
        this.ejecutando = false;
        this.interrupt();
    }

    // Getter
    public int getPedidosPendientes() {
        return pedidosPendientes;
    }
}
