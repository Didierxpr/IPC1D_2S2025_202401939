package modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa un pedido realizado por un cliente
 */
public class Pedido implements Serializable {

    // Atributos del pedido
    private String codigoPedido;
    private String codigoCliente;
    private String nombreCliente;
    private String codigoVendedor;
    private String fechaPedido; // DD/MM/YYYY
    private String horaPedido; // HH:mm:ss
    private String estado; // "PENDIENTE", "CONFIRMADO", "CANCELADO"
    private double totalPedido;

    // Arrays para almacenar los productos del pedido
    private String[] codigosProductos;
    private String[] nombresProductos;
    private int[] cantidades;
    private double[] preciosUnitarios;
    private double[] subtotales;

    /**
     * Constructor vacío
     */
    public Pedido() {
        this.codigosProductos = new String[0];
        this.nombresProductos = new String[0];
        this.cantidades = new int[0];
        this.preciosUnitarios = new double[0];
        this.subtotales = new double[0];
        this.totalPedido = 0.0;
        this.estado = "PENDIENTE";

        // Inicializar fecha y hora actual
        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
        Date ahora = new Date();
        this.fechaPedido = sdfFecha.format(ahora);
        this.horaPedido = sdfHora.format(ahora);
    }

    /**
     * Constructor con parámetros
     * @param codigoPedido Código único del pedido
     * @param codigoCliente Código del cliente
     * @param nombreCliente Nombre del cliente
     * @param codigoVendedor Código del vendedor asignado
     */
    public Pedido(String codigoPedido, String codigoCliente, String nombreCliente, String codigoVendedor) {
        this();
        this.codigoPedido = codigoPedido;
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.codigoVendedor = codigoVendedor;
    }

    // ==================== GETTERS Y SETTERS ====================

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getCodigoVendedor() {
        return codigoVendedor;
    }

