package controlador;

import interfaces.IAutenticacion;
import modelo.*;
import utilidades.SistemaArchivos;

/**
 * Controlador para gestionar la autenticación de usuarios
 * Implementa IAutenticacion
 */
public class ControladorAutenticacion implements IAutenticacion {

    private Usuario usuarioActual;
    private Administrador[] administradores;
    private Vendedor[] vendedores;
    private Cliente[] clientes;

    public ControladorAutenticacion() {
        this.usuarioActual = null;
        cargarUsuarios();
    }

    /**
     * Carga todos los usuarios del sistema
     */
    private void cargarUsuarios() {
        administradores = SistemaArchivos.cargarAdministradores();
        vendedores = SistemaArchivos.cargarVendedores();
        clientes = SistemaArchivos.cargarClientes();

        // Inicializar arrays vacíos si son null
        if (administradores == null) {
            administradores = new Administrador[0];
        }
        if (vendedores == null) {
            vendedores = new Vendedor[0];
        }
        if (clientes == null) {
            clientes = new Cliente[0];
        }
    }

    @Override
    public Usuario autenticar(String codigo, String contrasenia) {
        if (codigo == null || contrasenia == null) {
            return null;
        }

        // Buscar en administradores
        for (int i = 0; i < administradores.length; i++) {
            if (administradores[i].validarCredenciales(codigo, contrasenia)) {
                usuarioActual = administradores[i];
                registrarBitacora(Bitacora.loginExitoso(usuarioActual));
                return usuarioActual;
            }
        }

        // Buscar en vendedores
        for (int i = 0; i < vendedores.length; i++) {
            if (vendedores[i].validarCredenciales(codigo, contrasenia)) {
                usuarioActual = vendedores[i];
                registrarBitacora(Bitacora.loginExitoso(usuarioActual));
                return usuarioActual;
            }
        }

        // Buscar en clientes
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i].validarCredenciales(codigo, contrasenia)) {
                usuarioActual = clientes[i];
                registrarBitacora(Bitacora.loginExitoso(usuarioActual));
                return usuarioActual;
            }
        }

        // Login fallido
        registrarBitacora(Bitacora.loginFallido(codigo, "Credenciales incorrectas"));
        return null;
    }

    @Override
    public boolean cerrarSesion() {
        if (usuarioActual != null) {
            registrarBitacora(Bitacora.logout(usuarioActual));
            usuarioActual = null;
            return true;
        }
        return false;
    }

    @Override
    public Usuario obtenerUsuarioActual() {
        return usuarioActual;
    }

    @Override
    public boolean haySesionActiva() {
        return usuarioActual != null;
    }

    @Override
    public boolean cambiarContrasena(String contrasenaActual, String contrasenaNueva) {
        if (usuarioActual == null) {
            return false;
        }

        // Validar contraseña actual
        if (!usuarioActual.getContrasenia().equals(contrasenaActual)) {
            return false;
        }

        // Cambiar contraseña
        usuarioActual.setContrasenia(contrasenaNueva);

        // Guardar cambios según el tipo de usuario
        if (usuarioActual instanceof Administrador) {
            SistemaArchivos.guardarAdministradores(administradores);
        } else if (usuarioActual instanceof Vendedor) {
            SistemaArchivos.guardarVendedores(vendedores);
        } else if (usuarioActual instanceof Cliente) {
            SistemaArchivos.guardarClientes(clientes);
        }

        registrarBitacora(new Bitacora(
                usuarioActual.getTipoUsuario(),
                usuarioActual.getCodigo(),
                usuarioActual.getNombre(),
                "CAMBIAR_CONTRASENA",
                "EXITOSA",
                "Contraseña actualizada correctamente",
                ""
        ));

        return true;
    }

    @Override
    public boolean tienePermiso(String permiso) {
        if (usuarioActual == null) {
            return false;
        }

        return usuarioActual.tienePermiso(permiso);
    }

    /**
     * Registra una entrada en la bitácora
     */
    private void registrarBitacora(Bitacora entrada) {
        ControladorBitacora controladorBitacora = new ControladorBitacora();
        controladorBitacora.registrar(entrada);
    }

    /**
     * Verifica si un código de usuario existe
     */
    public boolean existeUsuario(String codigo) {
        // Buscar en todos los tipos de usuarios
        for (int i = 0; i < administradores.length; i++) {
            if (administradores[i].getCodigo().equals(codigo)) {
                return true;
            }
        }

        for (int i = 0; i < vendedores.length; i++) {
            if (vendedores[i].getCodigo().equals(codigo)) {
                return true;
            }
        }

        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i].getCodigo().equals(codigo)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Obtiene el tipo de usuario actual
     */
    public String obtenerTipoUsuarioActual() {
        if (usuarioActual == null) {
            return null;
        }
        return usuarioActual.getTipoUsuario();
    }

    /**
     * Recarga los usuarios desde el sistema de archivos
     */
    public void recargarUsuarios() {
        cargarUsuarios();
    }
}
