package views;
import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DatabaseConnection;

public class InfoAcademicaView extends JFrame {
    // Declarar campos de formulario
    private JComboBox<String> nivelField;
    private JComboBox<String> sedeField;
    private JComboBox<String> gradoAcademicoField;
    private JTextField periodoAdmisionField;
    private JTextField metodologiaField;
    private JComboBox<String> jornadaField;
    private JComboBox<String> planDecisionField;
    private JTextField gradoSeleccionadoField;
    private JTextField paisField;
    private JTextField gradoObtenidoField;
    private JTextField fechaGraduacionField;

    public InfoAcademicaView() {
        setTitle("Información Académica - App Administrativa");
        setSize(1000, 800); // Tamaño inicial
        setMinimumSize(new Dimension(800, 600)); // Tamaño mínimo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 38, 76));
        header.setPreferredSize(new Dimension(0, 60));
        JLabel title = new JLabel("Información Académica");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(title, BorderLayout.CENTER);
        mainPanel.add(header, BorderLayout.NORTH);

        // Panel del formulario con GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(250, 245, 250));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Encabezado con instrucciones
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        JTextArea instrucciones = new JTextArea(
            "Ingrese su información en cada una de las siguientes secciones. Los campos con un asterisco (*) rojo son obligatorios " +
            "y deben completarse para enviar la solicitud. Pueden ser requeridas preguntas adicionales en base a sus respuestas a una pregunta anterior.");
        instrucciones.setFont(new Font("Arial", Font.PLAIN, 14));
        instrucciones.setWrapStyleWord(true);
        instrucciones.setLineWrap(true);
        instrucciones.setEditable(false);
        instrucciones.setFocusable(false);
        instrucciones.setBackground(new Color(230, 230, 250));
        instrucciones.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 38, 76)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        formPanel.add(instrucciones, gbc);

        // Resetear gbc para las siguientes filas
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;

        // 2. Nivel
        gbc.gridy++;
        gbc.gridx = 0;
        String[] niveles = {"Seleccione...", "Primaria", "Secundaria", "Cursos complementarios"};
        nivelField = (JComboBox<String>) createComboBoxField("<html>Nivel <font color='red'>*</font></html>", niveles);
        formPanel.add(nivelField, gbc);

        // 3. Sede
        gbc.gridx = 1;
        String[] sedes = {
            "Seleccione...", "Apartado", "Arauca", "Armenia", "Barrancabermeja", "Barranquilla", 
            "Bogota D.C", "Boyaca", "Bucaramanga", "Buga", "Cali", "Cartagena", "Casanare", "Caqueta"
        };
        sedeField = (JComboBox<String>) createComboBoxField("<html>Sede <font color='red'>*</font></html>", sedes);
        formPanel.add(sedeField, gbc);

        // 4. Grado académico
        gbc.gridy++;
        gbc.gridx = 0;
        String[] grados = {
            "Seleccione...", "1 Primaria", "2 Primaria", "3 Primaria", "4 Primaria", "5 Primaria",
            "6 Bachillerato", "7 Bachillerato", "8 Bachillerato", "9 Bachillerato", "10 Bachillerato", "11 Bachillerato",
            "Cursos complementarios"
        };
        gradoAcademicoField = (JComboBox<String>) createComboBoxField("<html>Grado académico <font color='red'>*</font></html>", grados);
        formPanel.add(gradoAcademicoField, gbc);

        // 5. Periodo de admisión
        gbc.gridx = 1;
        periodoAdmisionField = (JTextField) createFormField("<html>Periodo de admisión <font color='red'>*</font></html>");
        formPanel.add(periodoAdmisionField, gbc);

        // 6. Metodología
        gbc.gridy++;
        gbc.gridx = 0;
        metodologiaField = (JTextField) createFormField("Metodología");
        formPanel.add(metodologiaField, gbc);

        // 7. Jornada
        gbc.gridx = 1;
        String[] jornadas = {"Seleccione...", "Diurna", "Nocturna"};
        jornadaField = (JComboBox<String>) createComboBoxField("<html>Jornada <font color='red'>*</font></html>", jornadas);
        formPanel.add(jornadaField, gbc);

        // 8. Plan de decisión
        gbc.gridy++;
        gbc.gridx = 0;
        String[] planes = {"Seleccione...", "Plan A", "Plan B", "Plan C"};
        planDecisionField = (JComboBox<String>) createComboBoxField("<html>Plan de decisión <font color='red'>*</font></html>", planes);
        formPanel.add(planDecisionField, gbc);

        // 9. Grado seleccionado
        gbc.gridx = 1;
        gradoSeleccionadoField = (JTextField) createFormField("Grado seleccionado");
        formPanel.add(gradoSeleccionadoField, gbc);

        // 10. País y Grado obtenido
        gbc.gridy++;
        gbc.gridx = 0;
        paisField = (JTextField) createFormField("<html>País <font color='red'>*</font></html>");
        formPanel.add(paisField, gbc);
        gbc.gridx = 1;
        gradoObtenidoField = (JTextField) createFormField("<html>Grado obtenido <font color='red'>*</font></html>");
        formPanel.add(gradoObtenidoField, gbc);

