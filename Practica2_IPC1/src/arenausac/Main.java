package arenausac;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Capacidad fija (vectores estáticos)
            ArenaUSAC arena = new ArenaUSAC(50, 50);
            MainWindow win = new MainWindow(arena);
            win.setVisible(true);
        });
    }
}

