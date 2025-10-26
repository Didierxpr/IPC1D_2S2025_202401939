package interfaces;

/**
 * Interface que define las operaciones de persistencia de datos
 * Implementada por las clases que manejan archivos y serialización
 *
 * @param <T> Tipo de objeto que se persistirá
 */
public interface IPersistencia<T> {

    /**
     * Guarda los datos en el sistema de persistencia
     * @param datos Datos a guardar
     * @return true si se guardó exitosamente
     */
    boolean guardar(T[] datos);

    /**
     * Carga los datos desde el sistema de persistencia
     * @return Array con los datos cargados o null si hay error
     */
    T[] cargar();

    /**
     * Verifica si existen datos guardados
     * @return true si existen datos
     */
    boolean existenDatos();

    /**
     * Elimina todos los datos guardados
     * @return true si se eliminó exitosamente
     */
    boolean eliminarDatos();

    /**
     * Obtiene la ruta del archivo de persistencia
     * @return Ruta del archivo
     */
    String obtenerRutaArchivo();

    /**
     * Crea un respaldo de los datos actuales
     * @param nombreRespaldo Nombre del archivo de respaldo
     * @return true si se creó el respaldo exitosamente
     */
    boolean crearRespaldo(String nombreRespaldo);

    /**
     * Restaura los datos desde un respaldo
     * @param nombreRespaldo Nombre del archivo de respaldo
     * @return true si se restauró exitosamente
     */
    boolean restaurarRespaldo(String nombreRespaldo);
}
