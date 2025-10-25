package modelo.estructuras;

import modelo.productos.*;
import java.io.Serializable;

public class ListaProductos implements Serializable {

    private Producto[] productos;
    private int cantidad;
    private final int MAX = 200; // capacidad máxima

    // Constructor
    public ListaProductos() {
        productos = new Producto[MAX];
        cantidad = 0;
    }

    // =====================================================
    // 🔸 Agregar producto
    // =====================================================
    public boolean agregarProducto(Producto nuevo) {
        if (cantidad >= productos.length) return false;
        productos[cantidad++] = nuevo;
        return true;
    }

    // =====================================================
    // 🔸 Buscar por código
    // =====================================================
    public Producto buscarPorCodigo(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (productos[i] != null && productos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return productos[i];
            }
        }
        return null;
    }

    // =====================================================
    // 🔸 Eliminar producto por código
    // =====================================================
    public boolean eliminarProducto(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (productos[i] != null && productos[i].getCodigo().equalsIgnoreCase(codigo)) {
                // Reacomodar arreglo
                for (int j = i; j < cantidad - 1; j++) {
                    productos[j] = productos[j + 1];
                }
                productos[--cantidad] = null;
                System.out.println("🗑️ Producto eliminado: " + codigo);
                return true;
            }
        }
        return false;
    }

    // =====================================================
    // 🔸 Obtener producto por índice
    // =====================================================
    public Producto getProducto(int index) {
        if (index < 0 || index >= cantidad) return null;
        return productos[index];
    }

    // =====================================================
    // 🔸 Obtener lista de productos actual
    // =====================================================
    public Producto[] getProductos() {
        Producto[] copia = new Producto[cantidad];
        for (int i = 0; i < cantidad; i++) {
            copia[i] = productos[i];
        }
        return copia;
    }

    // =====================================================
    // 🔸 Actualizar información de un producto existente
    // =====================================================
    public boolean actualizarProducto(String codigo, String nuevoNombre, int nuevoStock, double nuevoPrecio) {
        Producto p = buscarPorCodigo(codigo);
        if (p != null) {
            p.setNombre(nuevoNombre);
            p.setStock(nuevoStock);
            p.setPrecio(nuevoPrecio);
            return true;
        }
        return false;
    }

    // =====================================================
    // 🔸 Cantidad actual de productos
    // =====================================================
    public int getCantidad() {
        return cantidad;
    }

    // =====================================================
    // 🔸 Mostrar todos los productos (para consola)
    // =====================================================
    public void mostrarProductos() {
        System.out.println("\n=== LISTA DE PRODUCTOS ===");
        if (cantidad == 0) {
            System.out.println("⚠️ No hay productos registrados.");
            return;
        }
        for (int i = 0; i < cantidad; i++) {
            Producto p = productos[i];
            System.out.println((i + 1) + ". " + p.getCodigo() + " | " + p.getNombre()
                    + " | " + p.getCategoria() + " | Q" + p.getPrecio() + " | Stock: " + p.getStock());
        }
    }

    // =====================================================
    // 🔸 Exportar productos (para usar con reportes o GUI)
    // =====================================================
    public String[][] toMatriz() {
        String[][] datos = new String[cantidad][5];
        for (int i = 0; i < cantidad; i++) {
            Producto p = productos[i];
            datos[i][0] = p.getCodigo();
            datos[i][1] = p.getNombre();
            datos[i][2] = p.getCategoria();
            datos[i][3] = String.valueOf(p.getStock());
            datos[i][4] = String.format("Q %.2f", p.getPrecio());
        }
        return datos;
    }
}

