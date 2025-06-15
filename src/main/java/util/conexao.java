package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/jobauto?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    // Variável para injeção de dependência em testes
    private static Connection testConnection = null;
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado!");
            throw new RuntimeException("Driver JDBC não encontrado", e);
        }
    }
    
    public static Connection getConexao() throws SQLException {
        if (testConnection != null) {
            return testConnection; // Retorna a conexão mockada durante os testes
        }
        return DriverManager.getConnection(
            URL + "?useSSL=false&autoReconnect=true&connectTimeout=30000&socketTimeout=30000", 
            USER, 
            PASSWORD
        );
    }
    
    /**
     * Método para testes - permite injetar uma conexão mockada
     */
    public static void setTestConnection(Connection connection) {
        testConnection = connection;
    }
    
    /**
     * Método para testes - limpa a conexão mockada
     */
    public static void resetTestConnection() {
        testConnection = null;
    }
    
    public static void fecharConexao(Connection conexao) {
        if (conexao != null && conexao != testConnection) { // Não fecha conexões de teste
            try {
                conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
    public static void fecharRecursos(AutoCloseable... recursos) {
        for (AutoCloseable recurso : recursos) {
            if (recurso != null && !(recurso instanceof Connection && recurso == testConnection)) {
                try {
                    recurso.close();
                } catch (Exception e) {
                    System.err.println("Erro ao fechar recurso: " + e.getMessage());
                }
            }
        }
    }
}
