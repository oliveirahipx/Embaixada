package Telas;

import Database.Conexao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroTela extends JFrame {

    public CadastroTela() {
        setTitle("Cadastro");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        add(panel, BorderLayout.CENTER);

        // Configuração do layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos e rótulos
        JLabel titleLabel = new JLabel("Cadastro de Usuário");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(new Color(50, 50, 150));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        JTextField nomeField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JTextField gmailField = new JTextField();
        JTextField telefoneField = new JTextField();
        JTextField rgField = new JTextField();
        JTextField cpfField = new JTextField();
        JComboBox<String> sexoComboBox = new JComboBox<>(new String[]{"Masculino", "Feminino", "Outro"});
        JTextField dataNascimentoField = new JTextField("DDMMYYYY");
        JTextField nacionalidadeField = new JTextField();
        JTextField cepField = new JTextField();
        JComboBox<String> estadoCivilComboBox = new JComboBox<>(new String[]{"Solteiro", "Casado", "Divorciado", "Viúvo"});

        // Adicionando campos ao painel
        addField(panel, gbc, "Nome:", nomeField, 1);
        addField(panel, gbc, "Senha:", senhaField, 2);
        addField(panel, gbc, "Gmail:", gmailField, 3);
        addField(panel, gbc, "Telefone:", telefoneField, 4);
        addField(panel, gbc, "RG:", rgField, 5);
        addField(panel, gbc, "CPF:", cpfField, 6);
        addField(panel, gbc, "Sexo:", sexoComboBox, 7);
        addField(panel, gbc, "Data de Nascimento:", dataNascimentoField, 8);
        addField(panel, gbc, "Nacionalidade:", nacionalidadeField, 9);
        addField(panel, gbc, "CEP:", cepField, 10);
        addField(panel, gbc, "Estado Civil:", estadoCivilComboBox, 11);

        // Botões
        JButton cadastrarButton = new JButton("Cadastrar");
        JButton loginRedirectButton = new JButton("Já possui login? Clique aqui");
        cadastrarButton.setBackground(new Color(50, 150, 50));
        cadastrarButton.setForeground(Color.WHITE);
        loginRedirectButton.setBackground(new Color(50, 50, 150));
        loginRedirectButton.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 1;
        panel.add(cadastrarButton, gbc);

        gbc.gridx = 1;
        panel.add(loginRedirectButton, gbc);

        // Ação para cadastrar no banco de dados
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String senha = new String(senhaField.getPassword());
                String email = gmailField.getText();
                String telefone = telefoneField.getText();
                String rg = rgField.getText();
                String cpf = cpfField.getText();
                String sexo = (String) sexoComboBox.getSelectedItem();
                String dataNascimento = dataNascimentoField.getText();
                String nacionalidade = nacionalidadeField.getText();
                String cep = cepField.getText();
                String estadoCivil = (String) estadoCivilComboBox.getSelectedItem();

                String dataFormatada;
                try {
                    if (!dataNascimento.matches("\\d{8}")) {
                        JOptionPane.showMessageDialog(null, "Data inválida! Use o formato DDMMYYYY.");
                        return;
                    }
                    dataFormatada = dataNascimento.substring(4, 8) + "-" +
                            dataNascimento.substring(2, 4) + "-" +
                            dataNascimento.substring(0, 2);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao formatar a data de nascimento.");
                    return;
                }

                try (Connection connection = Conexao.getConnection()) {
                    String sql = "INSERT INTO usuario (nomeusuario, senha, email, numeroTelefone, rg, cpf, sexo, dataNascimento, nacionalidade, cep, estadoCivil) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, nome);
                    statement.setString(2, senha);
                    statement.setString(3, email);
                    statement.setString(4, telefone);
                    statement.setString(5, rg);
                    statement.setString(6, cpf);
                    statement.setString(7, sexo);
                    statement.setString(8, dataFormatada);
                    statement.setString(9, nacionalidade);
                    statement.setString(10, cep);
                    statement.setString(11, estadoCivil);
                    statement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar no banco de dados.");
                }
            }
        });

        // Ação para redirecionar ao login
        loginRedirectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginTela().setVisible(true);
            }
        });

        setVisible(true);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(field, gbc);
    }

    public static void main(String[] args) {
        new CadastroTela();
    }
}
