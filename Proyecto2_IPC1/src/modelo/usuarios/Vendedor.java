package modelo.usuarios;

public class Vendedor extends Usuario {

    // Atributo adicional
    private int ventasConfirmadas;

    // Constructor
    public Vendedor(String codigo, String nombre, String genero, String contrasena) {
        super(codigo, nombre, genero, contrasena, "VENDEDOR");
        this.ventasConfirmadas = 0;
    }

    // Getters / Setters
    public int getVentasConfirmadas() { return ventasConfirmadas; }
    public void aumentarVentasConfirmadas() { this.ventasConfirmadas++; }

    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ VENDEDOR =====");
        System.out.println("1. Gestionar stock");
        System.out.println("2. Gestionar clientes");
        System.out.println("3. Gestionar pedidos");
        System.out.println("4. Ver reportes");
        System.out.println("5. Cerrar sesión");
    }
}

