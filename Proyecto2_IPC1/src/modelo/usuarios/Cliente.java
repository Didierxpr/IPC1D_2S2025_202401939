package modelo.usuarios;

public class Cliente extends Usuario {

    // Atributos adicionales
    private int comprasRealizadas;

    // Constructor
    public Cliente(String codigo, String nombre, String genero, String contrasena) {
        super(codigo, nombre, genero, contrasena, "CLIENTE");
        this.comprasRealizadas = 0;
    }

    public int getComprasRealizadas() { return comprasRealizadas; }
    public void aumentarCompras() { this.comprasRealizadas++; }

    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ CLIENTE =====");
        System.out.println("1. Ver catálogo de productos");
        System.out.println("2. Agregar producto al carrito");
        System.out.println("3. Realizar pedido");
        System.out.println("4. Ver historial de compras");
        System.out.println("5. Cerrar sesión");
    }
}

