import datos.Tienda;
import ui.LoginFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        SwingUtilities.invokeLater(() -> {
            Tienda tienda = new Tienda();
            LoginFrame login = new LoginFrame(tienda);
            login.setVisible(true);
        });
    }
}
