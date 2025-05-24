package test;

import model.Assinatura;
import dao.Assinaturas;

public class TesteAssinaturaDAO {
    public static void main(String[] args) {
        Assinaturas assinaturas = new Assinaturas();
        
        try {
            System.out.println("=== TESTE DAO ASSINATURA ===");
            
            // 1. Teste de criação
            System.out.println("\n1. Teste de criação:");
            Assinatura novaAssinatura = new Assinatura();
            novaAssinatura.setCandidatoId(1);
            novaAssinatura.setPlanoId(2);
            novaAssinatura.setDataInicio("2023-11-01");
            novaAssinatura.setDataFim("2023-12-01");
            novaAssinatura.setAtiva(true);
            
            boolean criada = assinaturas.criar(novaAssinatura);
            System.out.println("Assinatura criada: " + criada);
            System.out.println("ID gerado: " + novaAssinatura.getId());
            
            // 2. Teste de busca por ID
            System.out.println("\n2. Teste de busca por ID:");
            Assinatura assinaturaBuscada = assinaturas.buscarPorId(novaAssinatura.getId());
            if (assinaturaBuscada != null) {
                System.out.println("Assinatura encontrada:");
                System.out.println("ID: " + assinaturaBuscada.getId());
                System.out.println("Candidato ID: " + assinaturaBuscada.getCandidatoId());
                System.out.println("Plano ID: " + assinaturaBuscada.getPlanoId());
                System.out.println("Ativa: " + assinaturaBuscada.isAtiva());
            } else {
                System.out.println("Assinatura não encontrada!");
            }
            
            // 3. Teste de atualização
            System.out.println("\n3. Teste de atualização:");
            assinaturaBuscada.setDataFim("2023-12-31");
            boolean atualizada = assinaturas.atualizar(assinaturaBuscada);
            System.out.println("Assinatura atualizada: " + atualizada);
            
            // 4. Teste de listagem
            System.out.println("\n4. Teste de listagem:");
            System.out.println("Todas as assinaturas:");
            assinaturas.listarTodas().forEach(a -> 
                System.out.println(a.getId() + " | Candidato: " + a.getCandidatoId() + 
                                 " | Ativa: " + a.isAtiva())
            );
            
            // 5. Teste de busca por candidato
            System.out.println("\n5. Teste de busca por candidato:");
            System.out.println("Assinaturas do candidato 1:");
            assinaturas.buscarPorCandidato(1).forEach(a ->
                System.out.println("ID: " + a.getId() + " | Plano: " + a.getPlanoId())
            );
            
            // 6. Teste de cancelamento
            System.out.println("\n6. Teste de cancelamento:");
            boolean cancelada = assinaturas.cancelar(novaAssinatura.getId());
            System.out.println("Assinatura cancelada: " + cancelada);
            
            // Verificar status após cancelamento
            Assinatura assinaturaCancelada = assinaturas.buscarPorId(novaAssinatura.getId());
            System.out.println("Status após cancelamento: " + (assinaturaCancelada.isAtiva() ? "Ativa" : "Inativa"));
            
        } finally {
           
            System.out.println("\n=== FIM DOS TESTES ===");
        }
    }
}