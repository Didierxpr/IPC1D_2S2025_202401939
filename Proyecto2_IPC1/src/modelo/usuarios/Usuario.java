package modelo.usuarios;

public abstract class Usuario {

    // Atributos comunes a todos los usuarios
    protected String codigo;
    protected String nombre;
    protected String genero;
    protected String contrasena;
    protected String tipo; // ADMIN, VENDEDOR o CLIENTE

    // Constructor
    public Usuario(String codigo, String nombre, String genero, String contrasena, String tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.contrasena = contrasena;
        this.tipo = tipo;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getGenero() { return genero; }
    public String getContrasena() { return contrasena; }
    public String getTipo() { return tipo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    // Método abstracto: cada tipo de usuario tendrá su propio menú
    public abstract void mostrarMenu();

    // Método común opcional
    @Override
    public String toString() {
        return "Código: " + codigo + " | Nombre: " + nombre + " | Tipo: " + tipo;
    }
}

