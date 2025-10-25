package modelo;

import modelo.estructuras.*;
import modelo.usuarios.*;
import modelo.bitacora.RegistroBitacora;
import modelo.persistencia.Persistencia;

public class Sistema implements java.io.Serializable {

    private static ListaUsuarios listaUsuarios;
    private static ListaProductos listaProductos;
    private static ListaPedidos listaPedidos;
    private static RegistroBitacora registroBitacora;

    // =========================================================
    // 🔹 Inicialización del sistema
    // =========================================================
    public static void inicializar() {
        Object[] datos = Persistencia.cargarDatos();

        try {
            listaUsuarios    = (ListaUsuarios) datos[0];
            listaProductos   = (ListaProductos) datos[1];
            listaPedidos     = (ListaPedidos) datos[2];
            registroBitacora = (RegistroBitacora) datos[3];
        } catch (Exception e) {
            System.out.println("⚠️ Error al cargar datos serializados: " + e.getMessage());
        }

        // 🔹 Si no existen archivos previos, crear nuevas instancias
        if (listaUsuarios == null) listaUsuarios = new ListaUsuarios(50);
        if (listaProductos == null) listaProductos = new ListaProductos();
        if (listaPedidos == null) listaPedidos = new ListaPedidos(100);
        if (registroBitacora == null) registroBitacora = new RegistroBitacora(200);

        // 🔹 Crear usuario admin por defecto si no existe
        if (listaUsuarios.buscarUsuario("admin") == null) {
            listaUsuarios.agregarUsuario(new Administrador(
                    "admin", "Administrador General", "M", "IPC1D"
            ));
            System.out.println("👤 Usuario administrador creado por defecto (admin / IPC1D)");
        }

        System.out.println("✅ Sistema inicializado correctamente.");
    }


    // =========================================================
    // 🔹 Guardar datos antes de cerrar el sistema
    // =========================================================
    public static void guardarDatos() {
        Persistencia.guardarDatos(listaUsuarios, listaProductos, listaPedidos, registroBitacora);
        System.out.println("Datos guardados correctamente.");
    }

    // =========================================================
    // 🔹 Getters y Setters globales
    // =========================================================
    public static ListaUsuarios getListaUsuarios() { return listaUsuarios; }
    public static ListaProductos getListaProductos() { return listaProductos; }
    public static ListaPedidos getListaPedidos() { return listaPedidos; }
    public static RegistroBitacora getRegistroBitacora() { return registroBitacora; }

    public static void setListaUsuarios(ListaUsuarios lista) { listaUsuarios = lista; }
    public static void setListaProductos(ListaProductos lista) { listaProductos = lista; }
    public static void setListaPedidos(ListaPedidos lista) { listaPedidos = lista; }
    public static void setRegistroBitacora(RegistroBitacora registro) { registroBitacora = registro; }

}




