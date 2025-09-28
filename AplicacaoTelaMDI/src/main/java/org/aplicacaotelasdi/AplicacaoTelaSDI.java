package org.aplicacaotelasdi;

import org.aplicacaotelasdi.services.VeiculoDAO;
import org.aplicacaotelasdi.visual.TelaPrincipalSDI;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AplicacaoTelaSDI {
    public static void main(String[] args) {
        // Cria a tabela veiculos no SQLite se ainda não existir
        VeiculoDAO.criarTabela();

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            TelaPrincipalSDI frame = new TelaPrincipalSDI();
            frame.setVisible(true);
        });
    }
}
