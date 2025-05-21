package test;

import util.conexao;
import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {
        System.out.println("=== TESTE DA CLASSE CONEXÃO ===");
        
        // Teste 1: Obter conexão pela primeira vez
        System.out.println("\n[TESTE 1] Obter primeira conexão");
        Connection conn1 = conexao.getConexao();
        if (conn1 != null) {
            System.out.println("✔ Conexão 1 obtida com sucesso!");
            testarConexao(conn1);
        } else {
            System.out.println("✖ Falha ao obter primeira conexão");
        }
        
        // Teste 2: Obter segunda conexão (deve reusar a mesma)
        System.out.println("\n[TESTE 2] Obter segunda conexão (deve reusar)");
        Connection conn2 = conexao.getConexao();
        if (conn2 != null) {
            System.out.println("✔ Conexão 2 obtida com sucesso!");
            System.out.println("As conexões são a mesma instância? " + (conn1 == conn2 ? "Sim ✔" : "Não ✖"));
        } else {
            System.out.println("✖ Falha ao obter segunda conexão");
        }
        
        // Teste 3: Fechar conexão
        System.out.println("\n[TESTE 3] Fechar conexão");
        conexao.fecharConexao();
        try {
            System.out.println("Conexão está fechada? " + (conn1.isClosed() ? "Sim ✔" : "Não ✖"));
        } catch (SQLException e) {
            System.out.println("✖ Erro ao verificar status da conexão: " + e.getMessage());
        }
        
        // Teste 4: Obter nova conexão após fechar
        System.out.println("\n[TESTE 4] Obter nova conexão após fechar");
        Connection conn3 = conexao.getConexao();
        if (conn3 != null) {
            System.out.println("✔ Nova conexão obtida com sucesso!");
            System.out.println("É uma nova instância? " + (conn3 != conn1 ? "Sim ✔" : "Não ✖"));
            testarConexao(conn3);
        } else {
            System.out.println("✖ Falha ao obter nova conexão");
        }
        
        // Fechar conexão no final
        conexao.fecharConexao();
        System.out.println("\n=== FIM DOS TESTES ===");
    }
    
    private static void testarConexao(Connection conn) {
        try {
            System.out.println("Auto-commit: " + conn.getAutoCommit());
            System.out.println("Isolamento: " + conn.getTransactionIsolation());
            System.out.println("Banco de dados: " + conn.getMetaData().getDatabaseProductName() + 
                              " v" + conn.getMetaData().getDatabaseProductVersion());
        } catch (SQLException e) {
            System.out.println("✖ Erro ao testar conexão: " + e.getMessage());
        }
    }
}