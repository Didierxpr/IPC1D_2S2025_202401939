package interfaces;

/**
 * Interface que define operaciones para cálculo de estadísticas
 * Usada por los reportes y el dashboard del sistema
 */
public interface IEstadisticas {

    /**
     * Calcula el total de ventas en un período
     * @param fechaInicio Fecha de inicio (DD/MM/YYYY)
     * @param fechaFin Fecha de fin (DD/MM/YYYY)
     * @return Total de ventas
     */
    double calcularTotalVentas(String fechaInicio, String fechaFin);

    /**
     * Obtiene el producto más vendido
     * @return Código del producto más vendido
     */
    String obtenerProductoMasVendido();

    /**
     * Obtiene el producto menos vendido
     * @return Código del producto menos vendido
     */
    String obtenerProductoMenosVendido();

    /**
     * Calcula el promedio de ventas por día
     * @param dias Cantidad de días a considerar
     * @return Promedio de ventas
     */
    double calcularPromedioVentasDiarias(int dias);

    /**
     * Obtiene la cantidad de clientes activos
     * @param dias Días a considerar para determinar si está activo
     * @return Cantidad de clientes activos
     */
    int contarClientesActivos(int dias);

    /**
     * Obtiene la cantidad de productos con stock crítico
     * @return Cantidad de productos
     */
    int contarProductosStockCritico();

    /**
     * Obtiene la cantidad de productos con stock bajo
     * @return Cantidad de productos
     */
    int contarProductosStockBajo();

    /**
     * Calcula los ingresos por categoría
     * @param categoria Categoría a consultar
     * @return Total de ingresos de la categoría
     */
    double calcularIngresosPorCategoria(String categoria);

    /**
     * Obtiene el vendedor con más ventas confirmadas
     * @return Código del vendedor
     */
    String obtenerVendedorDestacado();

    /**
     * Calcula el valor total del inventario
     * @return Valor total
     */
    double calcularValorInventario();
}