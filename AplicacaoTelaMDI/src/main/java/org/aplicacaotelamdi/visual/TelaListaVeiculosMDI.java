package org.aplicacaotelamdi.visual;

import org.aplicacaotelamdi.services.Database;
import org.aplicacaotelamdi.services.VeiculoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TelaListaVeiculosMDI extends JInternalFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnEditar, btnExcluir;

    public TelaListaVeiculosMDI() {
        setTitle("Lista de Veículos");
        setSize(700, 450);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setVisible(true);

        initComponents();
        carregarVeiculos();
    }

    private void initComponents() {
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Fabricante", "Modelo", "Cidade", "Ano"});

        
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        // Painel inferior com botões
        JPanel painelBotoes = new JPanel();

        btnEditar = new JButton("Editar");
        btnEditar.setIcon(criarIcone("/imagens/edit_icon.png", 20, 20));
        btnEditar.addActionListener(e -> editarVeiculo());

        btnExcluir = new JButton("Excluir");
        btnExcluir.setIcon(criarIcone("/imagens/delete_icon.png", 20, 20));
        btnExcluir.addActionListener(e -> excluirVeiculo());

        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarVeiculos() {
        String sql = "SELECT * FROM veiculos";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            modelo.setRowCount(0);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("fabricante"),
                        rs.getString("modelo"),
                        rs.getString("cidade"),
                        rs.getInt("ano")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar veículos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarVeiculo() {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um veículo para editar.");
                return;
            }

            int id = (int) modelo.getValueAt(linha, 0);
            String fabricante = (String) modelo.getValueAt(linha, 1);
            String modeloStr = (String) modelo.getValueAt(linha, 2);
            String cidade = (String) modelo.getValueAt(linha, 3);
            int ano = (int) modelo.getValueAt(linha, 4);

            TelaCadastroVeiculoMDI tela = new TelaCadastroVeiculoMDI(id, fabricante, modeloStr, cidade, ano);
            
            JDesktopPane desktopPane = this.getDesktopPane(); // pega o desktop pane onde o internal frame está

            if (desktopPane != null) {
                    desktopPane.add(tela);
                    tela.setVisible(true);

                    tela.toFront();
                    try {
                        tela.setSelected(true);
                    } catch (java.beans.PropertyVetoException ex) {
                        ex.printStackTrace();
                    }

                    tela.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                        @Override
                        public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                            carregarVeiculos(); // Recarrega a tabela após fechar o internal frame
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "Erro: não foi possível obter o DesktopPane.");
                }
            
        }

        private void excluirVeiculo() {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um veículo para excluir.");
                return;
            }

            int id = (int) modelo.getValueAt(linha, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir este veículo?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (VeiculoDAO.excluir(id)) {
                    modelo.removeRow(linha);
                    JOptionPane.showMessageDialog(this, "Veículo excluído com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir veículo.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    // Função para criar ícones redimensionados
    private ImageIcon criarIcone(String caminho, int largura, int altura) {
        java.net.URL url = getClass().getResource(caminho);
        if (url != null) {
            Image img = new ImageIcon(url).getImage();
            Image imgRedimensionada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            return new ImageIcon(imgRedimensionada);
        }
        return null;
    }
}
