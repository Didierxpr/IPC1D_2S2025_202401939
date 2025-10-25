package vista.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.Sistema;
import modelo.bitacora.Bitacora;
import modelo.bitacora.RegistroBitacora;

public class PanelBitacora extends JPanel {

    private final DefaultTableModel modeloTabla;
    private final JTable tabla;
    private final JLabel lblTotalEventos;

    public PanelBitacora() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // =====================================================
        // 🔹 PANEL SUPERIOR: BOTONES DE ACCIÓN
        // =====================================================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JButton btnActualizar = new JButton("Actualizar Bitácora");
        JButton btnLimpiar = new JButton("Limpiar Bitácora");

        btnActualizar.addActionListener(e -> cargarEventos());
        btnLimpiar.addActionListener(e -> limpiarBitacora());

        panelBotones.add(btnActualizar);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.NORTH);

        // =====================================================
        // 🔹 PANEL CENTRAL: TABLA DE EVENTOS
        // =====================================================
        modeloTabla = new DefaultTableModel(new Object[]{"Fecha", "Hora", "Tipo", "Descripción"}, 0);
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Eventos del Sistema"));
        add(scroll, BorderLayout.CENTER);

        // =====================================================
        // 🔹 PANEL INFERIOR: TOTAL DE EVENTOS
        // =====================================================
        lblTotalEventos = new JLabel("Eventos registrados: 0", SwingConstants.CENTER);
        lblTotalEventos.setFont(new Font("Segoe UI", Font.BOLD, 13));
        add(lblTotalEventos, BorderLayout.SOUTH);

        // Cargar al iniciar
        cargarEventos();
    }

    // =====================================================
    // 🔸 Cargar eventos de la bitácora
    // =====================================================
    private void cargarEventos() {
        // cantidad como variable local
        int cantidad = Sistema.getRegistroBitacora().getCantidad();
        modelo.bitacora.Bitacora[] eventos = Sistema.getRegistroBitacora().getEventos();

        // limpiar tabla
        modeloTabla.setRowCount(0);

        for (int i = 0; i < cantidad; i++) {
            modelo.bitacora.Bitacora e = eventos[i];
            if (e != null) {
                modeloTabla.addRow(new Object[]{
                        e.getFecha(),
                        e.getHora(),
                        e.getTipo(),
                        e.getDescripcion()
                });
            }
        }

        lblTotalEventos.setText("Eventos registrados: " + cantidad);
    }

    // =====================================================
    // 🔸 Limpiar toda la bitácora
    // =====================================================
    private void limpiarBitacora() {
        int opcion = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar todos los registros de la bitácora?",
                "Confirmar limpieza",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (opcion == javax.swing.JOptionPane.YES_OPTION) {
            Sistema.getRegistroBitacora().limpiar();
            cargarEventos();
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "🧹 Bitácora limpiada correctamente.",
                    "Limpieza exitosa",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

}
