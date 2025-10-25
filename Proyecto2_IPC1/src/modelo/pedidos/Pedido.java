package modelo.pedidos;

import modelo.productos.Producto;
import modelo.usuarios.Cliente;
import modelo.usuarios.Usuario;
import java.io.Serializable;

public class Pedido implements Serializable {

    private static int contador = 1;

    private String codigo;
    private Cliente cliente;
    private Producto producto;
    private int cantidad;
    private double total;
    private Usuario vendedor;
    private String fecha;

    // =========================================================
    // 🔹 Constructor principal
    // =========================================================
    public Pedido(Cliente cliente, Producto producto, int cantidad, double total, Usuario vendedor) {
        this.codigo = "PED" + contador++;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
        this.vendedor = vendedor;
        this.fecha = java.time.LocalDate.now().toString(); // fecha automática
    }

    public Pedido(String codigoPedido, Producto p, Cliente cliente, int cantidad, double total) {
    }

    // =========================================================
    // 🔹 Getters
    // =========================================================
    public String getCodigo() { return codigo; }
    public Cliente getCliente() { return cliente; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getTotal() { return total; }
    public Usuario getVendedor() { return vendedor; }
    public String getFecha() { return fecha; }

    // =========================================================
    // 🔹 Setters (si deseas modificarlos luego)
    // =========================================================
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setTotal(double total) { this.total = total; }

    // =========================================================
    // 🔹 Representación en texto
    // =========================================================
    @Override
    public String toString() {
        return String.format("%s | Cliente: %s | Producto: %s | Cantidad: %d | Total: Q%.2f | Vendedor: %s | Fecha: %s",
                codigo,
                cliente.getNombre(),
                producto.getNombre(),
                cantidad,
                total,
                vendedor.getNombre(),
                fecha
        );
    }
}




