package test;

import model.Plano;
import dao.Planos;
import java.util.List;

public class TestePlanoDAO {
    public static void main(String[] args) {
        Planos planos = new Planos();
        
        try {
            System.out.println("=== TESTE DAO PLANO ===");
            
            // 1. Teste de criação
            System.out.println("\n1. Teste de criação:");
            Plano novoPlano = new Plano();
            novoPlano.setNome("Plano Premium");
            novoPlano.setDescricao("Plano com benefícios exclusivos");
            novoPlano.setValorMensal(199.90);
            novoPlano.setCandidaturasMes(30);
            novoPlano.setBeneficios("Acesso a vagas exclusivas, Mentoria profissional");
            
            boolean criado = planos.criar(novoPlano);
            System.out.println("Plano criado: " + criado);
            
            if (criado) {
                System.out.println("ID gerado: " + novoPlano.getId());
                
                // 2. Teste de busca por ID
                System.out.println("\n2. Teste de busca por ID:");
                Plano planoBuscado = planos.buscarPorId(novoPlano.getId());
                if (planoBuscado != null) {
                    System.out.println("Plano encontrado:");
                    System.out.println("Nome: " + planoBuscado.getNome());
                    System.out.println("Valor: " + planoBuscado.getValorMensal());
                    System.out.println("Candidaturas/mês: " + planoBuscado.getCandidaturasMes());
                    
                    // 3. Teste de atualização
                    System.out.println("\n3. Teste de atualização:");
                    planoBuscado.setValorMensal(179.90);
                    boolean atualizado = planos.atualizar(planoBuscado);
                    System.out.println("Plano atualizado: " + atualizado);
                    
                    // 4. Teste de listagem
                    System.out.println("\n4. Teste de listagem:");
                    List<Plano> todosPlanos = planos.listarTodos();
                    System.out.println("Total de planos: " + todosPlanos.size());
                    for (Plano p : todosPlanos) {
                        System.out.println(p.getId() + " | " + p.getNome() + 
                                         " | R$ " + p.getValorMensal());
                    }
                    
                   
                }
            }
        } finally {
            planos.fecharConexao();
            System.out.println("\n=== FIM DOS TESTES ===");
        }
    }
}