package modelo.usuarios;

import java.io.Serializable;

public class Administrador extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L; // Evita problemas de compatibilidad

    // =====================================================
    // 🔹 Constructor
    // =====================================================
    public Administrador(String codigo, String nombre, String genero, String contrasena) {
        super(codigo, nombre, genero, contrasena, "ADMIN");
    }

    // =====================================================
    // 🔹 Implementación del menú específico
    // =====================================================
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ ADMINISTRADOR =====");
        System.out.println("1. Gestionar vendedores");
        System.out.println("2. Gestionar productos");
        System.out.println("3. Ver bitácora");
        System.out.println("4. Generar reportes");
        System.out.println("5. Cerrar sesión");
    }

    @Override
    public String toString() {
        return super.toString() + " | Rol: Administrador";
    }
}

