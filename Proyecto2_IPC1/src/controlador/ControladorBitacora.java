package controlador;

import modelo.Bitacora;
import utilidades.*;

/**
 * Controlador para gestión de la bitácora del sistema
 */
public class ControladorBitacora {

    private Bitacora[] registros;

    public ControladorBitacora() {
        cargarRegistros();
    }

    /**
     * Carga los registros de bitácora desde el sistema de archivos
     */
    private void cargarRegistros() {
        registros = SistemaArchivos.cargarBitacora();
        if (registros == null) {
            registros = new Bitacora[0];
        }
    }

    /**
     * Guarda los registros en el sistema de archivos
     */
    private boolean guardarRegistros() {
        return SistemaArchivos.guardarBitacora(registros);
    }

    /**
     * Registra una nueva entrada en la bitácora
     */
    public boolean registrar(Bitacora entrada) {
        if (entrada == null) {
            return false;
        }

        // Agrega la nueva entrada sin causar ClassCastException
        Object[] temp = Vectores.agregar(registros, entrada);

        // Convierte el arreglo genérico en un arreglo del tipo Bitacora
        Bitacora[] nuevo = new Bitacora[temp.length];
        for (int i = 0; i < temp.length; i++) {
            nuevo[i] = (Bitacora) temp[i];
        }

        registros = nuevo;

        return guardarRegistros();
    }


    /**
     * Obtiene todos los registros de la bitácora
     */
    public Bitacora[] obtenerTodos() {
        return registros;
    }

    /**
     * Filtra registros por tipo de usuario
     */
    public Bitacora[] filtrarPorTipoUsuario(String tipoUsuario) {
        Bitacora[] resultado = new Bitacora[0];

        for (int i = 0; i < registros.length; i++) {
            if (registros[i].esTipoUsuario(tipoUsuario)) {
                resultado = (Bitacora[]) Vectores.agregar(resultado, registros[i]);
            }
        }

        return resultado;
    }

    /**
     * Filtra registros por usuario específico
     */
    public Bitacora[] filtrarPorUsuario(String codigoUsuario) {
        Bitacora[] resultado = new Bitacora[0];

        for (int i = 0; i < registros.length; i++) {
            if (registros[i].esDeUsuario(codigoUsuario)) {
                resultado = (Bitacora[]) Vectores.agregar(resultado, registros[i]);
            }
        }

        return resultado;
    }

    /**
     * Filtra registros por operación
     */
    public Bitacora[] filtrarPorOperacion(String operacion) {
        Bitacora[] resultado = new Bitacora[0];

        for (int i = 0; i < registros.length; i++) {
            if (registros[i].esOperacion(operacion)) {
                resultado = (Bitacora[]) Vectores.agregar(resultado, registros[i]);
            }
        }

        return resultado;
    }

    /**
     * Filtra registros por fecha
     */
    public Bitacora[] filtrarPorFecha(String fecha) {
        Bitacora[] resultado = new Bitacora[0];

        for (int i = 0; i < registros.length; i++) {
            if (registros[i].esDeFecha(fecha)) {
                resultado = (Bitacora[]) Vectores.agregar(resultado, registros[i]);
            }
        }

        return resultado;
    }

    /**
     * Filtra registros por estado (EXITOSA/FALLIDA)
     */
    public Bitacora[] filtrarPorEstado(String estado) {
        Bitacora[] resultado = new Bitacora[0];

        for (int i = 0; i < registros.length; i++) {
            if (registros[i].getEstado().equalsIgnoreCase(estado)) {
                resultado = (Bitacora[]) Vectores.agregar(resultado, registros[i]);
            }
        }

        return resultado;
    }

    /**
     * Exporta la bitácora completa a CSV
     */
    public boolean exportarCSV(String rutaArchivo) {
        if (registros.length == 0) {
            System.err.println("No hay registros para exportar");
            return false;
        }

        String encabezado = "Fecha,Hora,Tipo Usuario,Codigo Usuario,Nombre Usuario," +
                "Operacion,Estado,Descripcion,Detalles Adicionales";

        String[] lineas = new String[registros.length];

        for (int i = 0; i < registros.length; i++) {
            lineas[i] = registros[i].toCSV();
        }

        return ManejadorArchivos.escribirCSV(rutaArchivo, encabezado, lineas);
    }

    /**
     * Obtiene la cantidad total de registros
     */
    public int contarRegistros() {
        return registros.length;
    }

    /**
     * Limpia todos los registros (usar con precaución)
     */
    public boolean limpiarTodo() {
        registros = new Bitacora[0];
        return guardarRegistros();
    }

    /**
     * Recarga los registros desde el sistema de archivos
     */
    public void recargar() {
        cargarRegistros();
    }
}
