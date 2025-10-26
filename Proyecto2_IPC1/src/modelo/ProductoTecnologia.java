package modelo;

import java.io.Serializable;

/**
 * Clase que representa productos de la categoría Tecnología
 * Atributo específico: meses de garantía
 */
public class ProductoTecnologia extends Producto implements Serializable {

    // Atributo específico
    private int mesesGarantia;

    /**
     * Constructor vacío
     */
    public ProductoTecnologia() {
        super();
        this.categoria = "TECNOLOGIA";
        this.mesesGarantia = 0;
    }

    /**
     * Constructor con parámetros
     * @param codigo Código único del producto
     * @param nombre Nombre del producto
     * @param precio Precio del producto
     * @param mesesGarantia Meses de garantía
     */
    public ProductoTecnologia(String codigo, String nombre, double precio, int mesesGarantia) {
        super(codigo, nombre, "TECNOLOGIA", precio);
        this.mesesGarantia = mesesGarantia;
    }

    // ==================== GETTERS Y SETTERS ====================

    public int getMesesGarantia() {
        return mesesGarantia;
    }

    public void setMesesGarantia(int mesesGarantia) {
        this.mesesGarantia = mesesGarantia;
    }

    // ==================== MÉTODOS ESPECÍFICOS ====================

    /**
     * Verifica si el producto aún tiene garantía vigente
     * @param mesesTranscurridos Meses desde la compra
     * @return true si aún tiene garantía
     */
    public boolean tieneGarantiaVigente(int mesesTranscurridos) {
        return mesesTranscurridos < this.mesesGarantia;
    }

    /**
     * Calcula los meses restantes de garantía
     * @param mesesTranscurridos Meses desde la compra
     * @return Meses restantes de garantía
     */
    public int mesesRestantesGarantia(int mesesTranscurridos) {
        int restantes = this.mesesGarantia - mesesTranscurridos;
        return (restantes > 0) ? restantes : 0;
    }

    // ==================== IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS ====================

    @Override
    public String obtenerAtributoEspecifico() {
        return "Garantía: " + mesesGarantia + " meses";
    }

    @Override
    public String obtenerDetalleCompleto() {
        return "╔════════════════════════════════════════╗\n" +
                "║      PRODUCTO TECNOLOGÍA              ║\n" +
                "╠════════════════════════════════════════╣\n" +
                "║ Código: " + String.format("%-28s", codigo) + "║\n" +
                "║ Nombre: " + String.format("%-28s", nombre) + "║\n" +
                "║ Categoría: TECNOLOGÍA                 ║\n" +
                "║ Precio: $" + String.format("%-26.2f", precio) + "║\n" +
                "║ Stock disponible: " + String.format("%-17d", stockDisponible) + "║\n" +
                "║ Garantía: " + String.format("%-24d", mesesGarantia) + " meses ║\n" +
                "║ Estado: " + String.format("%-28s", obtenerEstadoStock()) + "║\n" +
                "╚════════════════════════════════════════╝";
    }

    @Override
    public boolean validarCondicion() {
        // Un producto tecnológico está en buenas condiciones si tiene garantía
        return this.mesesGarantia > 0;
    }

    @Override
    public String toString() {
        return "ProductoTecnologia{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", mesesGarantia=" + mesesGarantia +
                ", stock=" + stockDisponible +
                '}';
    }
}
