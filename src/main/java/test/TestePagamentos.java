package test;

import model.Pagamento;
import dao.Pagamentos;
import java.util.List;

public class TestePagamentos {
    public static void main(String[] args) {
        Pagamentos pagamentos = new Pagamentos();
        
        try {
            System.out.println("=== TESTE DAO PAGAMENTO ===");
            
            // 1. Teste de inserção
            System.out.println("\n1. Teste de inserção:");
            Pagamento novoPagamento = new Pagamento(1, 199.90, "PENDENTE");
            novoPagamento.setDataPagamento("2023-11-15 14:30:00");
            novoPagamento.setMetodo("Cartão de Crédito");
            novoPagamento.setDescricao("Pagamento mensalidade plano Premium");
            
            boolean inserido = pagamentos.inserir(novoPagamento);
            System.out.println("Pagamento inserido: " + inserido);
            
            if (inserido) {
                System.out.println("ID gerado: " + novoPagamento.getId());
                
                // 2. Teste de busca por ID
                System.out.println("\n2. Teste de busca por ID:");
                Pagamento pagamentoBuscado = pagamentos.buscarPorId(novoPagamento.getId());
                if (pagamentoBuscado != null) {
                    System.out.println("Pagamento encontrado:");
                    System.out.println("Valor: " + pagamentoBuscado.getValor());
                    System.out.println("Status: " + pagamentoBuscado.getStatus());
                    
                    // 3. Teste de atualização
                    System.out.println("\n3. Teste de atualização:");
                    pagamentoBuscado.setStatus("APROVADO");
                    boolean atualizado = pagamentos.atualizar(pagamentoBuscado);
                    System.out.println("Pagamento atualizado: " + atualizado);
                    
                    // 4. Teste de listagem
                    System.out.println("\n4. Teste de listagem:");
                    List<Pagamento> todosPagamentos = pagamentos.listarTodos();
                    System.out.println("Total de pagamentos: " + todosPagamentos.size());
                    for (Pagamento p : todosPagamentos) {
                        System.out.println(p.getId() + " | R$ " + p.getValor() + " | " + p.getStatus());
                    }
                    
                    // 5. Teste de busca por usuário
                    System.out.println("\n5. Teste de busca por usuário:");
                    List<Pagamento> pagamentosUsuario = pagamentos.buscarPorUsuario(1);
                    System.out.println("Pagamentos do usuário 1: " + pagamentosUsuario.size());
                    
                    // 6. Teste de busca por status
                    System.out.println("\n6. Teste de busca por status:");
                    List<Pagamento> pagamentosAprovados = pagamentos.buscarPorStatus("APROVADO");
                    System.out.println("Pagamentos aprovados: " + pagamentosAprovados.size());
                    
                   
                }
            }
        } finally {
           
            System.out.println("\n=== FIM DOS TESTES ===");
        }
    }
}