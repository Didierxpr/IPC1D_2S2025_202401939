package utilidades;

import interfaces.IPersistencia;
import java.io.*;

/**
 * Clase genérica para serialización de objetos
 * Implementa la persistencia de datos mediante archivos .ser
 *
 * @param <T> Tipo de objeto a serializar
 */
public class Serializacion<T> implements IPersistencia<T> {

    private String rutaArchivo;
    private String nombreArchivo;
    private static final String DIRECTORIO_DATOS = "datos/";
    private static final String DIRECTORIO_RESPALDOS = "respaldos/";

    /**
     * Constructor
     * @param nombreArchivo Nombre del archivo .ser (sin extensión)
     */
    public Serializacion(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        this.rutaArchivo = DIRECTORIO_DATOS + nombreArchivo + ".ser";

        // Crear directorios si no existen
        ManejadorArchivos.crearDirectorio(DIRECTORIO_DATOS);
        ManejadorArchivos.crearDirectorio(DIRECTORIO_RESPALDOS);
    }

    // ==================== IMPLEMENTACIÓN DE IPersistencia ====================

    @Override
    public boolean guardar(T[] datos) {
        ObjectOutputStream output = null;

        try {
            output = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
            output.writeObject(datos);

            return true;

        } catch (IOException e) {
            System.err.println("Error al serializar datos: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar stream: " + e.getMessage());
                }
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] cargar() {
        ObjectInputStream input = null;

        try {
            if (!existenDatos()) {
                System.out.println("No existen datos para cargar en: " + rutaArchivo);
                return null;
            }

            input = new ObjectInputStream(new FileInputStream(rutaArchivo));
            T[] datos = (T[]) input.readObject();

            return datos;

        } catch (IOException e) {
            System.err.println("Error al deserializar datos: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Clase no encontrada al deserializar: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar stream: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public boolean existenDatos() {
        return ManejadorArchivos.existeArchivo(rutaArchivo);
    }

    @Override
    public boolean eliminarDatos() {
        return ManejadorArchivos.eliminarArchivo(rutaArchivo);
    }

    @Override
    public String obtenerRutaArchivo() {
        return rutaArchivo;
    }

    @Override
    public boolean crearRespaldo(String nombreRespaldo) {
        if (!existenDatos()) {
            System.err.println("No hay datos para respaldar");
            return false;
        }

        String rutaRespaldo = DIRECTORIO_RESPALDOS + nombreRespaldo + ".ser";
        return ManejadorArchivos.copiarArchivo(rutaArchivo, rutaRespaldo);
    }

    @Override
    public boolean restaurarRespaldo(String nombreRespaldo) {
        String rutaRespaldo = DIRECTORIO_RESPALDOS + nombreRespaldo + ".ser";

        if (!ManejadorArchivos.existeArchivo(rutaRespaldo)) {
            System.err.println("El respaldo no existe: " + rutaRespaldo);
            return false;
        }

        return ManejadorArchivos.copiarArchivo(rutaRespaldo, rutaArchivo);
    }

    // ==================== MÉTODOS ADICIONALES ====================

    /**
     * Guarda un solo objeto
     * @param objeto Objeto a guardar
     * @return true si se guardó exitosamente
     */
    public boolean guardarUno(T objeto) {
        ObjectOutputStream output = null;

        try {
            output = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
            output.writeObject(objeto);

            return true;

        } catch (IOException e) {
            System.err.println("Error al serializar objeto: " + e.getMessage());
            return false;
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar stream: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Carga un solo objeto
     * @return Objeto cargado o null si hay error
     */
    @SuppressWarnings("unchecked")
    public T cargarUno() {
        ObjectInputStream input = null;

        try {
            if (!existenDatos()) {
                return null;
            }

            input = new ObjectInputStream(new FileInputStream(rutaArchivo));
            T objeto = (T) input.readObject();

            return objeto;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al deserializar objeto: " + e.getMessage());
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar stream: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Obtiene información del archivo serializado
     * @return String con información del archivo
     */
    public String obtenerInformacion() {
        if (!existenDatos()) {
            return "Archivo no existe: " + rutaArchivo;
        }

        long tamano = ManejadorArchivos.obtenerTamanioArchivo(rutaArchivo);
        double tamanoKB = tamano / 1024.0;

        return "Archivo: " + nombreArchivo + ".ser\n" +
                "Ruta: " + rutaArchivo + "\n" +
                "Tamaño: " + String.format("%.2f", tamanoKB) + " KB\n" +
                "Existe: Sí";
    }

    /**
     * Crea un respaldo automático con timestamp
     * @return true si se creó exitosamente
     */
    public boolean crearRespaldoAutomatico() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new java.util.Date());
        String nombreRespaldo = nombreArchivo + "_" + timestamp;

        return crearRespaldo(nombreRespaldo);
    }

    /**
     * Lista todos los respaldos disponibles para este archivo
     * @return Array con nombres de respaldos
     */
    public String[] listarRespaldos() {
        String[] archivos = ManejadorArchivos.listarArchivos(DIRECTORIO_RESPALDOS);
        String[] respaldos = new String[0];

        for (String archivo : archivos) {
            if (archivo.startsWith(nombreArchivo) && archivo.endsWith(".ser")) {
                respaldos = Vectores.agregarString(respaldos,
                        ManejadorArchivos.obtenerNombreSinExtension(archivo));
            }
        }

        return respaldos;
    }

    /**
     * Elimina un respaldo específico
     * @param nombreRespaldo Nombre del respaldo a eliminar
     * @return true si se eliminó exitosamente
     */
    public boolean eliminarRespaldo(String nombreRespaldo) {
        String rutaRespaldo = DIRECTORIO_RESPALDOS + nombreRespaldo + ".ser";
        return ManejadorArchivos.eliminarArchivo(rutaRespaldo);
    }

    /**
     * Valida la integridad del archivo serializado
     * @return true si el archivo es válido
     */
    public boolean validarIntegridad() {
        try {
            T[] datos = cargar();
            return datos != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene el nombre del archivo sin extensión
     * @return Nombre del archivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * Cambia la ruta del archivo
     * @param nuevaRuta Nueva ruta del archivo
     */
    public void setRutaArchivo(String nuevaRuta) {
        this.rutaArchivo = nuevaRuta;
    }
}
