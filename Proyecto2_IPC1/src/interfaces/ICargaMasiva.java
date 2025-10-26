package interfaces;

/**
 * Interface que define operaciones para carga masiva de datos desde CSV
 *
 * @param <T> Tipo de objeto que se cargará
 */
public interface ICargaMasiva<T> {

    /**
     * Carga datos desde un archivo CSV
     * @param rutaArchivo Ruta del archivo CSV
     * @return Array con los objetos cargados
     */
    T[] cargarDesdeCSV(String rutaArchivo);

    /**
     * Exporta datos a un archivo CSV
     * @param rutaArchivo Ruta donde se guardará el archivo
     * @param datos Datos a exportar
     * @return true si se exportó exitosamente
     */
    boolean exportarACSV(String rutaArchivo, T[] datos);

    /**
     * Valida el formato de una línea del CSV
     * @param linea Línea a validar
     * @return true si el formato es correcto
     */
    boolean validarLineaCSV(String linea);

    /**
     * Obtiene el encabezado del archivo CSV
     * @return Array con los nombres de las columnas
     */
    String[] obtenerEncabezadoCSV();

    /**
     * Convierte un objeto a formato CSV
     * @param objeto Objeto a convertir
     * @return String en formato CSV
     */
    String convertirACSV(T objeto);

    /**
     * Convierte una línea CSV a objeto
     * @param lineaCSV Línea en formato CSV
     * @return Objeto creado o null si hay error
     */
    T convertirDesdeCSV(String lineaCSV);

    /**
     * Obtiene el número de líneas procesadas en la última operación
     * @return Cantidad de líneas
     */
    int obtenerLineasProcesadas();

    /**
     * Obtiene el número de errores en la última operación
     * @return Cantidad de errores
     */
    int obtenerErrores();

    /**
     * Obtiene los mensajes de error de la última operación
     * @return Array con mensajes de error
     */
    String[] obtenerMensajesError();
}
