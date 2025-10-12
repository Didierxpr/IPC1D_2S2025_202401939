package arenausac;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class MainWindow extends JFrame {

    private final ArenaUSAC arena;

    // Componentes compartidos
    private JTable tablaPersonajes;
    private JTable tablaHistorial;
    private JComboBox<String> cbP1;
    private JComboBox<String> cbP2;

    public MainWindow(ArenaUSAC arena) {
        super("Arena USAC - Práctica 2 (GUI)");
        this.arena = arena;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Personajes", buildPanelPersonajes());
        tabs.addTab("Batalla", buildPanelBatalla());
        tabs.addTab("Historial", buildPanelHistorial());
        tabs.addTab("Guardar/Cargar", buildPanelIO());
        tabs.addTab("Datos", buildPanelDatos());

        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);

        // Cargar datos iniciales en tablas/combos
        refrescarTablaPersonajes();
        refrescarHistorial();
        refrescarCombosBatalla();
    }

    /* =========================
       Pestaña: Personajes
       ========================= */
    private JPanel buildPanelPersonajes() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Tabla
        tablaPersonajes = new JTable();
        JScrollPane spTabla = new JScrollPane(tablaPersonajes);
        root.add(spTabla, BorderLayout.CENTER);

        // Panel de formularios (Agregar/Modificar/Eliminar/Buscar)
        JPanel form = new JPanel(new GridLayout(0, 4, 8, 8));

        JTextField tfNombre = new JTextField();
        JTextField tfArma = new JTextField();
        JSpinner spHP = new JSpinner(new SpinnerNumberModel(100, 100, 500, 1));
        JSpinner spAtaque = new JSpinner(new SpinnerNumberModel(10, 10, 100, 1));
        JSpinner spDefensa = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        JSpinner spAgilidad = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        JSpinner spVelocidad = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        form.add(new JLabel("Nombre:"));
        form.add(tfNombre);
        form.add(new JLabel("Arma:"));
        form.add(tfArma);
        form.add(new JLabel("HP (100-500):"));
        form.add(spHP);
        form.add(new JLabel("Ataque (10-100):"));
        form.add(spAtaque);
        form.add(new JLabel("Defensa (1-50):"));
        form.add(spDefensa);
        form.add(new JLabel("Agilidad (1-10):"));
        form.add(spAgilidad);
        form.add(new JLabel("Velocidad (1-10):"));
        form.add(spVelocidad);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnActualizarTabla = new JButton("Actualizar");
        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));
        JButton btnDatos = new  JButton("Datos");
        btnDatos.addActionListener(e -> {
            System.out.println("Carlos Didiere Cabrera Rodriguez");
        });

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        acciones.add(btnAgregar);
        acciones.add(btnModificar);
        acciones.add(btnEliminar);
        acciones.add(btnBuscar);
        acciones.add(btnActualizarTabla);
        acciones.add(btnSalir);
        acciones.add(btnDatos);

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(form, BorderLayout.CENTER);
        sur.add(acciones, BorderLayout.SOUTH);

        root.add(sur, BorderLayout.SOUTH);

        // Listeners
        btnAgregar.addActionListener(e -> {
            String nombre = tfNombre.getText().trim();
            String arma = tfArma.getText().trim();
            int hp = (Integer) spHP.getValue();
            int atk = (Integer) spAtaque.getValue();
            int def = (Integer) spDefensa.getValue();
            int agi = (Integer) spAgilidad.getValue();
            int vel = (Integer) spVelocidad.getValue();

            if (nombre.isEmpty() || arma.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y arma son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Validar rangos
            if (hp < 100 || hp > 500) {
                JOptionPane.showMessageDialog(null,
                        "El HP debe estar entre 100 y 500.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (atk < 10 || atk > 100) {
                JOptionPane.showMessageDialog(null,
                        "El Ataque debe estar entre 10 y 100.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (def < 1 || def > 50) {
                JOptionPane.showMessageDialog(null,
                        "La Defensa debe estar entre 1 y 50.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (agi < 1 || agi > 10) {
                JOptionPane.showMessageDialog(null,
                        "La Agilidad debe estar entre 1 y 10.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (vel < 1 || vel > 10) {
                JOptionPane.showMessageDialog(null,
                        "La Velocidad debe estar entre 1 y 10.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            arena.agregarPersonaje(nombre, arma, hp, atk, vel, agi, def);
            refrescarTablaPersonajes();
            refrescarCombosBatalla();
        });

        btnModificar.addActionListener(e -> {
            String criterio = JOptionPane.showInputDialog(this, "Ingrese ID o nombre del personaje a modificar:");
            if (criterio != null && !criterio.isEmpty()) {
                Personaje p = arena.buscar(criterio); // tu método ya acepta ID o nombre
                if (p != null) {
                    new ModificarPersonajeWindow(arena, p).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ No se encontró ningún personaje con ese criterio.");
                }
            }
        });



        btnEliminar.addActionListener(e -> {
            String criterio = JOptionPane.showInputDialog(this, "ID o Nombre del personaje a eliminar:");
            if (criterio == null || criterio.isEmpty()) return;
            Personaje p = arena.buscar(criterio);
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Personaje no encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int ans = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar a " + p.getNombre() + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {
                arena.eliminarPersonaje(criterio);
                refrescarTablaPersonajes();
                refrescarCombosBatalla();
            }
        });

        btnBuscar.addActionListener(e -> {
            String criterio = JOptionPane.showInputDialog(this, "ID o Nombre a buscar:");
            if (criterio == null || criterio.isEmpty()) return;
            Personaje p = arena.buscar(criterio);
            if (p == null) {
                JOptionPane.showMessageDialog(this, "No encontrado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "ID: " + p.getId() + "\nNombre: " + p.getNombre() +
                                "\nArma: " + p.getArma() + "\nHP: " + p.getHp() +
                                "\nAtaque: " + p.getAtaque() + "\nDefensa: " + p.getDefensa() +
                                "\nAgilidad: " + p.getAgilidad() + "\nVelocidad: " + p.getVelocidad() +
                                "\nVivo: " + (p.estaVivo() ? "Sí" : "No"),
                        "Resultado", JOptionPane.PLAIN_MESSAGE);
            }
        });

        btnActualizarTabla.addActionListener(e -> {
            refrescarTablaPersonajes();
            refrescarCombosBatalla();
        });

        return root;
    }

    /* =========================
       Pestaña: Batalla
       ========================= */
    private JPanel buildPanelBatalla() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        cbP1 = new JComboBox<>();
        cbP2 = new JComboBox<>();
        form.add(new JLabel("Personaje 1:"));
        form.add(cbP1);
        form.add(new JLabel("Personaje 2:"));
        form.add(cbP2);

        JButton btnBatalla = new JButton("Iniciar Batalla (GUI)");
        btnBatalla.addActionListener(e -> iniciarBatallaGUI());

        root.add(form, BorderLayout.NORTH);
        root.add(btnBatalla, BorderLayout.SOUTH);
        return root;
    }

    private void iniciarBatallaGUI() {
        String nombre1 = (String) cbP1.getSelectedItem();
        String nombre2 = (String) cbP2.getSelectedItem();
        if (nombre1 == null || nombre2 == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar dos personajes.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (nombre1.equalsIgnoreCase(nombre2)) {
            JOptionPane.showMessageDialog(this, "Seleccione personajes distintos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Personaje p1 = arena.buscar(nombre1);
        Personaje p2 = arena.buscar(nombre2);
        if (p1 == null || p2 == null || !p1.estaVivo() || !p2.estaVivo()) {
            JOptionPane.showMessageDialog(this, "Ambos deben existir y estar vivos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        BattleWindow ventana = new BattleWindow("Batalla " + p1.getNombre() + " vs " + p2.getNombre());
        ventana.setSubtitulo("En curso");

        // Usar tu flujo existente
        Batalla b = new Batalla(arena.getHistorial().getContador() + 1, p1, p2);
        b.setListener(ventana);
        b.iniciarBatalla();
        arena.getHistorial().agregarBatalla(b);

        refrescarHistorial();
        refrescarTablaPersonajes();
    }

    /* =========================
       Pestaña: Historial
       ========================= */
    private JPanel buildPanelHistorial() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        tablaHistorial = new JTable();
        JScrollPane sp = new JScrollPane(tablaHistorial);
        root.add(sp, BorderLayout.CENTER);

        JButton btnBitacora = new JButton("Ver Bitácora...");
        btnBitacora.addActionListener(e -> verBitacoraSeleccionada());
        root.add(btnBitacora, BorderLayout.SOUTH);

        return root;
    }

    private void verBitacoraSeleccionada() {
        int row = tablaHistorial.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una batalla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idBatalla = (Integer) tablaHistorial.getValueAt(row, 0);
        // Mostrar en diálogo simple la bitácora (usando tu Historial/Batalla)
        JDialog dlg = new JDialog(this, "Bitácora batalla #" + idBatalla, true);
        JTextArea ta = new JTextArea(22, 60);
        ta.setEditable(false);

        // Buscar batalla por id en el historial y volcar la bitácora
        Batalla[] arr = arena.getHistorial().getBatallas();
        for (int i = 0; i < arena.getHistorial().getContador(); i++) {
            Batalla b = arr[i];
            if (b.getIdBatalla() == idBatalla) {
                String[] bit = b.getBitacora();
                int n = b.getContadorEventos();
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < n; k++) sb.append(bit[k]).append("\n");
                ta.setText(sb.toString());
                break;
            }
        }
        dlg.add(new JScrollPane(ta));
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    /* =========================
       Pestaña: Guardar/Cargar
       ========================= */
    private JPanel buildPanelIO() {
        JPanel root = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton btnGuardar = new JButton("Guardar estado...");
        JButton btnCargar  = new JButton("Cargar estado...");

        btnGuardar.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("estado.txt"));
            int op = fc.showSaveDialog(this);
            if (op == JFileChooser.APPROVE_OPTION) {
                arena.guardarEstado(fc.getSelectedFile().getAbsolutePath());
            }
        });

        btnCargar.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int op = fc.showOpenDialog(this);
            if (op == JFileChooser.APPROVE_OPTION) {
                arena.cargarEstado(fc.getSelectedFile().getAbsolutePath());
                // refrescar vistas
                refrescarTablaPersonajes();
                refrescarHistorial();
                refrescarCombosBatalla();
            }
        });

        root.add(btnGuardar);
        root.add(btnCargar);
        return root;
    }

    /* =========================
       Pestaña: Datos
       ========================= */
    private JPanel buildPanelDatos() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        p.add(new JLabel("Universidad de San Carlos de Guatemala"));
        p.add(new JLabel("Facultad de Ingeniería"));
        p.add(new JLabel("Curso: Laboratorio IPC1"));
        p.add(new JLabel("Práctica 2"));
        p.add(Box.createVerticalStrut(12));
        p.add(new JLabel("Estudiante: Carlos Didiere Cabrera Rodríguez Carlos Didiere"));
        p.add(new JLabel("Carné: 202401939"));
        p.add(new JLabel("Sección: D"));
        return p;
    }

    /* =========================
       Utilidades de refresco
       ========================= */
    private void refrescarTablaPersonajes() {
        String[] cols = {"ID","Nombre","Arma","HP","Ataque","Defensa","Agilidad","Velocidad","Vivo"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) {
                return switch (c) {
                    case 0,3,4,5,6,7 -> Integer.class;
                    default -> String.class;
                };
            }
        };
        for (int i = 0; i < arena.getCantidadPersonajes(); i++) {
            Personaje p = arena.getPersonajeEn(i);
            if (p != null) {
                model.addRow(new Object[]{
                        p.getId(), p.getNombre(), p.getArma(), p.getHp(),
                        p.getAtaque(), p.getDefensa(), p.getAgilidad(),
                        p.getVelocidad(), (p.estaVivo() ? "Sí" : "No")
                });
            }
        }
        tablaPersonajes.setModel(model);
    }

    private void refrescarHistorial() {
        String[] cols = {"#Batalla","Fecha","P1","P2","Ganador"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        Batalla[] bs = arena.getHistorial().getBatallas();
        int n = arena.getHistorial().getContador();
        for (int i = 0; i < n; i++) {
            Batalla b = bs[i];
            model.addRow(new Object[]{
                    b.getIdBatalla(), b.getFechaHora(),
                    b.getJugador1().getNombre(), b.getJugador2().getNombre(),
                    b.getGanador()
            });
        }
        tablaHistorial.setModel(model);
    }

    private void refrescarCombosBatalla() {
        cbP1.removeAllItems();
        cbP2.removeAllItems();
        for (int i = 0; i < arena.getCantidadPersonajes(); i++) {
            Personaje p = arena.getPersonajeEn(i);
            if (p != null) {
                cbP1.addItem(p.getNombre());
                cbP2.addItem(p.getNombre());
            }
        }
    }
}
