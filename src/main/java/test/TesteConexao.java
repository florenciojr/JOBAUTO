package test;

import util.conexao;
import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {
        System.out.println("=== TESTE DA CLASSE CONEXÃO ===");
        
        Connection conn1 = null, conn2 = null, conn3 = null;
        
        try {
            // Teste 1: Obter conexão pela primeira vez
            System.out.println("\n[TESTE 1] Obter primeira conexão");
            conn1 = conexao.getConexao();
            if (conn1 != null && !conn1.isClosed()) {
                System.out.println("✔ Conexão 1 obtida com sucesso!");
                testarConexao(conn1);
            } else {
                System.out.println("✖ Falha ao obter primeira conexão");
            }
            
            // Teste 2: Obter segunda conexão (nova instância)
            System.out.println("\n[TESTE 2] Obter segunda conexão (nova instância)");
            conn2 = conexao.getConexao();
            if (conn2 != null && !conn2.isClosed()) {
                System.out.println("✔ Conexão 2 obtida com sucesso!");
                System.out.println("As conexões são a mesma instância? " + 
                    (conn1 == conn2 ? "Sim ✔" : "Não ✔ (como esperado)"));
                testarConexao(conn2);
            } else {
                System.out.println("✖ Falha ao obter segunda conexão");
            }
            
            // Teste 3: Fechar conexões
            System.out.println("\n[TESTE 3] Fechar conexões");
            conexao.fecharConexao(conn1);
            conexao.fecharConexao(conn2);
            
            try {
                System.out.println("Conexão 1 está fechada? " + 
                    (conn1.isClosed() ? "Sim ✔" : "Não ✖"));
                System.out.println("Conexão 2 está fechada? " + 
                    (conn2.isClosed() ? "Sim ✔" : "Não ✖"));
            } catch (SQLException e) {
                System.out.println("✖ Erro ao verificar status da conexão: " + e.getMessage());
            }
            
            // Teste 4: Obter nova conexão após fechar
            System.out.println("\n[TESTE 4] Obter nova conexão após fechar");
            conn3 = conexao.getConexao();
            if (conn3 != null && !conn3.isClosed()) {
                System.out.println("✔ Nova conexão obtida com sucesso!");
                System.out.println("É uma nova instância? " + 
                    (conn3 != conn1 && conn3 != conn2 ? "Sim ✔" : "Não ✖"));
                testarConexao(conn3);
            } else {
                System.out.println("✖ Falha ao obter nova conexão");
            }
            
        } catch (SQLException e) {
            System.out.println("✖ Erro durante os testes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Garante que todas as conexões serão fechadas
            conexao.fecharConexao(conn1);
            conexao.fecharConexao(conn2);
            conexao.fecharConexao(conn3);
            System.out.println("\n=== FIM DOS TESTES ===");
        }
    }
    
    private static void testarConexao(Connection conn) {
        try {
            System.out.println("- Auto-commit: " + conn.getAutoCommit());
            System.out.println("- Isolamento: " + getTransactionIsolationName(conn.getTransactionIsolation()));
            System.out.println("- Banco de dados: " + conn.getMetaData().getDatabaseProductName() + 
                              " v" + conn.getMetaData().getDatabaseProductVersion());
            System.out.println("- URL: " + conn.getMetaData().getURL());
        } catch (SQLException e) {
            System.out.println("✖ Erro ao testar conexão: " + e.getMessage());
        }
    }
    
    private static String getTransactionIsolationName(int level) {
        switch(level) {
            case Connection.TRANSACTION_NONE: return "NONE";
            case Connection.TRANSACTION_READ_UNCOMMITTED: return "READ_UNCOMMITTED";
            case Connection.TRANSACTION_READ_COMMITTED: return "READ_COMMITTED";
            case Connection.TRANSACTION_REPEATABLE_READ: return "REPEATABLE_READ";
            case Connection.TRANSACTION_SERIALIZABLE: return "SERIALIZABLE";
            default: return "DESCONHECIDO (" + level + ")";
        }
    }
}