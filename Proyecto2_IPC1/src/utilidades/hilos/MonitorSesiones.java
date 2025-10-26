package utilidades.hilos;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hilo que monitorea las sesiones activas del sistema
 * Se actualiza cada 10 segundos
 */
public class MonitorSesiones extends Thread {

    private boolean ejecutando;
    private int usuariosActivos;
    private String ultimaActividad;

    public MonitorSesiones() {
        this.ejecutando = true;
        this.usuariosActivos = 0;
        this.ultimaActividad = "Sin actividad";
        this.setName("Monitor-Sesiones");
        this.setDaemon(true); // Se cierra automáticamente cuando termina el programa
    }

    @Override
    public void run() {
        System.out.println("✓ Monitor de Sesiones iniciado");

        while (ejecutando) {
            try {
                // Actualizar timestamp
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String timestamp = sdf.format(new Date());

                // Mostrar información en consola
                System.out.println("\n┌─────────────────────────────────────────────┐");
                System.out.println("│ 👥 MONITOR DE SESIONES ACTIVAS             │");
                System.out.println("├─────────────────────────────────────────────┤");
                System.out.println(String.format("│ Usuarios Activos: %-24d│", usuariosActivos));
                System.out.println(String.format("│ Última Actividad: %-24s│", ultimaActividad));
                System.out.println(String.format("│ Timestamp: %-31s│", timestamp));
                System.out.println("└─────────────────────────────────────────────┘");

                // Esperar 10 segundos
                Thread.sleep(10000);

            } catch (InterruptedException e) {
                System.out.println("⚠ Monitor de Sesiones interrumpido");
                break;
            }
        }

        System.out.println("✗ Monitor de Sesiones detenido");
    }

    /**
     * Incrementa el contador de usuarios activos
     */
    public void incrementarUsuarios() {
        usuariosActivos++;
        actualizarActividad("Usuario conectado");
    }

    /**
     * Decrementa el contador de usuarios activos
     */
    public void decrementarUsuarios() {
        if (usuariosActivos > 0) {
            usuariosActivos--;
        }
        actualizarActividad("Usuario desconectado");
    }

    /**
     * Actualiza la última actividad registrada
     */
    public void actualizarActividad(String actividad) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        this.ultimaActividad = actividad + " (" + sdf.format(new Date()) + ")";
    }

    /**
     * Detiene el hilo
     */
    public void detener() {
        this.ejecutando = false;
        this.interrupt();
    }

    // Getters
    public int getUsuariosActivos() {
        return usuariosActivos;
    }

    public String getUltimaActividad() {
        return ultimaActividad;
    }
}
