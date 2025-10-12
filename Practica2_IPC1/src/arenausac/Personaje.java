package arenausac;

public class Personaje implements Runnable {
    // =========================
    // Atributos
    // =========================
    private int id;
    private String nombre;
    private String arma;

    private int hp;         // 100–500
    private int ataque;     // 10–100
    private int velocidad;  // 1–10 (turnos por segundo = 1000/velocidad)
    private int agilidad;   // 1–10 (prob. esquivar ~ agilidad/10)
    private int defensa;    // 1–50

    // En combate
    private Personaje oponente;

    // Callback para GUI/bitácora
    private BitacoraListener listener;

    // =========================
    // Constructores
    // =========================
    public Personaje(int id, String nombre, String arma, int hp, int ataque, int velocidad, int agilidad, int defensa) {
        this.id = id;
        this.nombre = nombre;
        this.arma = arma;
        this.hp = hp;
        this.ataque = ataque;
        this.velocidad = velocidad;
        this.agilidad = agilidad;
        this.defensa = defensa;
    }

    // =========================
    // Integración con GUI/Bitácora
    // =========================
    public void setListener(BitacoraListener listener) {
        this.listener = listener;
    }

    private void emitir(String evento) {
        // Consola
        System.out.println(evento);
        // GUI (si existe)
        if (listener != null) listener.onEvento(evento);
    }

    // =========================
    // Lógica de combate (hilo)
    // =========================
    public void setOponente(Personaje oponente) {
        this.oponente = oponente;
    }

    @Override
    public void run() {
        // No hacer nada si no hay oponente
        if (oponente == null) return;

        while (estaVivo() && oponente.estaVivo()) {
            // Ritmo del turno según velocidad
            try {
                int delay = Math.max(1, 1000 / Math.max(1, this.velocidad));
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                emitir(nombre + " fue interrumpido: " + e.getMessage());
                return;
            }

            // Verificar estado otra vez (por carreras)
            if (!estaVivo() || !oponente.estaVivo()) break;

            // Cálculo de esquiva
            double probEsquiva = Math.min(1.0, Math.max(0.0, oponente.getAgilidad() / 10.0));
            if (Math.random() < probEsquiva) {
                emitir(nombre + " ataca a " + oponente.getNombre() + " - Falló (esquiva)");
                continue;
            }

            // Daño neto
            int danio = this.ataque - oponente.getDefensa();
            if (danio < 0) danio = 0;

            // Aplicar daño de forma sincronizada sobre el oponente
            synchronized (oponente) {
                int hpActual = oponente.getHp();
                int nuevoHP = hpActual - danio;
                oponente.setHp(nuevoHP);
                emitir(nombre + " ataca a " + oponente.getNombre()
                        + " con " + arma + " - Daño: " + danio
                        + " | HP restante de " + oponente.getNombre() + ": " + oponente.getHp());
            }
        }

        // Al salir del bucle, uno (o ambos) ya no están vivos
        if (!estaVivo() && !oponente.estaVivo()) {
            emitir("Ambos cayeron: " + nombre + " y " + oponente.getNombre());
        } else if (!oponente.estaVivo()) {
            emitir(oponente.getNombre() + " ha caído. " + nombre + " sobrevive.");
        } else if (!estaVivo()) {
            emitir(nombre + " ha caído. " + oponente.getNombre() + " sobrevive.");
        }
    }

    // =========================
    // Utilidades de estado
    // =========================
    public boolean estaVivo() {
        return hp > 0;
    }

    // =========================
    // Getters / Setters
    // =========================
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getArma() { return arma; }
    public int getHp() { return hp; }
    public int getAtaque() { return ataque; }
    public int getVelocidad() { return velocidad; }
    public int getAgilidad() { return agilidad; }
    public int getDefensa() { return defensa; }
    public Personaje getOponente() { return oponente; }

    public void setArma(String arma) { this.arma = arma; }
    public void setHp(int hp) { this.hp = hp; }
    public void setAtaque(int ataque) { this.ataque = ataque; }
    public void setVelocidad(int velocidad) { this.velocidad = velocidad; }
    public void setAgilidad(int agilidad) { this.agilidad = agilidad; }
    public void setDefensa(int defensa) { this.defensa = defensa; }


}
