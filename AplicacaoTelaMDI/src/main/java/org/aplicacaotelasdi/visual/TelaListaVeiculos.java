package org.aplicacaotelasdi.visual;

import org.aplicacaotelasdi.services.Database;
import org.aplicacaotelasdi.services.VeiculoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TelaListaVeiculos extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnEditar, btnExcluir;

    public TelaListaVeiculos(JFrame parent) {
        setTitle("Lista de Veículos");
        setSize(700, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        carregarVeiculos();
    }

    private void initComponents() {
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Placa", "Modelo", "Marca", "Ano"});

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
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
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
        String placa = (String) modelo.getValueAt(linha, 1);
        String modeloStr = (String) modelo.getValueAt(linha, 2);
        String marca = (String) modelo.getValueAt(linha, 3);
        int ano = (int) modelo.getValueAt(linha, 4);

        TelaCadastroVeiculo tela = new TelaCadastroVeiculo(this, id, placa, modeloStr, marca, ano);
        tela.setVisible(true);
        tela.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarVeiculos(); // Recarrega a tabela após editar
            }
        });
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
