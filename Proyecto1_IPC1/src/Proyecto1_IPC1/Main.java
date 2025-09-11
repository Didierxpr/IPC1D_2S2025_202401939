package Proyecto1_IPC1;

import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    static Venta[] ventas = new Venta[50]; // máximo 50 ventas
    static int totalVentas = 0;
    private static final int MAX_PRODUCTOS = 50;
    static Producto[] inventario = new Producto[MAX_PRODUCTOS];
    static int totalProductos = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Registrar venta");
            System.out.println("5. Bitacora");
            System.out.println("6. Generacion de PDF's");
            System.out.println("7. Mostrar datos de estudiante");
            System.out.println("0. Salir");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    if (totalProductos >= MAX_PRODUCTOS) {
                        System.out.println("\nEl inventario está lleno. No se pueden agregar más productos.");
                        Bitacora.registrar("Agregar producto", false, "Didiere");
                        break;
                    }

                    System.out.print("Ingrese el código único del producto: ");
                    String codigo = scanner.nextLine();

                    // Validar que el código sea único
                    boolean codigoExiste = false;
                    for (int i = 0; i < totalProductos; i++) {
                        if (inventario[i].getCodigo().equalsIgnoreCase(codigo)) {
                            codigoExiste = true;
                            break;
                        }
                    }

                    if (codigoExiste) {
                        System.out.println("Ya existe un producto con ese código.");
                        Bitacora.registrar("Agregar producto", false, "Didiere");
                        break;
                    }

                    System.out.print("Ingrese el nombre del producto: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Ingrese la categoría (camisas, pantalones, accesorios, etc.): ");
                    String categoria = scanner.nextLine();

                    System.out.print("Ingrese el precio: ");
                    double precio;
                    try {
                        precio = Double.parseDouble(scanner.nextLine());
                        if (precio <= 0) {
                            System.out.println("El precio debe ser un valor positivo.");
                            Bitacora.registrar("Agregar producto", false, "Didiere");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Precio inválido.");
                        Bitacora.registrar("Agregar producto", false, "Didiere");
                        break;
                    }

                    System.out.print("Ingrese la cantidad en stock: ");
                    int stock;
                    try {
                        stock = Integer.parseInt(scanner.nextLine());
                        if (stock < 0) {
                            System.out.println("La cantidad debe ser positiva.");
                            Bitacora.registrar("Agregar producto", false, "Didiere");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Cantidad inválida.");
                        Bitacora.registrar("Agregar producto", false, "Didiere");
                        break;
                    }

                    // Crear producto y agregarlo al inventario
                    inventario[totalProductos] = new Producto(codigo, nombre, categoria, precio, stock);
                    totalProductos++;
                    System.out.println("Producto agregado con éxito.");
                    Bitacora.registrar("Agregar producto", true, "Didiere");
                    break;

                case 2:
                    System.out.println("\n--- Buscar Producto ---");

                    // Solicita al usuario el criterio de búsqueda: nombre, categoría o código
                    System.out.print("Ingrese el criterio de búsqueda (nombre, categoría o código): ");
                    String criterio = scanner.nextLine().toLowerCase();  // Convertimos a minúsculas para hacer la búsqueda más flexible

                    boolean encontrado = false; // Para saber si al menos un producto fue encontrado

                    // Recorremos el inventario actual
                    for (int i = 0; i < totalProductos; i++) {
                        Producto p = inventario[i];  // Accedemos al producto en la posición i

                        // Comprobamos coincidencia parcial en nombre/categoría y exacta en código
                        boolean coincide =
                                p.getNombre().toLowerCase().contains(criterio) ||
                                        p.getCategoria().toLowerCase().contains(criterio) ||
                                        p.getCodigo().toLowerCase().equals(criterio);  // búsqueda exacta solo para código

                        if (coincide) {
                            p.mostrarProducto();
                            encontrado = true;
                        }
                    }

                    if (!encontrado) {
                        System.out.println("No se encontraron productos que coincidan con el criterio proporcionado.");
                    }

                    // Registrar en bitácora si fue exitosa o no la búsqueda
                    Bitacora.registrar("Búsqueda de producto", encontrado, "Didiere");
                    break;

                case 3:
                    System.out.println("\n--- Eliminar Producto ---");
                    boolean eliminado = false;
                    // 1. Solicitar el código del producto
                    System.out.print("Ingrese el código del producto a eliminar: ");
                    String codigoEliminar = scanner.nextLine();

                    // 2. Buscar el producto en el inventario
                    int indiceEliminar = -1;
                    for (int i = 0; i < totalProductos; i++) {
                        if (inventario[i].getCodigo().equalsIgnoreCase(codigoEliminar)) {
                            indiceEliminar = i;
                            break;
                        }
                    }

                    // 3. Si no se encuentra el producto
                    if (indiceEliminar == -1) {
                        System.out.println("Producto no encontrado.");
                        break;
                    }

                    // 4. Confirmar la eliminación
                    System.out.print("¿Está seguro que desea eliminar este producto? (s/n): ");
                    String confirmacion = scanner.nextLine();

                    if (confirmacion.equalsIgnoreCase("s")) {
                        // 5. Desplazar elementos para eliminar el producto
                        for (int i = indiceEliminar; i < totalProductos - 1; i++) {
                            inventario[i] = inventario[i + 1];
                        }
                        inventario[totalProductos - 1] = null; // Limpia la última posición
                        totalProductos--; // Reduce el contador total
                        eliminado = true;
                        System.out.println("Producto eliminado con éxito.");
                    } else {
                        System.out.println("Eliminación cancelada.");
                    }
                    Bitacora.registrar("Eliminación de producto", eliminado, "Didiere");

                    break;

                case 4:
                    System.out.println("\n--- Registrar Venta ---");
                    boolean ventaExitosa = false;
                    if (totalProductos == 0) {
                        System.out.println("No hay productos en el inventario para vender.");
                        break;
                    }

                    System.out.print("Ingrese el código del producto: ");
                    String codigoVenta = scanner.nextLine();

                    Producto productoEncontrado = null;

                    // Buscar el producto en el inventario
                    for (int i = 0; i < totalProductos; i++) {
                        if (inventario[i].getCodigo().equalsIgnoreCase(codigoVenta)) {
                            productoEncontrado = inventario[i];
                            break;
                        }
                    }

                    // Validar existencia
                    if (productoEncontrado == null) {
                        System.out.println("El producto con ese código no existe.");
                        break;
                    }

                    // Mostrar información del producto encontrado
                    productoEncontrado.mostrarProducto();

                    System.out.print("Ingrese la cantidad que desea vender: ");
                    int cantidadVenta;

                    try {
                        cantidadVenta = Integer.parseInt(scanner.nextLine());

                        if (cantidadVenta <= 0) {
                            System.out.println("La cantidad debe ser positiva.");
                            break;
                        }

                        // Validar stock suficiente
                        if (productoEncontrado.getStock() < cantidadVenta) {
                            System.out.println("No hay suficiente stock disponible.");
                            break;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Cantidad inválida.");
                        break;
                    }

                    // Calcular total
                    double total = cantidadVenta * productoEncontrado.getPrecio();

                    // Restar del stock
                    productoEncontrado.setStock(productoEncontrado.getStock() - cantidadVenta);

                    // Crear objeto Venta y almacenarlo
                    Venta nuevaVenta = new Venta(codigoVenta, cantidadVenta, total);
                    if (totalVentas < ventas.length) {
                        ventas[totalVentas++] = nuevaVenta;
                    }

                    // Mostrar la venta en consola
                    nuevaVenta.mostrarVenta();

                    // Guardar en archivo de texto
                    try {
                        java.io.FileWriter writer = new java.io.FileWriter("registro_ventas.txt", true); // modo append
                        writer.write(nuevaVenta.formatoParaArchivo() + "\n");
                        writer.close();
                        System.out.println("Venta registrada correctamente en archivo.");
                        ventaExitosa = true;
                    } catch (Exception e) {
                        System.out.println("Error al guardar la venta en el archivo.");
                    }
                    Bitacora.registrar("Venta", ventaExitosa, "Didiere");

                    break;
                case 5:
                    System.out.println("\n--- BITÁCORA DE ACCIONES ---\n");
                    try (BufferedReader reader = new BufferedReader(new FileReader("bitacora.txt"))) {
                        String linea;
                        boolean hayContenido = false;

                        while ((linea = reader.readLine()) != null) {
                            System.out.println(linea);
                            hayContenido = true;
                        }

                        if (!hayContenido) {
                            System.out.println("La bitácora está vacía.");
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("La bitácora no existe todavía.");
                    } catch (IOException e) {
                        System.out.println("Error al leer la bitácora.");
                    }
                    break;
                case 6:
                    System.out.println("\n--- GENERACIÓN DE REPORTES ---");
                    System.out.println("1. Reporte de Stock");
                    System.out.println("2. Reporte de Ventas");
                    System.out.print("Seleccione una opción: ");
                    String opcionReporte = scanner.nextLine();

                    // Obtener fecha y hora actual
                    LocalDateTime ahora = LocalDateTime.now();
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");

                    switch (opcionReporte) {
                        case "1":
                            try {
                                // Generar nombre dinámico para el archivo de stock
                                String nombreArchivo = formato.format(ahora) + "_Stock.pdf";

                                ReportePDF.generarReporteStock(inventario, totalProductos, nombreArchivo); // genera el pdf
                                System.out.println("Reporte de stock generado exitosamente.");
                                Bitacora.registrar("Generación de reporte de stock", true, "Didiere");
                            } catch (Exception e) {
                                System.out.println("Error al generar el reporte de stock.");
                                Bitacora.registrar("Generación de reporte de stock", false, "Didiere");
                            }
                            break;

                        case "2":
                            try {
                                // Generar nombre dinámico para el archivo de ventas
                                String nombreArchivo = formato.format(ahora) + "_Venta.pdf";

                                ReportePDF.generarReporteVentas(ventas, totalVentas, nombreArchivo); // genera el pdf
                                System.out.println("Reporte de ventas generado exitosamente.");
                                Bitacora.registrar("Generación de reporte de ventas", true, "Didiere");
                            } catch (Exception e) {
                                System.out.println("Error al generar el reporte de ventas.");
                                Bitacora.registrar("Generación de reporte de ventas", false, "Didiere");
                            }
                            break;

                        default:
                            System.out.println("Opción inválida.");
                            break;
                    }
                    break;

                case 7: // muestra los datos del estudiante
                    System.out.println("\n--- DATOS DEL ESTUDIANTE ---");
                    System.out.println("Nombre: Carlos Didiere Cabrera Rodriguez");
                    System.out.println("ID Estudiante: 202401939");
                    System.out.println("Proyecto: Sistema de Inventario con Reportes PDF");
                    Bitacora.registrar("Visualización de datos del estudiante", true, "Didiere");
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un número válido.");
                scanner.nextLine(); // Limpiar entrada inválida
                opcion = -1;
            }
        } while (opcion != 0);

        scanner.close();
    }
}
