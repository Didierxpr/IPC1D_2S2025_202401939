package vista.gui;

import javax.swing.*;
import java.awt.*;
import modelo.Sistema;

public class VentanaLogin extends JFrame {
    public VentanaLogin() {
        setTitle("Inicio de Sesión - Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setContentPane(new PanelLogin());
    }
}
