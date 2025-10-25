package controlador;

import modelo.estructuras.ListaUsuarios;
import modelo.usuarios.Usuario;

public class ControladorAutenticacion {

    private ListaUsuarios listaUsuarios;

    // Constructor: recibe la lista de usuarios del sistema
    public ControladorAutenticacion(ListaUsuarios listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    // Método principal de inicio de sesión
    public Usuario iniciarSesion(String codigo, String password) {
        if (codigo == null || password == null) return null;

        // Busca el usuario en la lista
        Usuario usuario = listaUsuarios.autenticar(codigo, password);

        if (usuario != null) {
            System.out.println("✅ Sesión iniciada correctamente: " + usuario.getNombre() + " (" + usuario.getTipo() + ")");
            return usuario;
        } else {
            System.out.println("❌ Error de autenticación: código o contraseña incorrectos.");
            return null;
        }
    }
}



