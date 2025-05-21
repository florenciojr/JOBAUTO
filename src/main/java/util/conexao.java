package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/jobauto";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static Connection conexao = null;
    
    public static Connection getConexao() {
        if (conexao == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão estabelecida com sucesso!");
            } catch (ClassNotFoundException e) {
                System.out.println("Driver JDBC não encontrado!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados!");
                e.printStackTrace();
            }
        }
        return conexao;
    }
    
    public static void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
                System.out.println("Conexão fechada com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão com o banco de dados!");
                e.printStackTrace();
            }
        }
    }
}