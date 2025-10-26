package controlador;

import interfaces.ICRUD;
import modelo.*;
import utilidades.*;

/**
 * Controlador CRUD para gestión de productos
 * Implementa ICRUD<Producto>
 */
public class ControladorProducto implements ICRUD<Producto> {

    private Producto[] productos;
    private Usuario usuarioActual;

    public ControladorProducto(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        cargarProductos();
    }

    /**
     * Carga los productos desde el sistema de archivos
     */
    private void cargarProductos() {
        productos = SistemaArchivos.cargarProductos();
        if (productos == null) {
            productos = new Producto[0];
        }
    }

    /**
     * Guarda los productos en el sistema de archivos
     */
    private boolean guardarProductos() {
        return SistemaArchivos.guardarProductos(productos);
    }

    @Override
    public boolean crear(Producto producto) {
        if (producto == null) {
            return false;
        }

        // Verificar que el código sea único
        if (existe(producto.getCodigo())) {
            System.err.println("Ya existe un producto con el código: " + producto.getCodigo());
            return false;
        }

        productos = (Producto[]) Vectores.agregar(productos, producto);
        boolean resultado = guardarProductos();

        if (resultado) {
            registrarBitacora(Bitacora.crearProducto(usuarioActual, producto.getCodigo(), producto.getNombre()));
        }

        return resultado;
    }

    @Override
    public Producto[] obtenerTodos() {
        return productos;
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        for (int i = 0; i < productos.length; i++) {
            if (productos[i].getCodigo().equals(codigo)) {
                return productos[i];
            }
        }
        return null;
    }

    @Override
    public boolean actualizar(String codigo, Producto productoActualizado) {
        for (int i = 0; i < productos.length; i++) {
            if (productos[i].getCodigo().equals(codigo)) {
                productos[i] = productoActualizado;
                boolean resultado = guardarProductos();

                if (resultado) {
                    registrarBitacora(new Bitacora(
                            usuarioActual.getTipoUsuario(),
                            usuarioActual.getCodigo(),
                            usuarioActual.getNombre(),
                            "ACTUALIZAR_PRODUCTO",
                            "EXITOSA",
                            "Producto actualizado: " + codigo,
                            ""
                    ));
                }

                return resultado;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(String codigo) {
        for (int i = 0; i < productos.length; i++) {
            if (productos[i].getCodigo().equals(codigo)) {
                productos = (Producto[]) Vectores.eliminar(productos, i);
                boolean resultado = guardarProductos();

                if (resultado) {
                    registrarBitacora(new Bitacora(
                            usuarioActual.getTipoUsuario(),
                            usuarioActual.getCodigo(),
                            usuarioActual.getNombre(),
                            "ELIMINAR_PRODUCTO",
                            "EXITOSA",
                            "Producto eliminado: " + codigo,
                            ""
                    ));
                }

                return resultado;
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
        return productos.length;
    }

    @Override
    public boolean limpiarTodo() {
        productos = new Producto[0];
        return guardarProductos();
    }

    /**
     * Obtiene productos por categoría
     */
    public Producto[] obtenerPorCategoria(String categoria) {
        Producto[] resultado = new Producto[0];

        for (int i = 0; i < productos.length; i++) {
            if (productos[i].getCategoria().equalsIgnoreCase(categoria)) {
                resultado = (Producto[]) Vectores.agregar(resultado, productos[i]);
            }
        }

        return resultado;
    }

    /**
     * Obtiene productos con stock disponible
     */
    public Producto[] obtenerConStock() {
        Producto[] resultado = new Producto[0];

        for (int i = 0; i < productos.length; i++) {
            if (productos[i].getStockDisponible() > 0) {
                resultado = (Producto[]) Vectores.agregar(resultado, productos[i]);
            }
        }

        return resultado;
    }

    /**
     * Obtiene productos con stock crítico (menos de 10)
     */
    public Producto[] obtenerStockCritico() {
        Producto[] resultado = new Producto[0];

        for (int i = 0; i < productos.length; i++) {
            if (productos[i].esStockCritico()) {
                resultado = (Producto[]) Vectores.agregar(resultado, productos[i]);
            }
        }

        return resultado;
    }

    /**
     * Obtiene productos con stock bajo (10-20)
     */
    public Producto[] obtenerStockBajo() {
        Producto[] resultado = new Producto[0];

        for (int i = 0; i < productos.length; i++) {
            if (productos[i].esStockBajo()) {
                resultado = (Producto[]) Vectores.agregar(resultado, productos[i]);
            }
        }

        return resultado;
    }

    /**
     * Registra una entrada en la bitácora
     */
    private void registrarBitacora(Bitacora entrada) {
        ControladorBitacora controladorBitacora = new ControladorBitacora();
        controladorBitacora.registrar(entrada);
    }

    /**
     * Recarga los productos desde el sistema de archivos
     */
    public void recargar() {
        cargarProductos();
    }
}