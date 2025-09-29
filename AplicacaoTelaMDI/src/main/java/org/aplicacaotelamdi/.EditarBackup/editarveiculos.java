    private void editarVeiculo() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para editar.");
            return;
        }

        int id = (int) modelo.getValueAt(linha, 0);
        String fabricanteStr = (String) modelo.getValueAt(linha, 1);
        String modeloStr = (String) modelo.getValueAt(linha, 2);
        String cidadeStr = (String) modelo.getValueAt(linha, 3);

        TelaCadastroVeiculoMDI tela = new TelaCadastroVeiculoMDI(id, fabricanteStr, modeloStr, cidadeStr);
        getParent().add(tela);  // Garante que será adicionado ao JDesktopPane
        tela.setVisible(true);

        // Recarrega a tabela quando o formulário for fechado
        tela.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                carregarVeiculos();
            }
        });
    }

btnEditar = new JButton("Editar");
btnEditar.setIcon(criarIcone("/imagens/edit_icon.png", 20, 20));
btnEditar.addActionListener(e -> editarVeiculo());