package modelo;

import java.io.Serializable;

/**
 * Clase que representa al usuario Vendedor
 * Gestiona stock, clientes y confirma pedidos
 */
public class Vendedor extends Usuario implements Serializable {

    // Atributos específicos del vendedor
    private int ventasConfirmadas;
    private int clientesRegistrados;
    private double comisionTotal;

    /**
     * Constructor vacío
     */
    public Vendedor() {
        super();
        this.tipoUsuario = "VENDEDOR";
        this.ventasConfirmadas = 0;
        this.clientesRegistrados = 0;
        this.comisionTotal = 0.0;
    }

    /**
     * Constructor con parámetros
     * @param codigo Código único del vendedor
     * @param nombre Nombre completo
     * @param genero Género
     * @param contrasenia Contraseña
     */
    public Vendedor(String codigo, String nombre, String genero, String contrasenia) {
        super(codigo, nombre, genero, contrasenia, "VENDEDOR");
        this.ventasConfirmadas = 0;
        this.clientesRegistrados = 0;
        this.comisionTotal = 0.0;
    }

    // ==================== GETTERS Y SETTERS ====================

    public int getVentasConfirmadas() {
        return ventasConfirmadas;
    }

    public void setVentasConfirmadas(int ventasConfirmadas) {
        this.ventasConfirmadas = ventasConfirmadas;
    }

    public int getClientesRegistrados() {
        return clientesRegistrados;
    }

    public void setClientesRegistrados(int clientesRegistrados) {
        this.clientesRegistrados = clientesRegistrados;
    }

    public double getComisionTotal() {
        return comisionTotal;
    }

    public void setComisionTotal(double comisionTotal) {
        this.comisionTotal = comisionTotal;
    }

    // ==================== MÉTODOS ESPECÍFICOS ====================

    /**
     * Incrementa el contador de ventas confirmadas
     */
    public void incrementarVentasConfirmadas() {
        this.ventasConfirmadas++;
    }

    /**
     * Incrementa el contador de clientes registrados
     */
    public void incrementarClientesRegistrados() {
        this.clientesRegistrados++;
    }

    /**
     * Agrega comisión al total
     * @param comision Monto de la comisión
     */
    public void agregarComision(double comision) {
        this.comisionTotal += comision;
    }

    /**
     * Calcula el promedio de ventas por día
     * @param diasTrabajados Días trabajados
     * @return Promedio de ventas
     */
    public double calcularPromedioVentasPorDia(int diasTrabajados) {
        if (diasTrabajados <= 0) {
            return 0.0;
        }
        return (double) ventasConfirmadas / diasTrabajados;
    }

    // ==================== IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS ====================

    @Override
    public String obtenerInformacion() {
        return "=== VENDEDOR ===\n" +
                "Código: " + codigo + "\n" +
                "Nombre: " + nombre + "\n" +
                "Género: " + genero + "\n" +
                "Ventas confirmadas: " + ventasConfirmadas + "\n" +
                "Clientes registrados: " + clientesRegistrados + "\n" +
                "Comisión total: $" + String.format("%.2f", comisionTotal);
    }

    @Override
    public boolean tienePermiso(String accion) {
        // Permisos del vendedor
        switch (accion.toUpperCase()) {
            case "GESTIONAR_STOCK":
            case "GESTIONAR_CLIENTES":
            case "CONFIRMAR_PEDIDOS":
            case "VER_INVENTARIO":
            case "AGREGAR_STOCK":
                return true;
            case "GESTIONAR_VENDEDORES":
            case "GESTIONAR_PRODUCTOS":
            case "ELIMINAR_PRODUCTOS":
                return false;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", ventasConfirmadas=" + ventasConfirmadas +
                ", clientesRegistrados=" + clientesRegistrados +
                ", comisionTotal=" + comisionTotal +
                '}';
    }
}
