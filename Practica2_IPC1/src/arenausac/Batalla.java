package arenausac;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Batalla {
    // =========================
    // Atributos
    // =========================
    private int idBatalla;
    private Personaje jugador1;
    private Personaje jugador2;
    private String[] bitacora;   // Vector estático de eventos
    private int contadorEventos; // Para controlar posiciones en bitácora
    private String fechaHora;
    private String ganador;

    // =========================
    // Constructor
    // =========================
    public Batalla(int idBatalla, Personaje p1, Personaje p2) {
        this.idBatalla = idBatalla;
        this.jugador1 = p1;
        this.jugador2 = p2;
        this.bitacora = new String[100];  // Máximo 100 eventos por batalla
        this.contadorEventos = 0;

        // Fecha y hora actuales
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.fechaHora = LocalDateTime.now().format(formatter);

        this.ganador = "Indefinido";
    }

    // =========================
    // Métodos principales
    // =========================

    public void iniciarBatalla() {
        System.out.println("\n--- Iniciando Batalla #" + idBatalla + " ---");
        System.out.println(jugador1.getNombre() + " VS " + jugador2.getNombre());
        registrarEvento("Batalla iniciada: " + jugador1.getNombre() + " VS " + jugador2.getNombre());

        // Asignar oponentes
        jugador1.setOponente(jugador2);
        jugador2.setOponente(jugador1);

        // Crear hilos
        Thread t1 = new Thread(jugador1);
        Thread t2 = new Thread(jugador2);

        // Iniciar hilos
        t1.start();
        t2.start();

        // Esperar a que ambos terminen
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Definir ganador
        if (jugador1.estaVivo()) {
            ganador = jugador1.getNombre();
        } else if (jugador2.estaVivo()) {
            ganador = jugador2.getNombre();
        } else {
            ganador = "Empate (ambos cayeron)";
        }

        registrarEvento("Ganador: " + ganador);
        System.out.println("Resultado final: " + ganador);
        System.out.println("--- Fin de Batalla ---\n");
    }

    // =========================
    // Manejo de bitácora
    // =========================
    public void registrarEvento(String evento) {
        if (contadorEventos < bitacora.length) {
            bitacora[contadorEventos] = evento;
            contadorEventos++;
        }
    }

    public void mostrarBitacora() {
        System.out.println("\nBitácora de la Batalla #" + idBatalla);
        for (int i = 0; i < contadorEventos; i++) {
            System.out.println((i + 1) + ". " + bitacora[i]);
        }
    }

    // =========================
    // Getters
    // =========================
    public int getIdBatalla() { return idBatalla; }
    public String getFechaHora() { return fechaHora; }
    public String getGanador() { return ganador; }
    public Personaje getJugador1() { return jugador1; }
    public Personaje getJugador2() { return jugador2; }
    public String[] getBitacora() { return bitacora; }
    public int getContadorEventos() { return contadorEventos; }
}
