package test;

import dao.Usuarios;
import model.Usuario;
import java.util.List;

public class TesteUsuarioDAO {
    public static void main(String[] args) {
        Usuarios usuarioDAO = new Usuarios();
        
        System.out.println("=== TESTE CRUD USUÁRIO ===");
        
        // Teste de criação (CREATE)
        System.out.println("\n--- Teste CREATE ---");
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("João Teste");
        novoUsuario.setEmail("joao@testeettt.com");
        novoUsuario.setSenha("123456");
        novoUsuario.setTipo("CANDIDATO");
        novoUsuario.setTelefone("(11) 99999-9999");
        novoUsuario.setDataCadastro("2023-05-25");
        novoUsuario.setAtivo(true);
        novoUsuario.setFotoPerfil("uploads/foto.jpg");
        
        boolean criado = usuarioDAO.inserirUsuario(novoUsuario);
        System.out.println("Usuário criado? " + criado);
        System.out.println("ID do novo usuário: " + novoUsuario.getId());
        
        // Teste de leitura (READ)
        System.out.println("\n--- Teste READ ---");
        Usuario usuarioRecuperado = usuarioDAO.buscarPorId(novoUsuario.getId());
        System.out.println("Usuário recuperado: " + usuarioRecuperado.getNome());
        
        // Teste de listagem
        System.out.println("\n--- Listar todos ---");
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        for (Usuario u : usuarios) {
            System.out.println(u.getId() + " - " + u.getNome() + " - " + u.getEmail());
        }
        
        // Teste de atualização (UPDATE)
        System.out.println("\n--- Teste UPDATE ---");
        usuarioRecuperado.setNome("João Atualizado11111");
        usuarioRecuperado.setTelefone("(11) 98888-8888");
        boolean atualizado = usuarioDAO.atualizarUsuario(usuarioRecuperado);
        System.out.println("Usuário atualizado? " + atualizado);
        
        // Verificar atualização
        Usuario usuarioAtualizado = usuarioDAO.buscarPorId(usuarioRecuperado.getId());
        System.out.println("Nome após atualização: " + usuarioAtualizado.getNome());
        System.out.println("Telefone após atualização: " + usuarioAtualizado.getTelefone());
        
  
        // Verificar exclusão
        Usuario usuarioDeletado = usuarioDAO.buscarPorId(usuarioAtualizado.getId());
        System.out.println("Usuário ainda existe? " + (usuarioDeletado != null));
        
        // Teste buscar por email
        System.out.println("\n--- Teste buscar por email ---");
        Usuario usuarioEmail = usuarioDAO.buscarPorEmail("joao@teste.com");
        System.out.println("Usuário por email: " + (usuarioEmail != null ? usuarioEmail.getNome() : "Não encontrado"));
    }
}
