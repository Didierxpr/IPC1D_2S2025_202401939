package utilidades;

import modelo.*;

/**
 * Clase centralizada para gestionar todos los archivos del sistema
 * Proporciona acceso fácil a la serialización de todos los tipos de datos
 */
public class SistemaArchivos {

    // Instancias de serialización para cada tipo de dato
    private static Serializacion<Administrador> serializacionAdmins;
    private static Serializacion<Vendedor> serializacionVendedores;
    private static Serializacion<Cliente> serializacionClientes;
    private static Serializacion<Producto> serializacionProductos;
    private static Serializacion<Pedido> serializacionPedidos;
    private static Serializacion<Stock> serializacionStock;
    private static Serializacion<Bitacora> serializacionBitacora;

    // Nombres de archivos
    private static final String ARCHIVO_ADMINS = "administradores";
    private static final String ARCHIVO_VENDEDORES = "vendedores";
    private static final String ARCHIVO_CLIENTES = "clientes";
    private static final String ARCHIVO_PRODUCTOS = "productos";
    private static final String ARCHIVO_PEDIDOS = "pedidos";
    private static final String ARCHIVO_STOCK = "stock";
    private static final String ARCHIVO_BITACORA = "bitacora";

    /**
     * Inicializa el sistema de archivos
     */
    public static void inicializar() {
        serializacionAdmins = new Serializacion<>(ARCHIVO_ADMINS);
        serializacionVendedores = new Serializacion<>(ARCHIVO_VENDEDORES);
        serializacionClientes = new Serializacion<>(ARCHIVO_CLIENTES);
        serializacionProductos = new Serializacion<>(ARCHIVO_PRODUCTOS);
        serializacionPedidos = new Serializacion<>(ARCHIVO_PEDIDOS);
        serializacionStock = new Serializacion<>(ARCHIVO_STOCK);
        serializacionBitacora = new Serializacion<>(ARCHIVO_BITACORA);

        System.out.println("Sistema de archivos inicializado correctamente");
    }

    // ==================== ADMINISTRADORES ====================

    public static boolean guardarAdministradores(Administrador[] admins) {
        return serializacionAdmins.guardar(admins);
    }

    public static Administrador[] cargarAdministradores() {
        return serializacionAdmins.cargar();
    }

    public static boolean existenAdministradores() {
        return serializacionAdmins.existenDatos();
    }

    // ==================== VENDEDORES ====================

    public static boolean guardarVendedores(Vendedor[] vendedores) {
        return serializacionVendedores.guardar(vendedores);
    }

    public static Vendedor[] cargarVendedores() {
        return serializacionVendedores.cargar();
    }

    public static boolean existenVendedores() {
        return serializacionVendedores.existenDatos();
    }

    // ==================== CLIENTES ====================

    public static boolean guardarClientes(Cliente[] clientes) {
        return serializacionClientes.guardar(clientes);
    }

    public static Cliente[] cargarClientes() {
        return serializacionClientes.cargar();
    }

    public static boolean existenClientes() {
        return serializacionClientes.existenDatos();
    }

    // ==================== PRODUCTOS ====================

    public static boolean guardarProductos(Producto[] productos) {
        return serializacionProductos.guardar(productos);
    }

    public static Producto[] cargarProductos() {
        return serializacionProductos.cargar();
    }

    public static boolean existenProductos() {
        return serializacionProductos.existenDatos();
    }

    // ==================== PEDIDOS ====================

    public static boolean guardarPedidos(Pedido[] pedidos) {
        return serializacionPedidos.guardar(pedidos);
    }

    public static Pedido[] cargarPedidos() {
        return serializacionPedidos.cargar();
    }

    public static boolean existenPedidos() {
        return serializacionPedidos.existenDatos();
    }

    // ==================== STOCK ====================

    public static boolean guardarStock(Stock[] stock) {
        return serializacionStock.guardar(stock);
    }

