package utilidades;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import interfaces.IReporte;
import modelo.*;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase para generación de reportes en formato PDF
 * Utiliza iText 5 para crear documentos PDF
 */
public class GeneradorPDF {

    // Colores del tema
    private static final BaseColor COLOR_HEADER = new BaseColor(41, 128, 185);
    private static final BaseColor COLOR_HEADER_TEXT = BaseColor.WHITE;
    private static final BaseColor COLOR_TABLE_HEADER = new BaseColor(52, 152, 219);
    private static final BaseColor COLOR_ALT_ROW = new BaseColor(236, 240, 241);

    // Fuentes
    private static Font fuenteTitulo;
    private static Font fuenteSubtitulo;
    private static Font fuenteNormal;
    private static Font fuenteNegrita;
    private static Font fuenteTablaHeader;

    static {
        try {
            fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
            fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            fuenteNegrita = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK);
            fuenteTablaHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea la estructura base del PDF con encabezado
     */
    private static Document crearDocumentoBase(String nombreArchivo, String titulo) throws Exception {
        Document documento = new Document(PageSize.LETTER);
        PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
        documento.open();

        // Agregar encabezado
        agregarEncabezado(documento, titulo);

        return documento;
    }

    /**
     * Agrega el encabezado del documento
     */
    private static void agregarEncabezado(Document documento, String titulo) throws DocumentException {
        // Tabla para el encabezado
        PdfPTable tablaHeader = new PdfPTable(1);
        tablaHeader.setWidthPercentage(100);
        tablaHeader.setSpacingAfter(20);

        PdfPCell celdaHeader = new PdfPCell();
        celdaHeader.setBackgroundColor(COLOR_HEADER);
        celdaHeader.setPadding(15);
        celdaHeader.setBorder(Rectangle.NO_BORDER);

        // Logo/Título de la empresa
        Paragraph empresa = new Paragraph("SANCARLISTA SHOP",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, COLOR_HEADER_TEXT));
        empresa.setAlignment(Element.ALIGN_CENTER);
        celdaHeader.addElement(empresa);

        // Título del reporte
        Paragraph tituloReporte = new Paragraph(titulo,
                FontFactory.getFont(FontFactory.HELVETICA, 14, COLOR_HEADER_TEXT));
        tituloReporte.setAlignment(Element.ALIGN_CENTER);
        tituloReporte.setSpacingBefore(5);
        celdaHeader.addElement(tituloReporte);

        // Fecha de generación
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Paragraph fecha = new Paragraph("Generado: " + sdf.format(new Date()),
                FontFactory.getFont(FontFactory.HELVETICA, 9, COLOR_HEADER_TEXT));
        fecha.setAlignment(Element.ALIGN_CENTER);
        fecha.setSpacingBefore(5);
        celdaHeader.addElement(fecha);

        tablaHeader.addCell(celdaHeader);
        documento.add(tablaHeader);
    }

