package modelo;

import java.io.Serializable;

/**
 * Clase abstracta base para todos los productos del sistema
 * Implementa Serializable para persistencia de datos
 */
public abstract class Producto implements Serializable {

    // Atributos comunes a todos los productos
    protected String codigo;
    protected String nombre;
    protected String categoria; // "TECNOLOGIA", "ALIMENTO", "GENERAL"
    protected int stockDisponible;
    protected double precio;
    protected int cantidadVendida;

    /**
     * Constructor vacío
     */
    public Producto() {
        this.stockDisponible = 0;
        this.precio = 0.0;
        this.cantidadVendida = 0;
    }

    /**
     * Constructor con parámetros
     * @param codigo Código único del producto
     * @param nombre Nombre del producto
     * @param categoria Categoría del producto
     * @param precio Precio del producto
     */
    public Producto(String codigo, String nombre, String categoria, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stockDisponible = 0;
        this.cantidadVendida = 0;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    // ==================== MÉTODOS ABSTRACTOS ====================

    /**
     * Método abstracto para obtener el atributo específico del producto
     * Cada subclase implementará según su categoría
     * @return String con el atributo específico
     */
    public abstract String obtenerAtributoEspecifico();

    /**
     * Método abstracto para obtener detalles completos del producto
     * @return String con información detallada
     */
    public abstract String obtenerDetalleCompleto();

    /**
     * Método abstracto para validar si el producto está en condiciones óptimas
     * @return true si está en buenas condiciones
     */
    public abstract boolean validarCondicion();

    // ==================== MÉTODOS COMUNES ====================

    /**
     * Agrega stock al inventario
     * @param cantidad Cantidad a agregar
     */
    public void agregarStock(int cantidad) {
        if (cantidad > 0) {
            this.stockDisponible += cantidad;
        }
    }

    /**
     * Reduce el stock disponible (cuando se vende)
     * @param cantidad Cantidad a reducir
     * @return true si se pudo reducir, false si no hay suficiente stock
     */
    public boolean reducirStock(int cantidad) {
        if (cantidad > 0 && this.stockDisponible >= cantidad) {
            this.stockDisponible -= cantidad;
            this.cantidadVendida += cantidad;
            return true;
        }
        return false;
    }

    /**
     * Verifica si hay stock disponible
     * @param cantidadRequerida Cantidad que se necesita
     * @return true si hay suficiente stock
     */
    public boolean hayStock(int cantidadRequerida) {
        return this.stockDisponible >= cantidadRequerida;
    }

    /**
     * Verifica si el stock está en nivel crítico (menos de 10 unidades)
     * @return true si está en nivel crítico
     */
    public boolean esStockCritico() {
        return this.stockDisponible < 10;
    }

    /**
     * Verifica si el stock está en nivel bajo (entre 10 y 20 unidades)
     * @return true si está en nivel bajo
     */
    public boolean esStockBajo() {
        return this.stockDisponible >= 10 && this.stockDisponible <= 20;
    }

    /**
     * Calcula los ingresos generados por este producto
     * @return Ingresos totales
     */
    public double calcularIngresos() {
        return this.cantidadVendida * this.precio;
    }

    /**
     * Obtiene el estado del stock como texto
     * @return Estado del stock
     */
    public String obtenerEstadoStock() {
        if (esStockCritico()) {
            return "CRÍTICO";
        } else if (esStockBajo()) {
            return "BAJO";
        } else {
            return "NORMAL";
        }
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", stock=" + stockDisponible +
                ", precio=" + precio +
                ", vendidos=" + cantidadVendida +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return codigo.equals(producto.codigo);
    }
}
