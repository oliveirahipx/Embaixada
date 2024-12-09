package Telas;

import Database.Conexao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TelaSolicitarVisto extends JFrame {

    public TelaSolicitarVisto() {
        setTitle("Solicitar Visto");
        setSize(500, 450);  // Ajuste do tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de conteúdo
        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());
        painel.setBackground(new Color(230, 230, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel labelVisto = new JLabel("Solicitar Visto");
        labelVisto.setFont(new Font("Arial", Font.BOLD, 20));
        labelVisto.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painel.add(labelVisto, gbc);

        // Campos de entrada
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        painel.add(nomeLabel, gbc);

        JTextField nomeField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        painel.add(nomeField, gbc);

        JLabel passaporteLabel = new JLabel("Número do Passaporte:");
        passaporteLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        painel.add(passaporteLabel, gbc);

        JTextField numeroPassaporteField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        painel.add(numeroPassaporteField, gbc);

        JLabel dataEntradaLabel = new JLabel("Data de Entrada (DD/MM/YYYY):");
        dataEntradaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        painel.add(dataEntradaLabel, gbc);

        JTextField dataEntradaField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        painel.add(dataEntradaField, gbc);

        // Botão de Enviar
        JButton enviarButton = new JButton("Enviar Solicitação");
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

        // Ação do botão "Enviar Solicitação"
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String passaporte = numeroPassaporteField.getText();
                String dataEntrada = dataEntradaField.getText();

                if (nome.isEmpty() || passaporte.isEmpty() || dataEntrada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                    return;
                }

                try (Connection connection = Conexao.getConnection()) {
                    String insertPassaporteSQL = "INSERT INTO passaporte (numeroPassaporte, dataEmissao, autoridadeEmissora, idusuario) " +
                            "VALUES (?, NOW(), ?, (SELECT idusuario FROM usuario WHERE nomeusuario = ?))";
                    PreparedStatement passaporteStatement = connection.prepareStatement(insertPassaporteSQL);
                    passaporteStatement.setString(1, passaporte);
                    passaporteStatement.setString(2, "Embaixada");
                    passaporteStatement.setString(3, nome);
                    passaporteStatement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Solicitação de visto enviada com sucesso!");
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
        new TelaSolicitarVisto();
    }
}
