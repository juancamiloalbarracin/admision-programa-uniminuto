package views;
import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        setTitle("App Administrativa - Inicio");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 38, 76));
        JLabel title = new JLabel("App Administrativa - Sistema de Gestión");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(title);
        mainPanel.add(header, BorderLayout.NORTH);

        // Centro - Contenido principal
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(250, 245, 250));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Mensaje de bienvenida
        JLabel welcomeLabel = new JLabel("¡Bienvenido al Sistema Administrativo!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(0, 38, 76));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(welcomeLabel);

        centerPanel.add(Box.createVerticalStrut(20));

        JLabel instructionLabel = new JLabel("Utiliza la barra de navegación inferior para acceder a las diferentes secciones");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionLabel.setForeground(Color.DARK_GRAY);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(instructionLabel);

        centerPanel.add(Box.createVerticalStrut(30));

        // Panel de información con tarjetas
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardsPanel.setBackground(new Color(250, 245, 250));

        // Tarjeta Información Académica
        JPanel academicCard = createInfoCard("Información Académica", 
            "Gestiona tus datos académicos, historial y certificaciones");
        cardsPanel.add(academicCard);

        // Tarjeta Información Personal
        JPanel personalCard = createInfoCard("Información Personal", 
            "Actualiza y mantén al día tu información personal");
        cardsPanel.add(personalCard);

        // Tarjeta Radicar
        JPanel radicarCard = createInfoCard("Radicar", 
            "Presenta solicitudes y documentos oficiales");
        cardsPanel.add(radicarCard);

        centerPanel.add(cardsPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Navbar inferior
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(0, 38, 76));
        navBar.setLayout(new GridLayout(1, 3, 0, 0));
        navBar.setPreferredSize(new Dimension(getWidth(), 60));

        // Botón Información Académica
        JButton academicBtn = createNavButton("Información Académica");
        academicBtn.addActionListener(e -> {
            this.dispose();
            new InfoAcademicaView();
        });

        // Botón Información Personal
        JButton personalBtn = createNavButton("Información Personal");
        personalBtn.addActionListener(e -> {
            this.dispose();
            new InfoPersonalView();
        });

        // Botón Radicar
        JButton radicarBtn = createNavButton("Radicar");
        radicarBtn.addActionListener(e -> {
            this.dispose();
            new RadicarView();
        });

        navBar.add(academicBtn);
        navBar.add(personalBtn);
        navBar.add(radicarBtn);

        mainPanel.add(navBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createInfoCard(String title, String description) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 38, 76), 2),
            BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 38, 76));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(titleLabel);

        card.add(Box.createVerticalStrut(10));

        JTextArea descArea = new JTextArea(description);
        descArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descArea.setForeground(Color.DARK_GRAY);
        descArea.setBackground(Color.WHITE);
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(descArea);

        return card;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 38, 76));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efectos hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 50, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 38, 76));
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainView::new);
    }
}
