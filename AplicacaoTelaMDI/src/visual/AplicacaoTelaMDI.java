package visual;

import javax.swing.*;

public class AplicacaoTelaMDI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            TelaPrincipalMDI frame = new TelaPrincipalMDI();
            frame.setVisible(true);
        });
    }
}
