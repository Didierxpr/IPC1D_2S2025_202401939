package interfaces;

import modelo.Usuario;

/**
 * Interface que define las operaciones de autenticación del sistema
 */
public interface IAutenticacion {

    /**
     * Autentica un usuario en el sistema
     * @param codigo Código del usuario
     * @param contrasenia Contraseña del usuario
     * @return Usuario autenticado o null si las credenciales son incorrectas
     */
    Usuario autenticar(String codigo, String contrasenia);

    /**
     * Cierra la sesión del usuario actual
     * @return true si se cerró exitosamente
     */
    boolean cerrarSesion();

    /**
     * Obtiene el usuario actualmente autenticado
     * @return Usuario actual o null si no hay sesión activa
     */
    Usuario obtenerUsuarioActual();

    /**
     * Verifica si hay una sesión activa
     * @return true si hay un usuario autenticado
     */
    boolean haySesionActiva();

    /**
     * Cambia la contraseña del usuario actual
     * @param contrasenaActual Contraseña actual
     * @param contrasenaNueva Nueva contraseña
     * @return true si se cambió exitosamente
     */
    boolean cambiarContrasena(String contrasenaActual, String contrasenaNueva);

    /**
     * Verifica si el usuario actual tiene un permiso específico
     * @param permiso Permiso a verificar
     * @return true si tiene el permiso
     */
    boolean tienePermiso(String permiso);
}
