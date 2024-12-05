package Telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicialEmbaixada extends JFrame {

    public TelaInicialEmbaixada() {
        setTitle("Tela Inicial - Embaixada");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Obter nome e nacionalidade do usuário logado
        String nomeUsuario = SessaoUsuario.getUsuarioLogado();
        String nacionalidade = SessaoUsuario.getNacionalidade();

        // Adiciona opções com base na nacionalidade
        if ("Brasileiro".equals(nacionalidade)) {
            criarOpcoesBrasileiro(nomeUsuario);
        } else {
            criarOpcoesEstrangeiro(nomeUsuario);
        }

        setVisible(true);
    }

    private void criarOpcoesBrasileiro(String nomeUsuario) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        add(panel, BorderLayout.CENTER);

        // Criando os botões estilizados
        JButton vistoButton = criarBotao("Solicitar Visto");
        JButton passaporteButton = criarBotao("Solicitar Passaporte");
        JButton consultaButton = criarBotao("Consultas de Documentos");
        JButton certificadoButton = criarBotao("Emitir Certificados");

        // Adicionando botões ao painel
        panel.add(passaporteButton);
        panel.add(vistoButton);
        panel.add(consultaButton);
        panel.add(certificadoButton);

        // Barra superior estilizada com saudação personalizada
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 123, 255)); // Azul mais suave
        JLabel titleLabel = new JLabel("Bem-vindo, " + nomeUsuario + " - Embaixada Brasileira", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Ação para o botão "Solicitar Visto"
        vistoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redireciona para a tela de solicitação de visto
                new TelaSolicitarVisto().setVisible(true);
                dispose();
            }
        });

        // Ação para o botão "Solicitar Passaporte"
        passaporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redireciona para a tela de solicitação de passaporte
                new TelaSolicitarPassaporte().setVisible(true);
                dispose();
            }
        });
    }

    private void criarOpcoesEstrangeiro(String nomeUsuario) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        add(panel, BorderLayout.CENTER);

        // Criando os botões estilizados
        JButton vistoButton = criarBotao("Solicitar Visto");
        JButton passaporteButton = criarBotao("Solicitar Passaporte");  // Adicionando Passaporte para estrangeiros
        JButton consultaButton = criarBotao("Consulta de Documentos");
        JButton infoConsularButton = criarBotao("Obter Informação Consular");

        // Adicionando botões ao painel
        panel.add(passaporteButton);
        panel.add(vistoButton);
        panel.add(consultaButton);
        panel.add(infoConsularButton);

        // Barra superior estilizada com saudação personalizada
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(220, 53, 69)); // Vermelho mais suave
        JLabel titleLabel = new JLabel("Bem-vindo, " + nomeUsuario + " - Embaixada Estrangeira", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Ação para o botão "Solicitar Visto"
        vistoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redireciona para a tela de solicitação de visto
                new TelaSolicitarVisto().setVisible(true);
                dispose();
            }
        });

        // Ação para o botão "Solicitar Passaporte" para estrangeiros
        passaporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redireciona para a tela de solicitação de passaporte
                new TelaSolicitarPassaporte().setVisible(true);
                dispose();
            }
        });
    }


    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.PLAIN, 16));
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(50, 150, 50));  // Verde mais suave
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(350, 60));  // Botões maiores e proporcionais

        return botao;
    }

    public static void main(String[] args) {
        new TelaInicialEmbaixada();
    }
}
