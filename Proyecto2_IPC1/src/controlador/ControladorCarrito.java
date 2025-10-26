package controlador;

import modelo.*;
import utilidades.Vectores;

/**
 * Controlador para gestión del carrito de compras
 * Cada cliente tiene su propio carrito temporal
 */
public class ControladorCarrito {

    private String[] codigosProductos;
    private String[] nombresProductos;
    private int[] cantidades;
    private double[] preciosUnitarios;
    private double[] subtotales;

    private Cliente cliente;
    private ControladorProducto controladorProducto;

    public ControladorCarrito(Cliente cliente, Usuario usuarioActual) {
        this.cliente = cliente;
        this.controladorProducto = new ControladorProducto(usuarioActual);
        inicializarCarrito();
    }

    /**
     * Inicializa los arrays del carrito vacíos
     */
    private void inicializarCarrito() {
        codigosProductos = new String[0];
        nombresProductos = new String[0];
        cantidades = new int[0];
        preciosUnitarios = new double[0];
        subtotales = new double[0];
    }

    /**
     * Agrega un producto al carrito
     */
    public boolean agregarProducto(String codigoProducto, int cantidad) {
        if (cantidad <= 0) {
            System.err.println("La cantidad debe ser mayor que cero");
            return false;
        }

        Producto producto = controladorProducto.buscarPorCodigo(codigoProducto);

        if (producto == null) {
            System.err.println("Producto no encontrado");
            return false;
        }

        if (!producto.hayStock(cantidad)) {
            System.err.println("Stock insuficiente. Disponible: " + producto.getStockDisponible());
            return false;
        }

        // Verificar si el producto ya está en el carrito
        int indiceExistente = buscarProductoEnCarrito(codigoProducto);

        if (indiceExistente != -1) {
            // Actualizar cantidad existente
            int nuevaCantidad = cantidades[indiceExistente] + cantidad;

            if (!producto.hayStock(nuevaCantidad)) {
                System.err.println("Stock insuficiente para la cantidad total");
                return false;
            }

            cantidades[indiceExistente] = nuevaCantidad;
            subtotales[indiceExistente] = nuevaCantidad * preciosUnitarios[indiceExistente];
        } else {
            // Agregar nuevo producto
            codigosProductos = Vectores.agregarString(codigosProductos, producto.getCodigo());
            nombresProductos = Vectores.agregarString(nombresProductos, producto.getNombre());
            cantidades = agregarInt(cantidades, cantidad);
            preciosUnitarios = agregarDouble(preciosUnitarios, producto.getPrecio());
            subtotales = agregarDouble(subtotales, cantidad * producto.getPrecio());
        }

        return true;
    }

    /**
     * Elimina un producto del carrito
     */
    public boolean eliminarProducto(String codigoProducto) {
        int indice = buscarProductoEnCarrito(codigoProducto);

        if (indice == -1) {
            System.err.println("Producto no está en el carrito");
            return false;
        }

        codigosProductos = Vectores.eliminarString(codigosProductos, indice);
        nombresProductos = Vectores.eliminarString(nombresProductos, indice);
        cantidades = eliminarInt(cantidades, indice);
        preciosUnitarios = eliminarDouble(preciosUnitarios, indice);
        subtotales = eliminarDouble(subtotales, indice);

        return true;
    }

    /**
     * Actualiza la cantidad de un producto en el carrito
     */
    public boolean actualizarCantidad(String codigoProducto, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            return eliminarProducto(codigoProducto);
        }

        int indice = buscarProductoEnCarrito(codigoProducto);

        if (indice == -1) {
            System.err.println("Producto no está en el carrito");
            return false;
        }

        Producto producto = controladorProducto.buscarPorCodigo(codigoProducto);

        if (producto == null || !producto.hayStock(nuevaCantidad)) {
            System.err.println("Stock insuficiente");
            return false;
        }

        cantidades[indice] = nuevaCantidad;
        subtotales[indice] = nuevaCantidad * preciosUnitarios[indice];

        return true;
    }

    /**
     * Vacía el carrito completamente
     */
    public void vaciarCarrito() {
        inicializarCarrito();
    }

    /**
     * Verifica si el carrito está vacío
     */
    public boolean estaVacio() {
        return codigosProductos.length == 0;
    }

    /**
     * Obtiene la cantidad de productos diferentes en el carrito
     */
    public int getCantidadProductos() {
        return codigosProductos.length;
    }

    /**
     * Obtiene la cantidad total de items en el carrito
     */
    public int getCantidadTotalItems() {
        int total = 0;
        for (int i = 0; i < cantidades.length; i++) {
            total += cantidades[i];
        }
        return total;
    }

    /**
     * Calcula el total del carrito
     */
    public double calcularTotal() {
        double total = 0.0;
        for (int i = 0; i < subtotales.length; i++) {
            total += subtotales[i];
        }
        return total;
    }

    /**
     * Convierte el carrito en un pedido
     */
    public Pedido generarPedido(String codigoPedido) {
        if (estaVacio()) {
            System.err.println("El carrito está vacío");
            return null;
        }

        Pedido pedido = new Pedido(
                codigoPedido,
                cliente.getCodigo(),
                cliente.getNombre(),
                cliente.getVendedorAsignado()
        );

        // Agregar todos los productos del carrito al pedido
        for (int i = 0; i < codigosProductos.length; i++) {
            pedido.agregarProducto(
                    codigosProductos[i],
                    nombresProductos[i],
                    cantidades[i],
                    preciosUnitarios[i]
            );
        }

        return pedido;
    }

    /**
     * Busca un producto en el carrito por su código
     */
    private int buscarProductoEnCarrito(String codigoProducto) {
        for (int i = 0; i < codigosProductos.length; i++) {
            if (codigosProductos[i].equals(codigoProducto)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Obtiene un resumen del carrito
     */
    public String obtenerResumen() {
        if (estaVacio()) {
            return "Carrito vacío";
        }

        StringBuilder resumen = new StringBuilder();
        resumen.append("╔════════════════════════════════════════════════════╗\n");
        resumen.append("║              CARRITO DE COMPRAS                    ║\n");
        resumen.append("╠════════════════════════════════════════════════════╣\n");

        for (int i = 0; i < codigosProductos.length; i++) {
            resumen.append(String.format("║ %d. %-45s║\n", (i + 1), nombresProductos[i]));
            resumen.append(String.format("║    Cantidad: %-5d Precio: $%-10.2f      ║\n",
                    cantidades[i], preciosUnitarios[i]));
            resumen.append(String.format("║    Subtotal: $%-33.2f║\n", subtotales[i]));
            resumen.append("║                                                    ║\n");
        }

        resumen.append("╠════════════════════════════════════════════════════╣\n");
        resumen.append(String.format("║ Total items: %-37d║\n", getCantidadTotalItems()));
        resumen.append(String.format("║ TOTAL: $%-40.2f║\n", calcularTotal()));
        resumen.append("╚════════════════════════════════════════════════════╝");

        return resumen.toString();
    }

    // Getters para acceder a los datos del carrito
    public String[] getCodigosProductos() {
        return codigosProductos;
    }

    public String[] getNombresProductos() {
        return nombresProductos;
    }

    public int[] getCantidades() {
        return cantidades;
    }

    public double[] getPreciosUnitarios() {
        return preciosUnitarios;
    }

    public double[] getSubtotales() {
        return subtotales;
    }

    // ==================== MÉTODOS AUXILIARES PARA ARRAYS ====================

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
}
