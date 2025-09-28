package org.aplicacaotelasdi.visual;

import org.aplicacaotelasdi.services.VeiculoDAO;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class TelaCadastroVeiculo extends JFrame {
    private JTextField txtPlaca, txtModelo, txtMarca, txtAno;
    private JButton btnSalvar, btnCancelar;

    // Regex simples para placas brasileiras (ABC1234 ou ABC1D23)
    private static final Pattern PLACA_PATTERN = Pattern.compile("^[A-Z]{3}\\d{1}[A-Z]{1}\\d{2}$|^[A-Z]{3}\\d{4}$");

    public TelaCadastroVeiculo(JFrame parent) {
        setTitle("Cadastro de Veículo");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // NÃO fecha a aplicação principal
        setSize(420, 300);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Placa*:"), gbc);
        gbc.gridx = 1;
        txtPlaca = new JTextField(15);
        panel.add(txtPlaca, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Modelo*:"), gbc);
        gbc.gridx = 1;
        txtModelo = new JTextField(15);
        panel.add(txtModelo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1;
        txtMarca = new JTextField(15);
        panel.add(txtMarca, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1;
        txtAno = new JTextField(6);
        panel.add(txtAno, gbc);
        row++;

        JPanel botoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
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
        String modelo = txtModelo.getText().trim();
        String marca = txtMarca.getText().trim();
        String anoStr = txtAno.getText().trim();

        // Validação obrigatória
        if (placa.isEmpty() || modelo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios: Placa e Modelo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validação de placa
        if (!PLACA_PATTERN.matcher(placa).matches()) {
            JOptionPane.showMessageDialog(this, "Placa inválida. Use o formato ABC1234 ou ABC1D23.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validação do ano
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

        boolean ok = VeiculoDAO.salvar(placa, modelo, marca, ano);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Veículo salvo com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o veículo. Veja o console para detalhes.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
