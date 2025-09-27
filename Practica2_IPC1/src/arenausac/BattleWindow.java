package arenausac;

import javax.swing.*;
import java.awt.*;

public class BattleWindow extends JFrame implements BitacoraListener {
    private final JTextArea area;

    public BattleWindow(String titulo) {
        super(titulo);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 520);
        setLocationRelativeTo(null);

        area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(scroll, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void onEvento(String texto) {
        // Asegurar actualizaciones en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
            area.append(texto + "\n");
            area.setCaretPosition(area.getDocument().getLength());
        });
    }

    public void setSubtitulo(String subtitulo) {
        SwingUtilities.invokeLater(() -> setTitle(getTitle() + " - " + subtitulo));
    }
}

