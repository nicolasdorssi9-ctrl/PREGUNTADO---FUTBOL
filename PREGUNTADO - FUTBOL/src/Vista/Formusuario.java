package Vista;

import com.formdev.flatlaf.FlatDarkLaf;
import dominio.Sistemacategorias;
import dominio.Sistemaniveles;
import dominio.Sistemaopciones;
import dominio.Sistemapreguntas;
import dominio.Sistemausuario;
import dominio.Sistemausuarios_logros;
import dominio.categorias;
import dominio.logroJugador;
import dominio.nivel;
import dominio.opcion;
import dominio.pregunta;
import dominio.usuario;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;

public class Formusuario extends JFrame {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Sistemausuario sistema = new Sistemausuario();
    private final Sistemacategorias sistemaCategorias = new Sistemacategorias();
    private final Sistemaniveles sistemaNiveles = new Sistemaniveles();
    private final Sistemapreguntas sistemaPreguntas = new Sistemapreguntas();
    private final Sistemaopciones sistemaOpciones = new Sistemaopciones();
    private final Sistemausuarios_logros sistemaUsuariosLogros = new Sistemausuarios_logros();
    private final JTextField txtCorreo = new JTextField(22);
    private final JPasswordField txtContraseña = new JPasswordField(22);
    private final JButton btnIniciarSesion = new JButton("Iniciar sesión");
    private final JButton btnRegistrateAqui = new JButton("Regístrate aquí");
    private final JButton btnVerLogros = new JButton("Ver logros de jugadores");

