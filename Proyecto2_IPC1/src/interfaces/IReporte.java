package interfaces;

/**
 * Interface que define las operaciones para generación de reportes
 * Implementada por las clases que generan reportes en PDF
 */
public interface IReporte {

    /**
     * Genera el reporte en formato PDF
     * @param nombreArchivo Nombre del archivo a generar (sin extensión)
     * @return true si se generó exitosamente, false si falló
     */
    boolean generarReportePDF(String nombreArchivo);

    /**
     * Genera el reporte en formato CSV
     * @param nombreArchivo Nombre del archivo a generar (sin extensión)
     * @return true si se generó exitosamente, false si falló
     */
    boolean generarReporteCSV(String nombreArchivo);

    /**
     * Obtiene el título del reporte
     * @return Título del reporte
     */
    String obtenerTituloReporte();

    /**
     * Obtiene la descripción del reporte
     * @return Descripción del reporte
     */
    String obtenerDescripcionReporte();

    /**
     * Valida si hay datos suficientes para generar el reporte
     * @return true si hay datos, false si no hay datos
     */
    boolean tieneDatos();

    /**
     * Obtiene la fecha de generación del reporte en formato DD_MM_YYYY_HH_mm_ss
     * @return String con la fecha formateada
     */
    String obtenerFechaGeneracion();
}
