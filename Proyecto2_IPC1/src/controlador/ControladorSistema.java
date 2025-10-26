package controlador;

import modelo.*;
import utilidades.*;
import utilidades.hilos.GestorHilos;

/**
 * Controlador principal del sistema
 * Coordina todos los demás controladores
 */
public class ControladorSistema {

    private ControladorAutenticacion controladorAutenticacion;
    private ControladorAdministrador controladorAdministrador;
    private ControladorProducto controladorProducto;
    private ControladorPedido controladorPedido;
    private ControladorStock controladorStock;
    private ControladorCliente controladorCliente;
    private ControladorBitacora controladorBitacora;
    private ControladorCarrito controladorCarrito;
    private GestorHilos gestorHilos;

    private boolean sistemaIniciado;

    public ControladorSistema() {
        this.sistemaIniciado = false;
        this.gestorHilos = new GestorHilos();
    }

    /**
     * Inicializa el sistema completo
     */
    public boolean inicializarSistema(String seccion) {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║           SANCARLISTA SHOP - INICIANDO            ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");

        // Inicializar sistema de archivos
        SistemaArchivos.inicializar();

        // Verificar integridad de archivos
        System.out.println("Verificando integridad de archivos...");
        SistemaArchivos.verificarIntegridad();

        // Crear administrador por defecto si no existe
        if (!SistemaArchivos.existenAdministradores()) {
            System.out.println("\nCreando administrador por defecto...");
            SistemaArchivos.inicializarAdministradorDefecto(seccion);
        }

        // Inicializar controlador de autenticación
        controladorAutenticacion = new ControladorAutenticacion();
        controladorBitacora = new ControladorBitacora();

        // Registrar inicio del sistema
        Bitacora inicioSistema = new Bitacora(
                "SISTEMA",
                "SYSTEM",
                "Sistema",
                "INICIO_SISTEMA",
                "EXITOSA",
                "Sistema Sancarlista Shop iniciado",
                "Versión 1.0"
        );
        controladorBitacora.registrar(inicioSistema);

        gestorHilos.iniciarHilos();

        sistemaIniciado = true;

        System.out.println("\n✓ Sistema iniciado correctamente");
        SistemaArchivos.mostrarReporteArchivos();

        return true;
    }

    /**
     * Realiza login en el sistema
     */
    public Usuario login(String codigo, String contrasenia) {
        if (!sistemaIniciado) {
            System.err.println("El sistema no ha sido iniciado");
            return null;
        }

        Usuario usuario = controladorAutenticacion.autenticar(codigo, contrasenia);

        if (usuario != null) {
            // Inicializar controladores según el tipo de usuario
            inicializarControladores(usuario);
            gestorHilos.notificarLogin();

            System.out.println("\n✓ Bienvenido/a " + usuario.getNombre());
            System.out.println("  Tipo: " + usuario.getTipoUsuario());
        } else {
            System.err.println("\n✗ Credenciales incorrectas");
        }

        return usuario;
    }

    /**
     * Cierra sesión del usuario actual
     */
    public boolean logout() {
        gestorHilos.notificarLogout();
        boolean resultado = controladorAutenticacion.cerrarSesion();

        if (resultado) {
            // Limpiar controladores
            controladorAdministrador = null;
            controladorProducto = null;
            controladorPedido = null;
            controladorStock = null;
            controladorCliente = null;
            controladorCarrito = null;

            System.out.println("\n✓ Sesión cerrada correctamente");
        }

        return resultado;
    }

    /**
     * Inicializa los controladores según el tipo de usuario
     */
    private void inicializarControladores(Usuario usuario) {
        controladorProducto = new ControladorProducto(usuario);
        controladorPedido = new ControladorPedido(usuario);
        controladorStock = new ControladorStock(usuario);
        controladorCliente = new ControladorCliente(usuario);

        if (usuario instanceof Administrador) {
            controladorAdministrador = new ControladorAdministrador(usuario);
        } else if (usuario instanceof Cliente) {
            controladorCarrito = new ControladorCarrito((Cliente) usuario, usuario);
        }
    }

