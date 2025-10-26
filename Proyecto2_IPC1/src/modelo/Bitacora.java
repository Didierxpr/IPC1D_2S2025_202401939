package modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa una entrada en la bitácora del sistema
 * Registra todas las operaciones realizadas para auditoría
 */
public class Bitacora implements Serializable {

    // Atributos de la entrada de bitácora
    private String fecha; // DD/MM/YYYY
    private String hora; // HH:mm:ss
    private String tipoUsuario; // "ADMINISTRADOR", "VENDEDOR", "CLIENTE"
    private String codigoUsuario;
    private String nombreUsuario;
    private String operacion; // Tipo de operación realizada
    private String estado; // "EXITOSA", "FALLIDA"
    private String descripcion; // Descripción detallada del evento
    private String detallesAdicionales; // Información adicional opcional

    /**
     * Constructor vacío
     */
    public Bitacora() {
        // Inicializar fecha y hora actual
        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
        Date ahora = new Date();
        this.fecha = sdfFecha.format(ahora);
        this.hora = sdfHora.format(ahora);
    }

    /**
     * Constructor con parámetros básicos
     * @param tipoUsuario Tipo de usuario
     * @param codigoUsuario Código del usuario
     * @param operacion Operación realizada
     * @param estado Estado de la operación
     * @param descripcion Descripción del evento
     */
    public Bitacora(String tipoUsuario, String codigoUsuario, String operacion,
                    String estado, String descripcion) {
        this();
        this.tipoUsuario = tipoUsuario;
        this.codigoUsuario = codigoUsuario;
        this.operacion = operacion;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    /**
     * Constructor completo
     * @param tipoUsuario Tipo de usuario
     * @param codigoUsuario Código del usuario
     * @param nombreUsuario Nombre del usuario
     * @param operacion Operación realizada
     * @param estado Estado de la operación
     * @param descripcion Descripción del evento
     * @param detallesAdicionales Detalles adicionales
     */
    public Bitacora(String tipoUsuario, String codigoUsuario, String nombreUsuario,
                    String operacion, String estado, String descripcion, String detallesAdicionales) {
        this();
        this.tipoUsuario = tipoUsuario;
        this.codigoUsuario = codigoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.operacion = operacion;
        this.estado = estado;
        this.descripcion = descripcion;
        this.detallesAdicionales = detallesAdicionales;
    }

    // ==================== GETTERS Y SETTERS ====================

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetallesAdicionales() {
        return detallesAdicionales;
    }

    public void setDetallesAdicionales(String detallesAdicionales) {
        this.detallesAdicionales = detallesAdicionales;
    }

    // ==================== MÉTODOS DE VALIDACIÓN ====================

    /**
     * Verifica si la operación fue exitosa
     * @return true si fue exitosa
     */
    public boolean fueExitosa() {
        return "EXITOSA".equals(this.estado);
    }

    /**
     * Verifica si la operación falló
     * @return true si falló
     */
    public boolean fallo() {
        return "FALLIDA".equals(this.estado);
    }

    /**
     * Verifica si la entrada corresponde a un tipo de usuario específico
     * @param tipo Tipo de usuario a verificar
     * @return true si coincide
     */
    public boolean esTipoUsuario(String tipo) {
        return this.tipoUsuario.equalsIgnoreCase(tipo);
    }

    /**
     * Verifica si la entrada corresponde a una operación específica
     * @param op Operación a verificar
     * @return true si coincide
     */
    public boolean esOperacion(String op) {
        return this.operacion.equalsIgnoreCase(op);
    }

    /**
     * Verifica si la entrada es de una fecha específica
     * @param fechaBuscar Fecha a buscar
     * @return true si coincide
     */
    public boolean esDeFecha(String fechaBuscar) {
        return this.fecha.equals(fechaBuscar);
    }

    /**
     * Verifica si la entrada es de un usuario específico
     * @param codigo Código del usuario
     * @return true si coincide
     */
    public boolean esDeUsuario(String codigo) {
        return this.codigoUsuario.equals(codigo);
    }

    // ==================== MÉTODOS DE FORMATO ====================

    /**
     * Obtiene la entrada en formato estándar de bitácora
     * Formato: [DD/MM/YYYY HH:mm:ss] | [USUARIO_TIPO] | [CODIGO_USUARIO] | [OPERACION] | [ESTADO] | [DESCRIPCION]
     * @return String formateado
     */
    public String formatoEstandar() {
        return String.format("[%s %s] | [%s] | [%s] | [%s] | [%s] | [%s]",
                fecha, hora, tipoUsuario, codigoUsuario, operacion, estado, descripcion);
    }

    /**
     * Obtiene un resumen corto de la entrada
     * @return String con resumen
     */
    public String obtenerResumen() {
        return fecha + " " + hora + " - " + tipoUsuario + " (" + codigoUsuario + ") - " + operacion + " - " + estado;
    }

    /**
     * Obtiene el detalle completo de la entrada
     * @return String con toda la información
     */
    public String obtenerDetalleCompleto() {
        StringBuilder detalle = new StringBuilder();
        detalle.append("╔════════════════════════════════════════════════════╗\n");
        detalle.append("║              ENTRADA DE BITÁCORA                   ║\n");
        detalle.append("╠════════════════════════════════════════════════════╣\n");
        detalle.append(String.format("║ Fecha: %-41s║\n", fecha));
        detalle.append(String.format("║ Hora: %-42s║\n", hora));
        detalle.append("╠════════════════════════════════════════════════════╣\n");
        detalle.append(String.format("║ Tipo de usuario: %-31s║\n", tipoUsuario));
        detalle.append(String.format("║ Código usuario: %-32s║\n", codigoUsuario));

        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            detalle.append(String.format("║ Nombre: %-40s║\n", nombreUsuario));
        }

        detalle.append("╠════════════════════════════════════════════════════╣\n");
        detalle.append(String.format("║ Operación: %-37s║\n", operacion));
        detalle.append(String.format("║ Estado: %-40s║\n", estado));
        detalle.append("╠════════════════════════════════════════════════════╣\n");
        detalle.append(String.format("║ Descripción:                                       ║\n"));

        // Dividir descripción en líneas si es muy larga
        String[] palabras = descripcion.split(" ");
        StringBuilder lineaActual = new StringBuilder();

        for (String palabra : palabras) {
            if (lineaActual.length() + palabra.length() + 1 <= 46) {
                if (lineaActual.length() > 0) {
                    lineaActual.append(" ");
                }
                lineaActual.append(palabra);
            } else {
                detalle.append(String.format("║ %-47s║\n", lineaActual.toString()));
                lineaActual = new StringBuilder(palabra);
            }
        }

        if (lineaActual.length() > 0) {
            detalle.append(String.format("║ %-47s║\n", lineaActual.toString()));
        }

        if (detallesAdicionales != null && !detallesAdicionales.isEmpty()) {
            detalle.append("╠════════════════════════════════════════════════════╣\n");
            detalle.append(String.format("║ Detalles adicionales:                              ║\n"));
            detalle.append(String.format("║ %-47s║\n", detallesAdicionales));
        }

        detalle.append("╚════════════════════════════════════════════════════╝");

        return detalle.toString();
    }

