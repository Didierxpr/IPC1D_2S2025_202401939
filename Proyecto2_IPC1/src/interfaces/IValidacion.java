package interfaces;

/**
 * Interface que define operaciones de validación de datos
 */
public interface IValidacion {

    /**
     * Valida que un código sea único en el sistema
     * @param codigo Código a validar
     * @return true si es único, false si ya existe
     */
    boolean esCodigoUnico(String codigo);

    /**
     * Valida que un campo no esté vacío
     * @param campo Valor del campo
     * @param nombreCampo Nombre del campo para mensajes
     * @return true si es válido
     */
    boolean validarCampoRequerido(String campo, String nombreCampo);

    /**
     * Valida el formato de un código
     * @param codigo Código a validar
     * @param patron Patrón regex esperado
     * @return true si cumple el formato
     */
    boolean validarFormatoCodigo(String codigo, String patron);

    /**
     * Valida que un valor numérico esté en un rango
     * @param valor Valor a validar
     * @param minimo Valor mínimo permitido
     * @param maximo Valor máximo permitido
     * @return true si está en el rango
     */
    boolean validarRango(int valor, int minimo, int maximo);

    /**
     * Valida el formato de una fecha (DD/MM/YYYY)
     * @param fecha Fecha a validar
     * @return true si el formato es correcto
     */
    boolean validarFormatoFecha(String fecha);

    /**
     * Valida que una contraseña cumpla con requisitos mínimos
     * @param contrasena Contraseña a validar
     * @param longitudMinima Longitud mínima requerida
     * @return true si es válida
     */
    boolean validarContrasena(String contrasena, int longitudMinima);

    /**
     * Obtiene el último mensaje de error de validación
     * @return Mensaje de error
     */
    String obtenerMensajeError();
}
