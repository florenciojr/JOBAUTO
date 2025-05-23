package test;

import model.PerfilCandidato;

import java.util.List;

import dao.PerfilCandidatos;

public class TestePerfilCandidatoDAO {
    public static void main(String[] args) {
        PerfilCandidatos perfilDAO = new PerfilCandidatos();
        
        // Teste de inserção
        PerfilCandidato perfil1 = new PerfilCandidato();
        perfil1.setUsuarioId(1);
        perfil1.setProfissao("Desenvolvedor Java");
        perfil1.setResumo("Desenvolvedor com 5 anos de experiência");
        perfil1.setHabilidades("Java, Spring, SQL");
        perfil1.setExperiencia("Empresa X - 3 anos como desenvolvedor");
        perfil1.setPretensaoSalarial("R$ 8.000,00");
        
        boolean inserido = perfilDAO.inserirPerfil(perfil1);
        System.out.println("Perfil inserido: " + inserido);
        
        // Teste de busca
        PerfilCandidato perfilBuscado = perfilDAO.buscarPorUsuarioId(1);
        System.out.println("\nPerfil encontrado:");
        System.out.println("Profissão: " + perfilBuscado.getProfissao());
        System.out.println("Resumo: " + perfilBuscado.getResumo());
        
        // Teste de atualização
        perfilBuscado.setPretensaoSalarial("R$ 9.000,00");
        boolean atualizado = perfilDAO.atualizarPerfil(perfilBuscado);
        System.out.println("\nPerfil atualizado: " + atualizado);
        
        // Teste de listagem
        System.out.println("\nTodos os perfis:");
        List<PerfilCandidato> perfis = perfilDAO.listarTodos();
        for (PerfilCandidato p : perfis) {
            System.out.println(p.getProfissao() + " - " + p.getPretensaoSalarial());
        }
        
        // Teste de busca por profissão
        System.out.println("\nBusca por profissão (Desenvolvedor):");
        List<PerfilCandidato> devs = perfilDAO.buscarPorProfissao("Desenvolvedor");
        for (PerfilCandidato d : devs) {
            System.out.println(d.getProfissao() + " - " + d.getHabilidades());
        }
        
        // Teste de exclusão
        boolean excluido = perfilDAO.excluirPerfil(1);
        System.out.println("\nPerfil excluído: " + excluido);
    }
}