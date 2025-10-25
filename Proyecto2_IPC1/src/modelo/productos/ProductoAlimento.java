package modelo.productos;

public class ProductoAlimento extends Producto {
    private String fechaExpiracion;

    public ProductoAlimento(String codigo, String nombre, int stock, double precio, String fechaExpiracion) {
        super(codigo, nombre, "ALIMENTO", stock, precio);
        this.fechaExpiracion = fechaExpiracion;
    }

    @Override
    public String verDetalle() {
        return nombre + " (Alimento) - Expira: " + fechaExpiracion;
    }
}