    public static Stock[] cargarStock() {
        return serializacionStock.cargar();
    }

    public static boolean existenStock() {
        return serializacionStock.existenDatos();
    }

    // ==================== BITÁCORA ====================

    public static boolean guardarBitacora(Bitacora[] bitacora) {
        return serializacionBitacora.guardar(bitacora);
    }

    public static Bitacora[] cargarBitacora() {
        return serializacionBitacora.cargar();
    }

    public static boolean existenBitacora() {
        return serializacionBitacora.existenDatos();
    }

    // ==================== OPERACIONES GENERALES ====================

    /**
     * Guarda todos los datos del sistema
     * @param admins Administradores
     * @param vendedores Vendedores
     * @param clientes Clientes
     * @param productos Productos
     * @param pedidos Pedidos
     * @param stock Stock
     * @param bitacora Bitácora
     * @return true si todo se guardó exitosamente
     */
    public static boolean guardarTodo(Administrador[] admins, Vendedor[] vendedores,
                                      Cliente[] clientes, Producto[] productos,
                                      Pedido[] pedidos, Stock[] stock, Bitacora[] bitacora) {
        boolean exitoso = true;

        exitoso &= guardarAdministradores(admins);
        exitoso &= guardarVendedores(vendedores);
        exitoso &= guardarClientes(clientes);
        exitoso &= guardarProductos(productos);
        exitoso &= guardarPedidos(pedidos);
        exitoso &= guardarStock(stock);
        exitoso &= guardarBitacora(bitacora);

        if (exitoso) {
            System.out.println("✓ Todos los datos guardados exitosamente");
        } else {
            System.err.println("✗ Algunos datos no se pudieron guardar");
        }

        return exitoso;
    }

    /**
     * Crea respaldos de todos los archivos
     * @param sufijo Sufijo para identificar el respaldo
     * @return true si todos los respaldos se crearon exitosamente
     */
    public static boolean crearRespaldoCompleto(String sufijo) {
        boolean exitoso = true;

        exitoso &= serializacionAdmins.crearRespaldo(ARCHIVO_ADMINS + "_" + sufijo);
        exitoso &= serializacionVendedores.crearRespaldo(ARCHIVO_VENDEDORES + "_" + sufijo);
        exitoso &= serializacionClientes.crearRespaldo(ARCHIVO_CLIENTES + "_" + sufijo);
        exitoso &= serializacionProductos.crearRespaldo(ARCHIVO_PRODUCTOS + "_" + sufijo);
        exitoso &= serializacionPedidos.crearRespaldo(ARCHIVO_PEDIDOS + "_" + sufijo);
        exitoso &= serializacionStock.crearRespaldo(ARCHIVO_STOCK + "_" + sufijo);
        exitoso &= serializacionBitacora.crearRespaldo(ARCHIVO_BITACORA + "_" + sufijo);

        if (exitoso) {
            System.out.println("✓ Respaldo completo creado exitosamente: " + sufijo);
        } else {
            System.err.println("✗ Algunos respaldos no se pudieron crear");
        }

        return exitoso;
    }

