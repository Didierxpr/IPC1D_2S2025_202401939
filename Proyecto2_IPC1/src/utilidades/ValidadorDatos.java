package utilidades;

import interfaces.IValidacion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase utilitaria para validación de datos del sistema
 * Implementa IValidacion
 */
public class ValidadorDatos implements IValidacion {

    private String ultimoError;

    public ValidadorDatos() {
        this.ultimoError = "";
    }

    @Override
    public boolean esCodigoUnico(String codigo) {
        // Este método debe ser implementado en cada controlador específico
        // ya que necesita acceso a los datos del sistema
        return true;
    }

    @Override
    public boolean validarCampoRequerido(String campo, String nombreCampo) {
        if (campo == null || campo.trim().isEmpty()) {
            ultimoError = "El campo '" + nombreCampo + "' es requerido";
            return false;
        }
        return true;
    }

    @Override
    public boolean validarFormatoCodigo(String codigo, String patron) {
        if (codigo == null) {
            ultimoError = "El código no puede ser nulo";
            return false;
        }

        if (!codigo.matches(patron)) {
            ultimoError = "El código no cumple con el formato requerido";
            return false;
        }

        return true;
    }

    @Override
    public boolean validarRango(int valor, int minimo, int maximo) {
        if (valor < minimo || valor > maximo) {
            ultimoError = "El valor debe estar entre " + minimo + " y " + maximo;
            return false;
        }
        return true;
    }

    @Override
    public boolean validarFormatoFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            ultimoError = "La fecha no puede estar vacía";
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            Date fechaParseada = sdf.parse(fecha);

            // Verificar que la fecha no sea del futuro
            Date ahora = new Date();
            if (fechaParseada.after(ahora)) {
                ultimoError = "La fecha no puede ser futura";
                return false;
            }

            return true;
        } catch (ParseException e) {
            ultimoError = "Formato de fecha inválido. Use DD/MM/YYYY";
            return false;
        }
    }

    @Override
    public boolean validarContrasena(String contrasena, int longitudMinima) {
        if (contrasena == null || contrasena.isEmpty()) {
            ultimoError = "La contraseña no puede estar vacía";
            return false;
        }

        if (contrasena.length() < longitudMinima) {
            ultimoError = "La contraseña debe tener al menos " + longitudMinima + " caracteres";
            return false;
        }

        return true;
    }

    @Override
    public String obtenerMensajeError() {
        return ultimoError;
    }

    // ==================== MÉTODOS ADICIONALES DE VALIDACIÓN ====================

    /**
     * Valida que un número sea positivo
     * @param numero Número a validar
     * @param nombreCampo Nombre del campo
     * @return true si es válido
     */
    public boolean validarNumeroPositivo(int numero, String nombreCampo) {
        if (numero <= 0) {
            ultimoError = "El campo '" + nombreCampo + "' debe ser mayor que cero";
            return false;
        }
        return true;
    }

    /**
     * Valida que un precio sea válido
     * @param precio Precio a validar
     * @return true si es válido
     */
    public boolean validarPrecio(double precio) {
        if (precio <= 0) {
            ultimoError = "El precio debe ser mayor que cero";
            return false;
        }

        if (precio > 999999.99) {
            ultimoError = "El precio es demasiado alto";
            return false;
        }

        return true;
    }

    /**
     * Valida formato de correo electrónico
     * @param email Email a validar
     * @return true si es válido
     */
    public boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            ultimoError = "El email no puede estar vacío";
            return false;
        }

        String patronEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!email.matches(patronEmail)) {
            ultimoError = "Formato de email inválido";
            return false;
        }

        return true;
    }

    /**
     * Valida que una cadena no contenga caracteres especiales peligrosos
     * @param texto Texto a validar
     * @param nombreCampo Nombre del campo
     * @return true si es válido
     */
    public boolean validarTextoSeguro(String texto, String nombreCampo) {
        if (texto == null) {
            ultimoError = "El campo '" + nombreCampo + "' no puede ser nulo";
            return false;
        }

        // Caracteres peligrosos para CSV y archivos
        String caracteresProhibidos = "<>|\";";

        for (char c : caracteresProhibidos.toCharArray()) {
            if (texto.indexOf(c) != -1) {
                ultimoError = "El campo '" + nombreCampo + "' contiene caracteres no permitidos";
                return false;
            }
        }

        return true;
    }

    /**
     * Valida que una fecha sea posterior a otra
     * @param fecha1 Primera fecha (DD/MM/YYYY)
     * @param fecha2 Segunda fecha (DD/MM/YYYY)
     * @return true si fecha1 es posterior a fecha2
     */
    public boolean esFechaPosterior(String fecha1, String fecha2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date d1 = sdf.parse(fecha1);
            Date d2 = sdf.parse(fecha2);

            return d1.after(d2);
        } catch (ParseException e) {
            ultimoError = "Error al comparar fechas";
            return false;
        }
    }

    /**
     * Valida un género
     * @param genero Género a validar
     * @return true si es válido
     */
    public boolean validarGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            ultimoError = "El género no puede estar vacío";
            return false;
        }

        String generoUpper = genero.toUpperCase();

        if (!generoUpper.equals("MASCULINO") &&
                !generoUpper.equals("FEMENINO") &&
                !generoUpper.equals("OTRO")) {
            ultimoError = "Género inválido. Debe ser: Masculino, Femenino u Otro";
            return false;
        }

        return true;
    }

    /**
     * Valida una categoría de producto
     * @param categoria Categoría a validar
     * @return true si es válida
     */
    public boolean validarCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            ultimoError = "La categoría no puede estar vacía";
            return false;
        }

        String categoriaUpper = categoria.toUpperCase();

        if (!categoriaUpper.equals("TECNOLOGIA") &&
                !categoriaUpper.equals("ALIMENTO") &&
                !categoriaUpper.equals("GENERAL")) {
            ultimoError = "Categoría inválida. Debe ser: Tecnología, Alimento o General";
            return false;
        }

        return true;
    }

    /**
     * Valida longitud de un texto
     * @param texto Texto a validar
     * @param longitudMinima Longitud mínima
     * @param longitudMaxima Longitud máxima
     * @param nombreCampo Nombre del campo
     * @return true si es válido
     */
    public boolean validarLongitud(String texto, int longitudMinima, int longitudMaxima, String nombreCampo) {
        if (texto == null) {
            ultimoError = "El campo '" + nombreCampo + "' no puede ser nulo";
            return false;
        }

        int longitud = texto.length();

        if (longitud < longitudMinima || longitud > longitudMaxima) {
            ultimoError = "El campo '" + nombreCampo + "' debe tener entre " +
                    longitudMinima + " y " + longitudMaxima + " caracteres";
            return false;
        }

        return true;
    }

    /**
     * Valida que un stock sea suficiente
     * @param stockDisponible Stock disponible
     * @param cantidadSolicitada Cantidad solicitada
     * @return true si hay suficiente stock
     */
    public boolean validarStockSuficiente(int stockDisponible, int cantidadSolicitada) {
        if (cantidadSolicitada <= 0) {
            ultimoError = "La cantidad solicitada debe ser mayor que cero";
            return false;
        }

        if (stockDisponible < cantidadSolicitada) {
            ultimoError = "Stock insuficiente. Disponible: " + stockDisponible +
                    ", Solicitado: " + cantidadSolicitada;
            return false;
        }

        return true;
    }

    /**
     * Limpia el último error
     */
    public void limpiarError() {
        this.ultimoError = "";
    }
}
