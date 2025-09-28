package org.aplicacaotelasdi.visual;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipalSDI extends JFrame {
    private JMenuBar mnuBarTelaPrincipal;
    private JMenu mnuArquivo, mnuCadastro;
    private JMenuItem mnuCadastroVeiculo;

    public TelaPrincipalSDI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Cadastro de Carros - SDI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Barra de menu
        mnuBarTelaPrincipal = new JMenuBar();
        mnuArquivo = new JMenu("Arquivo");
        mnuCadastro = new JMenu("Cadastro");

        mnuCadastroVeiculo = new JMenuItem("Cadastro de Veículo");
        java.net.URL iconUrl = getClass().getResource("/imagens/car_icon.png");
        if (iconUrl != null) {
            mnuCadastroVeiculo.setIcon(new ImageIcon(iconUrl));
        }
        // Atalho Ctrl+C (pode mudar se quiser outro)
        mnuCadastroVeiculo.setAccelerator(KeyStroke.getKeyStroke('C', java.awt.Event.CTRL_MASK));
        mnuCadastroVeiculo.addActionListener(e -> abrirTelaCadastro());

        mnuCadastro.add(mnuCadastroVeiculo);
        mnuBarTelaPrincipal.add(mnuArquivo);
        mnuBarTelaPrincipal.add(mnuCadastro);
        setJMenuBar(mnuBarTelaPrincipal);

        // Painel central simples
        JPanel painelCentral = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("<html><center>Sistema SDI - Use o menu Cadastro -> Cadastro de Veículo</center></html>", SwingConstants.CENTER);
        painelCentral.add(lbl, BorderLayout.CENTER);
        add(painelCentral);
    }

    private void abrirTelaCadastro() {
        TelaCadastroVeiculo tela = new TelaCadastroVeiculo(this);
        tela.setVisible(true);
    }
}
