package Telas;

import Database.Conexao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelaExibirDadosUsuario extends JFrame {

    public TelaExibirDadosUsuario(int usuarioId) {
        setTitle("Informações do Usuário");
        setSize(600, 550);  // Ajuste do tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());
        painel.setBackground(new Color(230, 230, 250)); // Cor de fundo clara
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel tituloLabel = new JLabel("Dados do Usuário");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tituloLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painel.add(tituloLabel, gbc);

        // Labels para exibir os dados
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        painel.add(nomeLabel, gbc);

        JTextField nomeField = new JTextField(20);
        nomeField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        painel.add(nomeField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        painel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        emailField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        painel.add(emailField, gbc);

        JLabel rgLabel = new JLabel("RG:");
        rgLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        painel.add(rgLabel, gbc);

        JTextField rgField = new JTextField(20);
        rgField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        painel.add(rgField, gbc);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        painel.add(cpfLabel, gbc);

        JTextField cpfField = new JTextField(20);
        cpfField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        painel.add(cpfField, gbc);

        JLabel dataNascimentoLabel = new JLabel("Data de Nascimento:");
        dataNascimentoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        painel.add(dataNascimentoLabel, gbc);

        JTextField dataNascimentoField = new JTextField(20);
        dataNascimentoField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 5;
        painel.add(dataNascimentoField, gbc);

        JLabel sexoLabel = new JLabel("Sexo:");
        sexoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        painel.add(sexoLabel, gbc);

        JTextField sexoField = new JTextField(20);
        sexoField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 6;
        painel.add(sexoField, gbc);

        JLabel nacionalidadeLabel = new JLabel("Nacionalidade:");
        nacionalidadeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 7;
        painel.add(nacionalidadeLabel, gbc);

        JTextField nacionalidadeField = new JTextField(20);
        nacionalidadeField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 7;
        painel.add(nacionalidadeField, gbc);

        JLabel cepLabel = new JLabel("CEP:");
        cepLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 8;
        painel.add(cepLabel, gbc);

        JTextField cepField = new JTextField(20);
        cepField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 8;
        painel.add(cepField, gbc);

        JLabel numeroTelefoneLabel = new JLabel("Telefone:");
        numeroTelefoneLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 9;
        painel.add(numeroTelefoneLabel, gbc);

        JTextField numeroTelefoneField = new JTextField(20);
        numeroTelefoneField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 9;
        painel.add(numeroTelefoneField, gbc);

        JLabel estadoCivilLabel = new JLabel("Estado Civil:");
        estadoCivilLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 10;
        painel.add(estadoCivilLabel, gbc);

        JTextField estadoCivilField = new JTextField(20);
        estadoCivilField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 10;
        painel.add(estadoCivilField, gbc);

        // Botão de Voltar
        JButton voltarButton = new JButton("Voltar");
        voltarButton.setFont(new Font("Arial", Font.BOLD, 14));
        voltarButton.setBackground(new Color(220, 53, 69));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        voltarButton.setFocusPainted(false);
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        painel.add(voltarButton, gbc);

        // Adicionando o painel à janela principal
        add(painel, BorderLayout.CENTER);

        // Carregar os dados do usuário a partir do banco de dados
        carregarDadosUsuario(usuarioId, nomeField, emailField, rgField, cpfField, dataNascimentoField,
                sexoField, nacionalidadeField, cepField, numeroTelefoneField, estadoCivilField);

        // Ação do botão "Voltar"
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaInicialEmbaixada().setVisible(true);
                dispose();  // Fecha a tela atual
            }
        });

        setVisible(true);
    }

    private void carregarDadosUsuario(int usuarioId, JTextField nomeField, JTextField emailField,
                                      JTextField rgField, JTextField cpfField, JTextField dataNascimentoField,
                                      JTextField sexoField, JTextField nacionalidadeField, JTextField cepField,
                                      JTextField numeroTelefoneField, JTextField estadoCivilField) {
        String query = "SELECT * FROM usuario WHERE idusuario = ?";
        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nomeField.setText(rs.getString("nomeusuario"));
                emailField.setText(rs.getString("email"));
                rgField.setText(rs.getString("rg"));
                cpfField.setText(rs.getString("cpf"));
                dataNascimentoField.setText(rs.getString("dataNascimento"));
                sexoField.setText(rs.getString("sexo"));
                nacionalidadeField.setText(rs.getString("nacionalidade"));
                cepField.setText(rs.getString("cep"));
                numeroTelefoneField.setText(rs.getString("numeroTelefone"));
                estadoCivilField.setText(rs.getString("estadoCivil"));
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar os dados.");
        }
    }

    public static void main(String[] args) {
        new TelaExibirDadosUsuario(1); // Passar o ID do usuário logado
    }
}
