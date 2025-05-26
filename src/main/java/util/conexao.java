package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/jobauto?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado!");
            throw new RuntimeException("Driver JDBC não encontrado", e);
        }
    }
    
    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(
            URL + "?useSSL=false&autoReconnect=true&connectTimeout=30000&socketTimeout=30000", 
            USER, 
            PASSWORD
        );
    }
    
    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
    public static void fecharRecursos(AutoCloseable... recursos) {
        for (AutoCloseable recurso : recursos) {
            if (recurso != null) {
                try {
                    recurso.close();
                } catch (Exception e) {
                    System.err.println("Erro ao fechar recurso: " + e.getMessage());
                }
            }
        }
    }
}