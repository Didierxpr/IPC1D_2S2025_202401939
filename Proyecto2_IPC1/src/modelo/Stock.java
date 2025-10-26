package modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa un movimiento de stock (ingreso de inventario)
 */
public class Stock implements Serializable {

    // Atributos del movimiento de stock
    private String codigoMovimiento;
    private String codigoProducto;
    private String nombreProducto;
    private String codigoVendedor;
    private String nombreVendedor;
    private int cantidad;
    private String fechaIngreso; // DD/MM/YYYY
    private String horaIngreso; // HH:mm:ss
    private String tipoMovimiento; // "INGRESO", "VENTA", "AJUSTE"
    private int stockAnterior;
    private int stockActual;

    /**
     * Constructor vacío
     */
    public Stock() {
        // Inicializar fecha y hora actual
        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
        Date ahora = new Date();
        this.fechaIngreso = sdfFecha.format(ahora);
        this.horaIngreso = sdfHora.format(ahora);
        this.tipoMovimiento = "INGRESO";
    }

    /**
     * Constructor con parámetros
     * @param codigoMovimiento Código único del movimiento
     * @param codigoProducto Código del producto
     * @param nombreProducto Nombre del producto
     * @param codigoVendedor Código del vendedor responsable
     * @param nombreVendedor Nombre del vendedor
     * @param cantidad Cantidad ingresada
     * @param stockAnterior Stock antes del movimiento
     * @param stockActual Stock después del movimiento
     */
    public Stock(String codigoMovimiento, String codigoProducto, String nombreProducto,
                 String codigoVendedor, String nombreVendedor, int cantidad,
                 int stockAnterior, int stockActual) {
        this();
        this.codigoMovimiento = codigoMovimiento;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.codigoVendedor = codigoVendedor;
        this.nombreVendedor = nombreVendedor;
        this.cantidad = cantidad;
        this.stockAnterior = stockAnterior;
        this.stockActual = stockActual;
    }

    // ==================== GETTERS Y SETTERS ====================

    public String getCodigoMovimiento() {
        return codigoMovimiento;
    }

    public void setCodigoMovimiento(String codigoMovimiento) {
        this.codigoMovimiento = codigoMovimiento;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCodigoVendedor() {
        return codigoVendedor;
    }

    public void setCodigoVendedor(String codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(String horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getStockAnterior() {
        return stockAnterior;
    }

    public void setStockAnterior(int stockAnterior) {
        this.stockAnterior = stockAnterior;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    // ==================== MÉTODOS DE INFORMACIÓN ====================

    /**
     * Obtiene la diferencia de stock (puede ser positiva o negativa)
     * @return Diferencia entre stock actual y anterior
     */
    public int getDiferenciaStock() {
        return stockActual - stockAnterior;
    }

    /**
     * Obtiene un resumen del movimiento
     * @return String con información resumida
     */
    public String obtenerResumen() {
        return fechaIngreso + " " + horaIngreso + " | " +
                nombreVendedor + " | " +
                nombreProducto + " | " +
                "Cantidad: " + cantidad + " | " +
                "Stock: " + stockAnterior + " → " + stockActual;
    }

    /**
     * Obtiene el detalle completo del movimiento
     * @return String con toda la información
     */
    public String obtenerDetalleCompleto() {
        return "╔════════════════════════════════════════════════════╗\n" +
                "║          MOVIMIENTO DE INVENTARIO                  ║\n" +
                "╠════════════════════════════════════════════════════╣\n" +
                String.format("║ Código movimiento: %-28s║\n", codigoMovimiento) +
                String.format("║ Tipo: %-42s║\n", tipoMovimiento) +
                String.format("║ Fecha: %-41s║\n", fechaIngreso) +
                String.format("║ Hora: %-42s║\n", horaIngreso) +
                "╠════════════════════════════════════════════════════╣\n" +
                String.format("║ Producto: %-38s║\n", nombreProducto) +
                String.format("║ Código producto: %-31s║\n", codigoProducto) +
                String.format("║ Cantidad ingresada: %-27d║\n", cantidad) +
                "╠════════════════════════════════════════════════════╣\n" +
                String.format("║ Vendedor: %-38s║\n", nombreVendedor) +
                String.format("║ Código vendedor: %-31s║\n", codigoVendedor) +
                "╠════════════════════════════════════════════════════╣\n" +
                String.format("║ Stock anterior: %-31d║\n", stockAnterior) +
                String.format("║ Stock actual: %-33d║\n", stockActual) +
                String.format("║ Diferencia: %-35d║\n", getDiferenciaStock()) +
                "╚════════════════════════════════════════════════════╝";
    }

    /**
     * Convierte el movimiento a formato CSV
     * @return String en formato CSV
     */
    public String toCSV() {
        return fechaIngreso + "," +
                horaIngreso + "," +
                codigoVendedor + "," +
                nombreVendedor + "," +
                codigoProducto + "," +
                nombreProducto + "," +
                cantidad + "," +
                stockAnterior + "," +
                stockActual + "," +
                tipoMovimiento;
    }

    /**
     * Crea un movimiento desde formato CSV
     * @param linea Línea en formato CSV
     * @return Objeto Stock creado
     */
    public static Stock fromCSV(String linea) {
        String[] partes = linea.split(",");

        if (partes.length >= 10) {
            Stock stock = new Stock();
            stock.setFechaIngreso(partes[0].trim());
            stock.setHoraIngreso(partes[1].trim());
            stock.setCodigoVendedor(partes[2].trim());
            stock.setNombreVendedor(partes[3].trim());
            stock.setCodigoProducto(partes[4].trim());
            stock.setNombreProducto(partes[5].trim());
            stock.setCantidad(Integer.parseInt(partes[6].trim()));
            stock.setStockAnterior(Integer.parseInt(partes[7].trim()));
            stock.setStockActual(Integer.parseInt(partes[8].trim()));
            stock.setTipoMovimiento(partes[9].trim());

            return stock;
        }

        return null;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "codigo='" + codigoMovimiento + '\'' +
                ", producto='" + nombreProducto + '\'' +
                ", vendedor='" + nombreVendedor + '\'' +
                ", cantidad=" + cantidad +
                ", fecha='" + fechaIngreso + '\'' +
                ", tipo='" + tipoMovimiento + '\'' +
                '}';
    }
}
