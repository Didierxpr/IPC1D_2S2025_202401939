package vista.gui;

import javax.swing.*;
import java.awt.*;

public class PanelAcercaDe extends JPanel {

    public PanelAcercaDe() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // 🔹 Título
        JLabel lblTitulo = new JLabel("Información del Estudiante", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // 🔹 Contenido central
        JPanel panelDatos = new JPanel(new GridLayout(4, 1, 10, 10));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));
        panelDatos.setBackground(Color.WHITE);

        JLabel lblNombre = new JLabel("Nombre: Carlos Didiere Cabrera Rodríguez", SwingConstants.CENTER);
        JLabel lblCarnet = new JLabel("Carnet: 202401939", SwingConstants.CENTER);
        JLabel lblCurso = new JLabel("Curso: Introducción a la Programación y Computación 1 (IPC1)", SwingConstants.CENTER);
        JLabel lblSeccion = new JLabel("Universidad de San Carlos de Guatemala - USAC", SwingConstants.CENTER);

        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCarnet.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCurso.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSeccion.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        panelDatos.add(lblNombre);
        panelDatos.add(lblCarnet);
        panelDatos.add(lblCurso);
        panelDatos.add(lblSeccion);

        add(panelDatos, BorderLayout.CENTER);

        // 🔹 Pie de página
        JLabel lblFooter = new JLabel("© 2025 - Proyecto Final IPC2", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblFooter.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblFooter, BorderLayout.SOUTH);
    }
}

