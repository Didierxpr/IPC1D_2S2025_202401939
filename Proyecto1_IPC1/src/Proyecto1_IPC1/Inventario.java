package Proyecto1_IPC1;

public class Inventario {
    public static final int MAX_PRODUCTOS = 100;
    public static Producto[] productos = new Producto[MAX_PRODUCTOS];
    public static int totalProductos = 0;

    public static void agregarProducto(Producto producto) {
        if (totalProductos < MAX_PRODUCTOS) {
            productos[totalProductos] = producto;
            totalProductos++;
        }
    }
}
