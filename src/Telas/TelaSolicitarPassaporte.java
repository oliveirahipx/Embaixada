package Telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TelaSolicitarPassaporte extends JFrame {

    public TelaSolicitarPassaporte() {
        setTitle("Solicitar Passaporte");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        setLayout(new BorderLayout());

        // Barra superior estilizada
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 123, 255)); // Azul mais suave
        JLabel titleLabel = new JLabel("Solicitar Passaporte", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Painel central com formulário
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(new Color(240, 240, 240));
        add(formPanel, BorderLayout.CENTER);

        // Campos do formulário
        formPanel.add(new JLabel("Número do Passaporte:"));
        JTextField numeroPassaporteField = new JTextField();
        formPanel.add(numeroPassaporteField);

        formPanel.add(new JLabel("Data de Emissão:"));
        JTextField dataEmissaoField = new JTextField();
        formPanel.add(dataEmissaoField);

        formPanel.add(new JLabel("Autoridade Emissora:"));
        JTextField autoridadeEmissoraField = new JTextField();
        formPanel.add(autoridadeEmissoraField);

        // Botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton submitButton = new JButton("Solicitar Passaporte");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(50, 150, 50));
        submitButton.setPreferredSize(new Dimension(200, 50));
        buttonPanel.add(submitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Ação ao clicar no botão
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numeroPassaporte = numeroPassaporteField.getText();
                String dataEmissao = dataEmissaoField.getText();
                String autoridadeEmissora = autoridadeEmissoraField.getText();

                // Verificar se todos os campos foram preenchidos
                if (numeroPassaporte.isEmpty() || dataEmissao.isEmpty() || autoridadeEmissora.isEmpty()) {
                    JOptionPane.showMessageDialog(TelaSolicitarPassaporte.this, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Chama o método para salvar no banco de dados
                    salvarPassaporte(numeroPassaporte, dataEmissao, autoridadeEmissora);
                }
            }
        });

        setVisible(true);
    }

    // Método para salvar os dados do passaporte no banco de dados
    private void salvarPassaporte(String numeroPassaporte, String dataEmissao, String autoridadeEmissora) {
        try {
            // Conexão com o banco de dados
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/embaixadaBrasil", "root", "senha");

            // Obtendo o ID do usuário logado
            int idUsuario = SessaoUsuario.getIdUsuarioLogado(); // Supondo que tenha um método para pegar o id do usuário logado

            // Query SQL para inserir dados do passaporte
            String query = "INSERT INTO passaporte (numeroPassaporte, dataEmissao, autoridadeEmissora, idusuario) VALUES (?, ?, ?, ?)";

            // Preparando a consulta
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, numeroPassaporte);
            stmt.setString(2, dataEmissao);
            stmt.setString(3, autoridadeEmissora);
            stmt.setInt(4, idUsuario);

            // Executando a inserção no banco de dados
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Passaporte solicitado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                // Fechar a tela atual e voltar para a tela anterior ou inicial
                dispose();
            }

            // Fechar a conexão
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar passaporte: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new TelaSolicitarPassaporte();
    }
}
