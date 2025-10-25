package modelo.estructuras;

import modelo.usuarios.Usuario;
import java.io.Serializable;

public class ListaUsuarios implements Serializable {

    private static final long serialVersionUID = 1L; // Evita errores al serializar

    private Usuario[] usuarios;
    private int cantidad;

    // 🔹 Constructor
    public ListaUsuarios(int capacidadMaxima) {
        usuarios = new Usuario[capacidadMaxima];
        cantidad = 0;
    }

    // =====================================================
    // 🔹 Métodos principales CRUD
    // =====================================================

    // ✅ Agregar usuario
    public boolean agregarUsuario(Usuario nuevo) {
        if (cantidad >= usuarios.length) return false;
        if (buscarUsuario(nuevo.getCodigo()) != null) return false; // Evitar duplicados
        usuarios[cantidad++] = nuevo;
        return true;
    }

    // ✅ Buscar usuario por código
    public Usuario buscarUsuario(String codigo) {
        if (codigo == null) return null;
        for (int i = 0; i < cantidad; i++) {
            Usuario u = usuarios[i];
            if (u != null && u.getCodigo().equalsIgnoreCase(codigo)) {
                return u;
            }
        }
        return null;
    }

    // ✅ Eliminar usuario
    public boolean eliminarUsuario(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            Usuario u = usuarios[i];
            if (u != null && u.getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < cantidad - 1; j++) {
                    usuarios[j] = usuarios[j + 1];
                }
                usuarios[--cantidad] = null;
                return true;
            }
        }
        return false;
    }

    // ✅ Actualizar usuario existente
    public boolean actualizarUsuario(Usuario actualizado) {
        for (int i = 0; i < cantidad; i++) {
            if (usuarios[i] != null &&
                    usuarios[i].getCodigo().equalsIgnoreCase(actualizado.getCodigo())) {
                usuarios[i] = actualizado;
                return true;
            }
        }
        return false;
    }

    // =====================================================
    // 🔹 Métodos de utilidad
    // =====================================================

    // ✅ Autenticación
    public Usuario autenticar(String codigo, String password) {
        if (codigo == null || password == null) return null;
        String cod = codigo.trim();
        String pass = password.trim();

        for (int i = 0; i < cantidad; i++) {
            Usuario u = usuarios[i];
            if (u == null) continue;
            if (u.getCodigo().equalsIgnoreCase(cod) &&
                    u.getPassword().equals(pass)) {
                return u;
            }
        }
        return null;
    }

    // ✅ Obtener usuario por índice
    public Usuario getUsuario(int indice) {
        if (indice < 0 || indice >= cantidad) return null;
        return usuarios[indice];
    }

    // ✅ Obtener arreglo de usuarios
    public Usuario[] getUsuarios() {
        Usuario[] activos = new Usuario[cantidad];
        for (int i = 0; i < cantidad; i++) {
            activos[i] = usuarios[i];
        }
        return activos;
    }


    // ✅ Cantidad actual
    public int getCantidad() {
        return cantidad;
    }

    // ✅ Mostrar todos en consola (para depuración)
    public void mostrarUsuarios() {
        if (cantidad == 0) {
            System.out.println("⚠️ No hay usuarios registrados.");
            return;
        }

        System.out.printf("%-10s %-20s %-10s %-10s\n", "CÓDIGO", "NOMBRE", "TIPO", "GÉNERO");
        for (int i = 0; i < cantidad; i++) {
            Usuario u = usuarios[i];
            if (u == null) continue;

            String genero = (u.getTipo().equalsIgnoreCase("CLIENTE"))
                    ? ((modelo.usuarios.Cliente) u).getGenero()
                    : "-";

            System.out.printf("%-10s %-20s %-10s %-10s\n",
                    u.getCodigo(), u.getNombre(), u.getTipo(), genero);
        }
    }
}


