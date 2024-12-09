package Telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TelaSolicitarPassaporte extends JFrame {

    public TelaSolicitarPassaporte() {
        setTitle("Solicitar Passaporte");
        setSize(500, 450);  // Ajuste do tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de conteúdo
        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());
        painel.setBackground(new Color(230, 230, 250));  // Cor de fundo clara
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel labelPassaporte = new JLabel("Solicitar Passaporte");
        labelPassaporte.setFont(new Font("Arial", Font.BOLD, 20));
        labelPassaporte.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painel.add(labelPassaporte, gbc);

        // Campos de entrada
        JLabel numeroPassaporteLabel = new JLabel("Número do Passaporte:");
        numeroPassaporteLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        painel.add(numeroPassaporteLabel, gbc);

        JTextField numeroPassaporteField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        painel.add(numeroPassaporteField, gbc);

        JLabel dataEmissaoLabel = new JLabel("Data de Emissão:");
        dataEmissaoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        painel.add(dataEmissaoLabel, gbc);

        JTextField dataEmissaoField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        painel.add(dataEmissaoField, gbc);

        JLabel autoridadeEmissoraLabel = new JLabel("Autoridade Emissora:");
        autoridadeEmissoraLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        painel.add(autoridadeEmissoraLabel, gbc);

        JTextField autoridadeEmissoraField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        painel.add(autoridadeEmissoraField, gbc);

        // Botão de Enviar Solicitação
        JButton enviarButton = new JButton("Solicitar Passaporte");
        enviarButton.setFont(new Font("Arial", Font.BOLD, 14));
        enviarButton.setBackground(new Color(70, 130, 180));
        enviarButton.setForeground(Color.WHITE);
        enviarButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        enviarButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        painel.add(enviarButton, gbc);

        // Botão de Voltar para o Menu Principal
        JButton voltarButton = new JButton("Voltar para o Menu Principal");
        voltarButton.setFont(new Font("Arial", Font.BOLD, 14));
        voltarButton.setBackground(new Color(220, 53, 69));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        voltarButton.setFocusPainted(false);
        gbc.gridy = 5;
        painel.add(voltarButton, gbc);

        // Adicionando o painel à janela principal
        add(painel, BorderLayout.CENTER);

        // Ação do botão "Solicitar Passaporte"
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numeroPassaporte = numeroPassaporteField.getText();
                String dataEmissao = dataEmissaoField.getText();
                String autoridadeEmissora = autoridadeEmissoraField.getText();

                if (numeroPassaporte.isEmpty() || dataEmissao.isEmpty() || autoridadeEmissora.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                    return;
                }

                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/embaixadaBrasil", "root", "senha")) {
                    String insertPassaporteSQL = "INSERT INTO passaporte (numeroPassaporte, dataEmissao, autoridadeEmissora, idusuario) " +
                            "VALUES (?, ?, ?, (SELECT idusuario FROM usuario WHERE nomeusuario = ?))";
                    PreparedStatement passaporteStatement = connection.prepareStatement(insertPassaporteSQL);
                    passaporteStatement.setString(1, numeroPassaporte);
                    passaporteStatement.setString(2, dataEmissao);
                    passaporteStatement.setString(3, autoridadeEmissora);
                    passaporteStatement.setString(4, "NomeUsuario"); // Substituir pelo nome do usuário logado
                    passaporteStatement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Solicitação de passaporte enviada com sucesso!");
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao processar a solicitação.");
                }
            }
        });

        // Ação do botão "Voltar para o Menu Principal"
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaInicialEmbaixada().setVisible(true);  // Abre a tela inicial
                dispose();  // Fecha a tela atual
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaSolicitarPassaporte();
    }
}