        // 11. Fecha de graduación
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Abarca dos columnas
        fechaGraduacionField = (JTextField) createFormField("Fecha de graduación (DD/MM/AAAA)");
        formPanel.add(fechaGraduacionField, gbc);
        gbc.gridwidth = 1; // Reset

        // Panel de botones
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(createButtonPanel(), gbc);

        // Panel de relleno para empujar todo hacia arriba
        gbc.gridy++;
        gbc.weighty = 1.0;
        formPanel.add(new JPanel(), gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // --- Métodos de ayuda para crear componentes ---

    private JComponent createFormField(String label) {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createTitledBorder(label));
        field.setPreferredSize(new Dimension(0, 45));
        return field;
    }

    private JComboBox<String> createComboBoxField(String label, String[] options) {
        JComboBox<String> combo = new JComboBox<>(options);
        combo.setFont(new Font("Arial", Font.PLAIN, 14));
        combo.setBorder(BorderFactory.createTitledBorder(label));
        combo.setPreferredSize(new Dimension(0, 45));
        return combo;
    }

    private JComponent createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(250, 245, 250));
        
        JButton saveBtn = new JButton("Guardar Solicitud");
        saveBtn.setBackground(new Color(0, 38, 76));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 16));
        saveBtn.setFocusPainted(false);
        saveBtn.setPreferredSize(new Dimension(200, 45));
        saveBtn.addActionListener(e -> {
            // TODO: Reemplaza esto con la forma correcta de obtener el email del usuario actual
            String correoUserEmail = JOptionPane.showInputDialog(this, "Ingrese su correo electrónico:");

            DatabaseConnection db = new DatabaseConnection();
            boolean ok = db.upsertInformacionAcademica(
                /* email */ correoUserEmail,
                nivelField.getSelectedItem().toString(),
                sedeField.getSelectedItem().toString(),
                gradoAcademicoField.getSelectedItem().toString(),
                periodoAdmisionField.getText(),
                metodologiaField.getText(),
                jornadaField.getSelectedItem().toString(),
                planDecisionField.getSelectedItem().toString(),
                gradoSeleccionadoField.getText(),
                paisField.getText(),
                gradoObtenidoField.getText(),
                fechaGraduacionField.getText()
            );
            JOptionPane.showMessageDialog(this,
                ok ? "Información académica guardada exitosamente" : "Error al guardar información académica",
                ok ? "Éxito" : "Error", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton viewBtn = new JButton("Ver Información");
        viewBtn.setBackground(new Color(0, 38, 76));
        viewBtn.setForeground(Color.WHITE);
        viewBtn.setFont(new Font("Arial", Font.BOLD, 16));
        viewBtn.setFocusPainted(false);
        viewBtn.setPreferredSize(new Dimension(200, 45));
        viewBtn.addActionListener(e -> {
            String email = DatabaseConnection.getCurrentUserEmail();
            StringBuilder sb = new StringBuilder();
            try (PreparedStatement ps = new DatabaseConnection().getConnection()
                    .prepareStatement("SELECT * FROM informacion_academica WHERE email = ?")) {
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    sb.append("-- Datos Académicos --\n");
                    sb.append("Nivel: " + rs.getString("nivel") + "\n");
                    sb.append("Sede: " + rs.getString("sede") + "\n");
                    sb.append("Grado académico: " + rs.getString("grado_academico") + "\n");
                    sb.append("Periodo admisión: " + rs.getString("periodo_admision") + "\n");
                    sb.append("Metodología: " + rs.getString("metodologia") + "\n");
                    sb.append("Jornada: " + rs.getString("jornada") + "\n");
                    sb.append("Plan decisión: " + rs.getString("plan_decision") + "\n");
                    sb.append("Grado seleccionado: " + rs.getString("grado_seleccionado") + "\n");
                    sb.append("País: " + rs.getString("pais") + "\n");
                    sb.append("Grado obtenido: " + rs.getString("grado_obtenido") + "\n");
                    sb.append("Fecha graduación: " + rs.getString("fecha_graduacion") + "\n");
                } else {
                    sb.append("No hay información académica guardada.");
                }
            } catch (Exception ex) { sb.append("Error: " + ex.getMessage()); }
            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);
            area.setFont(new Font("Arial", Font.PLAIN, 14));
            JOptionPane.showMessageDialog(this, new JScrollPane(area), "Información Académica", JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(saveBtn);
        buttonPanel.add(viewBtn);

        JButton backBtn = new JButton("Volver al Inicio");
        backBtn.setBackground(new Color(220, 220, 220));
        backBtn.setForeground(Color.BLACK);
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setFocusPainted(false);
        backBtn.setPreferredSize(new Dimension(200, 45));
        backBtn.addActionListener(e -> {
            this.dispose();
            new MainView();
        });

        buttonPanel.add(backBtn);
        return buttonPanel;
    }
}
