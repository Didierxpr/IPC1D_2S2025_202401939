package modelo.productos;

public class ProductoGeneral extends Producto {
    private String material;

    public ProductoGeneral(String codigo, String nombre, int stock, double precio, String material) {
        super(codigo, nombre, "GENERAL", stock, precio);
        this.material = material;
    }

    @Override
    public String verDetalle() {
        return nombre + " (General) - Material: " + material;
    }
}



