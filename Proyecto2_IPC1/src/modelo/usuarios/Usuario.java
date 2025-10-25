package modelo.usuarios;

import java.io.Serializable;

public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 1L; // Evita errores de compatibilidad

    // Atributos comunes a todos los usuarios
    protected String codigo;
    protected String nombre;
    protected String genero;
    protected String password;
    protected String tipo; // ADMIN, VENDEDOR o CLIENTE

    // Constructor
    public Usuario(String codigo, String nombre, String genero, String password, String tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.password = password;
        this.tipo = tipo;
    }

    // ======================================================
    // 🔹 Getters y Setters
    // ======================================================
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getGenero() { return genero; }
    public String getPassword() { return password; }
    public String getTipo() { return tipo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPassword(String password) { this.password = password; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    // ======================================================
    // 🔹 Métodos abstractos y comunes
    // ======================================================

    // Cada tipo de usuario tendrá su propio menú
    public abstract void mostrarMenu();

    @Override
    public String toString() {
        return String.format("Código: %s | Nombre: %s | Tipo: %s | Género: %s",
                codigo, nombre, tipo, (genero != null ? genero : "-"));
    }
}

