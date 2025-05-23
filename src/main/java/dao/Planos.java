package dao;


import model.Plano;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Planos {
    private Connection conexao;

    public Planos() {
        this.conexao = util.conexao.getConexao();
    }

    // Método para inserir um novo plano
    public boolean criar(Plano plano) {
        String sql = "INSERT INTO plano (nome, descricao, valor_mensal, candidaturas_mes, beneficios) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, plano.getNome());
            stmt.setString(2, plano.getDescricao());
            stmt.setDouble(3, plano.getValorMensal());
            stmt.setInt(4, plano.getCandidaturasMes());
            stmt.setString(5, plano.getBeneficios());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        plano.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar plano: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Método para buscar plano por ID
    public Plano buscarPorId(int id) {
        String sql = "SELECT * FROM plano WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarPlanoAPartirResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar plano por ID: " + e.getMessage());
        }
        return null;
    }

    // Método para listar todos os planos
    public List<Plano> listarTodos() {
        String sql = "SELECT * FROM plano";
        List<Plano> planos = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                planos.add(criarPlanoAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar planos: " + e.getMessage());
        }
        return planos;
    }

    // Método para atualizar um plano
    public boolean atualizar(Plano plano) {
        String sql = "UPDATE plano SET nome = ?, descricao = ?, valor_mensal = ?, " +
                     "candidaturas_mes = ?, beneficios = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, plano.getNome());
            stmt.setString(2, plano.getDescricao());
            stmt.setDouble(3, plano.getValorMensal());
            stmt.setInt(4, plano.getCandidaturasMes());
            stmt.setString(5, plano.getBeneficios());
            stmt.setInt(6, plano.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar plano: " + e.getMessage());
        }
        return false;
    }

    // Método para deletar um plano
    public boolean deletar(int id) {
        String sql = "DELETE FROM plano WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar plano: " + e.getMessage());
        }
        return false;
    }

    // Método auxiliar para criar objeto Plano a partir do ResultSet
    private Plano criarPlanoAPartirResultSet(ResultSet rs) throws SQLException {
        Plano plano = new Plano();
        plano.setId(rs.getInt("id"));
        plano.setNome(rs.getString("nome"));
        plano.setDescricao(rs.getString("descricao"));
        plano.setValorMensal(rs.getDouble("valor_mensal"));
        plano.setCandidaturasMes(rs.getInt("candidaturas_mes"));
        plano.setBeneficios(rs.getString("beneficios"));
        return plano;
    }

    public void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}