    /**
     * Convierte la entrada a formato CSV
     * @return String en formato CSV
     */
    public String toCSV() {
        return fecha + "," +
                hora + "," +
                tipoUsuario + "," +
                codigoUsuario + "," +
                (nombreUsuario != null ? nombreUsuario : "") + "," +
                operacion + "," +
                estado + "," +
                escaparCSV(descripcion) + "," +
                (detallesAdicionales != null ? escaparCSV(detallesAdicionales) : "");
    }

    /**
     * Crea una entrada de bitácora desde formato CSV
     * @param linea Línea en formato CSV
     * @return Objeto Bitacora creado
     */
    public static Bitacora fromCSV(String linea) {
        String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Split respetando comillas

        if (partes.length >= 8) {
            Bitacora bitacora = new Bitacora();
            bitacora.setFecha(partes[0].trim());
            bitacora.setHora(partes[1].trim());
            bitacora.setTipoUsuario(partes[2].trim());
            bitacora.setCodigoUsuario(partes[3].trim());
            bitacora.setNombreUsuario(partes[4].trim());
            bitacora.setOperacion(partes[5].trim());
            bitacora.setEstado(partes[6].trim());
            bitacora.setDescripcion(desescaparCSV(partes[7].trim()));

            if (partes.length >= 9) {
                bitacora.setDetallesAdicionales(desescaparCSV(partes[8].trim()));
            }

            return bitacora;
        }

        return null;
    }

