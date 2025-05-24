package dao;

import model.Pagamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.conexao;

public class Pagamentos {
    
    public boolean inserir(Pagamento pagamento) {
        String sql = "INSERT INTO pagamento (usuario_id, valor, data_pagamento, metodo, status, descricao) " +
                   "VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, pagamento.getUsuarioId());
            stmt.setDouble(2, pagamento.getValor());
            stmt.setString(3, pagamento.getDataPagamento());
            stmt.setString(4, pagamento.getMetodo());
            stmt.setString(5, pagamento.getStatus());
            stmt.setString(6, pagamento.getDescricao());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    pagamento.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir pagamento: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexao.fecharRecursos(generatedKeys, stmt, conn);
        }
        return false;
    }

    public Pagamento buscarPorId(int id) {
        String sql = "SELECT * FROM pagamento WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarPagamentoAPartirResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pagamento por ID: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return null;
    }

    public List<Pagamento> listarTodos() {
        String sql = "SELECT * FROM pagamento";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Pagamento> pagamentos = new ArrayList<>();
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                pagamentos.add(criarPagamentoAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pagamentos: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return pagamentos;
    }

    public boolean atualizar(Pagamento pagamento) {
        String sql = "UPDATE pagamento SET usuario_id = ?, valor = ?, data_pagamento = ?, " +
                     "metodo = ?, status = ?, descricao = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, pagamento.getUsuarioId());
            stmt.setDouble(2, pagamento.getValor());
            stmt.setString(3, pagamento.getDataPagamento());
            stmt.setString(4, pagamento.getMetodo());
            stmt.setString(5, pagamento.getStatus());
            stmt.setString(6, pagamento.getDescricao());
            stmt.setInt(7, pagamento.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pagamento: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(stmt, conn);
        }
        return false;
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM pagamento WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar pagamento: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(stmt, conn);
        }
        return false;
    }

    public List<Pagamento> buscarPorUsuario(int usuarioId) {
        String sql = "SELECT * FROM pagamento WHERE usuario_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Pagamento> pagamentos = new ArrayList<>();
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuarioId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                pagamentos.add(criarPagamentoAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pagamentos por usuário: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return pagamentos;
    }

    public List<Pagamento> buscarPorStatus(String status) {
        String sql = "SELECT * FROM pagamento WHERE status = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Pagamento> pagamentos = new ArrayList<>();
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                pagamentos.add(criarPagamentoAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pagamentos por status: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return pagamentos;
    }

    private Pagamento criarPagamentoAPartirResultSet(ResultSet rs) throws SQLException {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(rs.getInt("id"));
        pagamento.setUsuarioId(rs.getInt("usuario_id"));
        pagamento.setValor(rs.getDouble("valor"));
        pagamento.setDataPagamento(rs.getString("data_pagamento"));
        pagamento.setMetodo(rs.getString("metodo"));
        pagamento.setStatus(rs.getString("status"));
        pagamento.setDescricao(rs.getString("descricao"));
        return pagamento;
    }
}