package arenausac;

import java.io.BufferedReader;  // lee archivo linea por linea
import java.io.FileReader;      // abrir archivo en modo lectura
import java.io.FileWriter;      // abrir archivo en modo escritura
import java.io.IOException;     // manejar errores de E/S
import java.io.PrintWriter;     // escribir texto fácilmente

public class ArenaUSAC {
    // =========================
    // Atributos
    // =========================
    private Personaje[] personajes;   // Vector estático de personajes
    private int contadorPersonajes;   // Cantidad actual de personajes
    private Historial historial;      // Historial de batallas
    private int nextId;               // IDs automáticos

    // =========================
    // Constructor
    // =========================
    public ArenaUSAC(int capacidadPersonajes, int capacidadBatallas) {
        this.personajes = new Personaje[capacidadPersonajes];
        this.contadorPersonajes = 0;
        this.historial = new Historial(capacidadBatallas);
        this.nextId = 1;
    }

    // Getter del historial
    public Historial getHistorial() {
        return historial;
    }

    // =========================
    // Gestión de Personajes
    // =========================
    public void agregarPersonaje(String nombre, String arma, int hp, int ataque,
                                 int velocidad, int agilidad, int defensa) {
        if (contadorPersonajes >= personajes.length) {
            System.out.println("⚠️ No se pueden agregar más personajes (límite alcanzado).");
            return;
        }

        // Validar duplicado por nombre
        for (int i = 0; i < contadorPersonajes; i++) {
            if (personajes[i].getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("⚠️ Ya existe un personaje con ese nombre.");
                return;
            }
        }

        Personaje nuevo = new Personaje(nextId, nombre, arma, hp, ataque, velocidad, agilidad, defensa);
        personajes[contadorPersonajes] = nuevo;
        contadorPersonajes++;
        nextId++;

        System.out.println("✅ Personaje agregado con éxito: " + nombre);
    }

    public void modificarPersonaje(int id, String arma, int hp, int ataque,
                                   int velocidad, int agilidad, int defensa) {
        Personaje p = buscarPorId(id);
        if (p == null) {
            System.out.println("⚠️ No se encontró el personaje con ID " + id);
            return;
        }

        p.setArma(arma);
        p.setHp(hp);
        p.setAtaque(ataque);
        p.setVelocidad(velocidad);
        p.setAgilidad(agilidad);
        p.setDefensa(defensa);

        System.out.println("✅ Personaje " + p.getNombre() + " modificado con éxito.");
    }

    public void eliminarPersonaje(String criterio) {
        int pos = -1;
        Personaje personajeAEliminar = buscar(criterio);

        if (personajeAEliminar == null) {
            System.out.println("⚠️ No se encontró personaje con el criterio: " + criterio);
            return;
        }

        // Buscar posición en el vector
        for (int i = 0; i < contadorPersonajes; i++) {
            if (personajes[i].getId() == personajeAEliminar.getId()) {
                pos = i;
                break;
            }
        }

        if (pos == -1) return;

        String nombre = personajeAEliminar.getNombre();

        // Reajustar vector
        for (int i = pos; i < contadorPersonajes - 1; i++) {
            personajes[i] = personajes[i + 1];
        }
        personajes[contadorPersonajes - 1] = null;
        contadorPersonajes--;

        System.out.println("🗑️ Personaje eliminado: " + nombre);
    }

    public void mostrarPersonajes() {
        System.out.println("\n=== LISTA DE PERSONAJES ===");
        if (contadorPersonajes == 0) {
            System.out.println("No hay personajes registrados.");
            return;
        }

        for (int i = 0; i < contadorPersonajes; i++) {
            Personaje p = personajes[i];
            System.out.println("ID: " + p.getId() +
                    " | Nombre: " + p.getNombre() +
                    " | Arma: " + p.getArma() +
                    " | HP: " + p.getHp() +
                    " | Ataque: " + p.getAtaque() +
                    " | Velocidad: " + p.getVelocidad() +
                    " | Agilidad: " + p.getAgilidad() +
                    " | Defensa: " + p.getDefensa() +
                    " | Vivo: " + (p.estaVivo() ? "Sí" : "No"));
        }
    }

