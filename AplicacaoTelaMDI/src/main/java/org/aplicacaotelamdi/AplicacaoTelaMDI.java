package org.aplicacaotelamdi;

import org.aplicacaotelamdi.services.VeiculoDAO;
import org.aplicacaotelamdi.visual.TelaPrincipalMDI;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AplicacaoTelaMDI {
    public static void main(String[] args) {
        // Cria a tabela veiculos no SQLite se ainda não existir
        VeiculoDAO.criarTabela();

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Erro ao aplicar o Look and Feel:");
                e.printStackTrace();
            }

            TelaPrincipalMDI frame = new TelaPrincipalMDI();
            frame.setVisible(true);
        });
    }
}