    /**
     * Escapa caracteres especiales para CSV
     * @param texto Texto a escapar
     * @return Texto escapado
     */
    private String escaparCSV(String texto) {
        if (texto == null) {
            return "";
        }

        if (texto.contains(",") || texto.contains("\"") || texto.contains("\n")) {
            return "\"" + texto.replace("\"", "\"\"") + "\"";
        }

        return texto;
    }

    /**
     * Desescapa caracteres especiales de CSV
     * @param texto Texto a desescapar
     * @return Texto desescapado
     */
    private static String desescaparCSV(String texto) {
        if (texto == null) {
            return "";
        }

        if (texto.startsWith("\"") && texto.endsWith("\"")) {
            return texto.substring(1, texto.length() - 1).replace("\"\"", "\"");
        }

        return texto;
    }

    // ==================== MÉTODOS ESTÁTICOS PARA CREAR ENTRADAS ====================

    /**
     * Crea una entrada de bitácora para login exitoso
     * @param usuario Usuario que inició sesión
     * @return Objeto Bitacora
     */
    public static Bitacora loginExitoso(Usuario usuario) {
        return new Bitacora(
                usuario.getTipoUsuario(),
                usuario.getCodigo(),
                usuario.getNombre(),
                "LOGIN",
                "EXITOSA",
                "Inicio de sesión exitoso",
                "Usuario autenticado correctamente"
        );
    }

    /**
     * Crea una entrada de bitácora para login fallido
     * @param codigo Código del intento de login
     * @param motivo Motivo del fallo
     * @return Objeto Bitacora
     */
    public static Bitacora loginFallido(String codigo, String motivo) {
        return new Bitacora(
                "DESCONOCIDO",
                codigo,
                "LOGIN",
                "FALLIDA",
                "Intento de inicio de sesión fallido: " + motivo
        );
    }

    /**
     * Crea una entrada de bitácora para logout
     * @param usuario Usuario que cerró sesión
     * @return Objeto Bitacora
     */
    public static Bitacora logout(Usuario usuario) {
        return new Bitacora(
                usuario.getTipoUsuario(),
                usuario.getCodigo(),
                usuario.getNombre(),
                "LOGOUT",
                "EXITOSA",
                "Cierre de sesión",
                "Usuario cerró sesión correctamente"
        );
    }

    /**
     * Crea una entrada de bitácora para creación de usuario
     * @param admin Administrador que crea el usuario
     * @param tipoCreado Tipo de usuario creado
     * @param codigoCreado Código del usuario creado
     * @return Objeto Bitacora
     */
    public static Bitacora crearUsuario(Usuario admin, String tipoCreado, String codigoCreado) {
        return new Bitacora(
                admin.getTipoUsuario(),
                admin.getCodigo(),
                admin.getNombre(),
                "CREAR_USUARIO",
                "EXITOSA",
                "Usuario " + tipoCreado + " creado con código: " + codigoCreado,
                "Tipo: " + tipoCreado
        );
    }

    /**
     * Crea una entrada de bitácora para creación de producto
     * @param admin Administrador que crea el producto
     * @param codigoProducto Código del producto creado
     * @param nombreProducto Nombre del producto
     * @return Objeto Bitacora
     */
    public static Bitacora crearProducto(Usuario admin, String codigoProducto, String nombreProducto) {
        return new Bitacora(
                admin.getTipoUsuario(),
                admin.getCodigo(),
                admin.getNombre(),
                "CREAR_PRODUCTO",
                "EXITOSA",
                "Producto creado: " + nombreProducto,
                "Código: " + codigoProducto
        );
    }

