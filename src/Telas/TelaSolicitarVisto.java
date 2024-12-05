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
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de conteúdo
        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());
        painel.setBackground(new Color(230, 230, 250));  // Cor de fundo leve e agradável
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Espaçamento entre os componentes

        // Título
        JLabel labelVisto = new JLabel("Solicitar Visto");
        labelVisto.setFont(new Font("Arial", Font.BOLD, 20));
        labelVisto.setForeground(new Color(60, 60, 60)); // Cor mais suave para o título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painel.add(labelVisto, gbc);

        // Campos de entrada
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nomeLabel.setForeground(new Color(60, 60, 60));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        painel.add(nomeLabel, gbc);

        JTextField nomeField = new JTextField(20);
        nomeField.setFont(new Font("Arial", Font.PLAIN, 14));
        nomeField.setBackground(Color.WHITE);
        nomeField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        gbc.gridx = 1;
        gbc.gridy = 1;
        painel.add(nomeField, gbc);

        JLabel passaporteLabel = new JLabel("Número do Passaporte:");
        passaporteLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passaporteLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 2;
        painel.add(passaporteLabel, gbc);

        JTextField numeroPassaporteField = new JTextField(20);
        numeroPassaporteField.setFont(new Font("Arial", Font.PLAIN, 14));
        numeroPassaporteField.setBackground(Color.WHITE);
        numeroPassaporteField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        gbc.gridx = 1;
        gbc.gridy = 2;
        painel.add(numeroPassaporteField, gbc);

        JLabel dataEntradaLabel = new JLabel("Data de Entrada (DD/MM/YYYY):");
        dataEntradaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dataEntradaLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 3;
        painel.add(dataEntradaLabel, gbc);

        JTextField dataEntradaField = new JTextField(10);
        dataEntradaField.setFont(new Font("Arial", Font.PLAIN, 14));
        dataEntradaField.setBackground(Color.WHITE);
        dataEntradaField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        gbc.gridx = 1;
        gbc.gridy = 3;
        painel.add(dataEntradaField, gbc);

        // Botão de Enviar
        JButton enviarButton = new JButton("Enviar Solicitação");
        enviarButton.setFont(new Font("Arial", Font.BOLD, 14));
        enviarButton.setBackground(new Color(70, 130, 180)); // Azul moderado
        enviarButton.setForeground(Color.WHITE);
        enviarButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        enviarButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        painel.add(enviarButton, gbc);

        // Adicionando o painel à janela principal
        add(painel, BorderLayout.CENTER);

        // Ação do botão "Enviar Solicitação"
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para enviar a solicitação de visto
                String nome = nomeField.getText();
                String passaporte = numeroPassaporteField.getText();
                String dataEntrada = dataEntradaField.getText();

                // Validação dos campos
                if (nome.isEmpty() || passaporte.isEmpty() || dataEntrada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                    return;
                }

                // Inserir dados nas tabelas do banco de dados
                try (Connection connection = Conexao.getConnection()) {
                    // 1. Inserir dados no passaporte
                    String insertPassaporteSQL = "INSERT INTO passaporte (numeroPassaporte, dataEmissao, autoridadeEmissora, idusuario) " +
                            "VALUES (?, NOW(), ?, (SELECT idusuario FROM usuario WHERE nomeusuario = ?))";
                    PreparedStatement passaporteStatement = connection.prepareStatement(insertPassaporteSQL);
                    passaporteStatement.setString(1, passaporte);
                    passaporteStatement.setString(2, "Embaixada");  // Isso pode ser um valor de input ou fixo
                    passaporteStatement.setString(3, nome);
                    passaporteStatement.executeUpdate();

                    // 2. Obter o ID do passaporte inserido
                    String selectPassaporteSQL = "SELECT idpassaporte FROM passaporte WHERE numeroPassaporte = ?";
                    PreparedStatement selectPassaporteStatement = connection.prepareStatement(selectPassaporteSQL);
                    selectPassaporteStatement.setString(1, passaporte);
                    var resultSet = selectPassaporteStatement.executeQuery();
                    if (!resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Erro ao obter ID do passaporte.");
                        return;
                    }
                    int passaporteId = resultSet.getInt("idpassaporte");

                    // 3. Inserir dados no visto
                    String insertVistoSQL = "INSERT INTO visto (tipo, paisesdestino, statusVisto, dataEmissao, dataValidade, idusuario, idpassaporte) " +
                            "VALUES (?, ?, ?, NOW(), ?, (SELECT idusuario FROM usuario WHERE nomeusuario = ?), ?)";
                    PreparedStatement vistoStatement = connection.prepareStatement(insertVistoSQL);
                    vistoStatement.setString(1, "Turismo");  // Tipo de visto
                    vistoStatement.setString(2, "Brasil");  // Países de destino
                    vistoStatement.setString(3, "Em Processamento");  // Status do visto
                    vistoStatement.setString(4, "2025-12-31");  // Data de validade do visto
                    vistoStatement.setString(5, nome);
                    vistoStatement.setInt(6, passaporteId);
                    vistoStatement.executeUpdate();

                    // Mostrar sucesso
                    JOptionPane.showMessageDialog(null, "Solicitação de visto enviada com sucesso!");
                    dispose();  // Fechar a tela de solicitação
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao processar a solicitação.");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaSolicitarVisto();
    }
}
