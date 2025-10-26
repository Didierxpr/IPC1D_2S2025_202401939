package controlador;

import interfaces.ICRUD;
import modelo.*;
import utilidades.*;

/**
 * Controlador CRUD para gestión de clientes
 * Implementa ICRUD<Cliente>
 */
public class ControladorCliente implements ICRUD<Cliente> {

    private Cliente[] clientes;
    private Usuario usuarioActual;

    public ControladorCliente(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        cargarClientes();
    }

    /**
     * Carga los clientes desde el sistema de archivos
     */
    private void cargarClientes() {
        clientes = SistemaArchivos.cargarClientes();
        if (clientes == null) {
            clientes = new Cliente[0];
        }
    }

    /**
     * Guarda los clientes en el sistema de archivos
     */
    private boolean guardarClientes() {
        return SistemaArchivos.guardarClientes(clientes);
    }

    @Override
    public boolean crear(Cliente cliente) {
        if (cliente == null) {
            return false;
        }

        // Verificar que el código sea único
        if (existe(cliente.getCodigo())) {
            System.err.println("Ya existe un cliente con el código: " + cliente.getCodigo());
            return false;
        }

        clientes = (Cliente[]) Vectores.agregar(clientes, cliente);
        boolean resultado = guardarClientes();

        if (resultado) {
            registrarBitacora(Bitacora.crearUsuario(usuarioActual, "CLIENTE", cliente.getCodigo()));

            // Si el usuario actual es vendedor, incrementar contador
            if (usuarioActual instanceof Vendedor) {
                ((Vendedor) usuarioActual).incrementarClientesRegistrados();
                Vendedor[] vendedores = SistemaArchivos.cargarVendedores();
                for (int i = 0; i < vendedores.length; i++) {
                    if (vendedores[i].getCodigo().equals(usuarioActual.getCodigo())) {
                        vendedores[i] = (Vendedor) usuarioActual;
                        break;
                    }
                }
                SistemaArchivos.guardarVendedores(vendedores);
            }
        }

        return resultado;
    }

    @Override
    public Cliente[] obtenerTodos() {
        return clientes;
    }

    @Override
    public Cliente buscarPorCodigo(String codigo) {
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i].getCodigo().equals(codigo)) {
                return clientes[i];
            }
        }
        return null;
    }

    @Override
    public boolean actualizar(String codigo, Cliente clienteActualizado) {
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i].getCodigo().equals(codigo)) {
                clientes[i] = clienteActualizado;
                boolean resultado = guardarClientes();

                if (resultado) {
                    registrarBitacora(new Bitacora(
                            usuarioActual.getTipoUsuario(),
                            usuarioActual.getCodigo(),
                            usuarioActual.getNombre(),
                            "ACTUALIZAR_CLIENTE",
                            "EXITOSA",
                            "Cliente actualizado: " + codigo,
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
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i].getCodigo().equals(codigo)) {
                clientes = (Cliente[]) Vectores.eliminar(clientes, i);
                boolean resultado = guardarClientes();

                if (resultado) {
                    registrarBitacora(new Bitacora(
                            usuarioActual.getTipoUsuario(),
                            usuarioActual.getCodigo(),
                            usuarioActual.getNombre(),
                            "ELIMINAR_CLIENTE",
                            "EXITOSA",
                            "Cliente eliminado: " + codigo,
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
        return clientes.length;
    }

    @Override
    public boolean limpiarTodo() {
        clientes = new Cliente[0];
        return guardarClientes();
    }

    /**
     * Obtiene los clientes de un vendedor específico
     */
    public Cliente[] obtenerClientesPorVendedor(String codigoVendedor) {
        Cliente[] resultado = new Cliente[0];

        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i].getVendedorAsignado().equals(codigoVendedor)) {
                resultado = (Cliente[]) Vectores.agregar(resultado, clientes[i]);
            }
        }

        return resultado;
    }

    /**
     * Obtiene clientes activos (con compras recientes)
     */
    public Cliente[] obtenerClientesActivos(int diasMinimos) {
        Cliente[] resultado = new Cliente[0];

        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i].getComprasRealizadas() > 0) {
                resultado = (Cliente[]) Vectores.agregar(resultado, clientes[i]);
            }
        }

        return resultado;
    }

    /**
     * Obtiene clientes por clasificación
     */
    public Cliente[] obtenerPorClasificacion(String clasificacion) {
        Cliente[] resultado = new Cliente[0];

        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i].getClasificacion().equalsIgnoreCase(clasificacion)) {
                resultado = (Cliente[]) Vectores.agregar(resultado, clientes[i]);
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
     * Recarga los clientes desde el sistema de archivos
     */
    public void recargar() {
        cargarClientes();
    }
}