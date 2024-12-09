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

public class TelaExibirDocumentos extends JFrame {

    private JTextArea textArea;

    public TelaExibirDocumentos() {
        setTitle("Exibir Documentos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Seus Documentos Cadastrados", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Adiciona botão "Voltar"
        JButton voltarButton = new JButton("Voltar para o Menu");
        voltarButton.setFont(new Font("Arial", Font.PLAIN, 16));
        voltarButton.setBackground(new Color(0, 123, 255));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setFocusPainted(false);

        panel.add(voltarButton, BorderLayout.SOUTH);

        add(panel);

        carregarDocumentos();

        // Evento do botão "Voltar" para retornar ao menu
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaInicialEmbaixada().setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }

    // Método para carregar os documentos do usuário logado
    private void carregarDocumentos() {
        int idUsuario = SessaoUsuario.getIdUsuarioLogado();

        StringBuilder documentos = new StringBuilder();

        try (Connection conn = Conexao.getConnection()) {
            // Consulta para obter os passaportes do usuário
            String sqlPassaporte = "SELECT numeroPassaporte, dataEmissao, autoridadeEmissora FROM passaporte WHERE idusuario = ?";
            PreparedStatement stmtPassaporte = conn.prepareStatement(sqlPassaporte);
            stmtPassaporte.setInt(1, idUsuario);
            ResultSet rsPassaporte = stmtPassaporte.executeQuery();

            documentos.append("=== Passaportes ===\n");
            boolean hasPassaporte = false;
            while (rsPassaporte.next()) {
                hasPassaporte = true;
                documentos.append("Número: ").append(rsPassaporte.getString("numeroPassaporte")).append("\n");
                documentos.append("Data de Emissão: ").append(rsPassaporte.getDate("dataEmissao")).append("\n");
                documentos.append("Autoridade Emissora: ").append(rsPassaporte.getString("autoridadeEmissora")).append("\n\n");
            }
            if (!hasPassaporte) {
                documentos.append("Nenhum passaporte cadastrado.\n\n");
            }

            // Consulta para obter os vistos do usuário
            String sqlVisto = "SELECT tipo, paisesdestino, statusVisto, dataEmissao, dataValidade FROM visto WHERE idusuario = ?";
            PreparedStatement stmtVisto = conn.prepareStatement(sqlVisto);
            stmtVisto.setInt(1, idUsuario);
            ResultSet rsVisto = stmtVisto.executeQuery();

            documentos.append("=== Vistos ===\n");
            boolean hasVisto = false;
            while (rsVisto.next()) {
                hasVisto = true;
                documentos.append("Tipo: ").append(rsVisto.getString("tipo")).append("\n");
                documentos.append("Países de Destino: ").append(rsVisto.getString("paisesdestino")).append("\n");
                documentos.append("Status: ").append(rsVisto.getString("statusVisto")).append("\n");
                documentos.append("Data de Emissão: ").append(rsVisto.getDate("dataEmissao")).append("\n");
                documentos.append("Data de Validade: ").append(rsVisto.getDate("dataValidade")).append("\n\n");
            }
            if (!hasVisto) {
                documentos.append("Nenhum visto cadastrado.\n\n");
            }

            textArea.setText(documentos.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar os documentos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Simulação de usuário logado para teste
        SessaoUsuario.setUsuarioLogado(SessaoUsuario.getUsuarioLogado(), SessaoUsuario.getNacionalidade(), SessaoUsuario.getIdUsuarioLogado());
        new TelaExibirDocumentos();
    }
}
