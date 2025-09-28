package visual;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AplicacaoTelaSDI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            TelaPrincipalSDI frame = new TelaPrincipalSDI();
            frame.setVisible(true);
        });
    }
}
