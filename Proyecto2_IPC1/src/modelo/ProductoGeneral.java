package modelo;

import java.io.Serializable;

/**
 * Clase que representa productos de la categoría General
 * Atributo específico: material del producto
 */
public class ProductoGeneral extends Producto implements Serializable {

    // Atributo específico
    private String material;

    /**
     * Constructor vacío
     */
    public ProductoGeneral() {
        super();
        this.categoria = "GENERAL";
        this.material = "";
    }

    /**
     * Constructor con parámetros
     * @param codigo Código único del producto
     * @param nombre Nombre del producto
     * @param precio Precio del producto
     * @param material Material del producto
     */
    public ProductoGeneral(String codigo, String nombre, double precio, String material) {
        super(codigo, nombre, "GENERAL", precio);
        this.material = material;
    }

    // ==================== GETTERS Y SETTERS ====================

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    // ==================== MÉTODOS ESPECÍFICOS ====================

    /**
     * Verifica si el producto es de un material específico
     * @param materialBuscado Material a verificar
     * @return true si coincide
     */
    public boolean esDeMaterial(String materialBuscado) {
        return this.material.equalsIgnoreCase(materialBuscado);
    }

    /**
     * Verifica si el material es reciclable
     * @return true si es reciclable
     */
    public boolean esMaterialReciclable() {
        String materialLower = this.material.toLowerCase();
        return materialLower.contains("plastico") ||
                materialLower.contains("papel") ||
                materialLower.contains("carton") ||
                materialLower.contains("vidrio") ||
                materialLower.contains("metal") ||
                materialLower.contains("aluminio");
    }

    /**
     * Verifica si el material es frágil
     * @return true si es frágil
     */
    public boolean esMaterialFragil() {
        String materialLower = this.material.toLowerCase();
        return materialLower.contains("vidrio") ||
                materialLower.contains("ceramica") ||
                materialLower.contains("cristal");
    }

    /**
     * Obtiene recomendaciones de cuidado según el material
     * @return String con recomendaciones
     */
    public String obtenerRecomendacionesCuidado() {
        String materialLower = this.material.toLowerCase();

        if (materialLower.contains("madera")) {
            return "Evitar humedad excesiva. Limpiar con paño seco.";
        } else if (materialLower.contains("metal")) {
            return "Evitar oxidación. Limpiar con productos específicos.";
        } else if (materialLower.contains("plastico")) {
            return "Evitar exposición directa al sol. Lavar con agua tibia.";
        } else if (materialLower.contains("vidrio") || materialLower.contains("cristal")) {
            return "Manipular con cuidado. Material frágil.";
        } else if (materialLower.contains("tela") || materialLower.contains("textil")) {
            return "Lavar según etiqueta. Evitar blanqueadores fuertes.";
        } else {
            return "Seguir instrucciones del fabricante.";
        }
    }

    // ==================== IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS ====================

    @Override
    public String obtenerAtributoEspecifico() {
        return "Material: " + material;
    }

    @Override
    public String obtenerDetalleCompleto() {
        return "╔════════════════════════════════════════╗\n" +
                "║      PRODUCTO GENERAL                 ║\n" +
                "╠════════════════════════════════════════╣\n" +
                "║ Código: " + String.format("%-28s", codigo) + "║\n" +
                "║ Nombre: " + String.format("%-28s", nombre) + "║\n" +
                "║ Categoría: GENERAL                    ║\n" +
                "║ Precio: $" + String.format("%-26.2f", precio) + "║\n" +
                "║ Stock disponible: " + String.format("%-17d", stockDisponible) + "║\n" +
                "║ Material: " + String.format("%-26s", material) + "║\n" +
                "║ Reciclable: " + String.format("%-24s", (esMaterialReciclable() ? "Sí" : "No")) + "║\n" +
                "║ Estado: " + String.format("%-28s", obtenerEstadoStock()) + "║\n" +
                "╚════════════════════════════════════════╝";
    }

    @Override
    public boolean validarCondicion() {
        // Un producto general está en buenas condiciones si tiene material definido
        return this.material != null && !this.material.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "ProductoGeneral{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", material='" + material + '\'' +
                ", stock=" + stockDisponible +
                '}';
    }
}
