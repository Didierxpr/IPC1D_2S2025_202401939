package controlador;

import java.util.Scanner;

import modelo.Sistema;
import modelo.estructuras.ListaProductos;
import modelo.productos.*;

public class ControladorProductos {

    private ListaProductos listaProductos;
    private Scanner scanner;

    // Constructor
    public ControladorProductos(ListaProductos listaProductos) {
        this.listaProductos = listaProductos;
        this.scanner = new Scanner(System.in);
    }


    // MENÚ PRINCIPAL

    public void menuProductos() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n===== GESTIÓN DE PRODUCTOS =====");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Mostrar todos los productos");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Volver al menú anterior");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    agregarProducto();
                    break;
                case "2":
                    buscarProducto();
                    break;
                case "3":
                    mostrarProductos();
                    break;
                case "4":
                    eliminarProducto();
                    break;
                case "5": continuar = false;
                break;
                case "0":
                    System.out.println("🔙 Regresando al menú principal...");
                    continuar = false;
                    break;
                default:
                    System.out.println("⚠️ Opción inválida. Intente nuevamente.");
            }
        }
    }


    // MÉTODO 1: AGREGAR PRODUCTO

    private void agregarProducto() {
        System.out.println("\n=== NUEVO PRODUCTO ===");
        System.out.print("Código: ");
        String codigo = scanner.nextLine().trim();

        // Evitar duplicados
        if (listaProductos.buscarPorCodigo(codigo) != null) {
            System.out.println("⚠️ Ya existe un producto con ese código.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.println("1. Tecnología");
        System.out.println("2. Alimento");
        System.out.println("3. General");
        System.out.print("Seleccione tipo: ");
        String tipo = scanner.nextLine();
        String descripcion = "";

        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());
        System.out.print("Stock inicial: ");
        int stock = Integer.parseInt(scanner.nextLine());

        Producto nuevo = null;

        switch (tipo) {
            case "1":
                System.out.print("Meses de garantía: ");
                int garantia = Integer.parseInt(scanner.nextLine());
                nuevo = new ProductoTecnologia(codigo, nombre, stock, precio, garantia);
                break;

            case "2":
                System.out.print("Fecha de caducidad (AAAA-MM-DD): ");
                String fecha = scanner.nextLine().trim();
                nuevo = new ProductoAlimento(codigo, nombre, stock, precio, fecha);
                break;

            case "3":
                nuevo = new ProductoGeneral(codigo, nombre, stock, precio, descripcion);
                break;

            default:
                System.out.println("⚠️ Categoría inválida. No se creó el producto.");
                return;
        }

        if (nuevo != null) {
            Sistema.getListaProductos().agregarProducto(nuevo);
            System.out.println("✅ Producto agregado correctamente: " + nuevo.getNombre());
        }

    }


    // MÉTODO 2: BUSCAR PRODUCTO

    private void buscarProducto() {
        System.out.print("\nIngrese el código del producto a buscar: ");
        String codigo = scanner.nextLine().trim();
        Producto p = listaProductos.buscarPorCodigo(codigo);
        if (p != null) {
            System.out.println("✅ Producto encontrado:");
            System.out.println(p.toString());
            System.out.println(p.verDetalle());
        } else {
            System.out.println("❌ No se encontró un producto con ese código.");
        }
    }


    // MÉTODO 3: MOSTRAR PRODUCTOS

    private void mostrarProductos() {
        System.out.println("\n=== LISTADO DE PRODUCTOS REGISTRADOS ===");

        var lista = Sistema.getListaProductos();

        if (lista.getCantidad() == 0) {
            System.out.println("⚠️ No hay productos registrados.");
            return;
        }

        System.out.printf("%-10s %-20s %-15s %-10s %-10s %-25s\n",
                "CÓDIGO", "NOMBRE", "CATEGORÍA", "STOCK", "PRECIO", "DETALLE");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (int i = 0; i < lista.getCantidad(); i++) {
            var p = lista.getProducto(i);
            System.out.printf("%-10s %-20s %-15s %-10d Q%-9.2f %-25s\n",
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCategoria(),
                    p.getStock(),
                    p.getPrecio(),
                    p.verDetalle());
        }

        System.out.println("--------------------------------------------------------------------------------------------");
    }




    //  MÉTODO 4: ELIMINAR PRODUCTO

    private void eliminarProducto() {
        System.out.print("\nIngrese el código del producto a eliminar: ");
        String codigo = scanner.nextLine().trim();
        if (listaProductos.eliminarProducto(codigo)) {
            System.out.println("✅ Producto eliminado exitosamente.");
        } else {
            System.out.println("❌ No se encontró el producto.");
        }
    }

}
