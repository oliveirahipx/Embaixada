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

        // Criar painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        add(panel, BorderLayout.CENTER);

        // Barra superior com os JComboBox e saudação
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(0, 123, 255));

        // Saudação personalizada
        JLabel titleLabel = new JLabel("Bem-vindo, " + nomeUsuario + " - Embaixada");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        // Primeiro JComboBox com opções específicas
        String[] opcoes;
        if ("Brasileiro".equals(nacionalidade)) {
            opcoes = new String[]{
                    "Solicitar Visto",
                    "Solicitar Passaporte",
                    "Exibir Documentos",
                    "Exibir informações pessoais"
            };
        } else {
            opcoes = new String[]{
                    "Solicitar Visto",
                    "Solicitar Passaporte",
                    "Exibir Documentos",
                    "Exibir informações pessoais"
            };
        }

        JComboBox<String> comboBox = new JComboBox<>(opcoes);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));

        // Segundo JComboBox com opções adicionais
        String[] menuOpcoes = {"Menu", "Logout", "Suporte"};
        JComboBox<String> menuComboBox = new JComboBox<>(menuOpcoes);
        menuComboBox.setFont(new Font("Arial", Font.PLAIN, 16));

        // Adiciona componentes ao painel superior
        topPanel.add(comboBox);
        topPanel.add(menuComboBox);
        topPanel.add(Box.createHorizontalStrut(20)); // Espaçamento
        topPanel.add(titleLabel);

        add(topPanel, BorderLayout.NORTH);

        // Botão Confirmar
        JButton confirmarButton = new JButton("Confirmar");
        confirmarButton.setFont(new Font("Arial", Font.PLAIN, 18));
        confirmarButton.setBackground(new Color(50, 150, 50));
        confirmarButton.setForeground(Color.WHITE);
        confirmarButton.setFocusPainted(false);
        confirmarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(100));
        panel.add(confirmarButton);

        // Ação para o botão "Confirmar"
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcaoSelecionada = (String) comboBox.getSelectedItem();

                switch (opcaoSelecionada) {
                    case "Solicitar Visto":
                        new TelaSolicitarVisto().setVisible(true);
                        dispose();
                        break;
                    case "Solicitar Passaporte":
                        new TelaSolicitarPassaporte().setVisible(true);
                        dispose();
                        break;
                    case "Exibir Documentos":
                        new TelaExibirDocumentos().setVisible(true);
                        dispose();
                        break;
                    case "Exibir informações pessoais":
                        new TelaExibirDadosUsuario(SessaoUsuario.getIdUsuarioLogado()).setVisible(true);
                        dispose();
                        break;


                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida.");
                        break;
                }
            }
        });

        // Ação para o segundo JComboBox (Logout e Suporte)
        menuComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) menuComboBox.getSelectedItem();
                if ("Logout".equals(selectedOption)) {
                    JOptionPane.showMessageDialog(null, "Você foi desconectado.");
                    dispose();
                    new LoginTela().setVisible(true);


                } else if ("Suporte".equals(selectedOption)) {
                    JOptionPane.showMessageDialog(null, "Contate o suporte pelo email: suporte@embaixada.com");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaInicialEmbaixada();
    }
}
