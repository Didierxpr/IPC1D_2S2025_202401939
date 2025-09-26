package ArenaUSAC;

import java.util.Scanner;

public class arenausac {
    // =========================
    // Atributos
    // =========================
    private Personaje[] personajes;  // Vector estático de personajes
    private int contadorPersonajes;  // Controla cuántos personajes activos hay
    private Historial historial;     // Registro de batallas
    private int nextId;              // Para asignar IDs únicos

    // =========================
    // Constructor
    // =========================
    public arenausac(int capacidadPersonajes, int capacidadBatallas) {
        this.personajes = new Personaje[capacidadPersonajes];
        this.contadorPersonajes = 0;
        this.historial = new Historial(capacidadBatallas);
        this.nextId = 1; // IDs empiezan en 1
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

        // Validar duplicados por nombre (ignorar mayúsculas/minúsculas)
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

    public void modificarPersonaje(int id, String arma, int hp, int ataque, int velocidad, int agilidad, int defensa) {
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

    // Eliminar personaje por ID o nombre
    public void eliminarPersonaje(String criterio) {
        int pos = -1;
        Personaje personajeAEliminar = null;

        // Verificar si el criterio es un número (ID) o texto (nombre)
        try {
            int id = Integer.parseInt(criterio); // intenta convertir a número
            for (int i = 0; i < contadorPersonajes; i++) {
                if (personajes[i].getId() == id) {
                    pos = i;
                    personajeAEliminar = personajes[i];
                    break;
                }
            }
        } catch (NumberFormatException e) {
            // Si no es número, se busca por nombre
            for (int i = 0; i < contadorPersonajes; i++) {
                if (personajes[i].getNombre().equalsIgnoreCase(criterio)) {
                    pos = i;
                    personajeAEliminar = personajes[i];
                    break;
                }
            }
        }

        if (pos == -1 || personajeAEliminar == null) {
            System.out.println("⚠️ No se encontró personaje con el criterio: " + criterio);
            return;
        }

        String nombre = personajeAEliminar.getNombre();

        // Reajustar el vector para eliminar
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
    // Batallas
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

        Batalla b = new Batalla(historial.getContador() + 1, p1, p2);
        b.iniciarBatalla();
        historial.agregarBatalla(b);
    }

    // =========================
    // Buscar
    // =========================
    public Personaje buscarPorId(int id) {
        for (int i = 0; i < contadorPersonajes; i++) {
            if (personajes[i].getId() == id) {
                return personajes[i];
            }
        }
        return null;
    }

    public void buscarPorNombre(String nombre) {
        boolean encontrado = false;
        for (int i = 0; i < contadorPersonajes; i++) {
            if (personajes[i].getNombre().equalsIgnoreCase(nombre)) {
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
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("⚠️ No se encontró un personaje con nombre " + nombre);
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
}

