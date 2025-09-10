package Proyecto1_IPC1;


import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;


import java.io.FileOutputStream;
import java.io.IOException;


public class ReportePDF {


    public static void generarReporteStock(Producto[] inventario, int totalProductos, String nombreArchivo) throws DocumentException, IOException {
        Document documento = new Document();
        PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
        documento.open();


        documento.add(new Paragraph("REPORTE DE STOCK"));
        documento.add(new Paragraph("-----------------------------"));


        for (int i = 0; i < totalProductos; i++) {
            Producto p = inventario[i];
            documento.add(new Paragraph("Nombre: " + p.getNombre()));
            documento.add(new Paragraph("Código: " + p.getCodigo()));
            documento.add(new Paragraph("Categoría: " + p.getCategoria()));
            documento.add(new Paragraph("Precio: Q" + p.getPrecio()));
            documento.add(new Paragraph("Cantidad disponible: " + p.getStock()));
            documento.add(new Paragraph("-----------------------------"));
        }


        documento.close();
    }


    public static void generarReporteVentas(Venta[] ventas, int totalVentas, String nombreArchivo) throws DocumentException, IOException {
        Document documento = new Document();
        PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
        documento.open();


        documento.add(new Paragraph("REPORTE DE VENTAS"));
        documento.add(new Paragraph("-----------------------------"));


        for (int i = 0; i < totalVentas; i++) {
            Venta v = ventas[i];
            documento.add(new Paragraph("Código del producto: " + v.codigoProducto));
            documento.add(new Paragraph("Cantidad vendida: " + v.cantidadVendida));
            documento.add(new Paragraph("Total de la venta: Q" + v.totalVenta));
            documento.add(new Paragraph("-----------------------------"));
        }


        documento.close();
    }
}
