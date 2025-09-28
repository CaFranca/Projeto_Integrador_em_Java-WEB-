package visual;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroVeiculoMDI extends JInternalFrame {
    private JTextField txtPlaca, txtModelo, txtMarca, txtAno;
    private JButton btnSalvar, btnCancelar;

    public TelaCadastroVeiculoMDI() {
        super("Cadastro de Veículo", true, true, true, true); // resizable, closable, maximizable, iconifiable
        setSize(420, 300);
        initComponents();
        setLocation(30, 30); // posição inicial; você pode centralizar dinamicamente se quiser
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Placa:"), gbc);
        gbc.gridx = 1; txtPlaca = new JTextField(15); panel.add(txtPlaca, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1; txtModelo = new JTextField(15); panel.add(txtModelo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1; txtMarca = new JTextField(15); panel.add(txtMarca, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1; txtAno = new JTextField(6); panel.add(txtAno, gbc);
        row++;

        JPanel botoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Fechar");
        btnCancelar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Veículo salvo (MDI)");
            dispose();
        });
        botoes.add(btnSalvar);
        botoes.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(botoes, gbc);

        add(panel, BorderLayout.CENTER);
    }
}
