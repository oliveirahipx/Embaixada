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

public class LoginTela extends JFrame {

    public LoginTela() {
        setTitle("Login");
        setSize(500, 400);
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

        // Título
        JLabel titleLabel = new JLabel("Login de Usuário");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(new Color(50, 50, 150));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Campos de login
        JTextField nomeField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        // Adicionando campos de login
        addField(panel, gbc, "Nome:", nomeField, 1);
        addField(panel, gbc, "Senha:", senhaField, 2);

        // Botões
        JButton loginButton = new JButton("Login");
        JButton cadastroRedirectButton = new JButton("Não possui cadastro? Clique aqui");
        loginButton.setBackground(new Color(50, 150, 50));
        loginButton.setForeground(Color.WHITE);
        cadastroRedirectButton.setBackground(new Color(50, 50, 150));
        cadastroRedirectButton.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        panel.add(cadastroRedirectButton, gbc);

        // Ação para redirecionar para a tela de cadastro
        cadastroRedirectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CadastroTela().setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String senha = new String(senhaField.getPassword());

                try (Connection connection = Conexao.getConnection()) {
                    String sql = "SELECT * FROM usuario WHERE nomeusuario = ? AND senha = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, nome);
                    statement.setString(2, senha);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        String nacionalidade = resultSet.getString("nacionalidade");
                        int idUsuario = resultSet.getInt("idusuario");  // Obtendo o ID do usuário
                        JOptionPane.showMessageDialog(null, "Login bem-sucedido!");

                        // Guardar o usuário logado para a sessão, incluindo o ID
                        SessaoUsuario.setUsuarioLogado(nome, nacionalidade, idUsuario);

                        // Redireciona para a tela inicial da embaixada
                        dispose();
                        new TelaInicialEmbaixada().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados.");
                }
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
        new LoginTela();
    }
}