    public void setCodigoVendedor(String codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(String horaPedido) {
        this.horaPedido = horaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public String[] getCodigosProductos() {
        return codigosProductos;
    }

    public void setCodigosProductos(String[] codigosProductos) {
        this.codigosProductos = codigosProductos;
    }

    public String[] getNombresProductos() {
        return nombresProductos;
    }

    public void setNombresProductos(String[] nombresProductos) {
        this.nombresProductos = nombresProductos;
    }

    public int[] getCantidades() {
        return cantidades;
    }

    public void setCantidades(int[] cantidades) {
        this.cantidades = cantidades;
    }

    public double[] getPreciosUnitarios() {
        return preciosUnitarios;
    }

    public void setPreciosUnitarios(double[] preciosUnitarios) {
        this.preciosUnitarios = preciosUnitarios;
    }

    public double[] getSubtotales() {
        return subtotales;
    }

    public void setSubtotales(double[] subtotales) {
        this.subtotales = subtotales;
    }

    // ==================== MÉTODOS PARA GESTIONAR PRODUCTOS ====================

    /**
     * Agrega un producto al pedido
     * @param codigoProducto Código del producto
     * @param nombreProducto Nombre del producto
     * @param cantidad Cantidad solicitada
     * @param precioUnitario Precio unitario del producto
     */
    public void agregarProducto(String codigoProducto, String nombreProducto, int cantidad, double precioUnitario) {
        // Verificar si el producto ya existe en el pedido
        int indiceExistente = buscarProducto(codigoProducto);

        if (indiceExistente != -1) {
            // Si existe, actualizar la cantidad
            cantidades[indiceExistente] += cantidad;
            subtotales[indiceExistente] = cantidades[indiceExistente] * preciosUnitarios[indiceExistente];
        } else {
            // Si no existe, agregarlo
            codigosProductos = agregarString(codigosProductos, codigoProducto);
            nombresProductos = agregarString(nombresProductos, nombreProducto);
            cantidades = agregarInt(cantidades, cantidad);
            preciosUnitarios = agregarDouble(preciosUnitarios, precioUnitario);
            subtotales = agregarDouble(subtotales, cantidad * precioUnitario);
        }

        calcularTotal();
    }

    /**
     * Elimina un producto del pedido
     * @param codigoProducto Código del producto a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminarProducto(String codigoProducto) {
        int indice = buscarProducto(codigoProducto);

        if (indice != -1) {
            codigosProductos = eliminarString(codigosProductos, indice);
            nombresProductos = eliminarString(nombresProductos, indice);
            cantidades = eliminarInt(cantidades, indice);
            preciosUnitarios = eliminarDouble(preciosUnitarios, indice);
            subtotales = eliminarDouble(subtotales, indice);

            calcularTotal();
            return true;
        }

        return false;
    }

    /**
     * Actualiza la cantidad de un producto en el pedido
     * @param codigoProducto Código del producto
     * @param nuevaCantidad Nueva cantidad
     * @return true si se actualizó correctamente
     */
    public boolean actualizarCantidad(String codigoProducto, int nuevaCantidad) {
        int indice = buscarProducto(codigoProducto);

        if (indice != -1 && nuevaCantidad > 0) {
            cantidades[indice] = nuevaCantidad;
            subtotales[indice] = nuevaCantidad * preciosUnitarios[indice];
            calcularTotal();
            return true;
        }

        return false;
    }

    /**
     * Busca un producto en el pedido por su código
     * @param codigoProducto Código a buscar
     * @return Índice del producto o -1 si no existe
     */
    private int buscarProducto(String codigoProducto) {
        for (int i = 0; i < codigosProductos.length; i++) {
            if (codigosProductos[i].equals(codigoProducto)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Calcula el total del pedido
     */
    private void calcularTotal() {
        totalPedido = 0.0;
        for (int i = 0; i < subtotales.length; i++) {
            totalPedido += subtotales[i];
        }
    }

    /**
     * Obtiene la cantidad de productos diferentes en el pedido
     * @return Cantidad de productos
     */
    public int getCantidadProductos() {
        return codigosProductos.length;
    }

    /**
     * Obtiene la cantidad total de items en el pedido
     * @return Suma de todas las cantidades
     */
    public int getCantidadTotalItems() {
        int total = 0;
        for (int i = 0; i < cantidades.length; i++) {
            total += cantidades[i];
        }
        return total;
    }

    /**
     * Verifica si el pedido está vacío
     * @return true si no tiene productos
     */
    public boolean estaVacio() {
        return codigosProductos.length == 0;
    }

    // ==================== MÉTODOS DE ESTADO ====================

    /**
     * Confirma el pedido
     */
    public void confirmar() {
        this.estado = "CONFIRMADO";
    }

    /**
     * Cancela el pedido
     */
    public void cancelar() {
        this.estado = "CANCELADO";
    }

    /**
     * Verifica si el pedido está pendiente
     * @return true si está pendiente
     */
    public boolean estaPendiente() {
        return "PENDIENTE".equals(this.estado);
    }

    /**
     * Verifica si el pedido está confirmado
     * @return true si está confirmado
     */
    public boolean estaConfirmado() {
        return "CONFIRMADO".equals(this.estado);
    }

    // ==================== MÉTODOS DE INFORMACIÓN ====================

    /**
     * Obtiene un resumen del pedido
     * @return String con información resumida
     */
    public String obtenerResumen() {
        return "Pedido: " + codigoPedido +
                " | Cliente: " + nombreCliente +
                " | Fecha: " + fechaPedido +
                " | Total: $" + String.format("%.2f", totalPedido) +
                " | Estado: " + estado;
    }

    /**
     * Obtiene el detalle completo del pedido
     * @return String con toda la información
     */
    public String obtenerDetalleCompleto() {
        StringBuilder detalle = new StringBuilder();
        detalle.append("╔════════════════════════════════════════════════════╗\n");
        detalle.append("║               DETALLE DE PEDIDO                    ║\n");
        detalle.append("╠════════════════════════════════════════════════════╣\n");
        detalle.append(String.format("║ Código pedido: %-32s║\n", codigoPedido));
        detalle.append(String.format("║ Cliente: %-39s║\n", nombreCliente));
        detalle.append(String.format("║ Código cliente: %-31s║\n", codigoCliente));
        detalle.append(String.format("║ Fecha: %-41s║\n", fechaPedido));
        detalle.append(String.format("║ Hora: %-42s║\n", horaPedido));
        detalle.append(String.format("║ Estado: %-40s║\n", estado));
        detalle.append("╠════════════════════════════════════════════════════╣\n");
        detalle.append("║                   PRODUCTOS                        ║\n");
        detalle.append("╠════════════════════════════════════════════════════╣\n");

        for (int i = 0; i < codigosProductos.length; i++) {
            detalle.append(String.format("║ %d. %-45s║\n", (i + 1), nombresProductos[i]));
            detalle.append(String.format("║    Código: %-39s║\n", codigosProductos[i]));
            detalle.append(String.format("║    Cantidad: %-17d Precio: $%-9.2f║\n",
                    cantidades[i], preciosUnitarios[i]));
            detalle.append(String.format("║    Subtotal: $%-33.2f║\n", subtotales[i]));
            detalle.append("║                                                    ║\n");
        }

        detalle.append("╠════════════════════════════════════════════════════╣\n");
        detalle.append(String.format("║ TOTAL: $%-40.2f║\n", totalPedido));
        detalle.append("╚════════════════════════════════════════════════════╝");

        return detalle.toString();
    }

    // ==================== MÉTODOS AUXILIARES PARA ARRAYS ====================

    private String[] agregarString(String[] array, String elemento) {
        String[] nuevo = new String[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            nuevo[i] = array[i];
        }
        nuevo[array.length] = elemento;
        return nuevo;
    }

    private String[] eliminarString(String[] array, int indice) {
        String[] nuevo = new String[array.length - 1];
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != indice) {
                nuevo[j++] = array[i];
            }
        }
        return nuevo;
    }

    private int[] agregarInt(int[] array, int elemento) {
        int[] nuevo = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            nuevo[i] = array[i];
        }
        nuevo[array.length] = elemento;
        return nuevo;
    }

    private int[] eliminarInt(int[] array, int indice) {
        int[] nuevo = new int[array.length - 1];
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != indice) {
                nuevo[j++] = array[i];
            }
        }
        return nuevo;
    }

    private double[] agregarDouble(double[] array, double elemento) {
        double[] nuevo = new double[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            nuevo[i] = array[i];
        }
        nuevo[array.length] = elemento;
        return nuevo;
    }

    private double[] eliminarDouble(double[] array, int indice) {
        double[] nuevo = new double[array.length - 1];
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != indice) {
                nuevo[j++] = array[i];
            }
        }
        return nuevo;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "codigo='" + codigoPedido + '\'' +
                ", cliente='" + nombreCliente + '\'' +
                ", fecha='" + fechaPedido + '\'' +
                ", total=" + totalPedido +
                ", estado='" + estado + '\'' +
                ", productos=" + codigosProductos.length +
                '}';
    }
}