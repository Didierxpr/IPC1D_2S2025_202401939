package modelo.persistencia;

import java.io.*;
import modelo.estructuras.*;
import modelo.bitacora.RegistroBitacora;

public class Persistencia {

    private static final String RUTA_USUARIOS = "data/usuarios.ser";
    private static final String RUTA_PRODUCTOS = "data/productos.ser";
    private static final String RUTA_PEDIDOS = "data/pedidos.ser";
    private static final String RUTA_BITACORA = "data/bitacora.ser";

    // =========================================================
    // 🔹 Guardar todos los datos del sistema
    // =========================================================
    public static void guardarDatos(ListaUsuarios usuarios, ListaProductos productos,
                                    ListaPedidos pedidos, RegistroBitacora bitacora) {
        crearCarpetaData(); // 👈 Previene errores si la carpeta no existe

        guardarObjeto(RUTA_USUARIOS, usuarios);
        guardarObjeto(RUTA_PRODUCTOS, productos);
        guardarObjeto(RUTA_PEDIDOS, pedidos);
        guardarObjeto(RUTA_BITACORA, bitacora);

        System.out.println("💾 Datos guardados correctamente en /data");
    }

    // =========================================================
    // 🔹 Cargar los objetos desde los archivos binarios
    // =========================================================
    public static Object[] cargarDatos() {
        ListaUsuarios usuarios = (ListaUsuarios) cargarObjeto(RUTA_USUARIOS);
        ListaProductos productos = (ListaProductos) cargarObjeto(RUTA_PRODUCTOS);
        ListaPedidos pedidos = (ListaPedidos) cargarObjeto(RUTA_PEDIDOS);
        RegistroBitacora bitacora = (RegistroBitacora) cargarObjeto(RUTA_BITACORA);

        return new Object[]{usuarios, productos, pedidos, bitacora};
    }

    // =========================================================
    // 🔹 Métodos auxiliares genéricos
    // =========================================================
    private static void guardarObjeto(String ruta, Object objeto) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(objeto);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar " + ruta + ": " + e.getMessage());
        }
    }

    private static Object cargarObjeto(String ruta) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("⚠️ Archivo no encontrado: " + ruta);
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error al cargar " + ruta + ": " + e.getMessage());
            return null;
        }
    }

    // =========================================================
    // 🔹 Crear carpeta /data si no existe (mejora de seguridad)
    // =========================================================
    private static void crearCarpetaData() {
        File carpeta = new File("data");
        if (!carpeta.exists()) {
            if (carpeta.mkdir()) {
                System.out.println("📂 Carpeta /data creada automáticamente.");
            } else {
                System.out.println("⚠️ No se pudo crear la carpeta /data.");
            }
        }
    }
}


