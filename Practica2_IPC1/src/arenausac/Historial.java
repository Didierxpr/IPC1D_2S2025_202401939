package arenausac;

public class Historial {
    // =========================
    // Atributos
    // =========================
    private Batalla[] batallas;   // Vector estático de batallas
    private int contador;         // Controla cuántas batallas se han guardado

    // =========================
    // Constructor
    // =========================
    public Historial(int capacidad) {
        this.batallas = new Batalla[capacidad];  // ej: capacidad = 50
        this.contador = 0;
    }

    // =========================
    // Métodos principales
    // =========================

    // Agregar batalla al historial
    public void agregarBatalla(Batalla b) {
        if (contador < batallas.length) {
            batallas[contador++] = b;
        } else {
            System.out.println("⚠️ No se pueden registrar más batallas, el historial está lleno.");
        }
    }

    // Mostrar todas las batallas registradas
    public void mostrarHistorial() {
        System.out.println("\n=== HISTORIAL DE BATALLAS ===");
        if (contador == 0) {
            System.out.println("⚠️ No hay batallas registradas.");
            return;
        }
        for (int i = 0; i < contador; i++) {
            Batalla b = batallas[i];
            if (b != null) {
                System.out.println("Batalla #" + b.getIdBatalla() +
                        " | Fecha: " + b.getFechaHora() +
                        " | " + b.getJugador1().getNombre() +
                        " VS " + b.getJugador2().getNombre() +
                        " | Ganador: " + b.getGanador());
            }
        }
    }

    // Buscar batallas donde participó un personaje
    public void buscarPorPersonaje(String nombre) {
        System.out.println("\n=== BUSCAR BATALLAS DE: " + nombre + " ===");
        boolean encontrado = false;

        for (int i = 0; i < contador; i++) {
            Batalla b = batallas[i];
            if (b != null && (
                    b.getJugador1().getNombre().equalsIgnoreCase(nombre) ||
                            b.getJugador2().getNombre().equalsIgnoreCase(nombre)
            )) {
                System.out.println("Batalla #" + b.getIdBatalla() +
                        " | Fecha: " + b.getFechaHora() +
                        " | Ganador: " + b.getGanador());
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("⚠️ No se encontraron batallas para el personaje " + nombre);
        }
    }

    // Mostrar bitácora completa de una batalla por ID
    public void mostrarBitacoraBatalla(int idBatalla) {
        Batalla b = buscarPorId(idBatalla);
        if (b != null) {
            b.mostrarBitacora();
        } else {
            System.out.println("⚠️ No se encontró la batalla con ID " + idBatalla);
        }
    }

    // =========================
    // Utilidades opcionales
    // =========================

    public boolean estaLleno() {
        return contador >= batallas.length;
    }

    public boolean estaVacio() {
        return contador == 0;
    }

    public Batalla buscarPorId(int idBatalla) {
        for (int i = 0; i < contador; i++) {
            Batalla b = batallas[i];
            if (b != null && b.getIdBatalla() == idBatalla) {
                return b;
            }
        }
        return null;
    }

    public Batalla getBatallaEn(int idx) {
        if (idx < 0 || idx >= contador) return null;
        return batallas[idx];
    }

    /** Reinicia el historial (útil antes de cargar estado desde archivo). */
    public void limpiar() {
        for (int i = 0; i < contador; i++) batallas[i] = null;
        contador = 0;
    }

    // =========================
    // Getters
    // =========================
    public int getContador() { return contador; }
    public Batalla[] getBatallas() { return batallas; }
}

