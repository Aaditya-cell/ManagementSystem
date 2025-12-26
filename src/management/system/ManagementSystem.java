package management.system;

import view.Login;

public class ManagementSystem {
    public static void main(String[] args) {
        // Set Look and Feel to Nimbus for the UI shown in your screenshots
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start the application with the Login screen
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}