    /**
     * Guarda todos los datos del sistema
     */
    public boolean guardarTodo() {
        if (!sistemaIniciado) {
            return false;
        }

        System.out.println("Guardando datos del sistema...");

        // Los controladores ya guardan automáticamente,
        // pero esto asegura que todo esté persistido
        boolean resultado = true;

        // Recargar y guardar todos los datos
        Administrador[] admins = SistemaArchivos.cargarAdministradores();
        Vendedor[] vendedores = SistemaArchivos.cargarVendedores();
        Cliente[] clientes = SistemaArchivos.cargarClientes();
        Producto[] productos = SistemaArchivos.cargarProductos();
        Pedido[] pedidos = SistemaArchivos.cargarPedidos();
        Stock[] stock = SistemaArchivos.cargarStock();
        Bitacora[] bitacora = SistemaArchivos.cargarBitacora();

        resultado = SistemaArchivos.guardarTodo(
                admins != null ? admins : new Administrador[0],
                vendedores != null ? vendedores : new Vendedor[0],
                clientes != null ? clientes : new Cliente[0],
                productos != null ? productos : new Producto[0],
                pedidos != null ? pedidos : new Pedido[0],
                stock != null ? stock : new Stock[0],
                bitacora != null ? bitacora : new Bitacora[0]
        );

        if (resultado) {
            System.out.println("✓ Datos guardados correctamente");
        } else {
            System.err.println("✗ Error al guardar algunos datos");
        }

        return resultado;
    }

    /**
     * Crea un respaldo completo del sistema
     */
    public boolean crearRespaldo() {
        if (!sistemaIniciado) {
            return false;
        }

        System.out.println("Creando respaldo del sistema...");
        boolean resultado = SistemaArchivos.crearRespaldoAutomatico();

        if (resultado) {
            System.out.println("✓ Respaldo creado exitosamente");
        } else {
            System.err.println("✗ Error al crear respaldo");
        }

        return resultado;
    }

    /**
     * Cierra el sistema de forma segura
     */
    public boolean cerrarSistema() {
        if (!sistemaIniciado) {
            return false;
        }

        System.out.println("\nCerrando sistema...");

        // Cerrar sesión si hay una activa
        if (controladorAutenticacion.haySesionActiva()) {
            logout();
        }

        // Guardar todos los datos
        guardarTodo();

        // Crear respaldo automático
        crearRespaldo();

        // Registrar cierre del sistema
        Bitacora cierreSistema = new Bitacora(
                "SISTEMA",
                "SYSTEM",
                "Sistema",
                "CIERRE_SISTEMA",
                "EXITOSA",
                "Sistema Sancarlista Shop cerrado",
                ""
        );
        controladorBitacora.registrar(cierreSistema);

        gestorHilos.detenerHilos();

        sistemaIniciado = false;

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║        SISTEMA CERRADO CORRECTAMENTE              ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        return true;
    }

    // ==================== GETTERS DE CONTROLADORES ====================

    public ControladorAutenticacion getControladorAutenticacion() {
        return controladorAutenticacion;
    }

    public ControladorAdministrador getControladorAdministrador() {
        return controladorAdministrador;
    }

    public ControladorProducto getControladorProducto() {
        return controladorProducto;
    }

    public ControladorPedido getControladorPedido() {
        return controladorPedido;
    }

    public ControladorStock getControladorStock() {
        return controladorStock;
    }

    public ControladorCliente getControladorCliente() {
        return controladorCliente;
    }

    public ControladorBitacora getControladorBitacora() {
        return controladorBitacora;
    }

    public ControladorCarrito getControladorCarrito() {
        return controladorCarrito;
    }

    public GestorHilos getGestorHilos() {
        return gestorHilos;
    }

    public boolean isSistemaIniciado() {
        return sistemaIniciado;
    }

    /**
     * Obtiene estadísticas generales del sistema
     */
    public String obtenerEstadisticasGenerales() {
        int totalAdmins = SistemaArchivos.existenAdministradores() ?
                SistemaArchivos.cargarAdministradores().length : 0;
        int totalVendedores = SistemaArchivos.existenVendedores() ?
                SistemaArchivos.cargarVendedores().length : 0;
        int totalClientes = SistemaArchivos.existenClientes() ?
                SistemaArchivos.cargarClientes().length : 0;
        int totalProductos = SistemaArchivos.existenProductos() ?
                SistemaArchivos.cargarProductos().length : 0;
        int totalPedidos = SistemaArchivos.existenPedidos() ?
                SistemaArchivos.cargarPedidos().length : 0;

        return "╔════════════════════════════════════════════════════╗\n" +
                "║       ESTADÍSTICAS GENERALES DEL SISTEMA          ║\n" +
                "╠════════════════════════════════════════════════════╣\n" +
                String.format("║ Administradores: %-33d║\n", totalAdmins) +
                String.format("║ Vendedores: %-38d║\n", totalVendedores) +
                String.format("║ Clientes: %-40d║\n", totalClientes) +
                String.format("║ Productos: %-39d║\n", totalProductos) +
                String.format("║ Pedidos: %-41d║\n", totalPedidos) +
                "╚════════════════════════════════════════════════════╝";
    }
}
