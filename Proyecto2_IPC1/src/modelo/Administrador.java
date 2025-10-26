package modelo;

import java.io.Serializable;

/**
 * Clase que representa al usuario Administrador
 * Tiene permisos completos sobre el sistema
 */
public class Administrador extends Usuario implements Serializable {

    // Atributos específicos del administrador
    private int usuariosCreados;
    private int productosGestionados;

    /**
     * Constructor vacío
     */
    public Administrador() {
        super();
        this.tipoUsuario = "ADMINISTRADOR";
        this.usuariosCreados = 0;
        this.productosGestionados = 0;
    }

    /**
     * Constructor con parámetros
     * @param codigo Código único del administrador
     * @param nombre Nombre completo
     * @param genero Género
     * @param contrasenia Contraseña
     */
    public Administrador(String codigo, String nombre, String genero, String contrasenia) {
        super(codigo, nombre, genero, contrasenia, "ADMINISTRADOR");
        this.usuariosCreados = 0;
        this.productosGestionados = 0;
    }

    // ==================== GETTERS Y SETTERS ====================

    public int getUsuariosCreados() {
        return usuariosCreados;
    }

    public void setUsuariosCreados(int usuariosCreados) {
        this.usuariosCreados = usuariosCreados;
    }

    public int getProductosGestionados() {
        return productosGestionados;
    }

    public void setProductosGestionados(int productosGestionados) {
        this.productosGestionados = productosGestionados;
    }

    // ==================== MÉTODOS ESPECÍFICOS ====================

    /**
     * Incrementa el contador de usuarios creados
     */
    public void incrementarUsuariosCreados() {
        this.usuariosCreados++;
    }

    /**
     * Incrementa el contador de productos gestionados
     */
    public void incrementarProductosGestionados() {
        this.productosGestionados++;
    }

    // ==================== IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS ====================

    @Override
    public String obtenerInformacion() {
        return "=== ADMINISTRADOR ===\n" +
                "Código: " + codigo + "\n" +
                "Nombre: " + nombre + "\n" +
                "Género: " + genero + "\n" +
                "Usuarios creados: " + usuariosCreados + "\n" +
                "Productos gestionados: " + productosGestionados;
    }

    @Override
    public boolean tienePermiso(String accion) {
        // El administrador tiene todos los permisos
        return true;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", usuariosCreados=" + usuariosCreados +
                ", productosGestionados=" + productosGestionados +
                '}';
    }
}
