package visual;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class TelaPrincipalMDI extends JFrame {
    private JMenuBar mnuBarTelaPrincipal;
    private JMenu mnuArquivo, mnuCadastro;
    private JMenuItem mnuCadastroVeiculo;

    public TelaPrincipalMDI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Cadastro de Carros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null); // centraliza a janela (conforme PDF). :contentReference[oaicite:3]{index=3}

        // Menu bar
        mnuBarTelaPrincipal = new JMenuBar();
        mnuArquivo = new JMenu("Arquivo");
        mnuCadastro = new JMenu("Cadastro");

        // Menu item "Cadastro de Veículo"
        mnuCadastroVeiculo = new JMenuItem("Cadastro de Veículo");
        // carrega ícone (coloque resources/imagens/car_icon.png)
        java.net.URL iconUrl = getClass().getResource("/imagens/car_icon.png");
        if (iconUrl != null) {
            mnuCadastroVeiculo.setIcon(new ImageIcon(iconUrl));
        }
        // atalho Ctrl+C (exemplo)
        mnuCadastroVeiculo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        mnuCadastroVeiculo.addActionListener(e -> abrirTelaCadastro());

        mnuCadastro.add(mnuCadastroVeiculo);
        mnuBarTelaPrincipal.add(mnuArquivo);
        mnuBarTelaPrincipal.add(mnuCadastro);
        setJMenuBar(mnuBarTelaPrincipal);
    }

    private void abrirTelaCadastro() {
        // abre uma janela nova (SDI) — quando fechar, apenas ela será descartada.
        TelaCadastroVeiculo tela = new TelaCadastroVeiculo(this);
        tela.setVisible(true);
    }
}
