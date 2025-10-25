package modelo.estructuras;

import modelo.pedidos.Pedido;

public class ListaPedidos implements java.io.Serializable {

    private Pedido[] pedidos;
    private int contador;

    public ListaPedidos(int capacidadMaxima) {
        pedidos = new Pedido[capacidadMaxima];
        contador = 0;
    }

    /** Agrega un pedido si hay espacio. */
    public boolean agregarPedido(Pedido nuevo) {
        if (nuevo == null) return false;
        if (contador >= pedidos.length) return false;
        pedidos[contador++] = nuevo;
        return true;
    }

    /** Muestra los pedidos por consola. */
    public void mostrarPedidos() {
        if (contador == 0) {
            System.out.println("No hay pedidos registrados.");
            return;
        }
        System.out.println("\n=== LISTA DE PEDIDOS ===");
        for (int i = 0; i < contador; i++) {
            System.out.println(pedidos[i]);
        }
    }

    /** Cantidad de pedidos actualmente almacenados. */
    public int getCantidad() {
        return contador;
    }

    /** Devuelve el arreglo interno (puede contener nulls al final). */
    public Pedido[] getPedidos() {
        return pedidos;
    }

    /** Devuelve el pedido en posición index. */
    public Pedido getPedidos(int index) {
        if (index >= 0 && index < contador) {
            return pedidos[index];
        }
        return null;
    }

    /** Alias si prefiere nombre en singular. */
    public Pedido getPedido(int index) {
        return getPedidos(index);
    }

    /** Busca por código de pedido (PED1, PED2, ...). */
    public Pedido buscarPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (int i = 0; i < contador; i++) {
            Pedido p = pedidos[i];
            if (p != null && codigo.equalsIgnoreCase(p.getCodigo())) {
                return p;
            }
        }
        return null;
    }

    /** Suma de todos los totales de los pedidos (para reportes). */
    public double calcularTotalGeneral() {
        double total = 0;
        for (int i = 0; i < contador; i++) {
            Pedido p = pedidos[i];
            if (p != null) total += p.getTotal();
        }
        return total;
    }
}

