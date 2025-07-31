package views;

import javax.swing.*;
import java.awt.*;
import utils.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class InfoPersonalView extends JFrame {
    // Añadir campos de formulario como variables de instancia
    private JTextField primerNombreField;
    private JTextField segundoNombreField;
    private JTextField apellidosField;
    private JComboBox<String> sexoField;
    private JComboBox<String> grupoSanguineoField;
    private JComboBox<String> tipoDocumentoField;
    private JTextField numeroDocumentoField;
    private JTextField fechaExpedicionField;
    private JTextField fechaNacimientoField;
    private JTextField paisNacimientoField;
    private JTextField ciudadNacimientoField;
    private JTextField telefonoCelularField;
    private JTextField correoField;
    private JComboBox<String> tipoDireccionField;
    private JTextField direccionField;
    private JTextField casaAptoField;
    private JComboBox<String> sisbenField;
    private JComboBox<String> epsField;
    private JComboBox<String> discapacidadField;
    private JTextField primerAcudienteField;
    private JTextField segundoAcudienteField;
    private JTextField apellidosAcudienteField;
    private JTextField telefonoAcudienteField;
    private JTextField correoAcudienteField;

    public InfoPersonalView() {
        setTitle("Información Personal - App Administrativa");
        setSize(1200, 900); // Tamaño inicial razonable
        setMinimumSize(new Dimension(800, 600)); // Tamaño mínimo para mantener la usabilidad
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
        JLabel title = new JLabel("Información Personal");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(title, BorderLayout.CENTER);
        mainPanel.add(header, BorderLayout.NORTH);

        // Panel del formulario con GridBagLayout para un diseño de dos columnas
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(250, 245, 250));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Encabezado con instrucciones
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Abarca dos columnas
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

        // Reset gridwidth
        gbc.gridwidth = 1;
        gbc.weightx = 0.5; // Cada columna toma 50% del espacio

        // Fila 1
        gbc.gridy++;
        gbc.gridx = 0;
        primerNombreField = (JTextField) createFormField("<html>Primer nombre <font color='red'>*</font></html>");
        formPanel.add(primerNombreField, gbc);
        gbc.gridx = 1;
        segundoNombreField = (JTextField) createFormField("<html>Segundo nombre <font color='red'>*</font></html>");
        formPanel.add(segundoNombreField, gbc);

        // Fila 2
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        apellidosField = (JTextField) createFormField("<html>Apellidos <font color='red'>*</font></html>");
        formPanel.add(apellidosField, gbc);
        gbc.gridwidth = 1;

        // Fila 3
        gbc.gridy++;
        gbc.gridx = 0;
        sexoField = (JComboBox<String>) createComboBoxField("<html>Sexo Biológico <font color='red'>*</font></html>", new String[]{"Seleccione...", "Masculino", "Femenino"});
        formPanel.add(sexoField, gbc);
        gbc.gridx = 1;
        grupoSanguineoField = (JComboBox<String>) createComboBoxField("<html>Grupo Sanguíneo RH <font color='red'>*</font></html>", new String[]{"Seleccione...", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        formPanel.add(grupoSanguineoField, gbc);

        // Título - Documento de Identidad
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        formPanel.add(createSectionTitle("DOCUMENTO DE IDENTIDAD"), gbc);
        gbc.gridwidth = 1;

        // Fila 4
        gbc.gridy++;
        gbc.gridx = 0;
        tipoDocumentoField = (JComboBox<String>) createComboBoxField("<html>Tipo de documento <font color='red'>*</font></html>", new String[]{"Seleccione...", "Cédula de Ciudadanía", "Tarjeta de Identidad", "Cédula de Extranjería", "Pasaporte"});
        formPanel.add(tipoDocumentoField, gbc);
        gbc.gridx = 1;
        numeroDocumentoField = (JTextField) createFormField("<html>Número de documento <font color='red'>*</font></html>");
        formPanel.add(numeroDocumentoField, gbc);

        // Fila 5
        gbc.gridy++;
        gbc.gridx = 0;
        fechaExpedicionField = (JTextField) createFormField("<html>Fecha de expedición (DD/MM/AAAA) <font color='red'>*</font></html>");
        formPanel.add(fechaExpedicionField, gbc);
        gbc.gridx = 1;
        fechaNacimientoField = (JTextField) createFormField("<html>Fecha de nacimiento (DD/MM/AAAA) <font color='red'>*</font></html>");
        formPanel.add(fechaNacimientoField, gbc);

        // Fila 6
        gbc.gridy++;
        gbc.gridx = 0;
        paisNacimientoField = (JTextField) createFormField("<html>País de nacimiento <font color='red'>*</font></html>");
        formPanel.add(paisNacimientoField, gbc);
        gbc.gridx = 1;
        ciudadNacimientoField = (JTextField) createFormField("<html>Ciudad de nacimiento <font color='red'>*</font></html>");
        formPanel.add(ciudadNacimientoField, gbc);

        // Fila 7
        gbc.gridy++;
        gbc.gridx = 0;
        telefonoCelularField = (JTextField) createFormField("<html>Teléfono celular <font color='red'>*</font></html>");
        formPanel.add(telefonoCelularField, gbc);
        gbc.gridx = 1;
        correoField = (JTextField) createFormField("<html>Correo electrónico <font color='red'>*</font></html>");
        formPanel.add(correoField, gbc);

        // Título - Dirección
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        formPanel.add(createSectionTitle("DIRECCIÓN"), gbc);
        gbc.gridwidth = 1;

        // Fila 8
        gbc.gridy++;
        gbc.gridx = 0;
        tipoDireccionField = (JComboBox<String>) createComboBoxField("<html>Tipo de dirección <font color='red'>*</font></html>", new String[]{"Seleccione...", "Casa", "Apartamento", "Finca", "Oficina", "Otro"});
        formPanel.add(tipoDireccionField, gbc);
        gbc.gridx = 1;
        direccionField = (JTextField) createFormField("<html>Dirección <font color='red'>*</font></html>");
        formPanel.add(direccionField, gbc);
        
        // Fila 9
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        casaAptoField = (JTextField) createFormField("<html>Casa, apartamento o barrio <font color='red'>*</font></html>");
        formPanel.add(casaAptoField, gbc);
        gbc.gridwidth = 1;

        // Título - Información Adicional
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        formPanel.add(createSectionTitle("INFORMACIÓN ADICIONAL"), gbc);
        gbc.gridwidth = 1;

        // Fila 10
        gbc.gridy++;
        gbc.gridx = 0;
        sisbenField = (JComboBox<String>) createComboBoxField("<html>¿Tiene Sisben? <font color='red'>*</font></html>", new String[]{"Seleccione...", "Sí", "No"});
        formPanel.add(sisbenField, gbc);
        gbc.gridx = 1;
        epsField = (JComboBox<String>) createComboBoxField("<html>¿Tiene EPS? <font color='red'>*</font></html>", new String[]{"Seleccione...", "Sí", "No"});
        formPanel.add(epsField, gbc);
        
        // Fila 11
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        discapacidadField = (JComboBox<String>) createComboBoxField("<html>¿Tiene discapacidad? <font color='red'>*</font></html>", new String[]{"Seleccione...", "Sí", "No"});
        formPanel.add(discapacidadField, gbc);
        gbc.gridwidth = 1;

        // Título - Datos Acudiente
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        formPanel.add(createSectionTitle("DATOS ACUDIENTE"), gbc);
        gbc.gridwidth = 1;

        // Fila 12
        gbc.gridy++;
        gbc.gridx = 0;
        primerAcudienteField = (JTextField) createFormField("<html>Primer nombre <font color='red'>*</font></html>");
        formPanel.add(primerAcudienteField, gbc);
        gbc.gridx = 1;
        segundoAcudienteField = (JTextField) createFormField("<html>Segundo nombre <font color='red'>*</font></html>");
        formPanel.add(segundoAcudienteField, gbc);

        // Fila 13
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        apellidosAcudienteField = (JTextField) createFormField("<html>Apellidos <font color='red'>*</font></html>");
        formPanel.add(apellidosAcudienteField, gbc);
        gbc.gridwidth = 1;

        // Fila 14
        gbc.gridy++;
        gbc.gridx = 0;
        telefonoAcudienteField = (JTextField) createFormField("<html>Teléfono celular <font color='red'>*</font></html>");
        formPanel.add(telefonoAcudienteField, gbc);
        gbc.gridx = 1;
        correoAcudienteField = (JTextField) createFormField("<html>Correo Acudiente <font color='red'>*</font></html>");
        formPanel.add(correoAcudienteField, gbc);

        // Panel de botones
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; // No rellenar
        gbc.anchor = GridBagConstraints.CENTER; // Centrar
        JButton saveBtn = new JButton("Guardar Solicitud");
        saveBtn.setBackground(new Color(0, 38, 76));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 16));
        saveBtn.setFocusPainted(false);
        saveBtn.setPreferredSize(new Dimension(200, 45));
        saveBtn.addActionListener(e -> {
            // Recopilar datos y guardar en BD
            DatabaseConnection db = new DatabaseConnection();
            boolean ok = db.upsertInformacionPersonal(
                primerNombreField.getText(), segundoNombreField.getText(), apellidosField.getText(),
                sexoField.getSelectedItem().toString(), grupoSanguineoField.getSelectedItem().toString(),
                tipoDocumentoField.getSelectedItem().toString(), numeroDocumentoField.getText(), fechaExpedicionField.getText(),
                paisNacimientoField.getText(), ciudadNacimientoField.getText(), fechaNacimientoField.getText(),
                telefonoCelularField.getText(), correoField.getText(), tipoDireccionField.getSelectedItem().toString(),
                direccionField.getText(), casaAptoField.getText(), sisbenField.getSelectedItem().toString(),
                epsField.getSelectedItem().toString(), discapacidadField.getSelectedItem().toString(),
                primerAcudienteField.getText(), segundoAcudienteField.getText(), apellidosAcudienteField.getText(),
                telefonoAcudienteField.getText(), correoAcudienteField.getText(),
                "" // <-- Agrega un argumento String vacío para completar 25 parámetros
            );
            db.closeConnection();
            JOptionPane.showMessageDialog(this,
                ok ? "Información personal guardada exitosamente" : "Error al guardar información personal",
                ok ? "Éxito" : "Error", JOptionPane.INFORMATION_MESSAGE);
        });
        JButton backBtn = new JButton("Volver a Página Inicio");
        backBtn.setBackground(new Color(220, 220, 220));
        backBtn.setForeground(Color.BLACK);
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setFocusPainted(false);
        backBtn.setPreferredSize(new Dimension(200, 45));
        backBtn.addActionListener(e -> {
            this.dispose();
            new MainView();
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
            try {
                PreparedStatement ps = new DatabaseConnection().getConnection()
                    .prepareStatement("SELECT * FROM informacion_personal WHERE email = ?");
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    sb.append("-- Datos Personales --\n");
                    sb.append("Nombre: " + rs.getString("primer_nombre") + " "+ rs.getString("segundo_nombre") + " " + rs.getString("apellidos")+"\n");
                    sb.append("Sexo: " + rs.getString("sexo_biologico") + "\n");
                    sb.append("Grupo Sanguíneo: " + rs.getString("grupo_sanguineo") + "\n\n");
                    sb.append("-- Documento --\n");
                    sb.append("Tipo: " + rs.getString("tipo_documento") + "\n");
                    sb.append("Número: " + rs.getString("numero_documento") + "\n\n");
                    // ...añadir más campos agrupados...
                } else {
                    sb.append("No hay información guardada.");
                }
            } catch (Exception ex) { sb.append("Error: " + ex.getMessage()); }
            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);
            area.setFont(new Font("Arial", Font.PLAIN, 14));
            JOptionPane.showMessageDialog(this, new JScrollPane(area), "Información Personal", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(250, 245, 250));
        buttonPanel.add(viewBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(backBtn);
        formPanel.add(buttonPanel, gbc);

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

    private JComponent createSectionTitle(String title) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(250, 245, 250));
        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(0, 38, 76));
        panel.add(label);
        return panel;
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
            // Recopilar datos y guardar en BD
            DatabaseConnection db = new DatabaseConnection();
            boolean ok = db.upsertInformacionPersonal(
                primerNombreField.getText(), segundoNombreField.getText(), apellidosField.getText(),
                sexoField.getSelectedItem().toString(), grupoSanguineoField.getSelectedItem().toString(),
                tipoDocumentoField.getSelectedItem().toString(), numeroDocumentoField.getText(), fechaExpedicionField.getText(),
                paisNacimientoField.getText(), ciudadNacimientoField.getText(), fechaNacimientoField.getText(),
                telefonoCelularField.getText(), correoField.getText(), tipoDireccionField.getSelectedItem().toString(),
                direccionField.getText(), casaAptoField.getText(), sisbenField.getSelectedItem().toString(),
                epsField.getSelectedItem().toString(), discapacidadField.getSelectedItem().toString(),
                primerAcudienteField.getText(), segundoAcudienteField.getText(), apellidosAcudienteField.getText(),
                telefonoAcudienteField.getText(), correoAcudienteField.getText(),
                "" // <-- Agrega un argumento String vacío para completar 25 parámetros
            );
            db.closeConnection();
            JOptionPane.showMessageDialog(this,
                ok ? "Información personal guardada exitosamente" : "Error al guardar información personal",
                ok ? "Éxito" : "Error", JOptionPane.INFORMATION_MESSAGE);
        });
        JButton backBtn = new JButton("Volver a Página Inicio");
        backBtn.setBackground(new Color(220, 220, 220));
        backBtn.setForeground(Color.BLACK);
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setFocusPainted(false);
        backBtn.setPreferredSize(new Dimension(200, 45));
        backBtn.addActionListener(e -> {
            this.dispose();
            new MainView();
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
            try {
                PreparedStatement ps = new DatabaseConnection().getConnection()
                    .prepareStatement("SELECT * FROM informacion_personal WHERE email = ?");
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    sb.append("-- Datos Personales --\n");
                    sb.append("Nombre: " + rs.getString("primer_nombre") + " "+ rs.getString("segundo_nombre") + " " + rs.getString("apellidos")+"\n");
                    sb.append("Sexo: " + rs.getString("sexo_biologico") + "\n");
                    sb.append("Grupo Sanguíneo: " + rs.getString("grupo_sanguineo") + "\n\n");
                    sb.append("-- Documento --\n");
                    sb.append("Tipo: " + rs.getString("tipo_documento") + "\n");
                    sb.append("Número: " + rs.getString("numero_documento") + "\n\n");
                    // ...añadir más campos agrupados...
                } else {
                    sb.append("No hay información guardada.");
                }
            } catch (Exception ex) { sb.append("Error: " + ex.getMessage()); }
            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);
            area.setFont(new Font("Arial", Font.PLAIN, 14));
            JOptionPane.showMessageDialog(this, new JScrollPane(area), "Información Personal", JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(viewBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(backBtn);
        return buttonPanel;
    }
}
