package interfaces;

/**
 * Interface que define operaciones para el sistema de notificaciones
 * Puede usarse para alertas de stock, productos próximos a caducar, etc.
 */
public interface INotificacion {

    /**
     * Envía una notificación al sistema
     * @param tipo Tipo de notificación (INFO, WARNING, ERROR, SUCCESS)
     * @param titulo Título de la notificación
     * @param mensaje Mensaje de la notificación
     */
    void enviarNotificacion(String tipo, String titulo, String mensaje);

    /**
     * Obtiene todas las notificaciones pendientes
     * @return Array con las notificaciones
     */
    String[] obtenerNotificacionesPendientes();

    /**
     * Marca una notificación como leída
     * @param indice Índice de la notificación
     * @return true si se marcó exitosamente
     */
    boolean marcarComoLeida(int indice);

    /**
     * Limpia todas las notificaciones
     * @return true si se limpiaron exitosamente
     */
    boolean limpiarNotificaciones();

    /**
     * Verifica si hay notificaciones pendientes
     * @return true si hay notificaciones sin leer
     */
    boolean hayNotificacionesPendientes();

    /**
     * Obtiene la cantidad de notificaciones pendientes
     * @return Cantidad de notificaciones
     */
    int contarNotificacionesPendientes();
}
