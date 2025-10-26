package modelo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Clase que representa productos de la categoría Alimento
 * Atributo específico: fecha de caducidad
 */
public class ProductoAlimento extends Producto implements Serializable {

    // Atributo específico
    private String fechaCaducidad; // Formato: DD/MM/YYYY

    /**
     * Constructor vacío
     */
    public ProductoAlimento() {
        super();
        this.categoria = "ALIMENTO";
        this.fechaCaducidad = "";
    }

    /**
     * Constructor con parámetros
     * @param codigo Código único del producto
     * @param nombre Nombre del producto
     * @param precio Precio del producto
     * @param fechaCaducidad Fecha de caducidad (DD/MM/YYYY)
     */
    public ProductoAlimento(String codigo, String nombre, double precio, String fechaCaducidad) {
        super(codigo, nombre, "ALIMENTO", precio);
        this.fechaCaducidad = fechaCaducidad;
    }

    // ==================== GETTERS Y SETTERS ====================

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    // ==================== MÉTODOS ESPECÍFICOS ====================

    /**
     * Calcula los días restantes hasta la caducidad
     * @param fechaActual Fecha actual en formato DD/MM/YYYY
     * @return Días restantes (negativo si ya caducó)
     */
    public int diasHastaCaducidad(String fechaActual) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date actual = sdf.parse(fechaActual);
            Date caducidad = sdf.parse(this.fechaCaducidad);

            long diferenciaMilisegundos = caducidad.getTime() - actual.getTime();
            return (int) TimeUnit.MILLISECONDS.toDays(diferenciaMilisegundos);
        } catch (ParseException e) {
            return -999; // Error en el formato de fecha
        }
    }

    /**
     * Verifica si el producto está caducado
     * @param fechaActual Fecha actual en formato DD/MM/YYYY
     * @return true si está caducado
     */
    public boolean estaCaducado(String fechaActual) {
        return diasHastaCaducidad(fechaActual) < 0;
    }

    /**
     * Verifica si el producto está próximo a caducar (menos de 7 días)
     * @param fechaActual Fecha actual en formato DD/MM/YYYY
     * @return true si está próximo a caducar
     */
    public boolean esProximoACaducar(String fechaActual) {
        int dias = diasHastaCaducidad(fechaActual);
        return dias >= 0 && dias <= 7;
    }

    /**
     * Obtiene la prioridad de venta según días hasta caducidad
     * @param fechaActual Fecha actual
     * @return CRÍTICO (1-3 días), URGENTE (4-7 días), NORMAL (más de 7 días)
     */
    public String obtenerPrioridadVenta(String fechaActual) {
        int dias = diasHastaCaducidad(fechaActual);

        if (dias < 0) {
            return "CADUCADO";
        } else if (dias >= 1 && dias <= 3) {
            return "CRÍTICO";
        } else if (dias >= 4 && dias <= 7) {
            return "URGENTE";
        } else {
            return "NORMAL";
        }
    }

    /**
     * Calcula el valor monetario en riesgo por caducidad
     * @return Valor total del stock disponible
     */
    public double calcularValorEnRiesgo() {
        return this.stockDisponible * this.precio;
    }

    // ==================== IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS ====================

    @Override
    public String obtenerAtributoEspecifico() {
        return "Fecha de caducidad: " + fechaCaducidad;
    }

    @Override
    public String obtenerDetalleCompleto() {
        return "╔════════════════════════════════════════╗\n" +
                "║      PRODUCTO ALIMENTO                ║\n" +
                "╠════════════════════════════════════════╣\n" +
                "║ Código: " + String.format("%-28s", codigo) + "║\n" +
                "║ Nombre: " + String.format("%-28s", nombre) + "║\n" +
                "║ Categoría: ALIMENTO                   ║\n" +
                "║ Precio: $" + String.format("%-26.2f", precio) + "║\n" +
                "║ Stock disponible: " + String.format("%-17d", stockDisponible) + "║\n" +
                "║ Fecha caducidad: " + String.format("%-19s", fechaCaducidad) + "║\n" +
                "║ Estado: " + String.format("%-28s", obtenerEstadoStock()) + "║\n" +
                "╚════════════════════════════════════════╝";
    }

    @Override
    public boolean validarCondicion() {
        // Un alimento está en buenas condiciones si no está caducado
        // Usamos fecha actual del sistema
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaActual = sdf.format(new Date());
        return !estaCaducado(fechaActual);
    }

    @Override
    public String toString() {
        return "ProductoAlimento{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", fechaCaducidad='" + fechaCaducidad + '\'' +
                ", stock=" + stockDisponible +
                '}';
    }
}
