package dao;

import model.Plano;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.conexao;

public class Planos {
    
    public boolean criar(Plano plano) {
        String sql = "INSERT INTO plano (nome, descricao, valor_mensal, candidaturas_mes, beneficios) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, plano.getNome());
            stmt.setString(2, plano.getDescricao());
            stmt.setDouble(3, plano.getValorMensal());
            stmt.setInt(4, plano.getCandidaturasMes());
            stmt.setString(5, plano.getBeneficios());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    plano.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar plano: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexao.fecharRecursos(generatedKeys, stmt, conn);
        }
        return false;
    }

    public Plano buscarPorId(int id) {
        String sql = "SELECT * FROM plano WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarPlanoAPartirResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar plano por ID: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return null;
    }

    public List<Plano> listarTodos() {
        String sql = "SELECT * FROM plano";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Plano> planos = new ArrayList<>();
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                planos.add(criarPlanoAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar planos: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return planos;
    }

    public boolean atualizar(Plano plano) {
        String sql = "UPDATE plano SET nome = ?, descricao = ?, valor_mensal = ?, " +
                     "candidaturas_mes = ?, beneficios = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            
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
        } finally {
            conexao.fecharRecursos(stmt, conn);
        }
        return false;
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM plano WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar plano: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(stmt, conn);
        }
        return false;
    }

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
}