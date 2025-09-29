package org.aplicacaotelamdi.visual;

import org.aplicacaotelamdi.services.VeiculoDAO;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroVeiculoMDI extends JInternalFrame {
    private JTextField txtFabricante, txtModelo, txtCidade, txtAno;
    private JButton btnSalvar, btnCancelar;
    private Integer idVeiculo = null; // null = novo, diferente de null = edição

    // ===========================
    // CONSTRUTOR PARA CADASTRO
    // ===========================
    public TelaCadastroVeiculoMDI() {
        setTitle("Cadastro de Veículo");
        configurarJanela();
        initComponents();
    }

    // ===========================
    // CONSTRUTOR PARA EDIÇÃO
    // ===========================
    public TelaCadastroVeiculoMDI(Integer id, String fabricante, String modelo, String cidade, Integer ano) {
        this.idVeiculo = id;
        setTitle("Editar Veículo - ID " + id);
        configurarJanela();
        initComponents();

        txtFabricante.setForeground(Color.BLACK);
        txtFabricante.setText(fabricante);

        txtModelo.setForeground(Color.BLACK);
        txtModelo.setText(modelo);

        txtCidade.setForeground(Color.BLACK);
        txtCidade.setText(cidade);

        if (ano != null && ano > 0) {
            txtAno.setForeground(Color.BLACK);
            txtAno.setText(String.valueOf(ano));
        }
    }

    // ===========================
    // CONFIGURAÇÃO DA JANELA
    // ===========================
    private void configurarJanela() {
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(420, 350);
        setVisible(true);

        
    }

    // ===========================
    // COMPONENTES DA JANELA
    // ===========================
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Fabricante
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Fabricante*:"), gbc);
        gbc.gridx = 1;
        txtFabricante = criarTextFieldComPlaceholder("Ex: Toyota");
        panel.add(txtFabricante, gbc);
        row++;

        // Modelo
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Modelo*:"), gbc);
        gbc.gridx = 1;
        txtModelo = criarTextFieldComPlaceholder("Ex: Corolla");
        panel.add(txtModelo, gbc);
        row++;

        // Cidade
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 1;
        txtCidade = criarTextFieldComPlaceholder("Ex: São Paulo");
        panel.add(txtCidade, gbc);
        row++;

        // Ano (opcional)
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1;
        txtAno = criarTextFieldComPlaceholder("Ex: 2023");
        panel.add(txtAno, gbc);
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

    // ===========================
    // MÉTODO PARA SALVAR OU ATUALIZAR
    // ===========================
    private void salvarVeiculo() {
        String fabricanteStr = txtFabricante.getText().trim().toUpperCase();
        String modeloStr = txtModelo.getText().trim();
        String cidadeStr = txtCidade.getText().trim();
        String anoStr = txtAno.getText().trim();

        if (fabricanteStr.equals("Ex: Toyota")) fabricanteStr = "";
        if (modeloStr.equals("Ex: Corolla")) modeloStr = "";
        if (cidadeStr.equals("Ex: São Paulo")) cidadeStr = "";
        int ano = 0;

        if (!anoStr.isEmpty()) {
            try {
                ano = Integer.parseInt(anoStr);
                int anoAtual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                if (ano < 1900 || ano > anoAtual) {
                    JOptionPane.showMessageDialog(this, "Ano inválido. Informe um valor entre 1900 e " + anoAtual + ".", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ano deve ser um número válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        if (fabricanteStr.isEmpty() || modeloStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean ok;
        if (idVeiculo == null) {
            ok = VeiculoDAO.salvar(fabricanteStr, modeloStr, cidadeStr, ano);
        } else {
            ok = VeiculoDAO.atualizar(idVeiculo, fabricanteStr, modeloStr, cidadeStr, ano);
        }

        if (ok) {
            JOptionPane.showMessageDialog(this, "Veículo salvo com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o veículo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===========================
    // PLACEHOLDER
    // ===========================
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

    // ===========================
    // ÍCONES REDIMENSIONADOS
    // ===========================
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
