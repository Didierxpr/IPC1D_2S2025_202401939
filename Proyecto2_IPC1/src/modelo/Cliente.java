package modelo;

import java.io.Serializable;

/**
 * Clase que representa al usuario Cliente
 * Realiza pedidos y gestiona su carrito de compras
 */
public class Cliente extends Usuario implements Serializable {

    // Atributos específicos del cliente
    private String cumpleanios; // Formato: DD/MM/YYYY
    private int comprasRealizadas;
    private double totalGastado;
    private String vendedorAsignado; // Código del vendedor que lo registró
    private String clasificacion; // "NUEVO", "OCASIONAL", "FRECUENTE"

    /**
     * Constructor vacío
     */
    public Cliente() {
        super();
        this.tipoUsuario = "CLIENTE";
        this.comprasRealizadas = 0;
        this.totalGastado = 0.0;
        this.clasificacion = "NUEVO";
    }

    /**
     * Constructor con parámetros
     * @param codigo Código único del cliente
     * @param nombre Nombre completo
     * @param genero Género
     * @param cumpleanios Fecha de cumpleaños
     * @param contrasenia Contraseña
     * @param vendedorAsignado Código del vendedor que lo registró
     */
    public Cliente(String codigo, String nombre, String genero, String cumpleanios,
                   String contrasenia, String vendedorAsignado) {
        super(codigo, nombre, genero, contrasenia, "CLIENTE");
        this.cumpleanios = cumpleanios;
        this.comprasRealizadas = 0;
        this.totalGastado = 0.0;
        this.vendedorAsignado = vendedorAsignado;
        this.clasificacion = "NUEVO";
    }

    // ==================== GETTERS Y SETTERS ====================

    public String getCumpleanios() {
        return cumpleanios;
    }

    public void setCumpleanios(String cumpleanios) {
        this.cumpleanios = cumpleanios;
    }

    public int getComprasRealizadas() {
        return comprasRealizadas;
    }

    public void setComprasRealizadas(int comprasRealizadas) {
        this.comprasRealizadas = comprasRealizadas;
    }

    public double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(double totalGastado) {
        this.totalGastado = totalGastado;
    }

    public String getVendedorAsignado() {
        return vendedorAsignado;
    }

    public void setVendedorAsignado(String vendedorAsignado) {
        this.vendedorAsignado = vendedorAsignado;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    // ==================== MÉTODOS ESPECÍFICOS ====================

    /**
     * Incrementa el contador de compras realizadas
     */
    public void incrementarCompras() {
        this.comprasRealizadas++;
        actualizarClasificacion();
    }

    /**
     * Agrega un monto al total gastado
     * @param monto Monto de la compra
     */
    public void agregarGasto(double monto) {
        this.totalGastado += monto;
    }

    /**
     * Actualiza la clasificación del cliente según sus compras
     */
    private void actualizarClasificacion() {
        if (comprasRealizadas >= 10) {
            this.clasificacion = "FRECUENTE";
        } else if (comprasRealizadas >= 3) {
            this.clasificacion = "OCASIONAL";
        } else {
            this.clasificacion = "NUEVO";
        }
    }

    /**
     * Calcula el promedio de gasto por compra
     * @return Promedio de gasto
     */
    public double calcularPromedioGasto() {
        if (comprasRealizadas == 0) {
            return 0.0;
        }
        return totalGastado / comprasRealizadas;
    }

    /**
     * Verifica si es el cumpleaños del cliente
     * @param fechaActual Fecha actual en formato DD/MM/YYYY
     * @return true si es su cumpleaños
     */
    public boolean esCumpleanios(String fechaActual) {
        if (cumpleanios == null || fechaActual == null) {
            return false;
        }

        String[] partesNacimiento = cumpleanios.split("/");
        String[] partesActual = fechaActual.split("/");

        if (partesNacimiento.length >= 2 && partesActual.length >= 2) {
            return partesNacimiento[0].equals(partesActual[0]) &&
                    partesNacimiento[1].equals(partesActual[1]);
        }

        return false;
    }

    // ==================== IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS ====================

    @Override
    public String obtenerInformacion() {
        return "=== CLIENTE ===\n" +
                "Código: " + codigo + "\n" +
                "Nombre: " + nombre + "\n" +
                "Género: " + genero + "\n" +
                "Cumpleaños: " + cumpleanios + "\n" +
                "Compras realizadas: " + comprasRealizadas + "\n" +
                "Total gastado: $" + String.format("%.2f", totalGastado) + "\n" +
                "Clasificación: " + clasificacion + "\n" +
                "Vendedor asignado: " + vendedorAsignado;
    }

    @Override
    public boolean tienePermiso(String accion) {
        // Permisos del cliente
        switch (accion.toUpperCase()) {
            case "VER_CATALOGO":
            case "AGREGAR_CARRITO":
            case "REALIZAR_PEDIDO":
            case "VER_HISTORIAL":
            case "GESTIONAR_CARRITO":
                return true;
            case "GESTIONAR_STOCK":
            case "CONFIRMAR_PEDIDOS":
            case "GESTIONAR_PRODUCTOS":
            case "GESTIONAR_USUARIOS":
                return false;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", cumpleanios='" + cumpleanios + '\'' +
                ", comprasRealizadas=" + comprasRealizadas +
                ", totalGastado=" + totalGastado +
                ", clasificacion='" + clasificacion + '\'' +
                '}';
    }
}