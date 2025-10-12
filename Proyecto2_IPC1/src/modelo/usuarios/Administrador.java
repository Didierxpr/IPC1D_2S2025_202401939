package modelo.usuarios;

public class Administrador extends Usuario {

    // Constructor
    public Administrador(String codigo, String nombre, String genero, String contrasena) {
        super(codigo, nombre, genero, contrasena, "ADMIN");
    }

    // Implementación del menú específico
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ ADMINISTRADOR =====");
        System.out.println("1. Gestionar vendedores");
        System.out.println("2. Gestionar productos");
        System.out.println("3. Ver bitácora");
        System.out.println("4. Generar reportes");
        System.out.println("5. Cerrar sesión");
    }
}

