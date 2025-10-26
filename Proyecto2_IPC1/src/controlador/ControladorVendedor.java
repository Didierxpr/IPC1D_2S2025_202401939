package controlador;

import interfaces.ICRUD;
import modelo.*;
import utilidades.*;

/**
 * Controlador CRUD para gestión de vendedores
 * Implementa ICRUD<Vendedor>
 */
public class ControladorVendedor implements ICRUD<Vendedor> {

    private Vendedor[] vendedores;
    private Usuario usuarioActual;

    public ControladorVendedor(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        cargarVendedores();
    }

    /**
     * Carga los vendedores desde el sistema de archivos
     */
    private void cargarVendedores() {
        vendedores = SistemaArchivos.cargarVendedores();
        if (vendedores == null) {
            vendedores = new Vendedor[0];
        }
    }

    /**
     * Guarda los vendedores en el sistema de archivos
     */
    private boolean guardarVendedores() {
        return SistemaArchivos.guardarVendedores(vendedores);
    }

    @Override
    public boolean crear(Vendedor vendedor) {
        if (vendedor == null) {
            return false;
        }

        // Verificar que el código sea único
        if (existe(vendedor.getCodigo())) {
            System.err.println("Ya existe un vendedor con el código: " + vendedor.getCodigo());
            return false;
        }

        // 👇 Solución al ClassCastException
        Object[] temp = Vectores.agregar(vendedores, vendedor);

        Vendedor[] nuevo = new Vendedor[temp.length];
        for (int i = 0; i < temp.length; i++) {
            nuevo[i] = (Vendedor) temp[i];
        }
        vendedores = nuevo;

        boolean resultado = guardarVendedores();

        if (resultado) {
            registrarBitacora(Bitacora.crearUsuario(usuarioActual, "VENDEDOR", vendedor.getCodigo()));
        }

        return resultado;
    }


    @Override
    public Vendedor[] obtenerTodos() {
        return vendedores;
    }

    @Override
    public Vendedor buscarPorCodigo(String codigo) {
        for (int i = 0; i < vendedores.length; i++) {
            if (vendedores[i].getCodigo().equals(codigo)) {
                return vendedores[i];
            }
        }
        return null;
    }

    @Override
    public boolean actualizar(String codigo, Vendedor vendedorActualizado) {
        for (int i = 0; i < vendedores.length; i++) {
            if (vendedores[i].getCodigo().equals(codigo)) {
                vendedores[i] = vendedorActualizado;
                boolean resultado = guardarVendedores();

                if (resultado) {
                    registrarBitacora(new Bitacora(
                            usuarioActual.getTipoUsuario(),
                            usuarioActual.getCodigo(),
                            usuarioActual.getNombre(),
                            "ACTUALIZAR_VENDEDOR",
                            "EXITOSA",
                            "Vendedor actualizado: " + codigo,
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
        for (int i = 0; i < vendedores.length; i++) {
            if (vendedores[i].getCodigo().equals(codigo)) {
                vendedores = (Vendedor[]) Vectores.eliminar(vendedores, i);
                boolean resultado = guardarVendedores();

                if (resultado) {
                    registrarBitacora(new Bitacora(
                            usuarioActual.getTipoUsuario(),
                            usuarioActual.getCodigo(),
                            usuarioActual.getNombre(),
                            "ELIMINAR_VENDEDOR",
                            "EXITOSA",
                            "Vendedor eliminado: " + codigo,
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
        return vendedores.length;
    }

    @Override
    public boolean limpiarTodo() {
        vendedores = new Vendedor[0];
        return guardarVendedores();
    }

    /**
     * Registra una entrada en la bitácora
     */
    private void registrarBitacora(Bitacora entrada) {
        ControladorBitacora controladorBitacora = new ControladorBitacora();
        controladorBitacora.registrar(entrada);
    }

    /**
     * Recarga los vendedores desde el sistema de archivos
     */
    public void recargar() {
        cargarVendedores();
    }
}
