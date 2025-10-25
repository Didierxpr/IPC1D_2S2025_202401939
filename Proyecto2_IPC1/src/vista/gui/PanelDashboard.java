package vista.gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import modelo.Sistema;

public class PanelDashboard extends JPanel {

    private final JLabel lblReloj = new JLabel("00:00:00", SwingConstants.CENTER);
    private final JLabel lblM1 = new JLabel("Usuarios: 0", SwingConstants.CENTER);
    private final JLabel lblM2 = new JLabel("Pedidos: 0", SwingConstants.CENTER);
    private final JLabel lblM3 = new JLabel("Total ventas: Q0.00", SwingConstants.CENTER);

    // Mantén referencias a los timers
    private javax.swing.Timer tReloj, tUsuarios, tPedidos, tVentas;

    public PanelDashboard() {
        setLayout(new GridLayout(4, 1, 6, 6));
        setBorder(BorderFactory.createTitledBorder("⏱️ Monitor en vivo"));
        setBackground(new Color(240, 245, 255));
        setPreferredSize(new Dimension(260, 180)); // visible en un lateral

        Font fBig = new Font("Segoe UI", Font.BOLD, 18);
        Font f = new Font("Segoe UI", Font.PLAIN, 15);

        lblReloj.setFont(fBig);
        lblM1.setFont(f);
        lblM2.setFont(f);
        lblM3.setFont(f);

        add(lblReloj);
        add(lblM1);
        add(lblM2);
        add(lblM3);
    }

    // Se llama cuando el panel entra en el árbol de componentes (ya se “ve”)
    @Override
    public void addNotify() {
        super.addNotify();
        iniciarTimers();
    }

    // Se llama cuando el panel se quita del árbol (evita timers zombies)
    @Override
    public void removeNotify() {
        detenerTimers();
        super.removeNotify();
    }

    private void iniciarTimers() {
        if (tReloj == null) {
            tReloj = new javax.swing.Timer(1000, e ->
                    lblReloj.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        }
        if (tUsuarios == null) {
            tUsuarios = new javax.swing.Timer(10_000, e -> {
                int activos = Sistema.getListaUsuarios().getCantidad();
                lblM1.setText("Usuarios: " + activos);
            });
        }
        if (tPedidos == null) {
            tPedidos = new javax.swing.Timer(8_000, e -> {
                int pedidos = Sistema.getListaPedidos().getCantidad();
                lblM2.setText("Pedidos: " + pedidos);
            });
        }
        if (tVentas == null) {
            tVentas = new javax.swing.Timer(15_000, e -> {
                double total = 0;
                var lista = Sistema.getListaPedidos();
                for (int i = 0; i < lista.getCantidad(); i++) {
                    var ped = lista.getPedidos()[i];
                    if (ped != null) total += ped.getTotal();
                }
                lblM3.setText("Total ventas: Q" + String.format("%.2f", total));
            });
        }

        tReloj.start();
        tUsuarios.start();
        tPedidos.start();
        tVentas.start();
    }

    private void detenerTimers() {
        if (tReloj != null) tReloj.stop();
        if (tUsuarios != null) tUsuarios.stop();
        if (tPedidos != null) tPedidos.stop();
        if (tVentas != null) tVentas.stop();
    }
}


