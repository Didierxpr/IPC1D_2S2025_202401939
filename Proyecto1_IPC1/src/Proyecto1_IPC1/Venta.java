package Proyecto1_IPC1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Venta {

    String codigoProducto;
    int cantidadVendida;
    double totalVenta;
    String fechaHora;

    // Constructor
    public Venta(String codigoProducto, int cantidadVendida, double totalVenta) {
        this.codigoProducto = codigoProducto;
        this.cantidadVendida = cantidadVendida;
        this.totalVenta = totalVenta;

        // Asignar la fecha y hora actual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.fechaHora = LocalDateTime.now().format(formatter);
    }

    // Mostrar en consola
    public void mostrarVenta() {
        System.out.println("Código producto: " + codigoProducto);
        System.out.println("Cantidad vendida: " + cantidadVendida);
        System.out.println("Total venta: Q" + totalVenta);
        System.out.println("Fecha y hora: " + fechaHora);
    }

    // Para guardar en archivo
    public String formatoParaArchivo() {
        return codigoProducto + "," + cantidadVendida + "," + totalVenta + "," + fechaHora;
    }
}
