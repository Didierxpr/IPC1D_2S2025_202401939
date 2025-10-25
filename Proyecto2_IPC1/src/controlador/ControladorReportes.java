package controlador;

import modelo.Sistema;
import modelo.pedidos.Pedido;
import modelo.productos.Producto;
import modelo.usuarios.Cliente;
import modelo.estructuras.ListaProductos;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ControladorReportes {

    private void asegurarCarpetaReportes() {
        java.io.File carpeta = new java.io.File("data/reportes");
        if (!carpeta.exists()) {
            boolean creada = carpeta.mkdirs();
            if (creada)
                System.out.println("📂 Carpeta /data/reportes creada automáticamente.");
            else
                System.out.println("⚠️ No se pudo crear la carpeta /data/reportes.");
        }
    }

    // =============================================================
    // 🔹 MENÚ DE CONSOLA (opcional)
    // =============================================================
    public void menuReportes() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n===== REPORTES DEL SISTEMA =====");
            System.out.println("1. Top 3 productos más vendidos");
            System.out.println("2. Ingresos totales por categoría");
            System.out.println("3. Ventas totales por cliente");
            System.out.println("4. Exportar reporte a CSV");
            System.out.println("5. Volver al menú anterior");
            System.out.print("Seleccione una opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1" -> topProductos();
                case "2" -> ingresosPorCategoria();
                case "3" -> ventasPorCliente();
                case "4" -> exportarCSV();
                case "5" -> continuar = false;
                default -> System.out.println("⚠️ Opción inválida.");
            }
        }
    }

    // =============================================================
    // 🏆 TOP 3 PRODUCTOS MÁS VENDIDOS
    // =============================================================
    private void topProductos() {
        var listaPedidos = Sistema.getListaPedidos();
        if (listaPedidos.getCantidad() == 0) {
            System.out.println("⚠️ No hay pedidos registrados.");
            return;
        }

        ListaProductos lista = Sistema.getListaProductos();
        Producto[] productos = new Producto[lista.getCantidad()];
        for (int i = 0; i < lista.getCantidad(); i++)
            productos[i] = lista.getProducto(i);

        int[] contador = contarVentasPorProducto(productos);

        // Ordenar descendente
        ordenarPorVentas(productos, contador);

        System.out.println("\n=== TOP 3 PRODUCTOS MÁS VENDIDOS ===");
        for (int i = 0; i < 3 && i < productos.length && productos[i] != null; i++) {
            System.out.println((i + 1) + ". " + productos[i].getNombre() + " → " + contador[i] + " ventas");
        }

        Sistema.getRegistroBitacora().registrarEvento("REPORTE", "Generado Top 3 productos más vendidos");
    }

    // =============================================================
    // 💰 INGRESOS POR CATEGORÍA
    // =============================================================
    private void ingresosPorCategoria() {
        var listaPedidos = Sistema.getListaPedidos();
        if (listaPedidos.getCantidad() == 0) {
            System.out.println("⚠️ No hay pedidos registrados.");
            return;
        }

        double totalTec = 0, totalAli = 0, totalGen = 0;

        for (int i = 0; i < listaPedidos.getCantidad(); i++) {
            Pedido p = listaPedidos.getPedidos(i);
            Producto producto = p.getProducto();
            if (producto == null) continue;

            double monto = producto.getPrecio() * p.getCantidad();
            switch (producto.getCategoria().toLowerCase()) {
                case "tecnologia" -> totalTec += monto;
                case "alimento" -> totalAli += monto;
                default -> totalGen += monto;
            }
        }

        System.out.println("\n=== INGRESOS TOTALES POR CATEGORÍA ===");
        System.out.printf("Tecnología: Q%.2f\n", totalTec);
        System.out.printf("Alimentos:  Q%.2f\n", totalAli);
        System.out.printf("General:    Q%.2f\n", totalGen);
        System.out.printf("TOTAL:      Q%.2f\n", (totalTec + totalAli + totalGen));

        Sistema.getRegistroBitacora().registrarEvento("REPORTE", "Generado reporte de ingresos por categoría");
    }

    // =============================================================
    // 👥 VENTAS POR CLIENTE
    // =============================================================
    private void ventasPorCliente() {
        var listaPedidos = Sistema.getListaPedidos();
        var listaUsuarios = Sistema.getListaUsuarios();

        if (listaPedidos.getCantidad() == 0) {
            System.out.println("⚠️ No hay pedidos registrados.");
            return;
        }

        // Filtrar solo clientes
        List<Cliente> clientes = new ArrayList<>();
        for (int i = 0; i < listaUsuarios.getCantidad(); i++) {
            var u = listaUsuarios.getUsuario(i);
            if (u instanceof Cliente cliente) clientes.add(cliente);
        }

        if (clientes.isEmpty()) {
            System.out.println("⚠️ No hay clientes registrados.");
            return;
        }

        // Contar ventas
        double[] totales = new double[clientes.size()];
        for (int i = 0; i < listaPedidos.getCantidad(); i++) {
            Pedido pedido = listaPedidos.getPedidos(i);
            Cliente c = pedido.getCliente();
            for (int j = 0; j < clientes.size(); j++) {
                if (clientes.get(j).getCodigo().equalsIgnoreCase(c.getCodigo())) {
                    totales[j] += pedido.getTotal();
                }
            }
        }

        // Mostrar resultados
        System.out.println("\n=== VENTAS POR CLIENTE ===");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.printf("%-20s → Q%.2f\n", clientes.get(i).getNombre(), totales[i]);
        }

        Sistema.getRegistroBitacora().registrarEvento("REPORTE", "Generado reporte de ventas por cliente");
    }

    // =============================================================
    // 📄 EXPORTAR REPORTE GENERAL A CSV
    // =============================================================
    public void exportarCSV() {
        asegurarCarpetaReportes();

        try (FileWriter fw = new FileWriter("data/reportes/reportes.csv")) {
            fw.write("=== REPORTE GENERAL ===\n\n");
            fw.write("TOP 3 PRODUCTOS MÁS VENDIDOS\nPosición,Producto,Ventas\n");

            var listaProductos = Sistema.getListaProductos();
            Producto[] productos = new Producto[listaProductos.getCantidad()];
            for (int i = 0; i < listaProductos.getCantidad(); i++)
                productos[i] = listaProductos.getProducto(i);

            int[] contador = contarVentasPorProducto(productos);
            ordenarPorVentas(productos, contador);

            for (int i = 0; i < 3 && i < productos.length && productos[i] != null; i++) {
                fw.write((i + 1) + "," + productos[i].getNombre() + "," + contador[i] + "\n");
            }

            fw.write("\nCLIENTE,MONTO TOTAL (Q)\n");
            var listaUsuarios = Sistema.getListaUsuarios();

            for (int i = 0; i < listaUsuarios.getCantidad(); i++) {
                var u = listaUsuarios.getUsuario(i);
                if (u instanceof Cliente cliente) {
                    double total = calcularTotalCliente(cliente);
                    if (total > 0)
                        fw.write(cliente.getNombre() + ",Q" + String.format("%.2f", total) + "\n");
                }
            }

            fw.flush();
            System.out.println("✅ Reporte CSV exportado correctamente en: " + "data/reportes/reportes.html");
            Sistema.getRegistroBitacora().registrarEvento("REPORTE", "Exportado reporte general a CSV");

        } catch (IOException e) {
            System.out.println("❌ Error al exportar CSV: " + e.getMessage());
        }
    }

    // =============================================================
    // 🌐 EXPORTAR A HTML
    // =============================================================
    public void generarReporteGeneral() {
        asegurarCarpetaReportes();
        var listaPedidos = Sistema.getListaPedidos();

        if (listaPedidos.getCantidad() == 0) {
            System.out.println("⚠️ No hay pedidos registrados. No se puede generar reporte general.");
            return;
        }

        try (FileWriter writer = new FileWriter("data/reportes/reportes.html")) {
            writer.write("""
                <html><head><meta charset='UTF-8'><title>Reporte General</title>
                <style>
                body { font-family: Arial; margin: 20px; }
                table { border-collapse: collapse; width: 80%; margin-bottom: 25px; }
                th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
                th { background-color: #f2f2f2; }
                </style></head><body>
                <h1>📊 Reporte General del Sistema de Ventas</h1>
            """);

            writer.write("<p>Fecha de generación: " + java.time.LocalDateTime.now() + "</p>");
            escribirTopProductosHTML(writer);
            escribirVentasClientesHTML(writer);
            escribirVentasCategoriaHTML(writer);

            writer.write("</body></html>");
            System.out.println("✅ Reporte HTML generado correctamente en: " + "data/reportes/reportes.html");
            java.awt.Desktop.getDesktop().browse(new java.io.File("data/reportes/reportes.html").toURI());
            Sistema.getRegistroBitacora().registrarEvento("REPORTE", "Generado reporte general de ventas");

        } catch (IOException e) {
            System.out.println("❌ Error al generar el reporte general: " + e.getMessage());
        }
    }

    // =============================================================
    // 🧩 MÉTODOS AUXILIARES PRIVADOS
    // =============================================================
    private int[] contarVentasPorProducto(Producto[] productos) {
        int[] contador = new int[productos.length];
        var pedidos = Sistema.getListaPedidos().getPedidos();

        for (int i = 0; i < Sistema.getListaPedidos().getCantidad(); i++) {
            Pedido p = pedidos[i];
            for (int j = 0; j < productos.length; j++) {
                if (productos[j] != null && p.getProducto().getCodigo().equals(productos[j].getCodigo())) {
                    contador[j] += p.getCantidad();
                }
            }
        }
        return contador;
    }

    private void ordenarPorVentas(Producto[] productos, int[] contador) {
        for (int i = 0; i < contador.length - 1; i++) {
            for (int j = i + 1; j < contador.length; j++) {
                if (contador[j] > contador[i]) {
                    int temp = contador[i];
                    contador[i] = contador[j];
                    contador[j] = temp;
                    Producto tempP = productos[i];
                    productos[i] = productos[j];
                    productos[j] = tempP;
                }
            }
        }
    }

    private double calcularTotalCliente(Cliente cliente) {
        double total = 0;
        var pedidos = Sistema.getListaPedidos().getPedidos();
        for (int j = 0; j < Sistema.getListaPedidos().getCantidad(); j++) {
            var pedido = pedidos[j];
            if (pedido.getCliente().getCodigo().equals(cliente.getCodigo())) {
                total += pedido.getTotal();
            }
        }
        return total;
    }

    private void escribirTopProductosHTML(FileWriter writer) throws IOException {
        writer.write("<h2>🏆 Top 3 Productos Más Vendidos</h2>");
        writer.write("<table><tr><th>Posición</th><th>Producto</th><th>Ventas</th></tr>");

        var listaProductos = Sistema.getListaProductos();
        Producto[] productos = new Producto[listaProductos.getCantidad()];
        for (int i = 0; i < listaProductos.getCantidad(); i++)
            productos[i] = listaProductos.getProducto(i);

        int[] contador = contarVentasPorProducto(productos);
        ordenarPorVentas(productos, contador);

        for (int i = 0; i < 3 && i < productos.length && productos[i] != null; i++) {
            writer.write("<tr><td>" + (i + 1) + "</td><td>" +
                    productos[i].getNombre() + "</td><td>" + contador[i] + "</td></tr>");
        }
        writer.write("</table>");
    }

    private void escribirVentasClientesHTML(FileWriter writer) throws IOException {
        writer.write("<h2>💰 Ventas Totales por Cliente</h2>");
        writer.write("<table><tr><th>Cliente</th><th>Total Comprado (Q)</th></tr>");

        var listaUsuarios = Sistema.getListaUsuarios();
        for (int i = 0; i < listaUsuarios.getCantidad(); i++) {
            var u = listaUsuarios.getUsuario(i);
            if (u instanceof Cliente cliente) {
                double total = calcularTotalCliente(cliente);
                if (total > 0)
                    writer.write("<tr><td>" + cliente.getNombre() + "</td><td>Q" +
                            String.format("%.2f", total) + "</td></tr>");
            }
        }
        writer.write("</table>");
    }

    private void escribirVentasCategoriaHTML(FileWriter writer) throws IOException {
        writer.write("<h2>📦 Ventas Totales por Categoría</h2>");
        writer.write("<table><tr><th>Categoría</th><th>Total Ventas (Q)</th></tr>");

        double totalTec = 0, totalAli = 0, totalGen = 0;
        var pedidos = Sistema.getListaPedidos().getPedidos();
        for (int i = 0; i < Sistema.getListaPedidos().getCantidad(); i++) {
            var pedido = pedidos[i];
            var producto = pedido.getProducto();
            if (producto == null) continue;
            switch (producto.getCategoria().toLowerCase()) {
                case "tecnologia" -> totalTec += pedido.getTotal();
                case "alimento" -> totalAli += pedido.getTotal();
                default -> totalGen += pedido.getTotal();
            }
        }

        writer.write("<tr><td>Tecnología</td><td>Q" + String.format("%.2f", totalTec) + "</td></tr>");
        writer.write("<tr><td>Alimento</td><td>Q" + String.format("%.2f", totalAli) + "</td></tr>");
        writer.write("<tr><td>General</td><td>Q" + String.format("%.2f", totalGen) + "</td></tr>");
        writer.write("</table>");
    }
}

