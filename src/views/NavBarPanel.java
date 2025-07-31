package views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavBarPanel extends JPanel {
    
    public NavBarPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        initializeNavBar();
    }

    private void initializeNavBar() {
        JButton homeButton = new JButton("Home");
        JButton personalInfoButton = new JButton("Personal Info");
        JButton academicInfoButton = new JButton("Academic Info");
        JButton submitButton = new JButton("Submit");

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle home button action
            }
        });

        personalInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle personal info button action
            }
        });

        academicInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle academic info button action
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle submit button action
            }
        });

        add(homeButton);
        add(personalInfoButton);
        add(academicInfoButton);
        add(submitButton);
    }
}