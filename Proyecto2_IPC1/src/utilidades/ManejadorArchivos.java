package utilidades;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Clase utilitaria para manejo de archivos
 * Proporciona métodos para leer, escribir y manipular archivos CSV y de texto
 */
public class ManejadorArchivos {

    /**
     * Lee un archivo de texto completo
     * @param rutaArchivo Ruta del archivo a leer
     * @return Contenido del archivo como String o null si hay error
     */
    public static String leerArchivoTexto(String rutaArchivo) {
        BufferedReader reader = null;
        StringBuilder contenido = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(rutaArchivo, StandardCharsets.UTF_8));
            String linea;

            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }

            return contenido.toString();

        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo no encontrado - " + rutaArchivo);
            return null;
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar archivo: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Lee un archivo y retorna sus líneas como array
     * @param rutaArchivo Ruta del archivo a leer
     * @return Array de líneas o null si hay error
     */
    public static String[] leerLineas(String rutaArchivo) {
        BufferedReader reader = null;
        String[] lineas = new String[0];

        try {
            reader = new BufferedReader(new FileReader(rutaArchivo, StandardCharsets.UTF_8));
            String linea;

            while ((linea = reader.readLine()) != null) {
                lineas = Vectores.agregarString(lineas, linea);
            }

            return lineas;

        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo no encontrado - " + rutaArchivo);
            return null;
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar archivo: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Escribe contenido en un archivo de texto (sobrescribe el archivo)
     * @param rutaArchivo Ruta del archivo
     * @param contenido Contenido a escribir
     * @return true si se escribió exitosamente
     */
    public static boolean escribirArchivo(String rutaArchivo, String contenido) {
        BufferedWriter writer = null;

        try {
            // Crear directorios si no existen
            File archivo = new File(rutaArchivo);
            File directorio = archivo.getParentFile();

            if (directorio != null && !directorio.exists()) {
                directorio.mkdirs();
            }

            writer = new BufferedWriter(new FileWriter(archivo, StandardCharsets.UTF_8));
            writer.write(contenido);

            return true;

        } catch (IOException e) {
            System.err.println("Error al escribir archivo: " + e.getMessage());
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar archivo: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Escribe líneas en un archivo (sobrescribe el archivo)
     * @param rutaArchivo Ruta del archivo
     * @param lineas Array de líneas a escribir
     * @return true si se escribió exitosamente
     */
    public static boolean escribirLineas(String rutaArchivo, String[] lineas) {
        BufferedWriter writer = null;

        try {
            // Crear directorios si no existen
            File archivo = new File(rutaArchivo);
            File directorio = archivo.getParentFile();

            if (directorio != null && !directorio.exists()) {
                directorio.mkdirs();
            }

            writer = new BufferedWriter(new FileWriter(archivo, StandardCharsets.UTF_8));

            for (int i = 0; i < lineas.length; i++) {
                writer.write(lineas[i]);
                if (i < lineas.length - 1) {
                    writer.newLine();
                }
            }

            return true;

        } catch (IOException e) {
            System.err.println("Error al escribir archivo: " + e.getMessage());
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar archivo: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Agrega contenido al final de un archivo (append)
     * @param rutaArchivo Ruta del archivo
     * @param contenido Contenido a agregar
     * @return true si se agregó exitosamente
     */
    public static boolean agregarAlArchivo(String rutaArchivo, String contenido) {
        BufferedWriter writer = null;

        try {
            // Crear directorios si no existen
            File archivo = new File(rutaArchivo);
            File directorio = archivo.getParentFile();

            if (directorio != null && !directorio.exists()) {
                directorio.mkdirs();
            }

            writer = new BufferedWriter(new FileWriter(archivo, StandardCharsets.UTF_8, true));
            writer.write(contenido);
            writer.newLine();

            return true;

        } catch (IOException e) {
            System.err.println("Error al agregar al archivo: " + e.getMessage());
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar archivo: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Verifica si un archivo existe
     * @param rutaArchivo Ruta del archivo
     * @return true si existe
     */
    public static boolean existeArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        return archivo.exists() && archivo.isFile();
    }

    /**
     * Verifica si un directorio existe
     * @param rutaDirectorio Ruta del directorio
     * @return true si existe
     */
    public static boolean existeDirectorio(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);
        return directorio.exists() && directorio.isDirectory();
    }

    /**
     * Crea un directorio y todos sus padres si no existen
     * @param rutaDirectorio Ruta del directorio
     * @return true si se creó exitosamente o ya existía
     */
    public static boolean crearDirectorio(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);

        if (directorio.exists()) {
            return true;
        }

        return directorio.mkdirs();
    }

    /**
     * Elimina un archivo
     * @param rutaArchivo Ruta del archivo a eliminar
     * @return true si se eliminó exitosamente
     */
    public static boolean eliminarArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);

        if (!archivo.exists()) {
            return false;
        }

        return archivo.delete();
    }

    /**
     * Copia un archivo de una ubicación a otra
     * @param rutaOrigen Ruta del archivo origen
     * @param rutaDestino Ruta del archivo destino
     * @return true si se copió exitosamente
     */
    public static boolean copiarArchivo(String rutaOrigen, String rutaDestino) {
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Crear directorios si no existen
            File archivoDestino = new File(rutaDestino);
            File directorio = archivoDestino.getParentFile();

            if (directorio != null && !directorio.exists()) {
                directorio.mkdirs();
            }

            input = new BufferedInputStream(new FileInputStream(rutaOrigen));
            output = new BufferedOutputStream(new FileOutputStream(rutaDestino));

            byte[] buffer = new byte[1024];
            int bytesLeidos;

            while ((bytesLeidos = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesLeidos);
            }

            return true;

        } catch (IOException e) {
            System.err.println("Error al copiar archivo: " + e.getMessage());
            return false;
        } finally {
            try {
                if (input != null) input.close();
                if (output != null) output.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar archivos: " + e.getMessage());
            }
        }
    }

    /**
     * Renombra un archivo
     * @param rutaActual Ruta actual del archivo
     * @param rutaNueva Nueva ruta del archivo
     * @return true si se renombró exitosamente
     */
    public static boolean renombrarArchivo(String rutaActual, String rutaNueva) {
        File archivoActual = new File(rutaActual);
        File archivoNuevo = new File(rutaNueva);

        if (!archivoActual.exists()) {
            return false;
        }

        return archivoActual.renameTo(archivoNuevo);
    }

    /**
     * Obtiene el tamaño de un archivo en bytes
     * @param rutaArchivo Ruta del archivo
     * @return Tamaño en bytes o -1 si hay error
     */
    public static long obtenerTamanioArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);

        if (!archivo.exists() || !archivo.isFile()) {
            return -1;
        }

        return archivo.length();
    }

    /**
     * Lista todos los archivos en un directorio
     * @param rutaDirectorio Ruta del directorio
     * @return Array con nombres de archivos
     */
    public static String[] listarArchivos(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);

        if (!directorio.exists() || !directorio.isDirectory()) {
            return new String[0];
        }

        File[] archivos = directorio.listFiles();
        String[] nombresArchivos = new String[0];

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    nombresArchivos = Vectores.agregarString(nombresArchivos, archivo.getName());
                }
            }
        }

        return nombresArchivos;
    }

    /**
     * Lista todos los directorios en un directorio
     * @param rutaDirectorio Ruta del directorio
     * @return Array con nombres de directorios
     */
    public static String[] listarDirectorios(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);

        if (!directorio.exists() || !directorio.isDirectory()) {
            return new String[0];
        }

        File[] archivos = directorio.listFiles();
        String[] nombresDirectorios = new String[0];

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    nombresDirectorios = Vectores.agregarString(nombresDirectorios, archivo.getName());
                }
            }
        }

        return nombresDirectorios;
    }

    // ==================== MÉTODOS ESPECÍFICOS PARA CSV ====================

    /**
     * Lee un archivo CSV y retorna sus líneas (omitiendo el encabezado)
     * @param rutaArchivo Ruta del archivo CSV
     * @param omitirEncabezado true si se debe omitir la primera línea
     * @return Array de líneas
     */
    public static String[] leerCSV(String rutaArchivo, boolean omitirEncabezado) {
        String[] lineas = leerLineas(rutaArchivo);

        if (lineas == null || lineas.length == 0) {
            return new String[0];
        }

        if (omitirEncabezado && lineas.length > 1) {
            String[] resultado = new String[lineas.length - 1];
            for (int i = 1; i < lineas.length; i++) {
                resultado[i - 1] = lineas[i];
            }
            return resultado;
        }

        return lineas;
    }

    /**
     * Escribe datos en formato CSV
     * @param rutaArchivo Ruta del archivo
     * @param encabezado Encabezado del CSV
     * @param datos Líneas de datos
     * @return true si se escribió exitosamente
     */
    public static boolean escribirCSV(String rutaArchivo, String encabezado, String[] datos) {
        String[] lineas = new String[datos.length + 1];
        lineas[0] = encabezado;

        for (int i = 0; i < datos.length; i++) {
            lineas[i + 1] = datos[i];
        }

        return escribirLineas(rutaArchivo, lineas);
    }

    /**
     * Parsea una línea CSV respetando comillas
     * @param linea Línea a parsear
     * @return Array con los valores separados
     */
    public static String[] parsearLineaCSV(String linea) {
        if (linea == null || linea.isEmpty()) {
            return new String[0];
        }

        String[] valores = new String[0];
        StringBuilder valorActual = new StringBuilder();
        boolean dentroDeComillas = false;

        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);

            if (c == '"') {
                dentroDeComillas = !dentroDeComillas;
            } else if (c == ',' && !dentroDeComillas) {
                valores = Vectores.agregarString(valores, valorActual.toString().trim());
                valorActual = new StringBuilder();
            } else {
                valorActual.append(c);
            }
        }

        // Agregar el último valor
        valores = Vectores.agregarString(valores, valorActual.toString().trim());

        return valores;
    }

    /**
     * Escapa un valor para formato CSV (agrega comillas si es necesario)
     * @param valor Valor a escapar
     * @return Valor escapado
     */
    public static String escaparCSV(String valor) {
        if (valor == null) {
            return "";
        }

        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }

        return valor;
    }

    /**
     * Valida si una línea CSV tiene el número correcto de columnas
     * @param linea Línea a validar
     * @param numeroColumnas Número esperado de columnas
     * @return true si es válida
     */
    public static boolean validarLineaCSV(String linea, int numeroColumnas) {
        if (linea == null || linea.trim().isEmpty()) {
            return false;
        }

        String[] valores = parsearLineaCSV(linea);
        return valores.length == numeroColumnas;
    }

    /**
     * Obtiene la extensión de un archivo
     * @param nombreArchivo Nombre del archivo
     * @return Extensión sin el punto, o cadena vacía si no tiene
     */
    public static String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            return "";
        }

        int ultimoPunto = nombreArchivo.lastIndexOf('.');

        if (ultimoPunto == -1 || ultimoPunto == nombreArchivo.length() - 1) {
            return "";
        }

        return nombreArchivo.substring(ultimoPunto + 1);
    }

    /**
     * Obtiene el nombre de archivo sin extensión
     * @param nombreArchivo Nombre del archivo
     * @return Nombre sin extensión
     */
    public static String obtenerNombreSinExtension(String nombreArchivo) {
        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            return "";
        }

        int ultimoPunto = nombreArchivo.lastIndexOf('.');

        if (ultimoPunto == -1) {
            return nombreArchivo;
        }

        return nombreArchivo.substring(0, ultimoPunto);
    }
}