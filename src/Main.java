import datos.Tienda;
import ui.LoginFrame;
import javax.swing.*;

/**
 * Punto de entrada del sistema StyleStore.
 * Ecommerce de ropa y accesorios — Java POO con Swing.
 */
public class Main {
    public static void main(String[] args) {
        // Aplicar Look and Feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Continúa con el L&F por defecto si falla
        }

        // Iniciar la aplicación en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            Tienda tienda = new Tienda();
            LoginFrame login = new LoginFrame(tienda);
            login.setVisible(true);
        });
    }
}
