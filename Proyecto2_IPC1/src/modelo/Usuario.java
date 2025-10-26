package modelo;

import java.io.Serializable;

/**
 * Clase abstracta base para todos los tipos de usuario del sistema
 * Implementa Serializable para persistencia de datos
 */
public abstract class Usuario implements Serializable {

    // Atributos comunes a todos los usuarios
    protected String codigo;
    protected String nombre;
    protected String genero;
    protected String contrasenia;
    protected String tipoUsuario; // "ADMINISTRADOR", "VENDEDOR", "CLIENTE"

    /**
     * Constructor vacío
     */
    public Usuario() {
    }

    /**
     * Constructor con parámetros
     * @param codigo Código único del usuario
     * @param nombre Nombre completo
     * @param genero Género (Masculino/Femenino/Otro)
     * @param contrasenia Contraseña de acceso
     * @param tipoUsuario Tipo de usuario
     */
    public Usuario(String codigo, String nombre, String genero, String contrasenia, String tipoUsuario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.contrasenia = contrasenia;
        this.tipoUsuario = tipoUsuario;
    }

    // ==================== GETTERS Y SETTERS ====================

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    // ==================== MÉTODOS ABSTRACTOS ====================

    /**
     * Método abstracto para obtener información específica del usuario
     * Cada subclase implementará su propia versión
     * @return String con información del usuario
     */
    public abstract String obtenerInformacion();

    /**
     * Método abstracto para validar permisos específicos
     * @param accion Acción que se desea realizar
     * @return true si tiene permiso, false si no
     */
    public abstract boolean tienePermiso(String accion);

    // ==================== MÉTODOS COMUNES ====================

    /**
     * Valida las credenciales del usuario
     * @param codigo Código a validar
     * @param contrasenia Contraseña a validar
     * @return true si las credenciales son correctas
     */
    public boolean validarCredenciales(String codigo, String contrasenia) {
        return this.codigo.equals(codigo) && this.contrasenia.equals(contrasenia);
    }

    /**
     * Verifica si el usuario es de un tipo específico
     * @param tipo Tipo a verificar
     * @return true si coincide el tipo
     */
    public boolean esTipo(String tipo) {
        return this.tipoUsuario.equalsIgnoreCase(tipo);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", genero='" + genero + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return codigo.equals(usuario.codigo);
    }
}