    /**
     * Crea respaldos automáticos con timestamp
     * @return true si se crearon exitosamente
     */
    public static boolean crearRespaldoAutomatico() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new java.util.Date());
        return crearRespaldoCompleto(timestamp);
    }

    /**
     * Verifica la integridad de todos los archivos
     * @return true si todos los archivos son válidos
     */
    public static boolean verificarIntegridad() {
        boolean valido = true;

        System.out.println("Verificando integridad de archivos...");

        if (existenAdministradores()) {
            valido &= serializacionAdmins.validarIntegridad();
            System.out.println("  Administradores: " + (valido ? "✓" : "✗"));
        }

        if (existenVendedores()) {
            valido &= serializacionVendedores.validarIntegridad();
            System.out.println("  Vendedores: " + (valido ? "✓" : "✗"));
        }

        if (existenClientes()) {
            valido &= serializacionClientes.validarIntegridad();
            System.out.println("  Clientes: " + (valido ? "✓" : "✗"));
        }

        if (existenProductos()) {
            valido &= serializacionProductos.validarIntegridad();
            System.out.println("  Productos: " + (valido ? "✓" : "✗"));
        }

        if (existenPedidos()) {
            valido &= serializacionPedidos.validarIntegridad();
            System.out.println("  Pedidos: " + (valido ? "✓" : "✗"));
        }

        if (existenStock()) {
            valido &= serializacionStock.validarIntegridad();
            System.out.println("  Stock: " + (valido ? "✓" : "✗"));
        }

        if (existenBitacora()) {
            valido &= serializacionBitacora.validarIntegridad();
            System.out.println("  Bitácora: " + (valido ? "✓" : "✗"));
        }

        return valido;
    }

    /**
     * Elimina todos los datos del sistema (usar con precaución)
     * @return true si todo se eliminó exitosamente
     */
    public static boolean eliminarTodo() {
        boolean exitoso = true;

        exitoso &= serializacionAdmins.eliminarDatos();
        exitoso &= serializacionVendedores.eliminarDatos();
        exitoso &= serializacionClientes.eliminarDatos();
        exitoso &= serializacionProductos.eliminarDatos();
        exitoso &= serializacionPedidos.eliminarDatos();
        exitoso &= serializacionStock.eliminarDatos();
        exitoso &= serializacionBitacora.eliminarDatos();

        if (exitoso) {
            System.out.println("⚠ Todos los datos eliminados");
        } else {
            System.err.println("✗ Algunos datos no se pudieron eliminar");
        }

        return exitoso;
    }

    /**
     * Muestra un reporte del estado de los archivos
     */
    public static void mostrarReporteArchivos() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║          REPORTE DE ARCHIVOS DEL SISTEMA           ║");
        System.out.println("╠════════════════════════════════════════════════════╣");

        mostrarInfoArchivo("Administradores", serializacionAdmins);
        mostrarInfoArchivo("Vendedores", serializacionVendedores);
        mostrarInfoArchivo("Clientes", serializacionClientes);
        mostrarInfoArchivo("Productos", serializacionProductos);
        mostrarInfoArchivo("Pedidos", serializacionPedidos);
        mostrarInfoArchivo("Stock", serializacionStock);
        mostrarInfoArchivo("Bitácora", serializacionBitacora);

        System.out.println("╚════════════════════════════════════════════════════╝\n");
    }

    /**
     * Muestra información de un archivo específico
     */
    private static void mostrarInfoArchivo(String nombre, Serializacion<?> serializacion) {
        if (serializacion.existenDatos()) {
            long tamano = ManejadorArchivos.obtenerTamanioArchivo(serializacion.obtenerRutaArchivo());
            double tamanoKB = tamano / 1024.0;
            System.out.println(String.format("║ %-20s: ✓ Existe (%.2f KB)%11s║",
                    nombre, tamanoKB, ""));
        } else {
            System.out.println(String.format("║ %-20s: ✗ No existe%19s║", nombre, ""));
        }
    }

    /**
     * Inicializa el administrador por defecto si no existe
     * @param seccion Sección del curso (ejemplo: "A", "B", "C")
     * @return true si se creó exitosamente
     */
    public static boolean inicializarAdministradorDefecto(String seccion) {
        if (existenAdministradores()) {
            System.out.println("Ya existen administradores en el sistema");
            return false;
        }

        Administrador admin = new Administrador("admin", "Administrador del Sistema",
                "Masculino", "IPC1D");

        Administrador[] admins = new Administrador[1];
        admins[0] = admin;

        boolean exitoso = guardarAdministradores(admins);

        if (exitoso) {
            System.out.println("✓ Administrador por defecto creado:");
            System.out.println("  Usuario: admin");
            System.out.println("  Contraseña: IPC1D");
        }

        return exitoso;
    }
}
