package utilidades.hilos;

/**
 * Gestor centralizado para todos los hilos del sistema
 * Controla el inicio y detención de los hilos de monitoreo
 */
public class GestorHilos {

    private MonitorSesiones monitorSesiones;
    private SimuladorPedidos simuladorPedidos;
    private GeneradorEstadisticas generadorEstadisticas;

    private boolean hilosActivos;

    public GestorHilos() {
        this.hilosActivos = false;
    }

    /**
     * Inicia todos los hilos del sistema
     */
    public void iniciarHilos() {
        if (hilosActivos) {
            System.out.println("⚠ Los hilos ya están activos");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║          INICIANDO HILOS DEL SISTEMA               ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");

        // Crear e iniciar hilos
        monitorSesiones = new MonitorSesiones();
        simuladorPedidos = new SimuladorPedidos();
        generadorEstadisticas = new GeneradorEstadisticas();

        monitorSesiones.start();
        simuladorPedidos.start();
        generadorEstadisticas.start();

        hilosActivos = true;

        System.out.println("\n✓ Todos los hilos están ejecutándose en segundo plano\n");
    }

    /**
     * Detiene todos los hilos del sistema
     */
    public void detenerHilos() {
        if (!hilosActivos) {
            System.out.println("⚠ Los hilos no están activos");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║          DETENIENDO HILOS DEL SISTEMA              ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");

        // Detener hilos
        if (monitorSesiones != null) {
            monitorSesiones.detener();
        }

        if (simuladorPedidos != null) {
            simuladorPedidos.detener();
        }

        if (generadorEstadisticas != null) {
            generadorEstadisticas.detener();
        }

        hilosActivos = false;

        System.out.println("\n✓ Todos los hilos han sido detenidos\n");
    }

    /**
     * Pausa la ejecución para permitir ver los hilos en acción
     */
    public void pausarParaVisualizacion(int segundos) {
        System.out.println("\n⏸ Pausando " + segundos + " segundos para visualizar hilos...\n");

        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            System.err.println("⚠ Pausa interrumpida");
        }
    }

    // ==================== MÉTODOS DE ACCESO A LOS HILOS ====================

    /**
     * Obtiene el monitor de sesiones
     */
    public MonitorSesiones getMonitorSesiones() {
        return monitorSesiones;
    }

    /**
     * Obtiene el simulador de pedidos
     */
    public SimuladorPedidos getSimuladorPedidos() {
        return simuladorPedidos;
    }

    /**
     * Obtiene el generador de estadísticas
     */
    public GeneradorEstadisticas getGeneradorEstadisticas() {
        return generadorEstadisticas;
    }

    /**
     * Verifica si los hilos están activos
     */
    public boolean isHilosActivos() {
        return hilosActivos;
    }

    // ==================== MÉTODOS DE UTILIDAD ====================

    /**
     * Notifica que un usuario inició sesión
     */
    public void notificarLogin() {
        if (monitorSesiones != null) {
            monitorSesiones.incrementarUsuarios();
        }
    }

    /**
     * Notifica que un usuario cerró sesión
     */
    public void notificarLogout() {
        if (monitorSesiones != null) {
            monitorSesiones.decrementarUsuarios();
        }
    }

    /**
     * Notifica que se creó un pedido
     */
    public void notificarNuevoPedido() {
        if (simuladorPedidos != null) {
            simuladorPedidos.incrementarPedidos();
        }

        if (monitorSesiones != null) {
            monitorSesiones.actualizarActividad("Nuevo pedido creado");
        }
    }

    /**
     * Notifica que se confirmó un pedido
     */
    public void notificarPedidoConfirmado() {
        if (simuladorPedidos != null) {
            simuladorPedidos.decrementarPedidos();
        }

        if (monitorSesiones != null) {
            monitorSesiones.actualizarActividad("Pedido confirmado");
        }
    }

    /**
     * Notifica actividad general del sistema
     */
    public void notificarActividad(String descripcion) {
        if (monitorSesiones != null) {
            monitorSesiones.actualizarActividad(descripcion);
        }
    }

    /**
     * Muestra el estado actual de todos los hilos
     */
    public void mostrarEstadoHilos() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║           ESTADO DE LOS HILOS                      ║");
        System.out.println("╠════════════════════════════════════════════════════╣");

        if (hilosActivos) {
            System.out.println("║ Monitor de Sesiones: " +
                    (monitorSesiones.isAlive() ? "✓ ACTIVO " : "✗ INACTIVO") + "                      ║");
            System.out.println("║ Simulador de Pedidos: " +
                    (simuladorPedidos.isAlive() ? "✓ ACTIVO" : "✗ INACTIVO") + "                     ║");
            System.out.println("║ Generador de Estadísticas: " +
                    (generadorEstadisticas.isAlive() ? "✓ ACTIVO" : "✗ INACTIVO") + "                ║");
        } else {
            System.out.println("║ Todos los hilos están detenidos                   ║");
        }

        System.out.println("╚════════════════════════════════════════════════════╝\n");
    }
}
