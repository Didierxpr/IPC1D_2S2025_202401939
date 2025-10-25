package modelo.usuarios;

import java.io.Serializable;

public class Vendedor extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L; // Recomendado para compatibilidad

    // Atributo adicional
    private int ventasConfirmadas;

    // Constructor
    public Vendedor(String codigo, String nombre, String genero, String contrasena) {
        super(codigo, nombre, genero, contrasena, "VENDEDOR");
        this.ventasConfirmadas = 0;
    }

    // =====================================================
    // 🔹 Métodos propios
    // =====================================================
    public int getVentasConfirmadas() {
        return ventasConfirmadas;
    }

    public void aumentarVentasConfirmadas() {
        this.ventasConfirmadas++;
    }

    public void resetearVentas() {
        this.ventasConfirmadas = 0;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ VENDEDOR =====");
        System.out.println("1. Gestionar stock");
        System.out.println("2. Gestionar clientes");
        System.out.println("3. Gestionar pedidos");
        System.out.println("4. Ver reportes");
        System.out.println("5. Cerrar sesión");
    }

    @Override
    public String toString() {
        return super.toString() + " | Ventas confirmadas: " + ventasConfirmadas;
    }
}



