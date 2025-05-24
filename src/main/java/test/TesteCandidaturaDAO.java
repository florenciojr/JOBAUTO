package test;

import model.Candidatura;
import dao.Candidaturas;
import java.util.List;

public class TesteCandidaturaDAO {
    public static void main(String[] args) {
        Candidaturas candidaturas = new Candidaturas();
        
        try {
            System.out.println("=== TESTE DAO CANDIDATURA ===");
            
            // 1. Teste de inserção
            System.out.println("\n1. Teste de inserção:");
            Candidatura novaCandidatura = new Candidatura();
            novaCandidatura.setVagaId(1);
            novaCandidatura.setCandidatoId(1);
            novaCandidatura.setTipo("AUTOMATICA");
            novaCandidatura.setStatus("PENDENTE");
            
            boolean inserida = candidaturas.inserir(novaCandidatura);
            System.out.println("Candidatura inserida: " + inserida);
            System.out.println("ID gerado: " + novaCandidatura.getId());
            
            // 2. Teste de busca por ID
            System.out.println("\n2. Teste de busca por ID:");
            Candidatura candidaturaBuscada = candidaturas.buscarPorId(novaCandidatura.getId());
            if (candidaturaBuscada != null) {
                System.out.println("Candidatura encontrada:");
                System.out.println("ID: " + candidaturaBuscada.getId());
                System.out.println("Vaga ID: " + candidaturaBuscada.getVagaId());
                System.out.println("Candidato ID: " + candidaturaBuscada.getCandidatoId());
                System.out.println("Tipo: " + candidaturaBuscada.getTipo());
                System.out.println("Status: " + candidaturaBuscada.getStatus());
            } else {
                System.out.println("Candidatura não encontrada!");
            }
            
            // 3. Teste de atualização
            System.out.println("\n3. Teste de atualização:");
            candidaturaBuscada.setStatus("VISUALIZADA");
            boolean atualizada = candidaturas.atualizar(candidaturaBuscada);
            System.out.println("Candidatura atualizada: " + atualizada);
            
            // 4. Teste de listagem
            System.out.println("\n4. Teste de listagem:");
            List<Candidatura> todasCandidaturas = candidaturas.listarTodas();
            System.out.println("Total de candidaturas: " + todasCandidaturas.size());
            for (Candidatura c : todasCandidaturas) {
                System.out.println(c.getId() + " | Vaga: " + c.getVagaId() + 
                                 " | Candidato: " + c.getCandidatoId() + 
                                 " | Status: " + c.getStatus());
            }
            
            // 5. Teste de busca por vaga
            System.out.println("\n5. Teste de busca por vaga:");
            List<Candidatura> candidaturasVaga = candidaturas.buscarPorVaga(1);
            System.out.println("Candidaturas para vaga 1: " + candidaturasVaga.size());
            
            // 6. Teste de busca por candidato
            System.out.println("\n6. Teste de busca por candidato:");
            List<Candidatura> candidaturasCandidato = candidaturas.buscarPorCandidato(1);
            System.out.println("Candidaturas do candidato 1: " + candidaturasCandidato.size());
            
            // 7. Teste de deleção
      
            
            // 8. Teste de validação de tipo inválido
            System.out.println("\n8. Teste de validação (tipo inválido):");
            Candidatura candidaturaInvalida = new Candidatura();
            candidaturaInvalida.setVagaId(2);
            candidaturaInvalida.setCandidatoId(2);
            candidaturaInvalida.setTipo("INVALIDO");
            candidaturaInvalida.setStatus("PENDENTE");
            
            try {
                boolean resultado = candidaturas.inserir(candidaturaInvalida);
                System.out.println("Inserção deveria falhar! Resultado: " + resultado);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro esperado: " + e.getMessage());
            }
            
        } finally {
            
            System.out.println("\n=== FIM DOS TESTES ===");
        }
    }
}