package controlador;

import interfaces.ICRUD;
import interfaces.ICargaMasiva;
import modelo.*;
import utilidades.*;

/**
 * Controlador para gestión de vendedores y productos por el administrador
 */
public class ControladorAdministrador {

    private Usuario usuarioActual;
    private ControladorVendedor controladorVendedor;
    private ControladorProducto controladorProducto;

    public ControladorAdministrador(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        this.controladorVendedor = new ControladorVendedor(usuarioActual);
        this.controladorProducto = new ControladorProducto(usuarioActual);
    }

    // ==================== GESTIÓN DE VENDEDORES ====================

    /**
     * Crea un nuevo vendedor
     */
    public boolean crearVendedor(String codigo, String nombre, String genero, String contrasenia) {
        ValidadorDatos validador = new ValidadorDatos();

        // Validaciones
        if (!validador.validarCampoRequerido(codigo, "Código")) {
            System.err.println(validador.obtenerMensajeError());
            return false;
        }

        if (!validador.validarCampoRequerido(nombre, "Nombre")) {
            System.err.println(validador.obtenerMensajeError());
            return false;
        }

        if (!validador.validarGenero(genero)) {
            System.err.println(validador.obtenerMensajeError());
            return false;
        }

        if (!validador.validarContrasena(contrasenia, 4)) {
            System.err.println(validador.obtenerMensajeError());
            return false;
        }

        Vendedor vendedor = new Vendedor(codigo, nombre, genero, contrasenia);
        boolean resultado = controladorVendedor.crear(vendedor);

        if (resultado && usuarioActual instanceof Administrador) {
            ((Administrador) usuarioActual).incrementarUsuariosCreados();
            SistemaArchivos.guardarAdministradores(new Administrador[]{(Administrador) usuarioActual});
        }

        return resultado;
    }

    /**
     * Actualiza un vendedor existente
     */
    public boolean actualizarVendedor(String codigo, String nuevoNombre, String nuevaContrasenia) {
        Vendedor vendedor = controladorVendedor.buscarPorCodigo(codigo);

        if (vendedor == null) {
            System.err.println("Vendedor no encontrado");
            return false;
        }

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            vendedor.setNombre(nuevoNombre);
        }

        if (nuevaContrasenia != null && !nuevaContrasenia.trim().isEmpty()) {
            vendedor.setContrasenia(nuevaContrasenia);
        }

