package views;
import javax.swing.*;
import java.awt.*;

public class SignUpView extends JFrame {

    public SignUpView() {
        setTitle("Registro - App Administrativa");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 38, 76));
        JLabel title = new JLabel("App Administrativa");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(title);
        mainPanel.add(header, BorderLayout.NORTH);

        // Centro con formulario
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(250, 245, 250));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        // Texto legal
        JTextArea legalText = new JTextArea(
                "En UNIMINUTO estamos comprometidos con el tratamiento confidencial de tus datos..."
        );
        legalText.setLineWrap(true);
        legalText.setWrapStyleWord(true);
        legalText.setEditable(false);
        legalText.setBackground(new Color(250, 245, 250));
        legalText.setFont(new Font("Arial", Font.PLAIN, 12));
        legalText.setBorder(null);
        formPanel.add(legalText);

        formPanel.add(Box.createVerticalStrut(10));

        // Autorización
        JLabel autorizaLabel = new JLabel("Autorizo el tratamiento de datos");
        formPanel.add(autorizaLabel);
        JComboBox<String> autorizacionCombo = new JComboBox<>(new String[]{"Seleccione...", "Sí", "No"});
        formPanel.add(autorizacionCombo);

        // Campos de texto y componentes
        String[] labels = {
                "Primer Nombre *", "Segundo Nombre *", "Apellidos *",
                "Correo *", "Confirmar Correo *", "Teléfono Móvil *",
                "Grado Académico *", "Sede *", "Tipo de Documento *",
                "Número de Documento *", "Contraseña *", "Repetir Contraseña *"
        };

        java.util.List<JTextField> textFields = new java.util.ArrayList<>();
        final JPasswordField[] passwordField = new JPasswordField[1];
        final JPasswordField[] repeatPasswordField = new JPasswordField[1];
        final JTextField[] emailField = new JTextField[1];
        final JTextField[] confirmEmailField = new JTextField[1];
        final JComboBox<String>[] tipoDocumentoCombo = new JComboBox[1];

        for (String label : labels) {
            formPanel.add(Box.createVerticalStrut(8));
            if (label.equals("Tipo de Documento *")) {
                tipoDocumentoCombo[0] = new JComboBox<>(new String[]{
                    "Seleccione...", "Cédula de Ciudadanía", "Tarjeta de Identidad", "Cédula de Extranjería", "Pasaporte"
                });
                tipoDocumentoCombo[0].setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                tipoDocumentoCombo[0].setBorder(BorderFactory.createTitledBorder(label));
                formPanel.add(tipoDocumentoCombo[0]);
            } else if (label.equals("Contraseña *")) {
                passwordField[0] = new JPasswordField();
                passwordField[0].setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                passwordField[0].setBorder(BorderFactory.createTitledBorder(label));
                formPanel.add(passwordField[0]);
            } else if (label.equals("Repetir Contraseña *")) {
                repeatPasswordField[0] = new JPasswordField();
                repeatPasswordField[0].setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                repeatPasswordField[0].setBorder(BorderFactory.createTitledBorder(label));
                formPanel.add(repeatPasswordField[0]);
            } else if (label.equals("Correo *")) {
                emailField[0] = new JTextField();
                emailField[0].setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                emailField[0].setBorder(BorderFactory.createTitledBorder(label));
                formPanel.add(emailField[0]);
                textFields.add(emailField[0]);
            } else if (label.equals("Confirmar Correo *")) {
                confirmEmailField[0] = new JTextField();
                confirmEmailField[0].setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                confirmEmailField[0].setBorder(BorderFactory.createTitledBorder(label));
                formPanel.add(confirmEmailField[0]);
                textFields.add(confirmEmailField[0]);
            } else {
                JTextField tf = new JTextField();
                tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                tf.setBorder(BorderFactory.createTitledBorder(label));
                formPanel.add(tf);
                textFields.add(tf);
            }
        }

        // Ayuda contraseña
        JLabel contraInfo = new JLabel("Mínimo 6 caracteres y 2 símbolos");
        contraInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        contraInfo.setForeground(Color.RED.darker());
        formPanel.add(contraInfo);

        // Botón
        JButton registerBtn = new JButton("Registrarse");
        registerBtn.setEnabled(false); // Inicialmente deshabilitado
        registerBtn.setBackground(Color.LIGHT_GRAY);
        registerBtn.setForeground(Color.DARK_GRAY);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(registerBtn);

        // Botón "Volver" debajo del botón "Registrarse"
        JButton volverBtn = new JButton("Volver");
        volverBtn.setBackground(new Color(220, 220, 220));
        volverBtn.setForeground(Color.BLACK);
        volverBtn.setFont(new Font("Arial", Font.BOLD, 14));
        volverBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        volverBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(volverBtn);

        // Acción del botón "Volver"
        volverBtn.addActionListener(e -> {
            this.dispose(); // Cierra la ventana actual
            new LoginView(); // Abre la ventana de Login
        });

        // Acción del botón "Registrarse" - Guardar en base de datos
        registerBtn.addActionListener(e -> {
            try {
                // Obtener datos del formulario
                String nombre = textFields.get(0).getText().trim(); // Primer Nombre
                String apellido = textFields.get(2).getText().trim(); // Apellidos
                String email = emailField[0].getText().trim();
                String username = textFields.get(7).getText().trim(); // Número de Documento como username (posición correcta)
                String password = new String(passwordField[0].getPassword());
                
                // Crear usuario
                models.Usuario usuario = new models.Usuario(nombre, apellido, email, username, password);
                utils.DatabaseConnection db = new utils.DatabaseConnection();
                boolean exito = db.insertUsuario(usuario);
                db.closeConnection();
                
                if (exito) {
                    JOptionPane.showMessageDialog(this, "¡Usuario registrado correctamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new LoginView();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar usuario. Por favor, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error de conexión o datos inválidos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Validación de campos
        Runnable validateForm = () -> {
            boolean allFilled = true;
            for (JTextField tf : textFields) {
                if (tf.getText().trim().isEmpty()) {
                    allFilled = false;
                    break;
                }
            }
            if (tipoDocumentoCombo[0] != null && tipoDocumentoCombo[0].getSelectedIndex() == 0) {
                allFilled = false;
            }
            if (passwordField[0] == null || repeatPasswordField[0] == null ||
                emailField[0] == null || confirmEmailField[0] == null) {
                registerBtn.setEnabled(false);
                registerBtn.setBackground(Color.LIGHT_GRAY);
                registerBtn.setForeground(Color.DARK_GRAY);
                return;
            }
            String pwd = new String(passwordField[0].getPassword());
            String pwd2 = new String(repeatPasswordField[0].getPassword());
            String email = emailField[0].getText().trim();
            String email2 = confirmEmailField[0].getText().trim();

            boolean emailsMatch = !email.isEmpty() && email.equals(email2);
            boolean pwdsMatch = !pwd.isEmpty() && pwd.equals(pwd2);

            boolean enable = allFilled && emailsMatch && pwdsMatch;
            registerBtn.setEnabled(enable);
            if (enable) {
                registerBtn.setBackground(new Color(0, 38, 76));
                registerBtn.setForeground(Color.WHITE);
            } else {
                registerBtn.setBackground(Color.LIGHT_GRAY);
                registerBtn.setForeground(Color.DARK_GRAY);
            }
        };

        javax.swing.event.DocumentListener docListener = new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validateForm.run(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validateForm.run(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validateForm.run(); }
        };

        for (JTextField tf : textFields) {
            tf.getDocument().addDocumentListener(docListener);
        }
        if (passwordField[0] != null) passwordField[0].getDocument().addDocumentListener(docListener);
        if (repeatPasswordField[0] != null) repeatPasswordField[0].getDocument().addDocumentListener(docListener);
        if (emailField[0] != null) emailField[0].getDocument().addDocumentListener(docListener);
        if (confirmEmailField[0] != null) confirmEmailField[0].getDocument().addDocumentListener(docListener);
        if (tipoDocumentoCombo[0] != null) {
            tipoDocumentoCombo[0].addActionListener(e -> validateForm.run());
        }

        // Scroll si es necesario
        JScrollPane scrollPane = new JScrollPane(formPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUpView::new);
    }
}
