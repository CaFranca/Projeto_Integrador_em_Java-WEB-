package org.aplicacaotelasdi.visual;

import org.aplicacaotelasdi.services.VeiculoDAO;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class TelaCadastroVeiculo extends JFrame {
    private JTextField txtPlaca, txtModelo, txtMarca, txtAno;
    private JButton btnSalvar, btnCancelar;

    private static final Pattern PLACA_PATTERN = Pattern.compile("^[A-Z]{3}\\d{1}[A-Z]{1}\\d{2}$|^[A-Z]{3}\\d{4}$");

    private Integer idVeiculo = null; // Para edição

    // Construtor para cadastro novo
    public TelaCadastroVeiculo(JFrame parent) {
        setTitle("Cadastro de Veículo");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(420, 300);
        setLocationRelativeTo(parent);
        initComponents();
    }

    // Construtor para edição
    public TelaCadastroVeiculo(JFrame parent, int id, String placa, String modeloStr, String marca, int ano) {
        this(parent);
        this.idVeiculo = id;
        txtPlaca.setText(placa);
        txtModelo.setText(modeloStr);
        txtMarca.setText(marca);
        txtAno.setText(String.valueOf(ano));
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Campos com placeholders
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Placa*:"), gbc);
        gbc.gridx = 1;
        txtPlaca = criarTextFieldComPlaceholder("Ex: ABC1234");
        panel.add(txtPlaca, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Modelo*:"), gbc);
        gbc.gridx = 1;
        txtModelo = criarTextFieldComPlaceholder("Ex: Corolla");
        panel.add(txtModelo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1;
        txtMarca = criarTextFieldComPlaceholder("Ex: Toyota");
        panel.add(txtMarca, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1;
        txtAno = criarTextFieldComPlaceholder("Ex: 2020");
        panel.add(txtAno, gbc);
        row++;

        // Botões com ícones
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
        String placa = txtPlaca.getText().trim().toUpperCase();
        String modeloStr = txtModelo.getText().trim();
        String marca = txtMarca.getText().trim();
        String anoStr = txtAno.getText().trim();

        // Remove placeholders antes de validar
        if (placa.equals("Ex: ABC1234")) placa = "";
        if (modeloStr.equals("Ex: Corolla")) modeloStr = "";
        if (marca.equals("Ex: Toyota")) marca = "";
        if (anoStr.equals("Ex: 2020")) anoStr = "";

        if (placa.isEmpty() || modeloStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios: Placa e Modelo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!PLACA_PATTERN.matcher(placa).matches()) {
            JOptionPane.showMessageDialog(this, "Placa inválida. Use o formato ABC1234 ou ABC1D23.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

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

        boolean ok;
        if (idVeiculo == null) {
            ok = VeiculoDAO.salvar(placa, modeloStr, marca, ano);
        } else {
            ok = VeiculoDAO.atualizar(idVeiculo, placa, modeloStr, marca, ano);
        }

        if (ok) {
            JOptionPane.showMessageDialog(this, "Veículo salvo com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o veículo. Veja o console para detalhes.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Cria JTextField com placeholder
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
