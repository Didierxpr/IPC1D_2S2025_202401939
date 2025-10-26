package controlador;

import interfaces.ICRUD;
import modelo.*;
import utilidades.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controlador CRUD para gestión de pedidos
 * Implementa ICRUD<Pedido>
 */
public class ControladorPedido implements ICRUD<Pedido> {

    private Pedido[] pedidos;
    private Usuario usuarioActual;
    private ControladorProducto controladorProducto;

    public ControladorPedido(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        this.controladorProducto = new ControladorProducto(usuarioActual);
        cargarPedidos();
    }

    /**
     * Carga los pedidos desde el sistema de archivos
     */
    private void cargarPedidos() {
        pedidos = SistemaArchivos.cargarPedidos();
        if (pedidos == null) {
            pedidos = new Pedido[0];
        }
    }

    /**
     * Guarda los pedidos en el sistema de archivos
     */
    private boolean guardarPedidos() {
        return SistemaArchivos.guardarPedidos(pedidos);
    }

    @Override
    public boolean crear(Pedido pedido) {
        if (pedido == null) {
            return false;
        }

        pedidos = (Pedido[]) Vectores.agregar(pedidos, pedido);
        boolean resultado = guardarPedidos();

        if (resultado && usuarioActual instanceof Cliente) {
            registrarBitacora(Bitacora.realizarPedido(
                    (Cliente) usuarioActual,
                    pedido.getCodigoPedido(),
                    pedido.getTotalPedido(),
                    pedido.getCantidadProductos()
            ));
        }

        return resultado;
    }

    @Override
    public Pedido[] obtenerTodos() {
        return pedidos;
    }

    @Override
    public Pedido buscarPorCodigo(String codigo) {
        for (int i = 0; i < pedidos.length; i++) {
            if (pedidos[i].getCodigoPedido().equals(codigo)) {
                return pedidos[i];
            }
        }
        return null;
    }

    @Override
    public boolean actualizar(String codigo, Pedido pedidoActualizado) {
        for (int i = 0; i < pedidos.length; i++) {
            if (pedidos[i].getCodigoPedido().equals(codigo)) {
                pedidos[i] = pedidoActualizado;
                return guardarPedidos();
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(String codigo) {
        for (int i = 0; i < pedidos.length; i++) {
            if (pedidos[i].getCodigoPedido().equals(codigo)) {
                pedidos = (Pedido[]) Vectores.eliminar(pedidos, i);
                return guardarPedidos();
            }
        }
        return false;
    }

    @Override
    public boolean existe(String codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    @Override
    public int contarRegistros() {
        return pedidos.length;
    }

    @Override
    public boolean limpiarTodo() {
        pedidos = new Pedido[0];
        return guardarPedidos();
    }

    /**
     * Obtiene pedidos pendientes
     */
    public Pedido[] obtenerPedidosPendientes() {
        Pedido[] resultado = new Pedido[0];

        for (int i = 0; i < pedidos.length; i++) {
            if (pedidos[i].estaPendiente()) {
                resultado = (Pedido[]) Vectores.agregar(resultado, pedidos[i]);
            }
        }

        return resultado;
    }

    /**
     * Obtiene pedidos confirmados
     */
    public Pedido[] obtenerPedidosConfirmados() {
        Pedido[] resultado = new Pedido[0];

        for (int i = 0; i < pedidos.length; i++) {
            if (pedidos[i].estaConfirmado()) {
                resultado = (Pedido[]) Vectores.agregar(resultado, pedidos[i]);
            }
        }

        return resultado;
    }

    /**
     * Obtiene pedidos de un cliente específico
     */
    public Pedido[] obtenerPedidosPorCliente(String codigoCliente) {
        Pedido[] resultado = new Pedido[0];

        for (int i = 0; i < pedidos.length; i++) {
            if (pedidos[i].getCodigoCliente().equals(codigoCliente)) {
                resultado = (Pedido[]) Vectores.agregar(resultado, pedidos[i]);
            }
        }

        return resultado;
    }

    /**
     * Obtiene pedidos confirmados de un cliente
     */
    public Pedido[] obtenerHistorialCompras(String codigoCliente) {
        Pedido[] resultado = new Pedido[0];

        for (int i = 0; i < pedidos.length; i++) {
            if (pedidos[i].getCodigoCliente().equals(codigoCliente) &&
                    pedidos[i].estaConfirmado()) {
                resultado = (Pedido[]) Vectores.agregar(resultado, pedidos[i]);
            }
        }

        return resultado;
    }

    /**
     * Confirma un pedido (vendedor)
     */
    public boolean confirmarPedido(String codigoPedido) {
        Pedido pedido = buscarPorCodigo(codigoPedido);

        if (pedido == null || !pedido.estaPendiente()) {
            System.err.println("Pedido no encontrado o ya fue procesado");
            return false;
        }

        // Verificar stock disponible para todos los productos
        String[] codigosProductos = pedido.getCodigosProductos();
        int[] cantidades = pedido.getCantidades();

        for (int i = 0; i < codigosProductos.length; i++) {
            Producto producto = controladorProducto.buscarPorCodigo(codigosProductos[i]);

            if (producto == null || !producto.hayStock(cantidades[i])) {
                System.err.println("Stock insuficiente para producto: " + codigosProductos[i]);
                return false;
            }
        }

        // Reducir stock de todos los productos
        for (int i = 0; i < codigosProductos.length; i++) {
            Producto producto = controladorProducto.buscarPorCodigo(codigosProductos[i]);
            producto.reducirStock(cantidades[i]);
            controladorProducto.actualizar(producto.getCodigo(), producto);
        }

        // Confirmar pedido
        pedido.confirmar();
        boolean resultado = actualizar(codigoPedido, pedido);

        if (resultado) {
            // Actualizar estadísticas del cliente
            Cliente[] clientes = SistemaArchivos.cargarClientes();
            for (int i = 0; i < clientes.length; i++) {
                if (clientes[i].getCodigo().equals(pedido.getCodigoCliente())) {
                    clientes[i].incrementarCompras();
                    clientes[i].agregarGasto(pedido.getTotalPedido());
                    SistemaArchivos.guardarClientes(clientes);
                    break;
                }
            }

            // Actualizar estadísticas del vendedor
            if (usuarioActual instanceof Vendedor) {
                ((Vendedor) usuarioActual).incrementarVentasConfirmadas();
                Vendedor[] vendedores = SistemaArchivos.cargarVendedores();
                for (int i = 0; i < vendedores.length; i++) {
                    if (vendedores[i].getCodigo().equals(usuarioActual.getCodigo())) {
                        vendedores[i] = (Vendedor) usuarioActual;
                        break;
                    }
                }
                SistemaArchivos.guardarVendedores(vendedores);

                registrarBitacora(Bitacora.confirmarPedido((Vendedor) usuarioActual, codigoPedido));
            }
        }

        return resultado;
    }

    /**
     * Genera un código único para pedido
     */
    public String generarCodigoPedido() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        return "PED-" + timestamp;
    }

    /**
     * Registra una entrada en la bitácora
     */
    private void registrarBitacora(Bitacora entrada) {
        ControladorBitacora controladorBitacora = new ControladorBitacora();
        controladorBitacora.registrar(entrada);
    }

    /**
     * Recarga los pedidos desde el sistema de archivos
     */
    public void recargar() {
        cargarPedidos();
    }
}
