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

    // Bitácora en vector estático
    private String[] bitacora;   // Máx. eventos
    private int contadorEventos; // Cantidad de eventos guardados

    private String fechaHora;
    private String ganador;

    // >>> NUEVO: callback para GUI
    private BitacoraListener listener;

    // =========================
    // Constructor
    // =========================
    public Batalla(int idBatalla, Personaje p1, Personaje p2) {
        this.idBatalla = idBatalla;
        this.jugador1 = p1;
        this.jugador2 = p2;
        this.bitacora = new String[100];  // Máximo 100 eventos por batalla
        this.contadorEventos = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.fechaHora = LocalDateTime.now().format(formatter);
        this.ganador = "Indefinido";
    }

    // =========================
    // Listener (GUI)
    // =========================
    public void setListener(BitacoraListener listener) {
        this.listener = listener;
    }

    private void notificarGUI(String evento) {
        if (listener != null) listener.onEvento(evento);
    }

    // =========================
    // Métodos principales
    // =========================
    public void iniciarBatalla() {
        registrarEvento("\n--- Iniciando Batalla #" + idBatalla + " ---");
        registrarEvento("Fecha/Hora: " + fechaHora);
        registrarEvento("Participantes: " + jugador1.getNombre() + " VS " + jugador2.getNombre());

        // Asignar oponentes
        jugador1.setOponente(jugador2);
        jugador2.setOponente(jugador1);

        // >>> Conectar también el listener a los personajes para que envíen sus ataques/fallos a la GUI
        jugador1.setListener(listener);
        jugador2.setListener(listener);

        // Crear hilos (los personajes implementan Runnable)
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
            registrarEvento("¡Error! Hilo interrumpido: " + e.getMessage());
        }

        // Definir ganador
        if (jugador1.estaVivo() && !jugador2.estaVivo()) {
            ganador = jugador1.getNombre();
        } else if (jugador2.estaVivo() && !jugador1.estaVivo()) {
            ganador = jugador2.getNombre();
        } else if (!jugador1.estaVivo() && !jugador2.estaVivo()) {
            ganador = "Empate (ambos cayeron)";
        } else {
            // Respaldo por si ambos siguen con HP > 0 (carrera o empate técnico): decide por HP
            ganador = (jugador1.getHp() >= jugador2.getHp()) ? jugador1.getNombre() : jugador2.getNombre();
        }

        registrarEvento("Resultado final: " + ganador);
        registrarEvento("--- Fin de Batalla ---\n");
    }

    // =========================
    // Bitácora
    // =========================
    public void registrarEvento(String evento) {
        // Mostrar en consola
        System.out.println(evento);

        // Guardar en vector si hay espacio
        if (contadorEventos < bitacora.length) {
            bitacora[contadorEventos++] = evento;
        }

        // Notificar a la ventana (GUI)
        notificarGUI(evento);
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
