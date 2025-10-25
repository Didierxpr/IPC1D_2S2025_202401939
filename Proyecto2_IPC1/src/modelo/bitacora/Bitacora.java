package modelo.bitacora;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bitacora implements Serializable {

    private String fecha;
    private String hora;
    private String tipo;
    private String descripcion;

    public Bitacora(String tipo, String descripcion) {
        LocalDateTime now = LocalDateTime.now();
        this.fecha = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.hora = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return String.format("[%s %s] [%s] %s", fecha, hora, tipo, descripcion);
    }

    public Object getUsuario() {
        return null;
    }

}



