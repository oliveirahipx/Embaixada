package Telas;

public class SessaoUsuario {
    private static String usuarioLogado;
    private static String nacionalidade;
    private static int idUsuario; // Adicionado para armazenar o ID do usuário

    // Método para configurar o usuário logado, nacionalidade e ID
    public static void setUsuarioLogado(String usuario, String nacionalidadeUsuario, int id) {
        usuarioLogado = usuario;
        nacionalidade = nacionalidadeUsuario;
        idUsuario = id;
    }

    // Método para obter o nome do usuário logado
    public static String getUsuarioLogado() {
        return usuarioLogado;
    }

    // Método para obter a nacionalidade do usuário logado
    public static String getNacionalidade() {
        return nacionalidade;
    }

    // Método para obter o ID do usuário logado
    public static int getIdUsuarioLogado() {
        return idUsuario;
    }
}