    /**
     * Crea una entrada de bitácora para agregar stock
     * @param vendedor Vendedor que agrega stock
     * @param codigoProducto Código del producto
     * @param cantidad Cantidad agregada
     * @return Objeto Bitacora
     */
    public static Bitacora agregarStock(Vendedor vendedor, String codigoProducto, int cantidad) {
        return new Bitacora(
                vendedor.getTipoUsuario(),
                vendedor.getCodigo(),
                vendedor.getNombre(),
                "AGREGAR_STOCK",
                "EXITOSA",
                "Stock agregado: Producto " + codigoProducto + ", Cantidad: " + cantidad,
                "Cantidad ingresada: " + cantidad
        );
    }

    /**
     * Crea una entrada de bitácora para realizar pedido
     * @param cliente Cliente que realiza el pedido
     * @param codigoPedido Código del pedido
     * @param total Total del pedido
     * @param cantidadProductos Cantidad de productos diferentes
     * @return Objeto Bitacora
     */
    public static Bitacora realizarPedido(Cliente cliente, String codigoPedido, double total, int cantidadProductos) {
        return new Bitacora(
                cliente.getTipoUsuario(),
                cliente.getCodigo(),
                cliente.getNombre(),
                "REALIZAR_PEDIDO",
                "EXITOSA",
                "Pedido creado: " + codigoPedido + ", Total: $" + String.format("%.2f", total) +
                        ", Productos: " + cantidadProductos,
                "Total: $" + String.format("%.2f", total)
        );
    }

    /**
     * Crea una entrada de bitácora para confirmar pedido
     * @param vendedor Vendedor que confirma el pedido
     * @param codigoPedido Código del pedido
     * @return Objeto Bitacora
     */
    public static Bitacora confirmarPedido(Vendedor vendedor, String codigoPedido) {
        return new Bitacora(
                vendedor.getTipoUsuario(),
                vendedor.getCodigo(),
                vendedor.getNombre(),
                "CONFIRMAR_PEDIDO",
                "EXITOSA",
                "Pedido confirmado: " + codigoPedido,
                "Pedido procesado y completado"
        );
    }

    /**
     * Crea una entrada de bitácora para generación de reporte
     * @param usuario Usuario que genera el reporte
     * @param tipoReporte Tipo de reporte generado
     * @param nombreArchivo Nombre del archivo generado
     * @return Objeto Bitacora
     */
    public static Bitacora generarReporte(Usuario usuario, String tipoReporte, String nombreArchivo) {
        return new Bitacora(
                usuario.getTipoUsuario(),
                usuario.getCodigo(),
                usuario.getNombre(),
                "GENERAR_REPORTE",
                "EXITOSA",
                "Reporte generado: " + tipoReporte,
                "Archivo: " + nombreArchivo
        );
    }

    /**
     * Crea una entrada de bitácora para carga de archivo CSV
     * @param usuario Usuario que carga el archivo
     * @param tipoArchivo Tipo de archivo cargado
     * @param cantidadRegistros Cantidad de registros cargados
     * @return Objeto Bitacora
     */
    public static Bitacora cargarCSV(Usuario usuario, String tipoArchivo, int cantidadRegistros) {
        return new Bitacora(
                usuario.getTipoUsuario(),
                usuario.getCodigo(),
                usuario.getNombre(),
                "CARGAR_CSV",
                "EXITOSA",
                "Archivo CSV cargado: " + tipoArchivo + ", Registros: " + cantidadRegistros,
                "Registros procesados: " + cantidadRegistros
        );
    }

    /**
     * Crea una entrada de bitácora para error del sistema
     * @param usuario Usuario que experimentó el error
     * @param operacion Operación que falló
     * @param mensajeError Mensaje de error
     * @return Objeto Bitacora
     */
    public static Bitacora error(Usuario usuario, String operacion, String mensajeError) {
        return new Bitacora(
                usuario != null ? usuario.getTipoUsuario() : "SISTEMA",
                usuario != null ? usuario.getCodigo() : "SYSTEM",
                usuario != null ? usuario.getNombre() : "Sistema",
                operacion,
                "FALLIDA",
                "Error: " + mensajeError,
                "Operación falló: " + mensajeError
        );
    }

    @Override
    public String toString() {
        return "Bitacora{" +
                "fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", usuario='" + tipoUsuario + " (" + codigoUsuario + ")'" +
                ", operacion='" + operacion + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}