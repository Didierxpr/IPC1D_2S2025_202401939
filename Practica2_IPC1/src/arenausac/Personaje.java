package arenausac;

import java.util.Random;

public class Personaje implements Runnable {
    // =========================
    // Atributos
    // =========================
    private int id;
    private String nombre;
    private String arma;
    private int hp;
    private int ataque;
    private int velocidad;
    private int agilidad;
    private int defensa;
    private boolean vivo;

    // Referencia al oponente (se asigna desde la batalla)
    private Personaje oponente;

    // Generador de números aleatorios para esquivar
    private Random random = new Random();

    // =========================
    // Constructor
    // =========================
    public Personaje(int id, String nombre, String arma,
                     int hp, int ataque, int velocidad, int agilidad, int defensa) {
        this.id = id;
        this.nombre = nombre;
        this.arma = arma;
        this.hp = validarRango(hp, 100, 500);
        this.ataque = validarRango(ataque, 10, 100);
        this.velocidad = validarRango(velocidad, 1, 10);
        this.agilidad = validarRango(agilidad, 1, 10);
        this.defensa = validarRango(defensa, 1, 50);
        this.vivo = true;
    }

    // =========================
    // Métodos principales
    // =========================

    public void setOponente(Personaje oponente) {
        this.oponente = oponente;
    }

    @Override
    public void run() {
        while (this.estaVivo() && oponente != null && oponente.estaVivo()) {
            atacar();

            try {
                // La velocidad controla el tiempo de espera (más veloz = ataca más seguido)
                Thread.sleep(1000 / this.velocidad);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!this.estaVivo()) {
            System.out.println(this.nombre + " ha caído en batalla.");
        }
    }

    // Simulación de un ataque
    private void atacar() {
        if (oponente == null || !oponente.estaVivo()) {
            return;
        }

        // Probabilidad de esquivar
        int chance = random.nextInt(10) + 1; // número entre 1 y 10
        if (chance <= oponente.getAgilidad()) {
            System.out.println(this.nombre + " ataca a " + oponente.getNombre() +
                    " pero " + oponente.getNombre() + " esquivó el ataque.");
            return;
        }

        // Cálculo del daño con defensa
        int danoReal = this.ataque - oponente.getDefensa();
        if (danoReal < 0) {
            danoReal = 0;
        }

        oponente.recibirAtaque(danoReal);

        System.out.println(this.nombre + " ataca a " + oponente.getNombre() +
                " causando " + danoReal + " de daño. " +
                "HP restante de " + oponente.getNombre() + ": " + oponente.getHp());
    }

    // Cuando recibe un ataque
    public void recibirAtaque(int dano) {
        this.hp -= dano;
        if (this.hp <= 0) {
            this.hp = 0;
            this.vivo = false;
        }
    }

    // =========================
    // Utilidades
    // =========================
    private int validarRango(int valor, int min, int max) {
        if (valor < min) return min;
        if (valor > max) return max;
        return valor;
    }

    public boolean estaVivo() {
        return vivo;
    }

    // =========================
    // Getters
    // =========================
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getArma() { return arma; }
    public int getHp() { return hp; }
    public int getAtaque() { return ataque; }
    public int getVelocidad() { return velocidad; }
    public int getAgilidad() { return agilidad; }
    public int getDefensa() { return defensa; }

    // =========================
    // Setters controlados
    // =========================
    public void setArma(String arma) { this.arma = arma; }
    public void setHp(int hp) { this.hp = validarRango(hp, 100, 500); }
    public void setAtaque(int ataque) { this.ataque = validarRango(ataque, 10, 100); }
    public void setVelocidad(int velocidad) { this.velocidad = validarRango(velocidad, 1, 10); }
    public void setAgilidad(int agilidad) { this.agilidad = validarRango(agilidad, 1, 10); }
    public void setDefensa(int defensa) { this.defensa = validarRango(defensa, 1, 50); }
}

