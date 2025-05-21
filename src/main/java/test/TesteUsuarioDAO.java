package test;

import dao.usuario;  // Note o nome minúsculo para coincidir com sua classe
import model.Usuario;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TesteUsuarioDAO {

    public static void main(String[] args) {
        // Instancia o DAO com o nome correto (usuario em minúsculo)
        usuario usuarioDAO = new usuario();
        
        // Formata a data atual para cadastro
        String dataAtual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        System.out.println("=== TESTE DA CLASSE usuario (DAO) ===");
        
        // Teste 1: Inserção
        System.out.println("\n[TESTE 1] Inserir usuário");
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail("teste@exemplo.com");
        novoUsuario.setSenha("123456");
        novoUsuario.setTipo("CANDIDATO");
        novoUsuario.setNome("Teste Silva");
        novoUsuario.setTelefone("(21) 98765-4321");
        novoUsuario.setDataCadastro(dataAtual);
        novoUsuario.setAtivo(true);
        novoUsuario.setFotoPerfil("avatar.jpg");

        if (usuarioDAO.inserirUsuario(novoUsuario)) {
            System.out.println("✔ Inserção OK - ID gerado: " + novoUsuario.getId());
            
            // Teste 2: Busca por ID
            System.out.println("\n[TESTE 2] Busca por ID");
            Usuario usuarioRecuperado = usuarioDAO.buscarPorId(novoUsuario.getId());
            
            if (usuarioRecuperado != null) {
                System.out.println("✔ Usuário encontrado: " + usuarioRecuperado.getNome());
                
                // Teste 3: Atualização
                System.out.println("\n[TESTE 3] Atualização");
                usuarioRecuperado.setNome("Nome Alterado");
                if (usuarioDAO.atualizarUsuario(usuarioRecuperado)) {
                    System.out.println("✔ Atualização OK");
                } else {
                    System.out.println("✖ Falha na atualização");
                }
                
                // Teste 4: Busca por Email
                System.out.println("\n[TESTE 4] Busca por Email");
                Usuario porEmail = usuarioDAO.buscarPorEmail(novoUsuario.getEmail());
                System.out.println(porEmail != null ? "✔ Usuário encontrado" : "✖ Usuário não encontrado");
                
                // Teste 5: Listagem
                System.out.println("\n[TESTE 5] Listar Todos");
                List<Usuario> usuarios = usuarioDAO.listarTodos();
                System.out.println("Total de usuários: " + usuarios.size());
                
        
            } else {
                System.out.println("✖ Usuário não encontrado após inserção");
            }
        } else {
            System.out.println("✖ Falha na inserção do usuário");
        }
        
        System.out.println("\n=== FIM DOS TESTES ===");
    }
}