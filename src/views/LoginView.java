package views;
import javax.swing.*;
import java.awt.*;
import utils.DatabaseConnection;


public class LoginView extends JFrame {

    public LoginView() {
        setTitle("App administrativa");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        // Encabezado
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 38, 76)); // Azul oscuro
        JLabel title = new JLabel("App administrativa");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(title);
        mainPanel.add(header, BorderLayout.NORTH);

        // Centro
        JPanel center = new JPanel();
        center.setBackground(Color.WHITE);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Logo
        ImageIcon logo = new ImageIcon("uniminuto_logo.png");
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(logoLabel);

        center.add(Box.createVerticalStrut(15));

        // Título
        JLabel loginLabel = new JLabel("Iniciar Sesión");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginLabel.setForeground(new Color(0, 38, 76));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(loginLabel);

        center.add(Box.createVerticalStrut(20));

        // Campos
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        emailField.setBorder(BorderFactory.createTitledBorder("Correo-e"));
        center.add(emailField);
        center.add(Box.createVerticalStrut(10));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        center.add(passwordField);
        center.add(Box.createVerticalStrut(10));

        // Checkboxes
        JCheckBox recordar = new JCheckBox("Recordarme");
        JCheckBox publico = new JCheckBox("Conectado desde un PC público");
        recordar.setBackground(Color.WHITE);
        publico.setBackground(Color.WHITE);
        center.add(recordar);
        center.add(publico);
        center.add(Box.createVerticalStrut(15));

        // Botón iniciar sesión y registrarse centrados
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);

        JButton loginBtn = new JButton("Iniciar Sesión");
        loginBtn.setBackground(new Color(0, 38, 76));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Acción para validar usuario en la base de datos
        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            // Validar usuario en la base de datos
            utils.DatabaseConnection db = new utils.DatabaseConnection();
            boolean valido = db.validarUsuario(email, password);
            db.closeConnection();
            if (valido) {
                // Guardar email de sesión
                DatabaseConnection.setCurrentUserEmail(email);
                JOptionPane.showMessageDialog(this, "¡Bienvenido!");
                // Abrir la ventana principal
                this.dispose();
                new MainView();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(loginBtn);

        buttonPanel.add(Box.createVerticalStrut(10));

        JButton registerBtn = new JButton("Registrarse");
        registerBtn.setBackground(new Color(0, 38, 76));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Acción para abrir SignUpView
        registerBtn.addActionListener(e -> {
            new SignUpView();
            dispose();
        });
        buttonPanel.add(registerBtn);

        buttonPanel.add(Box.createVerticalStrut(10));

        // Enlace centrado
        JLabel forgotPassword = new JLabel("¿Olvidó su contraseña?");
        forgotPassword.setForeground(Color.BLUE.darker());
        forgotPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        forgotPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(forgotPassword);

        center.add(buttonPanel);

        center.add(Box.createVerticalStrut(10));

        mainPanel.add(center, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(new Color(240, 235, 245));
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));

        JLabel footerLabel1 = new JLabel("© 2025 Sistema de Inscripción. Todos los derechos reservados.");
        JLabel footerLabel2 = new JLabel("Corporación Universitaria Minuto de Dios – UNIMINUTO.");
        JLabel footerLabel3 = new JLabel("Línea nacional gratuita: 01 8000 11 93 90. Bogotá: 593300");

        for (JLabel label : new JLabel[]{footerLabel1, footerLabel2, footerLabel3}) {
            label.setFont(new Font("Arial", Font.PLAIN, 10));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            footer.add(label);
        }

        mainPanel.add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginView::new);
    }
}