    public Formusuario() {
        super("Preguntado - Iniciar sesión");
        construirInterfaz();
        btnIniciarSesion.addActionListener(event -> iniciarSesion());
        btnRegistrateAqui.addActionListener(event -> abrirRegistro());
        btnVerLogros.addActionListener(event -> mostrarLogrosJugadores());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(420, 290));
        pack();
        setLocationRelativeTo(null);
    }

    private void construirInterfaz() {
        JPanel raiz = new JPanel(new GridBagLayout());
        raiz.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 6, 8, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;

        JLabel titulo = new JLabel("¡Bienvenido!");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 26f));
        raiz.add(titulo, c);

        c.gridwidth = 1;
        c.gridy++;
        raiz.add(new JLabel("Correo"), c);
        c.gridx = 1;
        raiz.add(txtCorreo, c);

        c.gridx = 0;
        c.gridy++;
        raiz.add(new JLabel("Contraseña"), c);
        c.gridx = 1;
        raiz.add(txtContraseña, c);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        acciones.add(btnRegistrateAqui);
        acciones.add(btnIniciarSesion);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        raiz.add(acciones, c);

        c.gridy++;
        JPanel accionesPublicas = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        accionesPublicas.add(btnVerLogros);
        raiz.add(accionesPublicas, c);

        setContentPane(raiz);
        getRootPane().setDefaultButton(btnIniciarSesion);
    }

    private void mostrarLogrosJugadores() {
        final java.util.List<logroJugador> logros;
        try {
            logros = sistemaUsuariosLogros.listarLogrosPorJugador();
        } catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar los logros. Revisa la conexión con la base de datos.",
                    "Error al consultar logros",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (logros.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Todavía no hay jugadores registrados.",
                    "Logros de jugadores",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Jugador", "Correo", "Logro", "Descripción", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (logroJugador logro : logros) {
            modelo.addRow(new Object[]{
                logro.getNombreJugador(),
                logro.getCorreoJugador(),
                logro.getNombreLogro() == null ? "Sin logros" : logro.getNombreLogro(),
                logro.getDescripcionLogro() == null ? "" : logro.getDescripcionLogro(),
                logro.getFechaObtencion() == null
                        ? ""
                        : logro.getFechaObtencion().toLocalDateTime().format(FORMATO_FECHA)
            });
        }

        JTable tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane desplazamiento = new JScrollPane(tabla);
        desplazamiento.setPreferredSize(new Dimension(820, 360));
        JOptionPane.showMessageDialog(
                this,
                desplazamiento,
                "Logros de jugadores registrados",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirRegistro() {
        RegistroDialog dialogo = new RegistroDialog(this);
        dialogo.setVisible(true);
    }

    private void iniciarSesion() {
        String correo = txtCorreo.getText().trim();
        char[] contrasena = txtContraseña.getPassword();

        if (correo.isEmpty() || contrasena.length == 0) {
            Arrays.fill(contrasena, '\0');
            JOptionPane.showMessageDialog(
                    this, "Ingresa tu correo y contraseña.", "Datos requeridos",
                    JOptionPane.WARNING_MESSAGE);
            limpiarCamposInicioSesion();
            return;
        }

        usuario usuarioAutenticado;
        try {
            usuarioAutenticado = sistema.autenticarUsuario(correo, contrasena);
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(
                    this, "No se pudo verificar la cuenta. Revisa la conexión con la base de datos.",
                    "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
            limpiarCamposInicioSesion();
            return;
        } finally {
            Arrays.fill(contrasena, '\0');
        }

        if (usuarioAutenticado != null) {
            JOptionPane.showMessageDialog(
                    this, "Inicio de sesión correcto.", "Bienvenido",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    this, "El correo o la contraseña son incorrectos.", "Inicio de sesión fallido",
                    JOptionPane.WARNING_MESSAGE);
        }
        limpiarCamposInicioSesion();
        if (usuarioAutenticado != null) {
            abrirSeleccionCategorias(usuarioAutenticado);
        }
    }

    private void abrirSeleccionCategorias(usuario usuarioActual) {
        java.util.List<categorias> categoriasDisponibles = sistemaCategorias.listar();
        if (categoriasDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No hay categorías disponibles. Verifica que existan categorías y que la base de datos esté conectada.",
                    "Categorías no disponibles",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JComboBox<String> selector = new JComboBox<>();
        for (categorias categoria : categoriasDisponibles) {
            selector.addItem(categoria.getNombre_categoria());
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                new Object[]{"Selecciona una categoría para jugar:", selector},
                "Elegir categoría",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.OK_OPTION) {
            categorias categoriaSeleccionada =
                    categoriasDisponibles.get(selector.getSelectedIndex());
            abrirSeleccionNiveles(usuarioActual, categoriaSeleccionada);
        }
    }

    private void abrirSeleccionNiveles(usuario usuarioActual, categorias categoriaSeleccionada) {
        java.util.List<nivel> nivelesDisponibles = sistemaNiveles.listar();
        if (nivelesDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No hay niveles disponibles. Verifica que existan niveles y que la base de datos esté conectada.",
                    "Niveles no disponibles",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JComboBox<String> selector = new JComboBox<>();
        for (nivel nivelDisponible : nivelesDisponibles) {
            selector.addItem(nivelDisponible.getNombre_nivel());
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                new Object[]{
                    "Categoría: " + categoriaSeleccionada.getNombre_categoria(),
                    "Selecciona un nivel para jugar:",
                    selector
                },
                "Elegir nivel",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.OK_OPTION) {
            nivel nivelSeleccionado = nivelesDisponibles.get(selector.getSelectedIndex());
            mostrarPreguntas(usuarioActual, categoriaSeleccionada, nivelSeleccionado);
        }
    }

    private void mostrarPreguntas(usuario usuarioActual, categorias categoriaSeleccionada,
            nivel nivelSeleccionado) {
        java.util.List<pregunta> preguntas = sistemaPreguntas.listarPorCategoriaYNivel(
                categoriaSeleccionada.getId_categoria(), nivelSeleccionado.getId_nivel());
        if (preguntas.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No hay preguntas para la categoría y el nivel seleccionados.",
                    "Preguntas no disponibles",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        java.util.List<opcion> todasLasOpciones = sistemaOpciones.listar();
        new JuegoDialog(usuarioActual, categoriaSeleccionada, nivelSeleccionado,
                preguntas, todasLasOpciones).setVisible(true);
    }

    private final class JuegoDialog extends JDialog {

        private final usuario usuarioActual;
        private final categorias categoriaSeleccionada;
        private final nivel nivelSeleccionado;
        private final java.util.List<pregunta> preguntas;
        private final java.util.List<opcion> todasLasOpciones;
        private final JLabel lblProgreso = new JLabel();
        private final JTextArea txtPregunta = new JTextArea(4, 36);
        private final JPanel panelOpciones = new JPanel();
        private final JLabel lblResultado = new JLabel(" ");
        private final JButton btnSiguiente = new JButton("Siguiente pregunta");
        private final JButton btnVolverInicio = new JButton("Volver al inicio");
        private int indicePregunta;
        private int respuestasCorrectas;
        private int preguntasRespondidas;
        private int rachaCorrectas;

        private JuegoDialog(usuario usuarioActual, categorias categoria, nivel nivel,
                java.util.List<pregunta> preguntas, java.util.List<opcion> opciones) {
            super(Formusuario.this, "Preguntas", true);
            this.usuarioActual = usuarioActual;
            this.categoriaSeleccionada = categoria;
            this.nivelSeleccionado = nivel;
            this.preguntas = preguntas;
            this.todasLasOpciones = opciones;

            construirInterfaz();
            mostrarPreguntaActual();
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setMinimumSize(new Dimension(520, 360));
            pack();
            setLocationRelativeTo(Formusuario.this);
        }

        private void construirInterfaz() {
            JPanel raiz = new JPanel(new BorderLayout(0, 14));
            raiz.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

            lblProgreso.setFont(lblProgreso.getFont().deriveFont(Font.BOLD, 16f));
            raiz.add(lblProgreso, BorderLayout.NORTH);

            txtPregunta.setEditable(false);
            txtPregunta.setLineWrap(true);
            txtPregunta.setWrapStyleWord(true);
            txtPregunta.setOpaque(false);
            txtPregunta.setFont(txtPregunta.getFont().deriveFont(17f));
            txtPregunta.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

            panelOpciones.setLayout(new javax.swing.BoxLayout(panelOpciones, javax.swing.BoxLayout.Y_AXIS));
            JPanel centro = new JPanel(new BorderLayout(0, 12));
            centro.add(new JScrollPane(txtPregunta), BorderLayout.NORTH);
            centro.add(panelOpciones, BorderLayout.CENTER);
            raiz.add(centro, BorderLayout.CENTER);

            JPanel pie = new JPanel(new BorderLayout(8, 8));
            lblResultado.setFont(lblResultado.getFont().deriveFont(Font.BOLD));
            pie.add(lblResultado, BorderLayout.NORTH);
            JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            acciones.add(btnVolverInicio);
            acciones.add(btnSiguiente);
            pie.add(acciones, BorderLayout.SOUTH);
            raiz.add(pie, BorderLayout.SOUTH);

            btnSiguiente.setVisible(false);
            btnSiguiente.addActionListener(event -> avanzar());
            btnVolverInicio.addActionListener(event -> dispose());
            setContentPane(raiz);
            getRootPane().setDefaultButton(btnSiguiente);
        }

        private void mostrarPreguntaActual() {
            if (indicePregunta >= preguntas.size()) {
                mostrarResultadoFinal();
                return;
            }

            pregunta preguntaActual = preguntas.get(indicePregunta);
            lblProgreso.setText("Pregunta " + (indicePregunta + 1) + " de " + preguntas.size()
                    + "  |  " + categoriaSeleccionada.getNombre_categoria()
                    + " - " + nivelSeleccionado.getNombre_nivel());
            txtPregunta.setText(preguntaActual.getTexto_pregunta());
            panelOpciones.removeAll();
            lblResultado.setText(" ");
            btnSiguiente.setText(indicePregunta == preguntas.size() - 1
                    ? "Ver resultado" : "Siguiente pregunta");
            btnSiguiente.setVisible(false);

            int opcionesAgregadas = 0;
            for (opcion opcionActual : todasLasOpciones) {
                if (opcionActual.getId_pregunta() == preguntaActual.getId_pregunta()) {
                    JButton botonOpcion = new JButton(opcionActual.getTexto_opcion());
                    botonOpcion.setAlignmentX(LEFT_ALIGNMENT);
                    botonOpcion.addActionListener(event -> responder(opcionActual));
                    panelOpciones.add(botonOpcion);
                    panelOpciones.add(javax.swing.Box.createVerticalStrut(6));
                    opcionesAgregadas++;
                }
            }

            if (opcionesAgregadas == 0) {
                lblResultado.setText("Esta pregunta no tiene opciones. Puedes continuar.");
                btnSiguiente.setVisible(true);
            }
            panelOpciones.revalidate();
            panelOpciones.repaint();
            pack();
        }

        private void responder(opcion opcionSeleccionada) {
            preguntasRespondidas++;
            if (opcionSeleccionada.isEs_correcta()) {
                respuestasCorrectas++;
                rachaCorrectas++;
                lblResultado.setText("¡Respuesta correcta!");
                otorgarLogroSiNuevo(
                        "Primeros Pasos", "Responde tu primera pregunta correctamente.");
                if (rachaCorrectas == 5) {
                    otorgarLogroSiNuevo(
                            "Racha Ganadora", "Consigue 5 aciertos seguidos en una partida.");
                }
                if (nivelSeleccionado.getId_nivel() == 3) {
                    otorgarLogroSiNuevo(
                            "Enciclopedia del Fútbol",
                            "Responde correctamente una pregunta de Nivel Avanzado.");
                }
            } else {
                rachaCorrectas = 0;
                opcion opcionCorrecta = obtenerOpcionCorrecta();
                if (opcionCorrecta != null) {
                    lblResultado.setText("Respuesta incorrecta. La correcta era: "
                            + opcionCorrecta.getTexto_opcion());
                } else {
                    lblResultado.setText("Respuesta incorrecta.");
                }
            }

            for (java.awt.Component componente : panelOpciones.getComponents()) {
                if (componente instanceof JButton) {
                    componente.setEnabled(false);
                }
            }
            btnSiguiente.setText(indicePregunta == preguntas.size() - 1
                    ? "Ver resultado" : "Siguiente pregunta");
            btnSiguiente.setVisible(true);
        }

        private opcion obtenerOpcionCorrecta() {
            pregunta preguntaActual = preguntas.get(indicePregunta);
            for (opcion opcionActual : todasLasOpciones) {
                if (opcionActual.getId_pregunta() == preguntaActual.getId_pregunta()
                        && opcionActual.isEs_correcta()) {
                    return opcionActual;
                }
            }
            return null;
        }

        private void otorgarLogroSiNuevo(String nombre, String descripcion) {
            try {
                if (sistemaUsuariosLogros.otorgarSiNoExiste(
                        usuarioActual.getId_usuario(), nombre)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "¡Logro desbloqueado!\n\n" + nombre + "\n" + descripcion,
                            "Nuevo logro",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (java.sql.SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo guardar el logro \"" + nombre + "\". Revisa la conexión con la base de datos.",
                        "Error al guardar el logro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        private void avanzar() {
            indicePregunta++;
            mostrarPreguntaActual();
        }

        private void mostrarResultadoFinal() {
            lblProgreso.setText("Partida finalizada");
            txtPregunta.setText("Respondiste correctamente " + respuestasCorrectas + " de "
                    + preguntasRespondidas + " preguntas.");
            panelOpciones.removeAll();
            panelOpciones.revalidate();
            panelOpciones.repaint();
            lblResultado.setText("Categoría: " + categoriaSeleccionada.getNombre_categoria()
                    + "  |  Nivel: " + nivelSeleccionado.getNombre_nivel());
            btnSiguiente.setVisible(false);
            getRootPane().setDefaultButton(btnVolverInicio);
            pack();
        }
    }

    private void limpiarCamposInicioSesion() {
        txtCorreo.setText("");
        txtContraseña.setText("");
    }

    private final class RegistroDialog extends JDialog {

        private final JTextField txtNombre = new JTextField(22);
        private final JTextField txtCorreoRegistro = new JTextField(22);
        private final JPasswordField txtContrasenaRegistro = new JPasswordField(22);
        private final JTextField txtFechaRegistro = new JTextField(22);

        private RegistroDialog(JFrame propietario) {
            super(propietario, "Crear cuenta", true);
            construirFormulario();
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            pack();
            setMinimumSize(new Dimension(440, getHeight()));
            setLocationRelativeTo(propietario);
        }

        private void construirFormulario() {
            JPanel raiz = new JPanel(new BorderLayout(0, 16));
            raiz.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

            JLabel titulo = new JLabel("Crea tu cuenta");
            titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 22f));
            raiz.add(titulo, BorderLayout.NORTH);

            JPanel campos = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(7, 4, 7, 10);
            c.anchor = GridBagConstraints.WEST;
            c.gridx = 0;
            c.gridy = 0;
            agregarCampo(campos, c, "Nombre", txtNombre);
            agregarCampo(campos, c, "Correo", txtCorreoRegistro);
            agregarCampo(campos, c, "Contraseña", txtContrasenaRegistro);

            txtFechaRegistro.setEditable(false);
            txtFechaRegistro.setFocusable(false);
            txtFechaRegistro.setText(LocalDateTime.now().format(FORMATO_FECHA));
            agregarCampo(campos, c, "Fecha de registro", txtFechaRegistro);
            raiz.add(campos, BorderLayout.CENTER);

            JButton btnCancelar = new JButton("Cancelar");
            JButton btnCrearCuenta = new JButton("Crear cuenta");
            btnCancelar.addActionListener(event -> dispose());
            btnCrearCuenta.addActionListener(event -> registrarCuenta());
            JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            acciones.add(btnCancelar);
            acciones.add(btnCrearCuenta);
            raiz.add(acciones, BorderLayout.SOUTH);

            setContentPane(raiz);
            getRootPane().setDefaultButton(btnCrearCuenta);
        }

        private void agregarCampo(JPanel panel, GridBagConstraints c, String etiqueta,
                JTextField campo) {
            c.gridx = 0;
            c.weightx = 0;
            c.fill = GridBagConstraints.NONE;
            panel.add(new JLabel(etiqueta), c);
            c.gridx = 1;
            c.weightx = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            panel.add(campo, c);
            c.gridy++;
        }

        private void registrarCuenta() {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreoRegistro.getText().trim();
            char[] contrasena = txtContrasenaRegistro.getPassword();

            if (nombre.isEmpty() || correo.isEmpty() || contrasena.length == 0) {
                Arrays.fill(contrasena, '\0');
                JOptionPane.showMessageDialog(
                        this, "Completa nombre, correo y contraseña.", "Datos requeridos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
                Arrays.fill(contrasena, '\0');
                JOptionPane.showMessageDialog(
                        this, "Escribe un correo electrónico válido.", "Correo inválido",
                        JOptionPane.WARNING_MESSAGE);
                txtCorreoRegistro.requestFocusInWindow();
                return;
            }
            if (contrasena.length < 8) {
                Arrays.fill(contrasena, '\0');
                JOptionPane.showMessageDialog(
                        this, "La contraseña debe tener al menos 8 caracteres.",
                        "Contraseña inválida", JOptionPane.WARNING_MESSAGE);
                txtContrasenaRegistro.requestFocusInWindow();
                return;
            }

            Timestamp fechaRegistro = Timestamp.valueOf(LocalDateTime.now());
            txtFechaRegistro.setText(fechaRegistro.toLocalDateTime().format(FORMATO_FECHA));
            int id;
            try {
                id = sistema.registrar(nombre, correo, contrasena, fechaRegistro);
            } finally {
                Arrays.fill(contrasena, '\0');
            }

            if (id < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo crear la cuenta. Verifica la conexión y que el correo no esté registrado.",
                        "Error al registrar",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(
                    this, "Tu cuenta fue creada correctamente.", "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        SwingUtilities.invokeLater(() -> new Formusuario().setVisible(true));
    }
}