    // =========================
    // Batallas (Consola)
    // =========================
    public void simularBatalla(int id1, int id2) {
        Personaje p1 = buscarPorId(id1);
        Personaje p2 = buscarPorId(id2);

        if (p1 == null || p2 == null) {
            System.out.println("⚠️ Uno o ambos personajes no existen.");
            return;
        }
        if (!p1.estaVivo() || !p2.estaVivo()) {
            System.out.println("⚠️ Ambos personajes deben estar vivos para pelear.");
            return;
        }
        if (p1.getId() == p2.getId()) {
            System.out.println("⚠️ Debe elegir personajes distintos.");
            return;
        }

        Batalla b = new Batalla(historial.getContador() + 1, p1, p2);
        b.iniciarBatalla();
        historial.agregarBatalla(b);
    }

    // =========================
    // Batallas (GUI) - Sobrecarga con listener
    // =========================
    public void simularBatalla(int id1, int id2, BitacoraListener listener) {
        Personaje p1 = buscarPorId(id1);
        Personaje p2 = buscarPorId(id2);

        if (p1 == null || p2 == null) {
            System.out.println("⚠️ Uno o ambos personajes no existen.");
            return;
        }
        if (!p1.estaVivo() || !p2.estaVivo()) {
            System.out.println("⚠️ Ambos personajes deben estar vivos para pelear.");
            return;
        }
        if (p1.getId() == p2.getId()) {
            System.out.println("⚠️ Debe elegir personajes distintos.");
            return;
        }

        Batalla b = new Batalla(historial.getContador() + 1, p1, p2);
        b.setListener(listener);   // 🔗 Conectar con GUI
        b.iniciarBatalla();
        historial.agregarBatalla(b);
    }

    // =========================
    // Métodos de búsqueda
    // =========================
    public Personaje buscarPorId(int id) {
        for (int i = 0; i < contadorPersonajes; i++) {
            if (personajes[i].getId() == id) {
                return personajes[i];
            }
        }
        return null;
    }

    public Personaje buscarPorNombre(String nombre) {
        for (int i = 0; i < contadorPersonajes; i++) {
            if (personajes[i].getNombre().equalsIgnoreCase(nombre)) {
                return personajes[i];
            }
        }
        return null;
    }

    // Método centralizado (ID o nombre)
    public Personaje buscar(String criterio) {
        try {
            int id = Integer.parseInt(criterio); // Si es número → buscar por ID
            return buscarPorId(id);
        } catch (NumberFormatException e) {
            return buscarPorNombre(criterio);    // Si no es número → buscar por nombre
        }
    }

    // =========================
    // Historial
    // =========================
    public void mostrarHistorial() {
        historial.mostrarHistorial();
    }

    public void buscarBatallasDePersonaje(String nombre) {
        historial.buscarPorPersonaje(nombre);
    }

