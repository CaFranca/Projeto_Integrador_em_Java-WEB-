package org.aplicacaotelamdi.visual;

import org.aplicacaotelamdi.services.VeiculoDAO;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroVeiculoMDI extends JInternalFrame {
    private JTextField txtFabricante, txtModelo, txtCidade;
    private JButton btnSalvar, btnCancelar;

    private Integer idVeiculo = null; // Para edição

    public TelaCadastroVeiculoMDI() {
        setTitle("Cadastro de Veículo");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(420, 300);
        setVisible(true);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Fabricante*:"), gbc);
        gbc.gridx = 1;
        txtFabricante = criarTextFieldComPlaceholder("Ex: Toyota");
        panel.add(txtFabricante, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Modelo*:"), gbc);
        gbc.gridx = 1;
        txtModelo = criarTextFieldComPlaceholder("Ex: Corolla");
        panel.add(txtModelo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 1;
        txtCidade = criarTextFieldComPlaceholder("Ex: São Paulo");
        panel.add(txtCidade, gbc);
        row++;

        // Botões
        JPanel botoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnSalvar.setIcon(criarIcone("/imagens/save_icon.png", 20, 20));
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setIcon(criarIcone("/imagens/cancel_icon.png", 20, 20));

        btnCancelar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(e -> salvarVeiculo());
        botoes.add(btnSalvar);
        botoes.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(botoes, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private void salvarVeiculo() {
        String fabricanteStr = txtFabricante.getText().trim().toUpperCase();
        String modeloStr = txtModelo.getText().trim();
        String cidadeStr = txtCidade.getText().trim();

        if (fabricanteStr.equals("Ex: Toyota")) fabricanteStr = "";
        if (modeloStr.equals("Ex: Corolla")) modeloStr = "";
        if (cidadeStr.equals("Ex: São Paulo")) cidadeStr = "";

        if (fabricanteStr.isEmpty() || modeloStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean ok;
        if (idVeiculo == null) {
            ok = VeiculoDAO.salvar(fabricanteStr, modeloStr, cidadeStr);
        } else {
            ok = VeiculoDAO.atualizar(idVeiculo, fabricanteStr, modeloStr, cidadeStr);
        }

        if (ok) {
            JOptionPane.showMessageDialog(this, "Veículo salvo com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o veículo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTextField criarTextFieldComPlaceholder(String placeholder) {
        JTextField field = new JTextField(15);
        field.setForeground(Color.GRAY);
        field.setText(placeholder);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });

        return field;
    }

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
