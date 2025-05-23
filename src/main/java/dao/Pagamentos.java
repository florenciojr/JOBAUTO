package dao;


import model.Pagamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pagamentos {
    private Connection conexao;

    public Pagamentos() {
        this.conexao = util.conexao.getConexao();
    }

    // Método para inserir um novo pagamento
    public boolean inserir(Pagamento pagamento) {
        String sql = "INSERT INTO pagamento (usuario_id, valor, data_pagamento, metodo, status, descricao) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, pagamento.getUsuarioId());
            stmt.setDouble(2, pagamento.getValor());
            stmt.setString(3, pagamento.getDataPagamento());
            stmt.setString(4, pagamento.getMetodo());
            stmt.setString(5, pagamento.getStatus());
            stmt.setString(6, pagamento.getDescricao());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pagamento.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir pagamento: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Método para buscar pagamento por ID
    public Pagamento buscarPorId(int id) {
        String sql = "SELECT * FROM pagamento WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarPagamentoAPartirResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pagamento por ID: " + e.getMessage());
        }
        return null;
    }

    // Método para listar todos os pagamentos
    public List<Pagamento> listarTodos() {
        String sql = "SELECT * FROM pagamento";
        List<Pagamento> pagamentos = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                pagamentos.add(criarPagamentoAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pagamentos: " + e.getMessage());
        }
        return pagamentos;
    }

    // Método para atualizar um pagamento
    public boolean atualizar(Pagamento pagamento) {
        String sql = "UPDATE pagamento SET usuario_id = ?, valor = ?, data_pagamento = ?, " +
                     "metodo = ?, status = ?, descricao = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
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
        }
        return false;
    }

    // Método para deletar um pagamento
    public boolean deletar(int id) {
        String sql = "DELETE FROM pagamento WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar pagamento: " + e.getMessage());
        }
        return false;
    }

    // Método para buscar pagamentos por usuário
    public List<Pagamento> buscarPorUsuario(int usuarioId) {
        String sql = "SELECT * FROM pagamento WHERE usuario_id = ?";
        List<Pagamento> pagamentos = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                pagamentos.add(criarPagamentoAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pagamentos por usuário: " + e.getMessage());
        }
        return pagamentos;
    }

    // Método para buscar pagamentos por status
    public List<Pagamento> buscarPorStatus(String status) {
        String sql = "SELECT * FROM pagamento WHERE status = ?";
        List<Pagamento> pagamentos = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                pagamentos.add(criarPagamentoAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pagamentos por status: " + e.getMessage());
        }
        return pagamentos;
    }

    // Método auxiliar para criar objeto Pagamento a partir do ResultSet
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