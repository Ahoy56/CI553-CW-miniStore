package DarkMode;

import javax.swing.*;
import java.awt.*;

public class Popup {

    /**
     * Method to show a popup window with a dark mode toggle
     */
    private static void showDarkModePopup() {
        // Create the popup dialog
        JDialog popup = new JDialog((JFrame) null, "Dark Mode Toggle", true);
        popup.setSize(300, 200);
        popup.setLayout(new BorderLayout());
        popup.setLocationRelativeTo(null); // Center the dialog

        // Create a panel to hold content
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE); // Initial background color

        // Add a label to display instructions
        JLabel label = new JLabel("Click the button to toggle Dark Mode!", SwingConstants.CENTER);
        label.setForeground(Color.BLACK); // Initial text color
        panel.add(label, BorderLayout.CENTER);

        // Create the toggle button
        JButton toggleButton = new JButton("Toggle Dark Mode");
        panel.add(toggleButton, BorderLayout.SOUTH);

        // Add action listener to toggle dark mode
        toggleButton.addActionListener(new java.awt.event.ActionListener() {
            private boolean isDarkMode = false;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (isDarkMode) {
                    // Switch to light mode
                    panel.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                    toggleButton.setText("Toggle Dark Mode");
                    isDarkMode = false;
                } else {
                    // Switch to dark mode
                    panel.setBackground(Color.DARK_GRAY);
                    label.setForeground(Color.WHITE);
                    toggleButton.setText("Toggle Light Mode");
                    isDarkMode = true;
                }
            }
        });

        // Add the panel to the popup
        popup.add(panel);
        popup.setVisible(true); // Display the popup
    }

    /**
     * Your main method where both windows are run
     */
    public static void main(String[] args) {
        // Existing application logic
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Main Application");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(400, 300);
            mainFrame.setLayout(new FlowLayout());
            mainFrame.add(new JLabel("Main Application Running!"));
            mainFrame.setVisible(true);

            // Run the popup in a new thread
            new Thread(() -> showDarkModePopup()).start();
        });
    }
}

