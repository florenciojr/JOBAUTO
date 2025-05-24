package dao;

import model.Candidatura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.conexao;

public class Candidaturas {
    
    public boolean inserir(Candidatura candidatura) {
        String sql = "INSERT INTO candidatura (vaga_id, candidato_id, data_candidatura, tipo, status) " +
                   "VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            String tipo = validarTipo(candidatura.getTipo());
            String status = validarStatus(candidatura.getStatus());
            
            stmt.setInt(1, candidatura.getVagaId());
            stmt.setInt(2, candidatura.getCandidatoId());
            stmt.setTimestamp(3, candidatura.getDataCandidatura() != null ? 
                Timestamp.valueOf(candidatura.getDataCandidatura()) : null);
            stmt.setString(4, tipo);
            stmt.setString(5, status);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    candidatura.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir candidatura: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Erro nos dados da candidatura: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(generatedKeys, stmt, conn);
        }
        return false;
    }

    public Candidatura buscarPorId(int id) {
        String sql = "SELECT * FROM candidatura WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarCandidaturaAPartirResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar candidatura por ID: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return null;
    }

    public List<Candidatura> listarTodas() {
        String sql = "SELECT * FROM candidatura";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Candidatura> candidaturas = new ArrayList<>();
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                candidaturas.add(criarCandidaturaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar candidaturas: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return candidaturas;
    }

    public boolean atualizar(Candidatura candidatura) {
        String sql = "UPDATE candidatura SET vaga_id = ?, candidato_id = ?, data_candidatura = ?, " +
                   "tipo = ?, status = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            
            String tipo = validarTipo(candidatura.getTipo());
            String status = validarStatus(candidatura.getStatus());
            
            stmt.setInt(1, candidatura.getVagaId());
            stmt.setInt(2, candidatura.getCandidatoId());
            stmt.setTimestamp(3, candidatura.getDataCandidatura() != null ? 
                Timestamp.valueOf(candidatura.getDataCandidatura()) : null);
            stmt.setString(4, tipo);
            stmt.setString(5, status);
            stmt.setInt(6, candidatura.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar candidatura: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro nos dados da candidatura: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(stmt, conn);
        }
        return false;
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM candidatura WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar candidatura: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(stmt, conn);
        }
        return false;
    }

    public List<Candidatura> buscarPorVaga(int vagaId) {
        String sql = "SELECT * FROM candidatura WHERE vaga_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Candidatura> candidaturas = new ArrayList<>();
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vagaId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                candidaturas.add(criarCandidaturaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar candidaturas por vaga: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return candidaturas;
    }

    public List<Candidatura> buscarPorCandidato(int candidatoId) {
        String sql = "SELECT * FROM candidatura WHERE candidato_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Candidatura> candidaturas = new ArrayList<>();
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, candidatoId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                candidaturas.add(criarCandidaturaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar candidaturas por candidato: " + e.getMessage());
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return candidaturas;
    }

    private String validarTipo(String tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo não pode ser nulo");
        }
        if (!tipo.equals("AUTOMATICA") && !tipo.equals("MANUAL")) {
            throw new IllegalArgumentException("Tipo inválido: " + tipo + ". Valores permitidos: AUTOMATICA, MANUAL");
        }
        return tipo;
    }

    private String validarStatus(String status) {
        if (status == null) return "PENDENTE";
        
        if (!status.equals("PENDENTE") && !status.equals("VISUALIZADA") && 
            !status.equals("ENTREVISTA") && !status.equals("CONTRATADO") && 
            !status.equals("REJEITADA")) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
        return status;
    }

    private Candidatura criarCandidaturaAPartirResultSet(ResultSet rs) throws SQLException {
        Candidatura candidatura = new Candidatura();
        candidatura.setId(rs.getInt("id"));
        candidatura.setVagaId(rs.getInt("vaga_id"));
        candidatura.setCandidatoId(rs.getInt("candidato_id"));
        candidatura.setDataCandidatura(rs.getTimestamp("data_candidatura") != null ? 
            rs.getTimestamp("data_candidatura").toLocalDateTime().toString() : null);
        candidatura.setTipo(rs.getString("tipo"));
        candidatura.setStatus(rs.getString("status"));
        return candidatura;
    }
}