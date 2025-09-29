package org.aplicacaotelamdi.visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TelaPrincipalMDI extends JFrame {
    private JDesktopPane dskPaneAplicacaoMDI;
    private JMenuBar mnuBarTelaPrincipal;
    private JMenu mnuArquivo, mnuCadastro;
    private JMenuItem mnuCadastroVeiculo, mnuListarVeiculos, mnuSair;

    public TelaPrincipalMDI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Veículos - MDI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Ícone da janela
        setIconImage(criarIcone("/imagens/car_icon.png", 20, 20).getImage());

        // Cria o DesktopPane e define layout BorderLayout para centralizar o label
        dskPaneAplicacaoMDI = new JDesktopPane();
        dskPaneAplicacaoMDI.setLayout(null);

        JLabel boasVindasLabel = new JLabel("Bem-vindo ao Sistema de Veículos", SwingConstants.CENTER);
        boasVindasLabel.setFont(new Font("Arial", Font.BOLD, 24));
        boasVindasLabel.setForeground(Color.DARK_GRAY);

        // Define tamanho inicial (pode ser zero, vai ajustar depois)
        boasVindasLabel.setBounds(0, 0, 0, 30);

        dskPaneAplicacaoMDI.add(boasVindasLabel, JDesktopPane.DEFAULT_LAYER);

        // Ajusta o tamanho do label sempre que o desktop pane mudar de tamanho
        dskPaneAplicacaoMDI.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = dskPaneAplicacaoMDI.getWidth();
                int height = dskPaneAplicacaoMDI.getHeight();

                boasVindasLabel.setBounds(0, height/2 - 15, width, 30); // 30 altura, ajusta y para centralizar verticalmente
            }
        });

        setContentPane(dskPaneAplicacaoMDI);

        
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
    }

    private void abrirTelaCadastro() {
        
        TelaCadastroVeiculoMDI telaCadastroCarro = new TelaCadastroVeiculoMDI();
        dskPaneAplicacaoMDI.add(telaCadastroCarro);
        telaCadastroCarro.setVisible(true);

        telaCadastroCarro.toFront();

        try {
            telaCadastroCarro.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
        }

    }

    private void abrirTelaListagem() {
        TelaListaVeiculosMDI lista = new TelaListaVeiculosMDI();
        dskPaneAplicacaoMDI.add(lista);
        lista.setVisible(true);

        lista.toFront();

        try {
            lista.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
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