        return controladorVendedor.actualizar(codigo, vendedor);
    }

    /**
     * Elimina un vendedor
     */
    public boolean eliminarVendedor(String codigo) {
        return controladorVendedor.eliminar(codigo);
    }

    /**
     * Obtiene todos los vendedores
     */
    public Vendedor[] obtenerTodosVendedores() {
        return controladorVendedor.obtenerTodos();
    }

    /**
     * Busca un vendedor por código
     */
    public Vendedor buscarVendedor(String codigo) {
        return controladorVendedor.buscarPorCodigo(codigo);
    }

    /**
     * Carga vendedores desde un archivo CSV
     */
    public boolean cargarVendedoresCSV(String rutaArchivo) {
        String[] lineas = ManejadorArchivos.leerCSV(rutaArchivo, true);

        if (lineas == null || lineas.length == 0) {
            System.err.println("No se pudieron leer los datos del archivo");
            return false;
        }

        int exitosos = 0;
        int errores = 0;

        for (String linea : lineas) {
            if (linea.trim().isEmpty()) {
                continue;
            }

            String[] datos = ManejadorArchivos.parsearLineaCSV(linea);

            if (datos.length < 4) {
                System.err.println("Línea con formato incorrecto: " + linea);
                errores++;
                continue;
            }

            String codigo = datos[0].trim();
            String nombre = datos[1].trim();
            String genero = datos[2].trim();
            String contrasenia = datos[3].trim();

            if (crearVendedor(codigo, nombre, genero, contrasenia)) {
                exitosos++;
            } else {
                errores++;
            }
        }

        System.out.println("Carga completada: " + exitosos + " exitosos, " + errores + " errores");
        return exitosos > 0;
    }

    // ==================== GESTIÓN DE PRODUCTOS ====================

    /**
     * Crea un nuevo producto
     */
    public boolean crearProducto(String codigo, String nombre, String categoria,
                                 double precio, String atributoEspecifico) {
        ValidadorDatos validador = new ValidadorDatos();

        // Validaciones
        if (!validador.validarCampoRequerido(codigo, "Código")) {
            System.err.println(validador.obtenerMensajeError());
            return false;
        }

        if (!validador.validarCampoRequerido(nombre, "Nombre")) {
            System.err.println(validador.obtenerMensajeError());
            return false;
        }

        if (!validador.validarCategoria(categoria)) {
            System.err.println(validador.obtenerMensajeError());
            return false;
        }

        if (!validador.validarPrecio(precio)) {
            System.err.println(validador.obtenerMensajeError());
            return false;
        }

        Producto producto = null;
        categoria = categoria.toUpperCase();

        switch (categoria) {
            case "TECNOLOGIA":
                try {
                    int mesesGarantia = Integer.parseInt(atributoEspecifico);
                    producto = new ProductoTecnologia(codigo, nombre, precio, mesesGarantia);
                } catch (NumberFormatException e) {
                    System.err.println("Meses de garantía inválidos");
                    return false;
                }
                break;

            case "ALIMENTO":
                if (!validador.validarFormatoFecha(atributoEspecifico)) {
                    System.err.println(validador.obtenerMensajeError());
                    return false;
                }
                producto = new ProductoAlimento(codigo, nombre, precio, atributoEspecifico);
                break;

            case "GENERAL":
                if (!validador.validarCampoRequerido(atributoEspecifico, "Material")) {
                    System.err.println(validador.obtenerMensajeError());
                    return false;
                }
                producto = new ProductoGeneral(codigo, nombre, precio, atributoEspecifico);
                break;
        }

        boolean resultado = controladorProducto.crear(producto);

        if (resultado && usuarioActual instanceof Administrador) {
            ((Administrador) usuarioActual).incrementarProductosGestionados();
            SistemaArchivos.guardarAdministradores(new Administrador[]{(Administrador) usuarioActual});
        }

        return resultado;
    }

    /**
     * Actualiza un producto existente
     */
    public boolean actualizarProducto(String codigo, String nuevoNombre, String nuevoAtributo) {
        Producto producto = controladorProducto.buscarPorCodigo(codigo);

        if (producto == null) {
            System.err.println("Producto no encontrado");
            return false;
        }

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            producto.setNombre(nuevoNombre);
        }

        // Actualizar atributo específico según categoría
        if (nuevoAtributo != null && !nuevoAtributo.trim().isEmpty()) {
            if (producto instanceof ProductoTecnologia) {
                try {
                    int mesesGarantia = Integer.parseInt(nuevoAtributo);
                    ((ProductoTecnologia) producto).setMesesGarantia(mesesGarantia);
                } catch (NumberFormatException e) {
                    System.err.println("Meses de garantía inválidos");
                    return false;
                }
            } else if (producto instanceof ProductoAlimento) {
                ValidadorDatos validador = new ValidadorDatos();
                if (validador.validarFormatoFecha(nuevoAtributo)) {
                    ((ProductoAlimento) producto).setFechaCaducidad(nuevoAtributo);
                } else {
                    System.err.println(validador.obtenerMensajeError());
                    return false;
                }
            } else if (producto instanceof ProductoGeneral) {
                ((ProductoGeneral) producto).setMaterial(nuevoAtributo);
            }
        }

        return controladorProducto.actualizar(codigo, producto);
    }

    /**
     * Elimina un producto
     */
    public boolean eliminarProducto(String codigo) {
        return controladorProducto.eliminar(codigo);
    }

    /**
     * Obtiene todos los productos
     */
    public Producto[] obtenerTodosProductos() {
        return controladorProducto.obtenerTodos();
    }

    /**
     * Busca un producto por código
     */
    public Producto buscarProducto(String codigo) {
        return controladorProducto.buscarPorCodigo(codigo);
    }

    /**
     * Carga productos desde un archivo CSV
     */
    public boolean cargarProductosCSV(String rutaArchivo) {
        String[] lineas = ManejadorArchivos.leerCSV(rutaArchivo, true);

        if (lineas == null || lineas.length == 0) {
            System.err.println("No se pudieron leer los datos del archivo");
            return false;
        }

        int exitosos = 0;
        int errores = 0;

        for (String linea : lineas) {
            if (linea.trim().isEmpty()) {
                continue;
            }

            String[] datos = ManejadorArchivos.parsearLineaCSV(linea);

            if (datos.length < 5) {
                System.err.println("Línea con formato incorrecto: " + linea);
                errores++;
                continue;
            }

            String codigo = datos[0].trim();
            String nombre = datos[1].trim();
            String categoria = datos[2].trim();
            double precio;

            try {
                precio = Double.parseDouble(datos[3].trim());
            } catch (NumberFormatException e) {
                System.err.println("Precio inválido en línea: " + linea);
                errores++;
                continue;
            }

            String atributoEspecifico = datos[4].trim();

            if (crearProducto(codigo, nombre, categoria, precio, atributoEspecifico)) {
                exitosos++;
            } else {
                errores++;
            }
        }

        System.out.println("Carga completada: " + exitosos + " exitosos, " + errores + " errores");
        return exitosos > 0;
    }

    /**
     * Obtiene estadísticas del administrador
     */
    public String obtenerEstadisticas() {
        if (!(usuarioActual instanceof Administrador)) {
            return "Usuario no es administrador";
        }

        Administrador admin = (Administrador) usuarioActual;

        return "╔════════════════════════════════════════╗\n" +
                "║   ESTADÍSTICAS DEL ADMINISTRADOR      ║\n" +
                "╠════════════════════════════════════════╣\n" +
                "║ Usuarios creados: " + String.format("%-18d", admin.getUsuariosCreados()) + "║\n" +
                "║ Productos gestionados: " + String.format("%-13d", admin.getProductosGestionados()) + "║\n" +
                "║ Vendedores activos: " + String.format("%-16d", controladorVendedor.contarRegistros()) + "║\n" +
                "║ Productos en sistema: " + String.format("%-14d", controladorProducto.contarRegistros()) + "║\n" +
                "╚════════════════════════════════════════╝";
    }
}
