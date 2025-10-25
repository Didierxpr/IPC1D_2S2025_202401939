package controlador;

import java.io.*;
import java.util.Scanner;
import modelo.usuarios.*;
import modelo.estructuras.ListaUsuarios;

public class ControladorUsuarios {

    private ListaUsuarios listaUsuarios;
    private Scanner scanner;

    public ControladorUsuarios(ListaUsuarios listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
        this.scanner = new Scanner(System.in);
    }

    // =============================================================
    // 🔹 MENÚ PRINCIPAL DE USUARIOS
    // =============================================================
    public void menuUsuarios() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n===== GESTIÓN DE USUARIOS =====");
            System.out.println("1. Registrar Vendedor");
            System.out.println("2. Registrar Cliente");
            System.out.println("3. Mostrar Usuarios");
            System.out.println("4. Cargar desde CSV");
            System.out.println("5. Editar Usuario");
            System.out.println("6. Eliminar Usuario");
            System.out.println("7. Volver al menú anterior");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1": registrarVendedor(); break;
                case "2": registrarCliente(); break;
                case "3": mostrarUsuarios(); break;
                case "4": cargarDesdeCSV(); break;
                case "5": editarUsuario(); break;
                case "6": eliminarUsuario(); break;
                case "7": continuar = false; break;
                default: System.out.println("⚠️ Opción inválida."); break;
            }
        }
    }


    // =============================================================
    // 🔸 REGISTRAR VENDEDOR
    // =============================================================
    private void registrarVendedor() {
        System.out.println("\n=== NUEVO VENDEDOR ===");
        System.out.print("Código: ");
        String codigo = scanner.nextLine().trim();

        if (listaUsuarios.buscarUsuario(codigo) != null) {
            System.out.println("⚠️ Ya existe un usuario con ese código.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Género (M/F): ");
        String genero = scanner.nextLine().trim().toUpperCase();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        Vendedor vendedor = new Vendedor(codigo, nombre, genero, pass);
        listaUsuarios.agregarUsuario(vendedor);
        System.out.println("✅ Vendedor registrado correctamente.");
    }

    // =============================================================
    // 🔸 REGISTRAR CLIENTE
    // =============================================================
    private void registrarCliente() {
        System.out.println("\n=== NUEVO CLIENTE ===");
        System.out.print("Código: ");
        String codigo = scanner.nextLine().trim();

        if (listaUsuarios.buscarUsuario(codigo) != null) {
            System.out.println("⚠️ Ya existe un usuario con ese código.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Género (M/F): ");
        String genero = scanner.nextLine().trim().toUpperCase();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        Cliente cliente = new Cliente(codigo, nombre, genero, pass);
        listaUsuarios.agregarUsuario(cliente);
        System.out.println("✅ Cliente registrado correctamente.");
    }

    // =============================================================
    // 🔸 MOSTRAR USUARIOS
    // =============================================================
    private void mostrarUsuarios() {
        listaUsuarios.mostrarUsuarios();
    }

    // =============================================================
    // 🔸 CARGAR USUARIOS DESDE CSV
    // =============================================================
    private void cargarDesdeCSV() {
        System.out.println("\n=== CARGA DESDE ARCHIVO CSV ===");
        System.out.println("1. Cargar Vendedores");
        System.out.println("2. Cargar Clientes");
        System.out.print("Seleccione: ");
        String tipo = scanner.nextLine();

        String archivo = tipo.equals("1") ? "data/vendedores.csv" : "data/clientes.csv";
        File file = new File(archivo);

        if (!file.exists()) {
            System.out.println("❌ El archivo " + archivo + " no existe.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            int count = 0;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty() || linea.startsWith("#")) continue;

                String[] datos = linea.split(",");
                if (datos.length < 4) continue;

                String codigo = datos[0].trim();
                String nombre = datos[1].trim();
                String genero = datos[2].trim();
                String pass = datos[3].trim();

                if (listaUsuarios.buscarUsuario(codigo) != null) continue;

                if (tipo.equals("1")) {
                    listaUsuarios.agregarUsuario(new Vendedor(codigo, nombre, genero, pass));
                } else {
                    listaUsuarios.agregarUsuario(new Cliente(codigo, nombre, genero, pass));
                }
                count++;
            }
            System.out.println("✅ Se cargaron " + count + " usuarios desde el archivo.");
        } catch (IOException e) {
            System.out.println("❌ Error al leer archivo: " + e.getMessage());
        }

    }
    // =============================================================
// 🔸 EDITAR USUARIO
// =============================================================
    private void editarUsuario() {
        System.out.print("\nIngrese el código del usuario a editar: ");
        String codigo = scanner.nextLine().trim();

        Usuario u = listaUsuarios.buscarUsuario(codigo);
        if (u == null) {
            System.out.println("❌ No se encontró ningún usuario con ese código.");
            return;
        }

        System.out.println("Usuario encontrado: " + u.getNombre() + " (" + u.getGenero() + ")");
        System.out.print("Nuevo nombre (dejar vacío para no cambiar): ");
        String nuevoNombre = scanner.nextLine().trim();
        if (!nuevoNombre.isEmpty()) u.setNombre(nuevoNombre);

        System.out.print("Nuevo género (M/F, dejar vacío para no cambiar): ");
        String nuevoGenero = scanner.nextLine().trim().toUpperCase();
        if (!nuevoGenero.isEmpty()) u.setGenero(nuevoGenero);

        System.out.print("Nueva contraseña (dejar vacío para no cambiar): ");
        String nuevaPass = scanner.nextLine();
        if (!nuevaPass.isEmpty()) u.setPassword(nuevaPass);

        System.out.println("✅ Usuario actualizado correctamente.");
        guardarUsuariosCSV();
    }
    // =============================================================
// 🔸 ELIMINAR USUARIO
// =============================================================
    private void eliminarUsuario() {
        System.out.print("\nIngrese el código del usuario a eliminar: ");
        String codigo = scanner.nextLine().trim();

        if (listaUsuarios.eliminarUsuario(codigo)) {
            System.out.println("✅ Usuario eliminado correctamente.");
            guardarUsuariosCSV();
        } else {
            System.out.println("❌ No se encontró el usuario especificado.");
        }
    }

    // =============================================================
// 🔹 GUARDAR USUARIOS EN ARCHIVO CSV
// =============================================================
    private void guardarUsuariosCSV() {
        try {
            File fileVendedores = new File("data/vendedores.csv");
            File fileClientes = new File("data/clientes.csv");

            FileWriter fwVend = new FileWriter(fileVendedores);
            FileWriter fwCli = new FileWriter(fileClientes);

            for (int i = 0; i < listaUsuarios.getCantidad(); i++) {
                Usuario u = listaUsuarios.getUsuario(i);

                if (u instanceof Vendedor) {
                    fwVend.write(u.getCodigo() + "," + u.getNombre() + "," +
                            u.getGenero() + "," + u.getPassword() + "\n");
                } else if (u instanceof Cliente) {
                    fwCli.write(u.getCodigo() + "," + u.getNombre() + "," +
                            u.getGenero() + "," + u.getPassword() + "\n");
                }
            }

            fwVend.close();
            fwCli.close();

            System.out.println("💾 Usuarios guardados correctamente en archivos CSV.");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar usuarios: " + e.getMessage());
        }
    }

}

