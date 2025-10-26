package controlador;

import modelo.*;
import utilidades.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controlador para gestión de movimientos de stock
 */
public class ControladorStock {

    private Stock[] movimientos;
    private Usuario usuarioActual;
    private ControladorProducto controladorProducto;

    public ControladorStock(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        this.controladorProducto = new ControladorProducto(usuarioActual);
        cargarMovimientos();
    }

    /**
     * Carga los movimientos de stock desde el sistema de archivos
     */
    private void cargarMovimientos() {
        movimientos = SistemaArchivos.cargarStock();
        if (movimientos == null) {
            movimientos = new Stock[0];
        }
    }

    /**
     * Guarda los movimientos en el sistema de archivos
     */
    private boolean guardarMovimientos() {
        return SistemaArchivos.guardarStock(movimientos);
    }

    /**
     * Agrega stock a un producto
     */
    public boolean agregarStock(String codigoProducto, int cantidad) {
        if (cantidad <= 0) {
            System.err.println("La cantidad debe ser mayor que cero");
            return false;
        }

        Producto producto = controladorProducto.buscarPorCodigo(codigoProducto);

        if (producto == null) {
            System.err.println("Producto no encontrado: " + codigoProducto);
            return false;
        }

        int stockAnterior = producto.getStockDisponible();
        producto.agregarStock(cantidad);
        int stockActual = producto.getStockDisponible();

        // Actualizar producto
        boolean resultadoProducto = controladorProducto.actualizar(codigoProducto, producto);

        if (!resultadoProducto) {
            return false;
        }

        // Crear movimiento de stock
        String codigoMovimiento = generarCodigoMovimiento();
        Stock movimiento = new Stock(
                codigoMovimiento,
                producto.getCodigo(),
                producto.getNombre(),
                usuarioActual.getCodigo(),
                usuarioActual.getNombre(),
                cantidad,
                stockAnterior,
                stockActual
        );

        movimientos = (Stock[]) Vectores.agregar(movimientos, movimiento);
        boolean resultadoMovimiento = guardarMovimientos();

        if (resultadoMovimiento && usuarioActual instanceof Vendedor) {
            registrarBitacora(Bitacora.agregarStock(
                    (Vendedor) usuarioActual,
                    codigoProducto,
                    cantidad
            ));
        }

        return resultadoMovimiento;
    }

    /**
     * Carga stock desde un archivo CSV
     */
    public boolean cargarStockCSV(String rutaArchivo) {
        String[] lineas = ManejadorArchivos.leerCSV(rutaArchivo, true);

        if (lineas == null || lineas.length == 0) {
            System.err.println("No se pudieron leer los datos del archivo");
            return false;
        }

        int exitosos = 0;
        int errores = 0;

        for (String linea : lineas) {
            if (linea.trim().isEmpty()) {
                continue;
            }

            String[] datos = ManejadorArchivos.parsearLineaCSV(linea);

            if (datos.length < 2) {
                System.err.println("Línea con formato incorrecto: " + linea);
                errores++;
                continue;
            }

            String codigoProducto = datos[0].trim();
            int cantidad;

            try {
                cantidad = Integer.parseInt(datos[1].trim());
            } catch (NumberFormatException e) {
                System.err.println("Cantidad inválida en línea: " + linea);
                errores++;
                continue;
            }

            if (agregarStock(codigoProducto, cantidad)) {
                exitosos++;
            } else {
                errores++;
            }
        }

        System.out.println("Carga completada: " + exitosos + " exitosos, " + errores + " errores");
        return exitosos > 0;
    }

    /**
     * Obtiene todos los movimientos de stock
     */
    public Stock[] obtenerTodosMovimientos() {
        return movimientos;
    }

    /**
     * Obtiene el historial de movimientos de un producto
     */
    public Stock[] obtenerHistorialProducto(String codigoProducto) {
        Stock[] resultado = new Stock[0];

        for (int i = 0; i < movimientos.length; i++) {
            if (movimientos[i].getCodigoProducto().equals(codigoProducto)) {
                resultado = (Stock[]) Vectores.agregar(resultado, movimientos[i]);
            }
        }

        return resultado;
    }

    /**
     * Obtiene los movimientos realizados por un vendedor
     */
    public Stock[] obtenerMovimientosPorVendedor(String codigoVendedor) {
        Stock[] resultado = new Stock[0];

        for (int i = 0; i < movimientos.length; i++) {
            if (movimientos[i].getCodigoVendedor().equals(codigoVendedor)) {
                resultado = (Stock[]) Vectores.agregar(resultado, movimientos[i]);
            }
        }

        return resultado;
    }

    /**
     * Exporta el historial de movimientos a CSV
     */
    public boolean exportarHistorialCSV(String rutaArchivo) {
        if (movimientos.length == 0) {
            System.err.println("No hay movimientos para exportar");
            return false;
        }

        String encabezado = "Fecha,Hora,Codigo Vendedor,Nombre Vendedor,Codigo Producto," +
                "Nombre Producto,Cantidad,Stock Anterior,Stock Actual,Tipo";

        String[] lineas = new String[movimientos.length];

        for (int i = 0; i < movimientos.length; i++) {
            lineas[i] = movimientos[i].toCSV();
        }

        return ManejadorArchivos.escribirCSV(rutaArchivo, encabezado, lineas);
    }

    /**
     * Genera un código único para movimiento
     */
    private String generarCodigoMovimiento() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        return "MOV-" + timestamp;
    }

    /**
     * Registra una entrada en la bitácora
     */
    private void registrarBitacora(Bitacora entrada) {
        ControladorBitacora controladorBitacora = new ControladorBitacora();
        controladorBitacora.registrar(entrada);
    }

    /**
     * Recarga los movimientos desde el sistema de archivos
     */
    public void recargar() {
        cargarMovimientos();
    }
}
