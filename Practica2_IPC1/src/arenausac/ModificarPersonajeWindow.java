package arenausac;

import javax.swing.*;
import java.awt.*;

public class ModificarPersonajeWindow extends JFrame {
    // Campos de texto
    private JTextField txtArma, txtHp, txtAtaque, txtVelocidad, txtAgilidad, txtDefensa;
    private JButton btnGuardar;
    private Personaje personaje;
    private ArenaUSAC arena;

    // ===== Límites máximos de atributos =====
    private static final int MAX_HP = 500;
    private static final int MAX_ATAQUE = 100;
    private static final int MAX_VELOCIDAD = 10;
    private static final int MAX_AGILIDAD = 10;
    private static final int MAX_DEFENSA = 50;

    public ModificarPersonajeWindow(ArenaUSAC arena, Personaje personaje) {
        this.arena = arena;
        this.personaje = personaje;

        setTitle("Modificar Personaje - ID: " + personaje.getId() + " | " + personaje.getNombre());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));

        panel.add(new JLabel("Arma:"));
        txtArma = new JTextField(personaje.getArma());
        panel.add(txtArma);

        panel.add(new JLabel("HP:"));
        txtHp = new JTextField(String.valueOf(personaje.getHp()));
        panel.add(txtHp);

        panel.add(new JLabel("Ataque:"));
        txtAtaque = new JTextField(String.valueOf(personaje.getAtaque()));
        panel.add(txtAtaque);

        panel.add(new JLabel("Velocidad:"));
        txtVelocidad = new JTextField(String.valueOf(personaje.getVelocidad()));
        panel.add(txtVelocidad);

        panel.add(new JLabel("Agilidad:"));
        txtAgilidad = new JTextField(String.valueOf(personaje.getAgilidad()));
        panel.add(txtAgilidad);

        panel.add(new JLabel("Defensa:"));
        txtDefensa = new JTextField(String.valueOf(personaje.getDefensa()));
        panel.add(txtDefensa);

        btnGuardar = new JButton("Guardar cambios");
        btnGuardar.addActionListener(e -> guardarCambios());
        panel.add(btnGuardar);

        add(panel);
    }

    private void guardarCambios() {
        try {
            String arma = txtArma.getText();
            int hp = Integer.parseInt(txtHp.getText());
            int ataque = Integer.parseInt(txtAtaque.getText());
            int velocidad = Integer.parseInt(txtVelocidad.getText());
            int agilidad = Integer.parseInt(txtAgilidad.getText());
            int defensa = Integer.parseInt(txtDefensa.getText());

            // ===== Validaciones de rangos =====
            if (hp <= 0 || hp > MAX_HP) {
                JOptionPane.showMessageDialog(this, "⚠️ El HP debe estar entre 1 y " + MAX_HP);
                return;
            }
            if (ataque <= 0 || ataque > MAX_ATAQUE) {
                JOptionPane.showMessageDialog(this, "⚠️ El Ataque debe estar entre 1 y " + MAX_ATAQUE);
                return;
            }
            if (velocidad <= 0 || velocidad > MAX_VELOCIDAD) {
                JOptionPane.showMessageDialog(this, "⚠️ La Velocidad debe estar entre 1 y " + MAX_VELOCIDAD);
                return;
            }
            if (agilidad <= 0 || agilidad > MAX_AGILIDAD) {
                JOptionPane.showMessageDialog(this, "⚠️ La Agilidad debe estar entre 1 y " + MAX_AGILIDAD);
                return;
            }
            if (defensa <= 0 || defensa > MAX_DEFENSA) {
                JOptionPane.showMessageDialog(this, "⚠️ La Defensa debe estar entre 1 y " + MAX_DEFENSA);
                return;
            }

            // ===== Guardar cambios =====
            arena.modificarPersonaje(personaje.getId(), arma, hp, ataque, velocidad, agilidad, defensa);

            JOptionPane.showMessageDialog(this, "✅ Personaje modificado con éxito.");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingresa valores numéricos válidos en las estadísticas.");
        }
    }
}