    /**
     * Agrega el pie de página con información adicional
     */
    private static void agregarPiePagina(Document documento) throws DocumentException {
        Paragraph pie = new Paragraph(
                "Este reporte fue generado automáticamente por el Sistema Sancarlista Shop",
                FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8, BaseColor.GRAY)
        );
        pie.setAlignment(Element.ALIGN_CENTER);
        pie.setSpacingBefore(20);
        documento.add(pie);
    }

    // ==================== REPORTE: PRODUCTOS MÁS VENDIDOS ====================

    /**
     * Genera reporte de productos más vendidos (Top 5)
     */
    public static boolean generarReporteProductosMasVendidos(String nombreArchivo, Producto[] productos) {
        Document documento = null;

        try {
            documento = crearDocumentoBase(nombreArchivo, "REPORTE DE PRODUCTOS MÁS VENDIDOS");

            // Ordenar productos por cantidad vendida (descendente)
            Producto[] productosOrdenados = ordenarPorVentas(productos, false);

            // Tomar solo los primeros 5
            int limite = Math.min(5, productosOrdenados.length);

            // Crear tabla
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1f, 3f, 2f, 2f, 2f});
            tabla.setSpacingBefore(10);

            // Encabezados
            agregarCeldaHeader(tabla, "#");
            agregarCeldaHeader(tabla, "Producto");
            agregarCeldaHeader(tabla, "Categoría");
            agregarCeldaHeader(tabla, "Cantidad Vendida");
            agregarCeldaHeader(tabla, "Ingresos");

            // Datos
            for (int i = 0; i < limite; i++) {
                Producto p = productosOrdenados[i];

                agregarCelda(tabla, String.valueOf(i + 1), i % 2 == 0);
                agregarCelda(tabla, p.getNombre(), i % 2 == 0);
                agregarCelda(tabla, p.getCategoria(), i % 2 == 0);
                agregarCelda(tabla, String.valueOf(p.getCantidadVendida()), i % 2 == 0);
                agregarCelda(tabla, "$" + String.format("%.2f", p.calcularIngresos()), i % 2 == 0);
            }

            documento.add(tabla);

            // Resumen
            double totalIngresos = 0;
            int totalVendido = 0;
            for (int i = 0; i < limite; i++) {
                totalIngresos += productosOrdenados[i].calcularIngresos();
                totalVendido += productosOrdenados[i].getCantidadVendida();
            }

            Paragraph resumen = new Paragraph(
                    "\nTotal vendido (Top 5): " + totalVendido + " unidades\n" +
                            "Ingresos totales (Top 5): $" + String.format("%.2f", totalIngresos),
                    fuenteNegrita
            );
            resumen.setSpacingBefore(15);
            documento.add(resumen);

            agregarPiePagina(documento);
            documento.close();

            System.out.println("✓ Reporte generado: " + nombreArchivo);
            return true;

        } catch (Exception e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
            e.printStackTrace();
            if (documento != null && documento.isOpen()) {
                documento.close();
            }
            return false;
        }
    }

    // ==================== REPORTE: PRODUCTOS MENOS VENDIDOS ====================

    /**
     * Genera reporte de productos menos vendidos (Top 5)
     */
    public static boolean generarReporteProductosMenosVendidos(String nombreArchivo, Producto[] productos) {
        Document documento = null;

        try {
            documento = crearDocumentoBase(nombreArchivo, "REPORTE DE PRODUCTOS MENOS VENDIDOS");

            // Ordenar productos por cantidad vendida (ascendente)
            Producto[] productosOrdenados = ordenarPorVentas(productos, true);

            // Tomar solo los primeros 5
            int limite = Math.min(5, productosOrdenados.length);

            // Crear tabla
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1f, 3f, 2f, 2f, 3f});
            tabla.setSpacingBefore(10);

            // Encabezados
            agregarCeldaHeader(tabla, "#");
            agregarCeldaHeader(tabla, "Producto");
            agregarCeldaHeader(tabla, "Categoría");
            agregarCeldaHeader(tabla, "Cantidad Vendida");
            agregarCeldaHeader(tabla, "Stock Actual");

            // Datos
            for (int i = 0; i < limite; i++) {
                Producto p = productosOrdenados[i];

                agregarCelda(tabla, String.valueOf(i + 1), i % 2 == 0);
                agregarCelda(tabla, p.getNombre(), i % 2 == 0);
                agregarCelda(tabla, p.getCategoria(), i % 2 == 0);
                agregarCelda(tabla, String.valueOf(p.getCantidadVendida()), i % 2 == 0);
                agregarCelda(tabla, String.valueOf(p.getStockDisponible()), i % 2 == 0);
            }

            documento.add(tabla);

            // Recomendaciones
            Paragraph recomendaciones = new Paragraph("\nRecomendaciones:", fuenteSubtitulo);
            recomendaciones.setSpacingBefore(15);
            documento.add(recomendaciones);

            List lista = new List(List.UNORDERED);
            lista.setListSymbol("• ");
            lista.add(new ListItem("Considerar promociones o descuentos para estos productos", fuenteNormal));
            lista.add(new ListItem("Evaluar la demanda del mercado para estos artículos", fuenteNormal));
            lista.add(new ListItem("Revisar estrategia de marketing y visibilidad", fuenteNormal));
            lista.add(new ListItem("Analizar si es necesario reducir inventario", fuenteNormal));
            documento.add(lista);

            agregarPiePagina(documento);
            documento.close();

            System.out.println("✓ Reporte generado: " + nombreArchivo);
            return true;

        } catch (Exception e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
            e.printStackTrace();
            if (documento != null && documento.isOpen()) {
                documento.close();
            }
            return false;
        }
    }

    // ==================== REPORTE: INVENTARIO ====================

    /**
     * Genera reporte de inventario con productos de stock crítico/bajo
     */
    public static boolean generarReporteInventario(String nombreArchivo, Producto[] productos) {
        Document documento = null;

        try {
            documento = crearDocumentoBase(nombreArchivo, "REPORTE DE INVENTARIO - STOCK CRÍTICO Y BAJO");

            // Filtrar productos con stock crítico o bajo
            Producto[] productosAlerta = new Producto[0];
            for (Producto p : productos) {
                if (p.esStockCritico() || p.esStockBajo()) {
                    productosAlerta = (Producto[]) Vectores.agregar(productosAlerta, p);
                }
            }

            if (productosAlerta.length == 0) {
                Paragraph mensaje = new Paragraph("No hay productos con stock crítico o bajo.", fuenteNormal);
                mensaje.setAlignment(Element.ALIGN_CENTER);
                documento.add(mensaje);
            } else {
                // Crear tabla
                PdfPTable tabla = new PdfPTable(6);
                tabla.setWidthPercentage(100);
                tabla.setWidths(new float[]{2f, 3f, 2f, 2f, 2f, 3f});
                tabla.setSpacingBefore(10);

                // Encabezados
                agregarCeldaHeader(tabla, "Código");
                agregarCeldaHeader(tabla, "Producto");
                agregarCeldaHeader(tabla, "Categoría");
                agregarCeldaHeader(tabla, "Stock");
                agregarCeldaHeader(tabla, "Estado");
                agregarCeldaHeader(tabla, "Sugerencia");

                // Datos
                for (int i = 0; i < productosAlerta.length; i++) {
                    Producto p = productosAlerta[i];
                    String estado = p.esStockCritico() ? "CRÍTICO" : "BAJO";
                    String sugerencia = p.esStockCritico() ?
                            "Reabastecer urgente" : "Reabastecer pronto";

                    agregarCelda(tabla, p.getCodigo(), i % 2 == 0);
                    agregarCelda(tabla, p.getNombre(), i % 2 == 0);
                    agregarCelda(tabla, p.getCategoria(), i % 2 == 0);
                    agregarCelda(tabla, String.valueOf(p.getStockDisponible()), i % 2 == 0);

                    // Celda de estado con color
                    PdfPCell celdaEstado = new PdfPCell(new Phrase(estado, fuenteNormal));
                    celdaEstado.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celdaEstado.setPadding(5);
                    if (p.esStockCritico()) {
                        celdaEstado.setBackgroundColor(new BaseColor(231, 76, 60)); // Rojo
                    } else {
                        celdaEstado.setBackgroundColor(new BaseColor(241, 196, 15)); // Amarillo
                    }
                    tabla.addCell(celdaEstado);

                    agregarCelda(tabla, sugerencia, i % 2 == 0);
                }

                documento.add(tabla);

                // Resumen
                int criticos = 0;
                int bajos = 0;
                for (Producto p : productosAlerta) {
                    if (p.esStockCritico()) criticos++;
                    else bajos++;
                }

                Paragraph resumen = new Paragraph(
                        "\nProductos con stock crítico: " + criticos + "\n" +
                                "Productos con stock bajo: " + bajos + "\n" +
                                "Total productos en alerta: " + productosAlerta.length,
                        fuenteNegrita
                );
                resumen.setSpacingBefore(15);
                documento.add(resumen);
            }

            agregarPiePagina(documento);
            documento.close();

            System.out.println("✓ Reporte generado: " + nombreArchivo);
            return true;

        } catch (Exception e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
            e.printStackTrace();
            if (documento != null && documento.isOpen()) {
                documento.close();
            }
            return false;
        }
    }

    // ==================== REPORTE: VENTAS POR VENDEDOR ====================

    /**
     * Genera reporte de ventas por vendedor
     */
    public static boolean generarReporteVentasPorVendedor(String nombreArchivo, Vendedor[] vendedores, Pedido[] pedidos) {
        Document documento = null;

        try {
            documento = crearDocumentoBase(nombreArchivo, "REPORTE DE VENTAS POR VENDEDOR");

            if (vendedores == null || vendedores.length == 0) {
                Paragraph mensaje = new Paragraph("No hay vendedores registrados.", fuenteNormal);
                mensaje.setAlignment(Element.ALIGN_CENTER);
                documento.add(mensaje);
                agregarPiePagina(documento);
                documento.close();
                return true;
            }

            // Crear tabla
            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{2f, 3f, 2f, 2f, 2f, 2f});
            tabla.setSpacingBefore(10);

            // Encabezados
            agregarCeldaHeader(tabla, "Código");
            agregarCeldaHeader(tabla, "Nombre");
            agregarCeldaHeader(tabla, "Pedidos Confirmados");
            agregarCeldaHeader(tabla, "Clientes");
            agregarCeldaHeader(tabla, "Comisión");
            agregarCeldaHeader(tabla, "Ranking");

            // Ordenar vendedores por ventas confirmadas
            Vendedor[] vendedoresOrdenados = ordenarVendedoresPorVentas(vendedores);

            // Datos
            for (int i = 0; i < vendedoresOrdenados.length; i++) {
                Vendedor v = vendedoresOrdenados[i];

                agregarCelda(tabla, v.getCodigo(), i % 2 == 0);
                agregarCelda(tabla, v.getNombre(), i % 2 == 0);
                agregarCelda(tabla, String.valueOf(v.getVentasConfirmadas()), i % 2 == 0);
                agregarCelda(tabla, String.valueOf(v.getClientesRegistrados()), i % 2 == 0);
                agregarCelda(tabla, "$" + String.format("%.2f", v.getComisionTotal()), i % 2 == 0);
                agregarCelda(tabla, String.valueOf(i + 1), i % 2 == 0);
            }

            documento.add(tabla);

            // Estadísticas generales
            int totalVentas = 0;
            double totalComisiones = 0;
            int totalClientes = 0;

            for (Vendedor v : vendedores) {
                totalVentas += v.getVentasConfirmadas();
                totalComisiones += v.getComisionTotal();
                totalClientes += v.getClientesRegistrados();
            }

            Paragraph estadisticas = new Paragraph(
                    "\nEstadísticas Generales:\n" +
                            "Total de vendedores: " + vendedores.length + "\n" +
                            "Total de ventas confirmadas: " + totalVentas + "\n" +
                            "Total de clientes registrados: " + totalClientes + "\n" +
                            "Total de comisiones: $" + String.format("%.2f", totalComisiones),
                    fuenteNegrita
            );
            estadisticas.setSpacingBefore(15);
            documento.add(estadisticas);

            agregarPiePagina(documento);
            documento.close();

            System.out.println("✓ Reporte generado: " + nombreArchivo);
            return true;

        } catch (Exception e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
            e.printStackTrace();
            if (documento != null && documento.isOpen()) {
                documento.close();
            }
            return false;
        }
    }

    // ==================== REPORTE: CLIENTES ACTIVOS ====================

    /**
     * Genera reporte de clientes activos
     */
    public static boolean generarReporteClientesActivos(String nombreArchivo, Cliente[] clientes) {
        Document documento = null;

        try {
            documento = crearDocumentoBase(nombreArchivo, "REPORTE DE CLIENTES ACTIVOS");

            if (clientes == null || clientes.length == 0) {
                Paragraph mensaje = new Paragraph("No hay clientes registrados.", fuenteNormal);
                mensaje.setAlignment(Element.ALIGN_CENTER);
                documento.add(mensaje);
                agregarPiePagina(documento);
                documento.close();
                return true;
            }

            // Crear tabla
            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{2f, 3f, 2f, 2f, 2f, 2f});
            tabla.setSpacingBefore(10);

            // Encabezados
            agregarCeldaHeader(tabla, "Código");
            agregarCeldaHeader(tabla, "Nombre");
            agregarCeldaHeader(tabla, "Compras");
            agregarCeldaHeader(tabla, "Total Gastado");
            agregarCeldaHeader(tabla, "Promedio/Compra");
            agregarCeldaHeader(tabla, "Clasificación");

            // Ordenar clientes por total gastado
            Cliente[] clientesOrdenados = ordenarClientesPorGasto(clientes);

            // Datos
            for (int i = 0; i < clientesOrdenados.length; i++) {
                Cliente c = clientesOrdenados[i];

                agregarCelda(tabla, c.getCodigo(), i % 2 == 0);
                agregarCelda(tabla, c.getNombre(), i % 2 == 0);
                agregarCelda(tabla, String.valueOf(c.getComprasRealizadas()), i % 2 == 0);
                agregarCelda(tabla, "$" + String.format("%.2f", c.getTotalGastado()), i % 2 == 0);
                agregarCelda(tabla, "$" + String.format("%.2f", c.calcularPromedioGasto()), i % 2 == 0);
                agregarCelda(tabla, c.getClasificacion(), i % 2 == 0);
            }

            documento.add(tabla);

            // Estadísticas por clasificación
            int frecuentes = 0;
            int ocasionales = 0;
            int nuevos = 0;
            double totalVentas = 0;

            for (Cliente c : clientes) {
                switch (c.getClasificacion()) {
                    case "FRECUENTE": frecuentes++; break;
                    case "OCASIONAL": ocasionales++; break;
                    case "NUEVO": nuevos++; break;
                }
                totalVentas += c.getTotalGastado();
            }

            Paragraph estadisticas = new Paragraph(
                    "\nClasificación de Clientes:\n" +
                            "Frecuentes: " + frecuentes + "\n" +
                            "Ocasionales: " + ocasionales + "\n" +
                            "Nuevos: " + nuevos + "\n\n" +
                            "Total en ventas: $" + String.format("%.2f", totalVentas),
                    fuenteNegrita
            );
            estadisticas.setSpacingBefore(15);
            documento.add(estadisticas);

            agregarPiePagina(documento);
            documento.close();

            System.out.println("✓ Reporte generado: " + nombreArchivo);
            return true;

        } catch (Exception e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
            e.printStackTrace();
            if (documento != null && documento.isOpen()) {
                documento.close();
            }
            return false;
        }
    }

    // ==================== REPORTE: FINANCIERO ====================

    /**
     * Genera reporte financiero por categorías
     */
    public static boolean generarReporteFinanciero(String nombreArchivo, Producto[] productos) {
        Document documento = null;

        try {
            documento = crearDocumentoBase(nombreArchivo, "REPORTE FINANCIERO POR CATEGORÍAS");

            if (productos == null || productos.length == 0) {
                Paragraph mensaje = new Paragraph("No hay productos registrados.", fuenteNormal);
                mensaje.setAlignment(Element.ALIGN_CENTER);
                documento.add(mensaje);
                agregarPiePagina(documento);
                documento.close();
                return true;
            }

            // Calcular datos por categoría
            String[] categorias = {"TECNOLOGIA", "ALIMENTO", "GENERAL"};
            int[] cantidadesPorCategoria = new int[3];
            double[] ingresosPorCategoria = new double[3];
            double[] promediosPorCategoria = new double[3];

            for (Producto p : productos) {
                int indice = obtenerIndiceCategoria(p.getCategoria());
                if (indice != -1) {
                    cantidadesPorCategoria[indice] += p.getCantidadVendida();
                    ingresosPorCategoria[indice] += p.calcularIngresos();
                }
            }

            // Calcular promedios y total
            double totalIngresos = 0;
            int totalVendido = 0;

            for (int i = 0; i < 3; i++) {
                totalIngresos += ingresosPorCategoria[i];
                totalVendido += cantidadesPorCategoria[i];

                if (cantidadesPorCategoria[i] > 0) {
                    promediosPorCategoria[i] = ingresosPorCategoria[i] / cantidadesPorCategoria[i];
                }
            }

            // Crear tabla
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{2f, 2f, 2f, 2f, 2f});
            tabla.setSpacingBefore(10);

            // Encabezados
            agregarCeldaHeader(tabla, "Categoría");
            agregarCeldaHeader(tabla, "Cantidad Vendida");
            agregarCeldaHeader(tabla, "Ingresos Totales");
            agregarCeldaHeader(tabla, "% Participación");
            agregarCeldaHeader(tabla, "Promedio/Unidad");

            // Datos
            for (int i = 0; i < 3; i++) {
                double porcentaje = totalIngresos > 0 ?
                        (ingresosPorCategoria[i] / totalIngresos) * 100 : 0;

                agregarCelda(tabla, categorias[i], i % 2 == 0);
                agregarCelda(tabla, String.valueOf(cantidadesPorCategoria[i]), i % 2 == 0);
                agregarCelda(tabla, "$" + String.format("%.2f", ingresosPorCategoria[i]), i % 2 == 0);
                agregarCelda(tabla, String.format("%.2f%%", porcentaje), i % 2 == 0);
                agregarCelda(tabla, "$" + String.format("%.2f", promediosPorCategoria[i]), i % 2 == 0);
            }

            documento.add(tabla);

            // Resumen financiero
            Paragraph resumen = new Paragraph(
                    "\nResumen Financiero:\n" +
                            "Total de productos vendidos: " + totalVendido + " unidades\n" +
                            "Ingresos totales: $" + String.format("%.2f", totalIngresos) + "\n" +
                            "Promedio general por unidad: $" + String.format("%.2f",
                            totalVendido > 0 ? totalIngresos / totalVendido : 0),
                    fuenteNegrita
            );
            resumen.setSpacingBefore(15);
            documento.add(resumen);

            // Categoría más rentable
            int categoriaTopIndex = 0;
            double maxIngresos = ingresosPorCategoria[0];
            for (int i = 1; i < 3; i++) {
                if (ingresosPorCategoria[i] > maxIngresos) {
                    maxIngresos = ingresosPorCategoria[i];
                    categoriaTopIndex = i;
                }
            }

            Paragraph categoriaTop = new Paragraph(
                    "\nCategoría más rentable: " + categorias[categoriaTopIndex] +
                            " con $" + String.format("%.2f", maxIngresos),
                    fuenteNegrita
            );
            categoriaTop.setSpacingBefore(10);
            documento.add(categoriaTop);

            agregarPiePagina(documento);
            documento.close();

            System.out.println("✓ Reporte generado: " + nombreArchivo);
            return true;

        } catch (Exception e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
            e.printStackTrace();
            if (documento != null && documento.isOpen()) {
                documento.close();
            }
            return false;
        }
    }

    // ==================== REPORTE: PRODUCTOS POR CADUCAR ====================

    /**
     * Genera reporte de productos alimenticios próximos a caducar
     */
    public static boolean generarReporteProductosPorCaducar(String nombreArchivo, Producto[] productos) {
        Document documento = null;

        try {
            documento = crearDocumentoBase(nombreArchivo, "REPORTE DE PRODUCTOS POR CADUCAR");

            // Filtrar solo productos de alimento
            Producto[] alimentosProximos = new Producto[0];
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaActual = sdf.format(new Date());

            for (Producto p : productos) {
                if (p instanceof ProductoAlimento) {
                    ProductoAlimento alimento = (ProductoAlimento) p;
                    int diasRestantes = alimento.diasHastaCaducidad(fechaActual);

                    // Solo productos que caducan en menos de 30 días
                    if (diasRestantes >= 0 && diasRestantes <= 30) {
                        alimentosProximos = (Producto[]) Vectores.agregar(alimentosProximos, p);
                    }
                }
            }

            if (alimentosProximos.length == 0) {
                Paragraph mensaje = new Paragraph(
                        "No hay productos alimenticios próximos a caducar en los próximos 30 días.",
                        fuenteNormal
                );
                mensaje.setAlignment(Element.ALIGN_CENTER);
                documento.add(mensaje);
            } else {
                // Ordenar por días restantes
                alimentosProximos = ordenarPorCaducidad(alimentosProximos, fechaActual);

                // Crear tabla
                PdfPTable tabla = new PdfPTable(6);
                tabla.setWidthPercentage(100);
                tabla.setWidths(new float[]{2f, 3f, 2f, 2f, 2f, 3f});
                tabla.setSpacingBefore(10);

                // Encabezados
                agregarCeldaHeader(tabla, "Código");
                agregarCeldaHeader(tabla, "Producto");
                agregarCeldaHeader(tabla, "Fecha Caducidad");
                agregarCeldaHeader(tabla, "Días Restantes");
                agregarCeldaHeader(tabla, "Stock");
                agregarCeldaHeader(tabla, "Prioridad");

                // Datos
                for (int i = 0; i < alimentosProximos.length; i++) {
                    ProductoAlimento p = (ProductoAlimento) alimentosProximos[i];
                    int diasRestantes = p.diasHastaCaducidad(fechaActual);
                    String prioridad = p.obtenerPrioridadVenta(fechaActual);

                    agregarCelda(tabla, p.getCodigo(), i % 2 == 0);
                    agregarCelda(tabla, p.getNombre(), i % 2 == 0);
                    agregarCelda(tabla, p.getFechaCaducidad(), i % 2 == 0);
                    agregarCelda(tabla, String.valueOf(diasRestantes), i % 2 == 0);
                    agregarCelda(tabla, String.valueOf(p.getStockDisponible()), i % 2 == 0);

                    // Celda de prioridad con color
                    PdfPCell celdaPrioridad = new PdfPCell(new Phrase(prioridad, fuenteNormal));
                    celdaPrioridad.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celdaPrioridad.setPadding(5);

                    if (prioridad.equals("CRÍTICO")) {
                        celdaPrioridad.setBackgroundColor(new BaseColor(231, 76, 60)); // Rojo
                    } else if (prioridad.equals("URGENTE")) {
                        celdaPrioridad.setBackgroundColor(new BaseColor(241, 196, 15)); // Amarillo
                    } else {
                        celdaPrioridad.setBackgroundColor(i % 2 == 0 ? COLOR_ALT_ROW : BaseColor.WHITE);
                    }

                    tabla.addCell(celdaPrioridad);
                }

                documento.add(tabla);

                // Calcular valor en riesgo
                double valorRiesgo = 0;
                int criticos = 0;
                int urgentes = 0;

                for (Producto p : alimentosProximos) {
                    ProductoAlimento alimento = (ProductoAlimento) p;
                    valorRiesgo += alimento.calcularValorEnRiesgo();

                    String prioridad = alimento.obtenerPrioridadVenta(fechaActual);
                    if (prioridad.equals("CRÍTICO")) criticos++;
                    else if (prioridad.equals("URGENTE")) urgentes++;
                }

                // Estadísticas
                Paragraph estadisticas = new Paragraph(
                        "\nEstadísticas:\n" +
                                "Productos críticos (1-3 días): " + criticos + "\n" +
                                "Productos urgentes (4-7 días): " + urgentes + "\n" +
                                "Otros (8-30 días): " + (alimentosProximos.length - criticos - urgentes) + "\n" +
                                "Valor total en riesgo: $" + String.format("%.2f", valorRiesgo),
                        fuenteNegrita
                );
                estadisticas.setSpacingBefore(15);
                documento.add(estadisticas);

                // Recomendaciones
                Paragraph recomendaciones = new Paragraph("\nRecomendaciones Urgentes:", fuenteSubtitulo);
                recomendaciones.setSpacingBefore(15);
                documento.add(recomendaciones);

                List lista = new List(List.UNORDERED);
                lista.setListSymbol("• ");

                if (criticos > 0) {
                    lista.add(new ListItem("Aplicar descuentos del 30-50% a productos críticos", fuenteNormal));
                    lista.add(new ListItem("Promoción 2x1 para productos con menos de 3 días", fuenteNormal));
                }
                if (urgentes > 0) {
                    lista.add(new ListItem("Descuentos del 15-25% para productos urgentes", fuenteNormal));
                }
                lista.add(new ListItem("Notificar a clientes frecuentes sobre ofertas especiales", fuenteNormal));
                lista.add(new ListItem("Ubicar productos en zonas de alta visibilidad", fuenteNormal));

                documento.add(lista);
            }

            agregarPiePagina(documento);
            documento.close();

            System.out.println("✓ Reporte generado: " + nombreArchivo);
            return true;

        } catch (Exception e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
            e.printStackTrace();
            if (documento != null && documento.isOpen()) {
                documento.close();
            }
            return false;
        }
    }

    // ==================== MÉTODOS AUXILIARES PARA TABLAS ====================

    /**
     * Agrega una celda de encabezado a la tabla
     */
    private static void agregarCeldaHeader(PdfPTable tabla, String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuenteTablaHeader));
        celda.setBackgroundColor(COLOR_TABLE_HEADER);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(8);
        tabla.addCell(celda);
    }

    /**
     * Agrega una celda normal a la tabla
     */
    private static void agregarCelda(PdfPTable tabla, String texto, boolean alternada) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuenteNormal));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(5);

        if (alternada) {
            celda.setBackgroundColor(COLOR_ALT_ROW);
        }

        tabla.addCell(celda);
    }

    // ==================== MÉTODOS DE ORDENAMIENTO ====================

    /**
     * Ordena productos por cantidad vendida
     */
    private static Producto[] ordenarPorVentas(Producto[] productos, boolean ascendente) {
        if (productos == null || productos.length == 0) {
            return new Producto[0];
        }

        Producto[] ordenados = new Producto[productos.length];
        for (int i = 0; i < productos.length; i++) {
            ordenados[i] = productos[i];
        }

        // Bubble Sort
        for (int i = 0; i < ordenados.length - 1; i++) {
            for (int j = 0; j < ordenados.length - i - 1; j++) {
                boolean condicion = ascendente ?
                        ordenados[j].getCantidadVendida() > ordenados[j + 1].getCantidadVendida() :
                        ordenados[j].getCantidadVendida() < ordenados[j + 1].getCantidadVendida();

                if (condicion) {
                    Producto temp = ordenados[j];
                    ordenados[j] = ordenados[j + 1];
                    ordenados[j + 1] = temp;
                }
            }
        }

        return ordenados;
    }

    /**
     * Ordena vendedores por ventas confirmadas
     */
    private static Vendedor[] ordenarVendedoresPorVentas(Vendedor[] vendedores) {
        if (vendedores == null || vendedores.length == 0) {
            return new Vendedor[0];
        }

        Vendedor[] ordenados = new Vendedor[vendedores.length];
        for (int i = 0; i < vendedores.length; i++) {
            ordenados[i] = vendedores[i];
        }

        // Bubble Sort (descendente)
        for (int i = 0; i < ordenados.length - 1; i++) {
            for (int j = 0; j < ordenados.length - i - 1; j++) {
                if (ordenados[j].getVentasConfirmadas() < ordenados[j + 1].getVentasConfirmadas()) {
                    Vendedor temp = ordenados[j];
                    ordenados[j] = ordenados[j + 1];
                    ordenados[j + 1] = temp;
                }
            }
        }

        return ordenados;
    }

    /**
     * Ordena clientes por total gastado
     */
    private static Cliente[] ordenarClientesPorGasto(Cliente[] clientes) {
        if (clientes == null || clientes.length == 0) {
            return new Cliente[0];
        }

        Cliente[] ordenados = new Cliente[clientes.length];
        for (int i = 0; i < clientes.length; i++) {
            ordenados[i] = clientes[i];
        }

        // Bubble Sort (descendente)
        for (int i = 0; i < ordenados.length - 1; i++) {
            for (int j = 0; j < ordenados.length - i - 1; j++) {
                if (ordenados[j].getTotalGastado() < ordenados[j + 1].getTotalGastado()) {
                    Cliente temp = ordenados[j];
                    ordenados[j] = ordenados[j + 1];
                    ordenados[j + 1] = temp;
                }
            }
        }

        return ordenados;
    }

    /**
     * Ordena productos alimenticios por días hasta caducidad
     */
    private static Producto[] ordenarPorCaducidad(Producto[] productos, String fechaActual) {
        if (productos == null || productos.length == 0) {
            return new Producto[0];
        }

        Producto[] ordenados = new Producto[productos.length];
        for (int i = 0; i < productos.length; i++) {
            ordenados[i] = productos[i];
        }

        // Bubble Sort (ascendente por días)
        for (int i = 0; i < ordenados.length - 1; i++) {
            for (int j = 0; j < ordenados.length - i - 1; j++) {
                if (ordenados[j] instanceof ProductoAlimento &&
                        ordenados[j + 1] instanceof ProductoAlimento) {

                    ProductoAlimento a1 = (ProductoAlimento) ordenados[j];
                    ProductoAlimento a2 = (ProductoAlimento) ordenados[j + 1];

                    if (a1.diasHastaCaducidad(fechaActual) > a2.diasHastaCaducidad(fechaActual)) {
                        Producto temp = ordenados[j];
                        ordenados[j] = ordenados[j + 1];
                        ordenados[j + 1] = temp;
                    }
                }
            }
        }

        return ordenados;
    }

    /**
     * Obtiene el índice de una categoría
     */
    private static int obtenerIndiceCategoria(String categoria) {
        switch (categoria.toUpperCase()) {
            case "TECNOLOGIA": return 0;
            case "ALIMENTO": return 1;
            case "GENERAL": return 2;
            default: return -1;
        }
    }

    // ==================== MÉTODO PARA GENERAR NOMBRE DE ARCHIVO ====================

    /**
     * Genera un nombre de archivo con formato: DD_MM_YYYY_HH_mm_ss_TipoReporte.pdf
     */
    public static String generarNombreArchivo(String tipoReporte) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        String timestamp = sdf.format(new Date());

        // Asegurar que el directorio existe
        ManejadorArchivos.crearDirectorio("reportes/");

        return "reportes/" + timestamp + "_" + tipoReporte + ".pdf";
    }
}
