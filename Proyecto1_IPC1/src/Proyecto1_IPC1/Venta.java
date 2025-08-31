package Proyecto1_IPC1;

public class Venta {

    String codigoProducto;
    int cantidadVendida;
    double totalVenta;

    public Venta(String codigoProducto, int cantidadVendida, double totalVenta) {
        this.codigoProducto = codigoProducto;
        this.cantidadVendida = cantidadVendida;
        this.totalVenta = totalVenta;
    }

    public void mostrarVenta() {
        System.out.println("Código producto: " + codigoProducto);
        System.out.println("Cantidad vendida: " + cantidadVendida);
        System.out.println("Total venta: Q" + totalVenta);
    }
}
