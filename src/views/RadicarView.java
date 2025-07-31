package views;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DatabaseConnection;

public class RadicarView extends JFrame {

    public RadicarView() {
        setTitle("Radicar Solicitud - App Administrativa");
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
        JLabel title = new JLabel("Radicar Solicitud");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(title);
        mainPanel.add(header, BorderLayout.NORTH);

        // Centro - Formulario
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(250, 245, 250));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Texto informativo y de certificación
        JTextArea infoArea = new JTextArea(
            "Por favor tenga en cuenta que, una vez enviada la presente solicitud, la misma no podrá ser modificada.\n" +
            "Por lo tanto, es importante que la lea con detenimiento y confirme la veracidad de la información antes de su envio.\n" +
            "Una vez se envíe con éxito, recibirá un mensaje de confirmación, con la correspondiente actualización del estatus del envío de su solicitud en la pestaña \"mis solicitudes\".\n\n" +
            "Recuerde que para que su solicitud de admisión sea tramitada, debe adjuntar los documentos requeridos de manera completa y los mismos deben ser legibles.\n\n" +
            "--- Certificación ---\n\n" +
            "Antes de enviar su solicitud usted manifiesta:\n\n" +
            "Certifico que toda la información suministrada en el presente formulario es correcta y veraz; y entiendo que, una vez enviada la solicitud, no es posible modificar la informacion diligenciada.\n" +
            "Certifico y entiendo que UNIMINUTO no está obligado a aceptar la solicitud con el simple registro de mis datos en el presente formulario y que la admisión está condicionada a la verificación del cumplimiento de los requisitos legales e institucionales. En consecuencia, autorizo a UNIMINUTO a verificar toda la información.\n" +
            "Entiendo y acepto que, en caso de que la información aportada no corresponda a la realidad, UNIMINUTO revocará la admisión e iniciará las investigaciones disciplinarias o legales que apliquen.\n" +
            "Con la aceptación de la presente solicitud, declaro de manera libre y voluntaria que me encuentro afiliado al Sistema General de Seguridad Social en Salud y me comprometo a mantener vigente mi afiliación durante la duración de todo el periodo académico al cual me matriculo. En consecuencia, me obligo a aportar los soportes de afiliación cuando me sea requerido por UNIMINUTO.\n\n" +
            "Por lo anterior, mantendré indemne a UNIMINUTO y la libero de toda responsabilidad por cualquier daño o perjuicio causado con ocasión del incumplimiento de este deber legal a mi cargo.\n\n" +
            "--- Contrato de Matrícula * ---\n\n" +
            "Declaro en forma libre y voluntaria que he leído, he comprendido y por lo tanto acepto todas y cada una de las estipulaciones contenidas en el presente contrato matrícula y los demás documentos que forman parte integral del mismo, para el periodo académico al cual me estoy postulando. El presente contrato de matricula se entiende firmado con la presente aceptacion. Conozco que uniminuto no establece fecha limite para el cierre de los proecsos de admision, y que las inscripciones estan sunetas a la disponibilidad de cupos."
        );
        infoArea.setWrapStyleWord(true);
        infoArea.setLineWrap(true);
        infoArea.setEditable(false);
        infoArea.setFocusable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 12));
        infoArea.setBackground(new Color(240, 240, 240));
        infoArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane infoScrollPane = new JScrollPane(infoArea);
        infoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        infoScrollPane.setPreferredSize(new Dimension(0, 200)); // Altura fija para el panel de texto
        infoScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        infoScrollPane.setBorder(BorderFactory.createTitledBorder("Información Importante y Términos de Solicitud"));
        
        centerPanel.add(infoScrollPane);
        centerPanel.add(Box.createVerticalStrut(20)); // Espacio entre el texto y el formulario

        // Tipo de solicitud
        centerPanel.add(Box.createVerticalStrut(10));
        JComboBox<String> tipoSolicitud = new JComboBox<>(new String[]{
            "Seleccione tipo de solicitud...",
            "Certificado de Estudios",
            "Constancia de Matrícula",
            "Solicitud de Traslado",
            "Solicitud de Reingreso",
            "Certificado de Notas",
            "Solicitud de Grado",
            "Cambio de Programa",
            "Solicitud de Homologación",
            "Retiro Temporal",
            "Otros"
        });
        tipoSolicitud.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        tipoSolicitud.setBorder(BorderFactory.createTitledBorder("Tipo de Solicitud *"));
        centerPanel.add(tipoSolicitud);

        // Asunto
        centerPanel.add(Box.createVerticalStrut(15));
        JTextField asuntoField = new JTextField();
        asuntoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        asuntoField.setBorder(BorderFactory.createTitledBorder("Asunto *"));
        centerPanel.add(asuntoField);

        // Descripción/Justificación
        centerPanel.add(Box.createVerticalStrut(15));
        JLabel descLabel = new JLabel("Descripción/Justificación *");
        descLabel.setFont(new Font("Arial", Font.BOLD, 12));
        centerPanel.add(descLabel);
        
        JTextArea descripcionArea = new JTextArea(8, 50);
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        descripcionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Detalle su solicitud"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        JScrollPane descScrollPane = new JScrollPane(descripcionArea);
        descScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        centerPanel.add(descScrollPane);

        // Documentos adjuntos
        centerPanel.add(Box.createVerticalStrut(15));
        JPanel attachPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        attachPanel.setBackground(new Color(250, 245, 250));
        
        JLabel attachLabel = new JLabel("Documentos Adjuntos:");
        attachLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JButton attachBtn = new JButton("Seleccionar Archivos");
        attachBtn.setBackground(new Color(0, 38, 76));
        attachBtn.setForeground(Color.WHITE);
        attachBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        attachBtn.setFocusPainted(false);
        
        JLabel fileLabel = new JLabel("Ningún archivo seleccionado");
        fileLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        fileLabel.setForeground(Color.GRAY);
        
        attachBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                java.io.File[] files = fileChooser.getSelectedFiles();
                fileLabel.setText(files.length + " archivo(s) seleccionado(s)");
            }
        });
        
        attachPanel.add(attachLabel);
        attachPanel.add(attachBtn);
        attachPanel.add(fileLabel);
        centerPanel.add(attachPanel);

        // Información adicional
        centerPanel.add(Box.createVerticalStrut(15));
        JTextField contactoField = new JTextField();
        contactoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        contactoField.setBorder(BorderFactory.createTitledBorder("Teléfono de Contacto"));
        centerPanel.add(contactoField);

        centerPanel.add(Box.createVerticalStrut(10));
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        emailField.setBorder(BorderFactory.createTitledBorder("Email de Notificación"));
        centerPanel.add(emailField);

        // Checkbox de términos
        centerPanel.add(Box.createVerticalStrut(15));
        JCheckBox terminos = new JCheckBox("Acepto los términos y condiciones para el procesamiento de solicitudes");
        terminos.setBackground(new Color(250, 245, 250));
        terminos.setFont(new Font("Arial", Font.PLAIN, 12));
        centerPanel.add(terminos);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(250, 245, 250));
        
        JButton submitBtn = new JButton("Radicar Solicitud");
        submitBtn.setBackground(new Color(0, 38, 76));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        submitBtn.setFocusPainted(false);
        submitBtn.addActionListener(e -> {
            if (terminos.isSelected()) {
                // Recopilar datos del formulario
                String tipo = tipoSolicitud.getSelectedItem().toString();
                String asunto = asuntoField.getText();
                String descripcion = descripcionArea.getText();
                String telefono = contactoField.getText();
                String emailNotif = emailField.getText();
                String userEmail = DatabaseConnection.getCurrentUserEmail();
                // Guardar en base de datos
                DatabaseConnection db = new DatabaseConnection();
                boolean ok = db.insertRadicado(userEmail, tipo, asunto, descripcion, telefono, emailNotif);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Solicitud radicada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al radicar la solicitud.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe aceptar los términos y condiciones", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton backBtn = new JButton("Volver al Inicio");
        backBtn.setBackground(new Color(220, 220, 220));
        backBtn.setForeground(Color.BLACK);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> {
            this.dispose();
            new MainView();
        });
        buttonPanel.add(backBtn);

        JButton viewBtn = new JButton("Ver Mis Solicitudes");
        viewBtn.setBackground(new Color(0, 38, 76));
        viewBtn.setForeground(Color.WHITE);
        viewBtn.setFont(new Font("Arial", Font.BOLD, 14));
        viewBtn.setFocusPainted(false);
        viewBtn.addActionListener(e -> {
            String email = DatabaseConnection.getCurrentUserEmail();
            DefaultListModel<String> model = new DefaultListModel<>();
            try (PreparedStatement ps = new DatabaseConnection().getConnection()
                    .prepareStatement("SELECT id, tipo_solicitud, asunto, fecha_radicado FROM radicados WHERE email = ?")) {
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String item = "[" + rs.getInt("id") + "] " + rs.getString("tipo_solicitud")
                        + " - " + rs.getString("asunto")
                        + " (" + rs.getTimestamp("fecha_radicado") + ")";
                    model.addElement(item);
                }
            } catch (Exception ex) {
                model.addElement("Error: " + ex.getMessage());
            }
            JList<String> list = new JList<>(model);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setFont(new Font("Arial", Font.PLAIN, 14));
            JScrollPane pane = new JScrollPane(list);
            pane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(this, pane, "Mis Solicitudes", JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(viewBtn);

        buttonPanel.add(submitBtn);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(buttonPanel);

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}