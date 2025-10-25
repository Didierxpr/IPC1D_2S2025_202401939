package modelo.usuarios;

import java.io.Serializable;

public class Cliente extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L; // Recomendado para serialización estable

    // Atributos adicionales
    private int comprasRealizadas;

    // Constructor
    public Cliente(String codigo, String nombre, String genero, String contrasena) {
        super(codigo, nombre, genero, contrasena, "CLIENTE");
        this.comprasRealizadas = 0;
    }

    // =====================================================
    // 🔹 Métodos propios
    // =====================================================
    public int getComprasRealizadas() {
        return comprasRealizadas;
    }

    public void aumentarCompras() {
        this.comprasRealizadas++;
    }

    public void resetearCompras() {
        this.comprasRealizadas = 0;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ CLIENTE =====");
        System.out.println("1. Ver catálogo de productos");
        System.out.println("2. Agregar producto al carrito");
        System.out.println("3. Realizar pedido");
        System.out.println("4. Ver historial de compras");
        System.out.println("5. Cerrar sesión");
    }

    @Override
    public String toString() {
        return super.toString() + " | Compras realizadas: " + comprasRealizadas;
    }
}