    // =========================
    // Guardar estado en archivo
    // =========================
    public void guardarEstado(String archivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("=== PERSONAJES ===");
            for (int i = 0; i < contadorPersonajes; i++) {
                Personaje p = personajes[i];
                if (p != null) {
                    // Formato: id;nombre;arma;hp;ataque;defensa;agilidad;velocidad
                    pw.println(p.getId() + ";" + p.getNombre() + ";" + p.getArma() + ";" +
                            p.getHp() + ";" + p.getAtaque() + ";" + p.getDefensa() + ";" +
                            p.getAgilidad() + ";" + p.getVelocidad());
                }
            }

            pw.println("\n=== BATALLAS ===");
            Batalla[] batallas = historial.getBatallas();
            for (int i = 0; i < historial.getContador(); i++) {
                Batalla b = batallas[i];
                pw.println(b.getIdBatalla() + ";" + b.getFechaHora() + ";" +
                        b.getJugador1().getNombre() + ";" +
                        b.getJugador2().getNombre() + ";" +
                        b.getGanador());
            }

            System.out.println("✅ Estado guardado en " + archivo);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar estado: " + e.getMessage());
        }
    }

    // =========================
    // Cargar estado desde archivo
    // =========================
    public void cargarEstado(String archivo) {
        int personajesCargados = 0;
        int batallasCargadas = 0;

        // 🔄 Reiniciar estructuras (opcional pero recomendado)
        this.contadorPersonajes = 0;
        for (int i = 0; i < personajes.length; i++) personajes[i] = null;

        // Re-crear historial con misma capacidad
        int capacidadHistorial = this.historial.getBatallas().length;
        this.historial = new Historial(capacidadHistorial);

        int maxId = 0; // para recalcular nextId

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean leyendoPersonajes = false;
            boolean leyendoBatallas = false;

            while ((linea = br.readLine()) != null) {
                if (linea.contains("=== PERSONAJES ===")) {
                    leyendoPersonajes = true;
                    leyendoBatallas = false;
                    continue;
                } else if (linea.contains("=== BATALLAS ===")) {
                    leyendoBatallas = true;
                    leyendoPersonajes = false;
                    continue;
                }

                if (linea.trim().isEmpty()) continue;

                if (leyendoPersonajes) {
                    String[] datos = linea.split(";");
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    String arma = datos[2];
                    int hp = Integer.parseInt(datos[3]);
                    int ataque = Integer.parseInt(datos[4]);
                    int defensa = Integer.parseInt(datos[5]);
                    int agilidad = Integer.parseInt(datos[6]);
                    int velocidad = Integer.parseInt(datos[7]);

                    // ⚠️ Orden correcto según tu constructor usado en agregarPersonaje:
                    // new Personaje(id, nombre, arma, hp, ataque, velocidad, agilidad, defensa)
                    if (contadorPersonajes < personajes.length) {
                        personajes[contadorPersonajes] =
                                new Personaje(id, nombre, arma, hp, ataque, velocidad, agilidad, defensa);
                        contadorPersonajes++;
                        personajesCargados++;
                        if (id > maxId) maxId = id;
                    }
                }

                if (leyendoBatallas) {
                    String[] datos = linea.split(";");
                    int idBatalla = Integer.parseInt(datos[0]);
                    String fecha = datos[1];
                    String nombre1 = datos[2];
                    String nombre2 = datos[3];
                    String ganador = datos[4];

                    Personaje p1 = buscar(nombre1);
                    Personaje p2 = buscar(nombre2);

                    if (p1 != null && p2 != null) {
                        Batalla b = new Batalla(idBatalla, p1, p2);
                        // Puedes registrar marcas para distinguir batallas cargadas
                        b.registrarEvento("Batalla cargada desde archivo (" + fecha + ") - Ganador: " + ganador);
                        historial.agregarBatalla(b);
                        batallasCargadas++;
                    }
                }
            }

            // Ajustar nextId para futuros ingresos
            this.nextId = (maxId > 0 ? maxId + 1 : this.nextId);

            System.out.println("✅ Estado cargado desde " + archivo);
            System.out.println("📌 Personajes restaurados: " + personajesCargados);
            System.out.println("📌 Batallas restauradas: " + batallasCargadas);

        } catch (IOException e) {
            System.out.println("❌ Error al cargar estado: " + e.getMessage());
        }
    }

    // =========================
    // Getters de apoyo para la GUI
    // =========================
    public int getCantidadPersonajes() {
        return contadorPersonajes;
    }

    public Personaje getPersonajeEn(int idx) {
        if (idx < 0 || idx >= contadorPersonajes) return null;
        return personajes[idx];
    }

    public String[] getNombresPersonajes() {
        String[] nombres = new String[contadorPersonajes];
        for (int i = 0; i < contadorPersonajes; i++) {
            nombres[i] = personajes[i].getNombre();
        }
        return nombres;
    }
}

