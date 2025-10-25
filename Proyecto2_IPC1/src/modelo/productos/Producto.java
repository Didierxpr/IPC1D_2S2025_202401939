package modelo.productos;

import java.io.Serializable;

public abstract class Producto implements Serializable {

    // Atributos comunes
    protected String codigo;
    protected String nombre;
    protected String categoria; // "TECNOLOGIA", "ALIMENTO" o "GENERAL"
    protected int stock;
    protected double precio;

    // Constructor base
    public Producto(String codigo, String nombre, String categoria, int stock, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public int getStock() { return stock; }
    public double getPrecio() { return precio; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setStock(int stock) { this.stock = stock; }
    public void setPrecio(double precio) { this.precio = precio; }

    // Método abstracto: cada categoría mostrará un detalle distinto
    public abstract String verDetalle();

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + categoria + ") | Stock: " + stock + " | Precio: Q" + precio;
    }
}


