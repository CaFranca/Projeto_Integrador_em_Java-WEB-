package org.aplicacaotelasdi.visual;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipalSDI extends JFrame {
    private JMenuBar mnuBarTelaPrincipal;
    private JMenu mnuArquivo, mnuCadastro;
    private JMenuItem mnuCadastroVeiculo, mnuListarVeiculos, mnuSair;

    public TelaPrincipalSDI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Veículos - SDI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Ícone da janela
        setIconImage(criarIcone("/imagens/car_icon.png", 20, 20).getImage());

        // Barra de menu
        mnuBarTelaPrincipal = new JMenuBar();
        mnuArquivo = new JMenu("Arquivo");
        mnuCadastro = new JMenu("Cadastro");

        // Item Cadastro de Veículo
        mnuCadastroVeiculo = new JMenuItem("Cadastro de Veículo");
        mnuCadastroVeiculo.setIcon(criarIcone("/imagens/car_icon.png", 20, 20));
        mnuCadastroVeiculo.setAccelerator(KeyStroke.getKeyStroke('C', java.awt.Event.CTRL_MASK | java.awt.Event.SHIFT_MASK));
        mnuCadastroVeiculo.addActionListener(e -> abrirTelaCadastro());

        // Item Listar Veículos
        mnuListarVeiculos = new JMenuItem("Listar Veículos");
        mnuListarVeiculos.setIcon(criarIcone("/imagens/list_icon.png", 20, 20));
        mnuListarVeiculos.addActionListener(e -> abrirTelaListagem());

        // Item Sair
        mnuSair = new JMenuItem("Sair");
        mnuSair.addActionListener(e -> System.exit(0));

        // Monta menus
        mnuCadastro.add(mnuCadastroVeiculo);
        mnuCadastro.add(mnuListarVeiculos);
        mnuArquivo.add(mnuSair);

        mnuBarTelaPrincipal.add(mnuArquivo);
        mnuBarTelaPrincipal.add(mnuCadastro);
        setJMenuBar(mnuBarTelaPrincipal);

        // Painel central com instruções
        JPanel painelCentral = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel(
                "<html><center>Sistema SDI - Use o menu Cadastro -> Cadastro de Veículo ou Listar Veículos</center></html>",
                SwingConstants.CENTER
        );
        painelCentral.add(lbl, BorderLayout.CENTER);
        add(painelCentral);
    }

    private void abrirTelaCadastro() {
        TelaCadastroVeiculo tela = new TelaCadastroVeiculo(this);
        tela.setVisible(true);
    }

    private void abrirTelaListagem() {
        TelaListaVeiculos lista = new TelaListaVeiculos(this);
        lista.setVisible(true);
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
