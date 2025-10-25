package modelo.productos;

public class ProductoTecnologia extends Producto {
    private int mesesGarantia;

    public ProductoTecnologia(String codigo, String nombre, int stock, double precio, int mesesGarantia) {
        super(codigo, nombre, "TECNOLOGIA", stock, precio);
        this.mesesGarantia = mesesGarantia;
    }

    @Override
    public String verDetalle() {
        return nombre + " (Tecnología) - Garantía: " + mesesGarantia + " meses";
    }
}





