package org.aplicacaotelamdi.EditarBackup;

import org.aplicacaotelamdi.visual.TelaCadastroVeiculoMDI;

import javax.swing.*;

public class EditarVeiculo {
    private JTable tabela;
    private javax.swing.table.DefaultTableModel modelo;

    public void editarVeiculo() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um veículo para editar.");
            return;
        }

        int id = (int) modelo.getValueAt(linha, 0);
        String fabricanteStr = (String) modelo.getValueAt(linha, 1);
        String modeloStr = (String) modelo.getValueAt(linha, 2);
        String cidadeStr = (String) modelo.getValueAt(linha, 3);

        // Obter ano, se existir
        Integer ano = null;
        Object valorAno = modelo.getValueAt(linha, 4);
        if (valorAno != null && !valorAno.toString().isEmpty()) {
            ano = Integer.parseInt(valorAno.toString());
        }

        // Cria a tela de edição usando 5 parâmetros
        TelaCadastroVeiculoMDI tela = new TelaCadastroVeiculoMDI(id, fabricanteStr, modeloStr, cidadeStr, ano);

        JDesktopPane desktop = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, tabela);

        if (desktop != null) {
            desktop.add(tela);
            tela.setVisible(true);
            try {
                tela.setSelected(true);
            } catch (java.beans.PropertyVetoException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro: não encontrou o JDesktopPane!");
        }

        tela.addInternalFrameListener(new FrameFecharListener(this));
    }


    // Classe interna que trata o fechamento da tela de edição
    private static class FrameFecharListener extends javax.swing.event.InternalFrameAdapter {
        private final EditarVeiculo parent;

        public FrameFecharListener(EditarVeiculo parent) {
            this.parent = parent;
        }

        @Override
        public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
            parent.carregarVeiculos();
        }
    }

    // Método que recarrega a tabela (implementar de acordo com seu DAO)
    private void carregarVeiculos() {
        // TODO: implemente para atualizar os dados na JTable
    }
}
